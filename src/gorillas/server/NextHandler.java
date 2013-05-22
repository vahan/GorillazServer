package gorillas.server;

import javax.servlet.http.HttpServletRequest;

import gorillas.Game;
import gorillas.views.GameView;

public class NextHandler extends Handler {

	public NextHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		int id = Integer.parseInt(request.getParameter("id").trim());
		boolean isReady = game.isReady(stage, round);
		if (isReady) {
			game.getPlayer(id).goToNext();
		}
		int stage = game.getPlayer(id).getStage();
		double wind = game.getWind(stage);
		String response = isReady ? stage + ":" + game.getPlayer(id).getRound() + ":" + wind : "NO";
		if (isReady) {
			GameView.LOGGER.log("Player " + id + "moved to stage " + stage + " round " + round);
			GameView.LOGGER.log("Wind: " + wind);
		}
		return response;
	}

}
