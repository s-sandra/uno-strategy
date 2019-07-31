package uno;

import java.util.ArrayList;
import java.util.List;

public class ashtabna_UnoPlayer implements UnoPlayer {
GameState gameState = new GameState();
    /**
     * play - This method is called when it's your turn and you need to
     * choose what card to play.
     *
     * The hand parameter tells you what's in your hand. You can call
     * getColor(), getRank(), and getNumber() on each of the cards it
     * contains to see what it is. The color will be the color of the card,
     * or "Color.NONE" if the card is a wild card. The rank will be
     * "Rank.NUMBER" for all numbered cards, and another value (e.g.,
     * "Rank.SKIP," "Rank.REVERSE," etc.) for special cards. The value of
     * a card's "number" only has meaning if it is a number card. 
     * (Otherwise, it will be -1.)
     *
     * The upCard parameter works the same way, and tells you what the 
     * up card (in the middle of the table) is.
     *
     * The calledColor parameter only has meaning if the up card is a wild,
     * and tells you what color the player who played that wild card called.
     *
     * Finally, the state parameter is a GameState object on which you can 
     * invoke methods if you choose to access certain detailed information
     * about the game (like who is currently ahead, what colors each player
     * has recently called, etc.)
     *
     * You must return a value from this method indicating which card you
     * wish to play. If you return a number 0 or greater, that means you
     * want to play the card at that index. If you return -1, that means
     * that you cannot play any of your cards (none of them are legal plays)
     * in which case you will be forced to draw a card (this will happen
     * automatically for you.)
     */
    public int play(List<Card> hand, Card upCard, Color calledColor,
        GameState state) {
    	
    	gameState = state;
    	Color colorOfUpCard = upCard.getColor();
    	int numberOfUpCard = upCard.getNumber();
    	List<Card> commandCards = new ArrayList<Card>();
    	List<Card> wildCards = new ArrayList<Card>();
    	Card commandCandidate = null;
    	Card wildCandidate = null;
    	
    	//determines if the upCard is a wild card.
    	if(upCard.getColor() == Color.NONE){
    		colorOfUpCard = calledColor;
    	}
    	
    	//finds all the command cards in the hand.
    	for(Card card: hand){
    		if(card.getNumber() == -1 && card.getRank() != Rank.WILD &&
    		   card.getRank() != Rank.WILD_D4){
    			commandCards.add(card);
    		}
    	}
    	
    	//finds all the wild cards in the hand.
    	for(Card card: hand){
    		if(card.getNumber() == -1 && (card.getRank() == Rank.WILD ||
    		   card.getRank() == Rank.WILD_D4)){
    			wildCards.add(card);
    		}
    	}
    	
    	//searches for a wild+4 card in the wild card list.
    	//if there is no wild+4, but there is a wild, assigns
    	//the wildCanidate to the wild.
    	for(Card card: wildCards){
    		if(card.getRank() == Rank.WILD_D4){
    			wildCandidate = card;
    			break;
    		}
    		else if(card.getRank() == Rank.WILD){
    			wildCandidate = card;
    		}
    	}
  
    	//determines if the hand contains command cards.
    	if(commandCards.size() > 0){
    		commandCandidate = chooseCommandCandidate(commandCards, colorOfUpCard,
    				upCard.getRank());
	    	
	    	//determines if a command candidate exists and returns it.
	    	if(commandCandidate != null){
	    		return hand.indexOf(commandCandidate);
	    	}
    	}
    	
    	//filters the hand by color, ignoring command cards.
    	List<Card> redCards = filterByColor(hand, Color.RED);
    	List<Card> blueCards = filterByColor(hand, Color.BLUE);
    	List<Card> yellowCards = filterByColor(hand, Color.YELLOW);
    	List<Card> greenCards = filterByColor(hand, Color.GREEN);
    	
    	//picks a valid candidate within each color group. Otherwise, 
    	//chooseCandidate returns null.
    	Card greenCandidate = chooseCandidate(greenCards, colorOfUpCard, 
    			Color.GREEN, numberOfUpCard);
    	Card redCandidate = chooseCandidate(redCards, colorOfUpCard, 
    			Color.RED, numberOfUpCard);
    	Card blueCandidate = chooseCandidate(blueCards, colorOfUpCard, 
    			Color.BLUE, numberOfUpCard);
    	Card yellowCandidate = chooseCandidate(yellowCards, colorOfUpCard, 
    			Color.YELLOW, numberOfUpCard);
    	 
    	List<Card> candidates = new ArrayList<Card>();
    	
    	//adds a candidate to the candidates ArrayList,
    	//if it exists.
    	if(greenCandidate != null){
    		candidates.add(greenCandidate);
    	}
    	if(redCandidate != null){
    		candidates.add(redCandidate);
    	}
    	if(blueCandidate != null){
    		candidates.add(blueCandidate);
    	}
    	if(yellowCandidate != null){
    		candidates.add(yellowCandidate);
    	}
    	
    	Card candidate = null;
    	
    	//accounts for only one element in the candidates ArrayList.
    	if(candidates.size() == 1){
    		return hand.indexOf(candidates.get(0));
    	}
    	
    	//determines which candidate has the greatest number.
    	//if there is a tie, it picks the card which belongs to
    	//the largest group of colors.
    	if(candidates.size() > 0){
    		candidate = candidates.get(0);
	    	for(int i = 1; i < candidates.size(); i++){
	    		if(candidate.getNumber() < candidates.get(i).getNumber()){
	    			candidate = candidates.get(i);
	    		}
	    		else if(candidate.getNumber() == candidates.get(i).getNumber()){
	    			Color c1Color = candidate.getColor();
	    			int c1Length = 0;
	    			switch(c1Color){ //finds the size of the group candidate one belongs to.
	    			case RED:
	    				c1Length = redCards.size();
	    				break;
	    			case BLUE:
	    				c1Length = blueCards.size();
	    				break;
	    			case GREEN:
	    				c1Length = greenCards.size();
	    				break;
	    			case YELLOW:
	    				c1Length = yellowCards.size();
	    				break;
	    			default: break;
	    			}
	    			
	    			Color c2Color = candidates.get(i).getColor();
	    			int c2Length = 0;
	    			switch(c2Color){ //finds the size of the group candidate two belongs to.
		    			case RED:
		    				c2Length = redCards.size();
		    				break;
		    			case BLUE:
		    				c2Length = blueCards.size();
		    				break;
		    			case GREEN:
		    				c2Length = greenCards.size();
		    				break;
		    			case YELLOW:
		    				c2Length = yellowCards.size();
		    				break;
		    			default: break;
	    			}
	    			
	    			if(c2Length < c1Length){
	    				candidate = candidates.get(i);
	    			}
	    		}
	    	}
    	}
    	
    	//if there is no command card candidate,returns
    	//a numbered candidate, if it exists.
    	if(candidate != null){
    		return hand.indexOf(candidate);
    	}
    	
    	//if there is no other candidate, searches for a wild
    	//card and returns it.
    	if(wildCandidate != null){
    		return hand.indexOf(wildCandidate);
    	}
        
        return -1;
    }


