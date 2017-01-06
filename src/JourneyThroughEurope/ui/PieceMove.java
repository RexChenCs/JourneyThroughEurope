package JourneyThroughEurope.ui;

import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.Point;

public class PieceMove extends java.util.TimerTask {
    Point des;
    PieceLabel piece;

    public PieceMove(City c1, PieceLabel pieceLabel) {
	this.piece = pieceLabel;
	this.des = c1.getP();
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	int x = this.piece.getX()*4;
	int y = this.piece.getY()*4;

	int x1 = des.getX();
	int y1 = des.getY();
	if (x < x1) {
	    x = x + 10;
	} else if (x > x1) {
	    x = x - 10;
	}
	if (y < y1) {
	    y = y + 10;
	} else if (y > y1) {
	    y = y - 10;
	}
	this.piece.setLocation(x/4, y/4);
        if(Math.abs((x - x1))<6 && Math.abs((y - y1))<6){
            this.piece.setLocation(des);
            this.cancel();         
        }
    }

}
