package aifinalproject;

import java.util.ArrayList;

public class Control {
	
	// CONSTANTS
	public static final char TEAM1 = 'X', 
		         			         TEAM2 = 'O',
		         			         NONE  = 'N';
	
	// STATIC VARIABLES	
	private static Control instance; //Instance of our entire program
	public static char[][] stateSpace; //Static so it can be accessed easily anywere 
	
	// VARIABLES
	private ArrayList<Agent> agents;
	private Interface gui;
	
	public Control(){
		
	
		// Gather Agents
		agents = new ArrayList<Agent>();
		//TODO populate this how?
		
		
		// Initialize GUI
		gui = new Interface(800, 500, agents);
		
		
		/*    OTHER NOTES    */
		// Declare/Build agents
		//   Specific agents encapsulated by Agent class
		
		// Teach agents (GUI optional)
		
		// Play agents
		
		// stateSpace = agent1.move(stateSpace)
		// stateSpace = agent2.move(stateSpace)
		
		
		
	}
	
	public static void run(){
		
		instance = new Control();
		
	}
	
	
	// METHODS
	
}
