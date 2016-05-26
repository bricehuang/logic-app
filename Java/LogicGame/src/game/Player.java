package game;

import java.util.*;

public class Player {
	
	/*
	 * number = 0,1,2,3
	 */
	private int number;
	private Deck deck;
	
	/*
	 * returns number (0,1,2,3) associated with player
	 */
	public int getNumber() {
		return number;
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	/*
	 * returns index in deck of card that player wishes to show partner
	 */
	public int getMoveLogicPartner() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Card: ");
		int cardShown = scan.nextInt();
		scan.close();
		return cardShown;
	}
	
	/*
	 * main move: guessing
	 * returns array containing [player #, index of card guessed, rank guessed]
	 */
	public int[] getMoveLogicGuess() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Player to Guess: ");
		int player = scan.nextInt();
		System.out.println("Card: ");
		int guess = scan.nextInt();
		System.out.println("Rank: ");
		int r = scan.nextInt();
		scan.close();
		return new int[] {player, guess, r};
	}
	
	
}
