package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;


public class Song extends Object{
    

	private final SimpleStringProperty title  = new SimpleStringProperty("");
    private final SimpleStringProperty artist = new SimpleStringProperty("");
	private final SimpleStringProperty album = new SimpleStringProperty("");
    private final SimpleStringProperty genre = new SimpleStringProperty("");
    private final SimpleStringProperty year = new SimpleStringProperty("");

    private final SimpleIntegerProperty ID = new SimpleIntegerProperty();
    private Image image;
    private final Media mediaFile;
    static int songsIDCounter = 0;
    
    
    
    public Media getMedia()
    {
        return this.mediaFile;
    }
    
    public Song(Media media, String fTitle, String fAlbum, String fArtist, String fGenre, String fYear,Image image) {
        this.setTitle(fTitle);
        this.setAlbum(fAlbum);
        this.setArtist(fArtist);
        this.setGenre(fGenre);
        this.setYear(fYear);
        this.mediaFile = media;
        this.ID.set(songsIDCounter);
        this.image = image;
    }
    
    
    public Song(Media media, String fTitle,String fArtist, String fAlbum, String fGenre,  String fYear) {
        this.setTitle(fTitle);
        this.setArtist(fArtist);
        this.setAlbum(fAlbum);
        this.setGenre(fGenre);
        this.setYear(fYear);
        this.mediaFile = media;
        this.ID.set(songsIDCounter);
       // songsIDCounter++;
    }
    public static void resetID()
    {
        songsIDCounter = 0;
    }
    
    public int getID() {
        return ID.get();
    }
    
   
    public void setTitle(String fTitle) {
        title.set(fTitle);
    }
    
    public String getTitle()
    {
        return this.title.get();
    }
    
    public String getAlbum() {
        return album.get();
    }
    
    public void setAlbum(String fAlbum) {
        album.set(fAlbum);
    }
    
    public String getArtist()
    {
        return artist.get();
    }
    public void setArtist(String fArtist)
    {
        this.artist.set(fArtist);
    }
    
    public void setYear(String fYear)
    {
        this.year.set(fYear);
    }
    
    public String getYear()
    {
        return this.year.get();
    }
    
    public Image getImage() 
    {
		return image;
	}
    
	public void setImage(Image image) 
	{
		this.image = image;
	}

	public void setGenre(String fGenre) {
        genre.set(fGenre);
    }
    
    public String getGenre()
    {
        return this.genre.get();
    }
	
}



