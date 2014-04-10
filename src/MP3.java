import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

public class MP3 {
	private String path;
	private String name;
	private String artist;

	public MP3(String path, String name) {
		super();
		this.path = path;
		this.name = name;
		this.artist = "unknown";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void getData() {
		MP3File tagData;
		try {
			tagData = new MP3File(path);
			if (tagData != null) {
				if (tagData.getID3v1Tag() != null) {
					if (!tagData.getID3v1Tag().getArtist().equals("")) {
						artist = tagData.getID3v1Tag().getArtist();
					}
					if (!tagData.getID3v1Tag().getSongTitle().equals("")) {
						name = tagData.getID3v1Tag().getSongTitle();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	} 
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String[] getVector() {
		return new String[]{getName(),artist};
	}
}
