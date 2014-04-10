import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FilterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField search;
	private JButton searchButton;
	private StatusPanel spanel;
	
	public FilterPanel(KeyListener kl, ActionListener al) {
		spanel = new StatusPanel(0, 0, 0);
	    
	    add(spanel);
	    
		search = new JTextField("",30);
		search.setForeground(Color.BLUE);

		search.setBackground(Color.YELLOW);
		
		search.addKeyListener(kl);
		
		add(search);
		
		searchButton = new JButton();
		searchButton.addActionListener(al);
		add(searchButton);
	}

	public void setSelected(int selectedRow) {
		spanel.setSelected(selectedRow);		
	}

	public void setFound(int size) {
		spanel.setFound(size);		
	}

	public String getText() {
		return search.getText().toString();
	}

	public void setMax(int size) {
		spanel.setMax(size);		
	}

	public void set(int index, int noE, int size) {
		spanel.setSelected(index);
		spanel.setFound(noE);
		spanel.setMax(size);		
	}
}
