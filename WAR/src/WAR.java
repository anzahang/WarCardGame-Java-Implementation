import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WAR {

	public static void main(String[] args)throws Exception {
		Scanner sc = new Scanner(System.in);
		// number of players
		int players = 2;

		ArrayList <Integer> deck = new ArrayList<Integer>();
		
		for(int i=0;i<52;i++) {
			deck.add(i);
		}
		
		int cardsPerPlayer = deck.size()/players;
		
		// shuffle
		Random random = new Random(deck.size());
		for (int i = 0; i < deck.size(); i++) {
			int r = random.nextInt(deck.size());
			int temp = deck.get(r);
			deck.set(r, deck.get(i));
			deck.set(i,temp);
		}
		
		int cardSelect;
		ArrayList<Integer>[] tiles = new ArrayList[players];
		for(int i=0;i<players;i++) {
			tiles[i]=new ArrayList<>();
		}
		int person = 0;
		for(int deckIndex=0;deckIndex<deck.size();deckIndex++) {
			tiles[person].add(deck.get(deckIndex));
			person = (person+1) % players;
		}
		int personsInGame = players;
		for(int i=0;personsInGame>1;i++) {
			int winner=0;
			int maxCard = Integer.MIN_VALUE;
			ArrayList<Integer> drawnCards = new ArrayList<>();
			for(int j=0;j<players;j++) {
				System.out.print("You have " + tiles[j].size() + " cards. Please choose a card to be drawn: ");
				cardSelect = sc.nextInt();
				
			
				int cardNum = getCardNum(tiles[j].get(cardSelect));
				drawnCards.add(tiles[j].get(cardSelect));
				tiles[j].remove(cardSelect);
				if(cardNum > maxCard) {
					maxCard = cardNum;
					winner = j;
					
				}
			}
			tiles[winner].addAll(drawnCards);
			personsInGame = getPersonsInGame(tiles);
			
				
		}
		System.out.println("Game over, the winner is " + getWinner(tiles));
		
		
	}

	private static int getWinner(ArrayList<Integer>[] tiles)throws Exception {
		int winner;
		for(int i=0;i<tiles.length;i++) {
			if(tiles[i].size() > 0) {
				return i;
			}
		}
		throw new Exception("Should not be here.");
	}

	private static int getPersonsInGame(ArrayList<Integer>[] tiles) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static int getCardNum(Integer cardID) {
		// TODO Auto-generated method stub
		return 0;
	}

}
