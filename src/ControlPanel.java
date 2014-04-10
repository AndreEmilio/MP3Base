import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton addbutton;
	private JButton filterbutton;
	private JButton liveButton;
	
	public ControlPanel(ActionListener al, ActionListener fal, ActionListener lal) {
		addbutton = new JButton();
		addbutton.addActionListener(al);
		addbutton.setText("Add");
		filterbutton = new JButton();
		filterbutton.addActionListener(fal);
		filterbutton.setText("Filter");
		liveButton = new JButton();
		liveButton.addActionListener(lal);
		liveButton.setText("Now");
		add(addbutton);
		add(filterbutton);
		add(liveButton);
	}
}
