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
import javax.swing.border.EmptyBorder;

import gorillas.Game;
import gorillas.Player;
import gorillas.TextSaver;
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
	private JPanel panelRemove = new JPanel();
	private JPanel panelSave = new JPanel();
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
		buttonNextStage.setEnabled(false);
		panelButtons.add(buttonNextStage);
		buttonNextStage.addActionListener(new StartNextController(this));
		//Next Round button
		buttonNextRound = new JButton("Start Round 0");
		buttonNextRound.setName("Start Round");
		buttonNextRound.setEnabled(false);
		panelButtons.add(buttonNextRound);
		buttonNextRound.addActionListener(new StartNextController(this));
		
		getContentPane().add(panelButtons);
		
		//Tabbed pane for players' info in all stages
		drawTabbedPane();
		
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

		//Save data
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
		
		getContentPane().add(panelRemove);
	}
	
	public void drawTabbedPane() {
		while (tabbedPane.getTabCount() > 0)
			tabbedPane.removeTabAt(0);
		tabbedPane.setAlignmentY(0);
		for (int stage = 0; stage < Game.STAGE_COUNT; ++stage) {
			StageView stageView = new StageView(stage, game.getActivePlayers());
			this.stageViews[stage] = stageView;
			JScrollPane scrolledPaneTabbed = new JScrollPane(stageView);
			scrolledPaneTabbed.setPreferredSize(new Dimension(this.getWidth(), 200));
			tabbedPane.addTab("Stage " + stage, scrolledPaneTabbed);
		}
		getContentPane().add(tabbedPane);
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
			game.reset();
			LOGGER.log("Server was stopped");
		}
		buttonNextStage.setEnabled(isStarted);
		buttonNextRound.setEnabled(false);
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
	
	public void removePlayer(Player player) {
		getGame().deactivatePlayer(getSelectedPlayer());
		drawTabbedPane();
		drawPlayerCombo();
	}

}
