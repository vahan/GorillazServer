package server;

import game.Game;
import game.Player;

import javax.servlet.http.HttpServletRequest;

public abstract class Handler {
	
	protected Game _game;
	protected HttpServletRequest _request;
	protected Player _player;
	protected int _stage;
	protected int _round;
	
	public Handler(HttpServletRequest request, Game game) {
		_game = game;
		_request = request;
		_stage = getStage();
		_round = getRound();
		_player = getPlayer();
	}
	
	public abstract String handle();
	

	private Player getPlayer() {
		try {
			int id = Integer.parseInt(_request.getParameter("id"));
			Player player = _game.getPlayer(id);
			return player;
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	private int getStage() {
		try {
			int stage = Integer.parseInt(_request.getParameter("stage"));
			return stage;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	private int getRound() {
		try {
			int round = Integer.parseInt(_request.getParameter("round"));
			return round;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

}
