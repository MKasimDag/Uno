package dataProcessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
public class Login {

	public static boolean logIn(String username, String password) {
		boolean result = false; 
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"));

			String line;
			try {
				while((line = reader.readLine()) != null) {
					String[] userInfo = line.split(":");
					if (username.equals(userInfo[0]) && password.equals(userInfo[1])) result = true;
				}
				reader.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			//System.err.println("Kullanıcı listesi bulunamadı");
			//e.printStackTrace();
		}

		return result;
	}

	private static boolean isExist(String username) {
		boolean result = false;
		try {
			BufferedReader isExistReader = new BufferedReader(new FileReader("data/users.txt"));

			String line;
			try {
				while((line = isExistReader.readLine()) != null) {
					String[] userInfo = line.split(":");
					if (username.equals(userInfo[0])) result = true;
				}
				isExistReader.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			result = false;
		}
		return result;
	}

	public static int registrar(String username, String password) {
		int status = 0;
		
		if(isExist(username) == true) {
			//System.err.println("Kullanıcı ztn var!!!");
			status = -1;
		}
		
		else {
			if(Character.isUpperCase(username.charAt(0)) && !username.contains(":") && !password.contains(":")) {
				try {
					BufferedWriter writerUserInfo = new BufferedWriter(new FileWriter("data/users.txt", true));

					writerUserInfo.write(username + ":" + password);
					writerUserInfo.newLine();
					writerUserInfo.close();


					BufferedWriter writerUsertxt = new BufferedWriter(new FileWriter(String.format("data/users/%s.txt", username), true));

					writerUsertxt.write("*****UserStatistic*****");
					writerUsertxt.newLine();
					writerUsertxt.write("Num of games:0");
					writerUsertxt.newLine();
					writerUsertxt.write("Num of wins:0");
					writerUsertxt.newLine();
					writerUsertxt.write("Num of loses:0");
					writerUsertxt.newLine();
					writerUsertxt.write("Total score:0");
					writerUsertxt.newLine();
					writerUsertxt.write("*****Games*****");
					writerUsertxt.newLine();
					writerUsertxt.close();
					
					status = 1;
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}

			else {
				//System.err.println("Kullanıcı adları büyük harfle başlamalı!");
				status = -2;
			}
		}
		return status;
	}
	
	public static List<Entry<String, Integer>> getLeaderboardData() {
		HashMap<String, Integer> data = new HashMap<>();
		
		File directory = new File("data/users"); 
		File[] files = directory.listFiles();
		
		if (files != null) { 
			
	        for (File file : files) { 
	        	//File file = new File(filePath);
	        	
	        	try {
					Scanner scanner = new Scanner(file);
					
					while (scanner.hasNextLine()) {
						String aLine = scanner.nextLine();

                        if (aLine.substring(0, 12).equals("Total score:")) {
                            String baseName = file.getName().replaceFirst("\\.txt$", "");
                            //System.out.println(baseName + ":" + aLine.split(":")[1]);
                            data.put(baseName ,Integer.valueOf(aLine.split(":")[1]));
                            break;
                        }
						
					}
				}
	        	catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        } 
	      }
		//Stream<Map.Entry<String, Integer>> sorted = data.entrySet().stream().sorted(Map.Entry.comparingByValue());
		List<Entry<String, Integer>> list = new ArrayList<>(data.entrySet());
        list.sort(Entry.comparingByValue());
        
		return list;
	}
	
	public static HashMap<String, String> getAUserData(String path) {
		HashMap<String, String> userData = new HashMap<>();
		
		File file = new File(path);
		
		try {
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) {
				String aLine = scanner.nextLine();

				if (!aLine.equals("*****Games*****")) {
					if (!aLine.equals("*****UserStatistic*****")) {
						//System.out.println(aLine);
						userData.put(aLine.split(":")[0], aLine.split(":")[1]);
					}
				}
				else break;
			}
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return userData;
	}
}
