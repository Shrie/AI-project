package aifinalproject;

import java.awt.Point;
import java.util.ArrayList;
/**
 * Object to represent a single state in the game.
 */
public class StateSpace {
	
	//==== CONSTANTS ===
	public static final int TERMINAL = -1;
	
	//=== VARIABLES ===
	private Node[][] state;					// Current state in game
	private ArrayList<StateSpace> children; // Enumerated possibilities  
	
	private long counter;
	
	//=== CONSTRUCTORS ===
	/**
	 * Creates stateSpace out of Node matrix. 
	 * Links Nodes to form graph.
	 * 
	 * @param state		Some state-space
	 */
	public StateSpace(Node[][] state){
		
		this.state = state;
		this.children = new ArrayList<StateSpace>();
		
		linkNodes(); // Forms graph
	}
	
	/**
	 * Creates stateSpace out of char matrix.
	 * Creates new Nodes without Board coordinates. 
	 * 
	 * @param state		Some state-space
	 */
	public StateSpace(char[][] state){
		
		Node[][] copy = new Node[state.length][]; // Prepare Node matrix
		
		for(int i=0; i<copy.length; i++)
			for(int j=0; j<copy[i].length; j++)
				copy[i][j] = new Node(state[i][j], i, j); // Copy data from char[][]
		
		this.state = copy;
		this.children = new ArrayList<StateSpace>();
		
		linkNodes(); // Forms graph
	}
	
	//=== METHODS ===
	public ArrayList<ArrayList<Node>> getOpenEndedPairs(char player){
		
		ArrayList<ArrayList<Node>> oep = new ArrayList<ArrayList<Node>>();
		
		
		
		
		return oep;
	}
	
	public ArrayList<ArrayList<Node>> getPairs(char player){
		
		ArrayList<ArrayList<Node>> pairs = new ArrayList<ArrayList<Node>>();
		
		for(int i=0; i<state.length; i++)
			for(int j=0; j<state[i].length; j++);
		
		return null;
	}
	
	private boolean sequenceExists(ArrayList<ArrayList<Node>> data, ArrayList<Node> sequence){
		
		if(data == null)
			return false;
		
		if(data.isEmpty())
			return false;
		
		
		
		for(int i=0; i<data.size(); i++){ // Cycle through each sequence in list
			
			if(data.get(i).size() == sequence.size()){ // If sequences are the same size
				
				boolean allExist = true; // Assume they all exist in this sequence
				
				for(int j=0; j<sequence.size(); j++){ // Cycle through each Node in the sequence to check
					
					if(!nodeExists(data.get(i), sequence.get(j)))
						allExist = false; // If Node i in sequence is not found
					
				}
				
				if(allExist) // All are present in this sequence
					return true;
				
			}
			
		}
			
		return false;
	}
	/**
	 * Checks each nodes i, j components to see if a particular Node is present
	 * in the given ArrayList.
	 * 
	 * @param data		List to check.
	 * @param node		Node to check for.
	 * @return			True if Node with same i,j assignment exists in ArrayList, 
	 * 					false if not.
	 */
	private boolean nodeExists(ArrayList<Node> data, Node node){
		
		if(data == null)
			return false;
		
		if(data.isEmpty())
			return false;
		
		for(int i=0; i<data.size(); i++)
			if(data.get(i).i == node.i && data.get(i).j == node.j)
				return true;
		
		return false;		
	}
	
	/**
	 * Cycles through each node in state-space, checking for a winning sequence.
	 * 
	 * @return		ArrayList containing each Node in a winning sequence or empty ArrayList 
	 * 				if no win is present.
	 */
	public ArrayList<Node> checkForWinSequence(){
		
		for(int i=0; i<state.length; i++)
			for(int j=0; j<state[i].length; j++)
				if(!state[i][j].winSequence().isEmpty())
					return state[i][j].winSequence();
		
		return new ArrayList<Node>();
	}
	
