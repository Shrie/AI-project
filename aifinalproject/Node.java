package aifinalproject;

import java.util.ArrayList;

public class Node {

	private char team;
	private ArrayList<Node> children;

	public int x;
	public int y;

	public Node(int x, int y) {

		this.x = x;
		this.y = y;
		this.team = Control.NONE;
		this.children = new ArrayList<Node>(8);
		
		for(int i=0; i<8; i++)
			this.children.add(null);
	}

	public boolean isInRange(int i, int j) {

		if (i < x + 10 && i > x - 10 && j < y + 10 && j > y - 10)
			return true;

		return false;

	}

	public char getTeam() {

		return team;
	}

	public void setTeam(char x) {

		if (x == Control.PLAYER1 || x == Control.PLAYER2 || x == Control.NONE)
			team = x;

	}
	
	
	public ArrayList<Node> getChildren(){
		return children;
	}
	
	//=== ADD CHILDREN ===
	public void addTopLeft(Node n){
		children.add(0, n);
	}
	
	public void addTop(Node n){
		children.add(1, n);
	}
	
	public void addTopRight(Node n){
		children.add(2, n);
	}
	
	public void addLeft(Node n){
		children.add(3, n);
	}
	
	public void addRight(Node n){
		children.add(4, n);
	}
	
	public void addBottomLeft(Node n){
		children.add(5, n);
	}
	
	public void addBottom(Node n){
		children.add(6, n);
	}
	
	public void addBottomRight(Node n){
		children.add(7, n);
	}
	
	//=== GET CHILDREN ===
	public Node getTopLeft(){
		return children.get(0);
	}
	
	public Node getTop(){
		return children.get(1);
	}
	
	public Node getTopRight(){
		return children.get(2);
	}
	
	public Node getLeft(){
		return children.get(3);
	}
	
	public Node getRight(){
		return children.get(4);
	}
	
	public Node getBottomLeft(){
		return children.get(5);
	}
	
	public Node getBottom(){
		return children.get(6);
	}
	
	public Node getBottomRight(){
		return children.get(7);
	}
	
	
	/**
	 * Checks all nodes relative to it, up to two spaces away to check for a win.
	 * 
	 * @return		true if a winning sequence relative to node, false if no winning sequence can be found.
	 */
	public boolean winFound(){
		
		if(this.team == Control.NONE)
			return false;
		
		// CHECK VERTICAL WIN
		try{
			if(this.team == this.getTop().getTop().getTeam() 
					&& this.team == this.getTop().getTeam()
					&& this.team == this.getBottom().getTeam())
				return true;
			
		}catch(NullPointerException e){}
		
		// CHECK HORIZONTAL WIN 
		try{
			if(this.team == this.getLeft().getLeft().getTeam()
					&& this.team == this.getLeft().getTeam()
					&& this.team == this.getRight().getTeam())
				return true;
			
		}catch(NullPointerException e){}
		
		// CHECK LEFT DIAGNONAL WIN
		try{
			if(this.team == this.getTopLeft().getTopLeft().getTeam()
					&& this.team == this.getTopLeft().getTeam()
					&& this.team == this.getBottomRight().getTeam())
				return true;
			
		}catch(NullPointerException e){}
		
		// CHECK RIGHT DIAGONAL WIN
		try{
			if(this.team == this.getTopRight().getTopRight().getTeam()
					&& this.team == this.getTopRight().getTeam()
					&& this.team == this.getBottomLeft().getTeam())
				return true;
			
		}catch(NullPointerException e){}
		
		return false;
	}
	
	
	public void printChildren(){
		
		if(!children.isEmpty())
			for(int i=0; i<children.size(); i++)
				if(children.get(i) != null)
					Interface.print(String.format("N%d: %c", i, children.get(i).getTeam()));
		
	}

}
