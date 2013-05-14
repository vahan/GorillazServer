package gorillas.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import org.eclipse.jetty.server.Server;

import gorillas.Game;


public class GorillasServer extends Observable implements ActionListener {
	
	private Game game;
	private Server server;
	
	public GorillasServer(Game game) {
		this.game = game;
		
		server = new Server(8070);
		server.setAttribute("allowedOrigins", "*");
		server.setHandler(new GorillasHandler(this.game));
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
