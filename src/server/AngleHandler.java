package server;

import game.Game;

import javax.servlet.http.HttpServletRequest;

public class AngleHandler extends Handler {
	
	public AngleHandler(HttpServletRequest request, Game game) {
		super(request, game);
	}

	@Override
	public String handle() {
		_game.submit();
		double angle = Double.parseDouble(_request.getParameter("angle"));
        System.out.println("angle: " + angle);
        return "OK";
	}

}
