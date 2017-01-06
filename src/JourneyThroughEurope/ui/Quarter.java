package JourneyThroughEurope.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import JourneyThroughEurope.bean.Card;
import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.Point;
import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.Cards;
import JourneyThroughEurope.common.PropertiesManager;
import JourneyThroughEurope.common.SaveLoad;

public class Quarter extends JLabel implements MouseListener {
    static int quarterNum = 1;
    static String[] pic = new String[] { "AC14", "DF14", "AC58", "DF58" };
    static String[] color = new String[] { "red", "blue", "green", "black",
	    "white", "yellow" };
    HashMap<String, Card> cards = Cards.getCardsManager().getCards();
    private static Quarter singleton = null;
    HashMap<Integer, ArrayList<PieceLabel>> pieces;
    HashMap<Integer, ArrayList<JLabel>> flags;
    HashMap<Integer, ArrayList<JLabel>> marks;

    public Quarter(int qNum) {
	super();
	pieces = new HashMap<Integer, ArrayList<PieceLabel>>();
	flags = new HashMap<Integer, ArrayList<JLabel>>();
	marks = new HashMap<Integer, ArrayList<JLabel>>();

	ImageIcon icon = new ImageIcon("Artwork/gameplay_" + pic[qNum - 1]
		+ ".jpg");
	icon.setImage(icon.getImage().getScaledInstance(
		icon.getIconWidth() / 4, icon.getIconHeight() / 4,
		Image.SCALE_SMOOTH));
	this.setIcon(icon);
	Quarter.quarterNum = qNum;
	setOpaque(false);
	setBounds(300, 20, getIcon().getIconWidth(), getIcon().getIconHeight());
	this.addMouseListener(this);
    }

    public void changeImageQuarter(int qNum) {
	ImageIcon icon = new ImageIcon("Artwork/gameplay_" + pic[qNum - 1]
		+ ".jpg");
	icon.setImage(icon.getImage().getScaledInstance(
		icon.getIconWidth() / 4, icon.getIconHeight() / 4,
		Image.SCALE_SMOOTH));
	this.setIcon(icon);
	Quarter.quarterNum = qNum;
	refreshQuarter();
    }

    public void changePieceQuarter(int oldQuarter, int newQuarter, Point des,
	    int type) {
	int playNum = GameHistory.getGameHistory().getCurrentNum();
	ArrayList<PieceLabel> arrayP = pieces.get(oldQuarter);
	for (PieceLabel p : arrayP) {
	    if (p.getPlayerNum() == playNum) {
		arrayP.remove(p);
		if (this.pieces.containsKey(newQuarter)) {
		    this.pieces.get(newQuarter).add(p);
		} else {
		    ArrayList<PieceLabel> a = new ArrayList<PieceLabel>();
		    a.add(p);
		    this.pieces.put(newQuarter, a);
		}
		break;
	    }
	}
	if (type == 0) { // for animate move
	    if ((oldQuarter == 1 && newQuarter == 2)
		    || (oldQuarter == 3 && newQuarter == 4)) {
		this.getCurrentPiece().setLocation(0, des.getY());
	    } else if ((oldQuarter == 2 && newQuarter == 1)
		    || (oldQuarter == 4 && newQuarter == 3)) {
		this.getCurrentPiece().setLocation(this.getWidth(), des.getY());
	    } else if ((oldQuarter == 1 && newQuarter == 3)
		    || (oldQuarter == 2 && newQuarter == 4)) {
		this.getCurrentPiece().setLocation(des.getX(), 0);
	    } else if ((oldQuarter == 3 && newQuarter == 1)
		    || (oldQuarter == 4 && newQuarter == 2)) {
		this.getCurrentPiece()
			.setLocation(des.getX(), this.getHeight());
	    }
	} else {
	    this.getCurrentPiece().setLocation(des.getX(), des.getY());
	}
	refreshQuarter();
    }

    public static Quarter getQuarter(int qNum) {
	if (singleton == null) {
	    singleton = new Quarter(qNum);
	} else if (qNum != Quarter.quarterNum) {
	    singleton.changeImageQuarter(qNum);
	}
	return singleton;
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub

	int x = arg0.getX();
	int y = arg0.getY();
	moveToCity(x, y);

    }

