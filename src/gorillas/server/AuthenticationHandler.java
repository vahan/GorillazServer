package gorillas.server;

import gorillas.Game;
import gorillas.views.GameView;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationHandler extends Handler {
	
	public AuthenticationHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}
	
	@Override
	public String handle() {
        int id = game.addPlayer();
        if (id < 0) {
        	System.err.println("ID: " + id + ". The room is full. " + Game.PLAYER_COUNT + "are already here");
        	return "-1";
        }
        game.getPlayer(id).start();
        String idStr = (new Integer(id)).toString();
		GameView.LOGGER.log("A new player with id " + id + " was authenticated");
		return idStr;
	}

}
