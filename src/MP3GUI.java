import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;


public class MP3GUI {
		
	private JFrame meinFrame;	
	private PaneContainer tabbedPane = new PaneContainer();
	
	public MP3GUI(MP3Container con) {
		
		initFrame();	
		
		FramePanel panel = new FramePanel(con, tabbedPane);
		
		tabbedPane.addTab("Main",  panel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

	    meinFrame.add(tabbedPane);
		
	}
	
	private void initFrame() {
		meinFrame = new JFrame("Beispiel JFrame");
	       
	    meinFrame.setSize(600,600);
	    
	    meinFrame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void startGUI() {
		
	    meinFrame.setVisible(true);
	}

}
