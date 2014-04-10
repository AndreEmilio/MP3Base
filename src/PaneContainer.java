import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JTabbedPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class PaneContainer extends JTabbedPane {

	private Player player;
	private MP3List mp3list;
	
	private int currMP3;
	private MP3List currList;
	private LivePane livePanel;
	
	private static final long serialVersionUID = 1L;

	public PaneContainer() {
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	public MP3List getMp3list() {
		return mp3list;
	}

	public void setMp3list(MP3List mp3list) {
		this.mp3list = mp3list;
	}
	
	public void playfile(int index) {
		FileInputStream in;
		
		String path = mp3list.getPath(index);
		
		if (!path.equals("")) {
		
		try {			
			
			in = new FileInputStream(path);
			
			if (player != null) {
				player.close();			
			};
			
			player = new Player(in);		
			currList = mp3list;
			currMP3 = index;
			
			final int ind = index;
			
			if (livePanel != null) {
				livePanel.set(mp3list.get(index));
			}
			
			Thread t = new Thread() {
				public void run() {
					try {
						player.play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (currList == mp3list && currMP3 == ind) {
						System.out.println(ind);
					}
				}
			};
			
			t.start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	public void stopPlayer() {
		currMP3 = -5;
		player.close();		
	}
	
	public void setLivePane(LivePane livePanel) {
		this.livePanel = livePanel;
	}

	public void play() {
		playfile(mp3list.getIndex());		
	}

	public void next() {
		playfile(mp3list.getIndex()+1);
	}
}
