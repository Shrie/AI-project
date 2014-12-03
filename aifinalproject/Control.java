package aifinalproject;

import java.util.ArrayList;

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

	//=== VARIABLES ===
	public StateSpace stateSpace; // The current state of the game
	
	private ArrayList<Agent> agents1, // List of AI agents for player 1 (populated manually in constructor)
							 agents2; // Agents for player 2
	
	private Interface gui;			  // Instance of GUI

	public boolean isPlayer1Human, // Is player1 a human
				   isPlayer2Human, // Is team2 a human
				   gameOver,	   // True when game is over, turns off timer thread
				   onFirstMove;    // Is the game on the first move?

	private Thread timer; // Start/stop timer
	
	private long time1, // Player 1 time in 1/60th seconds
				 time2; // Player 2 time in 1/60th seconds
	
	private int scoreToWin,		// Number of games to win to win overall 
				player1Score,	// Player 1 number of games won
				player2Score;	// Player 2 number of games won
	
	//=== CONSTRUCTOR ===
	public Control() {

		//Agent list initialization
		agents1 = new ArrayList<Agent>(); // Player 1 agent options
		agents2 = new ArrayList<Agent>(); // Player 2 agent options
		
		// TODO ADD NEW AGENTS HERE
		// Have one constructor that has no parameters to work as a 
		// somewhat 'generic' instance which is only used to populate the
		// option pane and name for the ComboBox
		agents1.add(new Human());
		agents1.add(new Randy());
		agents1.add(new Heuristics());
		
		agents2.add(new Human());
		agents2.add(new Randy());
		agents2.add(new Heuristics());
		
		// Initialize GUI
		gui = new Interface(800, 500, agents1, agents2);

		// Timer initialization
		time1 = 0;
		time2 = 0;

		// Initialize booleans
		onFirstMove = true;
		isPlayer1Human = false;
		isPlayer2Human = false;
		gameOver = false;
		
		// Create timer thread
		createTimer();

	} // END CONSTRUCTOR

	//=== METHODS ===	
	/**
	 * 
	 * @return		The GUI Interface
	 */
	public Interface getInterface(){
		
		return gui;
	}
	
	/**
	 * 
	 * @param score		Set number of games to win to win overall
	 */
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
				while (!gameOver) {

					if (stateSpace.player1Turn())
						gui.updatePlayer1Time(time1++); // Update scoreboard then increment
					else
						gui.updatePlayer2Time(time2++); // Same for P2

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
	 * Game loop found here. Keeps calling agents makeMove() until
	 * a win is discovered.
	 * 
	 * @param: agent1		Player 1 agent
	 * @param: agent2		Player 2 agent
	 */
	public void playGame(Agent agent1, Agent agent2){

		ArrayList<Node> winSequence;
		
		// Start timer
		timer.start();
		
		// Check for human agents
		if(agent1.getName().contains("Human"))
			isPlayer1Human = true;
		
		if(agent2.getName().contains("Human"))
			isPlayer2Human = true;
		
	
		// BEGIN GAME LOOP
		while(!isGameOver()){
			
			// Make a move
			if(stateSpace.player1Turn()){
				gui.setPrompt("Player 1, Make Your Move!");
				
				if(isPlayer1Human)
					humanPlay();
				else
					agent1.makeMove();
				
				onFirstMove = false; // A move has been made
				
			} else {
				gui.setPrompt("Player 2, Make Your Move!");
				
				if(isPlayer2Human)
					humanPlay();
				else
					agent2.makeMove();
				
			}
			
			
			// Check for a win
			winSequence = stateSpace.checkForWinSequence();
			
			if(!winSequence.isEmpty()){ // If a win exists
				
				if(winSequence.get(0).getTeam() == Control.PLAYER1)
					gui.updatePlayer1Score(++player1Score);
				else
					gui.updatePlayer2Score(++player2Score);
					
				stateSpace.reset();
				onFirstMove = true;
			}
			
			gui.getBoard().repaint(); // Update GUI to reflect move
			
		}
		
		// GAME OVER
		gameOver = true;
		gui.setPrompt("GAME OVER!");
		
	} // END playGame()
	
	private void humanPlay(){
		
		while(!gui.getBoard().moveMade){  // Wait for human player to make valid move
			try {
				
				Thread.sleep(10);
				
			} catch (InterruptedException e){
				
				Interface.print("Interruption in Human Agent L2!");
			}
		}
		
		gui.getBoard().moveMade = false; // Reset moveMade back to false

	}
	
	/**
	 * Checks each player's overall score for a win.
	 * 
	 * @return	True if either player has reached the score to win.
	 */
	private boolean isGameOver(){
		
		if(player1Score == scoreToWin || player2Score == scoreToWin)
			return true;
		
		return false;
	}
	
	
	//=== STATIC METHODS ===
	
	/**
	 * To be called in Main as program initializer.
	 */
	public static void run() {

		instance = new Control();

	}


} // END CONTROL
