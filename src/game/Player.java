package game;

public class Player {
	
	private double[][] _angles = new double[Game.STAGE_COUNT][Game.ROUND_COUNT];
	
	private String _ip;
	private int _id;
	private int _stage;
	private int _round;
	private int _submitted = 0;
	
	public Player(int id) {
		_id = id;
		_stage = -1;
		_round = -1;
	}
	
	public int getId() {
		return _id;
	}
	
	public int getStage() {
		return _stage;
	}
	
	public int getRound() {
		return _round;
	}
	
	public void start() {
		_round = 0;
		_stage = 1;
	}
	
	public void submit() {
		_submitted++;
	}
	
	public boolean isReady(int stage, int round) {
		return _submitted == stage * round;
	}
	
	public void goToNext() {
		if (_round < Game.ROUND_COUNT) {
			_round++;
		} else {
			_round = 0;
			if (_stage < Game.STAGE_COUNT) {
				_stage++;
			} else {
				Game.finish();
			}
		}
	}
	
	
	public void setAngle(int stage, int round, double angle) {
		_angles[stage][round] = angle;
	}
	
	public double getAngle(int stage, int round) {
		return _angles[stage][round];
	}
	
	@Override
	public String toString() {
		return "Player: " + _id;
	}
	

}
