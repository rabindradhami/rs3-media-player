package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class NowPlayingController implements Initializable {

	@FXML
	public ImageView albumArtIV;
	@FXML
	public Label titleLbl;
	@FXML
	public Label artistLbl;
	@FXML
	public Label albumLbl;
	@FXML
	public Label genreLbl;
	@FXML
	public Label yearLbl;
	@FXML
	private TableColumn<Song, String> titleColumn;
	@FXML
	private TableView<Song> nowPlayingSongsTable;
	private int selectedSongIndex;

	MainController mainController = new MainController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			albumArtIV.setImage(Playlist.image);
			titleLbl.setText(Playlist.title);
			artistLbl.setText(Playlist.artist);
			albumLbl.setText(Playlist.album);
			genreLbl.setText(Playlist.genre);
			yearLbl.setText(Playlist.year);
			showPlaylist();

			nowPlayingSongsTable.getSelectionModel().selectedItemProperty()
					.addListener((obs, oldSelection, newSelection) -> {
						selectedSongIndex = nowPlayingSongsTable.getSelectionModel().getSelectedIndex();
						if (MainController.isPlaying == 0) {
							MainController.i = selectedSongIndex;
							mainController.playForNow();
							setMetaData();
							MainController.isPlaying = 1;
							MainController.mp.setOnEndOfMedia(new Runnable() {

								@Override
								public void run() {
									mainController.playSongFromList();
								}
							});
						} else if (MainController.isPlaying == 1) {
							System.out.println(selectedSongIndex);
							mainController.stopCurrentSong();
							MainController.i = selectedSongIndex;
							mainController.playForNow();
							setMetaData();
							MainController.isPlaying = 1;
							MainController.mp.setOnEndOfMedia(new Runnable() {

								@Override
								public void run() {
									mainController.playSongFromList();
								}
							});
						} else {
							mainController.stopCurrentSong();
							MainController.i = selectedSongIndex;
							mainController.playForNow();
							setMetaData();
							MainController.isPlaying = 1;
							MainController.mp.setOnEndOfMedia(new Runnable() {

								@Override
								public void run() {
									mainController.playSongFromList();
								}
							});
						}
					});
		} catch (Exception e) {
			e.getCause();
		}

	}

	public void showPlaylist() {
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
		nowPlayingSongsTable.setItems(Playlist.data);
	}

	public void setMetaData() {
		try {
			titleLbl.setText(MainController.title);
			artistLbl.setText(MainController.artist);
			albumLbl.setText(MainController.album);
			genreLbl.setText(MainController.genre);
			yearLbl.setText(MainController.year);
			albumArtIV.setImage(MainController.image);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
