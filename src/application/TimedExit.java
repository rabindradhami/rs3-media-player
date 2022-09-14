package application;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TimedExit {
	
	@FXML
	public JFXTextField timerField;
	public Stage currentStage = MainController.timerStage;
	public static Stage timeStage;
	public TextField inputTimeLbl;
	public String inputTime;
	public int timeInSec;
	Timer timer = new Timer();

	TimerTask exitApp = new TimerTask() {
		@Override
		public void run() {
			System.exit(0);
		}
	};

	public void ExitAfterTime(ActionEvent event) {

		try {
			currentStage.close();
			inputTime = inputTimeLbl.getText();
			int hour = Integer.parseInt(inputTime.substring(0, Math.min(inputTime.length(), 2)));
			int minute = Integer.parseInt(inputTime.substring(3, Math.min(inputTime.length(), 5)));
			int second = Integer.parseInt(inputTime.substring(6, Math.min(inputTime.length(), 8)));
			timeInSec = hour*60*60+minute*60+second;
			timer.schedule(exitApp, new Date(System.currentTimeMillis() + timeInSec * 1000));
			
		} catch (IndexOutOfBoundsException  | NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Time Format Error Dialog");
			alert.setContentText("Please enter complete time in HH:MM:SS format");
			alert.showAndWait();
		}
	}
}