package aifinalproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Controls the drawing out and human interaction of our matrix representation
 *  of the tick tack toe game board.
 */
public class Board extends JPanel 
				   implements MouseListener {

	private static final long serialVersionUID = -1129694514241624631L;
	
	private int rings = 0;
	private Node[][] nodes; // Every valid intersection

	public Board() {

		this.addMouseListener(this);

	}

	private void draw(Graphics gin) {

		setBackground(Color.white);
		Graphics2D g = (Graphics2D) gin;

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
				g.drawOval(x - increment * i, y - increment * i, increment * i
						* 2, increment * i * 2);

		// Print Node States
		for (int i = 0; i < nodes.length; i++)
			for (int j = 0; j < nodes[i].length; j++) {

				if (nodes[i][j].getTeam() == Control.PLAYER2)
					g.drawOval(nodes[i][j].x - 5, nodes[i][j].y - 5, 10, 10);

				if (nodes[i][j].getTeam() == Control.PLAYER1) {
					g.drawLine(nodes[i][j].x - 5, nodes[i][j].y - 5,
							nodes[i][j].x + 5, nodes[i][j].y + 5);

					g.drawLine(nodes[i][j].x + 5, nodes[i][j].y - 5,
							nodes[i][j].x - 5, nodes[i][j].y + 5);

				}
			}

	}

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

	public void build(int rings) {

		this.rings = rings;
		nodes = new Node[rings][12];
		Control.stateSpace = new char[rings][12];
		initializeNodes((int) (getSize().getWidth() / 2), (int) (getSize()
				.getHeight() / 2));
		updateSS();

		repaint();

	}

	private void updateSS() {

		for (int i = 0; i < nodes.length; i++)
			for (int j = 0; j < nodes[i].length; j++)
				Control.stateSpace[i][j] = nodes[i][j].getTeam();

	}

	//=== OVERRIDES

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (rings != 0)
			draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		for (int i = 0; i < nodes.length; i++)
			for (int j = 0; j < nodes[i].length; j++)
				if (nodes[i][j].isInRange(e.getX(), e.getY())){
					
					//TODO
					Interface.print("Human plays will work again soon.");
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
