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
import javafx.scene.input.*;
import javafx.geometry.Pos;
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
		Text right = new Text("score");
		topbox.getChildren().addAll(left, smile, right);
		left.setStyle("fx-spacing: 20; -fx-text-alignment: left");
		smile.setStyle("fx-spacing: 20; -fx-alignment: center");
		right.setStyle("fx-spacing: 20; -fx-text-alignment: right");
		
		Image bomb = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\bomb.jpg"));
		Image flag = new Image(new FileInputStream("C:\\Users\\Home\\Desktop\\flag.png"));
		
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
				k.setOnMouseClicked(e -> {
					if (e.button == MouseButton.SECONDARY) {
						k.setDisable(true);
						k.setGraphic(new ImageView(flag));
						//this is an error
					}
					else {
						Random random = new Random();
						int rand = random.nextInt(4);

						k.setDisable(true);
						
						if(rand == 0) {
							k.setGraphic(new ImageView(bomb));
							}
						else {
							k.setText("1");
						}
					}
					
				});
				mines.add(k, i, j);
			}
		}
	
		
		VBox.getChildren().addAll(topbox, mines);
		
		Scene scene = new Scene(VBox, 500, 300);
		primaryStage.setTitle("Minesweeper");
		primaryStage.setScene(scene); 
		primaryStage.show();
	}
}
