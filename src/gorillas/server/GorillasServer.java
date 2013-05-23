package gorillas.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import org.eclipse.jetty.server.Server;

import gorillas.Game;


public class GorillasServer extends Observable implements ActionListener {
	
	private Game game;
	private Server server;
	private GorillasHandler handler;
	
	public GorillasServer(Game game) {
		this.game = game;
		handler = new GorillasHandler(this.game);
		
		server = new Server(8070);
		server.setAttribute("allowedOrigins", "*");
		server.setHandler(handler);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			setChanged();
			if (!server.isStarted()) {
				server.start();
				notifyObservers(true);
			}
			else {
				server.stop();
				notifyObservers(false);
			}
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
	}
	
	
	
}
