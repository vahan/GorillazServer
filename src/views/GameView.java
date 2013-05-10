package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import org.eclipse.jetty.server.Server;

import server.GorillasHandler;
import server.GorillasServer;

import game.Game;
import game.Player;

public class GameView implements Runnable, Observer, ActionListener {
	
	private Game game;
	private GorillasServer server;
	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton buttonStart;
	private JFrame frame;
	

	
	public static void main(String[] args) throws Exception
	{
		javax.swing.SwingUtilities.invokeAndWait(new GameView(new Game()));
		
	}
	
	
	public GameView(Game game) {
		this.game = game;
		this.server = new GorillasServer(game);
		
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

        draw();
        createTabbedPane();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	private void draw() {
		buttonStart = new JButton("Start");
		frame.add(buttonStart);
		buttonStart.addActionListener(this.server);
		for (int i = 0; i < playerViews.size(); ++i) {
			PlayerView playerView = playerViews.get(i);
			JPanel panel = new JPanel();
			panel.add(playerView);
			
			frame.getContentPane().add(panel);
		}
		
	}
	
	private void createTabbedPane() {
		for (int i = 0; i < Game.STAGE_COUNT; ++i) {
			
		}
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
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
	}
}
