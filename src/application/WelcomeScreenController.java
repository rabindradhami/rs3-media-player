package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class WelcomeScreenController implements Initializable {

	@FXML
	private StackPane pane1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		new Splash().start();
	}

	class Splash extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(150);

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Stage Stage1 = Main.stage;
						Parent root = null;
						try {
							root = FXMLLoader.load(getClass().getResource("Main.fxml"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						Stage1.setScene(scene);
						Stage1.show();
						// pane1.getScene().getWindow().hide();
					}
				});

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}