	/**
	 * Creates and returns a new Object mirroring the current instance.
	 * Does not carry over Board x,y coordinates from each Node.
	 * 
	 * @return		A new StateSpace which is a clone of this instance.
	 */
	public StateSpace copy(){
		
		Node[][] n =  new Node[state.length][12];
		
		for(int i=0; i<state.length; i++)			
			for(int j=0; j<state[i].length; j++)
				n[i][j] =  new Node(state[i][j].getTeam(), state[i][j].x, state[i][j].y, i, j);
			
		
		return new StateSpace(n);
	}
	
	/**
	 * @return		Enumerated child state-spaces
	 */
	public ArrayList<StateSpace> getChildren(){
		
		return children;
	}
	
	/**
	 * Sets all Node teams to NONE.
	 * Sets children to new empty ArrayList.
	 */
	public void reset(){
		
		if(state != null)
			for(int i=0; i<state.length; i++)
				for(int j=0; j<state[i].length; j++)
					state[i][j].setTeam(Control.NONE);
		
		this.children = new ArrayList<StateSpace>();
	}
	
	/**
	 * @return		True if no moves have been made on board yet, false if there has.
	 */
	public boolean noMovesMade(){
		
		if(state != null)
			for(int i=0; i<state.length; i++)
				for(int j=0; j<state[i].length; j++)
					if(state[i][j].getTeam() != Control.NONE)
						return false;
		
		return true;
	}
	
	/**
	 * Prints this stateSpace to console (not game console)
	 */
	public void printStateSpace(){
		
		if(state != null)
			for(int i=0; i<state.length; i++){
				for(int j=0; j<state[i].length; j++)
					System.out.print("[" + state[i][j].getTeam() + "]");
				
				System.out.println();
			}
	}
	
	/**
	 * Sets any particular node in state-space to value.
	 * 
	 * @param player	Char representation of player played.
	 * @param i			i index of Node location (Ring location)
	 * @param j			j index of Node location (Where in ring)
	 */
	public void setNode(char player, int i, int j){
		
		this.state[i][j].setTeam(player);
	}
	
	/**
	 * Enumerates all legal states possible and stores them in children of this instance.
	 * Runs recursively for each child to depth generations. 
	 * 
	 * @param depth
	 */
	public void expandStateSpace(int depth){
		
		expandSS(this, depth);
		
		
		Interface.print("StateSpaces Enumerated ");
		Interface.print("" + numberOfStates());
	}
	
	public long numberOfStates(){
		 
		counter = 0; // Reset
		count(this);
		
		return counter;
	}
	
	private void count(StateSpace root){
		
		if(root.getChildren().isEmpty())
			return;
		
		counter += root.getChildren().size();
		
		for(int i=0; i<root.getChildren().size(); i++)
			count(root.getChildren().get(i)); 
	}
	
	/**
	 * Recursive method of enumerating all possible states for the next player to take.
	 * 
	 * @param root
	 * @param depth
	 */
	private void expandSS(StateSpace root, int depth){
				
		ArrayList<Node> frontier = root.frontier(); // Every valid option
		
		if((depth == TERMINAL && frontier.isEmpty()) || depth == 0) // Base-case return
			return;
		
		for(int i=0; i<frontier.size(); i++){ // For each of those options
			
			StateSpace ss = root.copy(); // Copy the state-space
			
			char player = (player1Turn())? Control.PLAYER1 : Control.PLAYER2; // Determine who's turn
			
			ss.setNode(player, frontier.get(i).i, frontier.get(i).j); // Make that move to the new state-space
			
			root.getChildren().add(ss); // Add modified state-space to children of root
		}
	
		int newDepth = (depth == TERMINAL)? TERMINAL : depth - 1; // Decrement if not a terminal expansion

		for(int i=0; i<root.getChildren().size(); i++) // Repeat for all children
			expandSS(root.getChildren().get(i), newDepth);
		
	}// END expandSS()
	
	/**
	 * Counts all plays on the board to determine who's turn it is.
	 * 
	 * @return		True if same number of plays made for both (include none at all), false if Player 1 has one
	 * 				extra play.
	 */
	public boolean player1Turn(){
		
		int p1Moves = 0;
		int p2Moves = 0;
		
		if(state != null)
			for(int i=0; i<state.length; i++)
				for(int j=0; j<state[i].length; j++)
					if(state[i][j].getTeam() == Control.PLAYER1)
						p1Moves++;
					else if(state[i][j].getTeam() == Control.PLAYER2)
						p2Moves++;
		
		if(p1Moves == p2Moves)
			return true;
		
		return false;
		
	}// END player1Turn()
	
