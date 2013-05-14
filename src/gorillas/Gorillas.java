package gorillas;

import gorillas.views.GameView;

public class Gorillas {
	
	public static void main(String[] args) throws Exception
	{
		javax.swing.SwingUtilities.invokeAndWait(new GameView(new Game()));
		
	}

}
