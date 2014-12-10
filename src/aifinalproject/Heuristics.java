package aifinalproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Heuristics implements Agent {

    //=== VARIABLES ===
    private char player;
    private int delay,
    			depth,
    			heuristic;
    
    private long totalTime;
    
    private Minimax mm;
    
    private boolean prune;
    
    private JSpinner delaySpin,
    				 depthSpin;
    
    private JRadioButton h1, h2;
    
    private JCheckBox pruneCheck;

    //=== CONSTRUCTORS ===
    public Heuristics() {

    }

    public Heuristics(char player, int delay, int depth, boolean prune, int heuristic) {

    	this.delay = delay;
        this.player = player;
        this.depth = depth;
        this.prune = prune;
        this.heuristic = heuristic;
        this.totalTime = 0;
    }

    //=== METHODS ===
    
    public long getTotalTime(){
    	
    	return totalTime;
    }
    
    //=== OVERRIDES ===
    @Override
    public String getName() {

        return "Heuristics";
    }

    @Override
    public StateSpace makeMove(StateSpace in) {
    	
    	long time = System.nanoTime();

    	try {
    		
			Thread.sleep(delay * 1000);
			
		} catch (InterruptedException e) {
			Interface.print("Agent Sleep Interrupted!");
		}
 
    	Minimax mm = new Minimax(depth, prune, player, heuristic);
    	StateSpace move = mm.decideMove(in);

    	
    	time = System.nanoTime() - time;
    	totalTime += time;
    	Interface.clear();
    	Interface.print("\n      Player " + player);
    	Interface.print(String.format(" Move made in %d ms", time / 1000000));
    	Interface.print(" Search depth: " + depth);
    	Interface.print(" Nodes evaluated: " + mm.getNodesEvaluated());
    	
    	
    	return move;
    }

    @Override
    public JPanel createOptionPane() {

        JPanel p = new JPanel(new GridLayout(0, 1));
        p.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        p.add(new JLabel("Various Heuristics", JLabel.CENTER));

        p.add(h1 = new JRadioButton("Heuristic 1", true));
        h1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        p.add(h2 = new JRadioButton("Heuristic 2"));
        h2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        ButtonGroup gp = new ButtonGroup();
        gp.add(h1);
        gp.add(h2);
        
        p.add(depthSpin = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1)));
        depthSpin.setBorder(BorderFactory.createTitledBorder("Minimax Depth"));
		((DefaultEditor) depthSpin.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) depthSpin.getEditor()).getTextField().setEditable(false);
		
		p.add(pruneCheck = new JCheckBox("Alpha-Beta Pruning"));
		pruneCheck.setSelected(true);
		
		p.add(delaySpin = new JSpinner(new SpinnerNumberModel(0, 0, 25, 1)));
		delaySpin.setBorder(BorderFactory.createTitledBorder("Delay (seconds)"));
		((DefaultEditor) delaySpin.getEditor()).getTextField()
			.setHorizontalAlignment(JTextField.CENTER);
		((DefaultEditor) delaySpin.getEditor()).getTextField().setEditable(false);

	
        return p;
    }

    @Override
    public Agent createNew(char team) {

    	int h = (h1.isSelected())? StateSpace.HEURISTIC1 : StateSpace.HEURISTIC2;
    	
        return new Heuristics(team, (int) delaySpin.getValue(), 
        		(int) depthSpin.getValue(), pruneCheck.isSelected(), h);
    }

}// END HEURISTICS
