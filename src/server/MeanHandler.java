package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class MeanHandler extends Handler {

	public MeanHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(_request.getParameter("stage"));
		int round = Integer.parseInt(_request.getParameter("round"));
		if (_game.isReady(stage, round)) {
			String mean = (new Double(_game.getMeanAngle(stage, round))).toString();
			System.out.println("mean: " + mean);
			return mean;
		}
		return "ERROR: GAME IS NOT READY";
	}

}
