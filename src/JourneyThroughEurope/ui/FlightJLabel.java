package JourneyThroughEurope.ui;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.PlaneCity;
import JourneyThroughEurope.bean.Point;
import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.FlightPoints;

public class FlightJLabel extends JLabel implements MouseListener {
    private static final long serialVersionUID = 1L;
    FlightPoints fps = FlightPoints.getPointsManager();
    JFrame j1;
    JFrame j2;

    public FlightJLabel(JFrame parent1, JFrame parent2) {
	super();
	ImageIcon icon = new ImageIcon("Artwork/Fligh Plan.JPG");
	icon.setImage(icon.getImage().getScaledInstance(
		icon.getIconWidth() / 2, icon.getIconHeight() / 2,
		Image.SCALE_SMOOTH));
	this.setIcon(icon);
	setOpaque(false);
	setBounds(0, 0, getIcon().getIconWidth(), getIcon().getIconHeight());
	this.addMouseListener(this);
	this.j1 = parent1;
	this.j2 = parent2;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	GameHistory game = GameHistory.getGameHistory();
	if (game.getCurrentPlayer().getPlaneFlag() != 1) {
	    return;
	}
	int x = arg0.getX();
	int y = arg0.getY();
	moveToCity(x, y);

    }

    private int getIndex(City c) {
	String cname = c.getName();
	HashMap<String, PlaneCity> hash = this.fps.getPoints();
	for (String cn : hash.keySet()) {
	    if (cname.equals(cn)) {
		PlaneCity pc = hash.get(cn);
		return pc.getIndex();
	    }
	}
	return 0;
    }

    private int getCost(int i1, int i2) {
	if (i1 == i2) {

	    return 2;
	} else if ((i1 - i2 == 1) || (i2 - i1 == 1)) {
	    return 4;
	} else if ((i1 == 1 && i2 == 4) || (i2 == 1 && i1 == 4)
		|| (i1 == 3 && i2 == 6) || (i1 == 6 && i2 == 3)) {
	    return 4;
	} else {
	    return -1;
	}
    }

    public void moveToCity(int x, int y) {
	CVSPoints cps = CVSPoints.getPointsManager();
	GameHistory game = GameHistory.getGameHistory();
	Point p = new Point(x, y);
	PlaneCity c;

	if ((c = this.fps.getSelect(p)) == null) {
	    return;
	}
	System.out.println(c.getName());
	City c2 = cps.getPoints().get(c.getName());

	int index = this.getIndex(game.getCurrentCity());
	int index2 = this.getIndex(c2);
	int cost = this.getCost(index, index2);
	if (cost < 0 || cost > game.getRemainStep()) {
	    JOptionPane.showMessageDialog(this, "your points is not enough",
		    "Warning", JOptionPane.WARNING_MESSAGE);
	    this.j1.setEnabled(true);
	    return;
	}
	int flag = JOptionPane.showConfirmDialog(null,
		"You will Fly to " + c.getName(), "Care!",
		JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
	if (JOptionPane.YES_OPTION == flag) {

	    Quarter q = Quarter.getQuarter(c2.getQuarter());
	    q.getCurrentPiece().setLocation(c2.getP());
	    if (c2.getQuarter() != game.getCurrentCity().getQuarter()) {
		q.changePieceQuarter(game.getCurrentCity().getQuarter(),
			c2.getQuarter(), c2.getP().small(4), 1);
	    }
	    game.setCurrentPlayerCurrentCity(c2);
	    q.refreshQuarter();
	    q.repaint();
	    game.setRemainStep(game.getRemainStep() - cost);
	    if (game.getRemainStep() <= 0) {
		GameplayScreen gameplay = GameplayScreen.getGamePlay();
		gameplay.changePlayer();
	    }
	    game.getCurrentPlayer().setPlaneFlag(2);
	    this.j1.setEnabled(true);
	    this.j2.dispose();
	} else {
	    this.j1.setEnabled(true);
	    return;
	}

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }
}
