import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class WAR {

	final static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
	//declare the global variables and arrays
	final static int players = 2; // number of players
	static Scanner sc = new Scanner(System.in); // the scanner to read console;
	static int[] deck = new int[52];
	static int cardsPerPlayer = 0;
	static int maxRounds = 10;

	public static void main(String[] args) throws Exception {
		//file reader to output rules from a txt file if user needs
		File rules = new File("rules.txt");
		Scanner reader = new Scanner(rules);
		System.out.println("-----------------------------");
		System.out.println("Welcome, to the game of WAR! ");
		System.out.println("-----------------------------");
		System.out.println("Would you like to see the rules to how this game should be played?Type 0 for yes, and any integer for no. :)");
		int rulesCondition = sc.nextInt();

		if (rulesCondition == 0) {
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				System.out.println(data);
			}
		} else {
			System.out.print("Please input the maximum rounds for the game: ");
			maxRounds = sc.nextInt();
		}
		//starting the dealing process
		cardsPerPlayer = deck.length / players;
		for (int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}

		// shuffle the deck
		shuffleDeck(deck);
		
		int[][] tiles = new int[players][deck.length];
		int[] sizeOfTiles = new int[players];
		initializeTiles(tiles, sizeOfTiles);

		int cardSelect;

		int personsInGame = players;
		int rounds = 0;
		while (personsInGame > 1 && rounds < maxRounds) {
			int winner = 0;
			int maxCard = Integer.MIN_VALUE;
			int[] drawnCards = new int[deck.length];
			int sizeOfDrawnCards = 0;
			//prompting for card draw
			for (int person = 0; person < players; person++) {
				System.out.print("Player " + (person + 1) + " has " + sizeOfTiles[person]
						+ " cards. Please choose a card to be drawn: ");
				cardSelect = sc.nextInt();
				if (cardSelect > 26) {
					System.out.println("Invalid card count.");
				}
				//selecting the selected card from the hand of each player
				int cardId = tiles[person][cardSelect];
				System.out.println("You have drawn " + getCardFace(cardId));
				int cardNum = getCardNum(cardId);
				sizeOfDrawnCards = addCardToPile(drawnCards, sizeOfDrawnCards, cardNum);
				sizeOfTiles[person] = removeCardFromTile(tiles[person], sizeOfTiles[person], cardSelect);
				//determining the winner of the round
				if (cardNum > maxCard) {
					maxCard = cardNum;
					winner = person;
				}
			}
			//moving cards over from the loser of the round to the winner
			sizeOfTiles[winner] = mergeTiles(tiles[winner], sizeOfTiles[winner], drawnCards, sizeOfDrawnCards);
			sizeOfDrawnCards = clearTile(drawnCards, sizeOfDrawnCards);
			personsInGame = getPersonsInGame(tiles, sizeOfTiles);
			rounds++; //a counter to count the rounds that the game goes on for

			System.out.println("The winner of this round is player " + (winner + 1));
			System.out.println();

			System.out.println("Player 1 has " + sizeOfTiles[0] + " cards");
			System.out.println("Player 2 has " + sizeOfTiles[1] + " cards");

			System.out.println();

		}
		//if the counter for rounds reaches the max prompted rounds, the game will end
		//if not the game will continue
		if (rounds == maxRounds) {
			System.out.println("The maximum number of inputted rounds has been reached! (" + rounds + " round(s))");
			System.out.println("---------------------------------------------------------------");
			System.out.println("The winner of the game is player " + (getWinner(tiles, sizeOfTiles) + 1));
			System.out.println("---------------------------------------------------------------");
		}

	}
	
	public static int mergeTiles(int[] pile1, int pileSize1, int[] pile2, int pileSize2) {
		for (int i = 0; i < pileSize2; i++) { 
			pile1[i + pileSize1] = pile2[i];
		}
		return pileSize1 + pileSize2;
	}

	public static int clearTile(int[] pile, int pileSize) {
		return 0;
	}

	public static int removeCardFromTile(int[] pile, int sizeOfPile, int pileIndex) {
		for (int i = pileIndex; i < sizeOfPile - 2; i++) {
			pile[i] = pile[i + 1];
		}
		return sizeOfPile - 1;
	}

	public static void initializeTiles(int[][] piles, int[] sizeOfPiles) {

		for (int i = 0; i < players; i++) {
			sizeOfPiles[i] = 0;
		}

		// allocate cards to piles;
		int person = 0;
		for (int deckIndex = 0; deckIndex < deck.length; deckIndex++) {
			sizeOfPiles[person] = addCardToPile(piles[person], sizeOfPiles[person], deck[deckIndex]);
			person = (person + 1) % players;
		}
	}

	public static int addCardToPile(int[] pile, int originalSize, int cardId) {
		pile[originalSize] = cardId;
		return originalSize + 1;
	}

	public static void shuffleDeck(int[] deck) {
		Random random = new Random(deck.length);
		for (int i = 0; i < deck.length; i++) {
			int r = random.nextInt(deck.length);
			int temp = deck[r];
			deck[r] = deck[i];
			deck[i] = temp;
		}
	}

	public static int getWinner(int[][] piles, int[] sizeOfPiles) {
		int maxScore = Integer.MIN_VALUE;
		int winner = -1;
		for (int i = 0; i < piles.length; i++) {
			if (sizeOfPiles[i] > maxScore) {
				maxScore = sizeOfPiles[i];
				winner = i;
			}
		}
		return winner;
	}

	public static int getPersonsInGame(int[][] Piles, int[] sizesOfPiles) {
		int personsInGame = 0;
		for (int person = 0; person < sizesOfPiles.length; person++) {
			if (sizesOfPiles[person] > 0)
				personsInGame++;
		}
		return personsInGame;
	}

	public static int getCardNum(int cardID) {
		int cardNum = cardID % 13;
		if (cardNum == 0)
			cardNum += 13;
		return cardNum;

	}

	public static String getCardFace(int cardId) {
		int suit = cardId / 13;
		int cardNum = cardId % 13;
		String face = suits[suit];
		if (cardNum == 0) {
			return face + " Ace";
		} else if (cardNum >= 10) {
			cardNum -= 10;
			return face + " " + (char) ('J' + cardNum);
		} else {
			return face + " " + (cardNum + 1);
		}

	}

}