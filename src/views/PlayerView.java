package views;

import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import game.Player;

public class PlayerView extends JPanel implements Observer {
	
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7293744326745031617L;
	private Player player;
	private int stage;
	
	public PlayerView(Player player, int stage) {
		this.player = player;
		this.stage = stage;
		this.player.addObserver(this);
		draw();
	}
	
	
	public Player getPlayer() {
		return player;
	}
	
	private void draw() {
		add(new JLabel(player.getIp()));
		add(new JLabel(Integer.toString(player.getId())));
		for (int round = 0; round < Game.ROUND_COUNT; ++round) {
			add(new JLabel(Double.toString(player.getAngle(stage, round))));
			add(new JLabel(player.getResponseReceivedTime(stage, round) != null
										? player.getResponseReceivedTime(stage, round).toString()
										: "N/A"));
		}
		updateUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		draw();
		System.out.println("player " + player.getId() + " view was updated");
	}
	

}
