package game;

import java.util.*;

public class Deck {
	
	protected ArrayList<Card> deck;
	
	public Deck() {
		
	}
	
	/*
	 * d = ArrayList of Cards, equal to deck
	 */
	public Deck(ArrayList<Card> d) {
		deck = d;
	}
	
	/*
	 * c = Card to be added to deck
	 * returns true once c is added
	 */
	public boolean addCard(Card c) {
		deck.add(c);
		return true;
	}
	
	/*
	 * c = Card to be removed from deck
	 * returns true if c exists in deck and has been removed
	 */
	public boolean removeCard(Card c) {
		int size1 = deck.size();
		deck.remove(c);
		int size2 = deck.size();
		if (size1 > size2) {
			return true;
		}
		return false;
	}
	
	/* 
	 * returns deck
	 */
	public ArrayList<Card> getDeck() {
		return deck;
	}
	
	/*
	 * returns String containing the cards a player can see
	 */
	public String printDeck(int player) {
		String rep = "";
		for (Card c : deck) {
			rep += c.printCard(player) + " ";
		}
		return rep;
	}
	
	/*
	 * returns size of deck
	 */
	public int size() {
		return deck.size();
	}
	
	/*
	 * randomizes deck
	 */
	public boolean shuffle() {
		Collections.shuffle(deck);
		return true;
	}
	
	/*
	 * sorts deck by rank (min to max)
	 */
	public boolean sortByRank() {
		ArrayList<Card> newDeck = new ArrayList<Card>();
		while (deck.size() > 0) {
			Card card = minCard(deck);
			newDeck.add(card);
			deck.remove(card);
		}
		deck = newDeck;
		return true;
	}
	
	/*
	 * tempDeck = ArrayList of Cards
	 * returns index of card of lowest rank in tempDeck
	 */
	private Card minCard(ArrayList<Card> tempDeck) {
		Card card = tempDeck.get(0);
		int r = card.getRank();
		for (Card c : tempDeck) {
			if (c.getRank() < r) {
				r = c.getRank();
				card = c;
			}
		}
		return card;
	}
	
	
	
}
