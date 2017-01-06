package JourneyThroughEurope.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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

import JourneyThroughEurope.common.SaveLoad;

@SuppressWarnings("serial")
public class HistoryScreen extends JFrame implements ActionListener {
    private ShowTable hisTable;
    private JButton closeButton;
    private JFrame parent;

    public HistoryScreen(final JFrame parent) {

	this.setResizable(false);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	InitTable();
	this.setLayout(new BorderLayout());
	this.getContentPane().setBackground(new Color(100, 180, 240));
	this.setTitle("History");
	this.setSize(550, 650);

	this.parent = parent;
	this.closeButton = new JButton(
		"<html><h2><font color= \"red\">Close</font><h2></html>");
	this.closeButton.setBackground(new Color(100, 180, 240));
	this.closeButton.addActionListener(this);

	JScrollPane jsc = new JScrollPane(hisTable.getJta());
	jsc.setSize(this.getWidth() / 5 * 4, this.getHeight() / 5);
	this.add(
		new JLabel(
			"<html><h1><font color= \"red\">Game History</font><h1></html>"),
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
	this.addWindowListener(new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
		parent.setVisible(true);
		dispose();
	    }

	});

    }

    private void InitTable() {
	// TODO Auto-generated method stub
	String[] columns = new String[] { "op_user", "action", "or_city",
		"des_city", "leftmove" };
	this.hisTable = new ShowTable(columns);
	try {
	    List<String> list = SaveLoad.getHistory();
	    if (list != null) {
		this.hisTable.setTable(list);
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	parent.setVisible(true);
	dispose();
    }

}
