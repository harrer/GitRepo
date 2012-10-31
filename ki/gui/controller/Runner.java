package gui.controller;

import java.awt.Point;

import javax.swing.JOptionPane;

import gui.model.TicTacToe;
import gui.view.GUI;

public class Runner {
	
	public TicTacToe model;
	public GUI view;
	public KI ki;
	private boolean fertig;
	
	public Runner() {
		fertig = false;
		begin();
	}
	
	public String fallsFertig(){
		int f = model.fertig();
		if(f>=0){
			fertig = true;
			for (int i = 0; i < view.getButtons().size(); i++) {
				try {
					view.getButtons().get(i).removeActionListener(view.getListeners().get(i).getButton(i).getActionListeners()[0]);
				} catch (ArrayIndexOutOfBoundsException e) {}
			}//löscht alle ActionListeners der JButtons, s.d keine Aktion mehr vorgenommen werden kann
		}
		if(f==1)return "Spieler x hat gewonnen";
		else if(f==2) return "Spieler o hat gewonnen";
		else if(f==-1) return "Spiel noch nicht entschieden";
		else return "Spiel unentschieden";
	}
	
	private String result;
	
	public String getResult(){return result;}
	
	public void setze(int x, int y){
		model.setze('x', x, y);
		result = fallsFertig();
		if(result.equalsIgnoreCase("Spiel noch nicht entschieden")){
			Point xy = ki.position();
			System.out.println(xy.x+" "+xy.y);
			model.setze('o', xy.x, xy.y);
			view.getButtons().get((xy.x)*model.getBoard().length+xy.y).setText("o");
		}
		if(!fertig){result = fallsFertig();}
		view.setTitle(result);
	}
	
	public void begin() {
		view = new GUI(this);
	}
	
	public boolean getFertig(){return fertig;}
	
	public static void main(String[] args) {
		new Runner();
	}
}