package playingCards;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeMap;

public class GenerateDeck {
	private static LinkedList<Cards> generateMainDeck() {
		LinkedList<Cards> list = new LinkedList<> (); 

		String directoryPath = "images\\card_images\\"; 

		// Using File class create an object for specific directory 
		File directory = new File(directoryPath); 

		// Using listFiles method we get all the files of a directory  
		// return type of listFiles is array 
		File[] files = directory.listFiles(); 

		// Print name of the all files present in that path 
		if (files != null) { 
			for (File file : files) { 
				
				String path = directoryPath + file.getName();
				int id = Integer.parseInt(file.getName().substring(0,3));
				
				if (id <= 76) {
					String color = file.getName().split("_")[1];
					int number = Integer.parseInt(file.getName().split("_")[2].substring(0,1));
					list.add(new NumberCard(color, path, id, number));
				}
				
				if (id > 76 && id <= 80) {
					String color = "black";
					list.add(new WildCard(color, path, id, "change_color"));
				}
				
				if (id > 80 && id <= 104) {
					String color = file.getName().split("_")[1];
					String type = file.getName().split("_")[2];
					list.add(new ActionCard(color, path, id, type));
				}
				
				if (id > 104 && id <= 108) {
					String color = "black";
					list.add(new WildCard(color, path, id, "change_color_plus4"));
				}
			} 
		}
		return list;
	}
	
	public static LinkedList<Cards> loadDeck(String variable) {
		LinkedList<Cards> list = new LinkedList<> (); 

		String directoryPath = "images\\card_images\\"; 

		for (String idStr : variable.split(",")) {

			String path = directoryPath + idStr;

			int id = Integer.parseInt(idStr.substring(0,3));


			if (id <= 76) {
				String color = idStr.split("_")[1];
				int number = Integer.parseInt(idStr.split("_")[2].substring(0,1));
				list.add(new NumberCard(color, path, id, number));
			}

			if (id > 76 && id <= 80) {
				String color = "black";
				list.add(new WildCard(color, path, id, "change_color"));
			}

			if (id > 80 && id <= 104) {
				String color = idStr.split("_")[1];
				String type = idStr.split("_")[2];
				list.add(new ActionCard(color, path, id, type));
			}

			if (id > 104 && id <= 108) {
				String color = "black";
				list.add(new WildCard(color, path, id, "change_color_plus4"));
			}

		}
		return list;
	}

	public static TreeMap<String, LinkedList<Cards>> distributeDeck (int playerNumber, String playerName) {
		TreeMap<String, LinkedList<Cards>> decks = new TreeMap<String, LinkedList<Cards>>();
		
		LinkedList<Cards> list = generateMainDeck();
		Collections.shuffle(list);
		
		for (int i = 1; i <= playerNumber + 1; i++) {
			LinkedList<Cards> aDeck = new LinkedList<> (); 
			
			for (int j = 1; j <= 7; j++) {
				Cards aCard = list.getFirst();
				list.remove(0);
				
				aDeck.add(aCard);
			}
			if (i == playerNumber + 1) decks.put(playerName, aDeck);
			else decks.put("bot" + String.valueOf(i), aDeck);
		}
		Cards card = list.getLast();
		list.remove(card);
		
		decks.put("mid", list);
		LinkedList<Cards> midDeck = new LinkedList<>();
		midDeck.add(card);
		decks.put("throwed", midDeck);
		return decks;
	}
}