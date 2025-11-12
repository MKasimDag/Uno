package dataProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

import playingCards.Cards;
import playingCards.GenerateDeck;

public class Games {
	
	public static String getGameId() {
		String id;
		try {
			BufferedReader idReader = new BufferedReader(new FileReader("data/users.txt"));
			
			id = idReader.readLine().split(":")[1];
			
			idReader.close();
		}
		catch (FileNotFoundException e) {
			id = "err";
			e.printStackTrace();
		}
		catch (IOException e) {
			id = "err";
			e.printStackTrace();
		}
		return id;
	}
	
    private static String listToString(LinkedList<Cards> list) {
        String result = "";
        
        for (Cards aCard : list) {
            String fileName = new File(aCard.getPath()).getName();
            result = result.concat(fileName).concat(",");
        }
        if (result.length() != 0) result = result.substring(0, result.length() - 1);
        //System.out.println(result);
        return result;
    }
	
	private static void updateGameNumber(String path) {

		File txtFile = new File(path);
		BufferedReader reader = null;
		FileWriter writer = null;
		StringBuilder content = new StringBuilder();

		try {
			reader = new BufferedReader(new FileReader(txtFile));
			String satir;
			int i = 1;
			
			while ((satir = reader.readLine()) != null) {
				
				if (i == 1) {
					content.append(satir.split(":")[0] + ":" + String.valueOf(Integer.valueOf(satir.split(":")[1]) + 1)).append("\n");
				}
				else {
					content.append(satir).append("\n");
				}
				i++;
			}

			// Dosyayı tekrar yazmak için FileWriter kullanın
			writer = new FileWriter(txtFile);
			writer.write(content.toString());
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (reader != null) reader.close();
				if (writer != null) writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createGame(String username, int playerNumber, String sessionName) {
		updateGameNumber("data/users.txt");
		
		String gameId = getGameId();
		TreeMap<String, LinkedList<Cards>> decks = GenerateDeck.distributeDeck(playerNumber - 1, username);
		
		
		
		try {
			BufferedWriter writeGameToUser = new BufferedWriter(new FileWriter(String.format("data/users/%s.txt", username), true));
			
			writeGameToUser.write("game_" + gameId);
			writeGameToUser.newLine();
			writeGameToUser.close();
			
			
			BufferedWriter gameSession = new BufferedWriter(new FileWriter(String.format("data/gamelogs/game_%s.txt", gameId), false));
			
			gameSession.write("*****Begining Of Game*****");
			gameSession.newLine();
			gameSession.write("Session Name:" + sessionName);
			gameSession.newLine();
			gameSession.write("Num of players:" + String.valueOf(decks.size() - 2));
			gameSession.newLine();
			
			for (String key : decks.keySet()) {
				gameSession.write(key + ":" + listToString(decks.get(key)));
				gameSession.newLine();
			}

			gameSession.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static TreeMap<String, LinkedList<Cards>> loadGame(String gameNames) {
		File txtFile = new File(String.format("data/gamelogs/%s.txt", gameNames));
		BufferedReader reader = null;
		TreeMap<String, LinkedList<Cards>> decks = new TreeMap<String, LinkedList<Cards>>();

		try {
			reader = new BufferedReader(new FileReader(txtFile));
			String satir;
			int i = 1;
			boolean cont = true;
			while ((satir = reader.readLine()) != null) {
				if(satir.equals("*****End of Save*****")) cont = false;
				if (i > 3 && cont) {
					System.out.println(satir);
					//if (satir.equals("*****Gameplay*****"))
					if (satir.endsWith(":")) decks.put(satir.split(":")[0], new LinkedList<Cards>());
					else decks.put(satir.split(":")[0], GenerateDeck.loadDeck(satir.split(":")[1]));
					
				}
				i++;
			}
			//System.out.println("while bitti");
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (reader != null) reader.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return decks;
	}
	public static void saveGame(String gameNames, TreeMap<String, LinkedList<Cards>> decks) {
	    File txtFile = new File(String.format("data/gamelogs/%s.txt", gameNames));
	    ArrayList<LinkedList<Cards>> list = new ArrayList<LinkedList<Cards>>(decks.values());
	    ArrayList<String> list2 = new ArrayList<String>(decks.keySet());

	    try {
            BufferedReader br = new BufferedReader(new FileReader(txtFile));

            String line;
            StringBuilder firstThreeLines = new StringBuilder();
            int count = 0;
            while ((line = br.readLine()) != null && count < 3) {
                firstThreeLines.append(line).append("\n");
                System.out.println(line);
                count++;
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile));
            bw.write(firstThreeLines.toString());
            

            int k = 0;
            while ((line = br.readLine()) != null) {
            	System.out.println(line);
                bw.write(list2.get(k) + ":" + combinePaths(list, k));
                bw.newLine();
                k++;
            }
            bw.write(list2.get(k) + ":" + combinePaths(list, k));
            bw.newLine();

            br.close();
            bw.close();
            
        } catch (IOException e) {
            System.out.println("Dosya okuma veya yazma hatası: " + e.getMessage());
        }

	}

    private static String combinePaths(ArrayList<LinkedList<Cards>> list, int index) {
        LinkedList<Cards> cardsList = list.get(index);
        StringBuilder combinedPathsBuilder = new StringBuilder();
        
        for (int i = 0; i < cardsList.size(); i++) {
            combinedPathsBuilder.append(new File(cardsList.get(i).getPath()).getName());

            if (i < cardsList.size() - 1) {
                combinedPathsBuilder.append(",");
            }
        }
        
        return combinedPathsBuilder.toString();
    }
	public static String[] gameList(String filePath) {

		ArrayList<String> gameList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isGamesSection = false;
            while ((line = br.readLine()) != null) {
            	if (isGamesSection) {
            		gameList.add(line);
            	} else if (line.equals("*****Games*****")) {
            		isGamesSection = true;
            	}
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }

        return gameList.toArray(new String[0]);
	}
	public static void setPoints(String username, String winner, TreeMap<String, LinkedList<Cards>> decks) {
		ArrayList<LinkedList<Cards>> list = new ArrayList<LinkedList<Cards>>(decks.values());
		ArrayList<String> list2 = new ArrayList<String>(decks.keySet());

		if (username.equals(winner)) {
			int points = 0;
			String[] gamelists = gameList(String.format("data/users/%s.txt", username));
			for(String key : decks.keySet()) {
				if (!key.equals("throwed") && !key.equals("mid")) {
					LinkedList<Cards> aList = decks.get(key);
					for (Cards aCard : aList) {
						points += aCard.getScoreVal();
					}
				}
			}
			File txtFile2 = new File(String.format("data/users/%s.txt", username));

			try {
				BufferedReader br = new BufferedReader(new FileReader(txtFile2));
				StringBuilder fileContent = new StringBuilder();
				String line;
				int count = 0;

				while ((line = br.readLine()) != null && count < 6) {
					if (count < 5) {
						if (count == 0) {
							fileContent.append("*****UserStatistic*****").append("\n");
						} else {

							String[] parts = line.split(":");
							String value1 = parts[0].trim() + ":";
							String value2 = parts[1].trim();

							if (value1.equals("Total score:")) {
								fileContent.append(String.format("Total score:%d", Integer.valueOf(value2) + points)).append("\n");

							}
							if (value1.equals("Num of loses:")) {
								fileContent.append("Num of loses:" + value2).append("\n");
							}
							else {
								if(!value1.equals("Total score:"))
									fileContent.append(String.format(value1 + String.valueOf(Integer.valueOf(value2) + 1))).append("\n");
							}
						}
					}
					else {
						fileContent.append("*****Games*****\n");
					}
					count++;
				}
				br.close();

				BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile2));
				bw.write(fileContent.toString());

				for (String gameName : gamelists) {
					bw.write(gameName);
					bw.newLine();
				}

				bw.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		else {

			String[] gamelists = gameList(String.format("data/users/%s.txt", username));
			File txtFile2 = new File(String.format("data/users/%s.txt", username));

			try {
				BufferedReader br = new BufferedReader(new FileReader(txtFile2));
				StringBuilder fileContent = new StringBuilder();
				String line;
				int count = 0;

				while ((line = br.readLine()) != null && count < 6) {
					if (count < 5) {
						if (count == 0) {
							fileContent.append("*****UserStatistic*****").append("\n");
						} else {

							String[] parts = line.split(":");
							String value1 = parts[0].trim() + ":";
							String value2 = parts[1].trim();

							if (value1.equals("Total score:")) {
								fileContent.append("Total score:" + value2).append("\n");

							}
							if (value1.equals("Num of wins:")) {
								fileContent.append("Num of wins:" + value2).append("\n");
							}
							else {
								if(!value1.equals("Total score:"))
									fileContent.append(String.format(value1 + String.valueOf(Integer.valueOf(value2) + 1))).append("\n");
							}
						}
					}
					else {
						fileContent.append("*****Games*****\n");
					}
					count++;
				}
				br.close();

				BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile2));
				bw.write(fileContent.toString());

				for (String gameName : gamelists) {
					bw.write(gameName);
					bw.newLine();
				}

				bw.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
