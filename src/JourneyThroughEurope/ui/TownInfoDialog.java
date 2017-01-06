package JourneyThroughEurope.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import JourneyThroughEurope.common.SaveLoad;

@SuppressWarnings("serial")
public class TownInfoDialog extends JDialog implements ActionListener {
    private JButton closeButton;
    private JTextArea  townInfo;
    public TownInfoDialog(JFrame owner,String townName, String info) {
	super(owner);
	this.setResizable(false);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	this.setLayout(new BorderLayout());
	this.getContentPane().setBackground(new Color(100, 180, 240));
	this.setTitle(townName+" Information");
	this.setSize(300, 400);

	this.closeButton = new JButton(
		"<html><h2><font color= \"red\">Close</font><h2></html>");
	this.closeButton.setBackground(new Color(100, 180, 240));
	this.closeButton.addActionListener(this);
	this.townInfo = new JTextArea(info);
	this.townInfo.setLineWrap(true);
	this.townInfo.setFont(new Font("Serif",0,18));
	JScrollPane jsc = new JScrollPane(this.townInfo);
	jsc.setSize(this.getWidth() / 5 * 4, this.getHeight() / 5);
	this.add(
		new JLabel(
			"<html><h1><font color= \"red\">"+townName+"</font><h1></html>"),
		BorderLayout.NORTH);
	this.add(jsc, BorderLayout.CENTER);
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
}
