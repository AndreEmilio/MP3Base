import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;


public class MP3List extends JPanel {

	private static final long serialVersionUID = -4948828465616523449L;
	
	private MP3Container con;
	private JTable table;
	private JScrollPane scroll;

	public MP3List(MP3Container con, MouseListener ml, KeyListener kl) {
		super();
		this.con = con;
	
		table = new JTable();
		
		setModel();
		
		addListeners(ml,kl);
		
		scroll = new JScrollPane();
		scroll.setViewportView(table);		
		
		add(scroll);
		
	}
	
	public void update(MP3Container con) {
		
		this.con = con;
		
		update();
	}
	
	public void update() {
		
		remove(scroll);
		
		setModel();
		
		scroll = new JScrollPane();
		scroll.setViewportView(table);
		
		add(scroll);
		
		revalidate();
		repaint();
	}

	private class EnterAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
        
		}
	}
	
	public void addListeners(MouseListener ml, KeyListener kl) {
		table.addMouseListener(ml);
		table.addKeyListener(kl);
		
		final String solve = "Solve";
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, solve);
		
		table.getActionMap().put(solve, new EnterAction());		
	}
	
	private void setModel() {
		String[][] data = new String[con.size()][2];
		for (int i = 0; i < con.size(); i++) {
			data[i] = con.get(i).getVector();
		}
		
		String[] columnNames = {"Titel", "Interpret"};
		
		table.setModel(new DefaultTableModel(data,columnNames){
			private static final long serialVersionUID = 1L;

			@Override
			   public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return column == 3;
			   }
		});
	}

	public String getPath(int index) {
		if (index > con.size()-1) return "";
		return con.get(index).getPath();
	}
	
	public int getIndex() {
		return table.getSelectedRow();
	}

	public MP3 get(int index) {
		return con.get(index);
	}

	public int NoE() {
		return con.size();
	}

	public void setSelectedIndex(int i) {
		table.setRowSelectionInterval(i, i);	
	}

	public MP3Container getCon() {
		return con;
	}
	
}
