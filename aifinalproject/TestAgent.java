package aifinalproject;

import javax.swing.JPanel;

public class TestAgent implements Agent {

	public String getName() {
		return "Test Agent";
	}

	public void makeMove() {

	}

	public JPanel createOptionPane() {

		return new JPanel();
		
	}

	private int heuristic(char[][] board, char mine) {
		
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
		
		if (mine == p1) {
			return score1-score2;
		} else
			return score2-score1;
		
	}

	@Override
	public Agent createNew(char team) {

		return new TestAgent();
	}
}