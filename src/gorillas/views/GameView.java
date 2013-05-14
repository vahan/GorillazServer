package gorillas.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import gorillas.Game;
import gorillas.Player;
import gorillas.controllers.*;
import gorillas.server.GorillasServer;

public class GameView extends JFrame implements Runnable, Observer {
	
	public static final LogPanel LOGGER = new LogPanel();
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private GorillasServer server;
	private StageView[] stageViews = new StageView[Game.STAGE_COUNT];
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JPanel panelButtons = new JPanel();
	private JButton buttonStartOrStopServer;
	private JButton buttonNextRound;
	private JButton buttonNextStage;
	private JButton buttonRemove;
	private JComboBox<String> comboPlayers;
	private JPanel panelRemove = new JPanel();
	
	private Player selectedPlayer;
	
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
        setSize(1000, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        draw();
        pack();
        setVisible(true);
	}
	
	private void draw() {
		//buttons
		panelButtons.setLayout(new FlowLayout());
		//Start/stop button
		buttonStartOrStopServer = new JButton("Start the Server");
		panelButtons.add(buttonStartOrStopServer);
		buttonStartOrStopServer.addActionListener(this.server);
		//Next Round button
		buttonNextStage = new JButton("Start Stage 0");
		buttonNextStage.setName("Start Stage");
		panelButtons.add(buttonNextStage);
		buttonNextStage.addActionListener(new StartNextController(this));
		//Next Round button
		buttonNextRound = new JButton("Start Round 0");
		buttonNextRound.setName("Start Round");
		panelButtons.add(buttonNextRound);
		buttonNextRound.addActionListener(new StartNextController(this));
		
		getContentPane().add(panelButtons);
		
		//Tabbed pane for players' info in all stages
		drawTabbedPane();
		JScrollPane scrolledPaneTabbed = new JScrollPane(tabbedPane);
		scrolledPaneTabbed.setPreferredSize(new Dimension(this.getWidth(), 200));
		getContentPane().add(scrolledPaneTabbed);
		
		//Removing players
		panelRemove.setLayout(new FlowLayout());
		buttonRemove = new JButton("Remove player: ");
		buttonRemove.addActionListener(new RemovePlayerController(this));
		panelRemove.add(buttonRemove);
		
		//Logger
		JScrollPane scrollPaneLogger = new JScrollPane(LOGGER);
		scrollPaneLogger.setPreferredSize(new Dimension(this.getWidth(), 100));
		getContentPane().add(scrollPaneLogger);

		drawPlayerCombo();
		
		getContentPane().add(panelRemove);
	}
	
	public void drawTabbedPane() {
		while (tabbedPane.getTabCount() > 0)
			tabbedPane.removeTabAt(0);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage, game.getActivePlayers());
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
			buttonStartOrStopServer.setText("Stop the Server");
			LOGGER.log("Server was started");
		} else {
			buttonStartOrStopServer.setText("Start the Server");
			LOGGER.log("Server was stopped");
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

	public JButton getButtonNextStage() {
		return this.buttonNextStage;
	}
	
	public JButton getButtonNextRound() {
		return this.buttonNextRound;
	}

}
