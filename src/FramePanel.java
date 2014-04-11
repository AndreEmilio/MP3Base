import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTable;


public class FramePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private FilterPanel searchPanel;
	private ControlPanel controlPanel;
	private MP3List mp3list;
	private PaneContainer tabbedPane;
	private LivePane livePanel;
	private boolean search;
	
	public FramePanel(MP3Container con,PaneContainer tabbedPane) {
		this.tabbedPane = tabbedPane;
		search = true;
	    
		livePanel = tabbedPane.getLivePane();
		
	    JPanel panel = new JPanel();	    
	    panel.setLayout( new java.awt.BorderLayout() );	    
	    
	    showList(con);	
	    
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
		},new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showLivePane();			
			}
		},tabbedPane,mp3list);
		add(controlPanel,java.awt.BorderLayout.SOUTH);
	}
	
	public MP3List getMp3list() {
		return mp3list;
	}

	private void showLivePane() {
		
		if (isAncestorOf(livePanel)) {
			remove(livePanel);
		} else {
			add(livePanel,java.awt.BorderLayout.WEST);	
		}
		revalidate();
		repaint();
	}

	private void showList(MP3Container con) {
		mp3list = new MP3List(con,new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
				JTable list = (JTable)evt.getSource();
				searchPanel.setSelected(list.getSelectedRow()+1);	
				searchPanel.revalidate();
		        if (evt.getClickCount() == 2) {
		            playFile();
		        } 
		        if (evt.getButton() == MouseEvent.BUTTON3) {
		        	MP3ClickMenu popup = new MP3ClickMenu(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (arg0.getActionCommand().equals("mark")) {
								mp3list.setCurrentSelected();
							} else if (arg0.getActionCommand().equals("new")) {
								tabbedPane.add("Queue", new FramePanel(new MP3Container(),tabbedPane));
								queueFile(tabbedPane.getComponentCount()-1);
							} else {
								queueFile(Integer.valueOf(arg0.getActionCommand()));		
							}						
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
				if (arg0.getKeyCode() == KeyEvent.VK_J) {
					showFilter();
				}	
				if (arg0.getKeyCode() == KeyEvent.VK_C) {
					mp3list.scroll();
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
		return mp3list.getMainCon();
	}

	
	
	private void update() {
		
        	Thread t = new Thread() {
			public void run() {
						mp3list.newFiles(new File("/home/jovchev/Musik"),searchPanel);
										
						searchPanel.setFound(mp3list.NoE());
						
						mp3list.refreshTagData();						
					}
		    };
		
		t.start();
	}
	
	private void chSelect(int i) {
		if (mp3list.NoE() > 0) {
		int n = mp3list.getIndex();
		mp3list.setSelectedIndex((n+i+mp3list.NoE()) % mp3list.NoE());
		}
	}
	

	private void doSearch() {
		mp3list.filter(new MP3Filter(true, true, false, searchPanel.getText()));
		
		searchPanel.setFound(mp3list.NoE());
		searchPanel.setSelected(mp3list.getMainCon().getTagCount());
	}
		
	private void playFile() {	
		tabbedPane.setNext(mp3list);
		tabbedPane.play();
	}	
	
	private void createSearchPanel() {
			searchPanel = new FilterPanel(new KeyListener() {
							
			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (search) {
					doSearch();
					mp3list.setSelectedIndex(0);
				} else {
					search = true;
				}				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					chSelect(-1);
					search = false;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					chSelect(1);
					search = false;
				}
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					playFile();
					search = false;
				}
			}
		}, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tabbedPane.add("Search", new FramePanel(mp3list.getShowCon().getClone(),tabbedPane));			
			}
		});		
		searchPanel.setMax(mp3list.length());
		searchPanel.setFound(mp3list.NoE());
	}
	
	private void showFilter() {
		if (isAncestorOf(searchPanel)) {
			remove(searchPanel);
		} else {
			add(searchPanel,java.awt.BorderLayout.NORTH);
			searchPanel.set(mp3list.getIndex(),mp3list.NoE(),mp3list.length());
			searchPanel.Focus();
		}
		revalidate();
		repaint();
	}

	public void setCon(MP3Container con) {
		mp3list.setMainCon(con);	
	}

}
