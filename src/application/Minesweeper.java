package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Minesweeper extends Application{
	public static void main(String[] args){
		launch(args);
		}
	@Override public void start(Stage primaryStage) throws Exception {
		//TO DO
		//levels
		//harders is variant of minesweeper similar to rysunki z podpisanych numerków
		VBox VBox = new VBox();
		
		Button button1 = new Button("Level 1");
		button1.setOnAction(e -> {
			Play game = null;
			try {
				game = new Play(15, 8, 12);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    primaryStage.getScene().setRoot(game.getRootPane());
		});
		Button button2 = new Button("Level 2");
		button2.setOnAction(e -> {
			Play game = null;
			try {
				game = new Play(20, 12, 24);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    primaryStage.getScene().setRoot(game.getRootPane());
		});
		Button button3 = new Button("Level 3");
		button3.setOnAction(e -> {
			Play game = null;
			try {
				game = new Play(25, 16, 48);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		    primaryStage.getScene().setRoot(game.getRootPane());
		});
		Button button4 = new Button("Not working yet");
		
		VBox.getChildren().addAll(button1, button2, button3, button4);
		Scene scene = new Scene(VBox, 300, 300);
		Stage stage = new Stage();
		stage.setTitle("Minesweeper");
		stage.setScene(scene); 
		stage.show();
	}
}
