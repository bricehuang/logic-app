package game;

public class Card {
	
	/* 
	 * suit = "S" (spades) or "D" (diamonds) 
	 * rank = 1, ..., 12
	 * show = true if face up, false if face down (0,1,2,3 refer to different players)
	 */
	private String suit;
	private int rank;
	private boolean[] show = new boolean[4];
	
	/*
	 * Card can be constructed by inputting suit ("S" or "D") and rank ([1,12])
	 * is face down for all four players
	 */
	public Card(String s, int r) {
		if (s.equals("S") || s.equals("D")) {
			suit = s;
		}
		if (1 <= r && r <= 12) {
			rank = r;
		}
		show[0] = false;
		show[1] = false;
		show[2] = false;
		show[3] = false;
	}
	
	/*
	 * player = 0,1,2,3
	 * makes show[player] true
	 * returns true if player is 0,1,2,3; else false
	 */
	public boolean makeVisible(int player) {
		if (player < 0 || player >= 4) {
			return false;
		}
		show[player] = true;
		return true;
	}
	
	/*
	 * player = 0, 1, 2, 3
	 * returns String containing suit and rank if face up for player, else just the suit
	 */
	public String printCard(int player) {
		if (player >= 4 || player < 0) {
			return "Error";
		}
		if (show[player]) {
			return suit + rank;
		}
		return suit;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getRank() {
		return rank;
	}
	
	
	
}
