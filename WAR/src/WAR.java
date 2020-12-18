import java.util.Random;
import java.util.Scanner;

public class remake {
	final static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

	final static int players = 2; // number of players
	static Scanner sc = new Scanner(System.in); // the scanner to read console;
	static int[] deck = new int[52];
	static int cardsPerPlayer = 0;
	static int maxRounds = 10;

	public static void main(String[] args) {

			// initialization
			System.out.println("-----------------------------");
			System.out.println("Welcome, to the game of WAR! ");
			System.out.println("-----------------------------");
			System.out.print("Please input the maximum rounds for the game: ");
			maxRounds = sc.nextInt();

			cardsPerPlayer = deck.length / players;
			for (int i = 0; i < deck.length; i++) {
				deck[i] = i;
			}

			// shuffle
			shuffleDeck(deck);

			int[][] hand = new int[players][deck.length];
			int[] sizeOfPiles = new int[players];
			initializePiles(hand, sizeOfPiles);

			int cardSelect;

			int personsInGame = players;
			int rounds = 0;
			while (personsInGame > 1 && rounds < maxRounds) {
				int winner = 0;
				int maxCard = Integer.MIN_VALUE;
				int[] drawnCards = new int[deck.length];
				int sizeOfDrawnCards = 0;

				for (int person = 0; person < players; person++) {
					System.out.print("Player " + (person + 1) + " has " + sizeOfPiles[person] + " cards. Please choose a card to be drawn: ");
					cardSelect = sc.nextInt();
				
					
					int cardId = hand[person][cardSelect];
					System.out.println("You have drawn " + getCardFace(cardId));
					int cardNum = getCardNum(cardId);
					sizeOfDrawnCards = addCardToTile(drawnCards, sizeOfDrawnCards, cardNum);
					sizeOfPiles[person] = removeCardFromPile(hand[person], sizeOfPiles[person], cardSelect);

					if (cardNum > maxCard) {
						maxCard = cardNum;
						winner = person;
					}
				}

				sizeOfPiles[winner] = mergePiles(hand[winner], sizeOfPiles[winner], drawnCards, sizeOfDrawnCards);
				sizeOfDrawnCards = clearPile(drawnCards, sizeOfDrawnCards);
				personsInGame = getPersonsInGame(hand, sizeOfPiles);
				rounds++;

				System.out.println("The winner of this round is player " + (winner + 1));
				System.out.println();

				System.out.println("Player 1 has " + sizeOfPiles[0] + " cards");
				System.out.println("Player 2 has " + sizeOfPiles[1] + " cards");

				System.out.println();

			}
			if(rounds == maxRounds) {
			System.out.println("The maximum number of inputted rounds has been reached! (" + rounds + " round(s))");
			System.out.println("---------------------------------------------------------------");
			System.out.println("The winner of the game is player " + (getWinner(hand, sizeOfPiles) + 1));
			System.out.println("---------------------------------------------------------------");
			}
		}

		public static int mergePiles(int[] tile1, int tileSize1, int[] tile2, int tileSize2) {
			for (int i = 0; i < tileSize2; i++) {
				tile1[i + tileSize1] = tile2[i];
			}
			return tileSize1 + tileSize2;
		}

		public static int clearPile(int[] tile, int tileSize) {
			return 0;
		}

		public static int removeCardFromPile(int[] tile, int sizeOfTile, int tileIndex) {
			for (int i = tileIndex; i < sizeOfTile - 2; i++) {
				tile[i] = tile[i + 1];
			}
			return sizeOfTile - 1;
		}

		public static void initializePiles(int[][] tiles, int[] sizeOfTiles) {

			for (int i = 0; i < players; i++) {
				sizeOfTiles[i] = 0;
			}

			// allocate cards to piles;
			int person = 0;
			for (int deckIndex = 0; deckIndex < deck.length; deckIndex++) {
				sizeOfTiles[person] = addCardToTile(tiles[person], sizeOfTiles[person], deck[deckIndex]);
				person = (person + 1) % players;
			}
		}

		public static int addCardToTile(int[] tile, int originalSize, int cardId) {
			tile[originalSize] = cardId;
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

		public static int getWinner(int[][] tiles, int[] sizeOfTiles) {
			int maxScore = Integer.MIN_VALUE;
			int winner = -1;
			for (int i = 0; i < tiles.length; i++) {
				if (sizeOfTiles[i] > maxScore) {
					maxScore = sizeOfTiles[i];
					winner = i;
				}
			}
			return winner;
		}

		public static int getPersonsInGame(int[][] tiles, int[] sizesOfTiles) {
			int personsInGame = 0;
			for (int person = 0; person < sizesOfTiles.length; person++) {
				if (sizesOfTiles[person] > 0)
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
