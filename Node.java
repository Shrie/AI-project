package aifinalproject;

public class Node {

	private char team;
	
	public int x;
	public int y;
	
	
	public Node(int x, int y){
		
		this.x = x;
		this.y = y;
		this.team = Control.NONE;
		
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
		
		if(x == Control.TEAM1 || x == Control.TEAM2)
			this.team = x;
		
	}
	
}
