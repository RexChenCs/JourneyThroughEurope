package JourneyThroughEurope.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import JourneyThroughEurope.bean.City;
import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.Player;
import JourneyThroughEurope.common.CVSPoints;
import JourneyThroughEurope.common.SaveLoad;
import JourneyThroughEurope.bean.Point;

import java.util.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class GameplayScreen extends JFrame implements ActionListener {
    Quarter quarterImage;
    private GameHistory game;
    private JButton buttonHistory;
    private JButton buttonAbout;
    private JButton buttonFlight;
    private JButton buttonSave;
    private JLabel[][] cardsLabels;
    private JLabel cardsPlayerTip;
    private JButton[] quarters;
    private ImageIcon[] dies;
    private ImageIcon[] pieces;
    private ImageIcon[] flags;

    public JLabel leftLabel;
    private JLabel curPlayerLabel;
    private JLabel selectLabel;
    private JLabel curPieceLabel;
    private PieceLabel[] pieceLabel;
    private JLabel[] flagLabel;

    private JLabel curThrownLabel;
    private JLabel rollTips;
    static String[] color = new String[] { "red", "blue", "green", "black",
	    "white", "yellow" };
    private static GameplayScreen gamePlay = null;

    public static GameplayScreen getGamePlay(GameHistory game) {
	if (gamePlay == null) {
	    gamePlay = new GameplayScreen(game);
	    return gamePlay;
	} else {
	    return gamePlay;
	}
    }

    public static GameplayScreen getGamePlay() {
	return gamePlay;

    }

    private GameplayScreen(final GameHistory game) {
	this.setResizable(false);
	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	this.setLayout(null);
	this.getContentPane().setBackground(Color.white);
	this.game = game;
	// prepare players info : random generate cards....
	preparePlayers();
	// initials
	InitalPane();
	// animate distribute cards from first player
	if (game.getCurrentPlayer().getFirstFlag() == 1) {
	    cardsAnimate(1);
	} else {
	    for (int i = 0; i < game.getCurrentPlayer().getDesCities().length; i++) {
		if (game.getCurrentPlayer().getReachedNum(i) == 0) {
		    this.cardsLabels[game.getCurrentNum() - 1][i]
			    .setVisible(true);
		    this.cardsLabels[game.getCurrentNum() - 1][i].setLocation(
			    0, this.cardsLabels[game.getCurrentNum() - 1][i]
				    .getY());
		}
	    }
	}
	if (game.getCurrentThrown() != 0) {
	    curThrownLabel.setIcon(this.dies[game.getCurrentThrown() - 1]);
	    rollTips.setText("<html><h2><font color= \""
		    + color[this.game.getCurrentNum() - 1] + "\">Rolled "
		    + game.getCurrentThrown() + "</font><h2></html>");
	    leftLabel.setText("Remain moves: " + game.getRemainStep() + "");
	} else {
	    randDie();
	}

	this.setSize(1100, 720);
	// Form display center
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);
	for (int i = 0; i < game.getPlayers().length; i++) {
	    if (game.getPlayers()[i].getFirstFlag() == 0) {
		if (i != game.getCurrentNum() - 1) {
		    this.pieceLabel[i].removeListner();
		}
		this.quarterImage.addPiece(this.pieceLabel[i],
			game.getPlayers()[i].getCurrent());
		this.quarterImage.addFlag(this.flagLabel[i],
			game.getPlayers()[i].getCurrent().getP(),
			game.getPlayers()[i].getCurrent().getQuarter());
		City[] cities = game.getPlayers()[i].getDesCities();
		for (City c : cities) {
		    this.quarterImage.addMark(c);
		}
	    }
	}
	if (game.getCurrentPlayer().getType() == Player.COMPUTER) {
	    Timer timer = new Timer();
	    timer.schedule(new TimerTask() {
		public void run() {
		    game.getCurrentPlayer().setRoute();
		    computerMove();
		    this.cancel();
		}
	    }, 5 * 1000, 10);

	}
	this.getContentPane().setBackground(new Color(200, 200, 200));
	this.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		System.exit(-1);
	    }

	});
	this.setVisible(true);
    }

    private void InitalPane() {
	cardsPlayerTip = new JLabel();
	cardsPlayerTip.setText("<html><h3>" + game.getCurrentPlayer().getName()
		+ "<h3></html>");
	cardsPlayerTip.setBounds(0, 20, 300, 40);
	cardsPlayerTip.setOpaque(true);
	cardsPlayerTip.setBackground(Color.gray);
	this.add(cardsPlayerTip);
	curPlayerLabel = new JLabel("<html><h2><font color= \""
		+ color[this.game.getCurrentNum() - 1] + "\">"
		+ game.getCurrentPlayer().getName() + " turn</font><h2></html>");
	curPlayerLabel.setBounds(880, 20, 180, 50);
	this.add(curPlayerLabel);

	curPieceLabel = new JLabel();
	curPieceLabel.setSize(new Dimension(50, 40));
	curPieceLabel.setIcon(this.pieces[this.game.getCurrentNum() - 1]);
	curPieceLabel.setLocation(1000, 20);
	this.add(curPieceLabel);

	rollTips = new JLabel("<html><h2><font color= \""
		+ color[this.game.getCurrentNum() - 1]
		+ "\">Rolled</font><h2></html>");
	rollTips.setBounds(880, 55, 120, 30);
	this.add(rollTips);

	selectLabel = new JLabel("<html><h2><font color= \""
		+ color[this.game.getCurrentNum() - 1]
		+ "\"> Select city</font><h2></html>");
	selectLabel.setBounds(880, 80, 120, 30);
	this.add(selectLabel);

	curThrownLabel = new JLabel();
	curThrownLabel.setSize(new Dimension(120, 120));
	curThrownLabel.setLocation(900, 130);
	this.add(curThrownLabel);

	leftLabel = new JLabel();
	leftLabel.setSize(new Dimension(100, 10));
	leftLabel.setLocation(960, 10);
	this.add(leftLabel);

	this.quarters = new JButton[4];
	JLabel AC = new JLabel("<html><h2>A-C<h2></html>");
	AC.setBounds(910, 270, 40, 20);
	this.add(AC);
	JLabel DF = new JLabel("<html><h2>D-F<h2></html>");
	DF.setBounds(970, 270, 40, 20);
	this.add(DF);
	JLabel _14 = new JLabel("<html><h2>1-4<h2></html>");
	_14.setBounds(860, 320, 40, 20);
	this.add(_14);
	JLabel _58 = new JLabel("<html><h2>5-8<h2></html>");
	_58.setBounds(860, 380, 40, 20);
	this.add(_58);

	this.quarters[0] = new JButton();
	this.quarters[0].setBounds(900, 300, 60, 60);
	this.quarters[0].setBackground(Color.orange);
	Border blue = new LineBorder(new Color(100, 160, 250), 4);
	this.quarters[0].setBorder(blue);

	this.quarters[1] = new JButton();
	this.quarters[1].setBounds(960, 300, 60, 60);
	this.quarters[1].setBackground(Color.yellow);
	Border red = new LineBorder(Color.red, 4);
	this.quarters[1].setBorder(red);

	this.quarters[2] = new JButton();
	this.quarters[2].setBounds(900, 360, 60, 60);
	this.quarters[2].setBackground(new Color(100, 250, 100));
	Border green = new LineBorder(Color.green, 4);
	this.quarters[2].setBorder(green);

	this.quarters[3] = new JButton();
	this.quarters[3].setBounds(960, 360, 60, 60);
	this.quarters[3].setBackground(new Color(250, 150, 200));
	Border purple = new LineBorder(new Color(180, 80, 250), 4);
	this.quarters[3].setBorder(purple);

	for (int i = 0; i < 4; i++) {
	    this.quarters[i].addActionListener(this);
	    this.add(this.quarters[i]);
	}

	buttonHistory = new JButton(
		"<html><h2><font color= \"red\">Game History</font><h2></html>");
	buttonAbout = new JButton(
		"<html><h2><font color= \"red\">About JTE</font><h2></html>");
	buttonFlight = new JButton(
		"<html><h2><font color= \"red\">Flight Plan</font><h2></html>");
	buttonSave = new JButton(
		"<html><h2><font color= \"red\">Save</font><h2></html>");

	buttonHistory.setBackground(new Color(100, 180, 240));
	buttonHistory.setBounds(850, 510, 180, 40);
	this.add(buttonHistory);

	buttonAbout.setBounds(850, 560, 140, 40);
	buttonAbout.setBackground(new Color(100, 180, 240));
	this.add(buttonAbout);

	buttonFlight.setBounds(850, 460, 180, 40);
	buttonFlight.setBackground(new Color(100, 180, 240));
	this.add(buttonFlight);

	buttonSave.setBounds(850, 610, 140, 40);
	buttonSave.setBackground(new Color(100, 180, 240));
	this.add(buttonSave);

	// add actionListener
	buttonSave.addActionListener(this);
	buttonFlight.addActionListener(this);
	this.buttonAbout.addActionListener(this);
	this.buttonHistory.addActionListener(this);
	Border blueBorder = new LineBorder(new Color(0, 90, 250), 4);
	buttonSave.setBorder(blueBorder);
	buttonFlight.setBorder(blueBorder);
	buttonHistory.setBorder(blueBorder);
	buttonAbout.setBorder(blueBorder);
	int currentQuarter = game.getCurrentCity().getQuarter();
	quarterImage = Quarter.getQuarter(currentQuarter);
	this.add(quarterImage);
    }

    // Initail players info
    private void preparePlayers() {
	dies = new ImageIcon[6];
	for (int i = 0; i < 6; i++) {
	    dies[i] = new ImageIcon("Artwork/die_" + (i + 1) + ".jpg");
	    dies[i].setImage(dies[i].getImage().getScaledInstance(120, 120,
		    Image.SCALE_DEFAULT));
	}
	pieces = new ImageIcon[game.getPlayerNum()];
	this.pieceLabel = new PieceLabel[game.getPlayerNum()];
	for (int i = 0; i < game.getPlayerNum(); i++) {
	    pieces[i] = new ImageIcon("Artwork/piece_" + color[i] + ".png");
	    pieces[i].setImage(pieces[i].getImage().getScaledInstance(50, 40,
		    Image.SCALE_SMOOTH));
	    this.pieceLabel[i] = new PieceLabel((i + 1));
	}
	flags = new ImageIcon[game.getPlayerNum()];
	this.flagLabel = new JLabel[game.getPlayerNum()];
	for (int i = 0; i < game.getPlayerNum(); i++) {
	    flags[i] = new ImageIcon("Artwork/flag_" + color[i] + ".png");
	    flags[i].setImage(flags[i].getImage().getScaledInstance(50, 50,
		    Image.SCALE_SMOOTH));
	    this.flagLabel[i] = new JLabel();
	    this.flagLabel[i].setSize(new Dimension(50, 50));
	    this.flagLabel[i].setIcon(flags[i]);
	}
	String[] cityColors = new String[] { "red", "green", "yellow" };
	if (game.getIsNewGame() == 1) {
	    for (int i = 0; i < game.getPlayerNum(); i++) {
		HashMap<String, City> hashmap = CVSPoints.getPointsManager()
			.getPoints(cityColors[i % 3]);
		ArrayList<City> list = new ArrayList<City>(hashmap.values());
		City first;
		while (true) {
		    int pos = (int) Math.floor(Math.random() * list.size());
		    first = list.get(pos);
		    if (first.getSelected() == 0) {
			first.setSelected(1);
			game.getPlayers()[i].setCurrent(City.copy(first));
			break;
		    }
		}
		game.getPlayers()[i].setFirst(first);
		City[] cities = new City[3];
		cities[0] = first;
		hashmap = CVSPoints.getPointsManager().getPoints(
			cityColors[(i % 3 + 1) % 3]);
		list = new ArrayList<City>(hashmap.values());
		while (true) {
		    int pos = (int) Math.floor(Math.random() * list.size());
		    cities[1] = list.get(pos);
		    if (cities[1].getSelected() == 0) {
			cities[1].setSelected(1);
			break;
		    }
		}
		hashmap = CVSPoints.getPointsManager().getPoints(
			cityColors[(i % 3 + 2) % 3]);
		list = new ArrayList<City>(hashmap.values());
		while (true) {
		    int pos = (int) Math.floor(Math.random() * list.size());
		    cities[2] = list.get(pos);
		    if (cities[2].getSelected() == 0) {
			cities[2].setSelected(1);
			break;
		    }
		}
		game.getPlayers()[i].setDesCities(cities);
		game.getPlayers()[i].addHistoryCities(first);
	    }
	}
	for (int i = 0; i < game.getPlayerNum(); i++) {
	    if (game.getPlayers()[i].getType() == Player.COMPUTER) {
		game.getPlayers()[i].setRoute();
	    }
	}
	InitialPlayerCards();
    }

    private void InitialPlayerCards() {
	int citiesNum = 3;
	int playersNum = game.getPlayerNum();
	cardsLabels = new JLabel[playersNum][citiesNum];
	for (int i = 0; i < playersNum; i++) {
	    for (int j = citiesNum - 1; j >= 0; j--) {
		String path = "Artwork/"
			+ game.getPlayers()[i].getDesCities()[j].getColor()
			+ "/"
			+ game.getPlayers()[i].getDesCities()[j].getName()
			+ ".jpg";
		ImageIcon icon = new ImageIcon(path);
		if (!(icon.getImageLoadStatus() == MediaTracker.COMPLETE)) {
		    path = "Artwork/"
			    + game.getPlayers()[i].getDesCities()[j].getColor()
			    + "/"
			    + game.getPlayers()[i].getDesCities()[j].getName()
			    + ".png";
		    icon = new ImageIcon(path);
		}
		icon.setImage(icon.getImage().getScaledInstance(280, 350,
			Image.SCALE_SMOOTH));
		cardsLabels[i][j] = new JLabel();
		cardsLabels[i][j].setIcon(icon);
		cardsLabels[i][j].setBounds(500, 70 + j * 70, 280, 350);
		this.add(cardsLabels[i][j]);
		cardsLabels[i][j].setVisible(false);
	    }
	}
    }

    private void cardsAnimate(int playerNum) {
	this.cardsPlayerTip.setText("<html><h3> "
		+ game.getCurrentPlayer().getName() + "<h3></html>");
	City[] cities = game.getCurrentPlayer().getDesCities();
	for (City c : cities) {
	    this.quarterImage.addMark(c);
	}
	Timer timer = new Timer();
	timer.schedule(new CardsMove(cardsLabels[playerNum - 1]), 1000, 200);
	game.getCurrentPlayer().setFirstFlag(0);
    }

    private void randDie() {
	Random rand = new Random();
	int randnum = rand.nextInt(6) + 1;
	game.setRemainStep(randnum);
	game.setCurrentThrown(randnum);
	curThrownLabel.setIcon(this.dies[randnum - 1]);
	leftLabel.setText("Remain moves: " + game.getRemainStep() + "");
	rollTips.setText("<html><h2><font color= \""
		+ color[this.game.getCurrentNum() - 1] + "\">Rolled "
		+ game.getCurrentThrown() + "</font><h2></html>");
    }

    public void showDialog(String message) {
	JOptionPane.showMessageDialog(this, message, "Warning",
		JOptionPane.WARNING_MESSAGE);
    }

    public void oneMoreTurn() {
	JOptionPane.showMessageDialog(this, "one More Turn!", "Warning",
		JOptionPane.WARNING_MESSAGE);
	randDie();
    }

    public void preInstruction() {
	if (game.getCurrentPlayer().getAnotherGoes() > 0) {
	    this.showDialog("get " + game.getCurrentPlayer().getAnotherGoes()
		    + " of last turn!");
	    game.setRemainStep(game.getRemainStep()
		    + game.getCurrentPlayer().getAnotherGoes());
	    game.getCurrentPlayer().setAnotherGoes(0);
	}

    }

    public void changePlayer() {
	int i;
	int dieNum = 0;
	Player alive = null;
	for (i = 0; i < game.getPlayerNum(); i++) {
	    if (game.getPlayers()[i].getIsDie() == 1) {
		dieNum++;
	    } else {
		alive = game.getPlayers()[i];
	    }
	}
	if (dieNum < game.getPlayerNum() - 1) {
	    nextPlayer();
	    while (game.getCurrentPlayer().getIsDie() == 1) {
		JOptionPane.showMessageDialog(this, "Player:"
			+ game.getCurrentPlayer().getName()
			+ " die! Change next player", "Warning",
			JOptionPane.WARNING_MESSAGE);
		nextPlayer();
	    }
	    preInstruction();
	    if (game.getCurrentPlayer().getType() == Player.COMPUTER) {
		computerMove();
	    }
	} else {
	    if (dieNum == game.getPlayerNum() - 1) {

		showDialog(alive.getName()
			+ " are the only one survivied , win !");
	    } else {
		showDialog("all people die!");
	    }
	    new Splash();
	    this.dispose();
	}

    }

    public void computerMove() {
	Timer timer = new Timer();
	timer.schedule(new ComputerMove(), 3 * 1000, 1000 * 3);
    }

    public void nextPlayer() {
	/*
	 * JOptionPane.showMessageDialog(this, "Player Change!", "Warning",
	 * JOptionPane.WARNING_MESSAGE);
	 */
	this.pieceLabel[game.getCurrentNum() - 1].removeListner();
	this.game.setCurrentNum((game.getCurrentNum()) % game.getPlayerNum()
		+ 1);

	randDie();
	curThrownLabel.setIcon(this.dies[game.getCurrentThrown() - 1]);
	curPieceLabel.setIcon(this.pieces[this.game.getCurrentNum() - 1]);

	curPlayerLabel
		.setText("<html><h2><font color= \""
			+ color[this.game.getCurrentNum() - 1] + "\"> "
			+ game.getCurrentPlayer().getName()
			+ " turn</font><h2></html>");
	selectLabel.setText("<html><h2><font color= \""
		+ color[this.game.getCurrentNum() - 1]
		+ "\"> Select city</font><h2></html>");

	int flag = 0;
	if (game.getCurrentPlayer().getFirstFlag() == 1) {
	    this.quarterImage.addPiece(
		    this.pieceLabel[game.getCurrentNum() - 1],
		    game.getCurrentCity());
	    this.quarterImage.addFlag(this.flagLabel[game.getCurrentNum() - 1],
		    game.getCurrentCity().getP(), game.getCurrentCity()
			    .getQuarter());
	    this.game.getCurrentPlayer().setFirstFlag(0);
	    flag = 1;
	} else {
	    this.pieceLabel[game.getCurrentNum() - 1].addListner();
	}
	changeQuarter(game.getCurrentCity().getQuarter());
	this.quarterImage.repaint();
	this.changeCards(this.game.getCurrentNum(), flag);
    }

    public void changeCards(int curPlayer, int flag) {
	cardsPlayerTip.setText("<html><h3>  "
		+ game.getCurrentPlayer().getName() + "<h3></html>");
	int lastPlayer = curPlayer - 1;
	if (lastPlayer <= 0) {
	    lastPlayer = lastPlayer + game.getPlayerNum();
	}
	for (int i = 0; i < game.getPlayers()[lastPlayer - 1].getDesCities().length; i++) {
	    this.cardsLabels[lastPlayer - 1][i].setVisible(false);
	}
	if (flag == 1) {
	    cardsAnimate(curPlayer);
	} else {
	    for (int i = 0; i < game.getPlayers()[curPlayer - 1].getDesCities().length; i++) {
		if (game.getCurrentPlayer().getReachedNum(i) == 0) {
		    this.cardsLabels[curPlayer - 1][i].setVisible(true);
		    this.cardsLabels[game.getCurrentNum() - 1][i].setLocation(
			    0, this.cardsLabels[game.getCurrentNum() - 1][i]
				    .getY());
		}
	    }
	}
    }

    public void endGame(String message) {
	this.showDialog(message);
	new Splash();
	this.dispose();
    }

    public void unSeeCards(int index) {
	this.cardsLabels[game.getCurrentNum() - 1][index].setVisible(false);
    }

    private void changeQuarter(int quarter) {
	if (quarter == Quarter.quarterNum) {
	    return;
	}
	this.quarterImage = Quarter.getQuarter(quarter);
	this.quarterImage.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == this.buttonAbout) {
	    new AboutScreen(this);
	    this.setVisible(false);
	} else if (arg0.getSource() == this.buttonHistory) {
	    new HistoryScreen(this);
	    this.setVisible(false);
	} else if (arg0.getSource() == this.quarters[0]) {
	    changeQuarter(1);
	} else if (arg0.getSource() == this.quarters[1]) {
	    changeQuarter(2);
	} else if (arg0.getSource() == this.quarters[2]) {
	    changeQuarter(3);
	} else if (arg0.getSource() == this.quarters[3]) {
	    this.changeQuarter(4);
	} else if (arg0.getSource() == this.buttonSave) {
	    try {
		SaveLoad.saveGame();
		JOptionPane.showMessageDialog(this, "Save Success!", "Warning",
			JOptionPane.WARNING_MESSAGE);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(this, "Save Failed!", "Warning",
			JOptionPane.WARNING_MESSAGE);
		e.printStackTrace();
	    }

	} else if (arg0.getSource() == this.buttonFlight) {
	    new FlightScreen(this, null);
	    this.setEnabled(false);
	}

    }

}
