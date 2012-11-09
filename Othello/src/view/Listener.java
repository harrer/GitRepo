package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Move;

public class Listener implements ActionListener{

	private Move pos;
	int counter;
	private GUI gui;
	private int spieler;
	
	public Listener(Move pos, int counter, GUI gui){
		this.pos = new Move(pos.x, pos.y);
		this.counter = counter;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gui.setze(pos);
	}
}
