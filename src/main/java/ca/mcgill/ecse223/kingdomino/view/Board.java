package ca.mcgill.ecse223.kingdomino.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.controller.*;

public class Board extends JPanel {
	
	private int totalWidth, gridWidth;
	private Color color;
	// private ArrayList<TODominoInKingdom> dominos;
	
	public Board(int gridWidth, Color color) {
		this.gridWidth = gridWidth;
		this.totalWidth = gridWidth * 9;
		this.color = color;
		this.setLayout(null);
	}
	
	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
		this.totalWidth = gridWidth * 9;
	}
	
	public void doDrawing(Graphics g) {
        this.setBackground(Color.WHITE);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setPaint(color);
		
		// g2d.draw(new Line2D.Float(0, 0, totalWidth, 0));
		// g2d.draw(new Line2D.Float(0, totalWidth, totalWidth, totalWidth));
		// g2d.draw(new Line2D.Float(0, 0, 0, totalWidth));
		// g2d.draw(new Line2D.Float(totalWidth, 0, totalWidth, totalWidth));
		
		for(int i = 0; i <= 9; ++i) {
			Line2D newHorizontalLine = new Line2D.Float(0, gridWidth * i, totalWidth, gridWidth * i);
			Line2D newVerticalLine = new Line2D.Float(gridWidth * i, 0, gridWidth * i, totalWidth);
			g2d.draw(newHorizontalLine);
			g2d.draw(newVerticalLine);
			this.setSize(totalWidth, totalWidth);
			this.setAlignmentX(CENTER_ALIGNMENT);
			this.setAlignmentY(CENTER_ALIGNMENT);
	        // this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		}
		
		// TODO draw castle
		
		// for(TODominoInKingdom i : dominos) {
		//	addDomino(i.getX(), i.getY(), i.getDirection());
		// }
	}

	public void addCastle(JLabel castle, int x, int y) {
		int screenX, screenY, width, height;
			screenX = (x + 4) * gridWidth;
			screenY = (-y + 4) * gridWidth;
			width = gridWidth;
			height = gridWidth;

		ImageIcon largecastle = new ImageIcon(String.format("src/main/java/ca/mcgill/ecse223/kingdomino/view/Castle.png"));
		Image lcimg = largecastle.getImage();
		Image newlcimg = lcimg.getScaledInstance(gridWidth,gridWidth,Image.SCALE_SMOOTH);
		largecastle = new ImageIcon(newlcimg);

		castle.setIcon(largecastle);
		this.add(castle);
		castle.setLocation(screenX, screenY);
		castle.setBounds(screenX, screenY, width, height);
	}

	public void addDomino(JLabel domino, int x, int y, String direction) {
		int screenX, screenY, width, height;
		if(direction.equalsIgnoreCase("right")) {
			screenX = (x + 4) * gridWidth;
			screenY = (-y + 4) * gridWidth;
			width = 2 * gridWidth;
			height = gridWidth;
		} else if(direction.equalsIgnoreCase("left")) {
			screenX = (x + 3) * gridWidth;
			screenY = (-y + 4) * gridWidth;
			width = 2 * gridWidth;
			height = gridWidth;
		} else if(direction.equalsIgnoreCase("down")) {
			screenX = (x + 4) * gridWidth;
			screenY = (-y + 4) * gridWidth;
			width = gridWidth;
			height = 2 * gridWidth;
		} else if(direction.equalsIgnoreCase("up")) {
			screenX = (x + 4) * gridWidth;
			screenY = (-y + 3) * gridWidth;
			width = gridWidth;
			height = 2 * gridWidth;
		} else {
			throw new IllegalArgumentException("Illegal direction " + direction);
		}
		
		// TODO add domino
		
		Image image = ((ImageIcon) domino.getIcon()).getImage();
		domino.setIcon(new ImageIcon(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
		this.add(domino);
		domino.setLocation(screenX, screenY);
		domino.setBounds(screenX, screenY, width, height);
	}
	
	public void moveDomino(JLabel domino, String direction) {
		if(direction.equals("up")) {
			domino.setLocation(domino.getX(), domino.getY() - gridWidth);
		} else if(direction.equals("down")) {
			domino.setLocation(domino.getX(), domino.getY() + gridWidth);
		} else if(direction.equals("left")) {
			domino.setLocation(domino.getX() - gridWidth, domino.getY());
		} else if(direction.equals("right")) {
			domino.setLocation(domino.getX() + gridWidth, domino.getY());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
}