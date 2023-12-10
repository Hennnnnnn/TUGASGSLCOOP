package GSLCOOP;
import java.util.*;

import Repo.TeamRepository;
import Repo.UserRepository;

public class Main {
	Scanner scanner = new Scanner(System.in);
	Connection conn = new Connection();

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		mainMenu();
	}
	
	public void mainMenu() {
		System.out.println();
		System.out.println("-----Main Menu-----");
		System.out.println("1. Menu Utama");
		System.out.println("2. Insert Data");
		System.out.println("3. Show Data");
		System.out.println("4. Exit");
		System.out.print("inputut: ");
		Integer src = scanner.nextInt(); 
		scanner.nextLine();
		
		if(src.equals(1)){
			mainMenu();
		}else if(src.equals(2)) {
			insertMenu();
		}else if(src.equals(3)) {
			showMenu();
		}else if(src.equals(4)) {
			System.exit(0);
		}
	}
	
	private void showMenu() {
		System.out.println();
		System.out.print("Which table to show? ");
		System.out.print("1. User, ");
		System.out.println("2. Team");
		System.out.print("input: ");
		Integer src = scanner.nextInt(); scanner.nextLine();
		
		System.out.print("Want to filter by condition? ");
		System.out.print("1. Yes, ");
		System.out.println("2. No");
		System.out.print("input: ");
		Integer cond = scanner.nextInt(); scanner.nextLine();
		
		if(src.equals(1)) {
			showUser(cond);
		}else if(src.equals(2)) { 
			showTeam(cond);
		}
		
	}
	
	public void showUser(Integer condition) {
	    UserRepository userRep = new UserRepository();
	    
	    if (condition.equals(1)) {
	        System.out.println("Add condition:");
	        System.out.println("Format: [name or id or nim];[= or !=];[value]");
	        System.out.print("Condition: ");
	        String conditionInput = scanner.nextLine();
	        
	        System.out.print("Wanna join table with Team? [yes/no]: ");
	        String joinInput = scanner.nextLine();
	        
	        String[] sepCon = conditionInput.split(";");
	        String[] pass = new String[] {sepCon[1], sepCon[2]};
	        
	        boolean join = joinInput.equalsIgnoreCase("yes");
	        
	        userRep.find(sepCon[0], pass, join, "Team", conn);
	    } else if (condition.equals(2)) {
	        System.out.print("Wanna join table with Team? [yes/no]: ");
	        String joinInput = scanner.nextLine();
	        
	        boolean join = joinInput.equalsIgnoreCase("yes");
	        
	        userRep.find(null, null, join, "Team", conn);
	    }
	    
	    mainMenu();
	}
	
	public void showTeam(Integer condition) {
	    TeamRepository teamRep = new TeamRepository();

	    switch(condition) {
	        case 1:
	            System.out.println("add condition, separate by semicolon");
	            System.out.println("format: [name or id];[= or !=];[value]");
	            String con = scanner.nextLine();
	            String[] sepCon = con.split(";");
	            System.out.print("Wanna join table with table User? [yes/no]: ");
	            String join = scanner.next();
	            String[] pass = new String[] {sepCon[1], sepCon[2]};

	            if(join.equals("yes")) {
	            	teamRep.find(sepCon[0], pass, true, "User", conn);
	            } else if(join.equals("no")) {
	            	teamRep.find(sepCon[0], pass, false, null, conn);
	            }
	            break;

	        case 2:
	            System.out.print("Wanna join table with table User? [yes/no]: ");
	            String join2 = scanner.next();

	            if(join2.equals("yes")) {
	            	teamRep.find(null, null, true, "User", conn);
	            } else if(join2.equals("no")) {
	            	teamRep.find(null, null, false, null, conn);
	            }
	            break;

	        default:
	            break;
	    }
	    mainMenu();
	}


	public void insertMenu() {
		System.out.println("");
		System.out.println("---Insert---");
		System.out.println("Which table to insert?");
		System.out.println("1. User");
		System.out.println("2. Team");
		System.out.print("input: ");
		Integer src = scanner.nextInt(); scanner.nextLine();
		
		if(src.equals(1)) {
			insertUser();
		}else if(src.equals(2)) {
			insertTeam();
		}
		
		
		mainMenu();
	}
	
	public void insertUser() {
		String namaUser;
		String userNIM;
		String namaTim;
		
		System.out.print("Add Name: ");
		namaUser = scanner.nextLine();
		System.out.println("Add Nim: ");
		userNIM = scanner.nextLine();
		System.out.println("Add Team: ");
		namaTim = scanner.nextLine();
		
		UserRepository addUser = new UserRepository();
	
		
		String[] passNewUser = new String[]{namaUser, userNIM, namaTim}; 
		addUser.insert(passNewUser, conn);
		
		mainMenu();
	}
	
	public void insertTeam() {
	    System.out.print("Add Team Name: ");
	    String teamName = scanner.nextLine();

	    if (!teamName.isEmpty()) {
	        TeamRepository addTeam = new TeamRepository();
	        addTeam.insert(new String[]{teamName}, conn);

	        System.out.println("Team added successfully!");
	    } else {
	        System.out.println("No team name provided.");
	    }

	    mainMenu();
	}

	
}
