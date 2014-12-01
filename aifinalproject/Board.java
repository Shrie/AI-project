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
 */
public class Board extends JPanel 
				   implements MouseListener {

	private static final long serialVersionUID = -1129694514241624631L;
	
	private int rings = 0;
	private Node[][] nodes; // Every valid intersection
	
	private boolean reset;
	public static boolean moveMade = false;

	public Board() {

		
		addMouseListener(this);
		reset = false;

	}
	
	//===  METHODS ===
	
	private void initializeNodes(int x, int y) {

		int inc = x / (rings + 1);

		for (int i = 0; i < rings; i++) {

			int base = x - ((i + 1) * inc);
			int trigBase = (int) ((Math.cos(Math.PI / 6) * base));
			int trigHeight = (int) ((Math.sin(Math.PI / 6) * base));

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

	}
	
	
	private void drawNodes(Graphics2D g){
		
		Dimension size = getSize();

		int h = (int) size.getHeight();
		int w = (int) size.getWidth();
		int x = w / 2;
		int y = h / 2;
		
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
				
	}

	public void drawBoard(Graphics2D g) {
		
		setBackground(Color.white);
		
		if(rings == 0) return;
		
		Dimension size = getSize();

		int h = (int) size.getHeight();
		int w = (int) size.getWidth();
		int x = w / 2;
		int y = h / 2;
	
		// Draw lines
		int increment = w / (rings + 1) / 2;
		int trigBase = (int) ((Math.cos(Math.PI / 6) * x)); // x is hypotnuse
		int trigHeight = (int) ((Math.sin(Math.PI / 6) * x));

		// Axis
		g.drawLine(x, 0, x, h);
		g.drawLine(0, y, w, y);

		g.drawLine(x - trigHeight, y + trigBase, x + trigHeight, y - trigBase);
		g.drawLine(x - trigBase, y + trigHeight, x + trigBase, y - trigHeight);
		g.drawLine(x - trigBase, y - trigHeight, x + trigBase, y + trigHeight);
		g.drawLine(x - trigHeight, y - trigBase, x + trigHeight, y + trigBase);

		// Draw rings
		for (int i = 1; i <= rings + 1; i++)
			if (i != rings + 1)
				g.drawOval(x - increment * i, y - increment * i, 
						increment * i * 2, increment * i * 2);

	}
 


	public void build(int rings) {

		this.rings = rings;
		nodes = new Node[rings][12];
		Control.instance.stateSpace = new char[rings][12];
		
		for(int i=0; i<Control.instance.stateSpace.length; i++)
			for(int j=0; j<Control.instance.stateSpace[i].length; j++)
				Control.instance.stateSpace[i][j] = Control.NONE;
		
		initializeNodes((int) (getSize().getWidth() / 2), (int) (getSize()
				.getHeight() / 2));

		repaint();
		
		linkNodes();
	}
	
	/**
	 * Links all nodes by referencing "children" in "parent" node's arraylist.
	 */
	private void linkNodes(){
	
		
		for(int i=0; i<nodes.length; i++)
			for(int j=0; j<nodes[i].length; j++){
				
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
				
		
	}
	
	public void updateNodes(){
		
		for(int i=0; i<nodes.length; i++)
			for(int j=0; j<nodes[i].length; j++)				
				nodes[i][j].setTeam(Control.instance.stateSpace[i][j]);
				
		
	}
	
	public void reset(){
		
		for(int i=0; i<Control.instance.stateSpace.length; i++)
			for(int j=0; j<Control.instance.stateSpace[i].length; j++)
				Control.instance.stateSpace[i][j] = Control.NONE;
		
		updateNodes();
		
		repaint();
	}
	
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
	 * @param j		y-coordinate of matrix intrepretation of game board.
	 * @return		false if valid move, true if not valid
	 */
	private boolean invalidMove(int i, int j) {

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

		Graphics2D g2d =  (Graphics2D) g;
		
		
		drawBoard(g2d);
		drawNodes(g2d);
		
	
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	
		if((Control.instance.isPlayer1Human && Control.instance.player1Turn)
				|| (Control.instance.isPlayer2Human && !Control.instance.player1Turn))
			for(int i = 0; i < nodes.length; i++)
				for(int j = 0; j < nodes[i].length; j++)
					if(nodes[i][j].isInRange(e.getX(), e.getY())){					
						if(invalidMove(i, j) && !Control.instance.onFirstMove){
							
							Control.instance.getInterface().setPrompt("Invalid Move!");
							
						}else{
							
							if(Control.instance.player1Turn)
								Control.instance.stateSpace[i][j] = Control.PLAYER1;
							else
								Control.instance.stateSpace[i][j] = Control.PLAYER2;
							
							updateNodes();
							repaint();
							moveMade = true;
						}
					}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
