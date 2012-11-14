package gui.view;

import gui.controller.Runner;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Zuhoerer implements ActionListener {

	private int counter;
	private Point pos;
	private ArrayList<JButton> jb;
	private GUI gui;

	public Zuhoerer(Point p, ArrayList<JButton> jb, int i, GUI g) {
		counter = i;
		pos = p;
		this.jb = jb;
		gui = g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(gui.controller.model.getBoard()[pos.y][pos.x]==0){
			gui.controller.setze(gui.controller.model.getNextPlayer(), pos.y+1, pos.x+1);
			jb.get(counter).setText(""+gui.controller.model.getLastplayer());
			gui.setTitle("Noch nicht entschieden.");
		}
		if(gui.controller.getFertig()){
			if(gui.controller.model.fertig()>0){
				gui.setTitle("Spieler "+gui.controller.model.getLastplayer()+" hat gewonnen.");
				JOptionPane.showMessageDialog(gui, "Spieler "+gui.controller.model.getLastplayer()+" hat gewonnen.\n Nochmal?");
			}
			else{
				gui.setTitle("Unentschieden.");
				JOptionPane.showMessageDialog(gui,"Unentschieden");
			}
			gui.setVisible(false);
			new Runner();
		}
	}
	
	public JButton getButton(int i){
		return jb.get(i);
	}
}
