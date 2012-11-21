package controller;

import java.awt.Color;
import java.util.Date;
import java.util.Random;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import model.*;
import view.*;

public class Runner implements Player{
	
	private Board board;
	private GUI gui;
	private KI ki;
	private boolean player1;//schwarz==true
	private long maxTime;
	private Random random;
	private long currentTime;
	
	public Runner(int order, long t, Random rnd){
		board = new Board(0,8, new Random(), this);
		gui = new GUI(this);
		ki = new KI(board);
		player1 = true;
		if(player1){
			gui.setTitle("Othello - Schwarz beginnt!");
		}
		else{
			gui.setTitle("Othello - Weiß beginnt!");
		}
		init(order, t, rnd);
		for (int i = 0; i < 64; i++) {
			gui.getButtons().get(i).setBackground(Color.green);
		}
		gui.getButtons().get(27).setBackground(Color.white);
		gui.getButtons().get(28).setBackground(Color.black);
		gui.getButtons().get(35).setBackground(Color.black);
		gui.getButtons().get(36).setBackground(Color.white);
		currentTime = new Date().getTime();
	}
	
	public void setzeKI(Move move){
		if(move!=null){
			//try {Thread.sleep(random.nextInt(5)*1000);} catch (Exception e) {}
			board.setze(move, 2);
			gui.update(board.getBoard());
			if(board.istFertig()==-1){
				gui.setTitle("Othello - Spieler Schwarz ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
			}
			else{
				beende(-1);
			}
		}
		else{
			System.out.println("Computer passt");
			gui.setTitle("Passe, Schwarz ist an der Reihe!");
		}
	}
	
	public void setze(Move pos){
//		if(new Date().getTime()-currentTime>maxTime){
//			beende(1);
//		}
		if(board.getBoard()[pos.y][pos.x]==1){
			if(board.keinZugMöglich(1)){
				gui.setTitle("Othello - Schwarz passt. Spieler Weiß ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
				currentTime = new Date().getTime();
				setzeKI(nextMove(pos, new Date().getTime()-currentTime, 8000));
				gui.update(board.getBoard());
			}
			else{
				gui.setTitle("Kann nicht passen, es sind Züge für Schwarz möglich!");
			}
		}
		else if(board.setze(pos, 1)){
			gui.getButtons().get(pos.y*8+pos.x).setBackground(Color.black);
			currentTime = new Date().getTime();
			gui.update(board.getBoard());
			if(board.istFertig()==-1){
				gui.setTitle("Othello - Spieler Weiß ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
				
				setzeKI(nextMove(pos, new Date().getTime()-currentTime, 8000));
				gui.update(board.getBoard());
			}
			else{
				beende(-1);
			}
		}
	}
	
	private void beende(int player) {
		if(player==-1){
			int fertig = board.istFertig();
			if(fertig==0){
				gui.setTitle("Unentschieden!");
			}
			else if(fertig==1){
				gui.setTitle("Schwarz gewinnt! "+board.getBlack()+":"+board.getWhite());
			}
			else{
				gui.setTitle("Weiß gewinnt! "+board.getWhite()+":"+board.getBlack());
			}
		}
//		else if(player==1){
//			gui.setTitle("Zeit von Schwarz ist um, Weiß gewinnt!");
//		}
//		else if(player==2){
//			gui.setTitle("Zeit von Weiß ist um, Schwarz gewinnt!");
//		}
		for (int i = 0; i < 64; i++) {
			gui.getButtons().get(i).removeActionListener(gui.getButtons().get(i).getActionListeners()[0]);
		}
	}

	public GUI getGui() {return gui;}
	
	public static void main(String[] args){
		new Runner(0, 8000, new Random());
	}

	@Override
	public void init(int order, long t, Random rnd) {
		if(order==0){
			player1 = true;
		}
		else if(order==1){
			player1 = false;
		}
		maxTime = t;
		random = rnd;
	}

	@Override
	public Move nextMove(Move prevMove, long tOpponent, long t) {
		return ki.findeBestenZug(ki.möglicheZüge());
	}
	
}
