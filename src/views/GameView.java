package views;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import controllers.RemovePlayerController;
import controllers.SelectPlayerController;

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
	private JButton buttonRemove;
	private JComboBox<String> comboPlayers;
	private JPanel panelRemove = new JPanel();
	
	private Player selectedPlayer;
	
	public static void main(String[] args) throws Exception
	{
		javax.swing.SwingUtilities.invokeAndWait(new GameView(new Game()));
		
	}
	
	
	public GameView(Game game) {
		super("Gorillaz Server Controller");
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
		//Start/Stop button
		buttonStartOrStop = new JButton("Start");
		getContentPane().add(buttonStartOrStop);
		buttonStartOrStop.addActionListener(this.server);
		
		//Tabbed pane for players' info in all stages
		drawTabbedPane();
		getContentPane().add(new JScrollPane(tabbedPane));
		
		//Removing players
		panelRemove.setLayout(new FlowLayout());
		buttonRemove = new JButton("Remove player: ");
		buttonRemove.addActionListener(new RemovePlayerController(this));
		panelRemove.add(buttonRemove);

		drawPlayerCombo();
		
		getContentPane().add(panelRemove);
	}
	
	public void drawTabbedPane() {
		while (tabbedPane.getTabCount() > 0)
			tabbedPane.removeTabAt(0);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage);
			this.stageViews[stage] = stageView;
			tabbedPane.addTab("Stage " + stage, stageView);
		}
	}
	
	public void drawPlayerCombo() {
		if (Arrays.asList(panelRemove.getComponents()).contains(comboPlayers)) {
			panelRemove.remove(comboPlayers);
		}
		ArrayList<String> playerIds = new ArrayList<String>();
		for (Player player : game.getActivePlayers()) {
			playerIds.add(Integer.toString(player.getId()));
		}
		comboPlayers = new JComboBox<String>(playerIds.toArray(new String[playerIds.size()]));
		comboPlayers.setSelectedIndex(-1);
		comboPlayers.addActionListener(new SelectPlayerController(this));
		panelRemove.add(comboPlayers);
		panelRemove.updateUI();
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
		comboPlayers.addItem(Integer.toString(player.getId()));
	}
	
	private void updateFromServer(boolean isStarted) {
		if (isStarted) {
			buttonStartOrStop.setText("Stop");
		} else {
			buttonStartOrStop.setText("Start");
		}
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}
	
	public void setSelectedPlayer(String id) {
		for (Player player : game.getActivePlayers()) {
			if (player.getId() == Integer.parseInt(id)) {
				selectedPlayer = player;
				return;
			}
		}
	}

}
