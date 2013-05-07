package views;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Player;

public class PlayerView extends JPanel implements Observer {
	
	/**
	 * Generated serial version ID
	 */
	private static final long serialVersionUID = -7293744326745031617L;
	private Player player;
	
	public PlayerView(Player player) {
		this.player = player;
		
		draw();
	}
	
	
	public Player getPlayer() {
		return player;
	}
	
	private void draw() {
		JLabel idLabel = new JLabel(Integer.toString(player.getId()));
		add(idLabel);
		updateUI();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("player " + player.getId() + " view was updated");
		draw();
	}
	

}
