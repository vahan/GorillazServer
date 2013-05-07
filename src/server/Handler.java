package server;

import game.Game;
import game.Player;

import javax.servlet.http.HttpServletRequest;

public abstract class Handler {
	
	protected Game game;
	protected HttpServletRequest request;
	protected Player player;
	protected int stage;
	protected int round;
	
	public Handler(HttpServletRequest request, Game game) {
		this.game = game;
		this.request = request;
		this.stage = getStage();
		this.round = getRound();
		this.player = getPlayer();
	}
	
	public abstract String handle();
	

	private Player getPlayer() {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Player player = game.getPlayer(id);
			return player;
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	private int getStage() {
		try {
			int stage = Integer.parseInt(request.getParameter("stage"));
			return stage;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	private int getRound() {
		try {
			int round = Integer.parseInt(request.getParameter("round"));
			return round;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

}
