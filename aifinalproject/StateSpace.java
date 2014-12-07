package aifinalproject;

import java.util.ArrayList;
/**
 * Object to represent a single state in the game.
 */
public class StateSpace {
	
	//==== CONSTANTS ===
	public static final int TERMINAL = -1,
							HEURISTIC1 = 1,
							HEURISTIC2 = 2;
	
	//=== VARIABLES ===
	private Node[][] state;					// Current state in game
	private ArrayList<StateSpace> children; // Enumerated possibilities  
	private int minimaxValue;				// Value used in Minimax
	
	private int counter;	// Used for counting the number of state-spaces in an enumeration
	
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
	
	public boolean checkForDraw(){
		
		for(int i=0; i<state.length; i++)
			for(int j=0; j<state[i].length; j++)
				if(state[i][j].getTeam() == Control.NONE)
					return false;
		
		return true;
	}
	
	public void setMinimaxValue(int value){
		
		this.minimaxValue = value;
	}
	
	public int getMinimaxValue(){
		
		return minimaxValue;
	}
	
	/**
     * TODO
     *
     * @author Mason
     * @param player	Player character, either PLAYER1 or PLAYER2
     * @param board 2d char array representing board and moves
     * @return	TODO
     */
    public int heuristic1(char player) {

        char[][] board = getCharStateSpace();

        int rows = board.length;
        int cols = board[0].length;
        int score1 = 0;
        int score2 = 0;
        char p1 = Control.PLAYER1;
        char p2 = Control.PLAYER2;
        int c = 10; //constant for exponentials

        //look for 4s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols]) && (curr == board[row][(col + 3) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 4);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 4);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col]) && (curr == board[row + 2][col]) && (curr == board[row + 3][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 4);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 4);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 4);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 4);
                        }
                    }

                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 4);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 4);
                        }
                    }
                }
            }
        }

        //look for 3s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 2; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col]) && (curr == board[row + 2][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 2; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {

                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }

                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }
                }
            }
        }

        //look for 2s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 2);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 2);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 1; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 2);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 2);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if (curr == board[row + 1][(col - 1 + cols) % cols]) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 2);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 2);
                        }
                    }

                    if (curr == board[row + 1][(col - 1 + cols) % cols]) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 2);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 2);
                        }
                    }
                }
            }
        }

        //maybe change to other values for minimax
        if (player == p1) {
            return (score1 - score2);
        } else {
            return (score2 - score1);
        }

    }// END heuristic1()
    
    
    public int heuristic2(char player){
    	
    	char opponent = (player == Control.PLAYER1)? Control.PLAYER2 : Control.PLAYER1;
    	
    	if(!checkForWinSequence().isEmpty()) // You win this turn
    		return 5;
    	
    	if(!getOpenTriples(opponent).isEmpty()) // Opponent is one move away from a win
    		return -4;
    	
    	if(!getOpenEndedPairs(opponent).isEmpty()) // Opponent confirms a win in two moves
    		return -3;
    	
    	if(!getOpenEndedPairs(player).isEmpty()) // You confirm a win in two moves
    		return 3;
    	
    	
    	return 0;
    }
    
    
    
	/**
	 * Gathers triples still with a playable location. Does not gather closed triples.
	 * Ex. XXNX and XXXN 
	 * 
	 * @param player	Player to gather for
	 * @return			List of open triples.
	 */
	public ArrayList<Set> getOpenTriples(char player){
		
		ArrayList<Set> trips = new ArrayList<Set>();
		ArrayList<Set> spec = getSpecialCases(player);
		
		for(int i=0; i<spec.size(); i++) 
			if(spec.get(i).set.size() == 3)
				trips.add(spec.get(i)); // Add all the triples from special cases
										// XXNX special open trip
		
		for(int i=0; i<state.length; i++)
			for(int j=0; j<state[i].length; j++){ // Cycle through each node in SS
				
				Node n = state[i][j];
				
				if(n.getTeam() == player){ // Node is a match
					
					// Vertical check
					try{
						if(n.getBottom().getTeam() == player
								&& n.getTop().getTeam() == player
								&& n.getTop().getTop().getTeam() == Control.NONE)
							trips.add(new Set(n.getBottom(), n, n.getTop(), Set.VERTICAL));
				
					}catch(NullPointerException e){}
					
					try{
						if(n.getBottom().getTeam() == player
								&& n.getTop().getTeam() == player
								&& n.getBottom().getBottom().getTeam() == Control.NONE)
							trips.add(new Set(n.getBottom(), n, n.getTop(), Set.VERTICAL));
				
					}catch(NullPointerException e){}
					
					// Horizontal check
					if(n.getLeft().getTeam() == player
							&& n.getRight().getTeam() == player
							&& n.getRight().getRight().getTeam() == Control.NONE)
						trips.add(new Set(n.getLeft(), n, n.getRight(), Set.HORIZONTAL));
					
					if(n.getLeft().getTeam() == player
							&& n.getRight().getTeam() == player
							&& n.getLeft().getLeft().getTeam() == Control.NONE)
						trips.add(new Set(n.getLeft(), n, n.getRight(), Set.HORIZONTAL));
					
					// Left Diagonal Check
					try{
						if(n.getTopLeft().getTeam() == player
								&& n.getBottomRight().getTeam() == player
								&& n.getTopLeft().getTopLeft().getTeam() == Control.NONE)
							trips.add(new Set(n.getTopLeft(), n, n.getBottomRight(), Set.LEFT_DIAGONAL));
						
					}catch(NullPointerException e){}
					
					try{
						if(n.getTopLeft().getTeam() == player
								&& n.getBottomRight().getTeam() == player
								&& n.getBottomRight().getBottomRight().getTeam() == Control.NONE)
							trips.add(new Set(n.getTopLeft(), n, n.getBottomRight(), Set.LEFT_DIAGONAL));
						
					}catch(NullPointerException e){}
					
					// Right Diagonal Check
					try{
						if(n.getTopRight().getTeam() == player
								&& n.getBottomLeft().getTeam() == player
								&& n.getTopRight().getTopRight().getTeam() == Control.NONE)
							trips.add(new Set(n.getTopRight(), n, n.getBottomLeft(), Set.RIGHT_DIAGNONAL));
						
					}catch(NullPointerException e){}
					
					try{
						if(n.getTopRight().getTeam() == player
								&& n.getBottomLeft().getTeam() == player
								&& n.getBottomLeft().getBottomLeft().getTeam() == Control.NONE)
							trips.add(new Set(n.getTopRight(), n, n.getBottomLeft(), Set.RIGHT_DIAGNONAL));
						
					}catch(NullPointerException e){}
				}
				
			}
		
		return trips;
		
	} // END getOpenTriples()
	
	/**
	 * Gathers single-ended pairs.
	 * A single ended pair is something like this OXXNN
	 * 
	 * @param player	Player to gather pairs for
	 * @return			A list of single ended pairs.
	 */
	public ArrayList<Set> getSingleEndedPairs(char player){
		
		ArrayList<Set> cases = getSpecialCases(player);
		ArrayList<Set> pairs = new ArrayList<Set>();
		
		for(int i=0; i<cases.size(); i++)
			if(cases.get(i).set.size() == 2) // If case is a double, not a trip
				pairs.add(cases.get(i));	 // Then it's what we need
		
		return pairs;
	}
	
	/**
	 * Gathers pairs which have two open locations on one side or triples with
	 * One open location in the middle of it. EX: OXXNN or XXNX
	 * Does not work as expected for rings > 4 as the vertical pairs are not tested
	 * against "blocks" Ex. OXXNN, the O represents a block
	 * 
	 * @param player	Player to gather for
	 * @return			List of sets, some doubles and some trips
	 */
	private ArrayList<Set> getSpecialCases(char player){
		
		ArrayList<Set> pairs = getPairs(player);
		ArrayList<Set> spec = new ArrayList<Set>();
		
		char opponent = (player == Control.PLAYER1)? Control.PLAYER2 : Control.PLAYER1;
				
		for(int i=0; i<pairs.size(); i++){ // Cycle through each pair
			
			Node first = pairs.get(i).set.get(0);
			Node second = pairs.get(i).set.get(1);
			
			switch(pairs.get(i).direction){
			case Set.VERTICAL:
				
				// Opening Above
				try{
					if(second.getTop().getTeam() == Control.NONE
						&& second.getTop().getTop().getTeam() == Control.NONE) // XXNN
							spec.add(pairs.get(i));
					
					if(second.getTop().getTeam() == Control.NONE
							&& second.getTop().getTop().getTeam() == player){ // XXNX
						
								pairs.get(i).set.add(second.getTop().getTop()); // Add third Node
								spec.add(pairs.get(i));			
					}
				}catch(NullPointerException e){}
				
				// Opening Below
				try{
					if(first.getBottom().getTeam() == Control.NONE
						&& first.getBottom().getBottom().getTeam() == Control.NONE) // XXNN
							spec.add(pairs.get(i));
					
					if(first.getBottom().getTeam() == Control.NONE
							&& first.getBottom().getBottom().getTeam() == player){ // XXNX
								
								pairs.get(i).set.add(first.getBottom().getBottom()); // Add third Node
								spec.add(pairs.get(i));
					}
				}catch(NullPointerException e){}
				
				break;
				
			case Set.HORIZONTAL:
				if(second.getLeft().getTeam() == Control.NONE 
					&& second.getLeft().getLeft().getTeam() == Control.NONE
					&& first.getRight().getTeam() == opponent)
						spec.add(pairs.get(i));
				
				if(second.getLeft().getTeam() == Control.NONE 
					&& second.getLeft().getLeft().getTeam() == player){
					
						pairs.get(i).set.add(second.getLeft().getLeft());
						spec.add(pairs.get(i));
				}
				
				if(first.getRight().getTeam() == Control.NONE
					&& first.getRight().getRight().getTeam() == Control.NONE
					&& second.getLeft().getTeam() == opponent) //TODO for rings > 4, vertical pairs would need to check for blocks like this
						spec.add(pairs.get(i));
				
				if(first.getRight().getTeam() == Control.NONE
					&& first.getRight().getRight().getTeam() == player){
					
						pairs.get(i).set.add(first.getRight().getRight());
						spec.add(pairs.get(i));
				}
				
				break;
				
			case Set.LEFT_DIAGONAL:
				try{
					if(second.getTopLeft().getTeam() == Control.NONE 
						&& second.getTopLeft().getTopLeft().getTeam() == Control.NONE)
							spec.add(pairs.get(i));
					
					if(second.getTopLeft().getTeam() == Control.NONE 
						&& second.getTopLeft().getTopLeft().getTeam() == player){
						
							pairs.get(i).set.add(second.getTopLeft().getTopLeft());
							spec.add(pairs.get(i));
					}
				}catch(NullPointerException e){}
				
				try{
					if(first.getBottomRight().getTeam() == Control.NONE
						&& first.getBottomRight().getBottomRight().getTeam() == Control.NONE)
							spec.add(pairs.get(i));
					
					if(first.getBottomRight().getTeam() == Control.NONE
						&& first.getBottomRight().getBottomRight().getTeam() == player){
						
							pairs.get(i).set.add(first.getBottomRight().getBottomRight());
							spec.add(pairs.get(i));
					}
				}catch(NullPointerException e){}
				
				break;
				
			case Set.RIGHT_DIAGNONAL:
				try{
					if(second.getTopRight().getTeam() == Control.NONE 
						&& second.getTopRight().getTopRight().getTeam() == Control.NONE)
							spec.add(pairs.get(i));
					
					if(second.getTopRight().getTeam() == Control.NONE 
						&& second.getTopRight().getTopRight().getTeam() == player){
						
							pairs.get(i).set.add(second.getTopRight().getTopRight());
							spec.add(pairs.get(i));
					}
				}catch(NullPointerException e){}
				
				try{
					if(first.getBottomLeft().getTeam() == Control.NONE
						&& first.getBottomLeft().getBottomLeft().getTeam() == Control.NONE)
							spec.add(pairs.get(i));
					
					if(first.getBottomLeft().getTeam() == Control.NONE
						&& first.getBottomLeft().getBottomLeft().getTeam() == player){
						
							pairs.get(i).set.add(first.getBottomLeft().getBottomLeft());
							spec.add(pairs.get(i));
					}
				}catch(NullPointerException e){}
				
				break;
				
			}
		}
		
		return spec;
		
	} // END getSpecialCases()
	
	/**
	 * Complies a list of open-ended pairs. An open-ended pair is one which has a frontier node on
	 * each side of the pair. Ex. NXXN
	 * 
	 * @param player	The player to compile the data for
	 * @return			And ArrayList of Sets with direction and open ends.
	 */
	public ArrayList<Set> getOpenEndedPairs(char player){
		
		ArrayList<Set> pairs = getPairs(player); // Grab all pairs
		ArrayList<Set> oep = new ArrayList<Set>();
		
		for(int i=0; i<pairs.size(); i++){ // Cycle through each set
		
			Node first = pairs.get(i).set.get(0);
			Node second = pairs.get(i).set.get(1);
			
			// Vertical Pairs
			try{
				if(pairs.get(i).direction == Set.VERTICAL)
					if(first.getBottom().getTeam() == Control.NONE 
						&& second.getTop().getTeam() == Control.NONE)
							oep.add(pairs.get(i));
					
			}catch(NullPointerException e){}
				
			
			// Horizontal Pairs
			if(pairs.get(i).direction == Set.HORIZONTAL)
				if(first.getRight().getTeam() == Control.NONE
					&& second.getLeft().getTeam() == Control.NONE)
						oep.add(pairs.get(i));
					
			
			
			// Left Diagonal Pairs
			try{
				if(pairs.get(i).direction == Set.LEFT_DIAGONAL)
					if(first.getBottomRight().getTeam() == Control.NONE
						&& second.getTopLeft().getTeam() == Control.NONE)
							oep.add(pairs.get(i));
					
			}catch(NullPointerException e){}
			
			
			// Right Diagonal Pairs
			try{
				if(pairs.get(i).direction == Set.LEFT_DIAGONAL)
					if(first.getBottomLeft().getTeam() == Control.NONE
						&& second.getTopRight().getTeam() == Control.NONE)
							oep.add(pairs.get(i));
				
			}catch(NullPointerException e){}
			
			
		}
		
		return oep;
		
	} // END getOpenEndedPairs()
	
	/**
	 * Searches the entire state-space for adjacent Nodes which share the 
	 * same char value. Will not add sets which are actually greater than pairs.
	 * 
	 * @param player	Player to search for, typically X or O
	 * @return			An ArrayList of Sets, adjacent pairs of Nodes on the board
	 */
	public ArrayList<Set> getPairs(char player){
		
		ArrayList<ArrayList<Node>> pairs = new ArrayList<ArrayList<Node>>();
		ArrayList<Set> sets = new ArrayList<Set>();
		
		for(int i=0; i<state.length; i++)
			for(int j=0; j<state[i].length; j++){ // Cycle through each Node in state-space
				
				Node n = state[i][j]; // Current reference Node
				ArrayList<Node> set;
				
				if(n.getTeam() == player){ // If node belongs to the player of interest
					
					// Vertical Check
					if(n.getTop() != null)
						if(n.getTop().getTeam() == player){ // Top matches, could be possible pair. 
							
							// Test to make sure pair is not actually a triple or greater.
							boolean tripple = false;
							
							if(n.getTop().getTop() != null)
								if(n.getTop().getTop().getTeam() == player)
									tripple = true;
							
							if(n.getBottom() != null)
								if(n.getBottom().getTeam() == player)
									tripple = true;
		 
							
							set = new ArrayList<Node>(); // Prepare a new AL
							set.add(n); // Disrupting this add order and others will cause problems in getOpenEndedPairs()
							set.add(n.getTop());
							
							// Check for duplicates
							if(!setExists(pairs, set) && !tripple){
								pairs.add(set);
								sets.add(new Set(set, Set.VERTICAL));
							}
						}
					
					
					// Horizontal Check
					if(n.getLeft().getTeam() == player){ // Left matches, could be possible pair
						
						// Test for triples
						boolean tripple = false;
						
						if(n.getLeft().getLeft().getTeam() == player)
							tripple = true;
						
						if(n.getRight().getTeam() == player)
							tripple = true;
						
						set = new ArrayList<Node>();
						set.add(n);
						set.add(n.getLeft());
						
						if(!setExists(pairs, set) && !tripple){
							pairs.add(set);
							sets.add(new Set(set, Set.HORIZONTAL));
						}
					}
					
					// Left Diagonal Check
					if(n.getTopLeft() != null)
						if(n.getTopLeft().getTeam() == player){
							
							boolean tripple = false;
							
							if(n.getTopLeft().getTopLeft() != null)
								if(n.getTopLeft().getTopLeft().getTeam() == player)
									tripple = true;
							
							if(n.getBottomRight() != null)
								if(n.getBottomRight().getTeam() == player)
									tripple = true;
							
							set = new ArrayList<Node>();
							set.add(n);
							set.add(n.getTopLeft());
							
							if(!setExists(pairs, set) && !tripple){
								pairs.add(set);
								sets.add(new Set(set, Set.LEFT_DIAGONAL));
							}
							
						}
					
					// Right Diagonal Check
					if(n.getTopRight() != null)
						if(n.getTopRight().getTeam() == player){
							
							boolean tripple = false;
							
							if(n.getTopRight().getTopRight() != null)
								if(n.getTopRight().getTopRight().getTeam() == player)
									tripple = true;
							
							if(n.getBottomLeft() != null)
								if(n.getBottomLeft().getTeam() == player)
									tripple = true;
							
							set = new ArrayList<Node>();
							set.add(n);
							set.add(n.getTopRight());
							
							if(!setExists(pairs, set) && !tripple){
								pairs.add(set);
								sets.add(new Set(set, Set.RIGHT_DIAGNONAL));
							}
							
						}
	
				} // End if node is same as player
				
			} // End cycling through each Node in state-space
		
		return sets;
	}
	
	/**
	 * Checks data for any set of Nodes which contain the same exact nodes in the
	 * set parameter, disregarding their order of course.
	 * 
	 * @param data		List of sets to search in.
	 * @param set		Set to be found in data.
	 * @return			True if set is found in data, false if not found.
	 */
	public boolean setExists(ArrayList<ArrayList<Node>> data, ArrayList<Node> set){
		
		if(data == null || set == null)
			return false;
		
		if(data.isEmpty() || set.isEmpty())
			return false;
		
		
		for(int i=0; i<data.size(); i++){ // Cycle through each sequence in list
			
			if(data.get(i).size() == set.size()){ // If sequences are the same size
				
				boolean allExist = true; // Assume they all exist in this sequence
				
				for(int j=0; j<set.size(); j++){ // Cycle through each Node in the sequence to check
					
					if(!nodeExists(data.get(i), set.get(j)))
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
	public boolean nodeExists(ArrayList<Node> data, Node node){
		
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
		
		System.out.println("H:" + this.heuristic1(Control.PLAYER2) + " M:" + this.getMinimaxValue());
		
		System.out.println();
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
		 
		Interface.print("Expanded " + numberOfStates() + " states.");
	}
	
	/**
	 * Counts the number of StateSpaces enumerated as contained in this instance as well as
	 * all grandchildren and so forth.
	 * 
	 * @return	Number of possible state-space enumerations completed.
	 */
	public int numberOfStates(){
		 
		counter = 0; // Reset
		count(this);
		
		return counter;
	}
	
	/**
	 * Recursive method intended to be called by numberOfStates()
	 * Counts each state-spaces's children and adds it to a class variable.
	 * 
	 * @param root		StateSpace to count from. 
	 */
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
		
		if(frontier.isEmpty() || depth == 0) // Base-case return
			return;
		
		for(int i=0; i<frontier.size(); i++){ // For each of those options
			
			StateSpace ss = root.copy(); // Copy the state-space
			
			char player = (root.player1Turn())? Control.PLAYER1 : Control.PLAYER2; // Determine who's turn
			
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
	
	public class Set{
		
		//=== CONSTANTS ===
		public static final int VERTICAL = 0,
								HORIZONTAL = 1,
								LEFT_DIAGONAL = 2,
								RIGHT_DIAGNONAL = 3;
		
		public ArrayList<Node> set;
		public int direction;
		
		public Set(ArrayList<Node> set, int direction){
			
			this.set = set;
			this.direction = direction;
		}
		
		public Set(Node x, Node y, Node z, int direction){
			
			ArrayList<Node> set = new ArrayList<Node>();
			set.add(x);
			set.add(y);
			set.add(z);
			this.set = set;
			this.direction = direction;
		}
		
	
	}
	
}// END STATESPACE
