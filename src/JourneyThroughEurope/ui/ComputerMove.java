package JourneyThroughEurope.ui;

import java.io.IOException;

import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.common.SaveLoad;

public class ComputerMove extends java.util.TimerTask {
    GameHistory game = GameHistory.getGameHistory();
    GameplayScreen gameplay = GameplayScreen.getGamePlay();
    Quarter q;

    @Override
    public void run() {
	// TODO Auto-generated method stub
	String orName = game.getCurrentCity().getName();
	int oldQ = game.getCurrentCity().getQuarter();
	game.getCurrentPlayer().setNextCityofComputer();
	int newQ = game.getCurrentCity().getQuarter();
	String desName = game.getCurrentCity().getName();
	q = Quarter.getQuarter(game.getCurrentCity().getQuarter());
	if (oldQ != newQ) {
	    q.changePieceQuarter(oldQ, newQ, game.getCurrentCity().getP()
		    .small(4), 0);
	}
	q.getCurrentPiece().Move(game.getCurrentCity());
	q.repaint();
	game.setRemainStep(game.getRemainStep() - 1);
	gameplay.leftLabel
		.setText("Remain moves: " + game.getRemainStep() + "");
	game.getCurrentPlayer().addHistoryCities(game.getCurrentCity());
	String his = game.getCurrentPlayer().getName() + "(compter)" + ";"
		+ "move" + ";" + orName + ";" + desName + ";"
		+ game.getRemainStep();
	try {
	    SaveLoad.addHistory(his);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	if(game.getCurrentCity().getName().equals(game.getCurrentPlayer().getFirst().getName())){
	    gameplay.endGame(game.getCurrentPlayer().getName()+" win !");
	    this.cancel();
	}
	if (game.getRemainStep() <= 0) {
	    gameplay.changePlayer();
	    this.cancel();
	}
    }

}
