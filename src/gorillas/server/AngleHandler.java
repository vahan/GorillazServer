package gorillas.server;

import gorillas.Game;
import gorillas.views.GameView;

import javax.servlet.http.HttpServletRequest;

public class AngleHandler extends Handler {
	
	public static final int ROUND_TO = 1;
	
	public AngleHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int stage = Integer.parseInt(request.getParameter("stage").trim());
		int round = Integer.parseInt(request.getParameter("round").trim());
		double angle = Math.round(Double.parseDouble(request.getParameter("angle")) * Math.pow(10, ROUND_TO)) / Math.pow(10, ROUND_TO);
		String idStr = request.getParameter("id").trim();
		int id = Integer.parseInt(idStr);
		GameView.LOGGER.log("Player " + id + " submitted angle " + " at stage " + stage + ", round ");
		game.getPlayer(id).setAngle(stage, round, angle);
		return "OK";
	}

}
