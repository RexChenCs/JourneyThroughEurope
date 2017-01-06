package JourneyThroughEurope.ui;

import javax.swing.JLabel;

public class CardsMove extends java.util.TimerTask {
    private JLabel[] cards;
    private int index;
    private int newflag;
    CardsMove(JLabel[] jla){
	this.cards = jla;
	this.index = 0;
	this.newflag = 1;
    }
    @Override
    public void run() {
	// TODO Auto-generated method stub
	int v = 50;
	if (this.cards[this.index].getX() - v > 0) {
	    if(this.newflag == 1){
		this.cards[this.index].setVisible(true);
		this.newflag = 0;
	    }
	    this.cards[this.index].setLocation(this.cards[this.index].getX() - v, this.cards[this.index].getY());
	} else {
	    this.cards[this.index].setLocation(0, this.cards[this.index].getY());
	    this.index++;
	    this.newflag = 1;
	}
	if(this.index == this.cards.length){
	    this.cancel();
	}
    }

}
