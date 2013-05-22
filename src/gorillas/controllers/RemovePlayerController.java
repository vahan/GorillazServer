package gorillas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gorillas.views.GameView;

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
