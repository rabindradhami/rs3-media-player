
package application;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Playlist implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final ArrayList<Media> mediaList = new ArrayList<Media>();
	public static final ObservableList<Song> data = FXCollections.observableArrayList();
	public static Image image;
	public static String title;
	public static String artist;
	public static String album;
	public static String genre = "Music";
	public static String year;

	public ArrayList<Media> getMediaList() {
		return Playlist.mediaList;
	}
	
	  public ObservableList<Song> getData()
	    {
	        return Playlist.data;
	    }

	public void add(List<File> fileList) {
		for (File fileList1 : fileList) {
			URI filepath = fileList1.toURI();
			add(filepath);
		}
	}

	public void add(URI filepath) {
		Media me = new Media(filepath.toString());
		MediaPlayer mp = new MediaPlayer(me);

		mp.setOnReady(new Runnable() {

			@Override
			public void run() {
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
					artist = "Unknown ";
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

				final Song song = new Song(me, title, album, artist, genre, year,image);
				data.add(song);
			}

		});
		mediaList.add(me);
	}

}
