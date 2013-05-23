package gorillas;

import gorillas.views.GameView;

/**
 * Runs the server side GUI
 * @author vahan
 *
 */
public class Gorillas {
	
	private static GameView gameView;
	
	public static void main(String[] args) throws Exception
	{
		gameView = new GameView(new Game());
		javax.swing.SwingUtilities.invokeAndWait(gameView);
		
	}

}
