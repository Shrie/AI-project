package aifinalproject;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Heuristics implements Agent {

	//=== VARIABLES ===
	private char player;
	
	
	//=== CONSTRUCTORS ===
	public Heuristics(){
		
	}
	
	public Heuristics(char player){
		
		this.player = player;
	}
	
	
	//=== METHODS ===
	/**
	 * TODO
	 * 
	 * @author Mason
	 * @param player	Player character, either PLAYER1 or PLAYER2
	 * @return			TODO
	 */
	public int heuristic1(char player, char[][] board) {
	
		int rows = board.length;
		int cols = board[0].length;
		int score1 = 0;
		int score2 = 0;
		char p1 = Control.PLAYER1;
		char p2 = Control.PLAYER2;
		
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
	
	
	//=== OVERRIDES ===
	@Override
	public String getName() {
		
		return "Heuristics";
	}

	@Override
	public void makeMove() {
		
		StateSpace ss = Control.instance.stateSpace; // Grab current stateSpace
		
		ss.expandStateSpace(2); // Expand it down two layers
		
		StateSpace best = null;
		int bestH = 0;
		
		for(int i=0; i<ss.getChildren().size(); i++){ // Cycle though enumerated layer
			
			int h = heuristic1(this.player, ss.getChildren().get(i).getCharStateSpace()); // Calculate Heuristic
			
			if(h > bestH){ // If the state is better
				
				best = ss.getChildren().get(i); // Store it
				bestH = h;
			}
		}
		
		Control.instance.stateSpace = best;  // Take Move

	}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel(new GridLayout(0, 1));
		p.add(new JLabel("Various Heuristics", JLabel.CENTER));
		
		return p;
	}

	@Override
	public Agent createNew(char team) {
		
		return new Heuristics(team);
	}

}// END HEURISTICS