    public void moveToCity(int x, int y) {
	Point clickedPoint = new Point(x, y);
	GameplayScreen gameplay = GameplayScreen.getGamePlay();
	GameHistory game = GameHistory.getGameHistory();
	City c = game.getCurrentPlayer().getNei(clickedPoint, "land");

	if (c != null) {
	    String or_city = game.getCurrentCity().getName();
	    if (game.getCurrentPlayer().isCompleted(c)) {
		return;
	    }
	    if (game.getCurrentPlayer().hasReached(c)) {
		this.getCurrentPiece().dealReached();
		return;
	    }
	    if (Quarter.quarterNum != game.getCurrentCity().getQuarter()) {
		changePieceQuarter(game.getCurrentCity().getQuarter(),
			Quarter.quarterNum, c.getP().small(4), 0);
	    }
	    game.getCurrentPlayer().addHistoryCities(c);
	    this.getCurrentPiece().Move(c);
	    game.setRemainStep(game.getRemainStep() - 1);
	    game.setCurrentPlayerCurrentCity(c);
	    gameplay.leftLabel.setText("Remain moves: " + game.getRemainStep()
		    + "");
	    this.repaint();

	    if (SaveLoad.getInfo(c.getName()) != null) {
		new TownInfoDialog(gameplay, c.getName(), SaveLoad.getInfo(c
			.getName()));
	    }
	    this.getCurrentPiece().dealDestination(c, or_city);

	    if (game.getCurrentPlayer().getReachableCitys("sea").size() == 0
		    && game.getCurrentPlayer().getReachableCitys("land").size() == 0) {
		game.getCurrentPlayer().setIsDie(1);
		gameplay.changePlayer();

	    }
	    String his = game.getCurrentPlayer().getName() + ";" + "move" + ";"
		    + or_city + ";" + c.getName() + ";" + game.getRemainStep();
	    try {
		SaveLoad.addHistory(his);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    if (game.getCurrentPlayer().isAllReached()) {
		gameplay.endGame(game.getCurrentPlayer().getName()
			+ " win! others Lose!");
	    }
	    HashMap<String, HashMap<String, String>> cardIns = this.cards.get(
		    c.getName()).getInsMap();
	    if (cardIns != null && cardIns.containsKey("plane")) {
		if (game.getRemainStep() >= 2
			&& game.getCurrentPlayer().getPlaneFlag() == 0) {
		    gameplay.showDialog("you can fly! choose proper city");
		    game.getCurrentPlayer().setPlaneFlag(1);
		    new FlightScreen(gameplay, game.getCurrentCity());

		} else {
		    gameplay.showDialog("steps is  not enough to fly! move on");
		}
	    }

	    if (game.getRemainStep() == 0) {
		if (game.getCurrentPlayer().getGetOneMoreDice() == 1) {
		    game.getCurrentPlayer().setGetOneMoreDice(0);
		    gameplay.oneMoreTurn();
		}
		if (game.getCurrentThrown() == 6) {
		    gameplay.oneMoreTurn();
		} else {
		    gameplay.changePlayer();
		}
	    }
	    return;
	}
	c = game.getCurrentPlayer().getNei(clickedPoint, "sea");
	if (c != null) {
	    if (game.getCurrentPlayer().getHarborWaited() == 0) {
		gameplay.showDialog("Habor City ,wait next Turn");
		game.setRemainStep(0);
		String his = game.getCurrentPlayer().getName() + ";"
			+ "sea Road" + ";  wait at habor;" + c.getName() + ";"
			+ game.getRemainStep();
		try {
		    SaveLoad.addHistory(his);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		game.getCurrentPlayer().setHarborWaited(1);
		gameplay.changePlayer();
	    } else {
		game.getCurrentPlayer().setHarborWaited(0);
		String or_city = game.getCurrentCity().getName();
		if (game.getCurrentPlayer().isCompleted(c)) {
		    return;
		}
		if (game.getCurrentPlayer().hasReached(c)) {
		    this.getCurrentPiece().dealReached();
		    return;
		}
		if (Quarter.quarterNum != game.getCurrentCity().getQuarter()) {
		    changePieceQuarter(game.getCurrentCity().getQuarter(),
			    Quarter.quarterNum, c.getP().small(4), 0);
		}
		game.getCurrentPlayer().addHistoryCities(c);
		this.getCurrentPiece().Move(c);
		game.setRemainStep(0);
		game.setCurrentPlayerCurrentCity(c);
		gameplay.leftLabel.setText("Remain moves: "
			+ game.getRemainStep() + "");
		this.repaint();
		if (SaveLoad.getInfo(c.getName()) != null) {
		    new TownInfoDialog(gameplay, c.getName(),
			    SaveLoad.getInfo(c.getName()));
		}
		this.getCurrentPiece().dealDestination(c, or_city);
		if (game.getCurrentPlayer().getReachableCitys("sea").size() == 0
			&& game.getCurrentPlayer().getReachableCitys("land")
				.size() == 0) {
		    game.getCurrentPlayer().setIsDie(1);
		    gameplay.changePlayer();

		}

		String his = game.getCurrentPlayer().getName() + ";"
			+ "sea Road" + ";" + or_city + ";" + c.getName() + ";"
			+ game.getRemainStep();
		try {
		    SaveLoad.addHistory(his);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		if (game.getCurrentPlayer().isAllReached()) {
		    gameplay.endGame(game.getCurrentPlayer().getName()
			    + " win! others Lose!");
		}
		HashMap<String, HashMap<String, String>> cardIns = this.cards
			.get(c.getName()).getInsMap();
		if (cardIns != null && cardIns.containsKey("plane")) {
		    if (game.getRemainStep() >= 2
			    && game.getCurrentPlayer().getPlaneFlag() == 0) {
			gameplay.showDialog("you can fly! choose proper city");
			new FlightScreen(gameplay, game.getCurrentCity());
		    } else {
			gameplay.showDialog("steps is  not enough to fly! move on");
		    }
		}
		if (game.getRemainStep() == 0) {
		    if (game.getCurrentPlayer().getGetOneMoreDice() == 1) {
			game.getCurrentPlayer().setGetOneMoreDice(0);
			gameplay.oneMoreTurn();
		    }
		    if (game.getCurrentThrown() == 6) {
			gameplay.oneMoreTurn();
		    } else {
			gameplay.changePlayer();
		    }
		}
		return;
	    }

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

    public void refreshQuarter() {
	this.removeAll();
	ArrayList<PieceLabel> pieces = this.pieces.get(quarterNum);
	if (pieces != null) {
	    for (PieceLabel piece : pieces) {
		this.add(piece);
	    }
	}
	ArrayList<JLabel> flags = this.flags.get(quarterNum);
	if (flags != null) {
	    for (JLabel flag : flags) {
		this.add(flag);
	    }
	}
	ArrayList<JLabel> marks = this.marks.get(quarterNum);
	if (marks != null) {
	    for (JLabel mark : marks) {
		this.add(mark);
	    }
	}
    }

    public PieceLabel getCurrentPiece() {
	int playNum = GameHistory.getGameHistory().getCurrentNum();
	for (int i : pieces.keySet()) {
	    ArrayList<PieceLabel> arrayP = pieces.get(i);
	    for (PieceLabel p : arrayP) {
		if (p.getPlayerNum() == playNum) {
		    return p;
		}
	    }
	}
	return null;
    }

    public void addFlag(JLabel flag, Point point, int quarter) {
	// TODO Auto-generated method stub
	flag.setLocation(point.getX() / 4, point.getY() / 4);
	if (this.flags.containsKey(quarter)) {
	    this.flags.get(quarter).add(flag);
	} else {
	    ArrayList<JLabel> a = new ArrayList<JLabel>();
	    a.add(flag);
	    this.flags.put(quarter, a);
	}
	this.refreshQuarter();
    }

    public void addPiece(PieceLabel jLabel, City c) {
	// TODO Auto-generated method stub
	jLabel.setLocation(c.getP());
	if (this.pieces.containsKey(c.getQuarter())) {
	    this.pieces.get(c.getQuarter()).add(jLabel);
	} else {
	    ArrayList<PieceLabel> a = new ArrayList<PieceLabel>();
	    a.add(jLabel);
	    this.pieces.put(c.getQuarter(), a);
	}
	this.refreshQuarter();
    }

    public void addMark(City c) {

	GameHistory game = GameHistory.getGameHistory();
	JLabel x = new JLabel("<html><h1><font color= \""
		+ color[game.getCurrentNum() - 1] + "\"> x</font><h1></html>");
	x.setSize(new Dimension(40, 40));
	int _x = c.getP().getX() / 4 - x.getWidth() / 2;
	if (_x < 0) {
	    _x = c.getP().getX() / 4 + x.getWidth() / 2;
	}
	int _y = c.getP().getY() / 4 - x.getHeight() / 2;
	if (_y < 0) {
	    _y = c.getP().getY() / 4 + x.getHeight() / 2;
	}
	x.setLocation(_x, _y);
	if (this.marks.containsKey(c.getQuarter())) {
	    this.marks.get(c.getQuarter()).add(x);
	} else {
	    ArrayList<JLabel> a = new ArrayList<JLabel>();
	    a.add(x);
	    this.marks.put(c.getQuarter(), a);
	}
	this.refreshQuarter();
    }

    public void drawNeiCity(String type, Graphics g) {
	GameHistory game = GameHistory.getGameHistory();
	City city = game.getCurrentCity();
	if (quarterNum != city.getQuarter()) {
	    return;
	}
	ArrayList<City> cities = game.getCurrentPlayer()
		.getReachableCitys(type);
	for (City c : cities) {
	    Point neiCityP = c.getP().small(4);
	    if (c.getQuarter() == city.getQuarter()) {
		g.drawLine(city.getP().getX() / 4, city.getP().getY() / 4,
			neiCityP.getX(), neiCityP.getY());
	    }
	}

    }

    public void paint(Graphics g) {
	super.paint(g);
	g.setColor(Color.red);
	drawNeiCity("sea", g);
	drawNeiCity("land", g);
    }

    public void move(String name) {
	// TODO Auto-generated method stub
	 CVSPoints ps =  CVSPoints.getPointsManager();
	 City c = ps.getPoints().get(name);
	 this.moveToCity(c.getP().small(4).getX(), c.getP().small(4).getY());
    }
}
