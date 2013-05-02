package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class AngleHandler extends Handler {
	
	public AngleHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(_request.getParameter("stage").trim());
		int round = Integer.parseInt(_request.getParameter("round").trim());
		double angle = Double.parseDouble(_request.getParameter("angle"));
		String idStr = _request.getParameter("id").trim();
		int id = Integer.parseInt(idStr);
		_game.submit(id);
		_game.getPlayer(id).setAngle(stage, round, angle);
		return "OK";
	}

}
