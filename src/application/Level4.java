package application;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator; 
import java.util.List;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class Level4 {
	private final BorderPane rootPane ; // or any other kind of pane, or  Group...

    public Level4() throws Exception {

        rootPane = new BorderPane();
        
        VBox VBox = new VBox();
    	HBox topbox = new HBox();
        
        HBox countX = new HBox();
        VBox countY = new VBox();
        
        GridPane mines = new GridPane();
    	mines.setVgap(1);
    	mines.setHgap(1);
        
        Text spacing = new Text();
        spacing.setWrappingWidth(150);
        countX.getChildren().add(0, spacing);
        
        HBox countandmines = new HBox();
        
        int width = 30;
        int height = 20;
        int bombs = 450;
        
        List<Integer> xList = new ArrayList<Integer>();
        List<Integer> yList = new ArrayList<Integer>();
        ArrayList<HBox> fields = new ArrayList<HBox>();
        
        Button smile = new Button("New Game");
    	smile.setOnMouseClicked(e->{
    		for(HBox field : fields) {
    			field.getChildren().clear();
    		}
    		Game(bombs, width, height, countX, mines, xList, yList, fields);
    	});
    	topbox.setAlignment(Pos.CENTER);
    	topbox.getChildren().addAll(smile);
    	
    	//smile.setStyle("fx-spacing: 20; -fx-alignment: center");
        
    	
    	countY.prefHeight(mines.getPrefHeight());
    	countandmines.getChildren().addAll(countY, mines);

    	Game(bombs, width, height, countX, mines, xList, yList, fields);
    	
    	VBox.getChildren().addAll(topbox, countX, countandmines);
		Scene scene = new Scene(VBox, width*36+160, height*36+160);
		Stage stage = new Stage();
		stage.setTitle("Minesweeper");
		stage.setScene(scene); 
		stage.show();

    }

    public Pane getRootPane() {
        return rootPane ;
    }
    
    public void Game(int bombs, int width, int height, HBox countX, GridPane mines, List<Integer> xList, List<Integer> yList, ArrayList<HBox> fields) {
        
        GenerateBombs(bombs, 1, width+1, xList);
        GenerateBombs(bombs, 0, height, yList);
        
        final List<Integer> copyX = new ArrayList<Integer>(xList);
        final List<Integer> copyY = new ArrayList<Integer>(yList);
 
        ArrayList<List<Integer>> allNumX = new ArrayList<List<Integer>>();
        ArrayList<List<Integer>> allNumY = new ArrayList<List<Integer>>();
        
        count(width+1, xList, yList, allNumX, 1);
        count(height, yList, xList, allNumY, 0);
        
        String xy = "x";	
        int biggest = getBiggest(allNumX);
        for(int j = 0; j<allNumX.size(); j++) { //
        	VBox field = new VBox();
        	numbers(xy, allNumX.get(j), biggest, field);
            countX.getChildren().add(field);
        }
        ArrayList<Button> all = new ArrayList<Button>();
    	
    	List<Integer> clickedXList = new ArrayList<Integer>();
        List<Integer> clickedYList = new ArrayList<Integer>();
    	

    	for (int i=1;i<=width; ++i) {
    		for (int j=1; j<=height;++j){
    			
    			Button k = new Button("");
    			k.setPrefWidth(35);
    			k.setPrefHeight(35);
    			mines.add(k, i, j);
    			all.add(k);
    			
    			k.setOnMouseClicked(e -> {
    				if (e.getButton() == MouseButton.SECONDARY || e.getClickCount()==2) {
    					k.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    					clickedXList.add((int)k.getLayoutX()/35-3);
    					clickedYList.add((int)k.getLayoutY()/35);
    					if(clickedXList.size()==xList.size()) {
        					clickedXList.sort(Comparator.naturalOrder());
        					clickedYList.sort(Comparator.naturalOrder());
        					xList.sort(Comparator.naturalOrder());
        					yList.sort(Comparator.naturalOrder());
        					boolean mistake = false;
        					for(int m = 0; m<xList.size(); m++) {
        						if(xList.get(m)!=clickedXList.get(m)) {
        							mistake = true;
        						}
        						else if(yList.get(m) != clickedYList.get(m)) {
        							mistake = true;
        						}
        					}
        					if(mistake == false) {
        						System.out.print("Winner!!");
        						//to work on
        						System.out.println(copyX);
								System.out.println(copyY);
        						for(int m = 0; m<copyX.size(); m++) {
        							for(Button l: all) {
            							//System.out.println(copyX.get(m) + " " + ((int)l.getLayoutX()/35-3));
            							//System.out.println(yList.get(m) + " " + (int)l.getLayoutY()/35);
            							if(copyX.get(m)==(int)(l.getLayoutX()/35)-3 && copyY.get(m)==(int)(l.getLayoutY()/35)) {
            								l.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            							}
        							}
        						}
        						
        					}
        					else {
        						System.out.println("Loser!");
        					}
    					}
    					
    				}
    				else {
    					k.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
   
    				}
    				
    				//CheckIfAllClicked(all);
    				/*
    				int check_x = (int) k.getLayoutX()/35;
					int check_y = (int) k.getLayoutY()/35;
					
					System.out.println(check_x + " " + check_y);
					
    				
    				boolean is_bomb = CheckIfHasBomb(check_x, check_y, xList, yList);
    				
    				if(is_bomb == true) {
    					k.setDisable(true);
    					k.setGraphic(new ImageView(bomb));
    				}
    				else {
    					k.setDisable(true);
    				}
    		*/
    				
    			});
    		}
    	}
    
    	
    	
    	xy = "y";
    	biggest = getBiggest(allNumY);
        for(int j = 0; j<allNumY.size(); j++) { //
        	HBox field = new HBox();
        	numbers(xy, allNumY.get(j), biggest, field);
        	//field.alignmentProperty(Pos.CENTER_RIGHT);
        	field.setMinWidth(150);
        	field.setAlignment(Pos.CENTER_RIGHT);
        	mines.add(field, 0, j+1);
        	fields.add(field);
        }
    };
    
    public void GenerateBombs(int bombs, int min, int max, List<Integer> list) {
		Random random_x = new Random();
	    random_x.ints(bombs, min, max).forEach(i -> {
	    	list.add(i);
	    	});
	};
	public void SortX(List<Integer> list1, List<Integer> list2) {
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
		
	public int SortY(List<Integer> list1, List<Integer> list2) {
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
	
	public boolean CheckIfHasBomb(int check_x, int check_y, List<Integer> x, List<Integer> y) {
		boolean is_bomb = false;
		for(int o = 0; o<x.size(); o++) {
			if(check_x+1==x.get(o)  && check_y ==y.get(o)) {
				is_bomb = true;
			}
		}
		return is_bomb;
	};

	public void ShowAll(List<Button> all, int bombs, List<Integer> x, List<Integer>y) throws Exception {
		Image bomb = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\bomb.jpg"));
		for(Button k:all) {
			int check_x = (int)((k.getLayoutX()-160)/36);
			int check_y = (int)k.getLayoutY()/35;
			boolean is_bomb = CheckIfHasBomb(check_x, check_y, x, y);
			if(is_bomb == true) {
				k.setDisable(true);
				k.setGraphic(new ImageView(bomb));
			}
			else {
				k.setDisable(true);
			}
		}
	}
	
	public void count(int width, List<Integer> List, List<Integer> otherList, ArrayList<List<Integer>> allNum, int ini) {
		for(int i = ini; i<width; i++) {
        	List<Integer> sort = new ArrayList<Integer>();
        	for(int j = 0; j < List.size(); j++) {
        		if(List.get(j)==i) {
        			sort.add(otherList.get(j));
        		}
        	}
        	Collections.sort(sort);
        	List<Integer> repetitions = new ArrayList<Integer>();
        	for(int l = 0; l<sort.size()-1; l++) {
        		if(sort.get(l)==sort.get(l+1)) {
        			repetitions.add(sort.get(l));
        			sort.remove(l);
        			l--;
        		}
        	}
        	for(int n:repetitions) {
        		int counter = 0;
        		for(int m = 0; m< List.size(); m++) {
        			if(List.get(m) == i && otherList.get(m) == n) {
        				if(counter>0) {
        					List.remove(m);
            				otherList.remove(m);
        				} else {
        					counter++;
        				}	
        			}
        		}
        	}
        	
        	int counter2 = 1;
        	List<Integer> all = new ArrayList<Integer>();
        	for(int k =0; k<sort.size()-1; k++) {
        		if(sort.get(k+1)-sort.get(k)==1) {
        			counter2++;
        		} else {
       				all.add(counter2);
       				counter2 = 1;
       			}
       		}
        all.add(counter2);
       	allNum.add(all);
       }
	}
	
	public int getBiggest(ArrayList<List<Integer>> allNum) {
		int biggest = allNum.get(0).size();
        for(int i = 1; i<allNum.size()-1; i++) {
        	if(allNum.get(i).size()>biggest) {
        		biggest = allNum.get(i).size();
        	}
        }
        return biggest;
	}
	
	public void numbers(String xy, List<Integer> a, int biggest, Pane field) {
    	for(int k = 0; k<a.size(); k++) {
    		Text text = new Text(a.get(k).toString());
    		text.setWrappingWidth(15);
    		if(xy == "x") {
            	text.setTextAlignment(TextAlignment.CENTER);
            	text.setWrappingWidth(36);
            } else {
            	if(a.get(k)>9) {
            		text.setWrappingWidth(20);
            	}
            }
    		text.setOnMouseClicked(e -> {
				text.setOpacity(0.5);
				});
			field.getChildren().add(text);
    	}
    	while(field.getChildren().size() < biggest && xy == "x") {
			Text text = new Text("  ");
			field.getChildren().add(0, text);
       	}
	}
}
