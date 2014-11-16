package aifinalproject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	
	// STATIC VARIABLES
	private static JTextArea console;
	private static JLabel prompt;
		
	
	// VARIABLES
	private int width, height;
	private ArrayList<Agent> agents;
	
	private JComboBox<String> agent1, 
					  		 agent2;
	
	private JLabel t1time, 
				   t2time;
	
	private JTextField t1score, 
    				   t2score;
	
	private JPanel topOptionPane, botOptionPane;
	
	private Board board;
	private JSpinner scoreToWin, rings;
	private JButton start;

	// CONSTRUCTOR
	public Interface(int width, int height, ArrayList<Agent> agents){
		super("ARTIFICIAL INTELLIGENCE SEMESTER PROJECT");
		this.width = width;
		this.height = height;
		
		JScrollPane pane;
		add(pane = new JScrollPane(makeConsole()), BorderLayout.WEST);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(board = new Board(), BorderLayout.CENTER);
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

		//Returned panel
		JPanel p = new JPanel(new GridLayout(1,0));
		
		//Left Panel
		JPanel left = new JPanel(new GridLayout(0,1));
		left.setPreferredSize(new Dimension(width / 6, height));
		left.add(new JLabel(""));
		left.add(new JLabel("Score", JLabel.CENTER));
		p.add(left);
		
		
		//Right Panel
		JPanel right = new JPanel(new GridLayout(0,1));
		JPanel topOptionPane = new JPanel(new CardLayout());
		JPanel botOptionPane = new JPanel(new CardLayout());
		topOptionPane.add(new JPanel(), "Human");
		botOptionPane.add(new JPanel(), "Human");
		
		if(agents != null)
			for(int i=0; i < agents.size(); i++){
				topOptionPane.add(agents.get(i).createOptionPane(), agents.get(i).getName());
				botOptionPane.add(agents.get(i).createOptionPane(), agents.get(i).getName());
			}
		
		p.add(right);
		
		// SCORE/WIN COUNTERS
		
		JPanel m = new JPanel(new GridLayout());
		m.add(t1score = new JTextField("0"));
		t1score.setHorizontalAlignment(JTextField.CENTER);
		t1score.setEditable(false);
		m.add(t2score = new JTextField("0"));
		t2score.setHorizontalAlignment(JTextField.CENTER);
		t2score.setEditable(false);
		left.add(m);
		
		
		// ELAPSED TIME TEAM COUNTERS
		
		JPanel n = new JPanel(new GridLayout());
		n.add(t1time = new JLabel("00:00:00", JLabel.CENTER));
		n.add(t2time = new JLabel("00:00:00", JLabel.CENTER));
		left.add(n);
		
		left.add(new JLabel(""));
		
		
		// AGENT SELECTION
		// Gather names of agents for ComboBox population
		String[] agentNames = (agents == null)? new String[1] : new String[agents.size() + 1];
		
		agentNames[0] = "Human";
		
		if(agents != null)
			for(int i=0; i < agents.size(); i++)
				agentNames[i + 1] = agents.get(i).getName();
			
		left.add(agent1 = new JComboBox<String>(agentNames));
		agent1.setBorder(BorderFactory.createTitledBorder("Agent 1"));
		agent1.setActionCommand("agent1Select");
		agent1.addActionListener(this);
		
		left.add(agent2 = new JComboBox<String>(agentNames));
		agent2.setBorder(BorderFactory.createTitledBorder("Agent 2"));
		agent2.setActionCommand("agent2Select");
		agent2.addActionListener(this);
		

		// SCORE TO WIN SPINNER
		left.add(scoreToWin = new JSpinner());
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		scoreToWin.setBorder(BorderFactory.createTitledBorder("Score to Win"));
		
		// NUMBER OF RINGS
		left.add(rings = new JSpinner());
		((DefaultEditor) rings.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) rings.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		rings.setValue((int) 5);
		rings.setBorder(BorderFactory.createTitledBorder("Number of Rings"));
		
		left.add(new JLabel(""));
		
		left.add(start = new JButton("Start!"));
		start.setBackground(Color.gray);
		start.setOpaque(true);
		start.setActionCommand("start");
		start.addActionListener(this);
		
		return p;
		
	}
	
	private JToolBar makeBar(){
		
		JToolBar t = new JToolBar();
		t.setFloatable(false);
		t.add(prompt = new JLabel("The prompt"));
		t.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 0));
		
		return t;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		CardLayout c1 = (CardLayout) topOptionPane.getLayout();
		CardLayout c2 = (CardLayout) botOptionPane.getLayout();
		
		
		if(e.getActionCommand().equals("start")){
			//Starting the game			
			
			board.build((int) rings.getValue());
			start.setEnabled(false);
			scoreToWin.setEnabled(false);
			rings.setEnabled(false);
			agent1.setEnabled(false);
			agent2.setEnabled(false);
			
		}else if(e.getActionCommand().equals("agent1Select")){
			//Selecting Agent 1, populating its options
			
			c1.show(topOptionPane, (String) agent1.getSelectedItem());
			
		}else if(e.getActionCommand().equals("agent2Select")){
			//Selecting Agent 2, populating its options
			
			c2.show(botOptionPane, (String) agent2.getSelectedItem());
			
		}
	}
	
	
	// STATIC METHODS
	public static void print(String s){
		
		console.append(s + "\n");
		
	}
	
	public static void clear(){
		
		console.setText("");
		
	}

}
