package gorillas.views;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogPanel extends JPanel {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> log = new ArrayList<String>();
	
	public LogPanel() {
		super();
		
		setLayout(new BoxLayout(this, WIDTH));
	}

	public void log(String str) {
		log.add(str);
		this.add(new JLabel(str));
		super.updateUI();
	}
	

}
