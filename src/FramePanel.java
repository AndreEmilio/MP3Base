import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JPanel;
import javax.swing.JTable;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class FramePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MP3Container con;
	private FilterPanel searchPanel;
	private ControlPanel controlPanel;
	private MP3List mp3list;
	private PaneContainer tabbedPane;
	
	public FramePanel(MP3Container con,PaneContainer tabbedPane) {
		this.con = con;
		this.tabbedPane = tabbedPane;
	    
	    JPanel panel = new JPanel();	    
	    panel.setLayout( new java.awt.BorderLayout() );	    
	    
	    showList();	
	    
	    createSearchPanel();

	    showControlPanel();
		
	    

	}
	
	private void showControlPanel() {
		controlPanel = new ControlPanel( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();			
			}
		},new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showFilter();			
			}
		});
		add(controlPanel,java.awt.BorderLayout.SOUTH);
	}

	private void showList() {
		mp3list = new MP3List(con,new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
				JTable list = (JTable)evt.getSource();
				searchPanel.setSelected(list.getSelectedRow()+1);	
				searchPanel.revalidate();
		        if (evt.getClickCount() == 2) {
		            int index = list.getSelectedRow();  //list.lo  locationToIndex(evt.getPoint());
		            playFile(index);
		        } 
		        if (evt.getButton() == MouseEvent.BUTTON3) {
		        	MP3ClickMenu popup = new MP3ClickMenu(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							queueFile(Integer.valueOf(arg0.getActionCommand()));							
						}
					},tabbedPane.getTabCount());
		        	popup.show(evt.getComponent(),
		                       evt.getX(), evt.getY());
		        }
		    }
		},new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					playFile();
				}				
			}
		});
		
		add(mp3list,java.awt.BorderLayout.CENTER);		
	}
	
	private void queueFile(int n) {
		int index = mp3list.getIndex();
		if (index >= 0) {
			FramePanel fp = (FramePanel) tabbedPane.getComponent(n);
			fp.getCon().add(mp3list.get(index), fp.getSearchPanel());
			fp.getMp3List().update();
		}		
	}

	public FilterPanel getSearchPanel() {
		return searchPanel;
	}

	public MP3List getMp3List() {
		return mp3list;
	}

	private MP3Container getCon() {
		return con;
	}

	
	
	private void update() {
		
        	Thread t = new Thread() {
			public void run() {
						con.add(new File("/home/jovchev/Musik"),searchPanel);
										
						mp3list.update(con);
						searchPanel.setFound(con.getList().size());
						
						con.refreshTagData();						
					}
		    };
		
		t.start();
	}
	
	private void chSelect(int i) {
		if (con.size() > 0) {
		//int n = mp3list.getIndex();
		//mp3list.setSelectedIndex((n+i+con.size()) % con.size());
		mp3list.grabFocus();
		}
	}
	

	private void doSearch() {
		mp3list.update(con.lookFor(searchPanel.getText()));
		
		searchPanel.setFound(mp3list.NoE());
		searchPanel.setSelected(con.getTagCount());
	}
		
	private void playFile(int index) {		
		
		FileInputStream in;
		
		String path = mp3list.getPath(index);
		
		if (!path.equals("")) {
		
		try {			
			
			in = new FileInputStream(path);
			
			if (tabbedPane.getPlayer() != null) {
				tabbedPane.getPlayer().close();			
			};
			
			//final int sel = index;
			
			tabbedPane.setPlayer(new Player(in));		
			
			Thread t = new Thread() {
				public void run() {
					try {
						tabbedPane.getPlayer().play();
					} catch (JavaLayerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			};
			
			t.start();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	

	
	
	
	private void createSearchPanel() {
			searchPanel = new FilterPanel(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				doSearch();			
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					chSelect(-1);
				}
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					chSelect(1);
				}
			}
		}, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.add("Search", new FramePanel(con.lookFor(searchPanel.getText()),tabbedPane));			
			}
		});		
		searchPanel.setMax(con.size());
		searchPanel.setFound(con.size());
	}
	
	private void playFile() {
		playFile(mp3list.getIndex());		
	}
	
	private void showFilter() {
		add(searchPanel,java.awt.BorderLayout.NORTH);
		searchPanel.set(mp3list.getIndex(),mp3list.NoE(),con.size());
		revalidate();
		repaint();
	}

	public void setCon(MP3Container con) {
		this.con = con;		
	}

}
