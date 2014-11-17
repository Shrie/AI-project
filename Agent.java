package aifinalproject;

import javax.swing.JPanel;

public interface Agent {

	
	// WHEN TRAINING
	
	
	// WHEN PLAYING
	// Takes in some statespace[][]
	// Outputs some action
	
	
	// After each move should report
	//		Maximum search depth
	//		Number of nodes evaluated
	//		Time taken to decide a move
	
	public String getName();
	
	public char[][] makeMove(char[][] stateSpace);
	
	public JPanel createOptionPane();
	
	
}
