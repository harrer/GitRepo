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
			gui.controller.setze(pos.y, pos.x);
			jb.get(counter).setText("x");
		}
		if(gui.controller.getFertig()){
			JOptionPane.showMessageDialog(gui,gui.controller.getResult()+".\n Nochmal?");
			gui.setVisible(false);
			new Runner();
		}
	}
	
	public JButton getButton(int i){
		return jb.get(i);
	}
}
