package aifinalproject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Highest hierarchical class. Turn-cycle management happens here.
 * Uses static variable 'instance' which can be called anywhere.
 * Must call run() before any other method in program.
 */
public class Control {

	//=== CONSTANTS ===
	public static final char PLAYER1 = 'X', 
							 PLAYER2 = 'O', 
							 NONE = ' ';

	//=== STATIC VARIABLES ===
	public static Control instance; // Instance of our entire program
	public static char[][] stateSpace; // Static so it can be accessed easily anywhere
									   // TODO may make non-static as to follow better programming convention?

	//=== VARIABLES ===
	private ArrayList<Agent> agents; // List of AI agents (populated manually in constructor)
	private Interface gui;			 // Instance of GUI

	public boolean player1Turn,    // Flip-flops between player turns
				   isPlayer1Human, // Is player1 a human
				   isPlayer2Human, // Is team2 a human
				   onFirstMove;    // Is the game on the first move?

	private Thread timer; // Start/stop timer
	
	private long time1, // Player 1 time in 1/60th seconds
				 time2; // Player 2 time in 1/60th seconds

	private Agent player1,
				  player2;
	
	private int scoreToWin,
				player1Score,
				player2Score;
	
	//=== CONSTRUCTOR ===
	public Control() {

		//Agent list initialization
		agents = new ArrayList<Agent>(); 
		
		// TODO ADD NEW AGENTS HERE
		// Have one constructor that has no parameters to work as a 
		// somewhat 'generic' instance which is only used to populate the
		// option pane and name for the ComboBox
		agents.add(new Human());
		agents.add(new Randy());
		// TODO
		
		// Initialize GUI
		gui = new Interface(800, 500, agents);

		// Timer initialization
		time1 = 0;
		time2 = 0;

		// Start the game on player 1's turn 
		player1Turn = true;
		onFirstMove = true;
		
		// Assume no humans for now
		isPlayer1Human = false;
		isPlayer2Human = false;

		// Initialize timer thread
		createTimer();

	} // END CONSTRUCTOR

	//=== METHODS ===
	
	public void setScoreToWin(int score){
		
		scoreToWin = score;
	}
	
	/**
	 * Initializes a thread which loops every 1/60th of a second and adds a unit of time
	 * to whichever player is currently making a move. Also updates each player's Time label
	 * in the GUI. Only stops when .stop() is called.
	 */
	private void createTimer() {

		timer = new Thread() { 
			public void run() {
				while (true) {

					if (player1Turn)
						gui.updatePlayer1Time(time1++); // Update then increment
					else
						gui.updatePlayer2Time(time2++);

					try {

						Thread.sleep(100 / 6); // Sleep for 1/60 of second

					} catch (InterruptedException e) {
						Interface.print("The timer thread was interrupted!");
					}
				}
			}
		};
		
	} // END createTimer();
	
	/**
	 * Checks i,j coordinates for an invalid move.
	 * Returns true if location already has been played
	 * or if there are no played moves adjacent (or diagonal) to
	 * it. 
	 * 
	 * @param i		x-coordinate of matrix interpretation of game board.
	 * @param j		y-coordinate of matrix intrepretation of game board.
	 * @return		false if valid move, true if not valid
	 */
	private boolean invalidMove(int i, int j) {

		// Check for out-of-bounds indices
		if (i < 0 || i > stateSpace.length - 1 || j < 0 || j > 11)
			return true;

		// Check if exact node has already been played
		if (stateSpace[i][j] != NONE)
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

		// Check top row
		if (i > 0) {
			if (j > 0) {
				if (stateSpace[i - 1][j - 1] != NONE
						|| stateSpace[i - 1][j] != NONE
						|| stateSpace[i - 1][(j + 1) % 12] != NONE)
					return true;

			} else if (stateSpace[i - 1][11] != NONE
					|| stateSpace[i - 1][0] != NONE
					|| stateSpace[i - 1][1] != NONE)
				return true;
		}

		// Check bottom row
		if (i < stateSpace.length - 1) {
			if (j > 0) {
				if (stateSpace[i + 1][j - 1] != NONE
						|| stateSpace[i + 1][j] != NONE
						|| stateSpace[i + 1][(j + 1) % 12] != NONE)
					return true;

			} else if (stateSpace[i + 1][11] != NONE
					|| stateSpace[i + 1][0] != NONE
					|| stateSpace[i + 1][1] != NONE)
				return true;

		}

		// Check sides
		if (j > 0) {
			if (stateSpace[i][j - 1] != NONE
					|| stateSpace[i][(j + 1) % 12] != NONE)
				return true;

		} else if (stateSpace[i][11] != NONE || stateSpace[i][1] != NONE)
			return true;

		return false;

	} // END adjacentPlayed() 

	/**
	 * Game loop found here. Keeps calling agents makeMove() until
	 * a win is discovered.
	 * 
	 * @param: agent1		Player 1 agent
	 * @param: agent2		Player 2 agent
	 * @throws InterruptedException 
	 */
	public void playGame(Agent agent1, Agent agent2){

		
		// Start timer
		timer.start();
		
		// Check for human agents
		if(agent1.getName().contains("Human"))
			isPlayer1Human = true;
		
		if(agent2.getName().contains("Human"))
			isPlayer2Human = true;
		
		//Begin first play
		gui.setPrompt("Player 1, Your Turn!");
		agent1.makeMove();			// Make first move
		gui.update();				// Update board
		
		if(isPlayer1Human){
			
			while(!Board.moveMade){
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Board.moveMade = false;
			
		}
		
		player1Turn = false;		// Player 2 turn
		onFirstMove = false;		// No longer first turn
			
	
		
			//Begin game loop
		while(player1Score != scoreToWin && player2Score != scoreToWin){
		
			winCheck(); // Checks Player 1's move
			
			
			gui.setPrompt("Player 2, Your Turn!");
			agent2.makeMove();
			
			if(isPlayer2Human){
				while(!Board.moveMade){  // Wait for human player to move
					try {
						
						Thread.sleep(10);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				Board.moveMade = false;
			}
			
			gui.update();
			player1Turn = true;
			winCheck(); // Checks Player 2's move
			
			
			
			gui.setPrompt("Player 1, Your Turn!");
			agent1.makeMove();
			
			if(isPlayer1Human){
				while(!Board.moveMade){
					
					try {
						
						Thread.sleep(10);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Board.moveMade = false;
			}
			
			gui.update();
			player1Turn = false;	
		}
		

	} // END playGame()
	
	/**
	 * To be called after each player's turn to check for a win. 
	 * If win is found, update playerScore and GUI label.  
	 * 
	 * @return		True if any four nodes (of one type) are connected.
	 */
	private void winCheck(){
		
		Node[][] n = gui.getBoard().getNodes();
		
		for(int i=0; i<n.length; i++)
			for(int j=0; j<n[i].length; j++)
				if(n[i][j].winFound()){
					
					if(player1Turn)
						gui.updatePlayer1Score(++player1Score);
					else
						gui.updatePlayer2Score(++player2Score);
					
					Interface.print("Win Found");
					gui.getBoard().reset(); // Does not work??
					return;
					
					//TODO Somehow reset and update the board visually
				}
			
	} // END winCheck()

	
	//=== STATIC METHODS ===
	
	/**
	 * To be called in Main as program initializer.
	 */
	public static void run() {

		instance = new Control();

	}
	
	public static void printSS(){
		
		for(int i=0; i<stateSpace.length; i++){
			for(int j=0; j<12; j++)
				System.out.print("[" + stateSpace[i][j] + "]");
			System.out.println();
		}
	}

} // END CONTROL
