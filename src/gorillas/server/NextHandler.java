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
		int playerStage = game.getPlayer(id).getStage();
		int playerRound = game.getPlayer(id).getRound();
		double wind = game.getWind(playerStage);
		String response = isReady ? playerStage + ":" + playerRound + ":" + wind : "NO";
		if (isReady) {
			GameView.LOGGER.log("Player " + id + "moved to stage " + this.stage + " round " + this.round);
			GameView.LOGGER.log("Wind: " + wind);
		}
		return response;
	}

}
