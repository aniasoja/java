package application;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene; 
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.geometry.Pos;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Minesweeper extends Application{
	public static void main(String[] args){
		launch(args);
		}
	@Override public void start(Stage primaryStage) throws Exception {
		VBox VBox = new VBox();
		
		HBox topbox = new HBox();
		Text left = new Text ("score");
		Button smile = new Button("New Game");
		smile.setOnMouseClicked(e->{
		//	how do I start a new game?
		});
		
		Text right = new Text("score");
		topbox.getChildren().addAll(left, smile, right);
		left.setStyle("fx-spacing: 20; -fx-text-alignment: left");
		smile.setStyle("fx-spacing: 20; -fx-alignment: center");
		right.setStyle("fx-spacing: 20; -fx-text-alignment: right");
		
		Image bomb = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\bomb.jpg"));
		Image flag = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\flag.png"));
		
		int bombs = 12;
		
	    List<Integer> x = new ArrayList<Integer>();
	    Random random_x = new Random();
	    random_x.ints(bombs, 0, 15).forEach(i -> {
	    	x.add(i);
	    	});
	    
	    List<Integer> y = new ArrayList<Integer>();
	    Random random_y = new Random();
	    random_y.ints(bombs, 0, 7).forEach(i -> {
	    	y.add(i);
	    	});
	    
		GridPane mines = new GridPane();
		mines.setVgap(1);
		mines.setHgap(1);
		int i = 0;
		int j = 0;
		for (i=0;i<15; ++i) {
			for (j=0; j<8;++j){
				Button k = new Button("");
				k.setPrefWidth(100);
				k.setPrefHeight(100);
				mines.add(k, i, j);
				k.setOnMouseClicked(e -> {
					if (e.getButton() == MouseButton.SECONDARY) {
						k.setDisable(true);
						k.setGraphic(new ImageView(flag));
						}
					else {
						Random random = new Random();
						int rand = random.nextInt(4);
						
						double checkx = k.getLayoutX()/30;
						int check_x = (int) checkx;
						double checky = k.getLayoutY()/30;
						int check_y = (int) checky;

						k.setDisable(true);
						boolean is_bomb = false;
						for(int o = 0; o<12; o++) {
							if(check_x==x.get(o)  && check_y ==y.get(o)) {
								k.setGraphic(new ImageView(bomb));
								is_bomb = true;
								//end game
							}
						}
						if(is_bomb == false) {
							int counter = 0;
							for(int o = 0; o<12; o++) {
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
							if(counter == 0) {
								k.setText("");
							}
							else {
								String text = Integer.toString(counter);
								k.setText(text);
							}
						}
					}
				});
			}
		}
	
		
		VBox.getChildren().addAll(topbox, mines);
		
		Scene scene = new Scene(VBox, 500, 300);
		primaryStage.setTitle("Minesweeper");
		primaryStage.setScene(scene); 
		primaryStage.show();
	}
}
