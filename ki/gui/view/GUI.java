package gui.view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import gui.controller.KI;
import gui.controller.Runner;
import gui.model.TicTacToe;

public class GUI extends JFrame{
	
	private static int size;
	public Runner controller;
	private ArrayList<JButton> buttonList;
	private ArrayList<Zuhoerer> listeners;
	
	public GUI(Runner r){
		controller = r;
		selectSize();
	}
	
	public void selectSize(){
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setSize(200, 100);
		setVisible(true);
		setLayout(new GridLayout(2,2));
		final JTextField text = new JTextField("Größe");
		final JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int trySize = Integer.parseInt(text.getText());
					if(trySize>0){
						GUI.size = trySize;
						controller.model = new TicTacToe(size);
						controller.ki = new KI(controller);
						remove(ok);
						remove(text);
						init();
					}
					else{text.setText("Größe >0 eingeben");}
				} catch (NumberFormatException e) {
					text.setText("Eine Zahl >0 eingeben");
				}
			}
		});
		add(text);
		add(ok);
	}
	
	public void init(){
		this.setSize(size*100, size*100);
		this.setVisible(true);
		this.setLayout(new GridLayout(size,size,size,size));
		buttonList = new ArrayList<JButton>();
		for (int i = 0; i < size*size; i++) {
			buttonList.add(new JButton());
		}
		int x=0,y = 0;
		Container inhalt = this.getContentPane();
		listeners = new ArrayList<Zuhoerer>();
		for (int i = 0; i < buttonList.size(); i++) {
			inhalt.add(buttonList.get(i));
			Zuhoerer temp = new Zuhoerer(new Point(x, y),buttonList,i,this);
			buttonList.get(i).addActionListener(temp);
			listeners.add(temp);
			x++;
			if((i+1)%size==0){
				x = 0;
				y++;
			}
		}
	}
	
	public ArrayList<JButton> getButtons(){
		return buttonList;
	}
	
	public ArrayList<Zuhoerer> getListeners(){
		return listeners;
	}
}
