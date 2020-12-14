
public class WAR {

	public static void main(String[] args) {

		// number of players
		int players = 2;

		String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };

		String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };

		int n = suits.length * ranks.length;
		int cardsPerPlayer = n/players;
		
		// initialize deck
		String[] deck = new String[n];
		for (int i = 0; i < ranks.length; i++) {
			for (int j = 0; j < suits.length; j++) {
				deck[suits.length * i + j] = ranks[i] + " of " + suits[j];
			}
		}

		// shuffle
		for (int i = 0; i < n; i++) {
			int r = (i + (int) (Math.random() * (n - i)))%n;
			String temp = deck[r];
			deck[r] = deck[i];
			deck[i] = temp;
		}

		// print shuffled deck
		for (int i = 0; i < n; i++) {
			System.out.println(deck[i]);
			if (i % cardsPerPlayer == cardsPerPlayer - 1)
				System.out.println();
		}
	}

}