    /**
     * callColor - This method will be called when you have just played a
     * wild card, and is your way of specifying which color you want to 
     * change it to.
     *
     * You must return a valid Color value from this method. You must not
     * return the value Color.NONE under any circumstances.
     */
    public Color callColor(List<Card> hand) {
    	
    	Color colorCall = getGreatestColor(hand);
    	
    	//if the hand contains no colored cards,
    	//returns the color least found in the deck.
    	if(colorCall == null){
    		colorCall = getGreatestColor(gameState.getPlayedCards());
    	}
    	
    	if(colorCall != null){
    		return colorCall;
    	}
    	
        return Color.RED;
    }
    
    /**
     * getGreatestColor - This method returns the largest group
     * of colors in a given list of cards.
     */
    private Color getGreatestColor(List<Card> cards) {
    	
    	List<Card> redCards = filterByColor(cards, Color.RED);
    	List<Card> blueCards = filterByColor(cards, Color.BLUE);
    	List<Card> yellowCards = filterByColor(cards, Color.YELLOW);
    	List<Card> greenCards = filterByColor(cards, Color.GREEN);
    	
    	List<Integer> colorTotals = new ArrayList<Integer>();
    	
    	int redTotal = redCards.size();
    	int blueTotal = blueCards.size();
    	int yellowTotal = yellowCards.size();
    	int greenTotal = greenCards.size();
    	
    	colorTotals.add(redTotal);
    	colorTotals.add(blueTotal);
    	colorTotals.add(yellowTotal);
    	colorTotals.add(greenTotal);
    	
    	Color colorCall = null;
    	
    	//finds the color of the largest group of 
    	//colors in the hand.
    	for(int i = 0; i < colorTotals.size(); i++){
    		int largestTotal = 0;
    		if(largestTotal < colorTotals.get(i)){
    			largestTotal = colorTotals.get(i);
	    		switch(i){
	    			case 0:
	    				colorCall = Color.RED;
	    				break;
	    			case 1:
	    				colorCall = Color.BLUE;
	    				break;
	    			case 2:
	    				colorCall = Color.YELLOW;
	    				break;
	    			case 3:
	    				colorCall = Color.GREEN;
	    				break;
	    		}	
    		}
    	}
    	
    	return colorCall;
    }
    
    
    /**
     * filterByColor - This method will create a list
     * containing only cards of a specified color, ignoring 
     * command cards.
     */
    private List<Card> filterByColor (List<Card> hand, Color color){ 
    	
    	List<Card> cardList = new ArrayList<Card>();
    	for(Card card: hand){
    		if(card.getColor() == color && card.getNumber() != -1){
    			cardList.add(card);
    		}
    	}
    	return cardList;
    }
    
    
    /**
     * chooseCommandCandidate - This method returns a valid play from
     * a given list of command cards. If there are several valid
     * plays, then the game state is considered.
     */
     private Card chooseCommandCandidate (List<Card> cards, Color upCardColor,
    		 Rank upCardRank){ 
    	
    	List<Card> commandCandidates = new ArrayList<Card>();
     	Card commandCard = null;
     	
     	for(Card card : cards){
     		if(card.getColor() == upCardColor){
     			commandCandidates.add(card);
     			commandCard = card;
     		}
     		else if(card.getRank() == upCardRank){
     			commandCandidates.add(card);
     			commandCard = card;
     		}
     	}
     	
     	if(commandCandidates.size() == 1){
     		return commandCandidates.get(0);
     	}
     	
     	//at this point, commandCandidates has more than one valid 
     	//choice. Therefore, we must choose the more strategic card.
     	int[] playerCardTotals = gameState.getNumCardsInHandsOfUpcomingPlayers();
     	int nextPlayerCardTotal = playerCardTotals [0];
     	int secondPlayerCardTotal = playerCardTotals [1];
     	int lastPlayerCardTotal = playerCardTotals [playerCardTotals.length - 1];
     	
     	for(Card card : commandCandidates){
     		if(card.getRank() == Rank.REVERSE &&
     		   lastPlayerCardTotal > nextPlayerCardTotal){
     			return card;
     		}
     		else if(card.getRank() == Rank.SKIP &&
     				secondPlayerCardTotal > nextPlayerCardTotal){
     			return card;
     		}
     		else if(card.getRank() == Rank.DRAW_TWO){
     			return card;
     		}
     	}
     	
     	return commandCard;
     }
     
     
    /**
    * getGreatestNumberedCard - This method returns the greatest number 
    * from a given list of cards, assuming that it doesn't 
    * contain command cards.
    */
    private Card getGreatestNumberedCard (List<Card> cardList){ 
    	
    	Card greatestNumber = null;

    	if(cardList.size() > 0){
        	greatestNumber = cardList.get(0);
        	for(int i = 1; i < cardList.size(); i++){
        		if(cardList.get(i).getNumber() > greatestNumber.getNumber()) {
        			greatestNumber = cardList.get(i);
        		}
        	}
    	}
    	    	
    	return greatestNumber;
    }
    
    
    /**
     * chooseCandidate - This method chooses a possible candidate
     * based on the upCard's color and number, from a given list
     * of cards, each with the same color.
     */
    private Card chooseCandidate(List<Card> hand, Color upCardColor, Color listColor,
    		int upCardNumber){
    	
    	Card candidate = null;
    	
    	if(upCardColor == listColor){
    		return getGreatestNumberedCard(hand);
    	}
    	for(Card card : hand){
    		if(upCardNumber == card.getNumber()){
    			return card;
    		}
    	}
    	return candidate;
    }
}
