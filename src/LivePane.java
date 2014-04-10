import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LivePane extends JPanel {

	private static final long serialVersionUID = -1091553754758302916L;
	private PaneContainer tabbedPane;
	private JLabel curName;
	private JButton playButton;
	private JButton stopButton;
	private JButton nextButton;
	
	public LivePane(PaneContainer pc) {
		tabbedPane = pc;
		
		
		curName = new JLabel("nix");
		add(curName);
		
		stopButton = new JButton();
		stopButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.stopPlayer();				
			}
		});
		stopButton.setText("Stop");
		add(stopButton);
		
		playButton = new JButton();
		playButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.play();				
			}
		});
		playButton.setText("Play");
		add(playButton);
		
		nextButton = new JButton();
		nextButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.next();				
			}
		});
		nextButton.setText("Next");
		add(nextButton);
		
		
		tabbedPane.setLivePane(this);
	}
	
	public void set(MP3 mp3) {
		curName.setText(mp3.getName());
	}
}
