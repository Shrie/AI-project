package aifinalproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import aifinalproject.FileManager.AFile;

public class Classifier implements Agent {

	private ArrayList<AFile> files;
	private char player;
	private int threshold;
	
	private JComboBox<File> input;
	private ArrayList<Classified> data;
	private JSpinner thresh;
	
	public Classifier(){
		
		files = new FileManager().getClassiferFiles();
	}
	
	public Classifier(char player, ArrayList<Classified> data, int threshold){
		
		this.player = player;
		this.data = data;
		this.threshold = threshold;
	
	}
	
	@Override
	public String getName() {
		
		return "Classifer";
	}

	@Override
	public StateSpace makeMove(StateSpace in) {
		
		in.expandStateSpace(1);
		
		ArrayList<StateSpace> poss = in.getChildren();
		
		StateSpace move = poss.get(new Random().nextInt(poss.size() - 1));
		
		for(int i=0; i<poss.size(); i++){ // For each possible move
			
			Classified[] top5 = new Classified[5];
			
			for(int j=0; j<data.size(); j++){ // Compare against DB
				
				data.get(j).evaluate(poss.get(i)); // Evaluate space
				
				for(int k=0; k<top5.length; k++)
					if(top5[k] == null)
						top5[k] = data.get(j);
					else if(data.get(j).value > top5[k].value)
						top5[k] = data.get(j);
		
			}
			
			int supportingStates = 0;
			
			if(top5[0] != null)
				for(int j=0; j<top5.length; j++)
					if(top5[j].winFor == player)
						supportingStates++;
			
			if(supportingStates > (threshold - 1) / 2){
				move = poss.get(i);
				break;
			}
			
		}
		
		
		return move;

	}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel(new GridLayout(0,1));
		
		p.setBorder(BorderFactory.createLineBorder(Color.RED));
		p.add(new JLabel("K-NN Classifier", JLabel.CENTER));
		
		p.add(input = new JComboBox<File>());
		for(int i=0; i<files.size(); i++)
			input.addItem(files.get(i));
		
		input.setBorder(BorderFactory.createTitledBorder("Select Database"));
	
		p.add(thresh = new JSpinner(new SpinnerNumberModel(5, 3, 51, 2)));
		thresh.setBorder(BorderFactory.createTitledBorder("Nearest Neighbors"));
		((DefaultEditor) thresh.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) thresh.getEditor()).getTextField().setEditable(false);
		
		p.add(new JLabel());
		p.add(new JLabel());
		
		return p;
	}

	@Override
	public Classifier createNew(char team) {
		
		BufferedReader r;
		data = new ArrayList<Classified>();
		String line;
		
		try {
			r = new BufferedReader(new FileReader((File) input.getSelectedItem()));
			line = r.readLine();
			
			while(line != null){
				
				data.add(new Classified(new StateSpace(line.substring(0, line.length() - 2)), line.charAt(line.length() - 1)));
				line = r.readLine();
			}
			
			r.close();
			
			Interface.print("Classified " + data.size() + " states.");
			
		} catch (FileNotFoundException e) {
			Interface.print("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new Classifier(team, data, (int) thresh.getValue());
	}
	
	private class Classified{
		
		public char winFor;
		public StateSpace state;
		public int value;
		
		public Classified(StateSpace ss, char win){
			
			state = ss;
			winFor = win;
	
		}
		
		public int evaluate(StateSpace ss){
			
			int val = 0;
			char[][] ss1 = state.getCharStateSpace();
			char[][] ss2 = ss.getCharStateSpace();
			
			for(int i=0; i<ss1.length; i++)
				for(int j=0; j<ss1[i].length; j++)
					if(ss1[i][j] == ss2[i][j])
						val++;
			
			this.value = val;
			
			return val;
		}
		
	}

}
