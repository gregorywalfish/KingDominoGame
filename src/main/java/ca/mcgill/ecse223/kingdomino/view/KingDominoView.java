package ca.mcgill.ecse223.kingdomino.view;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;

import ca.mcgill.ecse223.kingdomino.controller.*;
import ca.mcgill.ecse223.kingdomino.model.Player;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KingDominoView extends JFrame {

    //drafts
	private HashMap<Integer, JButton> currDrafts;
	private HashMap<Integer, JLabel> placeDrafts;
	private JButton confirmSelection;
	private HashMap<Integer, JLabel> nextDrafts;

	private final int DominoWidth = 160;
	private final int DominoHeight = DominoWidth/2;

	private final int SmallDominoWidth = DominoWidth/2;
	private final int SmallDominoHeight = SmallDominoWidth/2;

	private boolean firstTurn = true;

    //pile


    //kingdoms

    //colors and buttons


    //colors
	float[] hsbvals = new float[3];
	float[] pblue = Color.RGBtoHSB(174,198,207, hsbvals);
	float[] ppink = Color.RGBtoHSB(100,82,86, hsbvals);
	float[] pgreen = Color.RGBtoHSB(71,93,71, hsbvals);
	float[] pyellow = Color.RGBtoHSB(253,253,150, hsbvals);
	float[] offwhite = Color.RGBtoHSB(249,241,241, hsbvals);

    // private Color player1;
    // private Color player2;
    // private Color player3;
    // private Color player4;
    private ArrayList<Color> players;
    
    private JPanel currentBoardPanel;
    private JPanel[] smallBoardPanels;
    private HashMap<Color, Board> largeBoards, smallBoards;
    private HashMap<Color, JLabel> nameLabels;
    
    private JLabel dominoToBePlaced, dominoToBePlaced_small;
    private HashMap<JLabel, String> dominoDirections;
    
    private JLabel messageLabel;
    private JLabel moveLabel, rotateLabel;

    private Color[] nextRoundOrder = new Color[4];
    private Color[] fixedRoundOrder = new Color[4];
    
    private JButton saveButton, exitButton;
    private JButton upButton, downButton, leftButton, rightButton;
    private JButton clockwiseButton, counterclockwiseButton;
    private JButton confirmPlacementButton;
    private JButton discardDominoButton;
    
    private final int LARGEBOARDGRIDWIDTH = 50, SMALLBOARDGRIDWIDTH = 18,
    		LARGEBOARDWIDTH = 9 * LARGEBOARDGRIDWIDTH, SMALLBOARDWIDTH = 9 * SMALLBOARDGRIDWIDTH;


    public KingDominoView() {
    	this.setVisible(true);
        initApp();
        refreshApp();
    }

    //helper for translating colors
    
    /**
     * Convert color in string to Color object
     * 
     * @param color String
     * @return Color The corresponding Color object
     * 
     * @author Zhekai Jiang
     */
    private Color getColor(String color) {
    	if(color.equalsIgnoreCase("Blue")) {
    		return Color.BLUE;
    	} else if(color.equalsIgnoreCase("Green")) {
    		return Color.GREEN;
    	} else if(color.equalsIgnoreCase("Yellow")) {
    		return Color.ORANGE;
    	} else if(color.equalsIgnoreCase("Pink")) {
    		return Color.PINK;
    	} else {
    		throw new IllegalArgumentException("Illegal color " + color);
    	}
    }



	private void initApp() {

		players = new ArrayList<Color>();
		try {
			/* Field player1color = Class.forName("java.awt.Color").getField(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getColor().toString().toUpperCase());
			player1 = (Color)player1color.get(null);
			Field player2color = Class.forName("java.awt.Color").getField(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getColor().toString().toUpperCase());
			player2 = (Color)player2color.get(null);
			Field player3color = Class.forName("java.awt.Color").getField(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getColor().toString().toUpperCase());
			player3 = (Color)player3color.get(null);
			Field player4color = Class.forName("java.awt.Color").getField(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getColor().toString().toUpperCase());
			player4 = (Color)player4color.get(null);*/
			
			for(int i = 0; i < Controller.getNumPlayers(); ++i)
				players.add(i, getColor(
						KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i).getColor().toString()));

		}
		catch (Exception e) {

		}
        //setting up the current player's kingdom grid

        // p1Border = BorderFactory.createLineBorder(player1);
        // currentTurnKingdom = new JPanel();
        // currentTurnKingdom.setBorder(p1Border);
        // currentTurnKingdom.setVisible(true);
        // currentTurnKingdom.setLayout(new GridLayout(Controller.getNumPlayers(), Controller.getNumPlayers())); //currently a nullpointer because Controller.getnumplayers doesn't work as there is no game

        // for (int i = 0; i < Math.pow(Controller.getNumPlayers(),2); i ++) {
        //    currentTurnKingdom.getComponent(i).setSize(50,50);
        //    currentTurnKingdom.getComponent(i).setBackground(Color.getHSBColor(245,245,245));
        //}

        this.setSize(new Dimension(1440,800));

        getContentPane().setBackground(Color.WHITE);


        //layout
        GroupLayout layout = new GroupLayout(getContentPane());

        getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		largeBoards = new HashMap<Color, Board>();
		smallBoards = new HashMap<Color, Board>();
		nameLabels = new HashMap<Color, JLabel>();
		dominoDirections = new HashMap<JLabel, String>();

		initBoardsAndNameLabels();
		initButtons();

		messageLabel = new JLabel(String.format("KINGDOMINO: %s's turn", KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getUser().getName()));
		messageLabel.setMinimumSize(new Dimension(this.getWidth() - 200, 30));

		currDrafts = new HashMap<Integer, JButton>();
		placeDrafts = new HashMap<Integer, JLabel>();
		nextDrafts = new HashMap<Integer, JLabel>();

		initCurrDraftSlots();
		initPlaceDraftSlots();
		initNextDraftSlots();

		for (int i = 0; i < 4; i++) {
			nextRoundOrder[i] = getColor(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(i).getColor().toString());
		}

		for (int i = 0; i < 4; i++) {
			fixedRoundOrder[i] = nextRoundOrder[i];
		}

		confirmPlacementButton.setEnabled(false);
		discardDominoButton.setEnabled(false);

		super.getContentPane().setBackground(Color.getHSBColor(offwhite[0],offwhite[1],offwhite[2]));

		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(messageLabel)
						.addComponent(saveButton)
						.addComponent(exitButton))
				.addGroup(layout.createSequentialGroup()
// <<<<<<< HEAD
					.addGroup(layout.createParallelGroup(Alignment.CENTER)
							.addComponent(currentBoardPanel, LARGEBOARDWIDTH+11, LARGEBOARDWIDTH+20, LARGEBOARDWIDTH+20)
/* =======
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(boards.get(getCurrentPlayerColor()), LARGEBOARDWIDTH, LARGEBOARDWIDTH, LARGEBOARDWIDTH)
>>>>>>> f88a6e51d3f80a58c2a2f74014133c865661d99a*/
							.addGroup(layout.createSequentialGroup()
									.addComponent(moveLabel)
									.addComponent(upButton)
									.addComponent(downButton)
									.addComponent(leftButton)
									.addComponent(rightButton))
							.addGroup(layout.createSequentialGroup()
									.addComponent(rotateLabel)
									.addComponent(clockwiseButton)
									.addComponent(counterclockwiseButton))
							.addGroup(layout.createSequentialGroup()
									.addComponent(confirmPlacementButton)
									.addComponent(discardDominoButton)
									))
					.addGap(50)
					.addGroup(layout.createParallelGroup()
							.addGroup(layout.createSequentialGroup()
                                    .addComponent(placeDrafts.get(1),DominoWidth,DominoWidth,DominoWidth)
                                    .addGap(50)
									.addComponent(currDrafts.get(1),DominoWidth,DominoWidth,DominoWidth)
							)
							.addGroup(layout.createSequentialGroup()
                                    .addComponent(placeDrafts.get(2),DominoWidth,DominoWidth,DominoWidth)
                                    .addGap(50)
									.addComponent(currDrafts.get(2),DominoWidth,DominoWidth,DominoWidth)
							)
							.addGroup(layout.createSequentialGroup()
                                    .addComponent(placeDrafts.get(3),DominoWidth,DominoWidth,DominoWidth)
                                    .addGap(50)
									.addComponent(currDrafts.get(3),DominoWidth,DominoWidth,DominoWidth)
							)
							.addGroup(layout.createSequentialGroup()
                                    .addComponent(placeDrafts.get(4),DominoWidth,DominoWidth,DominoWidth)
                                    .addGap(50)
									.addComponent(currDrafts.get(4),DominoWidth,DominoWidth,DominoWidth)
							)
							.addComponent(confirmSelection, GroupLayout.Alignment.TRAILING)
						.addGap(50)
							.addComponent(nextDrafts.get(1),SmallDominoWidth,SmallDominoWidth,SmallDominoWidth)
							.addComponent(nextDrafts.get(2),SmallDominoWidth,SmallDominoWidth,SmallDominoWidth)
							.addComponent(nextDrafts.get(3),SmallDominoWidth,SmallDominoWidth,SmallDominoWidth)
							.addComponent(nextDrafts.get(4),SmallDominoWidth,SmallDominoWidth,SmallDominoWidth))
					.addGap(50)
// <<<<<<< HEAD
					.addGroup(layout.createParallelGroup()
							.addComponent(smallBoardPanels[0], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							.addComponent(nameLabels.get(players.get(0)), Alignment.CENTER)
								.addGap(50)
							.addComponent(smallBoardPanels[1], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							.addComponent(nameLabels.get(players.get(1)), Alignment.CENTER))
						.addGroup(layout.createParallelGroup()
							.addComponent(smallBoardPanels[2], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							.addComponent(nameLabels.get(players.get(2)), Alignment.CENTER)
								.addGap(50)
							.addComponent(smallBoardPanels[3], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							.addComponent(nameLabels.get(players.get(3)), Alignment.CENTER))));
/* =======
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(boards.get(getOtherPlayersColors(0)), SMALLBOARDWIDTH, SMALLBOARDWIDTH, SMALLBOARDWIDTH)
							.addComponent(nameLabels.get(Color.BLUE))
							.addComponent(boards.get(getOtherPlayersColors(1)), SMALLBOARDWIDTH, SMALLBOARDWIDTH, SMALLBOARDWIDTH)
							.addComponent(nameLabels.get(Color.ORANGE))
							.addComponent(boards.get(getOtherPlayersColors(2)), SMALLBOARDWIDTH, SMALLBOARDWIDTH, SMALLBOARDWIDTH)
							.addComponent(nameLabels.get(Color.PINK)))));
>>>>>>> f88a6e51d3f80a58c2a2f74014133c865661d99a*/
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(messageLabel)
						.addComponent(saveButton, GroupLayout.Alignment.TRAILING)
						.addComponent(exitButton, GroupLayout.Alignment.TRAILING))
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
// <<<<<<< HEAD
							.addComponent(currentBoardPanel, LARGEBOARDWIDTH+11, LARGEBOARDWIDTH+20, LARGEBOARDWIDTH+20)
// =======
							// .addComponent(boards.get(getCurrentPlayerColor()), LARGEBOARDWIDTH, LARGEBOARDWIDTH, LARGEBOARDWIDTH)
// >>>>>>> f88a6e51d3f80a58c2a2f74014133c865661d99a

							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(moveLabel)
									.addComponent(upButton)
									.addComponent(downButton)
									.addComponent(leftButton)
									.addComponent(rightButton))
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(rotateLabel)
									.addComponent(clockwiseButton)
									.addComponent(counterclockwiseButton))
							.addGroup(layout.createParallelGroup()
									.addComponent(confirmPlacementButton)
									.addComponent(discardDominoButton)
									))
					.addGap(50)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(placeDrafts.get(1),DominoHeight,DominoHeight,DominoHeight)
                                .addComponent(placeDrafts.get(2),DominoHeight,DominoHeight,DominoHeight)
                                .addComponent(placeDrafts.get(3),DominoHeight,DominoHeight,DominoHeight)
                                .addComponent(placeDrafts.get(4),DominoHeight,DominoHeight,DominoHeight)
                        )
                        .addGap(50)
					.addGroup(layout.createSequentialGroup()
							.addComponent(currDrafts.get(1),DominoHeight,DominoHeight,DominoHeight)
							.addComponent(currDrafts.get(2),DominoHeight,DominoHeight,DominoHeight)
							.addComponent(currDrafts.get(3),DominoHeight,DominoHeight,DominoHeight)
							.addComponent(currDrafts.get(4),DominoHeight,DominoHeight,DominoHeight)
							.addGap(20)
							.addComponent(confirmSelection)
							.addGap(20)
							.addComponent(nextDrafts.get(1),SmallDominoHeight,SmallDominoHeight,SmallDominoHeight)
							.addComponent(nextDrafts.get(2),SmallDominoHeight,SmallDominoHeight,SmallDominoHeight)
							.addComponent(nextDrafts.get(3),SmallDominoHeight,SmallDominoHeight,SmallDominoHeight)
							.addComponent(nextDrafts.get(4),SmallDominoHeight,SmallDominoHeight,SmallDominoHeight))
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
								.addComponent(smallBoardPanels[0], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
								.addComponent(smallBoardPanels[2], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							)
							.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(nameLabels.get(players.get(0)))
									.addComponent(nameLabels.get(players.get(2)))
							)
							.addGroup(layout.createParallelGroup()
								.addComponent(smallBoardPanels[1], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
								.addComponent(smallBoardPanels[3], SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16, SMALLBOARDWIDTH+16)
							)
							.addGroup(layout.createParallelGroup(Alignment.CENTER)
									.addComponent(nameLabels.get(players.get(1)))
									.addComponent(nameLabels.get(players.get(3))))
							)));

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{upButton, downButton, leftButton, rightButton});

		layout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[]
				{clockwiseButton, counterclockwiseButton});

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void refreshApp() {

    	//Controller.createFirstDraft();
    	//Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());

		for (int i = 1; i <= 4; i++) {
			ImageIcon currDomino = new ImageIcon(String.format("src/main/java/ca/mcgill/ecse223/kingdomino/view/D%d.png", KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(i-1).getId()));
			Image currDominoIMG = currDomino.getImage();
			Image newcurrDominoIMG = currDominoIMG.getScaledInstance(DominoWidth,DominoHeight,Image.SCALE_SMOOTH);
			currDomino = new ImageIcon(newcurrDominoIMG);
			currDrafts.get(i);
			currDrafts.get(i).setIcon(currDomino);
		}

		fillNextDraftSpace();

		//button clicks
		currDrafts.get(1).addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDominoActionPerformedCurrDraft1(e);
			}
		});
		currDrafts.get(2).addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDominoActionPerformedCurrDraft2(e);
			}
		});
		currDrafts.get(3).addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDominoActionPerformedCurrDraft3(e);
			}
		});
		currDrafts.get(4).addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectDominoActionPerformedCurrDraft4(e);
			}
		});

    }

    private void initCurrDraftSlots() {
    	for (int i = 1; i <=4; i++) {
			currDrafts.put(i, new JButton());
			currDrafts.get(i).setBorder(null);
		}

		confirmSelection = new JButton("Confirm Domino Selection");
		confirmSelection.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confirmSelectionActionPerformed(evt);
			}
		});
	}

    private void initPlaceDraftSlots() {
        for (int i = 1; i <= 4; i++) {
            placeDrafts.put(i, new JLabel());
            placeDrafts.get(i).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            placeDrafts.get(i).setBackground(Color.gray);
        }
    }

    private void initNextDraftSlots() {
		for (int i = 1; i <=4; i++) {
			JLabel pane = new JLabel();
			pane.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));
			pane.setBackground(Color.getHSBColor(pblue[0],pblue[1],pblue[2]));
			nextDrafts.put(i, pane);

		}
	}

	private String colortoString(Color c) {
    	if (c == Color.blue) {
    		return "Blue";
		}
    	if (c == Color.orange) {
    		return "Orange";
		}
    	if (c == Color.green) {
    		return "Green";
		}
    	if (c == Color.pink) {
    		return "Red";
		}

    	return null;
	}

    /**
     * @author Zhekai Jiang
     */
    private void initBoardsAndNameLabels() {
    	currentBoardPanel = new JPanel();
    	currentBoardPanel.setSize(LARGEBOARDWIDTH, LARGEBOARDWIDTH);

    	smallBoardPanels = new JPanel[4];

    	smallBoardPanels[0] = new JPanel();
    	smallBoardPanels[0].setSize(SMALLBOARDWIDTH, SMALLBOARDGRIDWIDTH);

    	smallBoardPanels[1] = new JPanel();
    	smallBoardPanels[1].setSize(SMALLBOARDWIDTH, SMALLBOARDGRIDWIDTH);

    	smallBoardPanels[2] = new JPanel();
       	smallBoardPanels[2].setSize(SMALLBOARDWIDTH, SMALLBOARDGRIDWIDTH);

       	smallBoardPanels[3] = new JPanel();
       	smallBoardPanels[3].setSize(SMALLBOARDWIDTH, SMALLBOARDGRIDWIDTH);

    	largeBoards.put(players.get(0), new Board(LARGEBOARDGRIDWIDTH, players.get(0)));
    	largeBoards.get(players.get(0)).addCastle(new JLabel(), 0,0);

    	smallBoards.put(players.get(0), new Board(SMALLBOARDGRIDWIDTH, players.get(0)));
		smallBoards.get(players.get(0)).addCastle(new JLabel(), 0,0);

    	largeBoards.put(players.get(1), new Board(LARGEBOARDGRIDWIDTH, players.get(1)));
		largeBoards.get(players.get(1)).addCastle(new JLabel(), 0,0);
		smallBoards.put(players.get(1), new Board(SMALLBOARDGRIDWIDTH, players.get(1)));
		smallBoards.get(players.get(1)).addCastle(new JLabel(), 0,0);

		largeBoards.put(players.get(2), new Board(LARGEBOARDGRIDWIDTH, players.get(2)));
		largeBoards.get(players.get(2)).addCastle(new JLabel(), 0,0);
		smallBoards.put(players.get(2), new Board(SMALLBOARDGRIDWIDTH, players.get(2)));
		smallBoards.get(players.get(2)).addCastle(new JLabel(), 0,0);

		largeBoards.put(players.get(3), new Board(LARGEBOARDGRIDWIDTH	, players.get(3)));
		largeBoards.get(players.get(3)).addCastle(new JLabel(), 0,0);
		smallBoards.put(players.get(3), new Board(SMALLBOARDGRIDWIDTH, players.get(3)));
		smallBoards.get(players.get(3)).addCastle(new JLabel(), 0,0);

		//I CALL ADD CASTLE HERE

    	currentBoardPanel.setLayout(new GridLayout(1, 1));
    	currentBoardPanel.add(largeBoards.get(players.get(0)));
    	currentBoardPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray,10));

		smallBoardPanels[0].setLayout(new GridLayout(1, 1));
		smallBoardPanels[0].add(smallBoards.get(players.get(0)));
		smallBoardPanels[0].setBorder(BorderFactory.createLineBorder(Color.lightGray,8));
		nameLabels.put(players.get(0), new JLabel("Player Score: 0"));

		smallBoardPanels[1].setLayout(new GridLayout(1, 1));
		smallBoardPanels[1].add(smallBoards.get(players.get(1)));
		smallBoardPanels[1].setBorder(BorderFactory.createLineBorder(Color.lightGray,8));
		nameLabels.put(players.get(1), new JLabel("Player Score: 0"));

		smallBoardPanels[2].setLayout(new GridLayout(1, 1));
		smallBoardPanels[2].add(smallBoards.get(players.get(2)));
		smallBoardPanels[2].setBorder(BorderFactory.createLineBorder(Color.lightGray,8));
		nameLabels.put(players.get(2), new JLabel("Player Score: 0"));

		smallBoardPanels[3].setLayout(new GridLayout(1,1));
		smallBoardPanels[3].add(smallBoards.get(players.get(3)));
		smallBoardPanels[3].setBorder(BorderFactory.createLineBorder(Color.lightGray,8));
		nameLabels.put(players.get(3), new JLabel("Player Score: 0"));
    }

	/**
     * @author Zhekai Jiang
     */
    private void initButtons() {
    	saveButton = new JButton("Save");
    	saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});

    	exitButton = new JButton("Exit");
    	exitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				exitButtonActionPerformed(evt);
			}
		});

    	moveLabel = new JLabel("Move: ");

    	rotateLabel = new JLabel("Rotate: ");

    	upButton = new JButton("Up");
		upButton.setEnabled(false);
		upButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				upButtonActionPerformed(evt);
			}
		});

		downButton = new JButton("Down");
		downButton.setEnabled(false);
		downButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				downButtonActionPerformed(evt);
			}
		});

		leftButton = new JButton("Left");
		leftButton.setEnabled(false);
		leftButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leftButtonActionPerformed(evt);
			}
		});

		rightButton = new JButton("Right");
		rightButton.setEnabled(false);
		rightButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rightButtonActionPerformed(evt);
			}
		});

		clockwiseButton = new JButton("Clockwise");
		clockwiseButton.setEnabled(false);
		clockwiseButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clockwiseButtonActionPerformed(evt);
			}
		});

		counterclockwiseButton = new JButton("Counterclockwise");
		counterclockwiseButton.setEnabled(false);
		counterclockwiseButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				counterclockwiseButtonActionPerformed(evt);
			}
		});

		confirmPlacementButton = new JButton("Confirm Placement");
		confirmPlacementButton.setEnabled(false);
		confirmPlacementButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				confirmPlacementButtonActionPerformed(evt);
			}
		});

		discardDominoButton = new JButton("Discard Domino");
		discardDominoButton.setEnabled(false);
		discardDominoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				discardDominoButtonActionPerformed(e);
			}
		});
    }

    /**
     * @author Zhekai Jiang
     */
    private void enableMoveAndRotateButtons(boolean enable) {
    	upButton.setEnabled(enable);
    	downButton.setEnabled(enable);
    	leftButton.setEnabled(enable);
    	rightButton.setEnabled(enable);
    	clockwiseButton.setEnabled(enable);
    	counterclockwiseButton.setEnabled(enable);
    }

    boolean saved = false;

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	new ConfirmSaveOverwrite();
    	saved = true;
    }

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	new ConfirmExit(this);

    }

    private void checkifDominoIsDiscarded() {
    	if (KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer().getDominoSelection().getDomino().getStatus().toString().equalsIgnoreCase("discarded")) {
    		largeBoards.get(getCurrentPlayerColor()).remove(dominoToBePlaced);
    		smallBoards.get(getCurrentPlayerColor()).remove(dominoToBePlaced_small);

			playerTurn++;
			enableMoveAndRotateButtons(true);

			currentBoardPanel.removeAll();
			currentBoardPanel.add(largeBoards.get(fixedRoundOrder[playerTurn]));
			currentBoardPanel.repaint();
			currentBoardPanel.revalidate();

			confirmPlacementButton.setEnabled(true);
		}
	}

	private void discardDominoButtonActionPerformed(ActionEvent evt) {
    	Controller.discardCurrentDomino();
    	checkifDominoIsDiscarded();
	}

    private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	Controller.moveCurrentDomino("up");
    	if(dominoToBePlaced.getY() > 0) {
    		largeBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced, "up");
    		smallBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced_small, "up");
    	}
    	checkValidity();
    	checkifDominoIsDiscarded();

    }

    private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	Controller.moveCurrentDomino("down");
    	if(dominoToBePlaced.getY() + dominoToBePlaced.getHeight() < LARGEBOARDGRIDWIDTH * 9) {
    		largeBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced, "down");
    		smallBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced_small, "down");
    	}
    	checkValidity();
		checkifDominoIsDiscarded();
    }

    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	Controller.moveCurrentDomino("left");
    	if(dominoToBePlaced.getX() > 0) {
    		largeBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced, "left");
    		smallBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced_small, "left");
    	}
    	checkValidity();
		checkifDominoIsDiscarded();
    }

    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	Controller.moveCurrentDomino("right");
    	if(dominoToBePlaced.getX() + dominoToBePlaced.getWidth() < LARGEBOARDGRIDWIDTH * 9) {
    		largeBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced, "right");
    		smallBoards.get(getCurrentPlayerColor()).moveDomino(dominoToBePlaced_small, "right");
    	}
    	checkValidity();
		checkifDominoIsDiscarded();
    }

    private void clockwiseButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	rotateJLabelClockwise(dominoToBePlaced, LARGEBOARDGRIDWIDTH);
    	rotateJLabelClockwise(dominoToBePlaced_small, SMALLBOARDGRIDWIDTH);
    	Controller.rotateCurrentDomino("clockwise");
    	checkValidity();
		checkifDominoIsDiscarded();
    }

    private void counterclockwiseButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	rotateJLabelCounterclockwise(dominoToBePlaced, LARGEBOARDGRIDWIDTH);
    	rotateJLabelCounterclockwise(dominoToBePlaced_small, SMALLBOARDGRIDWIDTH);
    	Controller.rotateCurrentDomino("counterclockwise");
    	checkValidity();
		checkifDominoIsDiscarded();
    }

    /**
     * Rotate the domino (represented with JLabel) clockwise
     *
     * @param domino JLabel The domino to rotate
     * @param gridWidth int The width of each grid of the board
     *
     * @author Zhekai Jiang
     */
    private void rotateJLabelClockwise(JLabel domino, int gridWidth) {
    	if(dominoDirections.get(domino).equalsIgnoreCase("right") && domino.getY() >= 8 * gridWidth
    			|| dominoDirections.get(domino).equalsIgnoreCase("down") && domino.getX() <= 0
    			|| dominoDirections.get(domino).equalsIgnoreCase("left") && domino.getY() <= 0
    			|| dominoDirections.get(domino).equalsIgnoreCase("up") && domino.getX() >= 8 * gridWidth)
    		return;

    	BufferedImage rotatedImage = getRotatedImage(((ImageIcon) domino.getIcon()).getImage(), domino.getWidth(), domino.getHeight(), Math.acos(-1) / 2);

    	domino.setIcon(new ImageIcon((Image) rotatedImage));
    	domino.setSize(domino.getHeight(), domino.getWidth());

    	int x = domino.getX(), y = domino.getY();
    	if(dominoDirections.get(domino).equalsIgnoreCase("right")) {
    		dominoDirections.put(domino, "down");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("down")) {
    		x -= gridWidth;
    		dominoDirections.put(domino, "left");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("left")) {
    		x += gridWidth;
    		y -= gridWidth;
    		dominoDirections.put(domino, "up");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("up")) {
    		y += gridWidth;
    		dominoDirections.put(domino, "right");
    	}

    	domino.setBounds(x, y, domino.getWidth(), domino.getHeight());
    }

    /**
     * Rotate the domino (represented with JLabel) counterclockwise
     *
     * @param domino JLabel The domino to rotate
     * @param gridWidth int The width of each grid of the board
     *
     * @author Zhekai Jiang
     */
    private void rotateJLabelCounterclockwise(JLabel domino, int gridWidth) {
    	if(dominoDirections.get(domino).equalsIgnoreCase("right") && domino.getY() <= 0
    			|| dominoDirections.get(domino).equalsIgnoreCase("down") && domino.getX() >= 8 * gridWidth
    			|| dominoDirections.get(domino).equalsIgnoreCase("left") && domino.getY() >= 8 * gridWidth
    			|| dominoDirections.get(domino).equalsIgnoreCase("up") && domino.getX() <= 0)
    		return;

    	BufferedImage rotatedImage = getRotatedImage(((ImageIcon) domino.getIcon()).getImage(), domino.getWidth(), domino.getHeight(), -Math.acos(-1) / 2);

    	domino.setIcon(new ImageIcon((Image) rotatedImage));
    	domino.setSize(domino.getHeight(), domino.getWidth());

    	int x = domino.getX(), y = domino.getY();
    	if(dominoDirections.get(domino).equalsIgnoreCase("right")) {
    		y -= gridWidth;
    		dominoDirections.put(domino, "up");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("down")) {
    		dominoDirections.put(domino, "right");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("left")) {
    		x += gridWidth;
    		dominoDirections.put(domino, "down");
    	} else if(dominoDirections.get(domino).equalsIgnoreCase("up")) {
    		x -= gridWidth;
    		y += gridWidth;
    		dominoDirections.put(domino, "left");
    	}

    	domino.setBounds(x, y, domino.getWidth(), domino.getHeight());
    }

    /**
     * Return the image rotated by a certain degree, in radians
     *
     * @param image Image The original image
     * @param width int The width of the original image
     * @param height int The height of the original image
     * @param rad double The degrees in radians by which the image is rotated
     * @return BufferedImage The rotated image
     *
     * @author Zhekai Jiang
     */
    private BufferedImage getRotatedImage(Image image, int width, int height, double rad) {
    	BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    	bufferedImage.getGraphics().drawImage(image, 0, 0, null);

    	BufferedImage rotatedImage = new BufferedImage(height, width, bufferedImage.getType());
    	AffineTransform transform = new AffineTransform();
    	transform.translate(height / 2, width / 2);
    	transform.rotate(rad, 0, 0);
    	transform.translate(-width / 2, -height / 2);

    	AffineTransformOp rotate = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
    	rotate.filter(bufferedImage, rotatedImage);

    	return rotatedImage;
    }

    /**
     * If the domino's position is valid, then enable the confirm placement button.
     *
     * @author Zhekai Jiang
     */
    private void checkValidity() {
    	confirmPlacementButton.setEnabled(Controller.verifyNeighborAdjacency() && Controller.verifyNoOverlapping() && Controller.verifyKingdomGridSize());
    }

    private void confirmPlacementButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	saved = false;

    	KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
    	Controller.placeCurrentDomino();

        if (currDrafts.get(1).getBorder() == null) currDrafts.get(1).setEnabled(true);
        if (currDrafts.get(2).getBorder() == null) currDrafts.get(2).setEnabled(true);
        if (currDrafts.get(3).getBorder() == null) currDrafts.get(3).setEnabled(true);
        if (currDrafts.get(4).getBorder() == null) currDrafts.get(4).setEnabled(true);

		confirmPlacementButton.setEnabled(false);
        clockwiseButton.setEnabled(false);
        counterclockwiseButton.setEnabled(false);
        leftButton.setEnabled(false);
        rightButton.setEnabled(false);
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        discardDominoButton.setEnabled(false);

		if (Controller.getStateMachine().getGamestatusFullName().equalsIgnoreCase("ending.showingresults")) {
			this.dispose();
			new ScoreResults();
		}

		int currentplayerscore = Controller.CalculatePlayerScore(KingdominoApplication.getKingdomino().getCurrentGame().getNextPlayer());
		nameLabels.get(getCurrentPlayerColor()).setText("Player Score: " + currentplayerscore);
		nameLabels.get(getCurrentPlayerColor()).repaint();
		nameLabels.get(getCurrentPlayerColor()).revalidate();
    }

    private int selectedDraftSlot = 0;
    private int playerTurn = 0;

    private void selectDominoActionPerformedCurrDraft1(ActionEvent evt) {

        if (currDrafts.get(2).isEnabled()) currDrafts.get(2).setBorder(null);
        if (currDrafts.get(3).isEnabled()) currDrafts.get(3).setBorder(null);
        if (currDrafts.get(4).isEnabled()) currDrafts.get(4).setBorder(null);


		if (KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).hasDominoSelection()) KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getDominoSelection().delete();

    	currDrafts.get(1).setBorder(BorderFactory.createLineBorder(fixedRoundOrder[playerTurn], 3));
    	if(firstTurn)
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(0), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
    	else
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(0), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
		selectedDraftSlot = 1;

		confirmSelection.setEnabled(true);
    }

	private void selectDominoActionPerformedCurrDraft2(ActionEvent evt) {

        if (currDrafts.get(1).isEnabled()) currDrafts.get(1).setBorder(null);
        if (currDrafts.get(3).isEnabled()) currDrafts.get(3).setBorder(null);
        if (currDrafts.get(4).isEnabled()) currDrafts.get(4).setBorder(null);


		if (KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).hasDominoSelection()) KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getDominoSelection().delete();

    	currDrafts.get(2).setBorder(BorderFactory.createLineBorder(fixedRoundOrder[playerTurn], 3));
    	if(firstTurn)
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(1), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
    	else
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(1), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
		selectedDraftSlot = 2;
		confirmSelection.setEnabled(true);
    }
	private void selectDominoActionPerformedCurrDraft3(ActionEvent evt) {

        if (currDrafts.get(2).isEnabled()) currDrafts.get(2).setBorder(null);
        if (currDrafts.get(1).isEnabled()) currDrafts.get(1).setBorder(null);
        if (currDrafts.get(4).isEnabled()) currDrafts.get(4).setBorder(null);


		if (KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).hasDominoSelection()) KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getDominoSelection().delete();

    	currDrafts.get(3).setBorder(BorderFactory.createLineBorder(fixedRoundOrder[playerTurn], 3));
    	if(firstTurn)
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(2), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
    	else
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(2), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
		selectedDraftSlot = 3;
		confirmSelection.setEnabled(true);
	}
	private void selectDominoActionPerformedCurrDraft4(ActionEvent evt) {

        if (currDrafts.get(2).isEnabled()) currDrafts.get(2).setBorder(null);
        if (currDrafts.get(3).isEnabled()) currDrafts.get(3).setBorder(null);
        if (currDrafts.get(1).isEnabled()) currDrafts.get(1).setBorder(null);


		if (KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).hasDominoSelection()) KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getDominoSelection().delete();

    	currDrafts.get(4).setBorder(BorderFactory.createLineBorder(fixedRoundOrder[playerTurn], 3));

    	if(firstTurn)
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft().getIdSortedDomino(3), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
    	else
    		Controller.chooseNextDomino(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft(), KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(3), KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
		selectedDraftSlot = 4;

		confirmSelection.setEnabled(true);
	}

	Color currColor;

	private Color getCurrentPlayerColor() {


    	try {
			/*Field player1color = Class.forName("java.awt.Color").getField(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getColor().toString().toUpperCase());
			currColor = (Color) player1color.get(null);
			return currColor;*/
			return getColor(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getColor().toString());
		}
    	catch (Exception e) {

		}
		return null;
	}

	private Color getOtherPlayersColors(int index) {

		ArrayList<Color> otherColors = new ArrayList<>();

    	if (players.get(0) != null && players.get(0) != currColor) {
    		otherColors.add(players.get(0));
		}
		if (players.get(1) != null && players.get(1) != currColor) {
			otherColors.add(players.get(1));
		}
		if (players.get(2) != null && players.get(2) != currColor) {
			otherColors.add(players.get(2));
		}
		if (players.get(3) != null && players.get(3) != currColor) {
			otherColors.add(players.get(3));
		}

		return otherColors.get(index);
	}

	/**
	 * @author ricky
	 * @param evt
	 */

	private int toPlaceSlot = 1;

	private void sendToKingdom() {
        confirmPlacementButton.setEnabled(true);
        discardDominoButton.setEnabled(true);

        dominoToBePlaced = new JLabel();
        dominoToBePlaced.setIcon(placeDrafts.get(toPlaceSlot).getIcon());
        dominoToBePlaced_small = new JLabel();
        dominoToBePlaced_small.setIcon(placeDrafts.get(toPlaceSlot).getIcon());
        placeDrafts.get(toPlaceSlot).setIcon(null);

        placeDrafts.get(toPlaceSlot).setEnabled(false);
        largeBoards.get(getCurrentPlayerColor()).addDomino(dominoToBePlaced, 0, 0, "right");
        smallBoards.get(getCurrentPlayerColor()).addDomino(dominoToBePlaced_small, 0, 0, "right");
        dominoDirections.put(dominoToBePlaced, "right");
        dominoDirections.put(dominoToBePlaced_small, "right");
        rotateJLabelCounterclockwise(dominoToBePlaced, LARGEBOARDGRIDWIDTH);
        rotateJLabelCounterclockwise(dominoToBePlaced_small, SMALLBOARDGRIDWIDTH);
        Controller.addCurrentDominoToKingdom();
        checkValidity();

        clockwiseButton.setEnabled(true);
        counterclockwiseButton.setEnabled(true);
        leftButton.setEnabled(true);
        rightButton.setEnabled(true);
        upButton.setEnabled(true);
        downButton.setEnabled(true);

        if (toPlaceSlot == 4) {
            toPlaceSlot = 1;
        }
        else toPlaceSlot++;
    }

	private void confirmSelectionActionPerformed(ActionEvent evt) {

		saved = false;
		if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
			if (firstTurn) {
				KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));

				if (selectedDraftSlot != 0) {
					Controller.addCurrentDominoToKingdom();
					currDrafts.get(selectedDraftSlot).setEnabled(false);
					nextRoundOrder[selectedDraftSlot - 1] = (getColor(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getColor().toString()));

					if (playerTurn == 3) {

						Controller.changePlayerOrder(nextRoundOrder);
						for (int i = 0; i < 4; i++) {
							fixedRoundOrder[i] = nextRoundOrder[i];
						}

						playerTurn = 0;


						//move dominos to left currDrafts
						//fillPlaceDraftSpace();
						fillPlaceDraftSpace();

						confirmPlacementButton.setEnabled(true);
						discardDominoButton.setEnabled(true);

						sendToKingdom();

						currDrafts.get(1).setBorder(null);
						currDrafts.get(2).setBorder(null);
						currDrafts.get(3).setBorder(null);
						currDrafts.get(4).setBorder(null);

						confirmSelection.setEnabled(false);

						if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
							//KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(Controller.CreateNextDraft(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()));
							if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
								Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
								Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
								fillCurrentDraftSpace();
								fillNextDraftSpace();
							} else {
								Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
								fillCurrentDraftSpace();
								for (int i = 1; i <= 4; i++) {
									nextDrafts.get(i).setText(null);
									nextDrafts.get(i).setEnabled(false);
								}
							}
						} else {
							Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
							for (int i = 1; i <= 4; i++) {
								currDrafts.get(i).setIcon(null);
								currDrafts.get(i).setEnabled(false);
							}
							for (int i = 1; i <= 4; i++) {
								nextDrafts.get(i).setText(null);
								nextDrafts.get(i).setEnabled(false);
							}
						}

						currDrafts.get(1).setEnabled(false);
						currDrafts.get(2).setEnabled(false);
						currDrafts.get(3).setEnabled(false);
						currDrafts.get(4).setEnabled(false);

						firstTurn = false;

						//toPlaceSlot++;

					} else {
						playerTurn++;
					}

					currentBoardPanel.removeAll();
					currentBoardPanel.add(largeBoards.get(fixedRoundOrder[playerTurn]));
					currentBoardPanel.repaint();
					currentBoardPanel.revalidate();

				} else {
					System.out.println("No Domino Selected!");
				}
				messageLabel.setText(String.format("KINGDOMINO: %s's turn", KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getUser().getName()));
				messageLabel.repaint();
				messageLabel.revalidate();
				selectedDraftSlot = 0;
			} else {

				KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));

				if (selectedDraftSlot != 0) {
					confirmPlacementButton.setEnabled(true);
					discardDominoButton.setEnabled(true);

					currDrafts.get(selectedDraftSlot).setEnabled(false);
					nextRoundOrder[selectedDraftSlot - 1] = (getColor(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getColor().toString()));

					if (playerTurn == 3) {

						Controller.changePlayerOrder(nextRoundOrder);
						for (int i = 0; i < 4; i++) {
							fixedRoundOrder[i] = nextRoundOrder[i];
						}

						playerTurn = 0;

						//move dominos to left currDrafts
						//fillPlaceDraftSpace();
						fillPlaceDraftSpace();

						confirmPlacementButton.setEnabled(true);
						discardDominoButton.setEnabled(true);

						sendToKingdom();

						currDrafts.get(1).setBorder(null);
						currDrafts.get(2).setBorder(null);
						currDrafts.get(3).setBorder(null);
						currDrafts.get(4).setBorder(null);

						currDrafts.get(1).setEnabled(false);
						currDrafts.get(2).setEnabled(false);
						currDrafts.get(3).setEnabled(false);
						currDrafts.get(4).setEnabled(false);

						confirmSelection.setEnabled(false);

						if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
							//KingdominoApplication.getKingdomino().getCurrentGame().addAllDraft(Controller.CreateNextDraft(KingdominoApplication.getKingdomino().getCurrentGame().getAllDominos()));
							if (KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft() != null) {
								Controller.OrderDraft(KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft());
								Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
								fillCurrentDraftSpace();
								fillNextDraftSpace();
							} else {
								Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
								fillCurrentDraftSpace();
								for (int i = 1; i <= 4; i++) {
									nextDrafts.get(i).setText(null);
									nextDrafts.get(i).setEnabled(false);
								}
							}
						} else {
							Controller.RevealDraft(KingdominoApplication.getKingdomino().getCurrentGame().getCurrentDraft());
							for (int i = 1; i <= 4; i++) {
								currDrafts.get(i).setIcon(null);
								currDrafts.get(i).setEnabled(false);
							}
							for (int i = 1; i <= 4; i++) {
								nextDrafts.get(i).setText(null);
								nextDrafts.get(i).setEnabled(false);
							}
						}
						firstTurn = false;

						//toPlaceSlot++;

					} else {
						System.out.println(toPlaceSlot);
						playerTurn++;
						sendToKingdom();
					}

					currentBoardPanel.removeAll();
					currentBoardPanel.add(largeBoards.get(fixedRoundOrder[playerTurn]));
					currentBoardPanel.repaint();
					currentBoardPanel.revalidate();


				} else {
					System.out.println("No Domino Selected!");
				}

				currDrafts.get(1).setEnabled(false);
				currDrafts.get(2).setEnabled(false);
				currDrafts.get(3).setEnabled(false);
				currDrafts.get(4).setEnabled(false);
				messageLabel.setText(String.format("KINGDOMINO: %s's turn", KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn).getUser().getName()));
				messageLabel.repaint();
				messageLabel.revalidate();
				selectedDraftSlot = 0;

				enableMoveAndRotateButtons(true);
			}

			Controller.triggerSelectionComplete();
			KingdominoApplication.getKingdomino().getCurrentGame().setNextPlayer(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(playerTurn));
		}
		else {
			playerTurn++;
			enableMoveAndRotateButtons(true);

			currentBoardPanel.removeAll();
			currentBoardPanel.add(largeBoards.get(fixedRoundOrder[playerTurn]));
			currentBoardPanel.repaint();
			currentBoardPanel.revalidate();

			confirmPlacementButton.setEnabled(true);
			discardDominoButton.setEnabled(true);
		}
	}

	private void fillNextDraftSpace() {
		for (int i = 1; i <= 4; i++) {
			Integer id = KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i-1).getId();
			nextDrafts.get(i).setText(id.toString());
			nextDrafts.get(i).setBackground(Color.gray);
			nextDrafts.get(i).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,1));
		}
	}

	private void fillCurrentDraftSpace() {

    	currDrafts.get(1).setEnabled(true);
    	currDrafts.get(2).setEnabled(true);
    	currDrafts.get(3).setEnabled(true);
    	currDrafts.get(4).setEnabled(true);

		for (int i = 1; i <= 4; i++) {
			ImageIcon currDomino = new ImageIcon(String.format("src/main/java/ca/mcgill/ecse223/kingdomino/view/D%d.png", KingdominoApplication.getKingdomino().getCurrentGame().getNextDraft().getIdSortedDomino(i-1).getId()));
			Image currDominoIMG = currDomino.getImage();
			Image newcurrDominoIMG = currDominoIMG.getScaledInstance(DominoWidth,DominoHeight,Image.SCALE_SMOOTH);
			currDomino = new ImageIcon(newcurrDominoIMG);
			currDrafts.get(i);
			currDrafts.get(i).setIcon(currDomino);
			currDrafts.get(i).setBorder(null);
		}
	}

	private void fillPlaceDraftSpace() {
	    for (int i = 1; i <= 4; i++) {
	        placeDrafts.get(i).setIcon(currDrafts.get(i).getIcon());
	        placeDrafts.get(i).setBorder(currDrafts.get(i).getBorder());
	        placeDrafts.get(i).setEnabled(true);
        }
    }
}
