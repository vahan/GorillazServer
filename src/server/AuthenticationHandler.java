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
		Enumeration<String> params = _request.getParameterNames();
		while (params.hasMoreElements()) {
			final String param = params.nextElement();
			System.out.println(param + ": " + _request.getParameter(param));
		}
		
        int id = _game.addPlayer();
        if (id < 0) {
        	System.err.println("ID: " + id + ". The room is full. " + Game.PLAYER_COUNT + "are already here");
        	return "-1";
        }
        _game.start();
        String idStr = (new Integer(id)).toString();
        System.out.println("ID assigned: " + idStr);
		return idStr;
	}

}
