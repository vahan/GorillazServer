package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import views.GameView;

public class SelectPlayerController implements ActionListener {
	
	private GameView gameView;
	
	public SelectPlayerController(GameView gameView) {
		this.gameView = gameView;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String selectedId = (String)cb.getSelectedItem();
		gameView.setSelectedPlayer(selectedId);
	}

}
