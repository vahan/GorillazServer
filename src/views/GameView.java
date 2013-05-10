package views;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import server.GorillasServer;

import game.Game;
import game.Player;

public class GameView extends JFrame implements Runnable, Observer {
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private GorillasServer server;
	private StageView[] stageViews = new StageView[Game.STAGE_COUNT];
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton buttonStart;
	
	
	public static void main(String[] args) throws Exception
	{
		javax.swing.SwingUtilities.invokeAndWait(new GameView(new Game()));
		
	}
	
	
	public GameView(Game game) {
		this.game = game;
		this.server = new GorillasServer(this.game);
		this.game.addObserver(this);
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
        //Create and set up the window.
        setTitle("Gorillaz");
        setSize(600, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        draw();
        //Display the window.
        pack();
        setVisible(true);
	}
	
	private void draw() {
		buttonStart = new JButton("Start");
		getContentPane().add(buttonStart);
		buttonStart.addActionListener(this.server);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage);
			//this.game.addObserver(stageView);
			this.stageViews[stage] = stageView;
			tabbedPane.addTab("Stage " + stage, stageView);
		}
		getContentPane().add(tabbedPane);
	}
	
	private void addPlayerView(Player player) {
		for (Component comp : tabbedPane.getComponents()) {
			StageView tab = (StageView) comp;
			if (tab == null)
				continue;
			tab.addPlayerView(player);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Player player = (Player) arg;
		for (int i = 0; i < stageViews.length; ++i) {
			stageViews[i].addPlayerView(player);
		}
		System.out.println("game view was updated");
	}

}
