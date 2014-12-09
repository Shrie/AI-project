package aifinalproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
		
		// Initialize nodes
		Node[][] nodes = new Node[rings][12];
		
		
		// Initialize nodes 
		initializeNodes(nodes, (int) (getSize().getWidth() / 2), (int) (getSize().getHeight() / 2));

		// Initialize stateSpace
		Control.instance.stateSpace = new StateSpace(nodes);
		
		repaint();

	}// END build()
	
	/**
	 * To be called once after building board to create Nodes at each intersection. 
	 * 
	 * @param x		X-Coordinate of center of board.
	 * @param y		Y-Coordinate of center of board.
	 */
	private void initializeNodes(Node[][] nodes, int x, int y) {

		int inc = x / (rings + 1); // Increment between each ring

		for (int i = 0; i < rings; i++) { // Cycle through rings

			int base = x - ((i + 1) * inc) - 2;
			int trigBase = (int) ((Math.cos(Math.PI / 6) * base)) - 1;
			int trigHeight = (int) ((Math.sin(Math.PI / 6) * base)) - 1;

			// Quad 1
			nodes[i][0] = new Node(Control.NONE, x, y - base, i, 0); // Most top
			nodes[i][1] = new Node(Control.NONE, x + trigHeight, y - trigBase, i, 1);
			nodes[i][2] = new Node(Control.NONE, x + trigBase, y - trigHeight, i, 2);

			// Quad 2
			nodes[i][3] = new Node(Control.NONE, x + base, y, i, 3);
			nodes[i][4] = new Node(Control.NONE, x + trigBase, y + trigHeight, i, 4);
			nodes[i][5] = new Node(Control.NONE, x + trigHeight, y + trigBase, i, 5);

			// Quad 3
			nodes[i][6] = new Node(Control.NONE, x, y + base, i, 6); // Most bottom
			nodes[i][7] = new Node(Control.NONE, x - trigHeight, y + trigBase, i, 7);
			nodes[i][8] = new Node(Control.NONE, x - trigBase, y + trigHeight, i, 8);

			// Quad 4
			nodes[i][9] = new Node(Control.NONE, x - base, y, i, 9);
			nodes[i][10] = new Node(Control.NONE, x - trigBase, y - trigHeight, i, 10);
			nodes[i][11] = new Node(Control.NONE, x - trigHeight, y - trigBase, i, 11);

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
		
		if(rings == 0)
			return; 
		
		Node[][] nodes = Control.instance.stateSpace.getStateSpace();
		g.setStroke(new BasicStroke(3));
		
		// Print Node States
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
	
		StateSpace ss = Control.instance.stateSpace;
		
		if((Control.instance.isPlayer1Human && ss.player1Turn())
				|| (Control.instance.isPlayer2Human && !ss.player1Turn())) // If Current Player is Human
			for(int i = 0; i < ss.getStateSpace().length; i++)			
				for(int j = 0; j < ss.getStateSpace()[i].length; j++)												
					if(ss.getStateSpace()[i][j].isInRange(e.getX(), e.getY())){		// If any Node is in the location of click
						if(ss.invalidMove(i, j) && !Control.instance.onFirstMove){		   
							
							Control.instance.getInterface().setPrompt("Invalid Move!"); // Invalid Node selection
							
						}else{	// Valid Node selection
							
							// Make move
							if(ss.player1Turn())
								ss.getStateSpace()[i][j].setTeam(Control.PLAYER1);
							else
								ss.getStateSpace()[i][j].setTeam(Control.PLAYER2);
							
							
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
