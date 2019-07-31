# Uno Strategy
## Description
This graphical Uno simulator, created by Stephen Davies as part of a college Uno programming tournament in fall 2016, runs a series of 
Uno card games against at least two `UnoPlayer` objects. The simulator executes individual player turns by calling unique strategies
outlined by each `UnoPlayer`. Using Stephen’s existing framework, I implemented the `UnoPlayer` interface as `ashtabna_UnoPlayer`, 
which contains tactics for selecting cards from a given hand and calling the color of wild cards. During the actual tournament, 
my Uno strategy advanced to the final round, ultimately earning fourth place. For the purpose of demonstrating this application, 
Stephen provided additional Uno players, which were written by college students from prior semesters. A text-based Uno simulator, 
outlining individual hands and moves, is also provided.

## Game Rules

### Game Play
At the onset of the game, each player is dealt seven cards. The top card of the deck then forms the top of the discard pile (known as the 
up card). During their turn, a player must select one card from their hand that matches the color, number or symbol of the up card. If no 
such card exists, then the player must draw a card from the deck, which can be played immediately, if compatible with the up card. The 
played card then becomes the new up card.

### Deck
An Uno deck consists of four groups (red, green, blue and yellow), each of which contain cards numbered from 0 to 9, as well as three 
types of unnumbered “action cards” or “command cards.” If a command card is played, the next opponent must perform the command delineated 
on the card. “Draw 2” (next player draws two cards and skips their turn) “Skip” (next player skips their turn) and “Reverse” (the 
ordering of turns) are all command cards. In addition to the colored cards, an Uno deck contains two types of uncolored, unnumbered 
“wild cards.” These can be paired with any up card, regardless of color, number or symbol. If a wild card is used, the player gets to 
choose its color. There are two types of wild cards: plain “Wild” and “Wild Draw 4,” which additionally requires that the next 
opponent draw four cards and skip their turn.

#### Card Values
|Type|Value|
|---|---|
|Command|20|
|Wild|50|
|Numbered|Number of card|

### Win State
The first player with an empty hand winds. The numerical value of all opponents’ hands is then added to the winner’s total score.

## Strategy
My strategy consists of removing the greatest possible points at each play. First, I try to locate a valid wild card (with a preference
for “Wild Draw 4” cards) to play for later, if needed. If my hand contains a valid command card, I always return it. When I have 
several command cards to choose from, I examine the game state to determine the most strategic play. In this scenario, I choose a 
reverse card when the player going next has fewer cards than the player who has gone before. Skips are selected only when the player
going next has fewer cards than the player going after. If both a valid skip and a valid reverse are not present, I play “Draw 2.”

<p>In the event that I have no command cards, I proceed by sorting my hand by color and picking a valid candidate for each color group. 
If I have a group of cards corresponding to the color of the up card, I simply find the greatest numbered card within that group. 
Otherwise, I search for the number of the up card in the other groups. I then compare my various candidates, picking the card with the greatest 
number. If there is a tie, I compare the sizes of the groups they came from, choosing the card with the largest group. In the event 
that the tie is not broken, I use the first compared candidate. If I have no numbered or colored candidates, I return a wild card
only as a last resort. If those are also not present, then I draw a card from the deck.

<p>When I need to call a color on a wild card, I return the color of the largest group in my hand. If I have no colored cards, then 
I return the color of the largest group in the discarded pile. If neither contains any colored cards, I return red by default. 

## Interface
To run the simulator, you must include a list of all players in a text file called `players.txt.` The file must contain each players’ 
name, followed by the name that precedes their UnoPlayer class (i.e., `ashtabna` for `ashtabna_UnoPlayer`), separated by commas. 
Both simulators also require the number of Uno games to play, as command line arguments. To run the graphical Uno simulator, simply 
press on the Uno card button on the upper right corner of the application. At the end of the simulation, both programs display the
total number of points accumulated by each player.
