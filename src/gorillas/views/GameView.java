package gorillas.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import gorillas.Game;
import gorillas.Player;
import gorillas.TextSaver;
import gorillas.controllers.*;
import gorillas.server.GorillasServer;

/**
 * JFrame GUI element for the game. Shows the entire window,
 * including start/stop, next and remove buttons; the main tabbed pain of users' data; and the log panel
 * Observes Game
 * @author vahan
 *
 */
public class GameView extends JFrame implements Runnable, Observer {
	
	public static final LogPanel LOGGER = new LogPanel();
	
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	private Game game;
	private GorillasServer server;
	private StageView[] stageViews = new StageView[Game.STAGE_COUNT];
	
	private JPanel panelButtons = new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JScrollPane scrollPaneLogger;
	private JPanel panelRemove;
	private JPanel panelSave;
	private JButton buttonStartOrStopServer;
	private JButton buttonNextRound;
	private JButton buttonNextStage;
	private JButton buttonRemove;
	private JButton buttonSave;
	private JComboBox<String> comboPlayers;
	
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
	
	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public JButton getButtonNextStage() {
		return this.buttonNextStage;
	}
	
	public JButton getButtonNextRound() {
		return this.buttonNextRound;
	}
	
	public void setSelectedPlayer(String id) {
		for (Player player : game.getActivePlayers()) {
			if (player.getId() == Integer.parseInt(id)) {
				selectedPlayer = player;
				return;
			}
		}
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
	
	/**
	 * Updates the view whenever a change (added/removed player) is notified from the model
	 * @param o		The sender object: either Game or GorillasServer.
	 * @param arg	The changed player. If null all players are deactivated.
	 * 				if o is a Game, then contains the added player
	 * 				if o is a GorillasServer, then contains a boolean value indicating whether server was started
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg == null) {
			for (Player player : game.getActivePlayers()) {
				removePlayer(player);
			}
			buttonNextStage.setText("Start Stage 0");
			buttonNextRound.setText("Start Round 0");
			return;
		}
		if (o instanceof Game)
			updateFromGame((Player)arg);
		else if (o instanceof GorillasServer)
			updateFromServer((boolean) arg);
	}
	
	private void draw() {
		//buttons
		drawButtons();
		getContentPane().add(panelButtons);
		
		//Tabbed pane for players' info in all stages
		drawTabbedPane();
		getContentPane().add(tabbedPane);
		
		//Logger
		drawLogPanel();
		getContentPane().add(scrollPaneLogger);
		
		//Removing players
		panelRemove = new JPanel(new FlowLayout());
		//Remove button
		buttonRemove = new JButton("Remove player: ");
		buttonRemove.addActionListener(new RemovePlayerController(this));
		panelRemove.add(buttonRemove);
		//Combo
		comboPlayers = new JComboBox<String>();
		drawComboPlayers();
		comboPlayers.addActionListener(new SelectPlayerController(this));
		panelRemove.add(comboPlayers);
		getContentPane().add(panelRemove);
		
		//Save data
		panelSave = new JPanel();
		panelRemove.add(panelSave, BorderLayout.EAST);
		buttonSave = new JButton("Save");
		buttonSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TextSaver saver = new TextSaver(game);
				if (saver.save()) {
					JOptionPane.showMessageDialog(GameView.this, "The data was successfully saved");
				} else {
					JOptionPane.showMessageDialog(GameView.this, "An error accured. Could not save");
				}
			}
		});
		panelSave.add(buttonSave);
	}
	
	private void drawButtons() {
		panelButtons.setLayout(new FlowLayout());
		//Start/stop button
		buttonStartOrStopServer = new JButton("Start the Server");
		panelButtons.add(buttonStartOrStopServer);
		buttonStartOrStopServer.addActionListener(this.server);
		//Next Round button
		buttonNextStage = new JButton("Start Stage 0");
		buttonNextStage.setName("Start Stage");
		buttonNextStage.setEnabled(false);
		panelButtons.add(buttonNextStage);
		buttonNextStage.addActionListener(new StartNextController(this));
		//Next Round button
		buttonNextRound = new JButton("End Round 0");
		buttonNextRound.setName("Start Round");
		buttonNextRound.setEnabled(false);
		panelButtons.add(buttonNextRound);
		buttonNextRound.addActionListener(new StartNextController(this));
		
	}
	
	private void drawTabbedPane() {
		while (tabbedPane.getTabCount() > 0)
			tabbedPane.removeTabAt(0);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage, game.getActivePlayers());
			this.stageViews[stage] = stageView;
			JScrollPane scrolledPaneTabbed = new JScrollPane(stageView,
												JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
												JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrolledPaneTabbed.setPreferredSize(new Dimension(this.getWidth(), 200));
			tabbedPane.addTab("Stage " + stage, scrolledPaneTabbed);
		}
	}
	
	private void drawLogPanel() {
		scrollPaneLogger = new JScrollPane(LOGGER);
		scrollPaneLogger.setPreferredSize(new Dimension(this.getWidth(), 100));
	}
	
	private void drawComboPlayers() {
		//Combo
		while (comboPlayers.getItemCount() > 0) {
			comboPlayers.removeItemAt(0);
			comboPlayers.setSelectedIndex(-1);
		}
		for (Player player : game.getActivePlayers()) {
			comboPlayers.addItem(Integer.toString(player.getId()));
		}
		comboPlayers.setSelectedIndex(-1);
		comboPlayers.updateUI();
		panelRemove.updateUI();
	}
	/**
	 * Called for the cases, when the change notification is sent from Game,
	 * typically when a player was added or deleted
	 * @param player	the added/removed player
	 */
	private void updateFromGame(Player player) {
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			stageViews[stage].addPlayerView(player);
		}
		comboPlayers.addItem(Integer.toString(player.getId()));
	}
	
	/**
	 * Called when a change notification is sent from Server,
	 * typically when it has started/stopped
	 * @param isStarted
	 */
	private void updateFromServer(boolean isStarted) {
		if (isStarted) {
			buttonStartOrStopServer.setText("Stop the Server");
			LOGGER.log("Server was started");
		} else {
			buttonStartOrStopServer.setText("Start the Server");
			game.reset();
			LOGGER.log("Server was stopped");
		}
		buttonNextStage.setEnabled(isStarted);
		buttonNextRound.setEnabled(false);
	}
	
	/**
	 * Deactivates the given player. Does NOT remove it.
	 * @param player
	 */
	public void removePlayer(Player player) {
		game.deactivatePlayer(getSelectedPlayer());
		drawTabbedPane();
		drawComboPlayers();
	}
	

}