	/**
	 * Creates a graph by linking all nodes by referencing "children" in "parent" node's arraylist.
	 */
	public void linkNodes(){
	
		if(state != null)
			for(int i=0; i<state.length; i++)
				for(int j=0; j<state[i].length; j++){ // Cycle through each node
					
					// Allows wrap-around of state
					int offset1 = (j == 0)? -11 : 1; 
					int offset2 = (j == state[i].length - 1)? -11 : 1;
				
					if(i != 0){
						state[i][j].addTopLeft(state[i - 1][j - offset1]);	// Top Left
						state[i][j].addTop(state[i - 1][j]);				// Top Middle
						state[i][j].addTopRight(state[i - 1][j + offset2]);	// Top Right
					}
					
					state[i][j].addLeft(state[i][j - offset1]);				// Left
					state[i][j].addRight(state[i][j + offset2]); 	  	    // Right
					
					if(i != state.length - 1){
						state[i][j].addBottomLeft(state[i + 1][j - offset1]); // Bottom Left
						state[i][j].addBottom(state[i + 1][j]);				  // Bottom Middle
						state[i][j].addBottomRight(state[i + 1][j + offset2]);// Bottom Right
					}
				}
				
	}// END linkNodes()
	
	/**
	 * @return		Node matrix representation of state-space.
	 */
	public Node[][] getStateSpace(){
		
		return state;
	}
	
	/**
	 * @return		char matrix representation of state-space.
	 */
	public char[][] getCharStateSpace(){
		
		 char[][] ss = new char[state.length][12];
		 
		 for(int i=0; i<state.length; i++)
			 for(int j=0; j<state[i].length; j++)
				 ss[i][j] = state[i][j].getTeam();
		 
		 return ss;
	}
	
	/**
	 * Moves through each node in state-space. If location is valid for a move, then that Node (location) 
	 * is added to the ArrayList.
	 * 
	 * @return		ArrayList of all legal places to move. List is empty if there are none.
	 */
	public ArrayList<Node> frontier(){
		
		ArrayList<Node> frontier = new ArrayList<Node>();
		
		if(state != null)
			for(int i=0; i<state.length; i++)
				for(int j=0; j<state[i].length; j++)
					if(noMovesMade())
						frontier.add(state[i][j]);
					else if(!invalidMove(i, j))
						frontier.add(state[i][j]);
				
		return frontier;
	}
	
	/**
	 * Checks i,j coordinates for an invalid move.
	 * Returns true if location already has been played
	 * or if there are no played moves adjacent (or diagonal) to it.
	 * 
	 * @param i		x-coordinate of matrix interpretation of game board.
	 * @param j		y-coordinate of matrix interpretation of game board.
	 * @return		false if valid move, true if not valid
	 */
	public boolean invalidMove(int i, int j) {

		// Check for out-of-bounds indices
		if (i < 0 || i > state.length - 1 || j < 0 || j > 11)
			return true;
		
		// Check if exact node has already been played
		if (state[i][j].getTeam() != Control.NONE)
			return true;
		
		// Check for any adjacent played nodes
		return !adjacentPlayed(i, j);

	} // END invalidMove()
	
	
	/**
	 * Checks if any adjacent node from reference has been played, includes
	 * check for diagonals.
	 * 
	 * @param i		x-coordinate of node in matrix representation of game board.
	 * @param j		y-coordinate of node in matrix representation of game board.
	 * @return		true if any node adjacent or immediately diagonal has been played,
	 * 				false if none have been played yet.
	 */
	public boolean adjacentPlayed(int i, int j) {
		
		ArrayList<Node> adj = state[i][j].getChildren();
		
		for(int k=0; k<adj.size(); k++)
			if(adj.get(k) != null)
				if(adj.get(k).getTeam() != Control.NONE)
					return true;
			
				
		return false;

	} // END adjacentPlayed() 
	
}// END STATESPACE
