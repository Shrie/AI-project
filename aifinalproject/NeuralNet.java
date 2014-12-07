/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aifinalproject;

/**
 *
 * @author Mason
 */
public class NeuralNet {
    //input nodes
    NeuralNetNode inputs[] = new NeuralNetNode[48];
    //hidden layer
    NeuralNetNode hidden[] = new NeuralNetNode[12];
    //output node
    NeuralNetNode outputs[] = new NeuralNetNode[1];
    
    //connection values
    int[][] input_hidden = new int[inputs.length][hidden.length];
    int[][] hidden_output = new int[hidden.length][outputs.length];
}
