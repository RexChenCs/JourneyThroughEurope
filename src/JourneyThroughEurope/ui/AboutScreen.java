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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.colorchooser.ColorSelectionModel;

@SuppressWarnings("serial")
public class AboutScreen extends JDialog implements ActionListener{
    private JButton reButton;
    JFrame parent;
    public AboutScreen(final JFrame parent) {

	this.setResizable(false);
	this.parent = parent;
	this.getContentPane().setBackground(new Color(100, 180, 240));
	this.reButton =new JButton("<html><h2><font color= \"red\">Return</font><h2></html>");
	this.reButton.setBackground(new Color(100, 180, 240));
	this.reButton.addActionListener(this);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	this.setLayout( new BorderLayout());
        this.setTitle("About");
	this.setSize(480, 550);
	JLabel  title =  new JLabel("<html><h1><font color= \"red\">Journey Through Europe</font><h1></html>");
        JTextArea  contents = new JTextArea("Overall description Journey Through\n "
        	+ "Europe is a travel game of strategy and planning for all the family.\n"
        	+ " It can be played by 2 to 6 players and it contains:\n "
        	+ "- 1 playing board,\n"
        	+ "- 6 playing pieces,\n"+ 
                "- 6 flags in different colors,\n"
        	+"- 180 place cards (64 with illustrations and special instructions)\n"
        	+ "- 6 flight plans, and - 1 dice");
        contents.setEditable(false);
        contents.setFont(new Font("Serif",0,18));
        contents.setBackground(new Color(200,200,0));
        
        this.add(title,BorderLayout.NORTH);
        this.add(contents,BorderLayout.CENTER);
        JPanel j = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        j.add(this.reButton);
        this.add(j,BorderLayout.SOUTH);
	// Form display center
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
    @Override
    public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	parent.setVisible(true);
	dispose();
    }

}
