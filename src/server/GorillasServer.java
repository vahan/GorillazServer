package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.jetty.server.Server;

import game.Game;

import views.GameView;

public class GorillasServer implements ActionListener{
	
	private Game game;
	
	public GorillasServer(Game game) {
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			
			Server server = new Server(8070);
			server.setAttribute("allowedOrigins", "*");
			server.setHandler(new GorillasHandler(game));
			
			server.start();
			//server.join();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
	}
	
	
	
}
