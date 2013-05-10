package views;

import game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;

public class StageView extends JTable {
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private List<PlayerView> players;
	private List<String> columnNames = new ArrayList<String>(Arrays.asList("IP",
																			"ID",
																			"Time",
																			"Done Insertion"
																			));
	
	public StageView(List<PlayerView> players) {
		this.players = players;
		
		initColumnNames();
		draw();
	}
	
	private void initColumnNames() {
		for (int i = 0; i < Game.ROUND_COUNT; ++i) {
			columnNames.add("Angle in Round " + i);
			columnNames.add("Mean in Round " + i + "Received");
		}
	}
	
	private void draw() {
		for (int i = 0; i < columnNames.size(); ++i) {
			
		}
	}
	
	

}
