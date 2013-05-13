package views;

import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StageView extends JTable implements Observer {
	
	private int stage;
	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private List<String> columnNames = new ArrayList<String>(Arrays.asList("IP",
																			"ID"/*,
																			"Done Insertion"*/
																			));
	private DefaultTableModel model;
	
	public StageView(int stage) {
		super();
		this.stage = stage;
		
		//model = ((DefaultTableModel) getModel());
		//setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		init();
	}
	
	private void init() {
		for (int i = 0; i < Game.ROUND_COUNT; ++i) {
			columnNames.add("Angle in Round " + i);
			columnNames.add("Mean in Round " + i + "Received");
		}
		model = new DefaultTableModel(columnNames.toArray(new String[columnNames.size()]), 0);
		setModel(model);
	}
	
	private void updatePlayerView(PlayerViewData playerView) {
		int row = playerView.getRowIndex();
		model = ((DefaultTableModel) getModel());
		model.removeRow(row);
		model.insertRow(row, playerView.getData());
		//model.fireTableRowsUpdated(row, row);
	}
	
	public void addPlayerView(Player player) {
		PlayerViewData playerView = new PlayerViewData(player, stage, getRowCount());
		playerView.addObserver(this);
		//add(playerView);
		Object[] data = playerView.getData();
		model = ((DefaultTableModel) getModel());
		model.addRow(data);
		//model.fireTableRowsInserted(0, model.getRowCount() - 1);
		//updateUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		PlayerViewData playerView = (PlayerViewData) o;
		if (playerView == null)
			return;
		updatePlayerView(playerView);
		//updateUI();
	}
	

}
