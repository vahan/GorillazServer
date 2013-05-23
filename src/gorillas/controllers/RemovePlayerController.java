package gorillas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gorillas.views.GameView;

/**
 * Action listener for the remove player button
 * @author vahan
 *
 */

public class RemovePlayerController implements ActionListener {
	
	private GameView gameView;
	
	
	public RemovePlayerController(GameView game) {
		this.gameView = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameView.removePlayer(gameView.getSelectedPlayer());
	}

}
