package game;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Game {
	
	public static final int ROUND_COUNT = 5;
	public static final int STAGE_COUNT = 8;
	public static final int PLAYER_COUNT = 10;
	
	private ArrayList<Player> _players = new ArrayList<Player>();
	private int _counter = -1;
	private int _round = -1;
	private int _stage = -1;
	private int _submitted = 0;
	
	
	public Game() {
	}
	
	public int getRound() {
		return _round;
	}
	
	public void setRound(int round) {
		_round = round;
	}
	
	public int getStage() {
		return _stage;
	}
	
	public void setStage(int stage) {
		_stage = stage;
	}
	
	public Player getPlayer(int id) {
		if (id >= _counter)
			return null;
		return _players.get(id);
	}
	
	public int addPlayer() {
		int id = generateId();
		_players.add(id, new Player(id));
		return id;
	}
	
	public void submit() {
		_submitted++;
	}
	
	public boolean isReady() {
		return _submitted == _players.size();
	}
	
	public double getMeanAngle() {
		double meanAngle = 0;
		for (int i = 0; i < _counter; ++i) {
			meanAngle += _players.get(i).getAngle(_stage, _round);
		}
		return meanAngle / _counter;
	}
	
	

	
	private int generateId() {
		return ++_counter;
	}
	

}
