package aifinalproject;

import java.util.ArrayList;
import java.util.Random;

public class Minimax {

	//=== VARIABLES ===
	private int depth;
	private int heuristic;
	private boolean pruning;
	private char maximizer;
	
	//=== CONSTRUCTORS ===
	public Minimax(int depth, boolean pruning, char maximizer, int heuristic){
		
		this.depth = depth;
		this.pruning = pruning;
		this.maximizer = maximizer;
		this.heuristic = heuristic;
	}
	

	public StateSpace decideMove(StateSpace ss){
		
		expand(ss, depth);
		
		return maxMV(ss.getChildren());
	}
	
	private void expand(StateSpace root, int current){
		
		ArrayList<Node> frontier = root.frontier(); // Grab all valid move locations
		
		if(frontier.isEmpty() || current == 0) // Base case, return
			return;
		
		char player = (root.player1Turn())? Control.PLAYER1 : Control.PLAYER2; // Decide who's move it is
		
		for(int i=0; i<frontier.size(); i++){ // For all valid move locations
			
			StateSpace copy = root.copy(); 	// Grab a copy of the current game space
			copy.setNode(player, frontier.get(i).i, frontier.get(i).j); // Enumerate a move
			
			// TODO Pruning
			if(depth == 1 && heuristic == StateSpace.HEURISTIC1)
				copy.setMinimaxValue(copy.heuristic1(player));
			else if(depth == 1 && heuristic == StateSpace.HEURISTIC2)
				copy.setMinimaxValue(copy.heuristic2(player));
			
			root.getChildren().add(copy);
		}
		
		if(maximizer == player)
			root.setMinimaxValue( maxMV(root.getChildren()).getMinimaxValue() );
		else
			root.setMinimaxValue( minMV(root.getChildren()).getMinimaxValue() );
		
		for(int i=0; i<root.getChildren().size(); i++)
			expand(root.getChildren().get(i), current - 1);
		
	}
	
	private StateSpace minMV(ArrayList<StateSpace> neighbors){
		
		StateSpace minSS = neighbors.get(0);
		
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
		
		maxSS = maxSS.copy();
		
		return maxSS;
	}
	
	private StateSpace max(ArrayList<StateSpace> neighbors){
		
		int max = Integer.MIN_VALUE;
		StateSpace maxSS = neighbors.get(0);
		
		for(int i=0; i<neighbors.size(); i++){
			
			int h = (heuristic == StateSpace.HEURISTIC1)? 
					neighbors.get(i).heuristic1(maximizer) 
					: neighbors.get(i).heuristic2(maximizer);
			
			if(h > max){
				max = h;
				maxSS = neighbors.get(i);
			}
		}
		
		maxSS.setMinimaxValue(max);
			
		return maxSS;
		
	} // END indexOfMax()
	
	private StateSpace min(ArrayList<StateSpace> neighbors){
		
		int min = Integer.MAX_VALUE;
		StateSpace minSS = neighbors.get(0);
		
		for(int i=0; i<neighbors.size(); i++){
			
			int h = (heuristic == StateSpace.HEURISTIC1)? 
					neighbors.get(i).heuristic1(maximizer) 
					: neighbors.get(i).heuristic2(maximizer);
			
			if(h < min){
				min = h;
				minSS = neighbors.get(i);
			}
		}
		
		minSS.setMinimaxValue(min);
			
		return minSS;
		
	} // END indexOfMin()
	
} // END Minimax