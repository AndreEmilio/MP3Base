import java.util.Iterator;
import java.util.Vector;


public class MP3Filter {
	private String searchString;
	private Vector<BasicFilter> filterList;


	public MP3Filter(boolean title, boolean artist, boolean caseSensitive, String searchString) {
		super();
		filterList = new Vector<MP3Filter.BasicFilter>();
		if (caseSensitive) {
			    this.searchString = searchString;
			if (title) {
				filterList.add(new TitleFilter());
			}
			if (artist) {
				filterList.add(new ArtistFilter());
			}
		} else {
			this.searchString = searchString.toLowerCase();
			if (title) {
				filterList.add(new TitleNcsFilter());
			}
			if (artist) {
				filterList.add(new ArtistNcsFilter());
			}
		}
	}
	
	public boolean fit(MP3 mp3) {
		for (Iterator<BasicFilter> it = filterList.iterator();it.hasNext();) {
			if (it.next().fit(mp3, searchString)) return true;
		}
		return false;
	}	
	
	interface BasicFilter {
		public boolean fit(MP3 mp3,String search);
	}
	
	class ArtistFilter implements BasicFilter {
		@Override
		public boolean fit(MP3 mp3,String search) {
			return mp3.getArtist().contains(search);
		}
		
	}
	
	class ArtistNcsFilter implements BasicFilter {
		@Override
		public boolean fit(MP3 mp3,String search) {
			return mp3.getArtist().toLowerCase().contains(search);
		}
		
	}
	
	class TitleFilter implements BasicFilter {
		@Override
		public boolean fit(MP3 mp3,String search) {
			return mp3.getName().contains(search);
		}
		
	}
	
	class TitleNcsFilter implements BasicFilter {
		@Override
		public boolean fit(MP3 mp3,String search) {
			return mp3.getName().toLowerCase().contains(search);
		}
		
	}
}
