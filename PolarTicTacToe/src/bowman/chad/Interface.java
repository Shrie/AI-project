package bowman.chad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;

public class Interface extends JFrame implements ActionListener{
	
	// VARIABLES
	private static JTextArea console;
	private JLabel instruction;
	private int width, height;
	
	private JComboBox<Agent> agent1, 
					  		 agent2;
	
	private JLabel t1time, 
				   t2time;
	
	private JTextField t1score, 
					   t2score;
	
	private Board board;
	private JSpinner scoreToWin, rings;
	private JButton start;

	// CONSTRUCTOR
	public Interface(int width, int height){
		super("ARTIFICIAL INTELLIGENCE SEMESTER PROJECT");
		this.width = width;
		this.height = height;
		
		JScrollPane pane;
		add(pane = new JScrollPane(makeConsole()), BorderLayout.WEST);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(board = new Board(console), BorderLayout.CENTER);
		add(makeInfoPanel(), BorderLayout.EAST);
		add(makeBar(), BorderLayout.PAGE_END);
		
		prepareFrame();

	}
	
	// PRIVATE METHODS
	private void prepareFrame(){
		
		
		setPreferredSize(new Dimension(width,height));
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());
		
		Toolkit kit = this.getToolkit();
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] dev = env.getScreenDevices();
		Insets ins = kit.getScreenInsets(dev[0].getDefaultConfiguration());
		Dimension dim = kit.getScreenSize();
		int maxWidth = (dim.width - ins.left - ins.right);
		int maxHeight = (dim.height - ins.top - ins.bottom);
		this.setLocation((int) (maxWidth - this.getWidth()) / 2, 
				         (int) (maxHeight - this.getHeight()) / 2);
		this.setVisible(true);
	
	}
	
	private JPanel makeConsole(){
		
		JPanel p = new JPanel(new BorderLayout());
		p.setPreferredSize(new Dimension(width / 5, height));
		p.add(console = new JTextArea(), BorderLayout.CENTER);
		console.setEditable(false);
		
		return p;
		
	}
	
	
	private JPanel makeInfoPanel(){

		JLabel score;
		JPanel p = new JPanel(new GridLayout(0,1));
		p.setPreferredSize(new Dimension(width / 6, height));
		p.add(new JLabel(""));
		p.add(score = new JLabel("Score", JLabel.CENTER));
		
		
		JPanel m = new JPanel(new GridLayout());
		m.add(t1score = new JTextField("0"));
		t1score.setHorizontalAlignment(JTextField.CENTER);
		t1score.setEditable(false);
		m.add(t2score = new JTextField("0"));
		t2score.setHorizontalAlignment(JTextField.CENTER);
		t2score.setEditable(false);
		p.add(m);
		
		
		JPanel n = new JPanel(new GridLayout());
		n.add(t1time = new JLabel("00:00:00", JLabel.CENTER));
		n.add(t2time = new JLabel("00:00:00", JLabel.CENTER));
		p.add(n);
		
		p.add(new JLabel(""));
		
		
		p.add(agent1 = new JComboBox());
		agent1.setBorder(BorderFactory.createTitledBorder("Agent 1"));
		
		p.add(agent2 = new JComboBox());
		agent2.setBorder(BorderFactory.createTitledBorder("Agent 2"));
		
		p.add(scoreToWin = new JSpinner());
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		scoreToWin.setBorder(BorderFactory.createTitledBorder("Score to Win"));
		
		p.add(rings = new JSpinner());
		((DefaultEditor) rings.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) rings.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		rings.setValue((int) 5);
		rings.setBorder(BorderFactory.createTitledBorder("Number of Rings"));
		
		p.add(new JLabel(""));
		
		p.add(start = new JButton("Start!"));
		start.setBackground(Color.gray);
		start.setOpaque(true);
		start.setActionCommand("start");
		start.addActionListener(this);
		
		return p;
		
	}
	
	private JToolBar makeBar(){
		
		JToolBar t = new JToolBar();
		t.setFloatable(false);
		t.add(new JLabel("Your Turn!"));
		
		return t;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("start")){
			board.build((int) rings.getValue());
			start.setEnabled(false);
			scoreToWin.setEnabled(false);
			rings.setEnabled(false);
			agent1.setEnabled(false);
			agent2.setEnabled(false);
			
		}
	}
	
	
	// PUBLIC STATIC METHODS
	public static void print(String s){
		
		console.append(s + "\n");
		
	}
	
	public static void clear(){
		
		console.setText("");
		
	}

}
