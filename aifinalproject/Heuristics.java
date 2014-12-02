package aifinalproject;

import java.awt.GridLayout;
import java.util.ArrayList;

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
    public int heuristic1(char player, char[][] board) {

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

    /**
     *
     * @return	An ArrayList containing each currently playable node.
     */
    private ArrayList<Node> grabFrontier() {

        Node[][] ss = Control.instance.getInterface().getBoard().getNodes();

        ArrayList<Node> frontier = new ArrayList<Node>();

        for (int i = 0; i < ss.length; i++) {
            for (int j = 0; j < ss[i].length; j++) {
                if (!Control.instance.onFirstMove
                        && !Control.instance.getInterface().getBoard().invalidMove(i, j)) {
                    frontier.add(ss[i][j]);
                } else if (Control.instance.onFirstMove) {
                    frontier.add(ss[i][j]);
                }
            }
        }

        return frontier;
    }

    //=== OVERRIDES ===
    @Override
    public String getName() {

        return "Heuristics";
    }

    @Override
    public void makeMove() {

        ArrayList<Node> frontier = grabFrontier();

        int best = 0;
        Node bestMove = null;

        if (!frontier.isEmpty()) {
            for (int i = 0; i < frontier.size(); i++) {
                char[][] ss = Control.instance.getCopyOfStateSpace();

                ss[frontier.get(i).getIIndex()][frontier.get(i).getJIndex()] = this.player;

                int h = heuristic1(this.player, ss);

                if (h >= best) {
                    bestMove = frontier.get(i);
                    best = h;
                }
            }
        }

        Control.instance.stateSpace[bestMove.getIIndex()][bestMove.getJIndex()] = this.player;

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
