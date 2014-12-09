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

	public int i;	// First index of node in double array
	public int j;  // Second index of node in double array
	
	public int x;	// X-Coordinate of node on game board
	public int y;   // Y-Coordinate of node on game board

	private int clickTolerance = 10;
	
	//== CONSTRUCTORS ===
	/**
	 * Constructor to be used in conjunction with the Board due to the x, y parameters.
	 * 
	 * @param team	Char representation of team.
	 * @param x		X-Coordinate of Node on game board
	 * @param y		Y-Coordinate of Node on game board
	 * @param i		First index of matrix representation (Ring placement)
	 * @param j		Second index of matrix representation (Where in the ring)
	 */
	public Node(char team, int x, int y, int i, int j) {

		this.x = x;
		this.y = y;
		this.i = i;
		this.j = j;
		this.team = team;
		this.children = new ArrayList<Node>(8);
		
		// Initialize Nodes
		for(int k=0; k<8; k++)
			this.children.add(null);
	}
	
	/**
	 * Constructor to be used when a generic node is needed to make a graph.
	 * 
	 * @param i
	 * @param j
	 */
	public Node(char team, int i, int j){
		
		this.team = team;
		this.i = i;
		this.j = j;
		this.children = new ArrayList<Node>(8);
		
		// Initialize Nodes
		for(int k=0; k<8; k++)
			this.children.add(null);
	}
	
	//=== METHODS ===

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
	 * @return		ArrayList with all nodes responsible for win. Empty ArrayList if no win is present.
	 */
	public ArrayList<Node> winSequence(){
		
		ArrayList<Node> winSequence = new ArrayList<Node>();
		
		if(this.team == Control.NONE)
			return winSequence;
		
		// CHECK VERTICAL WIN
		try{
			if(this.team == this.getTop().getTop().getTeam() 
					&& this.team == this.getTop().getTeam()
					&& this.team == this.getBottom().getTeam()){
				
				winSequence.add(this.getTop().getTop());
				winSequence.add(this.getTop());
				winSequence.add(this);
				winSequence.add(this.getBottom());
				
				return winSequence;
			}
			
		}catch(NullPointerException e){}
		
		// CHECK HORIZONTAL WIN 
		try{
			if(this.team == this.getLeft().getLeft().getTeam()
					&& this.team == this.getLeft().getTeam()
					&& this.team == this.getRight().getTeam()){
				
				winSequence.add(this.getLeft().getLeft());
				winSequence.add(this.getLeft());
				winSequence.add(this);
				winSequence.add(this.getRight());
				
				return winSequence;
			}
			
		}catch(NullPointerException e){}
		
		// CHECK LEFT DIAGNONAL WIN
		try{
			if(this.team == this.getTopLeft().getTopLeft().getTeam()
					&& this.team == this.getTopLeft().getTeam()
					&& this.team == this.getBottomRight().getTeam()){
				
				winSequence.add(this.getTopLeft().getTopLeft());
				winSequence.add(this.getTopLeft());
				winSequence.add(this);
				winSequence.add(this.getBottomRight());
				
				return winSequence;
			}
			
		}catch(NullPointerException e){}
		
		// CHECK RIGHT DIAGONAL WIN
		try{
			if(this.team == this.getTopRight().getTopRight().getTeam()
					&& this.team == this.getTopRight().getTeam()
					&& this.team == this.getBottomLeft().getTeam()){
				
				winSequence.add(this.getTopRight().getTopRight());
				winSequence.add(this.getTopRight());
				winSequence.add(this);
				winSequence.add(this.getBottomLeft());
				
				return winSequence;
			}
			
		}catch(NullPointerException e){}
		
		
		return winSequence; // empty
		
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
