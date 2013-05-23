package gorillas.server;

import javax.servlet.http.HttpServletRequest;

import gorillas.Game;
import gorillas.Player;

/**
 * Abstract base class for specific request handlers
 * @author vahan
 *
 */
public abstract class Handler {
	
	protected Game game;
	protected HttpServletRequest request;
	protected Player player;
	protected int stage;
	protected int round;
	
	public Handler(HttpServletRequest request, Game game) {
		this.game = game;
		this.request = request;
		this.stage = getStage();
		this.round = getRound();
		this.player = getPlayer();
	}
	
	/**
	 * Handle the request and returns the resulting response
	 * @return	Response string
	 */
	public abstract String handle();

	/**
	 * Gets the sending player based on its ID
	 * @return
	 */
	private Player getPlayer() {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			Player player = game.getPlayer(id);
			return player;
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	/**
	 * Gets the stage of the sending player
	 * @return
	 */
	private int getStage() {
		try {
			int stage = Integer.parseInt(request.getParameter("stage"));
			return stage;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

	
	/**
	 * Gets the round of the sending player
	 * @return
	 */
	private int getRound() {
		try {
			int round = Integer.parseInt(request.getParameter("round"));
			return round;
		} catch (NumberFormatException ex) {
			return -1;
		}
	}

}
