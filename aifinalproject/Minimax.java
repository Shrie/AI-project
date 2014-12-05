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
		
		ss.expandStateSpace(depth); // Expand root
				
		if(depth == 1)
			return max(ss.getChildren());
		
		populateValues(0, ss);
				
		return maxMV(ss.getChildren()).copy(); 
	}
	
	private void populateValues(int currentDepth, StateSpace ss){
		
		if(currentDepth == depth - 1){ // At the bottom
			
			int h = (currentDepth % 2 == 0)? 
					min(ss.getChildren()).getMinimaxValue() 
					: max(ss.getChildren()).getMinimaxValue();
					
					
			ss.setMinimaxValue(h);
			
		} else {
		
			for(int i=0; i<ss.getChildren().size(); i++)
				populateValues(currentDepth + 1, ss.getChildren().get(i));
			
			if(currentDepth % 2 == 0)
				ss.setMinimaxValue( minMV(ss.getChildren()).getMinimaxValue() );
			else
				ss.setMinimaxValue( maxMV(ss.getChildren()).getMinimaxValue() );
		
		}
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
		
		for(int i=0; i<neighbors.size(); i++){
			neighbors.get(i).printStateSpace();
			if(neighbors.get(i).getMinimaxValue() > maxSS.getMinimaxValue())
				maxSS = neighbors.get(i);
		}
	
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