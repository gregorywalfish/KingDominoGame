package ca.mcgill.ecse223.kingdomino.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;


/**
 * ScoreBoard
 * @author gregorywalfish
 */

public class ScoreResults implements ActionListener {
	// final scores
	private int p1score_property;
	private int p2score_property;
	private int p3score_property;
	private int p4score_property;
	private int p1score_bonus;
	private int p2score_bonus;
	private int p3score_bonus;
	private int p4score_bonus;
	private int p1score_total;
	private int p2score_total;
	private int p3score_total;
	private int p4score_total;
	private int p1rank_val;
	private int p2rank_val;
	private int p3rank_val;
	private int p4rank_val;
	private Color p1color;
	private Color p2color;
	private Color p3color;
	private Color p4color;
	private String p1name;
	private String p2name;
	private String p3name;
	private String p4name;
	private JLabel results;
	private JLabel propertyscorep1;
	private JLabel propertyscorep2;
	private JLabel propertyscorep3;
	private JLabel propertyscorep4;
	private JLabel bonusscorep1;
	private JLabel bonusscorep2;
	private JLabel bonusscorep3;
	private JLabel bonusscorep4;
	private JLabel totalscorep1;
	private JLabel totalscorep2;
	private JLabel totalscorep3;
	private JLabel totalscorep4;
	private JLabel rankP1;
	private JLabel rankP2;
	private JLabel rankP3;
	private JLabel rankP4;
	private JLabel P1;
	private JLabel P2;
	private JLabel P3;
	private JLabel P4;

	JButton playAgain;
	JButton exit;
	JFrame frame;

