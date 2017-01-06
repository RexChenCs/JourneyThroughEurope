package JourneyThroughEurope.ui;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import JourneyThroughEurope.bean.City;

public class FlightScreen extends JFrame {
    FlightJLabel fj;
    JFrame parent;
    public FlightScreen(final JFrame parent, City c) {
	super();
	this.setLayout(null);
	this.parent = parent;
	this.fj = new FlightJLabel(this.parent,this);
	this.setSize(580, 630);
	this.setResizable(false);
	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	
	this.add(fj);
	this.getContentPane().setBackground(Color.white);
	// Form display center
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);
	this.setVisible(true);
	this.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		parent.setEnabled(true);
		dispose();
	    }
	});
    }
}
