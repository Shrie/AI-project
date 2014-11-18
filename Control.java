package aifinalproject;

import java.util.ArrayList;

public class Control {
	
	// CONSTANTS
	public static final char TEAM1 = 'X', 
		         			 TEAM2 = 'O',
		         			 NONE  = 'N';
	
	// STATIC VARIABLES	
	public static Control instance; //Instance of our entire program
	public static char[][] stateSpace; //Static so it can be accessed easily anywhere 

	
	// VARIABLES
	private ArrayList<Agent> agents; // List of AI agents
	private Interface gui;			 // Instance of GUI
	
	public boolean team1Turn;
	public boolean isHuman1, // Is team1 a human 
				   isHuman2; // Is team2 a human
	private boolean firstMove;
	
	private Thread timer; // Start/stop timer
	private long time1, time2; // millisecond time
	
	// CONSTRUCTOR
	public Control(){
		
	
		// Gather Agents
		agents = new ArrayList<Agent>();
		agents.add(new NeuralNet()); // Dummy agent (for now)
		//TODO populate this how?
		
		
		// Initialize GUI
		gui = new Interface(800, 500, agents);
		
		time1 = 0;
		time2 = 0;
		
		team1Turn = true;
		firstMove = true;
		
		createTimers();
				
		
	}
	
	private void createTimers(){
		
		timer = new Thread(){
					
					public void run(){
						
						while( true ){
							
							if(team1Turn)
								gui.updateT1Time(time1++);
							else
								gui.updateT2Time(time2++);
							
							
							try {
								
								Thread.sleep(100 / 6); // Sleep for 1/60 of second
								
							} catch (InterruptedException e) {
								
								
								
							}
							
						}
						
					}
				};
		
	}
	
	/*
	 * Called after "Start" is pressed to start timers, allow play, etc
	 * 
	 * @param: team1 name of team1 i.e. human, ANN
	 * @param: team2 name of team2 
	 */
	public void startGame(boolean t1h, boolean t2h){
		
		this.isHuman1 = t1h;
		this.isHuman2 = t2h;
		
		


			if(isHuman1){
				
				gui.setPrompt("Team 1 Your Move!");
				timer.start();
				
				
			}
	
		
	}
	
	public boolean makeMove(int i, int j){
		
		
		if(team1Turn){
			
			if(!firstMove && notValidMove(i, j)){ // If move not valid
				
				gui.setPrompt("Invalid Move Location!");
				return false;
				
			} else{
			
				gui.setPrompt("Team 2 Your Move!");
				firstMove = false; // No longer firstMove
				team1Turn = false;
			}
			
		} else {
			
			if(notValidMove(i, j)){
				
				gui.setPrompt("Invalid Move Location!");
				return false;
				
			} else {
				
				gui.setPrompt("Team 1 Your Move!");
				team1Turn = true;
				
			}
		
		}
		
		
		return true;
		
		
	}
	
	private boolean notValidMove(int i, int j){
		
		// Check for out-of-bounds indexes
		if(i < 0 || i > stateSpace.length-1 || j < 0 || j > 11)
			return true;
		
		if(stateSpace[i][j] != NONE)
			return true;
		
		return !adjacentPlayed(i, j);
		
	}
	
	/*
	 * Returns true if an adjacent move has been made
	 */
	public boolean adjacentPlayed(int i, int j){
		
		// Check top row
		if(i > 0){
			if(j > 0){
				if(stateSpace[i - 1][j - 1] != NONE
						|| stateSpace[i - 1][j] != NONE
						|| stateSpace[i - 1][(j + 1) % 12] != NONE)
					return true;
				
			} else if(stateSpace[i - 1][11] != NONE
					|| stateSpace[i - 1][0] != NONE
					|| stateSpace[i - 1][1] != NONE)
				return true;
		}
		
		// Check bottom row
		if(i < stateSpace.length - 1){
			if(j > 0){
				if(stateSpace[i + 1][j - 1] != NONE
						|| stateSpace[i + 1][j] != NONE
						|| stateSpace[i + 1][(j + 1) % 12] != NONE)
					return true;
				
			} else if(stateSpace[i + 1][11] != NONE
					|| stateSpace[i + 1][0] != NONE
					|| stateSpace[i + 1][1] != NONE)
				return true;
			
		}
		
		// Check sides
		if(j > 0){
			if(stateSpace[i][j - 1] != NONE
					|| stateSpace[i][(j + 1) % 12] != NONE)
				return true;
			
		} else if(stateSpace[i][11] != NONE
				|| stateSpace[i][1] != NONE)
			return true;

		
	
		return false;
		
	}
	
	
	// STATIC METHODS
	public static void run(){
		
		instance = new Control();
		
	}
	
	
}
