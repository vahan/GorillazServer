package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.eclipse.jetty.server.Server;

import game.Game;

import views.GameView;

public class GorillasServer implements ActionListener{
	
	private Game game;
	private GameView gameView;
	
	public static void main(String[] args) throws Exception
	{
		GorillasServer gServer = new GorillasServer(new Game());
		javax.swing.SwingUtilities.invokeAndWait(gServer.gameView);
		
	}
	
	public GorillasServer(Game game) {
		this.game = game;
		this.gameView = new GameView(game, this);
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
