package JourneyThroughEurope.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import JourneyThroughEurope.bean.GameHistory;
import JourneyThroughEurope.bean.Player;

@SuppressWarnings("serial")
public class SetUserFrame extends JFrame implements ActionListener {

	private JLabel labelPlayerNumber;
	private JComboBox<Integer> comboxPlayerNumber;
	private JButton buttonGo;
	private JLabel[] labelFlag;
	private JRadioButton[] radioButtonPlayer;
	private JTextField[] textFieldName;
	private ButtonGroup[] buttonGroup ;
	public SetUserFrame() {
		super();
		this.setResizable(false);
		this.setBackground(new Color(184,153,102));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// TODO Auto-generated constructor stub
		// initials
		labelPlayerNumber = new JLabel("Number of players:");
		comboxPlayerNumber = new JComboBox<Integer>();
		for (int i = 2; i <= 6; i++) {
			comboxPlayerNumber.addItem(i);
		}
		buttonGo = new JButton("GO!");
		buttonGo.addActionListener(this);
		labelFlag = new JLabel[6];
		JPanel panel[] = new JPanel[6];
		ImageIcon[] iconFlag = new ImageIcon[6];
		for (int i = 0; i < labelFlag.length; i++) {
			labelFlag[i] = new JLabel();
			labelFlag[i].setSize(new Dimension(100, 20));
			panel[i] = new JPanel();
			panel[i].setLayout(new GridLayout(1, 3));
		}
		iconFlag[0] = new ImageIcon("Artwork/flag_black.png");
		iconFlag[1] = new ImageIcon("Artwork/flag_yellow.png");
		iconFlag[2] = new ImageIcon("Artwork/flag_blue.png");
		iconFlag[3] = new ImageIcon("Artwork/flag_red.png");
		iconFlag[4] = new ImageIcon("Artwork/flag_green.png");
		iconFlag[5] = new ImageIcon("Artwork/flag_white.png");

		labelFlag[0].setIcon(iconFlag[0]);
		labelFlag[1].setIcon(iconFlag[1]);
		labelFlag[2].setIcon(iconFlag[2]);
		labelFlag[3].setIcon(iconFlag[3]);
		labelFlag[4].setIcon(iconFlag[4]);
		labelFlag[5].setIcon(iconFlag[5]);

		

		JPanel panelUp = new JPanel();
		JPanel panelDown = new JPanel();
	      
		panelUp.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelUp.setBackground(this.getBackground());
		panelUp.add(labelPlayerNumber);
		panelUp.add(comboxPlayerNumber);
		panelUp.add(buttonGo);

		panelDown.setBounds(new Rectangle(900, 50));
		panelDown.setLayout(new GridLayout(2, 3));
		panelDown.setBackground(this.getBackground());
		buttonGroup = new ButtonGroup[6];
		for (int i = 0; i < buttonGroup.length; i++) {
			buttonGroup[i] = new ButtonGroup();
		}
		radioButtonPlayer = new JRadioButton[12];
		for (int i = 0; i < radioButtonPlayer.length; i++) {
			if (i % 2 == 0) {
				radioButtonPlayer[i] = new JRadioButton("player     ");
				radioButtonPlayer[i].setSelected(true);
				
			} else {
				radioButtonPlayer[i] = new JRadioButton("computer");
			}
			radioButtonPlayer[i].setBackground(this.getBackground());
			buttonGroup[i / 2].add(radioButtonPlayer[i]);
			// panel[i / 2].add(radioButtonPlayer[i]);
		}

		textFieldName = new JTextField[6];
		for (int i = 0; i < textFieldName.length; i++) {
			textFieldName[i] = new JTextField("Player" + (i + 1), 7);
		}
		// add six panel
		for (int i = 0; i < panel.length; i++) {
			panelDown.add(panel[i]);
		}

		JPanel[][] panels = new JPanel[6][3];
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[0].length; j++) {
				panels[i][j] = new JPanel();
				panels[i][j].setBackground(this.getBackground());
				panel[i].add(panels[i][j]);
				panel[i].setBorder(BorderFactory.createLineBorder(Color.orange));
			}
		}
		for (int i = 0; i < panels.length; i++) {
			panels[i][0].add(labelFlag[i]);
			panels[i][1].add(radioButtonPlayer[2 * i]);
			panels[i][1].add(radioButtonPlayer[2 * i + 1]);
			panels[i][2].add(new JLabel("Name:"));
			panels[i][2].add(textFieldName[i]);
		}
                this.setLayout(new BorderLayout());
		this.getContentPane().add(panelUp, BorderLayout.NORTH);
		this.getContentPane().add(panelDown, BorderLayout.CENTER);
		JPanel left = new JPanel();
		left.setBackground(this.getBackground());
		JPanel right = new JPanel();
		right.setBackground(this.getBackground());
		JPanel south = new JPanel();
		south.setBackground(this.getBackground());
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(right, BorderLayout.EAST);
		this.getContentPane().add(south, BorderLayout.SOUTH);
		this.setSize(900, 520);
		this.setVisible(true);
		// Form display center
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - this
				.getWidth()) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - this
				.getHeight()) / 2;
		this.setLocation(w, h);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	    if(arg0.getSource() == this.buttonGo){
		Player[] players = new Player[this.comboxPlayerNumber.getSelectedIndex()+2];
		for(int i = 0; i < players.length; i++){
		    int flag = Player.HUMAN;
		    if(this.radioButtonPlayer[i*2 + 1].isSelected()){
			flag = Player.COMPUTER;
		    }
		    players[i] = new Player(i+1,flag,textFieldName[i].getText(),1);
		}
		GameHistory game = GameHistory.getGameHistory();
		game.setPlayers(players);
		game.setCurrentNum(1);
		game.setIsNewGame(1);
		game.setPlayerNum(players.length);
		GameplayScreen.getGamePlay(game);
		this.dispose();
	    }
	}

}
