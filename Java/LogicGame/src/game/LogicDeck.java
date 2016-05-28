package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

 /*
  * I made LogicDeck and Hand separate classes because 
  * LogicDeck supports operations (like shuffle, deal) 
  * that Hand doesn't need to support, and vice versa 
  * (like sort and move).  
  * 
  * Most of this is your code, just moved around lol
  */

/**
 * Represents a full deck of cards in Logic
 */
public class LogicDeck {
    private final List<Card> deck;
    
    /**
     * Constructs a LogicDeck with all cards
     */
    public LogicDeck(){
        deck = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            deck.add(new Card("S", i));
            deck.add(new Card("D", i));
        }
    }
    
    /**
     * Randomly permutes deck
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }
    
    /**
     * Deals a deck randomly to four players
     * @return a List of four hands, representing the hands dealt
     */
    public List<Hand> deal(){
        this.shuffle();
        List<ArrayList<Card>> handsAsLists = new ArrayList<ArrayList<Card>>();
        for (int i=0; i<4; i++){
            handsAsLists.add(new ArrayList<Card>());
        }
        for (int card=0; card<24; card++){
            handsAsLists.get(card%4).add(deck.get(card));
        }
        
        List<Hand> dealtHands = new ArrayList<>();
        for (int i=0; i<4; i++){
            dealtHands.add(new Hand(handsAsLists.get(i)));
        }
        return dealtHands;
    }
    
}
