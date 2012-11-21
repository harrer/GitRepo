package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Move;
import controller.*;

public class GUI extends JFrame{
	
	private Runner controller;
	private ArrayList<MyButton> buttons;
	
	public GUI(Runner cont){
		super();
		buttons = new ArrayList<MyButton>();
		controller = cont;
		init();
	}
	
	public void setze(Move pos){
		controller.setze(pos);
	}
	
	public void update(int[][] board){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j]==1){
					buttons.get(i*8+j).setBackground(Color.black);
				}
				else if(board[i][j]==2){
					buttons.get(i*8+j).setBackground(Color.white);
				}
			}
		}
	}
	
	public void init(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);
		setVisible(true);
		setTitle("Othello");
		for (int i = 0; i < 64; i++) {
			buttons.add(new MyButton());//buttons.add(new JButton());
			buttons.get(i).addActionListener(new Listener(new Move(i%8, i/8), i, this));
			buttons.get(i).setVisible(true);
			add(buttons.get(i));
		}
		setLayout(new GridLayout(8, 8));
	}
	
	public ArrayList<MyButton> getButtons() {return buttons;}
	
	public class MyButton extends JButton {
		
		private Move move;
		
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if(move==null){
	    		g.setColor(this.getBackground());
		        g.drawLine(0, 0, 100, 100);
		        g.drawLine(50, 0, 50, 100);
		        g.drawLine(100, 0, 0, 100);
		        g.drawLine(0, 50, 100, 50);
	    	}
	    	else if((move.x==1&&move.y==-1)||(move.x==-1&&move.y==1)){//1,5
	    		g.setColor(Color.red);
	    		g.drawLine(100, 0, 0, 100);
	    	}
	    	else if((move.x==1&&move.y==0)||(move.x==-1&&move.y==0)){//2,6
	    		g.setColor(Color.red);
	    		g.drawLine(0, 50, 100, 50);
	    	}
	    	else if((move.x==0&&move.y==-1)||(move.x==0&&move.y==1)){//4,8
	    		g.setColor(Color.red);
	    		g.drawLine(50, 1, 50, 100);
	    	}
	    	else if((move.x==1&&move.y==1)||(move.x==-1&&move.y==-1)){//3,7
	    		g.setColor(Color.red);
	    		g.drawLine(0, 0, 100, 100);
	    	}
	        
	    }
	    
	    public void paintLine(Move move){
	    	this.move = move;
	    	paintComponent(this.getGraphics());
	    	repaint();
	    }
	}
}