package JourneyThroughEurope.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DistructionDialog extends JDialog implements ActionListener {
    private JButton closeButton;
    private JLabel cityLabel;

    public DistructionDialog(JFrame owner, String cname, String color) {
	super(owner);
	this.setResizable(false);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	this.setLayout(new BorderLayout());
	this.getContentPane().setBackground(new Color(100, 180, 240));
	this.setTitle(cname + " Information");
	this.setSize(300, 450);

	this.cityLabel = new JLabel();
	String path = "Artwork/" + color + "/" + cname + "_I.jpg";
	ImageIcon icon = new ImageIcon(path);
	if (!(icon.getImageLoadStatus() == MediaTracker.COMPLETE)) {
	    path = "Artwork/" + color + "/" + cname + "_I.png";
	     icon = new ImageIcon(path);
	}
	icon.setImage(icon.getImage().getScaledInstance(280, 350,
		Image.SCALE_SMOOTH));

	cityLabel.setIcon(icon);
	cityLabel.setSize(280, 350);

	this.closeButton = new JButton(
		"<html><h2><font color= \"red\">Close</font><h2></html>");
	this.closeButton.setBackground(new Color(100, 180, 240));
	this.closeButton.addActionListener(this);

	this.add(new JLabel("<html><h1><font color= \"red\">" + cname
		+ "</font><h1></html>"), BorderLayout.NORTH);

	this.add(this.cityLabel, BorderLayout.CENTER);
	JPanel j = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	j.add(this.closeButton);
	this.add(j, BorderLayout.SOUTH);
	int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
		.getWidth()) / 2;
	int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
		.getHeight()) / 2;
	this.setLocation(w, h);
	this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	this.dispose();
    }

    public static void main(String[] args) {
	new DistructionDialog(null, "BERN", "green");
    }
}
