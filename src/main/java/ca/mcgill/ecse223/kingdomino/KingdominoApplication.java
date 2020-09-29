package ca.mcgill.ecse223.kingdomino;

import ca.mcgill.ecse223.kingdomino.view.KingDominoView;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.mcgill.ecse223.kingdomino.controller.Controller;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.view.ScoreResults;
import ca.mcgill.ecse223.kingdomino.view.UserUint;

public class KingdominoApplication {

	private static Kingdomino kingdomino;

	public static void main(String[] args) {

		System.out.println("Hello World!");

		setKingdomino(getKingdomino());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UserUint();
			}
		});
	}

	public static Kingdomino getKingdomino() {
		if (kingdomino == null) {
			kingdomino = new Kingdomino();
		}
		return kingdomino;
	}

	public static void setKingdomino(Kingdomino kd) {
		kingdomino = kd;
	}
	static void makeEmptyGame() {
		Kingdomino kingdomino = new Kingdomino();
		Game game = new Game(48, kingdomino);
		kingdomino.setCurrentGame(game);
		KingdominoApplication.setKingdomino(kingdomino);
		for (int i = 0; i < 4; i++) {
			Player player = new Player(KingdominoApplication.getKingdomino().getCurrentGame());
			player.setColor(Player.PlayerColor.values()[i]);
		}
		
	}
}
	
