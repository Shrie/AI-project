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
    private JSpinner del,
    				 dep;
    
    private boolean h1;
    private int delay,
    			depth;

    //=== CONSTRUCTORS ===
    public Heuristics() {

    }

    public Heuristics(char player, boolean h1, int delay, int depth) {

        this.player = player;
        this.h1 = h1;
        this.delay = delay;
        this.depth = depth;
    }

    //=== METHODS ===



    //=== OVERRIDES ===
    @Override
    public String getName() {

        return "Heuristics";
    }

    @Override
    public void makeMove() {
    	
    	int h = (h1)? StateSpace.HEURISTIC1 : StateSpace.HEURISTIC2;
    	
    	Minimax mm = new Minimax(depth, false, player, h);
    	
    	try {
			Thread.sleep(delay * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	StateSpace move = mm.decideMove(Control.instance.stateSpace);
    
    	Control.instance.stateSpace = move; // Finalize move
    	
    	
    }

    @Override
    public JPanel createOptionPane() {

        JPanel p = new JPanel(new GridLayout(0, 1));
        p.add(new JLabel("Various Heuristics", JLabel.CENTER));
       
		
        p.add(mason = new JRadioButton("Mason's", true));
        p.add(chad = new JRadioButton("Chad's"));
        
        ButtonGroup gp = new ButtonGroup();
        gp.add(mason);
        gp.add(chad);
        
        dep = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        dep.setBorder(BorderFactory.createTitledBorder("Minimax Depth"));
		((DefaultEditor) dep.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) dep.getEditor()).getTextField().setEditable(false);
		p.add(dep);
        
        del = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
		del.setBorder(BorderFactory.createTitledBorder("Delay (seconds)"));
		((DefaultEditor) del.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) del.getEditor()).getTextField().setEditable(false);
		p.add(del);
        
		return p;
    }

    @Override
    public Agent createNew(char team) {

    	boolean h1 = (mason.isSelected())? true : false;
    	
        return new Heuristics(team, h1, (int)del.getValue(), (int)dep.getValue());
    }

}// END HEURISTICS
