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
		String n1 = Integer.toString(i+1);
		JMenuItem menuItem = new JMenuItem(n1);
		menuItem.addActionListener(queAl);
		menuItem.setActionCommand(n);
	    menu.add(menuItem);
	}

		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(queAl);
		menuItem.setActionCommand("new");
		menu.add(menuItem);
	
		JMenuItem menuItem2 = new JMenuItem("Mark Next");
		menuItem2.addActionListener(queAl);
		menuItem2.setActionCommand("mark");
		
		add(menu);
		add(menuItem2);
	
	}
	
}
