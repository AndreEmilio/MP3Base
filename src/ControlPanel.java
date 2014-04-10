import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton addbutton;
	private JButton filterbutton;
	
	public ControlPanel(ActionListener al, ActionListener fal) {
		addbutton = new JButton();
		addbutton.addActionListener(al);
		filterbutton = new JButton();
		filterbutton.addActionListener(fal);
		add(addbutton);
		add(filterbutton);
	}
}
