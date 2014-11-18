package aifinalproject;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*
 *  For now this is really just a dummy class to test the
 *  createOptionPane() method
 */
public class NeuralNet implements Agent {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ANN";
	}

	@Override
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel();
		p.add(new JLabel("ANN OPTIONS"));
		p.setBorder(BorderFactory.createLineBorder(Color.yellow));
		
		return p;
	}

}
