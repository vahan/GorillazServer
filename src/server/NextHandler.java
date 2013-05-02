package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class NextHandler extends Handler {

	public NextHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		boolean isReady = _game.isReady();
		String idStr = _request.getParameter("id").trim();
		int id = Integer.parseInt(idStr);
		System.out.println("game is ready: " + isReady);
		if (isReady) {
			_game.goToNext(id);
		}
		return isReady ? _game.getPlayer(id).getStage() + ":" + _game.getPlayer(id).getRound() : "0";
	}

}
