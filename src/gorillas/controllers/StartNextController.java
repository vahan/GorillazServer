package gorillas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gorillas.Game;
import gorillas.views.GameView;

public class StartNextController implements ActionListener {
	
	private GameView gameView;
	
	public StartNextController(GameView gameView) {
		super();
		this.gameView = gameView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameView.getGame().goToNext();

		if (gameView.getGame().getNextStage() < Game.STAGE_COUNT)
			gameView.getButtonNextStage().setText("Start Stage " + gameView.getGame().getNextStage());
		else
			gameView.getButtonNextStage().setText("Finish");
		gameView.getButtonNextRound().setText("Start Round " + gameView.getGame().getNextRound());
	}

}
