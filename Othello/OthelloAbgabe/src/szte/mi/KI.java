package szte.mi;

import java.util.ArrayList;

import szte.mi.Move;

public class KI {
	
	private Board board;
	private final int computer = 2;
	
	public KI(Board board) {
		this.board = board;
	}

	public Entry[][] möglicheZüge(){
		Entry[][] checkBoard = new Entry[Board.size][Board.size];
		for (int i = 0; i < checkBoard.length; i++) {
			for (int j = 0; j < checkBoard.length; j++) {
				ArrayList<Move> list = new ArrayList<Move>();
				if(board.getBoard()[i][j]==0){
					list = board.korrekterZug(new Move(j, i), 2);
				}
				checkBoard[i][j] = new Entry(list);
			}
		}
		return checkBoard;
	}
	
	public Move findeBestenZug(Entry[][] züge){
		int count = 0, max=0;
		Move result = null;
		for (int i = 0; i < züge.length; i++) {
			for (int j = 0; j < züge.length; j++) {
				if(züge[i][j].getList().size()>0){
					for (int j2 = 0; j2 < züge[i][j].getList().size(); j2++) {
						Move next = new Move(züge[i][j].getList().get(j2).x+j, züge[i][j].getList().get(j2).y+i);
						while(!(next.x<0||next.y<0||next.x>=Board.size||next.y>=Board.size)&&board.getBoard()[next.y][next.x]!=computer){
							count++;
							next = new Move(next.x+züge[i][j].getList().get(j2).x, next.y+züge[i][j].getList().get(j2).y);
						}
						if(count>max){
							max = count;
							result = new Move(j, i);
						}
						count = 0;
					}
				}
			}
		}
		return result;
	}
	
	class Entry{
		private ArrayList<Move> list;
		public int umgedrehteFelder;
		public Move richtung;
		public Entry(ArrayList<Move> list) {
			this.list = list;
		}
		public ArrayList<Move> getList() {return list;}
	}
	
}
