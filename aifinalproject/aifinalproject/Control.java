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
	public char[][] stateSpace; // The current state of the game
	
	private ArrayList<Agent> agents1, // List of AI agents for player 1 (populated manually in constructor)
							 agents2; // Agents for player 2
	
	private Interface gui;			  // Instance of GUI

	public boolean player1Turn,    // Flip-flops between player turns
				   isPlayer1Human, // Is player1 a human
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
		
		agents2.add(new Human());
		agents2.add(new Randy());
		
		// Initialize GUI
		gui = new Interface(800, 500, agents1, agents2);

		// Timer initialization
		time1 = 0;
		time2 = 0;

		// Initialize booleans
		player1Turn = true;
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

					if (player1Turn)
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

		// Start timer
		timer.start();
		
		// Check for human agents
		if(agent1.getName().contains("Human"))
			isPlayer1Human = true;
		
		if(agent2.getName().contains("Human"))
			isPlayer2Human = true;
		
		// BEGIN FIRST TURN
		gui.setPrompt("Player 1, Your Turn!");
		agent1.makeMove();			// Make first move
		
		// Human Agent Move Listener 1
		if(isPlayer1Human){
			
			while(!gui.getBoard().moveMade){ // Wait for valid move to be made
				
				try {
					
					Thread.sleep(10);
					 
				}catch(InterruptedException e){
					
					Interface.print("Interruption in Human Agent L1!");
				}
			}
			
			gui.getBoard().moveMade = false; // Reset moveMade back to false
			
		}// End Human Agent Move Listener 1
		
		gui.update();				// Update board
		onFirstMove = false; // No longer first turn	
	
		
		// BEGIN GAME LOOP
		while(!isGameOver()){ // Run until a player achieves the score to win
		
			winCheck(); // Checks Player 1's move
			
			player1Turn = false;	// Player 2 Turn
			gui.setPrompt("Player 2, Your Turn!");
			agent2.makeMove();
			
			// Human Agent Move Listener 2
			if(isPlayer2Human){
				while(!gui.getBoard().moveMade){  // Wait for human player to make valid move
					try {
						
						Thread.sleep(10);
						
					} catch (InterruptedException e){
						
						Interface.print("Interruption in Human Agent L2!");
					}
				}
				
				gui.getBoard().moveMade = false; // Reset moveMade back to false
				
			}// End Human Agent Move Listener 2
			
			gui.update();// Update board
			winCheck();  // Checks Player 2's move
			
			
			if(isGameOver()) // Check for game winning move
				break;
			
			player1Turn = true; // Player 1 Turn
			gui.setPrompt("Player 1, Your Turn!");
			agent1.makeMove();
			
			// Human Agent Move Listener 3
			if(isPlayer1Human){
				while(!gui.getBoard().moveMade){
					try {
						
						Thread.sleep(10);
						
					} catch (InterruptedException e) {
						
						Interface.print("Interruption in Human Agent L3!");
					}
				}
				
				gui.getBoard().moveMade = false; // Reset moveMade back to false
				
			}// End Human Agent Move Listener 3
			
			gui.update(); // Update board
			
		}// END GAME LOOP WHILE
		
		// GAME OVER
		gameOver = true;
		gui.setPrompt("GAME OVER!");
		

	} // END playGame()
	
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
	
	/**
	 * To be called after each player's turn to check for a win. 
	 * If win is found, update playerScore and GUI label.  
	 * Does NOT use resolution/unification. Uses a simple graph approach found in
	 * Node.winFound(). 
	 * 
	 * TODO replace with winCheck() which uses resolution.
	 * TODO How to handle draws?
	 * 
	 * @return		True if any four nodes (of one type) are connected.
	 */
	private void winCheck(){
		
		Node[][] n = gui.getBoard().getNodes(); // Grab double array of Nodes from Board
		
		for(int i=0; i<n.length; i++)
			for(int j=0; j<n[i].length; j++) // Cycle through each Node
				if(n[i][j].winFound()){		 // If win found at
					
					if(player1Turn)								// If Player 1 turn
						gui.updatePlayer1Score(++player1Score); // Increment score, then update GUI
					else
						gui.updatePlayer2Score(++player2Score);
					
					onFirstMove = true;	// Reset back to first move
					player1Turn = true; // Player 1 turn
					gui.getBoard().reset(); // Reset to blank board
					
					return;		// Return after one instance of a win is found.
				}
			
	} // END winCheck()
	
	/**
	 * TODO
	 * 
	 * @param player	Player character, either PLAYER1 or PLAYER2
	 * @return			TODO
	 */
	public int heuristic(char player) {
			
		char[][] board = stateSpace;
	
		int rows = board.length;
		int cols = board[0].length;
		int score1 = 0;
		int score2 = 0;
		char p1 = 'X';
		char p2 = 'O';
		
		for (int row = 0; row < rows; row++) {
			char prev = '-';
			char curr = '-';
			
			for (int col = 0; col <= cols; col++) {
				curr = board[row][col%cols];
				if ((curr == prev) && (curr == p2)) {
					score1++;
				} else if ((curr == prev) && (curr == p1)) {
					score2++;
				}
			}
			
			prev = curr;
		}
		
		for (int col = 0; col <= cols; col++) {
			char prev = '-';
			char curr = '-';
			
			for (int row = 0; row < rows; row++) {
				curr = board[row][col%cols];
				if ((curr == prev) && (curr == p1)) {
					score1++;
				} else if ((curr == prev) && (curr == p2)) {
					score2++;
				}
			}
		}
		
		for (int row = 0; row < rows; row++) {
			char curr = '-';
			
			for (int col = 0; col < cols; col++) {
				curr = board[row][col%cols];
				char l = board[row+1][(col-1+cols)%cols];
				char r = board[row+1][(col+1+cols)%cols];
				if ((curr == l) && (curr == p1)) {
					score1++;
				} else if ((curr == l) && (curr == p2)) {
					score2++;
				}
				
				if ((curr == r) && (curr == p1)) {
					score1++;
				} else if ((curr == r) && (curr == p2)) {
					score2++;
				}
			}
		}
		
		if (player == p1) {
			return score1-score2;
		} else
			return score2-score1;
			
	}// END heuristic()

	
	/**
	 * Prints the current state space to the console. 
	 */
	public void printSS(){
		
		for(int i=0; i<stateSpace.length; i++){
			for(int j=0; j<12; j++)
				System.out.print("[" + stateSpace[i][j] + "]");
			System.out.println();
		}
	}

	
	//=== STATIC METHODS ===
	
	/**
	 * To be called in Main as program initializer.
	 */
	public static void run() {

		instance = new Control();

	}


} // END CONTROL
