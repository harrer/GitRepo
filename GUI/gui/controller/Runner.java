package gui.controller;

import gui.model.TicTacToe;
import gui.view.GUI;

public class Runner {
	
	public TicTacToe model;
	public GUI view;
	private boolean fertig;
	
	public Runner() {
		fertig = false;
		begin();
	}
	
	public void setze(char c, int x, int y){
		model.setze(c, x, y);
		if(model.fertig()>=0){
			fertig = true;
			for (int i = 0; i < view.getButtons().size(); i++) {
				view.getButtons().get(i).removeActionListener(view.getListeners().get(i).getButton(i).getActionListeners()[0]);
			}//löscht alle ActionListeners der JButtons, s.d keine Aktion mehr vorgenommen werden kann
		}
	}
	
	public void begin() {
		view = new GUI(this);
	}
	
	public boolean getFertig(){return fertig;}
	
	public static void main(String[] args) {
		new Runner();
	}
}