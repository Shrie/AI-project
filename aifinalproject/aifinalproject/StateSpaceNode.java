package aifinalproject;

import java.util.ArrayList;

public class StateSpaceNode {
	//----variables----
	private char[][] stateSpace;
	private ArrayList<StateSpaceNode> children;
	//----end-variables----
	
	//----constructor----
	/**
	 * 
	 * @param sp current state space
	 * @param d depth
	 */
	public StateSpaceNode(char[][] sp, int d) {
		this.stateSpace = sp;
		this.children = new ArrayList<StateSpaceNode>();
	}
	
	public StateSpaceNode(char[][] sp) {
		this.stateSpace = sp;
		this.children = new ArrayList<StateSpaceNode>();
	}
	//----end-constructor----
	
	//----methods----
	public void makeChildren(){
		
	}
	//----end-methods----
}
