package controller;

import java.awt.Color;
import java.util.Date;
import java.util.Random;

import model.*;
import view.*;

public class Runner extends Thread implements Player{
	
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
	
	public boolean setze(Move pos){
		if(player1){
			//Move m = ki.findeBestenZug(ki.möglicheZüge());
			//System.out.println(m.x+", "+m.y);
			if(new Date().getTime()-currentTime>maxTime){
				beende(1);
			}
			else if(board.getBoard()[pos.y][pos.x]==1){
				if(board.keinZugMöglich(1)){
					gui.setTitle("Othello - Schwarz passt. Spieler Weiß ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
					currentTime = new Date().getTime();
					player1 = false;
					return false;
				}
				else{
					gui.setTitle("Kann nicht passen, es sind Züge für Schwarz möglich!");
					return false;
				}
			}
			else if(board.setze(pos, 1)){
				gui.getButtons().get(pos.y*8+pos.x).setBackground(Color.black);
				currentTime = new Date().getTime();
				player1 = false;
				gui.update(board.getBoard());
				if(board.istFertig()==-1){
					gui.setTitle("Othello - Spieler Weiß ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
				}
				else{
					beende(-1);
				}
				return true;
			}
			return false;
		}
		else{
			if(new Date().getTime()-currentTime>maxTime){
				beende(2);
			}
			else if(board.getBoard()[pos.y][pos.x]==2){
				if(board.keinZugMöglich(2)){
					gui.setTitle("Othello - Weiß passt. Spieler Schwarz ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
					currentTime = new Date().getTime();
					player1 = true;
					return false;
				}
				else{
					gui.setTitle("Kann nicht passen, es sind Züge für Weiß möglich!");
					return false;
				}
			}
			else if(board.setze(pos, 2)){
				gui.getButtons().get(pos.y*8+pos.x).setBackground(Color.white);
				currentTime = new Date().getTime();
				player1 = true;
				gui.update(board.getBoard());
				if(board.istFertig()==-1){
					gui.setTitle("Othello - Spieler Schwarz ist an der Reihe! S: "+board.getBlack()+" W: "+board.getWhite()+" Zum passen auf eigenes Feld klicken.");
				}
				else beende(-1);
				return true;
			}
			return false;
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
		else if(player==1){
			gui.setTitle("Zeit von Schwarz ist um, Weiß gewinnt!");
		}
		else if(player==2){
			gui.setTitle("Zeit von Weiß ist um, Schwarz gewinnt!");
		}
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
		return null;
	}
	
}
