package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {

	public static MediaPlayer mp;
	Playlist playlist = new Playlist();
	public static Media me;
	public static List<File> fileList = new ArrayList<>();
	public static Stage timerStage = new Stage();
	public static Stage stage2 = new Stage();
	public static Stage stage1=new Stage();
	private static String path;
	public static int i = 0;
	public int nowPlayingSong;
	Object[] songsArray = fileList.toArray();
	boolean[] canSeek = { true };
	public static int isPlaying = 0;
	public static int isShuffleOn = 0;
	public static int isRepeatOn = 0;
	private double volume = 0.8;

	Image playImage = new Image("icons/play.png");
	Image pauseImage = new Image("icons/Pause.png");
	Image unmuteImage = new Image("icons/volume.png");
	Image muteImage = new Image("icons/Mute.png");

	@FXML
	public Label currentTimeLbl;
	@FXML
	public Label totalTimeLbl;
	@FXML
	public BorderPane borderPane;
	@FXML
	public ScrollPane tablePane;
	@FXML
	public ImageView pp;
	@FXML
	public ImageView un;
	@FXML
	public Label artistBar;
	@FXML
	public Label titleBar;
	@FXML
	public ImageView albumArtIV;
	@FXML
	public ImageView albumArtIVBar;
	@FXML
	public Slider seekslider;
	@FXML
	public Slider volumeslider;
	@FXML
	public TableView<Song> songsTable;
	@FXML
	public TableColumn<Song, String> titleColumn;
	@FXML
	public TableColumn<Song, String> artistColumn;
	@FXML
	public TableColumn<Song, String> albumColumn;
	@FXML
	public TableColumn<Song, String> genreColumn;
	@FXML
	public TableColumn<Song, String> yearColumn;
	@FXML
	private TableRow<Song> selectedRow;
	@FXML
	public MenuItem open;
	@FXML
	public MenuItem quit;
	@FXML
	public MenuItem fwd;
	@FXML
	public MenuItem bwd;
	@FXML
	public Button repeatbtn;
	@FXML
	public Button shufflebtn;
	@FXML
	public Button videobtn;
	@FXML
	public Button stopbtn;
	@FXML
	public Button previousbtn;
	@FXML
	public Button playpausebtn;
	@FXML
	public Button nextbtn;
	@FXML
	public Button mutebtn;
	@FXML
	public Button timerbtn;

	public static Image image;
	public static String title;
	public static String artist;
	public static String album;
	public static String genre = "Unknown";
	public static String year;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {

			// to display toolTip(additional info) on button
			previousbtn.setTooltip(new Tooltip("Previous"));
			nextbtn.setTooltip(new Tooltip("Next"));
			timerbtn.setTooltip(new Tooltip("Sleep Timer"));
			volumeslider.setTooltip(new Tooltip("Volume"));
			seekslider.setTooltip(new Tooltip("Seekbar"));
			stopbtn.setTooltip(new Tooltip("Stop"));
			playpausebtn.setTooltip(new Tooltip("Play"));
			videobtn.setTooltip(new Tooltip("Video"));
			shufflebtn.setTooltip(new Tooltip("Shuffle"));
			repeatbtn.setTooltip(new Tooltip("Repeat"));

			// to set table invisible at start
			songsTable.setVisible(true);
			
			//keyboard shortcut
			// Keyboard-Open
			open.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					OpenFile();
				}
			});
			open.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

			// Keyboard-Quit
			quit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					quit(null);
				}
			});
			quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

			// Keyboard-Forward
			fwd.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					forward(null);
				}
			});
			fwd.setAccelerator(new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));

			// Keyboard-Backward
			bwd.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					backward(null);
				}
			});
			bwd.setAccelerator(new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// to play songs selected from table
		songsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				try {
					if (isPlaying == 0) {
						i = songsTable.getSelectionModel().getSelectedIndex();
						play();
						isPlaying = 1;
						mp.setOnEndOfMedia(new Runnable() {

							@Override
							public void run() {
								playSongFromList();
							}
						});
					}
					if (isPlaying == 1) {
						stopCurrentSong();
						i = songsTable.getSelectionModel().getSelectedIndex();
						play();
						mp.setOnEndOfMedia(new Runnable() {

							@Override
							public void run() {
								playSongFromList();
							}
						});
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void OpenFile() {
		importMusic();
		showPlaylist();

	}

	public void importMusic() {

		try {
			FileChooser fc = new FileChooser();
			fileList = fc.showOpenMultipleDialog(null);
			if (fileList == null) {
				return;
			}
			playlist.add(fileList);
		} catch ( UnsupportedOperationException e) {
			e.printStackTrace();
		}

	}

	//to save loaded songs
	public void saveList(ActionEvent event) {
		File file = new File("New File");
		try {
			PrintWriter pw = new PrintWriter(file);
			for (i = 0; i < fileList.size()-1; i++) {
				pw.write("swss");
				pw.write(fileList.get(i).getAbsolutePath().toString());

			}
			pw.close();
		} catch (FileNotFoundException  e) {
			e.printStackTrace();
		}

	}

	public void playSongFromList() {

		path = fileList.get(i).getAbsolutePath();
		playSelectedSong(path);

	}

	public void playSelectedSong(String path) {
		try {
			me = new Media(new File(path).toURI().toString());
			mp = new MediaPlayer(me);
			mp.setVolume(volume);
			Status status = mp.getStatus();
			if (status == Status.PAUSED) {
				mp.play();
				pp.setImage(pauseImage);
			}
			if (status == Status.PLAYING) {
				mp.pause();
				pp.setImage(playImage);
			}
			if (status == Status.UNKNOWN) {
				mp.play();
				pp.setImage(pauseImage);
			}

			mp.setOnEndOfMedia(new Runnable() {

				@Override
				public void run() {
					try {
						i++;
						if (i > fileList.size()) {
						} else {
							mp.stop();
							playSelectedSong(fileList.get(i).getAbsolutePath());
							;

						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});
			seekslider();
			volumeSlider();

		} catch (Exception e) {
			e.getCause();
		}
	}

	public void volumeSlider() {
		try {
			// VolumeSlider
			volumeslider.setValue(mp.getVolume() * 100);
			volumeslider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable arg0) {
					// TODO Auto-generated method stub
					volume = (volumeslider.getValue() / 100);
					mp.setVolume(volume);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void seekslider() {
		try {
			// SeekBar
			mp.currentTimeProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					updatevalues();
				}

				private void updatevalues() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							seekslider
									.setValue(mp.getCurrentTime().toMillis() / mp.getTotalDuration().toMillis() * 100);
							try {
								double currentTime = mp.getCurrentTime().toMillis();
								double currentTimeInSec = currentTime/600;
								int currentTimeMin = (int) currentTimeInSec/60;
								int currentTimeSec = (int) currentTimeInSec%60;
								currentTimeLbl.setText(currentTimeMin+ ":" + currentTimeSec);
								
								double totalTime = mp.getTotalDuration().toMillis()/2;
								double totalTimeInSec = totalTime/600;
								int totalTimeMin = (int) totalTimeInSec/60;
								int totalTimeSec = (int) totalTimeInSec%60;
								totalTimeLbl.setText(totalTimeMin+ ":" + totalTimeSec);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
			seekslider.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					if (seekslider.isPressed()) {
						mp.seek(mp.getMedia().getDuration().multiply(seekslider.getValue() / 100));
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void play() {
		
		getMetaData();
		playSelectedSong(fileList.get(i).getAbsolutePath());
		titleBar.setText(title);
		artistBar.setText(artist);
		albumArtIVBar.setImage(image);

	}

	public void playForNow() {
		getMetaData();
		playSelectedSong(fileList.get(i).getAbsolutePath());

	}

	public void stopCurrentSong() {
		mp.stop();
	}

	// Play-Pause
	public void playpause(ActionEvent event) {
		if (fileList != null) {
			Status status = mp.getStatus();
			if (status == Status.PAUSED) {
				mp.play();
				pp.setImage(pauseImage);
				playpausebtn.setTooltip(new Tooltip("Pause"));
			}
			if (status == Status.PLAYING) {
				mp.pause();
				pp.setImage(playImage);
				playpausebtn.setTooltip(new Tooltip("Play"));
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Music Error");
			alert.setHeaderText("Empty Playlist");
			alert.setContentText("Please Import Songs");
			alert.showAndWait();
		}
		
	}

	// Forward song
	public void forward(ActionEvent event) {
		if (mp.getRate() == 1) {
			mp.setRate(1.5);
		} else if (mp.getRate() == 1.5) {
			mp.setRate(2);
		} else {
			mp.setRate(1);
		}

	}

	// Backward Song
	public void backward(ActionEvent event) {
		if (mp.getRate() == 1) {
			mp.setRate(0.75);
		} else if (mp.getRate() == 0.75) {
			mp.setRate(0.5);
		} else {
			mp.setRate(1);
		}
	}

	// Unmute-mute audio
	public void unmute() {
		if (mp.isMute()) {
			mp.setMute(false);
			un.setImage(unmuteImage);
			mutebtn.setTooltip(new Tooltip("Mute"));
		} else {
			mp.setMute(true);
			un.setImage(muteImage);
			mutebtn.setTooltip(new Tooltip("Unmute"));
		}
	}

	// to play next song
	public void next(ActionEvent event) {
		
		if (isRepeatOn == 1) {
			mp.seek(Duration.ZERO);
		} else {
			if (isShuffleOn == 1) {
				try {
					shuffle();
					mp.stop();
					play();
				} catch (Exception ne) {
					ne.printStackTrace();
				}
			} else if (isShuffleOn == 0) {
				try {
					if (i < fileList.size() - 1) {
						i++;
						mp.stop();
						play();
					} else {
						i = 0;
						mp.stop();
						play();
					}

				} catch (Exception ne) {
					ne.printStackTrace();
				}
			}

			else {
				i++;
				mp.stop();
				play();
			}
		}

	}

	// to play previous song
	public void previous(ActionEvent event) {
		try {
			fileList.get(i).delete();
			if (isRepeatOn == 1) {
				mp.seek(Duration.ZERO);
			} else {
				if (isShuffleOn == 0) {
					i--;
					if (i >= 0) {
						mp.stop();
						play();
					} else {
						i = fileList.size() - 1;
						mp.stop();
						play();
					}
				} else if (isShuffleOn == 1) {
					shuffle();
					mp.stop();
					play();
				} else {
					i--;
					if (i >= 0) {
						mp.stop();
						play();
					} else {
						i = fileList.size();
						mp.stop();
						play();
					}

				}
			}

		} catch (Exception ne) {
			ne.printStackTrace();
		}

	}

	// to set Repeat
	public void setRepeat(ActionEvent event) {
		if (isRepeatOn == 1) {
			isRepeatOn = 0;
			repeatbtn.setTooltip(new Tooltip("Repeat on"));
			mp.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					next(event);
				}
			});
		} else if (isRepeatOn == 0) {
			isRepeatOn = 1;
			repeatbtn.setTooltip(new Tooltip("Repeat off"));
			mp.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					mp.seek(Duration.ZERO);
				}
			});
			mp.play();
		} else {
			isRepeatOn = 0;
			repeatbtn.setTooltip(new Tooltip("Repeat on"));
		}

	}

	//
	public void doRepeat() {
		if (isShuffleOn == 0) {

		} else {

		}
	}

	// to shuffle playlist
	public void shuffle(ActionEvent event) {
		try {
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(fileList.size() - 1);
			i = randomInt;
			if (isShuffleOn == 1) {
				isShuffleOn = 0;
				shufflebtn.setTooltip(new Tooltip("Shuffle On"));
			} else if (isShuffleOn == 0) {
				isShuffleOn = 1;
				shufflebtn.setTooltip(new Tooltip("Shuffle Off"));
			} else {
				isShuffleOn = 0;
				shufflebtn.setTooltip(new Tooltip("Shuffle On"));
			}
			// }
			mp.setOnEndOfMedia(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < fileList.size() - 1; j++) {
						int randomInt = randomGenerator.nextInt(fileList.size() - 1);
						i = randomInt;
						playSelectedSong(fileList.get(i).getAbsolutePath());
					}
				}
			});
		} catch (Exception e) {
			e.getCause();
		}
	}

	// to generate random number
	public void shuffle() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(fileList.size() - 1);
		i = randomInt;
	}

	// Stop
	public void stop(ActionEvent event) {
		mp.stop();
	}

	// Quit
	public void quit(ActionEvent event) {
		System.exit(0);
	}

	public void showPlaylist() {
		try {
			songsTable.setVisible(true);
			titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
			artistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Artist"));
			albumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Album"));
			genreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
			yearColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Year"));
			songsTable.setItems(Playlist.data);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// to loadmetadata of song
	public void getMetaData() {

		try {
			title = me.getMetadata().get("title").toString();
		} catch (NullPointerException e) {
			title = "Unknown";
		}

		try {
			artist = me.getMetadata().get("artist").toString();
		} catch (NullPointerException e) {
			artist = "Unknown";
		}

		try {
			album = me.getMetadata().get("album").toString();
		} catch (NullPointerException e) {
			album = "Unknown";
		}

		try {
			genre = me.getMetadata().get("genre").toString();
		} catch (NullPointerException e) {
			artist = "Unknown";
		}

		try {
			year = me.getMetadata().get("year").toString();
		} catch (NullPointerException e) {
			year = "Unknown";
		}
		try {
			Object object = me.getMetadata().get("image");
			image = (Image) object;
			if (object instanceof Image) {
				image = (Image) object;
			} else {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			try {
				image = new Image(Playlist.class.getResource("Next.png").toURI().toString());
			} catch (Exception e1) {
				e1.printStackTrace();
				image = null;
			}
		}

	}

	// to set timer
	public void setTimer(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("SetTimer.fxml"));
			Scene scene = new Scene(root);
			timerStage.setScene(scene);
			timerStage.setResizable(false);
			timerStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//loading video screen
	public void video(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("video.fxml"));
			Scene scene = new Scene(root);
			stage1.setTitle("RS3-MEDIA PLAYER");
			scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent doubleClicked) {
					if(doubleClicked.getClickCount()==2) {
						stage1.setFullScreen(true);
					}
				}
				});
			
			stage1.setScene(scene);
			stage1.setResizable(false);
			stage1.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//to display about page
	public void about(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage2.setScene(scene);
			stage2.setResizable(false);
			stage2.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	

	// to goto now playing screen
	public void goToNowPlaying(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NowPlaying.fxml"));
			AnchorPane anchorPane = new AnchorPane();
			anchorPane = fxmlLoader.load();
			borderPane.setCenter(anchorPane);
			borderPane.setRight(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// to goto main screen
	public void returnScreen(ActionEvent event) {

		try {
			borderPane.setRight(tablePane);
			borderPane.setCenter(null);
			artistColumn.setVisible(true);
			albumColumn.setVisible(true);
			titleColumn.setVisible(true);
			yearColumn.setVisible(true);
			genreColumn.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// load songs
	public void loadSongs() {
		getMetaData();
		albumColumn.setVisible(false);
		artistColumn.setVisible(false);
		genreColumn.setVisible(false);
		yearColumn.setVisible(false);
		titleColumn.setVisible(true);
		titleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
		songsTable.setItems(Playlist.data);
	}
	
	// load albums
		public void loadAlbum() {
			getMetaData();
			albumColumn.setVisible(true);
			titleColumn.setVisible(false);
			artistColumn.setVisible(false);
			genreColumn.setVisible(false);
			yearColumn.setVisible(false);
			albumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Album"));
			songsTable.setItems(Playlist.data);
		}
		
		// load genre
		public void loadGenre() {
			getMetaData();
			genreColumn.setVisible(true);
			artistColumn.setVisible(false);
			albumColumn.setVisible(false);
			titleColumn.setVisible(false);
			yearColumn.setVisible(false);
			genreColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Genre"));
			songsTable.setItems(Playlist.data);
		}

	// load artists
	public void loadArtist(ActionEvent event) {
		getMetaData();
		artistColumn.setVisible(true);
		titleColumn.setVisible(false);
		albumColumn.setVisible(false);
		genreColumn.setVisible(false);
		yearColumn.setVisible(false);
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Artist"));
		songsTable.setItems(Playlist.data);
	}
	
	//for genre based playlists
	public void genrePlaylist(ActionEvent event) {
		ArrayList<String> genreList = new ArrayList<>();
		for(i=0;i<fileList.size()-1;i++){
			getMetaData();
		String uniqueGenre = genre;
		        if(!genreList.contains(genre))
		        	genreList.add(uniqueGenre);
		        System.out.println(genreList);
		    
		}
	}
}
