package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;


public class Play  {
	
	private final BorderPane rootPane ; // or any other kind of pane, or  Group...

    public Play(int width, int height, int bombs) throws Exception {

        rootPane = new BorderPane();
        
        VBox VBox = new VBox();
    	HBox topbox = new HBox();
    	
    	Text left = new Text ("score");
    	Button smile = new Button("New Game");
    	//smile.setOnMouseClicked(e->{
    	//	how do I start a new game?
    	//});
    	
    	Text right = new Text("score");
    	
    	topbox.getChildren().addAll(left, smile, right);
    	left.setStyle("fx-spacing: 20; -fx-text-alignment: left");
    	smile.setStyle("fx-spacing: 20; -fx-alignment: center");
    	right.setStyle("fx-spacing: 20; -fx-text-alignment: right");
    	
    	Image flag = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\flag.png"));
    	
    	
    	//here I generate 12 (amount of bombs) X-axis numbers and 12 Y-axis numbers
    	// so I have 12 coordinates (X, Y) of bombs
    	
        List<Integer> x = new ArrayList<Integer>();
        List<Integer> y = new ArrayList<Integer>();
        GenerateBombs(bombs, width, x);
        GenerateBombs(bombs, height, y);
        
        
    	GridPane mines = new GridPane();
    	mines.setVgap(1);
    	mines.setHgap(1);
    	
    	ArrayList<Button> all = new ArrayList<Button>();

    	for (int i=0;i<width; ++i) {
    		for (int j=0; j<height;++j){
    			
    			Button k = new Button("");
    			k.setPrefWidth(35);
    			k.setPrefHeight(35);
    			mines.add(k, i, j);
    			all.add(k);
    			
    			k.setOnMouseClicked(e -> {
    				if (e.getButton() == MouseButton.SECONDARY) { //if right click
    					if(k.getOpacity() <1.0) { //so if it is flagged
    						k.setOpacity(1.0);
    						k.setGraphic(null); //I unflag it
    					}
    					else { // I flag it
    						k.setOpacity(0.4);
    						k.setGraphic(new ImageView(flag));
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
    							ShowAll(all, bombs, x, y);
    						} catch (Exception e1) {
    							e1.printStackTrace();
    						}
    						//end of game
    					}
    					
    							
    					if(is_bomb == false) {
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

    	
    	VBox.getChildren().addAll(topbox, mines);
		Scene scene = new Scene(VBox, width*35, height*35+40);
		Stage stage = new Stage();
		stage.setTitle("Minesweeper");
		stage.setScene(scene); 
		stage.show();

    }

    public Pane getRootPane() {
        return rootPane ;
    }
    public void GenerateBombs(int bombs, int max, List<Integer> list) {
		Random random_x = new Random();
	    random_x.ints(bombs, 0, max).forEach(i -> {
	    	list.add(i);
	    	});
	};
	
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
			if(check_x + 1 == x.get(o) && check_y == y.get(o)) {
				counter++;
			}
			if(check_x + 1 == x.get(o) && check_y - 1 == y.get(o)) {
				counter++;
			}
			if(check_x - 1 == x.get(o) && check_y + 1 == y.get(o)) {
				counter++;
			}
			if(check_x - 1 == x.get(o) && check_y == y.get(o)) {
				counter++;
			}
			if(check_x - 1 == x.get(o) && check_y - 1 == y.get(o)) {
				counter++;
			}
			if(check_x == x.get(o) && check_y + 1 == y.get(o)) {
				counter++;
			}
			if(check_x == x.get(o) && check_y - 1 == y.get(o)) {
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
	
	public void ShowFields(int check_x, int check_y, List<Button> all, int bombs, List<Integer> x, List<Integer> y, int width, int height) {
		List<Integer> openX = new ArrayList<Integer>();
		List<Integer> openY = new ArrayList<Integer>();
		
		System.out.println("A " + check_x + " " + check_y);
		
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
						System.out.println(" ");
						System.out.println("D " + openX.get(0) + " " + openY.get(0));
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
						
						System.out.print("C " + openX.get(openX.size()-1) + " " + openY.get(openY.size()-1));
						
					}
				}
			}
	}
}
}