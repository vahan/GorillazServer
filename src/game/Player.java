package game;

import java.util.Date;
import java.util.Observable;


public class Player extends Observable {
	
	private double[][] angles = new double[Game.STAGE_COUNT][Game.ROUND_COUNT + 1];
	
	private String ip;
	private int id;
	private int stage;
	private int round;
	private boolean submitted = false;
	private boolean isActive = true;
	
	private Date[][] responseReceivedTime = new Date[Game.STAGE_COUNT][Game.ROUND_COUNT + 1];
	
	public Player(int id) {
		this.id = id;
		this.stage = -1;
		this.round = -1;
		this.submitted = false;
	}
	
	public String getIp() {
		return ip;
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
		stage = 0;
		setChanged();
		notifyObservers();
	}
	
	public boolean isReady() {
		return submitted;
	}
	
	public boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void goToNext() {
		if (round == 0) {
			responseReceivedTime[stage][round] = new Date();
		}
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
		responseReceivedTime[stage][round] = new Date();
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

	public Date getResponseReceivedTime(int stage, int round) {
		return responseReceivedTime[stage][round];
	}
	

}
