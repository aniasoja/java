package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Play  {
	
	private final BorderPane rootPane ; // or any other kind of pane, or  Group...

    public Play(int width, int height, int bombs, int region) throws Exception {
    	
    	//Interface

    	Image flag = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\flag.png"));
        rootPane = new BorderPane();
        
        VBox VBox = new VBox();
    	HBox topbox = new HBox();
    	
    	Label gametime = new Label("0");
    	Region a = new Region();
    	a.setMinWidth(region);
    	Button smile = new Button("New Game");
    	Region b = new Region();
    	b.setMinWidth(region);
    	Text right = new Text("0");
    	topbox.getChildren().addAll(gametime, a, smile, b, right);
   
    	GridPane mines = new GridPane();
    	mines.setVgap(1);
    	mines.setHgap(1);
    	
    	ArrayList<Button> all = new ArrayList<Button>();
    	
    	AnimationTimer timer = new AnimationTimer() {
    	    public long timestamp;
    	    public long time = 0;
    	    public long fraction = 0;
    	    @Override
    	    public void start() {
    	        // current time adjusted by remaining time from last run
    	        timestamp = System.currentTimeMillis() - fraction;
    	        super.start();
    	    }
    	    @Override
    	    public void stop() {
    	        super.stop();
    	        // save leftover time not handled with the last update
    	        fraction = System.currentTimeMillis() - timestamp;
    	    }
    	    @Override
    	    public void handle(long now) {
    	        long newTime = System.currentTimeMillis();
    	        if (timestamp + 1000 <= newTime) {
    	            long deltaT = (newTime - timestamp) / 1000;
    	            time += deltaT;
    	            timestamp += 1000 * deltaT;
    	            gametime.setText(Long.toString(time));
    	        }
    	    }
    	};
    	smile.setOnMouseClicked(e->{
    		mines.getChildren().clear();
    		Game(width, height, timer, right, flag, bombs, mines, all);
    	});
    	
    	Game(width, height,timer, right, flag, bombs, mines, all);
    	
    	VBox.getChildren().addAll(topbox, mines);
		Scene scene = new Scene(VBox, width*35, height*35+40);
		Stage stage = new Stage();
		stage.setTitle("Minesweeper");
		stage.setScene(scene); 
		stage.show();
		
		

    }
    public Pane getRootPane() {
    	return rootPane;
    }
    
    public void Game(int width, int height, AnimationTimer timer, Text right, Image flag, int bombs, GridPane mines, ArrayList<Button> all) {
    	List<Integer> x = new ArrayList<Integer>();
        List<Integer> y = new ArrayList<Integer>();
        GenerateBombs(bombs, width, x);
        GenerateBombs(bombs, height, y);
        SortX(x, y, height);
        while(SortY(x, y, height) != 0) {
        	RemoveRepeats(x, y, height);
        }
        System.out.println(x);
        System.out.println(y);
        
    	
    	for (int i=0;i<width; ++i) {
    		for (int j=0; j<height;++j){
    			
    			Button k = new Button("");
    			k.setPrefWidth(35);
    			k.setPrefHeight(35);
    			k.setText("");
    			k.setGraphic(null);
    			k.setDisable(false);
    			mines.add(k, i, j);
    			all.add(k);
    			
    			k.setOnMouseClicked(e -> {
    				timer.start();
    				if (e.getButton() == MouseButton.SECONDARY) { //if right click
    					if(k.getOpacity() <1.0) { //so if it is flagged
    						k.setOpacity(1.0);
    						k.setGraphic(null); //I unflag it
    						right.setText((Integer.parseInt(right.getText()))-1+"");	
    					}
    					else { // I flag it
    						k.setOpacity(0.4);
    						k.setGraphic(new ImageView(flag));
    						int test = Integer.parseInt(right.getText());
    						right.setText((test+1)+"");
    						if(Integer.parseInt(right.getText())==bombs) {
    							if(CheckAll(all, bombs, x, y)==bombs) {
    								timer.stop();
    								//win
    							}
    							else {
    								try {
    	    							timer.stop();
    	    							ShowAll(all, bombs, x, y);
    	    						} catch (Exception e1) {
    	    							e1.printStackTrace();
    	    						}
    							}
    						}
    					}
    				}
    				else {
    					//here I get the coordinates - width of one button is around 30 
    					//and i change it from double, eg. 2.3 to int, showing its order
    					int check_x = (int) k.getLayoutX()/33;
    					int check_y = (int) k.getLayoutY()/33;
    					
    					boolean is_bomb = CheckIfHasBomb(bombs, check_x, check_y, x, y);
    					if(is_bomb == true) {
    						try {
    							timer.stop();
    							ShowAll(all, bombs, x, y);
    						} catch (Exception e1) {
    							e1.printStackTrace();
    						}
    						//end of game
    					}		
    					else {
    						k.setDisable(true);
    						int counter = HowManyBombsAround(check_x, check_y, bombs, x, y);
    						if(counter > 0) {
    							String text = Integer.toString(counter);
    							k.setText(text);
    						}
    						else {
    							k.setDisable(true);
    							ShowFields(check_x, check_y, all, bombs, x, y, width, height);
    						}
    					}
    				}
    			});
    		}
    	}
    	
    }

    
    public void GenerateBombs(int bombs, int max, List<Integer> list) {
		Random random_x = new Random();
	    random_x.ints(bombs, 0, max).forEach(i -> {
	    	list.add(i);
	    	});
	};
	
	public void SortX(List<Integer> list1, List<Integer> list2, int height) {
		boolean change = true;
		while(change!=false) {
			change = false;
			for(int i =0; i< list1.size()-1; i++) {
				if(list1.get(i+1)<list1.get(i)) {
					int a = list1.get(i);
					list1.remove(i);
					list1.add(i+1, a);
					int b = list2.get(i);
					list2.remove(i);
					list2.add(i+1, b);
					change = true;
				}
			}
		}
	}
		
	public int SortY(List<Integer> list1, List<Integer> list2, int height) {
		int counter = 0;
		for(int k = 0; k<list1.size()-1; k++) {
			if(list1.get(k)==list1.get(k+1)) {
				boolean change = true;
				while(change!=false) {
					change = false;
					if(list2.get(k+1)<list2.get(k)) {
						int c = list2.get(k);
						list2.remove(k);
						list2.add(k+1, c);
						change = true;
						counter++;
					}
				}
			}
		}
		return counter;
	}
	
	public void RemoveRepeats(List<Integer> list1, List<Integer> list2, int height) {
		for(int j = 0; j<list1.size()-1; j++) {
			if(list1.get(j) == list1.get(j+1)) {
				if(list2.get(j) == list2.get(j+1)) {
					boolean done = false;
					while(done != true) {
						Random rand = new Random();
						int n = rand.nextInt(height);
						if(n != list2.get(j)) {
							if(j+2 < list2.size()) {
								if(n!= list2.get(j+2)) {
									list2.set(j+1, n);
									done = true;
								}
							}	
						}
					}
				}
			}
		}
	}
	
	public boolean CheckIfHasBomb(int bombs, int check_x, int check_y, List<Integer> x, List<Integer> y) {
		boolean is_bomb = false;
		for(int o = 0; o<bombs; o++) {
			if(check_x==x.get(o)  && check_y ==y.get(o)) {
				is_bomb = true;
			}
		}
		return is_bomb;
	};
	
	public int HowManyBombsAround(int check_x, int check_y, int bombs, List<Integer> x, List<Integer> y) {
		int counter = 0;
		for(int o = 0; o<bombs; o++) {
			if(check_x + 1 == x.get(o) && check_y + 1 == y.get(o)) {
				counter++;
			}
			else if(check_x + 1 == x.get(o) && check_y == y.get(o)) {
				counter++;
			}
			else if(check_x + 1 == x.get(o) && check_y - 1 == y.get(o)) {
				counter++;
			}
			else if(check_x - 1 == x.get(o) && check_y + 1 == y.get(o)) {
				counter++;
			}
			else if(check_x - 1 == x.get(o) && check_y == y.get(o)) {
				counter++;
			}
			else if(check_x - 1 == x.get(o) && check_y - 1 == y.get(o)) {
				counter++;
			}
			else if(check_x == x.get(o) && check_y + 1 == y.get(o)) {
				counter++;
			}
			else if(check_x == x.get(o) && check_y - 1 == y.get(o)) {
				counter++;
			}
			
		}
		return counter;
	};
	public void ShowAll(List<Button> all, int bombs, List<Integer> x, List<Integer>y) throws Exception {
		Image bomb = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\bomb.jpg"));
		for(Button k:all) {
			int check_x = (int)k.getLayoutX()/33;
			int check_y = (int)k.getLayoutY()/33;
			boolean is_bomb = CheckIfHasBomb(bombs, check_x, check_y, x, y);
			if(is_bomb == true) {
				k.setDisable(true);
				k.setGraphic(new ImageView(bomb));
			}
			else {
				k.setDisable(true);
				int counter = HowManyBombsAround((int)k.getLayoutX()/33, (int)k.getLayoutY()/33, bombs, x, y);
				if(counter > 0) {
					String text = Integer.toString(counter);
					k.setText(text);
				}
			}
		}
	}
	
	public int CheckAll(List<Button> all, int bombs, List<Integer> x, List<Integer>y) {
		int counter = 0;
		for(Button k:all) {
			int check_x = (int)k.getLayoutX()/33;
			int check_y = (int)k.getLayoutY()/33;
			boolean is_bomb = CheckIfHasBomb(bombs, check_x, check_y, x, y);
			if(is_bomb == true) {
				if(k.getOpacity() <1.0) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	public void ShowFields(int check_x, int check_y, List<Button> all, int bombs, List<Integer> x, List<Integer> y, int width, int height) {
		List<Integer> openX = new ArrayList<Integer>();
		List<Integer> openY = new ArrayList<Integer>();
		
		AddNeighbours(check_x, check_y, openX, openY, all, bombs, x, y, width, height);
	
		while(openX.isEmpty()==false) {
			
			for (Button k : all){
				if((int) k.getLayoutX()/33==openX.get(0) && (int) k.getLayoutY()/33 == openY.get(0)) {
					k.setDisable(true);
					int counter = HowManyBombsAround(openX.get(0), openY.get(0), bombs, x, y);
					if(counter>0) {
						String text = Integer.toString(counter);
						k.setText(text);
					}
					else {
						AddNeighbours(openX.get(0), openY.get(0), openX, openY, all, bombs, x, y, width, height);
					}
				}
			}	
			openX.remove(0);
			openY.remove(0);
		}
	}
	
	public void AddNeighbours(int check_x, int check_y, List<Integer> openX, List<Integer> openY, List<Button> all, int bombs, List<Integer> x, List<Integer> y, int width, int height) {
		if(check_y>0 && check_x>0) {
			AddToList(all, check_y-1, check_x-1, openY, openX, bombs, x, y);
		}
		if(check_y>0 && check_x<width) {
			AddToList(all, check_y-1, check_x+1, openY, openX, bombs, x, y);
		}
		if(check_y<height && check_x>0) {
			AddToList(all, check_y+1, check_x-1, openY, openX, bombs, x, y);
		}
		if(check_y<height && check_x<width) {
			AddToList(all, check_y+1, check_x+1, openY, openX, bombs, x, y);
		}
		if(check_y>0) {
			AddToList(all, check_y-1, check_x, openY, openX, bombs, x, y);
		}
		if(check_y<height) {
			AddToList(all, check_y+1, check_x, openY, openX, bombs, x, y);
		}
		if(check_x>0) {
			AddToList(all, check_y, check_x-1, openY, openX, bombs, x, y);
		}
		if(check_x<width) {
			AddToList(all, check_y, check_x+1, openY, openX, bombs, x, y);
		}
	};
	
	public void AddToList(List<Button> all, int a, int b, List<Integer> openY, List<Integer> openX, int bombs, List<Integer> x, List<Integer> y){
		for (Button k : all){
			boolean add = true;
			if((int) k.getLayoutY()/33==a && (int) k.getLayoutX()/33 == b) {
				if(k.isDisabled()==false && k.getOpacity()==1.0) {
					for(int n=0; n<openX.size(); n++) {
						if(openX.get(n) == b && openY.get(n) == a) {
							add = false;
						}
					}
					if(add == true) {
						openY.add(a);
						openX.add(b);
					}
				}
			}
		}
	}
	
}