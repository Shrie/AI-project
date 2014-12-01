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
	public char[][] stateSpace; // Static so it can be accessed easily anywhere

	//=== VARIABLES ===
	private ArrayList<Agent> agents1,
							 agents2; // List of AI agents (populated manually in constructor)
	private Interface gui;			 // Instance of GUI

	public boolean player1Turn,    // Flip-flops between player turns
				   isPlayer1Human, // Is player1 a human
				   isPlayer2Human, // Is team2 a human
				   onFirstMove;    // Is the game on the first move?

	private Thread timer; // Start/stop timer
	
	private long time1, // Player 1 time in 1/60th seconds
				 time2; // Player 2 time in 1/60th seconds
	
	private int scoreToWin,
				player1Score,
				player2Score;
	
	//=== CONSTRUCTOR ===
	public Control() {

		//Agent list initialization
		agents1 = new ArrayList<Agent>(); 
		agents2 = new ArrayList<Agent>();
		
		// TODO ADD NEW AGENTS HERE
		// Have one constructor that has no parameters to work as a 
		// somewhat 'generic' instance which is only used to populate the
		// option pane and name for the ComboBox
		agents1.add(new Human());
		agents1.add(new Randy());
		
		agents2.add(new Human());
		agents2.add(new Randy());
		// TODO
		
		// Initialize GUI
		gui = new Interface(800, 500, agents1, agents2);

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
	
	public Interface getInterface(){
		return gui;
	}
	
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
		
		onFirstMove = false;		// No longer first turn
			
	
		
			//Begin game loop
		while(player1Score != scoreToWin && player2Score != scoreToWin){
		
			winCheck(); // Checks Player 1's move
			
			player1Turn = false;	
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
			winCheck(); // Checks Player 2's move
			
			
			player1Turn = true;
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
					onFirstMove = true;
					player1Turn = true;
					gui.getBoard().reset();
					return;
					
				}
			
	} // END winCheck()
	
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
