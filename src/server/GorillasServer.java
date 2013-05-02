package server;

import game.Game;

import org.eclipse.jetty.server.*;

public class GorillasServer {
	public static void main(String[] args) throws Exception
	{
		Game game = new Game();
		
		Server server = new Server(8070);
		server.setAttribute("allowedOrigins", "*");
		server.setHandler(new GorillasHandler(game));
		
		server.start();
		server.join();
	}
}