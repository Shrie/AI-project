package aifinalproject;

import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
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
	
	public Randy(){
		
		this.player = Control.NONE;
	}
	
	public Randy(char player){
		
		this.delay = delay * 10;
		this.player = player;
	}
	
	@Override
	public String getName() {
		return "Randy";
	}

	@Override
	public void makeMove() {
		
		char[][] ss = Control.stateSpace;
		
		Random r = new Random(100);
		
		
		//if(Control.instance.onFirstMove)
			ss[Math.abs(r.nextInt(69)) % ss.length][Math.abs(r.nextInt(69)) % 11] = player;

			Interface.print("Move made!");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		JSpinner delay = new JSpinner();
		delay.setBorder(BorderFactory.createTitledBorder("Delay Between Moves"));
		
		p.add(delay);
		
		return p;
		
	}

	@Override
	public Randy createNew(char team) {
		
		return new Randy(team);
	}

}
