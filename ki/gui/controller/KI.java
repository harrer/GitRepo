package gui.controller;

import java.awt.Point;
import java.util.Random;

public class KI {

	private Runner controller;
	private int[][] board;
	
	public KI(Runner r){
		controller = r;
		board = controller.model.getBoard();
	}
	
	public Point position(){
		Random r = new Random();
		int x = r.nextInt(board.length);
		int y = r.nextInt(board.length);
		if(board[x][y]==0){return new Point(x,y);}
		else{return position();}
		
	}
	
}
