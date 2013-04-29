package game;

public class Player {
	
	private double[][] _angles = new double[Game.STAGE_COUNT][Game.ROUND_COUNT];
	
	private String _ip;
	private int _id;
	
	public Player(int id) {
		_id = id;
	}
	
	public int getId() {
		return _id;
	}
	
	
	public void setAngle(int stage, int round, double angle) {
		_angles[stage][round] = angle;
	}
	
	public double getAngle(int stage, int round) {
		return _angles[stage][round];
	}
	

}
