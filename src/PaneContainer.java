import javax.swing.JTabbedPane;

import javazoom.jl.player.Player;


public class PaneContainer extends JTabbedPane {

	private Player player;
	
	private static final long serialVersionUID = 1L;

	public PaneContainer() {
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}
