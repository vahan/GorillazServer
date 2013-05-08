package game;

import java.util.Observable;

public class Player extends Observable {
	
	private double[][] angles = new double[Game.STAGE_COUNT][Game.ROUND_COUNT + 1];
	
	private String ip;
	private int id;
	private int stage;
	private int round;
	private boolean submitted = false;
	
	public Player(int id) {
		this.id = id;
		this.stage = -1;
		this.round = -1;
		this.submitted = false;
	}
	
	public int getId() {
		return id;
	}
	
	public int getStage() {
		return stage;
	}
	
	public int getRound() {
		return round;
	}
	
	public void start() {
		round = 0;
		stage = 1;
		setChanged();
		notifyObservers();
	}
	
	public boolean isReady() {
		return submitted;
	}
	
	public void goToNext() {
		if (round < Game.ROUND_COUNT) {
			round++;
		} else {
			round = 0;
			if (stage < Game.STAGE_COUNT) {
				stage++;
			} else {
				Game.finish();
			}
		}
		submitted = false;
		setChanged();
		notifyObservers();
	}
	
	
	public void setAngle(int stage, int round, double angle) {
		angles[stage][round] = angle;
		submitted = true;
		setChanged();
		notifyObservers();
	}
	
	public double getAngle(int stage, int round) {
		return angles[stage][round];
	}
	
	@Override
	public String toString() {
		return "Player: " + id;
	}
	

}
