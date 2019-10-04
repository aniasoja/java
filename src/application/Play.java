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

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Play  {
	
	private final BorderPane rootPane ; // or any other kind of pane, or  Group...

    public Play() throws Exception {

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
    	
    	Image bomb = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\bomb.jpg"));
    	Image flag = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\flag.png"));
    	
    	int bombs = 12;
    	
    	//here I generate 12 (amount of bombs) X-axis numbers and 12 Y-axis numbers
    	// so I have 12 coordinates (X, Y) of bombs
    	
        List<Integer> x = new ArrayList<Integer>();
        List<Integer> y = new ArrayList<Integer>();
        GenerateBombs(bombs, 15, x);
        GenerateBombs(bombs, 8, y);
        
        
    	GridPane mines = new GridPane();
    	mines.setVgap(1);
    	mines.setHgap(1);

    	for (int i=0;i<15; ++i) {
    		for (int j=0; j<8;++j){
    			
    			Button k = new Button("");
    			k.setPrefWidth(33);
    			k.setPrefHeight(33);
    			mines.add(k, i, j);
    			
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
    					int check_x = (int) k.getLayoutX()/30;
    					int check_y = (int) k.getLayoutY()/30;
    					
    					boolean is_bomb = CheckIfHasBomb(bombs, check_x, check_y, x, y);
    					if(is_bomb == true) {
    						//end of game
    						k.setGraphic(new ImageView(bomb));
    					}
    					
    							
    					if(is_bomb == false) {
    						k.setDisable(true);
    						int counter = HowManyBombsAround(check_x, check_y, bombs, x, y);
    						if(counter > 0) {
    							String text = Integer.toString(counter);
    							k.setText(text);
    						}
    						else {
    							//odkrywanie ca³ych po³aci
    						}
    					}
    				}
    			});
    		}
    	}

    	
    	VBox.getChildren().addAll(topbox, mines);
		Scene scene = new Scene(VBox, 500, 300);
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
	}
	

}
