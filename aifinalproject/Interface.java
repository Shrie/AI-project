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
import javax.swing.SpinnerNumberModel;

/**
 * Manages the graphical interface.
 * Extends JFrame. 
 */
public class Interface extends JFrame 
					   implements ActionListener {

	private static final long serialVersionUID = 520977260727391732L;
	
	//=== STATIC VARIABLES ===
	private static JTextArea console; // Console on left side to provide live-data
	private static JLabel prompt;	  // Prompt on bottom of screen

	//=== VARIABLES ===
	private int width,	// Width of frame 
				height; // Height of frame
	
	private JComboBox<String> agent1Select, // Agent 1 selection 
							  agent2Select; // Agent 2 selection

	private JLabel player1Time, // Current elapsed time of player 1
				   player2Time;	// Current elapsed time of player 2

	private JTextField player1Score, // Number of games won by player 1
					   player2Score; // Number of games won by player 2

	private JPanel options, 	  // Parent panel contains console and agent options
				   agent1Options, // Panel where options can be changed for agent 1
				   agent2Options; // Panel where options can be changed for agent 2

	private final Board board; // Game board
	
	private JSpinner scoreToWin, // How many games won before program stops and winner declared?
					 rings;      // How many rings to start with and draw?
							
	private JButton start; // Button to start the game
	
	private ArrayList<Agent> player1Agents, // All available agents for player 1
							 player2Agents; // All available agents for player 2
											// Needed to provide JPanels for Agent options
											// as well as their names for the ComboBoxes

	//=== CONSTRUCTOR ===
	/**
	 * Only constructor. 
	 * 
	 * @param width		Width of entire frame
	 * @param height	Height of entire frame
	 * @param agents1	List of available agents for Player 1 to choose from
	 * @param agents2	List of available agents for Player 3 to choose from
	 */
	public Interface(int width, int height, ArrayList<Agent> agents1, ArrayList<Agent> agents2) {
		super("ARTIFICIALLY INTELLIGENT TIC TAC TOE"); // Frame title
		
		//Initialize variables
		this.width = width;
		this.height = height;
		this.player1Agents = agents1;
		this.player2Agents = agents2;

		//Build Interface
		add(makeConsolePane(), BorderLayout.WEST);		// Left
		add(board = new Board(), BorderLayout.CENTER);  // Center
		add(makeInfoPanel(), BorderLayout.EAST);		// Right
		add(makeBar(), BorderLayout.PAGE_END);			// Bottom

		// Center frame on screen and tidy up
		prepareFrame();
		
	} // END CONSTRUCTOR

	
	//=== METHODS ===
	/**
	 * 
	 * @return	The game board used.
	 */
	public Board getBoard(){
		
		return board;
	}
	
	//=== GUI INITIALIZATION ===
	/**
	 * Creates and centers frame on screen.
	 */
	private void prepareFrame() {

		setPreferredSize(new Dimension(width, height));
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		Toolkit kit = this.getToolkit();
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] dev = env.getScreenDevices();
		Insets ins = kit.getScreenInsets(dev[0].getDefaultConfiguration());
		Dimension dim = kit.getScreenSize();
		int maxWidth = (dim.width - ins.left - ins.right);
		int maxHeight = (dim.height - ins.top - ins.bottom);
		this.setLocation((int) (maxWidth - this.getWidth()) / 2,
				(int) (maxHeight - this.getHeight()) / 2);
		this.setVisible(true);

	} // END prepareFrame()

	/**
	 * Uses CardLayout to toggle between showing the console (during gameplay) and Agent option
	 * panels (before gameplay). Agent option panels allow users to modify certain traits in regards 
	 * to corresponding agents selected via the two ComboBoxes on the right of the frame.
	 * 
	 * @return		Skinny vertical panel to be placed on left side of frame. Contains console and 
	 * 				Agent option panels. 
	 */
	private JPanel makeConsolePane() {

		options = new JPanel(new CardLayout()); // JPanel which houses console and Agent options
		options.setPreferredSize(new Dimension((width - height + 20) / 2, height)); // Size up

		//=== AGENT OPTION PANELS ====
		JPanel agentOptions = new JPanel(new GridLayout(0, 1));

		agent1Options = new JPanel(new CardLayout()); // Top Panel
		agent2Options = new JPanel(new CardLayout()); // Bottom Panel

		// Add agent's option panels
		// Use index values as card 'names'
		if(player1Agents != null)
			for(int i = 0; i < player1Agents.size(); i++) {
				
				//Populate card layout for agent option panes.
				agent1Options.add(player1Agents.get(i).createOptionPane(), "" + i); 
				agent2Options.add(player2Agents.get(i).createOptionPane(), "" + i);
			}

		// Add two option panels to parent panel
		agentOptions.add(agent1Options);
		agentOptions.add(agent2Options);

		// Add to grandparent panel
		options.add(agentOptions, "options");

		//=== CONSOLE ===
		JPanel consolePane = new JPanel(new BorderLayout());
		JScrollPane pane; // Scroll pane for Console
		consolePane.add(pane = new JScrollPane(console = new JTextArea()), BorderLayout.CENTER);
		
		// Remove ScrollPane's horizontal bar (because it's ugly for a console)
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		console.setEditable(false); // Read-only console
		options.add(consolePane, "console");

		return options;

	} // END makeConsolePane()


	/**
	 * Info panel contains information like player scores, elapsed time, as well as pre-game
	 * options like agent selection, ring selection, and score to win.
	 * 
	 * @return		JPanel with real-time information and pre-game options.
	 */
	private JPanel makeInfoPanel() {

		// Left Panel
		JPanel info = new JPanel(new GridLayout(0, 1));
		info.setPreferredSize(new Dimension((width - height - 30) / 2, height));
		info.add(new JLabel("")); // Spcae
		info.add(new JLabel("Score", JLabel.CENTER));

		JPanel m = new JPanel(new GridLayout());
		m.add(player1Score = new JTextField("0"));
		player1Score.setHorizontalAlignment(JTextField.CENTER);
		player1Score.setEditable(false);
		m.add(player2Score = new JTextField("0"));
		player2Score.setHorizontalAlignment(JTextField.CENTER);
		player2Score.setEditable(false);
		info.add(m);

		// ELAPSED TIME TEAM COUNTERS
		JPanel n = new JPanel(new GridLayout());
		n.add(player1Time = new JLabel("00:00:00", JLabel.CENTER));
		player1Time.setFont(new Font("Courier New", Font.BOLD, 12));
		n.add(player2Time = new JLabel("00:00:00", JLabel.CENTER));
		player2Time.setFont(new Font("Courier New", Font.BOLD, 12));
		info.add(n);

		info.add(new JLabel(""));

		// AGENT SELECTION
		// Gather names of agents for ComboBox population
		String[] agentNames = (player1Agents == null) ? new String[0]
				: new String[player1Agents.size()];

		if(player1Agents != null)
			for(int i = 0; i < player1Agents.size(); i++)
				agentNames[i] = player1Agents.get(i).getName();

		info.add(agent1Select = new JComboBox<String>(agentNames));
		agent1Select.setBorder(BorderFactory.createTitledBorder("Player 1"));
		agent1Select.setActionCommand("agent1SelectSelect");
		agent1Select.addActionListener(this); // Action Listener for populating agent options

		info.add(agent2Select = new JComboBox<String>(agentNames));
		agent2Select.setBorder(BorderFactory.createTitledBorder("Player 2"));
		agent2Select.setActionCommand("agent2SelectSelect");
		agent2Select.addActionListener(this);

		// SCORE TO WIN SPINNER
		info.add(scoreToWin = new JSpinner(new SpinnerNumberModel(10, 1, 1000000, 1)));
		((DefaultEditor) scoreToWin.getEditor()).getTextField()
				.setHorizontalAlignment(JTextField.CENTER);
		scoreToWin.setValue((int) 10);
		scoreToWin.setBorder(BorderFactory.createTitledBorder("Score to Win"));

		// NUMBER OF RINGS
		info.add(rings = new JSpinner(new SpinnerNumberModel(5, 1, 25, 1)));
		((DefaultEditor) rings.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) rings.getEditor()).getTextField()
				.setHorizontalAlignment(JTextField.CENTER);
		rings.setValue((int) 4);
		rings.setBorder(BorderFactory.createTitledBorder("Number of Rings"));

		info.add(new JLabel("")); //Space

		info.add(start = new JButton("Start!"));
		start.setBackground(Color.gray);
		start.setOpaque(true);
		start.setActionCommand("start");
		start.addActionListener(this);

		return info;

	} // END makeInfoPanel()

	/**
	 * Contains only one element, a JLabel to be used as a user prompt.
	 * 
	 * @return	JToolBar to function as a user prompt.
	 */
	private JToolBar makeBar() {

		JToolBar t = new JToolBar();
		t.setFloatable(false);
		t.add(prompt = new JLabel(" "));
		t.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

		return t;

	} // END makeBar()
	
	
	//=== GUI UPDATE METHODS ===
	/**
	 * Updates the elapsed time label for Player 1.
	 * 
	 * @param time	Elapsed time in sixitiths of a second.
	 */
	public void updatePlayer1Time(long time) {

		int cs = (int) time % 60;
		int sec = (int) (time / 60) % 60;
		int min = (int) (time / 3600) % 60;

		player1Time.setText(String.format("%2d:%2d:%2d", min, sec, cs));

	} // END updatePlayer1Time()

	/**
	 * Updates the elapsed time label for Player 2.
	 * 
	 * @param time	Elapsed time in sixitiths of a second.
	 */
	public void updatePlayer2Time(long time) {

		int cs = (int) time % 60;
		int sec = (int) (time / 60) % 60;
		int min = (int) (time / 3600) % 60;

		player2Time.setText(String.format("%2d:%2d:%2d", min, sec, cs));

	} // END updatePlayer2Time()
	
	/**
	 * Updates JLabel displaying Player 1's score.
	 * 
	 * @param score		Player 1 score.
	 */
	public void updatePlayer1Score(int score){
		
		player1Score.setText("" + score);
	}
	
	/**
	 * Updates JLabel for displaying Player 2's score.
	 * 
	 * @param score		Player 2 score.
	 */
	public void updatePlayer2Score(int score){
		
		player2Score.setText("" + score);
	}

	/**
	 * Sets text in bottom toolbar/prompt.
	 * 
	 * TODO Incorporate sounds?
	 * 
	 * @param text	Text to replace in prompt.
	 */
	public void setPrompt(String text) {

		prompt.setText(text);
	}


	//=== OVERRIDES ===
	
	@Override
	public void actionPerformed(ActionEvent e) {

		CardLayout cards = (CardLayout) options.getLayout();
		CardLayout ag1 = (CardLayout) agent1Options.getLayout();
		CardLayout ag2 = (CardLayout) agent2Options.getLayout();

		if (e.getActionCommand().equals("start")) {
			// Starting the game

			board.build((int) rings.getValue()); // Build board
			
			// Set score to win
			Control.instance.setScoreToWin((int) scoreToWin.getValue());
			
			// Lock options down
			start.setEnabled(false);
			scoreToWin.setEnabled(false);
			rings.setEnabled(false);
			agent1Select.setEnabled(false);
			agent2Select.setEnabled(false);
			
			// Show console
			cards.show(options, "console");
			
			// Create new Agents to start playing
			final Agent play1 = player1Agents.get(agent1Select.getSelectedIndex()).createNew(Control.PLAYER1);
			final Agent play2 = player2Agents.get(agent2Select.getSelectedIndex()).createNew(Control.PLAYER2);
			
			//=== START GAME THREAD ===
			new Thread(){
				public void run(){
					Control.instance.playGame(play1, play2);
				}
			}.start();
			

		} else if (e.getActionCommand().equals("agent1SelectSelect")) {
			// Selecting Agent 1, populating its options

			ag1.show(agent1Options, "" + agent1Select.getSelectedIndex());

		} else if (e.getActionCommand().equals("agent2SelectSelect")) {
			// Selecting Agent 2, populating its options

			ag2.show(agent2Options, "" + agent2Select.getSelectedIndex());

		}
		
	} // END actionPreformed()

	//=== STATIC METHODS ===
	/**
	 * Prints to the console
	 * 
	 * TODO make text wrap somehow so it doesn't run off the edge of the textarea.
	 * 
	 * @param s		Text to append to the console.
	 */
	public static void print(String s) {

		console.append(s + "\n");

	}

	/**
	 * Clears the console.
	 */
	public static void clear() {

		console.setText("");

	}

} // END INTERFACE
