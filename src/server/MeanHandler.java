package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class MeanHandler extends Handler {

	public MeanHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(request.getParameter("stage"));
		int round = Integer.parseInt(request.getParameter("round"));
		String mean = (new Double(game.getMeanAngle(stage, round))).toString();
		System.out.println("mean: " + mean);
		return mean;
	}

}
