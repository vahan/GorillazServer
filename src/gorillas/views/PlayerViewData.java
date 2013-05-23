package gorillas.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import gorillas.Game;
import gorillas.Player;

/**
 * Contains player's data to be shown on the GUI
 * Observes the Players and is being observed by StageViews
 * @author vahan
 *
 */
public class PlayerViewData extends Observable implements Observer {
	
	private Player player;
	private int stage;
	/**
	 * The data is stored as an array
	 */
	private List<String> data = new ArrayList<String>();
	private int rowIndex;
	
	public PlayerViewData(Player player, int stage, int rowIndex) {
		this.player = player;
		this.stage = stage;
		this.rowIndex = rowIndex;
		this.player.addObserver(this);
		setData();
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	/**
	 * Exports the players' info to an array, which will be displayed in StageViews
	 */
	private void setData() {
		data.clear();
		data.add(player.getIp());
		data.add(Integer.toString(player.getId()));
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		for (int round = 0; round <= Game.ROUND_COUNT; ++round) {
			data.add(Double.toString(player.getAngle(stage, round)));
			data.add(player.getResponseReceivedTime(stage, round) != null
										? df.format(player.getResponseReceivedTime(stage, round))
										: "N/A");
		}
	}
	
	public Object[] getData() {
		return data.toArray(new Object[data.size()]); //Return a copy of the data, to prevent it from being changed from outside
	}

	@Override
	public void update(Observable o, Object arg) {
		setData();
		setChanged();
		notifyObservers();
	}
	

}
