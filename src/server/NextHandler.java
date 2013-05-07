package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class NextHandler extends Handler {

	public NextHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		boolean isReady = game.isReady();
		String idStr = request.getParameter("id").trim();
		int id = Integer.parseInt(idStr);
		System.out.println("game is ready: " + isReady);
		if (isReady) {
			game.goToNext(id);
		}
		return isReady ? game.getPlayer(id).getStage() + ":" + game.getPlayer(id).getRound() : "0";
	}

}
