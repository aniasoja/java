package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene; 
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class Minesweeper extends Application{
	public static void main(String[] args){
		launch(args);
		}
	@Override public void start(Stage primaryStage) throws Exception {
		//TO DO
		//levels
		//harders is variant of minesweeper similar to rysunki z podpisanych numerków
		
		
		Button startButton = new Button("Start Game");
		startButton.setOnAction(e -> {
		    Play game = new Play();
		    primaryStage.getScene().setRoot(game.getRootPane());
		});
		
		
	}
}
