import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class MP3ClickMenu extends JPopupMenu{

	private static final long serialVersionUID = -6720962030327365500L;

	public MP3ClickMenu(ActionListener queAl, int number) {
		
		JMenu menu = new JMenu("Queue");
		
	for (int i = 0; i < number; i++) {
		String n = Integer.toString(i);
		JMenuItem menuItem = new JMenuItem(n);
		menuItem.addActionListener(queAl);
		menuItem.setActionCommand(n);
	    menu.add(menuItem);
	}

		add(menu);
	
	}
	
}
