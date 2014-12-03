package aifinalproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DefaultEditor;

/**
 * A dummy agent which will select a random, valid node. 
 * This is to pilot the functionality of an Agent so others can follow suit (just
 * a whole lot smarter-like)
 */
public class Randy implements Agent {

	//=== VARIABLES ===
	private char player; // Team char, usually X or O
	private int delay;   // Seconds to delay a response
	private boolean legalMoves; // Allow only legal moves?
	
	// Options Panel Variables
	private JSpinner del; // Spinner to select delay
	private JCheckBox legal;
	
	//=== CONSTRUCTORS ===
	/**
	 * Generic Randy. Intended for game initialization.
	 */
	public Randy(){
		
		this.player = Control.NONE;
		this.delay = 100;
	}
	
	/**
	 * Intended for actual Agent gameplay.
	 * 
	 * @param player	Team character, usually X or O
	 * @param delay		Seconds to delay a move decision.
	 */
	public Randy(char player, int delay, boolean legalMoves){
		
		this.delay = delay * 1000;
		this.player = player;
		this.legalMoves = legalMoves;
	}
	
	
	//=== OVERRIDES ===
	@Override
	public String getName() {
		
		return "Randy";
	}

	@Override
	public void makeMove() {
			
			StateSpace ss = Control.instance.stateSpace; // Grab current stateSpace
			
			// Randomly choose indices
			Random r = new Random();
			int x = r.nextInt(ss.getStateSpace().length - 1);
			int y = r.nextInt(11);
			
			if(legalMoves && !Control.instance.onFirstMove){
				
				while(ss.invalidMove(x, y)){ // If invalid, try again
					
					// Randomly reselect a node
					x = r.nextInt(ss.getStateSpace().length - 1); 
					y = r.nextInt(11);
					
				}
					
			}
			
			
			try {
				Thread.sleep(delay); // Delay
				
			} catch (InterruptedException e) {
				
				Interface.print("Randy Delay Interrupted!");
			}
			
			ss.setNode(player, x, y);  // Set node
			
	}

	/**
	 * Interface for users to select options for Randy before game starts.
	 */
	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel(new GridLayout(0,1));
		p.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		p.add(new JLabel("Randy the Randomizer", JLabel.CENTER));
		
		del = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		del.setBorder(BorderFactory.createTitledBorder("Delay (seconds)"));
		((DefaultEditor) del.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) del.getEditor()).getTextField().setEditable(false);
		
		p.add(new JLabel());
		
		p.add(legal = new JCheckBox("Only Legal Moves"));
		legal.setSelected(true);
		
		p.add(del);
		p.add(new JLabel());

		
		return p;
		
	}

	@Override
	public Randy createNew(char team) {
	
		return new Randy(team, (int) del.getValue(), legal.isSelected());
	}

}// END RANDY
