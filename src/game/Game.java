package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class Game extends Observable {
	
	public static final int ROUND_COUNT = 5;
	public static final int STAGE_COUNT = 8;
	public static final int PLAYER_COUNT = 10;
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private int counter = -1;
	
	public int getRound(int playerId) {
		return players.get(playerId).getRound();
	}
	
	public int getStage(int playerId) {
		return players.get(playerId).getStage();
	}
	
	public Player getPlayer(int id) {
		return players.get(id);
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
		return id;
	}
	
	public boolean hasStarted() {
		return players.size() > 0;
	}
	
	public void goToNext(int playerId) {
		players.get(playerId).goToNext();
		notifyObservers(players.get(playerId));
	}
	
	public boolean isReady() {
		boolean isReady = true;
		for (int i = 0; i < players.size(); ++i) {
			Player player = players.get(i);
			isReady = isReady && player.isReady();
		}
		return isReady;
	}
	
	public double getMeanAngle(int stage, int round) {
		System.out.println("players: " + Arrays.toString(players.toArray()));
		double meanAngle = 0;
		for (int i = 0; i < players.size(); ++i) {
			double angle = players.get(i).getAngle(stage, round);
			System.out.print(players.get(i).toString() + " - angle: " + angle + "\t");
			meanAngle += angle;
		}
		System.out.println();
		return meanAngle / players.size();
	}
	
	public static void finish() {
		//TODO: END THE GAME
	}
	
	private int generateId() {
		return ++counter;
	}
	

}
