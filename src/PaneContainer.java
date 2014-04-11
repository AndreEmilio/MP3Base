import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JTabbedPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class PaneContainer extends JTabbedPane {

	public static final int STOP = -5;
	public static final int READY = 1;
	public static final int CHANGED = 2;
	
	private Player player;
	private MP3List mp3list;
	private MP3 playingMP3;
	
	private LivePane livePanel;
	private int status;
	
	private static final long serialVersionUID = 1L;

	public PaneContainer() {
		status = READY;
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2 && getComponentCount() > 1) {
					remove(getSelectedIndex());
					revalidate();
					repaint();
		        }
			}
		});
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
	
	public void playfile() {
		FileInputStream in;
		
		String path = mp3list.getSong().getPath();
		
		if (!path.equals("")) {
		
		try {			
			
			in = new FileInputStream(path);
			
			if (player != null) {
				player.close();			
			};
			
			player = new Player(in);		
			
			markPlaying();
			
			setLivePanel();
			
			mp3list.markNextSong();
			mp3list = mp3list.nextList();
					
			status = READY;
						
			Thread t = new Thread() {
				public void run() {
					try {
						player.play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (status != STOP) {
						playfile();
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

	private void markPlaying() {
		if (playingMP3 != null) {
			playingMP3.setPlaying(false);
		}
		mp3list.getSong().setPlaying(true);
		revalidate();
		repaint();
		playingMP3 = mp3list.getSong();		
	}

	public void stopPlayer() {
		status = STOP;
		if (player != null) {
			player.close();		
		}
		player = null;
	}
	
	public void nextList() {
		mp3list = mp3list.nextList();
	}
	
	public void setLivePane(LivePane livePanel) {
		this.livePanel = livePanel;
	}

	public void setNext(MP3List mp3list) {
		this.mp3list = mp3list;
		mp3list.setCurrentSelected();
		status = CHANGED;
	}
	
	public void play() {
		if (player != null) {
			player.close();
		} else {
			playfile();
		}
	}
	
	public void setLivePanel() {
		if (livePanel != null) {
			livePanel.set(mp3list.getSong());
		}
	}

	public void next() {
		if (status == STOP) {
			mp3list.markNextSong();
			mp3list = mp3list.nextList();
			setLivePanel();
		} else {
			play();
		}
	}

	public String getCurSongname() {
		if (mp3list != null) {
			return mp3list.getSong().getName();
		}
		return "nichts";
	}

	public LivePane getLivePane() {
		if (livePanel != null){
			return livePanel;
		}
		return new LivePane(this);
	}
}
