package aifinalproject;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Heuristics implements Agent {

    //=== VARIABLES ===
    private char player;

    //=== CONSTRUCTORS ===
    public Heuristics() {

    }

    public Heuristics(char player) {

        this.player = player;
    }

    //=== METHODS ===
    /**
     * TODO
     *
     * @author Mason
     * @param player	Player character, either PLAYER1 or PLAYER2
     * @param board 2d char array representing board and moves
     * @return	TODO
     */
    public int heuristic1(char player, StateSpace ss) {

    	char[][] board = ss.getCharStateSpace();
    	
        int rows = board.length;
        int cols = board[0].length;
        int score1 = 0;
        int score2 = 0;
        char p1 = Control.PLAYER1;
        char p2 = Control.PLAYER2;
        int c = 2; //constant for exponentials

        //look for 4s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols]) && (curr == board[row][(col + 3) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 4);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 4);
                    }
                }
            }
        }

        for (int col = 0; col <= cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                char curr = board[row][col];
                if ((curr == board[row + 1][col]) && (curr == board[row + 2][col]) && (curr == board[row + 3][col])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 4);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 4);
                    }
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                
                if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 4);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 4);
                    }
                }

                if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 4);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 4);
                    }
                }
            }
        }

        //look for 3s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 3);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 3);
                    }
                }
            }
        }

        for (int col = 0; col <= cols; col++) {
            for (int row = 0; row < rows - 2; row++) {
                char curr = board[row][col];
                if ((curr == board[row + 1][col]) && (curr == board[row + 2][col])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 3);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 3);
                    }
                }
            }
        }

        for (int row = 0; row < rows - 2; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                
                if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 3);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 3);
                    }
                }

                if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols])) {
                    if (curr == p1) {
                        score1 += Math.pow(c, 3);
                    } else if (curr == p2) {
                        score2 += Math.pow(c, 3);
                    }
                }
            }
        }
        
        //look for 2s
        for (int row = 0; row < rows; row++) {
            char prev = '-';
            char curr = '-';

            for (int col = 0; col <= cols; col++) {
                curr = board[row][col % cols];
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
                curr = board[row][col % cols];
                if ((curr == prev) && (curr == p1)) {
                    score1++;
                } else if ((curr == prev) && (curr == p2)) {
                    score2++;
                }
            }
        }

        for (int row = 0; row < rows - 1; row++) {
            char curr = '-';

            for (int col = 0; col < cols; col++) {
                curr = board[row][col % cols];
                char l = board[row + 1][(col - 1 + cols) % cols];
                char r = board[row + 1][(col + 1 + cols) % cols];
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
        //look for 1s

        for (int row = 0; row < rows; row++) {
            char prev = '-';
            char curr = '-';

            for (int col = 0; col <= cols; col++) {
                curr = board[row][col % cols];
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
                curr = board[row][col % cols];
                if ((curr == prev) && (curr == p1)) {
                    score1++;
                } else if ((curr == prev) && (curr == p2)) {
                    score2++;
                }
            }
        }

        for (int row = 0; row < rows - 1; row++) {
            char curr = '-';

            for (int col = 0; col < cols; col++) {
                curr = board[row][col % cols];
                char l = board[row + 1][(col - 1 + cols) % cols];
                char r = board[row + 1][(col + 1 + cols) % cols];
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

        //maybe change to other values for minimax
        if (player == p1) {
            return score1 - score2;
        } else {
            return score2 - score1;
        }

    }// END heuristic()


    //=== OVERRIDES ===
    @Override
    public String getName() {

        return "Heuristics";
    }

    @Override
    public void makeMove() {

    	StateSpace ss = Control.instance.stateSpace; // Grab current statespace
    	ss.expandStateSpace(1);						 // Enumerate all possibilities for the agent
    	
    	StateSpace best = ss.getChildren().get(0); // To avoid returning NULL if no better state is found
    	int bestH = 0;
    	
    	for(int i=0; i<ss.getChildren().size(); i++){ // For all possibilities
    		
    		int h = heuristic1(this.player, ss.getChildren().get(i)); // Apply the heurisitic to the state
    		
    		if( h > bestH){	// If this state is better than what we currently have, store it
    			
    			bestH = h;
    			best = ss.getChildren().get(i);
    		}
    		
    	}
    		
    
    	Control.instance.stateSpace = best; // Finalize move
    	
    }

    @Override
    public JPanel createOptionPane() {

        JPanel p = new JPanel(new GridLayout(0, 1));
        p.add(new JLabel("Various Heuristics", JLabel.CENTER));

        p.add(new JLabel());
        p.add(new JLabel());
        p.add(new JLabel());
        
        return p;
    }

    @Override
    public Agent createNew(char team) {

        return new Heuristics(team);
    }

}// END HEURISTICS
