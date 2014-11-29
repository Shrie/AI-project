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
							 NONE = 'N';

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
	 */
	public void playGame(Agent agent1, Agent agent2) {

		// Check for human agents
		if(agent1.getName().contains("Human"))
			this.isPlayer1Human = true;
		
		if(agent2.getName().contains("Human"))
			this.isPlayer2Human = true;

		//Begin first play
		gui.setPrompt("Player 1, Your Turn!");
		agent1.makeMove();
		gui.update();
		this.onFirstMove = false;
		
		//Begin game loop
		while(!winFound()){
			
			gui.setPrompt("Player 2, Your Turn!");
			agent2.makeMove();
			gui.update();
			
			gui.setPrompt("Player 1, Your Turn!");
			agent1.makeMove();
			gui.update();
			
		}

	} // END playGame()
	
	/**
	 * To be called after each player's turn to check for a win.
	 * 
	 * @return		True if any four nodes (of one type) are connected.
	 */
	private boolean winFound(){
		
		//TODO Below is filler junk for now
		Random r = new Random();
		
		if(r.nextInt(15) == 9)
			return true;
		
		return false;
		
	} // END winFound()

	
	//=== STATIC METHODS ===
	
	/*
	 * To be called in Main as program initializer.
	 */
	public static void run() {

		instance = new Control();

	}

} // END CONTROL
