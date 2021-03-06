package gorillas.views;

import gorillas.Game;
import gorillas.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Show the stage info as a table
 * Observes PlayerViewDatas
 * @author vahan
 *
 */
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
	/**
	 * Contains the actual data to be shown on the table
	 */
	private DefaultTableModel model;
	private List<PlayerViewData> playerViewDatas = new ArrayList<PlayerViewData>();
	
	public StageView(int stage, List<Player> players) {
		super();
		this.stage = stage;
		
		init();
		
		for (Player player : players) {
			addPlayerView(player);
		}
		setAutoResizeMode(AUTO_RESIZE_OFF);
	}
	
	private void init() {
		for (int i = 0; i <= Game.ROUND_COUNT; ++i) {
			columnNames.add("Angle in Round " + i);
			columnNames.add("Mean in Round " + i + " Received");
		}
		model = new DefaultTableModel(columnNames.toArray(new String[columnNames.size()]), 0);
		setModel(model); 
	}
	
	private void updatePlayerView(PlayerViewData playerView) {
		int row = playerView.getRowIndex();
		model = ((DefaultTableModel) getModel());
		model.removeRow(row);
		model.insertRow(row, playerView.getData());
		model.fireTableRowsInserted(row, row);
	}
	
	public void addPlayerView(Player player) {
		PlayerViewData plViewData = new PlayerViewData(player, stage, getRowCount());
		playerViewDatas.add(plViewData);
		PlayerViewData playerView = plViewData;
		playerView.addObserver(this);
		Object[] data = playerView.getData();
		model = ((DefaultTableModel) getModel());
		model.addRow(data);
		model.fireTableRowsInserted(0, model.getRowCount() - 1);
	}

	/**
	 * Updates the according player view whenever a change notification is received from PlayerViewData
	 */
	@Override
	public void update(Observable o, Object arg) {
		PlayerViewData playerView = (PlayerViewData) o;
		if (playerView == null)
			return;
		updatePlayerView(playerView);
	}
	

}
