package JourneyThroughEurope.ui;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import JourneyThroughEurope.bean.Card;
import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.Instruction;
import JourneyThroughEurope.bean.Player;
import JourneyThroughEurope.bean.Point;
import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.Cards;
import JourneyThroughEurope.common.Instructions;
import JourneyThroughEurope.common.PropertiesManager;
import JourneyThroughEurope.common.SaveLoad;

public class PieceLabel extends JLabel {
    int x, y;
    static String[] color = new String[] { "red", "blue", "green", "black",
	    "white", "yellow" };
    int playerNum;
    HashMap<String, Card> cards = Cards.getCardsManager().getCards();
    MouseAdapter dag = new MouseAdapter() {
	@Override
	public void mouseDragged(MouseEvent e) {
	    setLocation(e.getX() + getX() - x, e.getY() + getY() - y);
	    super.mouseDragged(e);
	}
    };
    MouseAdapter release = new MouseAdapter() {
	@Override
	public void mousePressed(MouseEvent e) {
	    x = e.getX();
	    y = e.getY();
	    super.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	    movetoCity();
	}
    };

    public void movetoCity() {
	GameHistory game = GameHistory.getGameHistory();
	GameplayScreen gameplay = GameplayScreen.getGamePlay();
	Point poi = new Point(getX(), getY());
	City c = game.getCurrentPlayer().getNei(poi, "land");
	if (c != null) {
	    if (game.getCurrentPlayer().hasReached(c)) {
		dealReached();
		return;
	    }
	    String or_city = game.getCurrentCity().getName();
	    game.setCurrentPlayerCurrentCity(c);
	    setLocation(c.getP());
	    game.getCurrentPlayer().setCurrent(c);
	    game.setRemainStep(game.getRemainStep() - 1);
	    game.getCurrentPlayer().addHistoryCities(c);

	    gameplay.leftLabel.setText("Remain moves: " + game.getRemainStep()
		    + "");
	    Quarter q = Quarter.getQuarter(game.getCurrentCity().getQuarter());
	    q.repaint();
	    if (SaveLoad.getInfo(c.getName()) != null) {
		new TownInfoDialog(gameplay, c.getName(), SaveLoad.getInfo(c
			.getName()));
	    }
	    dealDestination(c, or_city);
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

	    if (game.getRemainStep() <= 0) {
		if (game.getCurrentPlayer().getGetOneMoreDice() == 1) {
		    game.getCurrentPlayer().setGetOneMoreDice(0);
		    gameplay.oneMoreTurn();
		}
		if (game.getCurrentThrown() == 6) {
		    gameplay.oneMoreTurn();
		} else {
		    gameplay.changePlayer();
		}
		return;
	    }
	}
	c = game.getCurrentPlayer().getNei(poi, "sea");
	if (c != null) {
	    if (game.getCurrentPlayer().hasReached(c)) {
		dealReached();
		return;
	    }
	    if (game.getCurrentPlayer().getHarborWaited() == 0) {
		gameplay.showDialog("Habor City ,wait next Turn");
		setLocation(game.getCurrentCity().getP());
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
		setLocation(game.getCurrentCity().getP());
		game.getCurrentPlayer().setHarborWaited(1);
		gameplay.changePlayer();
	    } else {
		game.getCurrentPlayer().setHarborWaited(0);
		String or_city = game.getCurrentCity().getName();
		game.setCurrentPlayerCurrentCity(c);
		setLocation(c.getP());
		game.getCurrentPlayer().setCurrent(c);
		game.setRemainStep(0);
		game.getCurrentPlayer().addHistoryCities(c);

		gameplay.leftLabel.setText("Remain moves: "
			+ game.getRemainStep() + "");
		Quarter q = Quarter.getQuarter(game.getCurrentCity()
			.getQuarter());
		q.repaint();
		if (SaveLoad.getInfo(c.getName()) != null) {
		    new TownInfoDialog(gameplay, c.getName(),
			    SaveLoad.getInfo(c.getName()));
		}
		dealDestination(c, or_city);
		if (game.getCurrentPlayer().getReachableCitys("sea").size() == 0
			&& game.getCurrentPlayer().getReachableCitys("land")
				.size() == 0) {
		    game.getCurrentPlayer().setIsDie(1);
		    gameplay.changePlayer();

		}
		String his = game.getCurrentPlayer().getName() + ";"
			+ "sea road" + ";" + or_city + ";" + c.getName() + ";"
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

	}
	setLocation(game.getCurrentCity().getP());
    }

    public void dealReached() {
	GameHistory game = GameHistory.getGameHistory();
	JOptionPane.showMessageDialog(this,
		"You have reached this city before!", "Warning",
		JOptionPane.WARNING_MESSAGE);
	setLocation(game.getCurrentCity().getP());
    }

    public void dealDestination(City c, String or_city) {
	GameHistory game = GameHistory.getGameHistory();
	int curNum = game.getCurrentNum();
	Player curPlayer = GameHistory.getGameHistory().getPlayers()[curNum - 1];
	City[] destinations = curPlayer.getDesCities();
	for (int i = 0; i < destinations.length; i++) {
	    if (c.getName().equals(destinations[i].getName())) {
		GameplayScreen gameplay = GameplayScreen.getGamePlay();
		curPlayer.setReachedNum(i);
		if (this.cards.get(c.getName()).getInsMap() != null) {
		    instructionDealer(this.cards.get(c.getName()));
		    JOptionPane
			    .showMessageDialog(
				    this,
				    "You are at one of your Destination City! Give Back your card to dealer!",
				    "Warning", JOptionPane.WARNING_MESSAGE);
		    gameplay.unSeeCards(i);
		    destinations[i].setSelected(0);
		    return;
		}
		JOptionPane
			.showMessageDialog(
				this,
				"You are at one of your Destination City! Give Back your card to dealer!",
				"Warning", JOptionPane.WARNING_MESSAGE);
		gameplay.unSeeCards(i);
		destinations[i].setSelected(0);
		String his = game.getCurrentPlayer().getName() + ";"
			+ "des_move" + ";" + or_city + ";" + c.getName() + ";"
			+ 0;
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
		gameplay.changePlayer();
	    }
	}
    }

    public void instructionDealer(Card c) {
	CVSPoints cityPoints = CVSPoints.getPointsManager();
	HashMap<String, Instruction> ins = Instructions
		.getInstructionsManager().getInstructions();
	GameHistory game = GameHistory.getGameHistory();
	GameplayScreen gameplay = GameplayScreen.getGamePlay();
	new DistructionDialog(gameplay, game.getCurrentCity().getName(), game
		.getCurrentCity().getColor());
	for (String type : c.getInsMap().keySet()) {
	    if (type.equals("miss")) {
		int curRemainStep = game.getRemainStep() - 1;
		if (curRemainStep < 0) {
		    curRemainStep = 0;
		}
		game.setRemainStep(curRemainStep);
	    } else if (type.equals("trans")) {
		String cityName = c.getInsMap().get("trans").get("place");
		String cityColor = c.getInsMap().get("trans").get("color");
		City t_des = cityPoints.getPoints(cityColor).get(cityName);
		/*
		 * JOptionPane.showMessageDialog(this, "trans to " + cityName,
		 * "Warning", JOptionPane.WARNING_MESSAGE);
		 */
		Quarter q = Quarter.getQuarter(t_des.getQuarter());
		q.getCurrentPiece().setLocation(t_des.getP());
		if (t_des.getQuarter() != game.getCurrentCity().getQuarter()) {
		    q.changePieceQuarter(game.getCurrentCity().getQuarter(),
			    t_des.getQuarter(), t_des.getP().small(4), 1);
		}
		game.setCurrentPlayerCurrentCity(t_des);
		q.refreshQuarter();
		q.repaint();
		game.setRemainStep(0);
	    } else if (type.equals("move")) {
		int anotherMove = Integer.parseInt(c.getInsMap().get("move")
			.get("cities"));
		String whenTime = c.getInsMap().get("move").get("when");
		/*
		 * JOptionPane.showMessageDialog(this, "you will move " +
		 * anotherMove + " cities further in " + whenTime + " turn!",
		 * "Warning", JOptionPane.WARNING_MESSAGE);
		 */
		if (whenTime.equals("this")) {
		    game.setRemainStep(game.getRemainStep() + anotherMove);
		} else {
		    game.getCurrentPlayer().setAnotherGoes(anotherMove);
		    game.setRemainStep(0);
		}

	    } else if (type.equals("dice")) {
		String whenTime = c.getInsMap().get("dice").get("when");
		/*
		 * JOptionPane.showMessageDialog(this,
		 * "you will get another dice in " + whenTime + " turn!",
		 * "Warning", JOptionPane.WARNING_MESSAGE);
		 */
		if (whenTime.equals("this")) {
		    gameplay.oneMoreTurn();
		} else {
		    game.getCurrentPlayer().setGetOneMoreDice(1);
		    game.setRemainStep(0);
		}
	    } else if (type.equals("card")) {
		String color = c.getInsMap().get("card").get("color");

		HashMap<String, City> cities = cityPoints.getPoints(color);
		for (String city : cities.keySet()) {
		    if (cities.get(city).getSelected() == 0) {
			JOptionPane
				.showMessageDialog(this, "you will add city: "
					+ city + " to your route", "Warning",
					JOptionPane.WARNING_MESSAGE);
			game.getCurrentPlayer().addHistoryCities(
				cities.get(city));
			break;
		    }
		}
	    } else if (type.equals("destination")) {
		City t_des = game.getCurrentPlayer().getAnotherDes(c.getName());
		Quarter q = Quarter.getQuarter(t_des.getQuarter());
		if (t_des.getQuarter() != game.getCurrentCity().getQuarter()) {
		    q.changePieceQuarter(game.getCurrentCity().getQuarter(),
			    t_des.getQuarter(), t_des.getP().small(4), 1);
		}
		q.refreshQuarter();
		q.repaint();
		game.setRemainStep(0);
	    } else if (type.equals("limit")) {

	    }/*
	      * else if (type.equals("plane")) { if (game.getRemainStep() > 2 &&
	      * game.getCurrentPlayer().getPlaneFlag() == 0) {
	      * gameplay.showDialog("you can fly! choose proper city");
	      * game.getCurrentPlayer().setPlaneFlag(1); new
	      * FlightScreen(gameplay, game.getCurrentCity()); } else {
	      * gameplay.showDialog("steps is  not enough to fly! move on"); } }
	      */

	}
    }

    /**
     * @return the currentPlayerNum
     */
    public int getPlayerNum() {
	return playerNum;
    }

    /**
     * @param currentPlayerNum
     *            the currentPlayerNum to set
     */
    public void setPlayerNum(int currentPlayerNum) {
	this.playerNum = currentPlayerNum;
    }

    public void setPoint(Point p) {
	this.x = p.getX();
	this.y = p.getY();
    }

    public void setPoint(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public void setLocation(Point point) {
	Point truePoint = point.small(4);
	int x = truePoint.getX() - this.getWidth() / 2;
	if (x < 0) {
	    x = truePoint.getX() + this.getWidth() / 2;
	}
	int y = truePoint.getY() - this.getHeight() / 2;
	if (y < 0) {
	    y = truePoint.getY() + this.getHeight() / 2;
	}
	this.setLocation(x, y);
    }

    public void removeListner() {
	removeMouseListener(this.release);
	this.removeMouseMotionListener(this.dag);
    }

    public void addListner() {
	this.addMouseListener(this.release);
	this.addMouseMotionListener(this.dag);
    }

    public PieceLabel(int PlayerNum) {
	super();
	this.playerNum = PlayerNum;
	ImageIcon icon = new ImageIcon("Artwork/piece_" + color[PlayerNum - 1]
		+ ".png");
	icon.setImage(icon.getImage().getScaledInstance(
		icon.getIconWidth() / 6, icon.getIconHeight() / 6,
		Image.SCALE_SMOOTH));
	this.setIcon(icon);
	setOpaque(false);
	setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
	addListner();

    }

    public void Move(City des) {
	Timer timer = new Timer();
	timer.schedule(new PieceMove(des, this), 0, 1);
    }

}
