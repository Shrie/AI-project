package bowman.chad;

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

public class Board extends JPanel implements MouseListener {
	
	private int rings;
	private ArrayList<Node> nodes; // Every valid intersection
	private boolean team1Turn;
	
	public Board(JTextArea console){
		
		this.nodes = new ArrayList<Node>();
		this.addMouseListener(this);
		this.team1Turn = true;
	}
	
	
	private void draw(Graphics gin){
		
		
		setBackground(Color.white);
		Graphics2D g = (Graphics2D) gin;
		
		Dimension size = getSize();
		
		
		int h = (int) size.getHeight();
		int w = (int) size.getWidth();
		int x = w / 2;
		int y = h / 2;
		
		if(nodes.size() < 1)
			gatherIntersections(x, y);
		
	
		// Draw lines
		int increment = w / (rings + 1) / 2;	
		int base = (int) Math.sqrt(0.5 * (w / 2) * (w / 2));
		
		g.drawLine(x, 0, x, h);
		g.drawLine(0, y, w, y);
		g.drawLine(x - base, y - base, x + base, y + base);
		g.drawLine(x - base, y + base, x + base, y - base);
		
		
		// Draw rings
		for(int i=1; i <= rings + 1; i++)
			if(i != rings + 1)
				g.drawOval(x - increment * i, 
						   y - increment * i, 
						   increment * i * 2, 
						   increment * i * 2);
		
	
		for(int i=0; i < nodes.size(); i++){
			
			if(nodes.get(i).getTeam() == Node.TEAM2)
				g.drawOval(nodes.get(i).x - 5, 
						   nodes.get(i).y - 5,
						   10, 10);
			
			if(nodes.get(i).getTeam() == Node.TEAM1){
				g.drawLine(nodes.get(i).x - 5,
						   nodes.get(i).y - 5,
						   nodes.get(i).x + 5,
						   nodes.get(i).y + 5);
				
				g.drawLine(nodes.get(i).x + 5,
						   nodes.get(i).y - 5,
						   nodes.get(i).x - 5,
						   nodes.get(i).y + 5);
			}
		}
		
	}
	
	
	private void gatherIntersections(int x, int y){
		
		int inc = x / (rings + 1);
		
		
		for(int i=1; i <= rings; i++){
			
			int base = inc * i; // Update half-width of circle
			int z = (int) Math.sqrt(0.5 * (base * base)); // Arc intersect
			
			nodes.add(new Node(x, y - base)); // Most top
			nodes.add(new Node(x + z, y - z));
			nodes.add(new Node(x + base, y));
			nodes.add(new Node(x + z, y + z));
			nodes.add(new Node(x, y + base)); // Most bottom
			nodes.add(new Node(x - z, y + z));
			nodes.add(new Node(x - base, y));
			nodes.add(new Node(x - z, y - z));
			
		}
		
	}
	
	// 	PUBLIC METHODS
	
	public void build(int rings){
		
		this.rings = rings;
		repaint();
		
	}
	
	// OVERRIDES
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(rings != 0)
			draw(g);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {

		Interface.print("X:" + e.getX() + " Y:" + e.getY());
		
		for(int i=0; i < nodes.size(); i++)
			if(nodes.get(i).isInRange(e.getX(), e.getY()))
				if(team1Turn){
					nodes.get(i).setTeam(Node.TEAM1);
					team1Turn = false;
				}else{
					nodes.get(i).setTeam(Node.TEAM2);
					team1Turn = true;
				}
		
	
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
