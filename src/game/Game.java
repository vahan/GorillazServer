package game;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Game {
	
	public static final int ROUND_COUNT = 5;
	public static final int STAGE_COUNT = 8;
	public static final int PLAYER_COUNT = 10;
	
	private ArrayList<Player> _players = new ArrayList<Player>();
	private int _counter = -1;
	
	
	public int getRound(int playerId) {
		return _players.get(playerId).getRound();
	}
	
	public int getStage(int playerId) {
		return _players.get(playerId).getStage();
	}
	
	public Player getPlayer(int id) {
		return _players.get(id);
	}
	
	public int addPlayer() {
		int id = generateId();
		_players.add(id, new Player(id));
		return id;
	}
	
	public void submit(int playerId) {
		_players.get(playerId).submit();
	}
	
	public void start() {
		for (int i = 0; i < _players.size(); ++i) {
			_players.get(i).start();
		}
	}
	
	public void goToNext(int _playerId) {
		_players.get(_playerId).goToNext();
	}
	
	public boolean isReady(int stage, int round) {
		boolean isReady = true;
		for (int i = 0; i < _players.size(); ++i) {
			Player player = _players.get(i);
			isReady = isReady && player.isReady(stage, round);
		}
		return isReady;
	}
	
	public boolean isReady() {
		boolean isReady = true;
		for (int i = 0; i < _players.size(); ++i) {
			Player player = _players.get(i);
			isReady = isReady && player.isReady(player.getStage(), player.getRound());
		}
		return isReady;
	}
	
	public double getMeanAngle(int stage, int round) {
		System.out.println("players: " + Arrays.toString(_players.toArray()));
		double meanAngle = 0;
		for (int i = 0; i < _players.size(); ++i) {
			double angle = _players.get(i).getAngle(stage, round);
			System.out.print(_players.get(i).toString() + " - angle: " + angle + "\t");
			meanAngle += angle;
		}
		System.out.println();
		return meanAngle / _players.size();
	}
	
	public static void finish() {
		//TODO: END THE GAME
	}
	
	private int generateId() {
		return ++_counter;
	}
	

}
