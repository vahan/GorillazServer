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
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int counter = -1;
	private int stage = 0;
	private int round = -1;
	private double[] winds = new double[STAGE_COUNT];
	
	private static double minWind = -5;
	private static double maxWind = 5;
	
	public Game() {
		super();
		winds[0] = generateWind();
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
	
	public ArrayList<Player> getActivePlayers() {
		ArrayList<Player> activePlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (!player.getIsActive())
				continue;
			activePlayers.add(player);
		}
		return activePlayers;
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
			winds[stage] = generateWind();
		}
		if (round < Game.ROUND_COUNT) {
			round++;
		} else {
			round = 0;
			if (stage < Game.STAGE_COUNT) {
				stage++;
				winds[stage] = generateWind();
			} else {
				Game.finish();
			}
		}
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
		XMLSaver saver = new XMLSaver(this);
		
		GameView.LOGGER.log("Game was successfully saved");
		return saver.save();
	}
	
	public static void finish() {
		//TODO: END THE GAME
		
		GameView.LOGGER.log("Game is over");
	}
	
	private double generateWind() {
		double wind = minWind + (int)(Math.random() * ((maxWind - minWind) + 1));
		GameView.LOGGER.log("wind: " + wind);
		return wind;
	}
	
	private int generateId() {
		return ++counter;
	}
	

}
