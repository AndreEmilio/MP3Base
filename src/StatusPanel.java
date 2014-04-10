import javax.swing.JLabel;
import javax.swing.JPanel;


public class StatusPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel sel;
	private JLabel hit;
	
	private int selected;
	private int found;
	private int max;
	
	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
		if (selected >= 0) {
			sel.setText(Integer.toString(selected));
		} else {
			sel.setText("");
		}		
		sel.revalidate();
	}

	public int getFound() {
		return found;
	}

	public void setFound(int found) {
		this.found = found;
		
		setHit();
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
		
		setHit();
	}
	
	private void setHit() {

		StringBuilder sb = new StringBuilder();
		sb.append(found); sb.append("/"); sb.append(max);
		hit.setText(sb.toString());
		
		hit.revalidate();
		hit.repaint();
	}

	public StatusPanel(int selected, int found, int max) {
		super();
		this.selected = selected;
		this.found = found;
		this.max = max;
		
		sel = new JLabel(Integer.toString(selected));
		hit = new JLabel("");
		
		setHit();
		
		add(sel);
		add(hit);
	}
}
