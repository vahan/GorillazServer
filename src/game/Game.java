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
		players.add(id, new Player(id));
		setChanged();
		notifyObservers();
		return id;
	}
	
	public void submit(int playerId) {
		players.get(playerId).submit();
	}
	
	public void start() {
		for (int i = 0; i < players.size(); ++i) {
			players.get(i).start();
		}
	}
	
	public void goToNext(int _playerId) {
		players.get(_playerId).goToNext();
	}
	
	public boolean isReady(int stage, int round) {
		boolean isReady = true;
		for (int i = 0; i < players.size(); ++i) {
			Player player = players.get(i);
			isReady = isReady && player.isReady(stage, round);
		}
		return isReady;
	}
	
	public boolean isReady() {
		boolean isReady = true;
		for (int i = 0; i < players.size(); ++i) {
			Player player = players.get(i);
			isReady = isReady && player.isReady(player.getStage(), player.getRound());
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
