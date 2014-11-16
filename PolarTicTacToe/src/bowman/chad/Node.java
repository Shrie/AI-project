package bowman.chad;

import java.util.Random;


public class Node {

	private char team;
	
	public int x;
	public int y;
	
	public static final char TEAM1 = 'X', 
					  		 TEAM2 = 'O',
					  		 NONE = 'N';
	
	public Node(int x, int y){
		
		this.x = x;
		this.y = y;
		this.team = NONE;
		
	}
	
	public boolean isInRange(int i, int j){
		
		if(i < x + 10 && i > x - 10 && j < y + 10 && j > y - 10)
			return true;
		
		
		return false;
		
	}
	
	public char getTeam(){
		
		return team;
	}
	
	public void setTeam(char x){
		
		if(x == TEAM1 || x == TEAM2)
			this.team = x;
		
	}
	
}
