package aifinalproject;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 * A dummy agent which will select a random, valid node on the game frontier. 
 * This is to pilot the functionality of an Agent so others can follow suit (just
 * a whole lot smarter-like)
 */
public class Randy implements Agent {

	private char player;
	private int delay;
	
	private JSpinner del;
	
	public Randy(){
		
		this.player = Control.NONE;
		this.delay = 100;
	}
	
	public Randy(char player, int delay){
		
		this.delay = delay * 1000;
		this.player = player;
	}
	
	@Override
	public String getName() {
		return "Randy";
	}

	@Override
	public void makeMove() {
			
			char[][] ss = Control.instance.stateSpace;
			
			Random r = new Random();
			
			
			//if(Control.instance.onFirstMove)
				ss[Math.abs(r.nextInt(69)) % ss.length][Math.abs(r.nextInt(69)) % 11] = player;
				
				
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel(new GridLayout(0,1));
		p.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		
		p.add(new JLabel("Randy the Randomizer", JLabel.CENTER));
		
		del = new JSpinner();
		del.setBorder(BorderFactory.createTitledBorder("Delay (seconds)"));
		
		p.add(new JLabel());
		
		p.add(del);
		p.add(new JLabel());
		p.add(new JLabel());
		p.add(new JLabel());
		
		return p;
		
	}

	@Override
	public Randy createNew(char team) {
	
		return new Randy(team, (int) del.getValue());
	}

}
