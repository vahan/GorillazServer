package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class AngleHandler extends Handler {
	
	public AngleHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(request.getParameter("stage").trim());
		int round = Integer.parseInt(request.getParameter("round").trim());
		double angle = Double.parseDouble(request.getParameter("angle"));
		String idStr = request.getParameter("id").trim();
		int id = Integer.parseInt(idStr);
		game.submit(id);
		game.getPlayer(id).setAngle(stage, round, angle);
		return "OK";
	}

}
