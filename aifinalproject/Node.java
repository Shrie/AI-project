package aifinalproject;

import java.util.ArrayList;

/**
 * Data structure to represent a playable location on the Tic-Tack-Toe board.
 * 
 *  Used to represent current game state for GUI as well as a Object Oriented graph, 
 *  currently used for win-checking.
 */
public class Node {

	//=== VARIABLES ===
	private char team;					// Character representation of team, usually X or O
	private ArrayList<Node> children;   // All adjacent nodes

	public int x;	// X-Coordinate of node on game board
	public int y;   // Y-Coordiatte of node on game board

	private int clickTolerance = 10;
	
	//== CONSTRUCTOR ===
	/**
	 * Only constructor.
	 * 
	 * @param x		X-Coordinate of Node on game board
	 * @param y		Y-Coordinate of Node on game board
	 */
	public Node(int x, int y) {

		this.x = x;
		this.y = y;
		this.team = Control.NONE; // Initially set to NONE
		this.children = new ArrayList<Node>(8);
		
		// Initialize Nodes
		for(int i=0; i<8; i++)
			this.children.add(null);
	}

	/**
	 * Checks to see if click is within range of a node.
	 * 
	 * @param i		X-Coordinate of click
	 * @param j		Y-Coordinate of click
	 * @return		True if click is within tolerance of a node, false if not.
	 */
	public boolean isInRange(int i, int j) {

		if (i < x + clickTolerance 
				&& i > x - clickTolerance 
				&& j < y + clickTolerance 
				&& j > y - clickTolerance)
			return true;

		return false;
	}

	/**
	 * @return		Character representing Node's player status, usually X or O
	 */
	public char getTeam() {

		return team;
	}

	/**
	 * @param x		New character to set Node, restricted to PLAYER1, PLAYER2, or NONE
	 */
	public void setTeam(char x) {

		if (x == Control.PLAYER1 || x == Control.PLAYER2 || x == Control.NONE)
			team = x;

	}
	
	/** 
	 * @return		ArrayList of all adjacent nodes.
	 */
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
	 * TODO return a number to explain which test (vertical, horizonal, left diag, etc) to be able to map 
	 * which nodes are in a winning sequence
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
		
	}// END winFound()
	
	/**
	 * Prints all adjacent nodes to the game console.
	 */
	public void printChildren(){
		
		if(!children.isEmpty())
			for(int i=0; i<children.size(); i++)
				if(children.get(i) != null)
					Interface.print(String.format("N%d: %c", i, children.get(i).getTeam()));
		
	}

}// END NODE
