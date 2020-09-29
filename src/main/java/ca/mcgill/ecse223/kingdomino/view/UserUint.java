package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;

public class UserUint implements ActionListener {


	// Labels and other fields

	private JLabel KingDominotitle;
	private JLabel newGametitle;
	private JLabel name;
	private JLabel name1;
	private JLabel name2;
	private JLabel name3;
	private JLabel bonusoptions;
	private JTextField nametext;
	private JTextField nametext1;
	private JTextField nametext2;
	private JTextField nametext3;
	private JComboBox<String> bonustext;

	float[] hsbvals = new float[3];
	Color pblue = new Color(174,198,207, 100);
	Color ppink = new Color(100,82,86, 100);
	Color pgreen = new Color(71,93,71, 100);
	Color pyellow = new Color(253,253,150, 100);
	Color offwhite = new Color(249,241,241, 100);

	public UserUint() {

		//setting frame
		JFrame frame = new JFrame();


		//setting buttons
		JButton loadAgame = new JButton("Load A Game");
		JButton StartAgame = new JButton("Start A Game");



		loadAgame.addActionListener(this);
		StartAgame.addActionListener(this);






		nametext= new JTextField();
		nametext1= new JTextField();
		nametext2= new JTextField();
		nametext3= new JTextField();



		bonustext= new JComboBox<String>();
		bonustext.addItem("Regular");
		bonustext.addItem("Harmony and Middle Kingdom");
		bonustext.addItem("Harmony");
		bonustext.addItem("Middle Kingdom");


		KingDominotitle= new JLabel("KINGDOMINO");


		newGametitle= new JLabel("New Game (4 Player)");



		name= new JLabel("Player 1 Name (blue) :");
		name1=new JLabel("Player 2 Name (yellow):");
		name2=new JLabel("Player 3 Name (pink):");
		name3=new JLabel("Player 4 Name (green):");


		bonusoptions= new JLabel("Bonus Scoring Option:");

		//Titles
		KingDominotitle.setFont(new Font("Slab Serif", Font.BOLD, 60));
		KingDominotitle.setForeground(Color.RED);
		newGametitle.setFont(new Font("Slab Serif", Font.BOLD, 35));
		newGametitle.setForeground(Color.red);

		//Set fonts


		name.setFont(new Font("Slab Serif", Font.BOLD, 15));
		name1.setFont(new Font("Slab Serif", Font.BOLD, 15));
		name2.setFont(new Font("Slab Serif", Font.BOLD, 15));
		name3.setFont(new Font("Slab Serif", Font.BOLD, 15));

		bonusoptions.setFont(new Font("Slab Serif", Font.BOLD, 15));

		//top panel
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createEmptyBorder(25, 100, 50, 50));
		titlePanel.setBackground(pblue);
		titlePanel.setLayout(new GridLayout(0, 1));
		titlePanel.add(KingDominotitle);
		titlePanel.add(loadAgame);

		// bottom panel
		JPanel titlePanel2 = new JPanel();
		titlePanel2.setBorder(BorderFactory.createEmptyBorder(25, 100, 100, 810));
		titlePanel2.setLayout(new GridLayout(0, 1));
		titlePanel2.setBackground(ppink);
		titlePanel2.add(newGametitle);
		titlePanel2.add(name);
		titlePanel2.add(nametext);
		titlePanel2.add(name1);
		titlePanel2.add(nametext1);
		titlePanel2.add(name2);
		titlePanel2.add(nametext2);
		titlePanel2.add(name3);
		titlePanel2.add(nametext3);

		titlePanel2.add(bonusoptions);
		titlePanel2.add(bonustext);
		titlePanel2.add(StartAgame);



		//set button attributes
		loadAgame.setBackground(pyellow);
		loadAgame.setOpaque(true);
		loadAgame.setBorderPainted(false);
		StartAgame.setBackground(pgreen);
		StartAgame.setOpaque(true);
		StartAgame.setBorderPainted(false);



		loadAgame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent args) {
				if(loadAgame.isEnabled()) {
					frame.dispose();
					new LoadGame();
				}
			}
		});



		StartAgame.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent args) {
				if(StartAgame.isEnabled()) {

					String playernamestartagame=nametext.getText();
					String playernamestartagame1=nametext1.getText();
					String playernamestartagame2=nametext2.getText();
					String playernamestartagame3=nametext3.getText();
					String bonustextoption=(String) bonustext.getSelectedItem();



					if(bonustextoption.equals("Harmony")) {
						Controller.setGameOptions(4, true, false);
					}
					else if(bonustextoption.equals("Middle Kingdom")) {
						Controller.setGameOptions(4, false, true);
					}
					else if(bonustextoption.equals("Harmony and Middle Kingdom")) {
						Controller.setGameOptions(4, true, true);
					}

					else  {
						Controller.setGameOptions(4, false, false);
					}


					Controller.UserintoPlayer(playernamestartagame, playernamestartagame1, playernamestartagame2, playernamestartagame3);
					Controller.startGame();

					StartAgame.setEnabled(false);
					frame.dispose();
					new KingDominoView();
				}
			}
		});


		//add panels in specific spots
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(titlePanel2, BorderLayout.WEST);



		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("KINGDOMINO");
		frame.setSize(1280,800);
		frame.setVisible(true);

	}


	@Override
	public void actionPerformed(ActionEvent e) {

	}


}