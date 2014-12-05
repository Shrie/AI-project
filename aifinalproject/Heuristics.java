package aifinalproject;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

public class Heuristics implements Agent {

    //=== VARIABLES ===
    private char player;
    private JRadioButton mason, chad;
    private JSpinner del;
    private boolean h1;
    private int delay;

    //=== CONSTRUCTORS ===
    public Heuristics() {

    }

    public Heuristics(char player, boolean h1, int delay) {

        this.player = player;
        this.h1 = h1;
        this.delay = delay;
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
        int c = 5; //constant for exponentials

        //look for 4s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols]) && (curr == board[row][(col + 3) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 5);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 5);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 3; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col]) && (curr == board[row + 2][col]) && (curr == board[row + 3][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 5);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 5);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 3; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 5);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 5);
                        }
                    }

                    if ((curr == board[row + 1][(col - 1 + cols) % cols]) && (curr == board[row + 2][(col - 2 + cols) % cols]) && (curr == board[row + 3][(col - 3 + cols) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 5);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 5);
                        }
                    }
                }
            }
        }

        //look for 3s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols]) && (curr == board[row][(col + 2) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 2; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col]) && (curr == board[row + 2][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 3);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 3);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 2; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {

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
        }

        //look for 2s
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row][(col + 1) % cols])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 1);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 1);
                        }
                    }
                }
            }
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 1; row++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if ((curr == board[row + 1][col])) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 1);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 1);
                        }
                    }
                }
            }
        }

        for (int row = 0; row < rows - 1; row++) {
            for (int col = 0; col < cols; col++) {
                char curr = board[row][col];
                if (curr == p1 || curr == p2) {
                    if (curr == board[row + 1][(col - 1 + cols) % cols]) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 1);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 1);
                        }
                    }

                    if (curr == board[row + 1][(col - 1 + cols) % cols]) {
                        if (curr == p1) {
                            score1 += Math.pow(c, 1);
                        } else if (curr == p2) {
                            score2 += Math.pow(c, 1);
                        }
                    }
                }
            }
        }

        //maybe change to other values for minimax
        if (player == p1) {
            return score1 - score2;
        } else {
            return score2 - score1;
        }

    }// END heuristic1()
    
    public int heuristic2(char player, StateSpace ss){
    	
    	char opponent = (player == Control.PLAYER1)? Control.PLAYER2 : Control.PLAYER1;
    	ArrayList<Node> win = ss.checkForWinSequence();
    	
    	if(!win.isEmpty())
    		if(win.get(0).getTeam() == player)
    			return 10;	// Win for player is present
    		else if(win.get(0).getTeam() != player)
    			return -10; // Win for opponent is present
    	
    	if(!ss.getOpenTriples(opponent).isEmpty()) // Opponent is one move away from win
    		return -8;
    	
    	if(!ss.getOpenEndedPairs(opponent).isEmpty())
    		return -7;
    	
    	 if(!ss.getOpenEndedPairs(player).isEmpty()) // Will solidify a win
    		 return 8;
    	 
    	 if(!ss.getOpenTriples(player).isEmpty())
    		 return 7;
    	 
    	 if(!ss.getSingleEndedPairs(opponent).isEmpty())
    		 return -5;
    	 
    	 if(!ss.getSingleEndedPairs(player).isEmpty())
    		 return 5;
    	 
    	
    	return 0;
    	
    }

    //=== OVERRIDES ===
    @Override
    public String getName() {

        return "Heuristics";
    }

    @Override
    public void makeMove() {
    	
    	StateSpace ss = Control.instance.stateSpace; // Grab current state-space
    	ss.expandStateSpace(1);						 // Enumerate one ply
    	
    	int currentValue = (h1)? heuristic1(player, ss) : heuristic2(player, ss); // Current value of board
    	
    	StateSpace best = ss.getChildren().get(new Random().nextInt(ss.getChildren().size() - 1));
    	int bestDifference = currentValue;
    	
    	for(int i=0; i<ss.getChildren().size(); i++){
    		    		
    		int state = (h1)? 
    				heuristic1(player, ss.getChildren().get(i)) 
    				: heuristic2(player, ss.getChildren().get(i));
    				
    		int dif = state - currentValue;
    		
    		if(dif > bestDifference ){
    			
    			bestDifference = dif;
    			best = ss.getChildren().get(i);
    		}
    			
    	}
    	
    	try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    
    	Control.instance.stateSpace = best; // Finalize move
    	
    }

    @Override
    public JPanel createOptionPane() {

        JPanel p = new JPanel(new GridLayout(0, 1));
        p.add(new JLabel("Various Heuristics", JLabel.CENTER));
        
        del = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		del.setBorder(BorderFactory.createTitledBorder("Delay (seconds)"));
		((DefaultEditor) del.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) del.getEditor()).getTextField().setEditable(false);
		p.add(del);
        p.add(mason = new JRadioButton("Mason's", true));
        p.add(chad = new JRadioButton("Chad's"));
        p.add(new JLabel());
        
        ButtonGroup gp = new ButtonGroup();
        gp.add(mason);
        gp.add(chad);
        
        return p;
    }

    @Override
    public Agent createNew(char team) {

    	boolean h1 = (mason.isSelected())? true : false;
    	
        return new Heuristics(team, h1, (int)del.getValue());
    }

}// END HEURISTICS
