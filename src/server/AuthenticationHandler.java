package server;

import java.util.Enumeration;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationHandler extends Handler {
	
	public AuthenticationHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}
	
	@Override
	public String handle() {
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			final String param = params.nextElement();
			System.out.println(param + ": " + request.getParameter(param));
		}
		
        int id = game.addPlayer();
        if (id < 0) {
        	System.err.println("ID: " + id + ". The room is full. " + Game.PLAYER_COUNT + "are already here");
        	return "-1";
        }
        game.getPlayer(id).start();
        String idStr = (new Integer(id)).toString();
        System.out.println("ID assigned: " + idStr);
		return idStr;
	}

}
