package aifinalproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Controls the drawing out and human interaction of our matrix representation
 *  of the tick tack toe game board.
 *  
 *  Extends JPanel.
 */
public class Board extends JPanel 
				   implements MouseListener {

	private static final long serialVersionUID = -1129694514241624631L;
	
	//=== VARIABLES ===
	private int rings = 0;  // Number of rings on board
	private Node[][] nodes; // Every valid intersection
	
	public boolean moveMade = false; // Used for human interaction to control the Human Action Listeners in Interface

	
	//=== CONSTRUCTOR ===
	public Board() {

		addMouseListener(this);

	}
	
	//===  METHODS ===
	/**
	 * Initializes stateSpace and paints game board for the first time.
	 * 
	 * @param rings		Number of rings to have on the board.
	 */
	public void build(int rings) {

		this.rings = rings;
		
		// Initialize nodes and stateSpace
		nodes = new Node[rings][12];
		Control.instance.stateSpace = new char[rings][12];
		
		// Set all stateSpace elements to NONE to start
		for(int i=0; i<Control.instance.stateSpace.length; i++)
			for(int j=0; j<Control.instance.stateSpace[i].length; j++)
				Control.instance.stateSpace[i][j] = Control.NONE;
		
		// Initialize nodes 
		initializeNodes((int) (getSize().getWidth() / 2), (int) (getSize().getHeight() / 2));

		// Link Nodes to form a graph
		linkNodes();
		
		repaint();

	}// END build()
	
	/**
	 * To be called once after building board to create Nodes at each intersection. 
	 * 
	 * @param x		X-Coordinate of center of board.
	 * @param y		Y-Coordinate of center of board.
	 */
	private void initializeNodes(int x, int y) {

		int inc = x / (rings + 1); // Increment between each ring

		for (int i = 0; i < rings; i++) { // Cycle through rings

			int base = x - ((i + 1) * inc) - 2;
			int trigBase = (int) ((Math.cos(Math.PI / 6) * base)) - 1;
			int trigHeight = (int) ((Math.sin(Math.PI / 6) * base)) - 1;

			// Quad 1
			nodes[i][0] = new Node(x, y - base); // Most top
			nodes[i][1] = new Node(x + trigHeight, y - trigBase);
			nodes[i][2] = new Node(x + trigBase, y - trigHeight);

			// Quad 2
			nodes[i][3] = new Node(x + base, y);
			nodes[i][4] = new Node(x + trigBase, y + trigHeight);
			nodes[i][5] = new Node(x + trigHeight, y + trigBase);

			// Quad 3
			nodes[i][6] = new Node(x, y + base); // Most bottom
			nodes[i][7] = new Node(x - trigHeight, y + trigBase);
			nodes[i][8] = new Node(x - trigBase, y + trigHeight);

			// Quad 4
			nodes[i][9] = new Node(x - base, y);
			nodes[i][10] = new Node(x - trigBase, y - trigHeight);
			nodes[i][11] = new Node(x - trigHeight, y - trigBase);

		}

	}// END intializeNodes()
	
	/**
	 * Draws the game board axis and circles.
	 * 
	 * @param g		Passed through from paintComponent()
	 */
	private void drawBoard(Graphics2D g) {
			
			setBackground(Color.white);
			
			if(rings == 0) return;	// Before number of rings are chosen, skip building board.
			
			Dimension size = getSize();
	
			int h = (int) size.getHeight();	// Width of board
			int w = (int) size.getWidth();	// Height of board
			int x = w / 2;					// X-Coordinate of center of board
			int y = h / 2;					// Y-Coordinate of center of board
		
			// Draw lines
			int increment = w / (rings + 1) / 2;
			int trigBase = (int) ((Math.cos(Math.PI / 6) * x)); // x is hypotnuse
			int trigHeight = (int) ((Math.sin(Math.PI / 6) * x));
	
			// Axis
			g.drawLine(x, 0, x, h);
			g.drawLine(0, y, w, y);
	
			// Diagnonal lines
			g.drawLine(x - trigHeight, y + trigBase, x + trigHeight, y - trigBase);
			g.drawLine(x - trigBase, y + trigHeight, x + trigBase, y - trigHeight);
			g.drawLine(x - trigBase, y - trigHeight, x + trigBase, y + trigHeight);
			g.drawLine(x - trigHeight, y - trigBase, x + trigHeight, y + trigBase);
	
			// Draw rings
			for (int i = 1; i <= rings + 1; i++)
				if (i != rings + 1)
					g.drawOval(x - increment * i, y - increment * i, 
							increment * i * 2, increment * i * 2);
	
	}// END drawBoard()
 
	
	/**
	 * Draws the current game state of Nodes.
	 * 
	 * TODO Use images instead of lines.
	 * 
	 * @param g		Passed through from paintComponent()
	 */
	private void drawNodes(Graphics2D g){
		
		// Print Node States
		if(rings != 0)
			for(int i = 0; i < nodes.length; i++)
				for(int j = 0; j < nodes[i].length; j++) {
					
					if(nodes[i][j].getTeam() == Control.PLAYER1) {
						g.drawLine(nodes[i][j].x - 5, nodes[i][j].y - 5,
								nodes[i][j].x + 5, nodes[i][j].y + 5);
	
						g.drawLine(nodes[i][j].x + 5, nodes[i][j].y - 5,
								nodes[i][j].x - 5, nodes[i][j].y + 5);	
					}
					
					
					if(nodes[i][j].getTeam() == Control.PLAYER2)
						g.drawOval(nodes[i][j].x - 5, nodes[i][j].y - 5, 10, 10);
								
				}	
				
	}// END drawNodes()

	
	/**
	 * Creates a graph by linking all nodes by referencing "children" in "parent" node's arraylist.
	 */
	private void linkNodes(){
	
		
		for(int i=0; i<nodes.length; i++)
			for(int j=0; j<nodes[i].length; j++){ // Cycle through each node
				
				// Allows wrap-around of nodes
				int offset1 = (j == 0)? -11 : 1; 
				int offset2 = (j == nodes[i].length - 1)? -11 : 1;
			
			
				if(i != 0){
					nodes[i][j].addTopLeft(nodes[i - 1][j - offset1]);	// Top Left
					nodes[i][j].addTop(nodes[i - 1][j]);				// Top Middle
					nodes[i][j].addTopRight(nodes[i - 1][j + offset2]);	// Top Right
				}
				
				nodes[i][j].addLeft(nodes[i][j - offset1]);				// Left
				nodes[i][j].addRight(nodes[i][j + offset2]); 	  	    // Right
				
				if(i != nodes.length - 1){
					nodes[i][j].addBottomLeft(nodes[i + 1][j - offset1]); // Bottom Left
					nodes[i][j].addBottom(nodes[i + 1][j]);				  // Bottom Middle
					nodes[i][j].addBottomRight(nodes[i + 1][j + offset2]);// Bottom Right
				}
			}
				
	}// END linkNodes()
	
	/**
	 * Updates the nodes double array from stateSpace
	 */
	public void updateNodes(){
		
		for(int i=0; i<nodes.length; i++)
			for(int j=0; j<nodes[i].length; j++)				
				nodes[i][j].setTeam(Control.instance.stateSpace[i][j]);
		
	}
	
	/**
	 * Sets all elements of stateSpace to NONE, updates the nodes accordingly, and repaints the board.
	 */
	public void reset(){
		
		for(int i=0; i<Control.instance.stateSpace.length; i++)
			for(int j=0; j<Control.instance.stateSpace[i].length; j++)
				Control.instance.stateSpace[i][j] = Control.NONE;
		
		updateNodes(); // Copy changes to nodes
		
		repaint();
	}
	
	/**
	 * Updates the nodes from stateSpace before returning the nodes double array.
	 * 
	 * @return		nodes double array
	 */
	public Node[][] getNodes(){
		
		updateNodes();
		return nodes;
	}
	
	/**
	 * Checks i,j coordinates for an invalid move.
	 * Returns true if location already has been played
	 * or if there are no played moves adjacent (or diagonal) to
	 * it. 
	 * 
	 * @param i		x-coordinate of matrix interpretation of game board.
	 * @param j		y-coordinate of matrix interpretation of game board.
	 * @return		false if valid move, true if not valid
	 */
	public boolean invalidMove(int i, int j) {

		// Check for out-of-bounds indices
		if (i < 0 || i > Control.instance.stateSpace.length - 1 || j < 0 || j > 11)
			return true;
		
		// Check if exact node has already been played
		if (Control.instance.stateSpace[i][j] != Control.NONE)
			return true;
		
		// Check for any adjacent played nodes
		return !adjacentPlayed(i, j);

	} // END invalidMove()
	
	
	/**
	 * Checks if any adjacent node from reference has been played, includes
	 * check for diagonals.
	 * 
	 * @param i		x-coordinate of node in matrix representation of game board.
	 * @param j		y-coordinate of node in matrix representation of game board.
	 * @return		true if any node adjacent or immediately diagonal has been played,
	 * 				false if none have been played yet.
	 */
	public boolean adjacentPlayed(int i, int j) {

		updateNodes(); // Sync with stateSpace
		
		ArrayList<Node> adj = nodes[i][j].getChildren();
		
		for(int k=0; k<adj.size(); k++)
			if(adj.get(k) != null)
				if(adj.get(k).getTeam() != Control.NONE)
					return true;
			
				
		return false;

	} // END adjacentPlayed() 

	//=== OVERRIDES ===

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Called each time repaint() is called
		drawBoard((Graphics2D) g);
		drawNodes((Graphics2D) g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		if((Control.instance.isPlayer1Human && Control.instance.player1Turn)
				|| (Control.instance.isPlayer2Human && !Control.instance.player1Turn)) // If Current Player is Human
			for(int i = 0; i < nodes.length; i++)			
				for(int j = 0; j < nodes[i].length; j++)												
					if(nodes[i][j].isInRange(e.getX(), e.getY())){					   // If any Node is in the location of click
						if(invalidMove(i, j) && !Control.instance.onFirstMove){		   
							
							Control.instance.getInterface().setPrompt("Invalid Move!"); // Invalid Node selection
							
						}else{	// Valid Node selection
							
							// Make mvoe
							if(Control.instance.player1Turn)
								Control.instance.stateSpace[i][j] = Control.PLAYER1;
							else
								Control.instance.stateSpace[i][j] = Control.PLAYER2;
							
							repaint();		 // Update board
							moveMade = true; // Valid move finished by Human agent
						}
					}

	}// END mouseClicked()

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//TODO Show a valid location upon mouse hover?
	}

	//=== NOT USED ===
	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}// END BOARD
