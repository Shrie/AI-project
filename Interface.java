package aifinalproject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	
	private JPanel options, agent1Options, agent2Options; // Panel to toggle agent options and console
	
	private Board board;
	private JSpinner scoreToWin, rings;
	private JButton start;

	// CONSTRUCTOR
	public Interface(int width, int height, ArrayList<Agent> agents){
		super("ARTIFICIAL INTELLIGENCE SEMESTER PROJECT");
		this.width = width;
		this.height = height;
		this.agents = agents;
		
		add(makeConsolePane(), BorderLayout.WEST);
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
	
	private JPanel makeConsolePane(){
		
		
		
	    options = new JPanel(new CardLayout());
	    options.setPreferredSize(new Dimension((width - height + 20) / 2, height));
		
	    
		// Top half, agent1's options, bottom half agent2's
		JPanel agentOptions = new JPanel(new GridLayout(0,1));
		
		agent1Options = new JPanel(new CardLayout());
		agent2Options = new JPanel(new CardLayout());
		
		agent1Options.add(createHumanOptionsPane(), "0");
		agent2Options.add(createHumanOptionsPane(), "0");
		
		if(agents != null)
			for(int i=0; i < agents.size(); i++){
				agent1Options.add(agents.get(i).createOptionPane(), "" + (i+1));
				agent2Options.add(agents.get(i).createOptionPane(), "" + (i+1));
			}
		
		agentOptions.add(agent1Options);
		agentOptions.add(agent2Options);
		
		options.add(agentOptions, "options");
		 
		
		// Console
		JPanel consolePane = new JPanel(new BorderLayout());
		JScrollPane pane;
		consolePane.add(pane = new JScrollPane(console = new JTextArea()), BorderLayout.CENTER);
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		console.setEditable(false);
		options.add(consolePane, "console");
		
		
		
		return options;
		
	}
	
	private JPanel createHumanOptionsPane(){
		
		JPanel p = new JPanel();
		p.add(new JLabel("Human Options"));
		p.setBorder(BorderFactory.createLineBorder(Color.green));
		
		return p;
		
	}
	
	
	private JPanel makeInfoPanel(){

		
		//Left Panel
		JPanel info = new JPanel(new GridLayout(0,1));
		info.setPreferredSize(new Dimension((width - height - 30) / 2, height));
		info.add(new JLabel(""));
		info.add(new JLabel("Score", JLabel.CENTER));
		
		
		JPanel m = new JPanel(new GridLayout());
		m.add(t1score = new JTextField("0"));
		t1score.setHorizontalAlignment(JTextField.CENTER);
		t1score.setEditable(false);
		m.add(t2score = new JTextField("0"));
		t2score.setHorizontalAlignment(JTextField.CENTER);
		t2score.setEditable(false);
		info.add(m);
		
		
		// ELAPSED TIME TEAM COUNTERS
		
		JPanel n = new JPanel(new GridLayout());
		n.add(t1time = new JLabel("00:00:00", JLabel.CENTER));
		t1time.setFont(new Font("Courier New", Font.BOLD, 12));
		n.add(t2time = new JLabel("00:00:00", JLabel.CENTER));
		t2time.setFont(new Font("Courier New", Font.BOLD, 12));
		info.add(n);
		
		info.add(new JLabel(""));
		
		
		// AGENT SELECTION
		// Gather names of agents for ComboBox population
		String[] agentNames = (agents == null)? new String[1] : new String[agents.size() + 1];
		
		agentNames[0] = "Human";
		 
		if(agents != null)
			for(int i=0; i < agents.size(); i++)
				agentNames[i + 1] = agents.get(i).getName();
			
		info.add(agent1 = new JComboBox<String>(agentNames));
		agent1.setBorder(BorderFactory.createTitledBorder("Agent 1"));
		agent1.setActionCommand("agent1Select");
		agent1.addActionListener(this);
		
		info.add(agent2 = new JComboBox<String>(agentNames));
		agent2.setBorder(BorderFactory.createTitledBorder("Agent 2"));
		agent2.setActionCommand("agent2Select");
		agent2.addActionListener(this);
		

		// SCORE TO WIN SPINNER
		info.add(scoreToWin = new JSpinner());
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) scoreToWin.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		scoreToWin.setBorder(BorderFactory.createTitledBorder("Score to Win"));
		
		// NUMBER OF RINGS
		info.add(rings = new JSpinner());
		((DefaultEditor) rings.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) rings.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
		rings.setValue((int) 5);
		rings.setBorder(BorderFactory.createTitledBorder("Number of Rings"));
		
		info.add(new JLabel(""));
		
		info.add(start = new JButton("Start!"));
		start.setBackground(Color.gray);
		start.setOpaque(true);
		start.setActionCommand("start");
		start.addActionListener(this);
		
		return info;
		
	}
	
	private JToolBar makeBar(){
		
		JToolBar t = new JToolBar();
		t.setFloatable(false);
		t.add(prompt = new JLabel("The prompt"));
		t.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 0));
		
		return t;
		
	}
	
	// PUBLIC METHODS
	
	public void updateT1Time(long time){
		
		
		int cs = (int) time % 60;
		int sec = (int) (time / 60) % 60; 
		int min = (int) (time / 3600) % 60;
		
		t1time.setText(String.format("%2d:%2d:%2d", min, sec, cs));
		
	}
	
	public void updateT2Time(long time){
		
		
		int cs = (int) time % 60;
		int sec = (int) (time / 60) % 60; 
		int min = (int) (time / 3600) % 60;
		
		t2time.setText(String.format("%2d:%2d:%2d", min, sec, cs));
		
	}
	
	public void setPrompt(String p){
		
		prompt.setText(p);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		CardLayout cards = (CardLayout) options.getLayout();
		CardLayout ag1 = (CardLayout) agent1Options.getLayout();
		CardLayout ag2 = (CardLayout) agent2Options.getLayout();
		
		if(e.getActionCommand().equals("start")){
			//Starting the game			
			
			//Lock everything
			board.build((int) rings.getValue());
			start.setEnabled(false);
			scoreToWin.setEnabled(false);
			rings.setEnabled(false);
			agent1.setEnabled(false);
			agent2.setEnabled(false);
			
			//Show console
			cards.show(options, "console");
			
			//Start Game!
			boolean t1 = (((String) agent1.getSelectedItem()).equals("Human"))? true : false;
			boolean t2 = (((String) agent2.getSelectedItem()).equals("Human"))? true : false;
			
			Control.instance.startGame(t1, t2);
			
		}else if(e.getActionCommand().equals("agent1Select")){
			//Selecting Agent 1, populating its options
			

			ag1.show(agent1Options, "" + agent1.getSelectedIndex());
		
			
		}else if(e.getActionCommand().equals("agent2Select")){
			//Selecting Agent 2, populating its options
			
			
			ag2.show(agent2Options, "" + agent2.getSelectedIndex());
		
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
