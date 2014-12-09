/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aifinalproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Mason
 */
public class NeuralNet implements Agent {

    double[][] input_hidden; //edge weights
    double[][] hidden_output; //edge weights
    double[] input; //input nodes
    double[] hidden; //hidden layer
    double[] output; //output node(s)
    char player;
    double rate;
    double lambda;
    boolean training;
    int gameNumber; //possible to use modified sigmoid function for curve when changing lambda
    ArrayList<char[][]> moveList; //to save board states when training
    ArrayList<Double[]> outputLog; //to savee output value when training

    /**
     * THEORY: inputs will have value -1 for opponent's char, 0 for empty, and
     * +1 for player's char Will start with 12 hidden nodes and adjust to find
     * improved solution Will have 1 output to begin with. During training, the
     * expected output will be +1 for player win, -1 for player loss To decide
     * move to make in prediction mode, input first level possible moves and
     * make the move with max output. In theory, this will generate a quality of
     * the board and move from -1 to 1.
     */
    public NeuralNet() {
        input = new double[48];
        hidden = new double[12];
        output = new double[1];
        input_hidden = new double[input.length][hidden.length];
        hidden_output = new double[hidden.length][output.length];

        training = true;

        lambda = 1.0; // decreases over time
        rate = 0.5; //remains static
        player = 'X'; // for now
        outputLog = new ArrayList();
        moveList = new ArrayList();
        // continue contructor as needed        
    }

    public void updateWeights(double score) {
        //apply update
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < hidden.length; j++) {
                input_hidden[i][j] += score;
            }
        }

        for (int i = 0; i < hidden.length; i++) {
            for (int j = 0; j < output.length; j++) {
                hidden_output[i][j] += score;
            }
        }
    }

    @Override
    public String getName() {
        return "Neural Net";
    }

    @Override
    public Agent createNew(char team) {
        return new NeuralNet();
    }

    /**
     * Uses the static Control.stateSpace to make a decision then modifies the
     * Control.stateSpace to reflect that play.
     *
     * @param in
     * @return
     */
    @Override
    public StateSpace makeMove(StateSpace in) {
        player = in.player1Turn() ? Control.PLAYER1 : Control.PLAYER2;
        StateSpace best = null;
        double high = -100;
        //looks ahead one turn
        ArrayList<StateSpace> nextTurn = in.expandStateSpace(1);
        //gets list of valid moves
        for (StateSpace ss : nextTurn) {
            //evaluates each move, stores highest value move
            char[][] board = ss.getCharStateSpace();
            updateInput(board);
            sumValues();
            if (output[0] >= high) {
                high = output[0];
                best = ss;
            }
        }
        // if training boolean is true, use train method
        if (training && (best != null)) {
            train(best);
        }
        //returns high move
        return best;
    }

    /**
     * @return	A panel which allows users to modify all options for this agent
     * before the game starts.
     */
    @Override
    public JPanel createOptionPane() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        p.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        p.add(new JLabel("TD Neural Net", JLabel.CENTER));

        return p;
    }

    public void updateInput(char[][] board) {
        for (int i = 0; i < input.length; i++) {
            //assign new values to input
            if (board[i / 12][i % 12] == player) {
                input[i] = 1;
            } else if (board[i / 12][i % 12] == ' ') {
                input[i] = 0;
            } else {
                input[i] = -1;
            }
        }
    }

    public void sumValues() {
        double t = 0;

        for (int i = 0; i < hidden.length; i++) {
            t = 0;
            for (int j = 0; j < input.length; j++) {
                t += input[i] * input_hidden[j][i];
            }
            double sig = 1 / (1 + Math.exp(t)); //sigmoid continuous function
            hidden[i] = sig;
        }

        for (int i = 0; i < output.length; i++) {
            t = 0;
            for (int j = 0; j < hidden.length; j++) {
                t += hidden[i] * hidden_output[j][i];
            }
            double sig = 1 / (1 + Math.exp(t)); //sigmoid continuous function
            output[i] = sig;
        }
    }

    public void train(StateSpace in) {
        char[][] board = in.getCharStateSpace();
        //apply new board state to input nodes
        updateInput(board);
        //feed inputs forward through neural net
        sumValues();
        Double[] copyScore = new Double[output.length]; //capitol for object rather than primitive
        char[][] copyBoard = new char[board.length][board[0].length];

        //add current board state and output to ArrayLists
        for (int i = 0; i < copyScore.length; i++) {
            copyScore[i] = output[i];
        }
        for (int i = 0; i < copyBoard.length; i++) {
            for (int j = 0; j < copyBoard[0].length; j++) {
                copyBoard[i][j] = board[i][j];
            }
        }
        outputLog.add(copyScore);
        moveList.add(copyBoard);

        //calculate weight update value
        int logSize = outputLog.size();
        double sum = 0;

        for (int i = 0; i <= logSize; i++) {
            sum += Math.pow(lambda, logSize - i) * (outputLog.get(i)[0] * (1 - outputLog.get(i)[0]));
        }
        double weightChange;
        if (in.checkForWinSequence().isEmpty()) {
            weightChange = rate * (outputLog.get(logSize)[0] - outputLog.get(logSize - 1)[0]) * sum;
        } else {
            weightChange = rate * (1 - outputLog.get(logSize - 1)[0]) * sum;
        }
        updateWeights(weightChange); //apply weight change to all nodes
    }
}
