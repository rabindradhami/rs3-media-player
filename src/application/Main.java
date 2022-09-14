package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
		
			Parent root = FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Main.stage = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("RS3-MEDIA");
			primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
