package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class MeanHandler extends Handler {

	public MeanHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		if (_game.isReady())
			return (new Double(_game.getMeanAngle())).toString();
		return "NO";
	}

}
