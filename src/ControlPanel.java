import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ControlPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JButton addbutton;
	private JButton filterbutton;
	private JButton liveButton;
	private JSpinner nQueue;
	private SpinnerNumberModel snm;
	private MP3List mp3list;
	
	public ControlPanel(ActionListener al, ActionListener fal, ActionListener lal,PaneContainer tabbedPane, MP3List mp3list) {
		this.mp3list = mp3list;
		
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
		JLabel nLabel = new JLabel("Next List");
		add(nLabel);
		snm = new MySpinnerModel(tabbedPane);
		nQueue = new JSpinner(snm);
		add(nQueue);
	}
	
	class MySpinnerModel extends SpinnerNumberModel {

		private static final long serialVersionUID = 6275278561467586079L;
		private PaneContainer tabbedPane;	
		
		public MySpinnerModel(PaneContainer tp) {
			super(tp.getComponentCount()+1,1,tp.getComponentCount()+1,1);
			this.tabbedPane = tp;
			addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent arg0) {
					mp3list.setNextList(((FramePanel) tabbedPane.getComponent(getNumber().intValue()-1)).getMp3list());		
					tabbedPane.nextList();
				}
			});
		}
		
		@Override
		public Object getNextValue() {
			setMaximum(new Integer(tabbedPane.getComponentCount()));
			int n = getNumber().intValue();
			if (n == tabbedPane.getComponentCount()) {
				return n;
			}
				return n+1;
		}
		
		@Override
		public Object getPreviousValue() {
			int n = getNumber().intValue();
			if (n == 1) {
				return n;
			}
				return n-1;
		}
		
	}
	
}
