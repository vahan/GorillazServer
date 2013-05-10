package views;

import game.Game;
import game.Player;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JTable;

public class StageView extends JTable {
	
	private int stage;
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private List<String> columnNames = new ArrayList<String>(Arrays.asList("IP",
																			"ID",
																			"Done Insertion"
																			));
	
	public StageView(int stage) {
		this.stage = stage;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		initColumnNames();
	}
	
	private void initColumnNames() {
		for (int i = 0; i < Game.ROUND_COUNT; ++i) {
			columnNames.add("Angle in Round " + i);
			columnNames.add("Mean in Round " + i + "Received");
		}
	}
	
	public void addPlayerView(Player player) {
		add(new PlayerView(player, stage));
		updateUI();
	}
	

}
