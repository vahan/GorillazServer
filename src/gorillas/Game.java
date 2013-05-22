package gorillas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import org.apache.commons.lang3.ArrayUtils;

import gorillas.views.GameView;

public class Game extends Observable {
	
	public static final int STAGE_COUNT = 8;
	public static final int ROUND_COUNT = 5;
	public static final int PLAYER_COUNT = 10;
	
	private static final double MIN_WIND = -5;
	private static final double MAX_WIND = 5;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int counter = -1;
	private int stage = 0;
	private int round = -1;
	private double[] winds = new double[STAGE_COUNT];
	private boolean isOver = false;
	
	public Game() {
		super();
		for (int stage = 0; stage < STAGE_COUNT; ++stage) {
			winds[stage] = generateWind();
		}
	}
	
	public void reset() {
		counter = -1;
		stage = 0;
		round = -1;
		winds = new double[STAGE_COUNT];
		players = new ArrayList<Player>();
		setChanged();
		notifyObservers(null);
	}
	
	public int getStage() {
		return stage;
	}
	
	public int getNextStage() {
		return stage + 1;
	}
	
	public int getRound() {
		return round;
	}
	
	public int getNextRound() {
		return round < Game.ROUND_COUNT ? round + 1 : 0;
	}
	public Player getPlayer(int id) {
		return players.get(id);
	}
	
	public double getWind() {
		return this.winds[stage];
	}
	
	public double getWind(int stage) {
		return this.winds[stage];
	}
	
	public ArrayList<Player> getActivePlayers() {
		ArrayList<Player> activePlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (!player.getIsActive())
				continue;
			activePlayers.add(player);
		}
		return activePlayers;
	}
	
	public ArrayList<Player> getAllPlayers() {
		return new ArrayList<Player>(players);
	}
	
	public int playerCount() {
		return players.size();
	}
	
	public int addPlayer() {
		int id = generateId();
		Player player = new Player(id);
		players.add(id, player);
		setChanged();
		notifyObservers(player);
		GameView.LOGGER.log("player " + id + " was added to the game");
		return id;
	}
	
	public boolean deactivatePlayer(Player player) {
		if (players.contains(player)) {
			player.setIsActive(false);
			GameView.LOGGER.log("player " + player.getId() + " was deactivated");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasActivePlayer(int id) {
		for (Player player : players) {
			if (player.getId() == id && player.getIsActive())
				return true;
		}
		return false;
	}
	
	public boolean hasStarted() {
		return players.size() > 0;
	}
	
	public void goToNext() {
		if (stage < 0 && round < 0) {
			stage++;
			//winds[stage] = generateWind();
		}
		if (round < Game.ROUND_COUNT) {
			round++;
		} else {
			if (round == Game.ROUND_COUNT) {
				//winds[stage + 1] = generateWind();
			}
			round = 0;
			if (stage < Game.STAGE_COUNT - 1) {
				stage++;
			} else {
				finish();
			}
		}
		if (!isOver)
			GameView.LOGGER.log("New stage: " + stage + "\t new round: " + round);
	}
	
	public boolean isReady(int stage, int round) {
		return this.stage == stage && this.round == round;
	}
	
	private double getMeanAngle(int stage, int round) {
		double meanAngle = 0;
		for (Player player : getActivePlayers()) {
			double angle = player.getAngle(stage, round);
			meanAngle += angle;
		}
		meanAngle = meanAngle / getActivePlayers().size();
		GameView.LOGGER.log("mean at stage: " + stage + " and round: " + round + " : " + meanAngle);
		return meanAngle;
	}
	
	public double[] getAllMeanAngles() {
		double[] means = new double[round];
		
		for (int r = 0; r < round; ++r) {
			means[r] = getMeanAngle(stage, r + 1);
		}
		ArrayUtils.reverse(means); //To show the last mean first
		GameView.LOGGER.log("All means: " + Arrays.toString(means));
		return means;
	}
	
	public boolean save() {
		TextSaver saver = new TextSaver(this);
		
		GameView.LOGGER.log("Game was successfully saved");
		return saver.save();
	}
	
	public void finish() {
		//TODO: END THE GAME
		isOver = true;
		GameView.LOGGER.log("Game is over");
	}
	
	public boolean getIsOver() {
		return isOver;
	}
	
	private double generateWind() {
		double wind = MIN_WIND + (int)(Math.random() * ((MAX_WIND - MIN_WIND) + 1));
		return wind;
	}
	
	private int generateId() {
		return ++counter;
	}
	

}
