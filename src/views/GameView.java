package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import game.Game;
import game.Player;

public class GameView implements Runnable, Observer {
	
	private Game game;
	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	
	private JFrame frame;
	
	public GameView(Game game) {
		this.game = game;
		
		updatePlayers();
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        //Create and set up the window.
        frame = new JFrame("Gorillaz server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        draw(false);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	private void draw(boolean clear) {
		if (clear)
			frame.removeAll();
		for (int i = 0; i < playerViews.size(); ++i) {
			PlayerView playerView = playerViews.get(i);
			JPanel panel = new JPanel();
			panel.add(playerView);
			
			frame.getContentPane().add(panel);
		}
		
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
	}
	
	private void updatePlayers() {
		playerViews = new ArrayList<PlayerView>();
		for (int i = 0; i < game.playerCount(); ++i) {
			Player player = game.getPlayer(i);
			PlayerView playerView = new PlayerView(player);
			playerViews.add(playerView);
			player.addObserver(playerView);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		updatePlayers();
		System.out.println("game view was updated");
		draw(true);
	}
}
