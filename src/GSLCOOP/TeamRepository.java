package GSLCOOP;

import java.util.ArrayList;

public class TeamRepository implements Repository {
	
	public ArrayList<Team> teamList = new ArrayList<Team>();
	public ArrayList<ArrayList<User>> teamMember = new ArrayList<ArrayList<User>>();

	public void stringToObject(ArrayList<String> dataTeam) {
		ArrayList<Team> result = new ArrayList<Team>();
		
		for (String team : dataTeam) {
			String[] splitData = team.split(",");
			result.add(new Team(Integer.parseInt(splitData[0]), splitData[1]));
//			System.out.println(splitData[0]+ splitData[1]);
		}
		
		this.teamList = result;
		return;
	}
	
	// dapetin list of team
	public void getDataTeam(Connection conn) {
		ArrayList<String> teamCSV = new ArrayList<String>();
		teamCSV = conn.readFile("Team");
		stringToObject(teamCSV);
	}
	
	// cari sign
	public int getSign(String[] condition){
		if(condition[0].equals("=")) return 1;
		return 0;
	}
	 
	public ArrayList<Team> filterTeam(String col, String[] condition){
		ArrayList<Team> answer = new ArrayList<Team>();
		
		for (Team team : this.teamList) {
			int sign = getSign(condition);
			String requiredValue = condition[1];
			if(col.equals("name")){
				if(sign == 1 && !team.namaTim.equals(requiredValue)) continue;
				if(sign == 0 && team.namaTim.equals(requiredValue)) continue;
				answer.add(team);
			}
			else if(col.equals("id")){
				if(sign == 1 && team.idTim != Integer.parseInt(requiredValue)) continue;
				if(sign == 0 && team.idTim == Integer.parseInt(requiredValue)) continue;
				answer.add(team);
			}
		}
		
		return answer;
	}
	
	public void Fill_TeamMember(Connection conn) {
		UserRepository user = new UserRepository();
		user.getDataUser(conn);
		
		ArrayList<User> userList = user.userList;

		while (teamMember.size() <= teamList.size()) {
			teamMember.add(new ArrayList<User>());
		}

		for (User users : userList) {
			User userNow = new User(null,null,null);
			userNow = users;
			int idTeam = userNow.idTim;
			this.teamMember.get(idTeam).add(userNow);
		}
		
	}

	public Boolean validate(String col, String[] condition, Boolean join, String tableJoin, Connection conn) {
	    if (col == null && condition != null) {
	        return false;
	    }

	    if (col != null && condition == null) {
	        return false;
	    }

	    if (col != null && !(col.equals("id") || col.equals("name"))) {
	        return false;
	    }

	    if (condition != null) {
	        if (condition.length != 2 || condition[0] == null || condition[1] == null) {
	            return false;
	        }

	        if (!(condition[0].equals("=") || condition[0].equals("!="))) {
	            return false;
	        }
	    }

	    if (!join && tableJoin != null) {
	        return false;
	    }

	    if (join && (tableJoin == null || !tableJoin.equals("User"))) {
	        return false;
	    }

	    return conn != null;
	}

	public void find(String col, String[] condition, Boolean join, String tableJoin, Connection conn) {
	    if (!validate(col, condition, join, tableJoin, conn)) {
	        System.out.println("Error: Wrong Condition or Parameter");
	        return;
	    }

	    getDataTeam(conn);

	    if (condition == null) {
	        System.out.println("ID| Team Name");
	        System.out.println("------------------");
	        for (Team team : teamList) {
	            System.out.print(team.idTim);
	            System.out.print(" | " + team.namaTim);
	            System.out.println();
	        }
	    } else {
	        ArrayList<Team> teamAnswer = filterTeam(col, condition);
	        Team team_curr = new Team(null, null);
	        Fill_TeamMember(conn);
	        for (int i = 0; i < teamAnswer.size(); i++) {
	            team_curr = teamAnswer.get(i);
	            System.out.println("ID| Team Name ");
	            System.out.println("------------------");
	            System.out.print(team_curr.idTim);
	            System.out.print(" | " + team_curr.namaTim);
	            System.out.println();
	        }
	    }
	}


	public Team findOne(String col, String[] condition, Boolean join, String tableJoin, Connection conn) {

	    this.getDataTeam(conn);
	    ArrayList<Team> teamAnswer = filterTeam(col, condition);
	    if(teamAnswer.size() == 0) return null;
	    
	    Team teamResult = teamAnswer.get(0);
	    return teamResult;
	}
	
	public void insert(String[] namaTim, Connection conn) {
		String[] pass = new String[]{"=", namaTim[0]};
		
		
		try {
			Team find = findOne("name", pass, false, null, conn);
			if(find.namaTim.equals(namaTim[0])) {
				System.out.println("Team name is already been used, choose another one.");
			}
		} catch (Exception e) {

			Integer max = 0;
			
			for (Team team : teamList) {
				if(team.idTim > max) {
					max = team.idTim;
				}
			}
			
			Integer newTeamID = max+1;
			String newTeamData = newTeamID.toString()+","+ namaTim[0];
			conn.writeFile("Team", newTeamData);
			
			System.out.println("New Team has been registered.");
		}
		
	}

}
