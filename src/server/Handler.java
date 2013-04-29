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
		_request = request;
		_stage = getStage();
		_round = getRound();
		_player = getPlayer();
		_game = game;
	}
	
	public abstract String handle();
	

	public boolean validate() {
		String type = _request.getParameter("request");
		if (_stage != _game.getStage() || _round != _game.getRound() || (_player == null && type == "authenticate"))
			return false;
		return true;
	}
	

	private Player getPlayer() {
		try {
			int id = Integer.parseInt(_request.getParameter("id"));
			return _game.getPlayer(id);
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
