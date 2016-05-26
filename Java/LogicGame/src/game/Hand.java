package game;

import java.util.ArrayList;

public class Hand extends Deck {
	
	private Player owner;
	
	public Hand() {
		
	}
	
	/*
	 * d = ArrayList of Cards, equal to deck
	 */
	public Hand(ArrayList<Card> d) {
		deck = d;
	}
	
	/*
	 * returns owner
	 */
	public Player getOwner() {
		return owner;
	}
	
	
	
}
