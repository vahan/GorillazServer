package views;

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
	private JButton buttonStartOrStop;
	
	
	public static void main(String[] args) throws Exception
	{
		javax.swing.SwingUtilities.invokeAndWait(new GameView(new Game()));
		
	}
	
	
	public GameView(Game game) {
		this.game = game;
		this.game.addObserver(this);
		this.server = new GorillasServer(this.game);
		this.server.addObserver(this);
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public void run() {
        setTitle("Gorillaz");
        setSize(600, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        draw();
        pack();
        setVisible(true);
	}
	
	private void draw() {
		buttonStartOrStop = new JButton("Start");
		getContentPane().add(buttonStartOrStop);
		buttonStartOrStop.addActionListener(this.server);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage);
			this.stageViews[stage] = stageView;
			tabbedPane.addTab("Stage " + stage, new JScrollPane(stageView));
		}
		getContentPane().add(tabbedPane);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Game)
			updateFromGame((Player)arg);
		else if (o instanceof GorillasServer)
			updateFromServer((boolean) arg);
	}
	
	private void updateFromGame(Player player) {
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			stageViews[stage].addPlayerView(player);
		}
	}
	
	private void updateFromServer(boolean isStarted) {
		if (isStarted) {
			buttonStartOrStop.setText("Stop");
		} else {
			buttonStartOrStop.setText("Start");
		}
	}

}