	public ScoreResults() {

		frame = new JFrame();

		// get all player ranks
		p1rank_val = Controller.GetPlayerRanks(0); 
		p2rank_val = Controller.GetPlayerRanks(1); 
		p3rank_val = Controller.GetPlayerRanks(2); 
		p4rank_val = Controller.GetPlayerRanks(3); 
		
		
		// get all player scores
		p1score_property = Controller.GetPropertyScores(0);
		p2score_property = Controller.GetPropertyScores(1);
		p3score_property = Controller.GetPropertyScores(2);
		p4score_property = Controller.GetPropertyScores(3);
		
		// get all player BONUS scores
		p1score_bonus = Controller.GetBonusScores(0);
		p2score_bonus = Controller.GetBonusScores(1);
		p3score_bonus = Controller.GetBonusScores(2);
		p4score_bonus = Controller.GetBonusScores(3);
		
		// get all player TOTAL scores
		p1score_total = Controller.GetTotalScores(0);
		p2score_total = Controller.GetTotalScores(1);
		p3score_total = Controller.GetTotalScores(2);
		p4score_total = Controller.GetTotalScores(3);
		
		// get all player names
		p1name = Controller.GetNames(0);
		p2name = Controller.GetNames(1);
		p3name = Controller.GetNames(2);
		p4name = Controller.GetNames(3);

		// get all players COLORS
		p1color = Controller.GetColors(0);
		p2color = Controller.GetColors(1);
		p3color = Controller.GetColors(2);
		p4color = Controller.GetColors(3);
		
		
		
		playAgain = new JButton("Play Again");
		playAgain.setPreferredSize(new Dimension(40, 40));
		playAgain.addActionListener(this);
		
		exit = new JButton("Exit");
		exit.setPreferredSize(new Dimension(40, 40));
		
		exit.addActionListener(this);

		// results label/format
		results = new JLabel("CONGRATULATIONS!");
		results.setFont(new Font("Slab Serif", Font.BOLD, 60));
		results.setForeground(Color.RED);

		// PROPERTY scores label/format
		propertyscorep1 = new JLabel(p1score_property +" pts");
		propertyscorep2 = new JLabel(p2score_property+" pts");
		propertyscorep3 = new JLabel(p3score_property+" pts");
		propertyscorep4 = new JLabel(p4score_property+" pts");
		propertyscorep1.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		propertyscorep2.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		propertyscorep3.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		propertyscorep4.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		
		// BONUS scores label/format
		bonusscorep1 = new JLabel("+"+p1score_bonus +" pts");
		bonusscorep2 = new JLabel("+"+p2score_bonus+" pts");
		bonusscorep3 = new JLabel("+"+p3score_bonus+" pts");
		bonusscorep4 = new JLabel("+"+p4score_bonus+" pts");
		bonusscorep1.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		bonusscorep2.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		bonusscorep3.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		bonusscorep4.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		bonusscorep1.setForeground(Color.red);
		bonusscorep2.setForeground(Color.red);
		bonusscorep3.setForeground(Color.red);
		bonusscorep4.setForeground(Color.red);
		
		// TOTAL scores label/format
		totalscorep1 = new JLabel(p1score_total+" pts");
		totalscorep2 = new JLabel(p2score_total+" pts");
		totalscorep3 = new JLabel(p3score_total+" pts");
		totalscorep4 = new JLabel(p4score_total+" pts");
		totalscorep1.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		totalscorep2.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		totalscorep3.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		totalscorep4.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		
	

		// RANKS label/format
		rankP1 = new JLabel("   "+p1rank_val+".");
		rankP2 = new JLabel("   "+p2rank_val+".");
		rankP3 = new JLabel("   "+p3rank_val+".");
		rankP4 = new JLabel("   "+p4rank_val+".");
		rankP1.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		rankP2.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		rankP3.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		rankP4.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));

		// USERNAMES label/format
		P1 = new JLabel(p1name);
		P2 = new JLabel(p2name);
		P3 = new JLabel(p3name);
		P4 = new JLabel(p4name);
		P1.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		P2.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		P3.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		P4.setFont(new Font("Slab Serif", Font.BOLD + Font.ITALIC, 30));
		
		//TITLES
		JLabel Rank = new JLabel(" Rank");
		JLabel Username = new JLabel("Name ");
		JLabel PropertyScore = new JLabel("Property  ");
		JLabel BonusScore = new JLabel("Bonus");
		JLabel TotalScore = new JLabel("Total");
		Rank.setFont(new Font("Slab Serif", Font.BOLD, 30));
		Username.setFont(new Font("Slab Serif", Font.BOLD, 30));
		PropertyScore.setFont(new Font("Slab Serif", Font.BOLD, 30));
		BonusScore.setFont(new Font("Slab Serif", Font.BOLD, 30));
		TotalScore.setFont(new Font("Slab Serif", Font.BOLD, 30));
		Username.setForeground(Color.white);
		PropertyScore.setForeground(Color.white);
		TotalScore.setForeground(Color.white);
		BonusScore.setForeground(Color.white);
		Rank.setForeground(Color.white);
		

		// panel for the title
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0,300,0,10));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setLayout(new GridLayout(0, 1));
		titlePanel.add(results);
		
		// panel for titles
		JPanel titles = new JPanel();
		titles.setBorder(BorderFactory.createEmptyBorder());
		titles.setBackground(Color.WHITE);
		titles.setLayout(new GridLayout(1, 5));
		titles.add(Rank);
		titles.add(Username);
		titles.add(PropertyScore);
		titles.add(BonusScore);
		titles.add(TotalScore);
		titles.setBackground(Color.gray);
		
		// panel for player 1
		JPanel p1frame = new JPanel();
		p1frame.setBorder(BorderFactory.createEmptyBorder());
		p1frame.setBackground(Color.WHITE);
		p1frame.setLayout(new GridLayout(1, 5));
		p1frame.add(rankP1);
		p1frame.add(P1);
		p1frame.add(propertyscorep1);
		p1frame.add(bonusscorep1);
		p1frame.add(totalscorep1);
		p1frame.setBackground(p1color);

		// panel for player 2
		JPanel p2frame = new JPanel();
		p2frame.setBorder(BorderFactory.createEmptyBorder());
		p2frame.setBackground(Color.WHITE);
		p2frame.setLayout(new GridLayout(1, 5));
		p2frame.add(rankP2);
		p2frame.add(P2);
		p2frame.add(propertyscorep2);
		p2frame.add(bonusscorep2);
		p2frame.add(totalscorep2);
		p2frame.setBackground(p2color);


		// panel for player 3
		JPanel p3frame = new JPanel();
		p3frame.setBorder(BorderFactory.createEmptyBorder());
		p3frame.setBackground(Color.WHITE);
		p3frame.setLayout(new GridLayout(1, 5));
		p3frame.add(rankP3);
		p3frame.add(P3);
		p3frame.add(propertyscorep3);
		p3frame.add(bonusscorep3);
		p3frame.add(totalscorep3);
		p3frame.setBackground(p3color);

		

		// panel for player 4
		JPanel p4frame = new JPanel();
		p4frame.setBorder(BorderFactory.createEmptyBorder());
		p4frame.setBackground(Color.WHITE);
		p4frame.setLayout(new GridLayout(1, 5));
		p4frame.add(rankP4);
		p4frame.add(P4);
		p4frame.add(propertyscorep4);
		p4frame.add(bonusscorep4);
		p4frame.add(totalscorep4);
		p4frame.setBackground(p4color);

		
		//intermediate Panel
		JPanel scoreBoard = new JPanel();
		scoreBoard.setBackground(Color.WHITE);
		scoreBoard.setLayout(new GridLayout(5, 1));
		scoreBoard.add(titles);
		scoreBoard.add(p4frame);
		scoreBoard.add(p3frame);
		scoreBoard.add(p2frame);
		scoreBoard.add(p1frame);
		
		
		

		// panel for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(BorderFactory.createEmptyBorder());
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new GridLayout(0, 2));
		buttonPanel.add(playAgain);
		buttonPanel.add(exit);

		// adding to the frame
		frame.add(scoreBoard, BorderLayout.CENTER);
		frame.add(titlePanel, BorderLayout.PAGE_START);
		frame.add(buttonPanel, BorderLayout.PAGE_END);

		// formatting the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setTitle("ScoreResults");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(1280, 800);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == playAgain) {
			frame.dispose();
			new UserUint();
		}
		if (e.getSource() == exit) {
			System.exit(0);
		}

	}
}
