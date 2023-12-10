package GSLCOOP;

import java.io.*;
import java.util.*;

public class Connection {
	
	Scanner scan = new Scanner(System.in);
	
	public void writeFile(String option, String insert) {
		FileWriter file = null;
		PrintWriter print = null;
		
		
		if(option.equals("User")) {
			try {
				file = new FileWriter("user.csv", true);
				print = new PrintWriter(file, true);
				
				print.println(insert);
				
				file.close();
				print.close();
			} catch (Exception e) {
				System.out.println("Failed");
			}
		}else if(option.equals("Team")) {
			try {
				file = new FileWriter("teams.csv", true);
				print = new PrintWriter(file, true);
				
				print.println(insert);
				
				file.close();
				print.close();
			} catch (Exception e) {
				System.out.println("Failed");
			}
		}	
	}
	
	public ArrayList<String> readFile(String option) {
		
		ArrayList<String> rawData = new ArrayList<String>();
		
		if(option.equals("User")) {
			try (BufferedReader reader = new BufferedReader(new FileReader("user.csv"))) {
			
				String headerLine = reader.readLine();
	            String line;
	            while ((line = reader.readLine()) != null) {
	                rawData.add(line);
	            }
	        } catch (Exception e) {
	        	System.out.println("Empty team");
	        }
		}else if(option.equals("Team")) {
			try (BufferedReader reader = new BufferedReader(new FileReader("teams.csv"))) {
				String headerLine = reader.readLine();			
	            String line;
	            while ((line = reader.readLine()) != null) {
	                rawData.add(line);
	            }
	        } catch (Exception e) {
	        	System.out.println("Empty team");
	        }
		}

		return rawData;
	}

}
