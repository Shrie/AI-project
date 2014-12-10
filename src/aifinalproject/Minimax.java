package aifinalproject;

import java.util.ArrayList;
import java.util.Random;

public class Minimax {

	private final int UNEVALUATED = -99;
	
	//=== VARIABLES ===
	private int depth;
	private int heuristic;
	private boolean pruning;
	private char maximizer;
		
	private long nodesEvaluated;
	
	//=== CONSTRUCTORS ===
	public Minimax(int depth, boolean pruning, char maximizer, int heuristic){
		
		this.depth = depth;
		this.pruning = pruning;
		this.maximizer = maximizer;
		this.heuristic = heuristic;
		this.nodesEvaluated = 0;
		
	}
	

	public StateSpace decideMove(StateSpace ss){
		
		expand(ss, 1, UNEVALUATED);
				
		return maxMV(ss.getChildren());
	}
	
	private void expand(StateSpace root, int current, int alpha){
		
		ArrayList<Node> frontier = root.frontier(); // Grab all valid move locations
		
		if(frontier.isEmpty() || current == depth + 1) // Base case, return
			return;
		
		char player = (root.player1Turn())? Control.PLAYER1 : Control.PLAYER2; // Decide who's move it is
		
		//TODO Not sure why, but these two lines are needed 
		char opponent = (player == Control.PLAYER1)? Control.PLAYER2 : Control.PLAYER1;
		char test = (depth % 2 == 0)? opponent : player;
		
		for(int i=0; i<frontier.size(); i++){ // For all valid move locations
			
			StateSpace copy = root.copy(); 	// Grab a copy of the current game space
			copy.setNode(player, frontier.get(i).i, frontier.get(i).j); // Enumerate a move
			
			if(current == depth && heuristic == StateSpace.HEURISTIC1)
				copy.setMinimaxValue( copy.heuristic1(test) );
			else if(current == depth)
				copy.setMinimaxValue( copy.heuristic2(test) );
			
			// PRUNING
			if(pruning && alpha != UNEVALUATED){
				
				if(maximizer == player && copy.getMinimaxValue() > alpha)
					return;
				else if(maximizer != player && copy.getMinimaxValue() < alpha)
					return;
				
			}
			
			root.getChildren().add(copy);
			nodesEvaluated++;
		}
		
		
		for(int i=0; i<root.getChildren().size(); i++){
			
			if(i == 0)
				expand(root.getChildren().get(i), current + 1, UNEVALUATED);
			else
				expand(root.getChildren().get(i), current + 1, root.getChildren().get(i - 1).getMinimaxValue());
			
		}
			

		if(maximizer == player)
			root.setMinimaxValue( maxMV(root.getChildren()).getMinimaxValue() );
		else
			root.setMinimaxValue( minMV(root.getChildren()).getMinimaxValue() );
		
		
	}
	
	private StateSpace minMV(ArrayList<StateSpace> neighbors){
		
		StateSpace minSS = neighbors.get(new Random().nextInt(neighbors.size()));
		
		for(int i=0; i<neighbors.size(); i++)
			if(neighbors.get(i).getMinimaxValue() < minSS.getMinimaxValue())
				minSS = neighbors.get(i);
		
		return minSS;
	}
	
	private StateSpace maxMV(ArrayList<StateSpace> neighbors){
		
		StateSpace maxSS = neighbors.get(new Random().nextInt(neighbors.size()));
		
		for(int i=0; i<neighbors.size(); i++)
			if(neighbors.get(i).getMinimaxValue() > maxSS.getMinimaxValue())
				maxSS = neighbors.get(i);
		
		
		return maxSS;
	}
	
	public long getNodesEvaluated(){
		
		return nodesEvaluated;
	}
	
	
} // END Minimax