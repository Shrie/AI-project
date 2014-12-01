package aifinalproject;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Used to designate Human players as well as provide a place to 
 * create options. 
 */
public class Human implements Agent {

	private char team;
	
	public Human(){
		
		this.team = Control.NONE;
	}
	
	public Human(char team){
		
		this.team = team;
	}
	
	
	
	@Override
	public String getName() {
		return "Human";
	}

	/**
	 * Does nothing for a human player. 
	 * Game turns are instead handled by Control and Board.
	 */
	@Override
	public void makeMove() {}

	@Override
	public JPanel createOptionPane() {
		
		JPanel p = new JPanel(); // Panel to return
		
		p.add(new JLabel("Human Options"));
		p.setBorder(BorderFactory.createLineBorder(Color.orange));
		
		//TODO Ad some human options... such as??
		
		// but for now...
		p.add(new JLabel());
		JCheckBox prayer = new JCheckBox("No Prayer");
		prayer.setSelected(true);
		p.add(prayer);

		return p;
		
	}

	@Override
	public Human createNew(char team) {
		
		return new Human(team);
	}

}
