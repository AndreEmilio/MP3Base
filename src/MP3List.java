import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class MP3List extends JPanel {

	private static final long serialVersionUID = -4948828465616523449L;
	
	private MP3Container mainCon;
	private MP3Container showCon;
	private JTable table;
	private JScrollPane scroll;

	private MP3List nextList;
	private int curSong;

	public MP3List(MP3Container con, MouseListener ml, KeyListener kl) {
		super();
		mainCon = con;
		showCon = con;
		nextList = this;
		curSong = 0;
	
		final MyRenderer renderer = new MyRenderer();
		table = new JTable() {
			
			private static final long serialVersionUID = -7666798269867418515L;

			public TableCellRenderer getCellRenderer(int row, int column) {
		        if (showCon.getResourceNumber(row) == curSong) {
		            return renderer;
		        }
		        if (showCon.get(row).isPlaying()) {
		            return renderer;
		        }
		        return super.getCellRenderer(row, column);
		    }
		};
		
		setModel();
		
		addListeners(ml,kl);
		
		scroll = new JScrollPane();
		scroll.setViewportView(table);		
		
		add(scroll);
		
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
		String[][] data = new String[NoE()][2];
		for (int i = 0; i < NoE(); i++) {
			data[i] = showCon.get(i).getVector();
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
		if (index > mainCon.size()-1) return "";
		return mainCon.get(index).getPath();
	}
	
	public int getIndex() {
		if (table.getSelectedRow() < 0)  {
			return -1;
		}
		return showCon.getResourceNumber(table.getSelectedRow());
	}

	public MP3 get(int index) {
		return mainCon.get(index);
	}

	public int NoE() {
		return showCon.size();
	}

	public void setSelectedIndex(int i) {
		if (NoE() > i) {
			table.setRowSelectionInterval(i, i);	
		}
		scroll(i);
	}

	public int next(int index) {
		return (index+1+NoE()) % NoE();
	}
	
	public void markNextSong() {
		curSong = (curSong+1+length()) % length();
		revalidate();
		repaint();
	}
	
	public void setCurrentSelected() {
		curSong = getIndex();
		revalidate();
		repaint();
	}
	
	public MP3List nextList() {
		return nextList;
	}

	public MP3 getSong() {
		return get(curSong);
	}

	public void setNextList(MP3List mp3list) {
		nextList = mp3list;		
	}
	
	class MyRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -2445733531558324984L;

		public Component getTableCellRendererComponent(
		      JTable table, Object value, 
		      boolean isSelected, boolean hasFocus, 
		      int row, int col)  
		   {
		      // get the DefaultCellRenderer to give you the basic component
		      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		      // apply your rules
		      c.setFont(new Font("Arial", Font.BOLD, 14));
		      c.setForeground(Color.RED);
		      if (showCon.get(row).isPlaying()) {
		    	  c.setForeground(Color.BLUE);
		      }
		      return c;
		   }
		   
		}

	public void scroll(int n) {
		table.scrollRectToVisible(table.getCellRect(n, 0, true));		
	}
	
	public void scroll() {
		scroll(curSong);		
	}

	public MP3Container getMainCon() {
		return mainCon;
	}

	public void newFiles(File file, FilterPanel searchPanel) {
		MP3Container temp = showCon;
		mainCon.add(file, searchPanel);	
		if (temp == showCon) {
			update();
		}		
	}

	public void refreshTagData() {
		mainCon.refreshTagData();		
	}

	public void filter(MP3Filter mp3Filter) {
		showCon = mainCon.lookFor(mp3Filter);	
		update();
	}

	public int length() {
		return mainCon.size();
	}

	public void setMainCon(MP3Container con) {
		mainCon = con;		
	}

	public MP3Container getShowCon() {
		return showCon;
	}
	
}
