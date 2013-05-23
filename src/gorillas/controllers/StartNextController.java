package gorillas.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gorillas.Game;
import gorillas.views.GameView;

/**
 * Action listener for Next Stage and Next Round buttons
 * @author vahan
 *
 */
public class StartNextController implements ActionListener {
	
	private GameView gameView;
	
	public StartNextController(GameView gameView) {
		super();
		this.gameView = gameView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameView.getGame().goToNext();

		if (gameView.getGame().getNextStage() < Game.STAGE_COUNT) {
			gameView.getButtonNextStage().setText("Start Stage " + gameView.getGame().getNextStage());
		}
		else {
			gameView.getButtonNextStage().setText("Finish");
			gameView.getButtonNextRound().setEnabled(false);
		}
		if (!gameView.getGame().getIsOver()) {
			gameView.getButtonNextRound().setText("End Round " + gameView.getGame().getNextRound());
		}
		
		gameView.getButtonNextStage().setEnabled(gameView.getGame().getRound() == Game.ROUND_COUNT && !gameView.getGame().getIsOver());
		gameView.getButtonNextRound().setEnabled(gameView.getGame().getRound() != Game.ROUND_COUNT && !gameView.getGame().getIsOver());
	}

}
