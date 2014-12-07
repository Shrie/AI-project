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
public class NeuralNetNode {
    NeuralNetNode[] inputs, output;
    double weights[]; //weights for inputs
    double c = 1; //learning constant
    int index; //might need for message passing
    double outVal;
    
    public double sigmoid() {
        double t = 0;
        for (int i = 0; i < inputs.length; i++) {
            t += inputs[i].outVal * weights[i]; //still need to get value out of input
        }
        
        double out = 1 / (1 + Math.exp(t)); //sigmoid continuous function
        return out;
    }
}
