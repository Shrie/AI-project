package aifinalproject;

import javax.swing.JPanel;
/**
 * Interface for all Agents including Human
 *
 */
public interface Agent {
	
	/**
	 * Pretty self-explanatory here
	 */
	public String getName();

	/**
	 * Uses the static Control.stateSpace to make a decision then 
	 * modifies the Control.stateSpace to reflect that play. 
	 */
	public void makeMove();

	/**
	 * @return		A panel which allows users to modify all options for this
	 * 				agent before the game starts.
	 */
	public JPanel createOptionPane();
	
	/**
	 * This method is called right before the game starts to make a brand new
	 * instance of this specific agent with all the modifications made via the 
	 * option pane. See Interface.actionPerformed() for an example. 
	 * 
	 * @param team		char PLAYER1 or PLAYER2 (X or O)
	 * @return			A brand new instance of your class, but with modifications
	 */
	public Agent createNew(char team);

}
