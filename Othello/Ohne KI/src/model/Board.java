package model;

import java.util.ArrayList;
import java.util.Random;

import controller.Player;
import controller.Runner;

public class Board{

	public static final int size = 8;
	private int[][] board;//[y/vert][x/horizontal] 0==leer 1==schwarz 2==weiß
	//private boolean order;//false==schwarz/S1/order0, true==weiß/S2/order1
	private int currentPlayer;
	private Runner controller;
	
	public Board(int order, long t, java.util.Random rnd, Runner cont) {
		board = new int[size][size];
		board[3][3] = 2; board[3][4] = 1; board[4][3] = 1; board[4][4] = 2;
		controller = cont;
	}
	
	public boolean setze(Move move, int player){
		if(board[move.y][move.x]!=0){
			return false;
		}
		ArrayList<Move> richtungen = korrekterZug(move, player);
		if((player==1||player==2)&&richtungen.size()>0){
			board[move.y][move.x] = player;
			currentPlayer = player;
			dreheAngrenzendeSteineUm(move, richtungen);
			return true;
		}
		else{
			return false;
		}
	}
	
	public int andererSpieler(int spieler){
		if(spieler==1){return 2;}
		else{return 1;}
	}
	
	public ArrayList<Move> korrekterZug(Move move, int player){
		int otherPlayer = andererSpieler(player);
		ArrayList<Move> directionList = new ArrayList<Move>();
		if(!(move.x>=0&&move.x<size&&move.y>=0&&move.y<size)){//sind die Koordinaten korrekt?
			return directionList;
		}
		
		if(prüfAutomat(true, player, otherPlayer, move, 1,-1)){directionList.add(new Move(1,-1));}// /^
		if(prüfAutomat(true, player, otherPlayer, move, 1, 0)){directionList.add(new Move(1, 0));}// ->
		if(prüfAutomat(true, player, otherPlayer, move, 1, 1)){directionList.add(new Move(1, 1));}// \v
		if(prüfAutomat(true, player, otherPlayer, move, 0, 1)){directionList.add(new Move(0, 1));}// |v
		if(prüfAutomat(true, player, otherPlayer, move, -1,1)){directionList.add(new Move(-1,1));}// v/
		if(prüfAutomat(true, player, otherPlayer, move, -1,0)){directionList.add(new Move(-1,0));}// <-
		if(prüfAutomat(true, player, otherPlayer, move,-1,-1)){directionList.add(new Move(-1,-1));}// ^\
		if(prüfAutomat(true, player, otherPlayer, move, 0,-1)){directionList.add(new Move(0, -1));}// |^
		
		return directionList;
	}
	
	public boolean prüfAutomat(boolean first, int s1, int s2, Move lastPos, int nachRechts, int nachUnten){
		Move nextMove = new Move(lastPos.x+nachRechts, lastPos.y+nachUnten);
		if(nextMove.x<0||nextMove.y<0||nextMove.x>=size||nextMove.y>=size){
			return false;
		}
		if(board[nextMove.y][nextMove.x]==0){//falls das angrenzende Feld leer ist
			return false;
		}
		if(board[nextMove.y][nextMove.x]==s2){
			return prüfAutomat(false, s1, s2, nextMove, nachRechts, nachUnten);
		}
		if(board[nextMove.y][nextMove.x]==s1){
			if(first){
				return false;
			}
			return true;
		}
		else{
			return prüfAutomat(false, s1, s2, nextMove, nachRechts, nachUnten);
		}
	}
	
	public void dreheAngrenzendeSteineUm(Move move, ArrayList<Move> richtungen){
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				controller.getGui().getButtons().get(i*8+j).paintLine(null);//setzt alle Markierungen zurück
			}
		}
		for (int i = 0; i < richtungen.size(); i++) {
			Move next = new Move(richtungen.get(i).x+move.x, richtungen.get(i).y+move.y);
			while(!(next.x<0||next.y<0||next.x>=size||next.y>=size)&&board[next.y][next.x]!=currentPlayer){
				board[next.y][next.x] = currentPlayer;
				controller.getGui().getButtons().get(next.y*8+next.x).paintLine(richtungen.get(i));//zeigt an,
				//welche Steine umgedreht worden sind, malt rote Linie darüber.
				next = new Move(next.x+richtungen.get(i).x, next.y+richtungen.get(i).y);
			}
		}
	}
	
	public boolean keinZugMöglich(int player){
		int length = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				length+=korrekterZug(new Move(j, i), player).size();
			}
		}
		if(length==0){
			return true;
		}
		else return false;
	}
	
	public int istFertig(){
		int white = getWhite();
		int black = getBlack();
		if(white==0){
			return 1;
		}
		else if(black==0){
			return 2;
		}
		else if(black+white==64){
			if(white==32){
				return 0;
			}
			else if(white<black){
				return 1;
			}
			else return 2;
		}
		else{
			return -1;
		}
	}
	
	public int getWhite() {
		int white = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j]==2){
					white++;
				}
			}
		}
		return white;
	}
	
	public int getBlack() {
		int black = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j]==1){
					black++;
				}
			}
		}
		return black;
	}
	
	public int[][] getBoard() {return board;}
	
}
