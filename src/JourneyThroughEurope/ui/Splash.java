package JourneyThroughEurope.ui;

import javax.swing.*;

import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.common.SaveLoad;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class Splash extends JFrame implements ActionListener {
    Container contentpane;
    myPanel mypanel;
    private JButton buttonStart;
    private JButton buttonLoad;
    private JButton buttonQuit;
    private JButton buttonAbout;

    public Splash() {
	this.setResizable(false);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	contentpane = (JPanel) getContentPane();
	mypanel = new myPanel();
	contentpane.add(mypanel);

	// initials
	buttonStart = new JButton();
	buttonLoad = new JButton();
	buttonAbout = new JButton();
	buttonQuit = new JButton();

	// set icon
	Icon iconStart = new ImageIcon("button/Start.jpg");
	Icon iconLoad = new ImageIcon("button/Load.jpg");
	Icon iconAbout = new ImageIcon("button/About.jpg");
	Icon iconQuit = new ImageIcon("button/Quit.jpg");
	buttonStart.setIcon(iconStart);
	buttonLoad.setIcon(iconLoad);
	buttonAbout.setIcon(iconAbout);
	buttonQuit.setIcon(iconQuit);

	// show buttons
	buttonStart.setBounds(390, 245, 120, 55);
	mypanel.add(buttonStart);
	buttonLoad.setBounds(390, 300, 120, 55);
	mypanel.add(buttonLoad);
	buttonAbout.setBounds(390, 355, 120, 55);
	mypanel.add(buttonAbout);
	buttonQuit.setBounds(390, 410, 120, 55);
	mypanel.add(buttonQuit);

	// add actionListener
	this.buttonAbout.addActionListener(this);
	this.buttonLoad.addActionListener(this);
	this.buttonQuit.addActionListener(this);
	this.buttonStart.addActionListener(this);

	File file = new File("data/load.txt");
	if (!file.isFile() || !file.exists()) {
	    this.buttonLoad.setEnabled(false);
	}
	this.setSize(900, 500);

	// Form display center
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);
	this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	if (arg0.getSource() == this.buttonQuit) {
	    System.exit(-1);
	} else if (arg0.getSource() == this.buttonStart) {
	    SaveLoad.clearHistoy();
	    SaveLoad.clearLoad();
	    new SetUserFrame();
	    this.dispose();
	} else if (arg0.getSource() == this.buttonAbout) {
	    new AboutScreen(this);
	    this.setVisible(false);
	} else if (arg0.getSource() == this.buttonLoad) {
	    try {
		SaveLoad.LoadGame();
		GameHistory game = GameHistory.getGameHistory();
		GameplayScreen.getGamePlay(game);
		this.dispose();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(this, "Load failed!", "Warning",
			JOptionPane.WARNING_MESSAGE);
	    }
	}

    }
}

@SuppressWarnings("serial")
class myPanel extends JPanel {
    ImageIcon myimage;

    myPanel() {
	this.setLayout(null);
	myimage = new ImageIcon("button/Background.jpg");
    }

    public void paintComponent(Graphics g) {
	g.drawImage(myimage.getImage(), 0, 0, 900, 480, null);
    }
}