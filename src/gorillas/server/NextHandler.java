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
		String response = isReady ? game.getPlayer(id).getStage() + ":" + game.getPlayer(id).getRound() : "NO";
		if (isReady)
			GameView.LOGGER.log("Player " + id + "moved to stage " + stage + " round " + round);
		return response;
	}

}
