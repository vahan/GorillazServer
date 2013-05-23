package gorillas.server;

import gorillas.Game;
import gorillas.views.GameView;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles users' authentication requests
 * @author vahan
 *
 */
public class AuthenticationHandler extends Handler {
	
	public AuthenticationHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}
	
	/**
	 * Assigns a unique ID to the user and sends it back 
	 * with the previously randomly generated wind to the user
	 * The response format is ID:WIND
	 */
	@Override
	public String handle() {
        int id = game.addPlayer();
        if (id < 0) {
        	System.err.println("ID: " + id + ". The room is full. " + Game.PLAYER_COUNT + "are already here");
        	return "-1";
        }
        game.getPlayer(id).start();
        String response = (new Integer(id)).toString() + ":" + game.getWind();
		GameView.LOGGER.log("A new player with id " + id + " was authenticated");
		return response;
	}

}
