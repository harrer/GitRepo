package szte.mi;

public class KI2 extends KIGreedy{

	private Board board;
	private int[][] bewertung;
	
	public KI2(Board board){
		super(board);
		this.board = board;
		bewertung = new int[8][8];
		int[] zeile= {3,1,1,1,1,1,1,3};
		for (int i=0;i<8;i++) {
			bewertung[i] = zeile;
		}
//		bewertung[0][0] = 5;
//		bewertung[0][7] = 5;
//		bewertung[7][0] = 5;
//		bewertung[7][7] = 5;
		String out = "";
		for (int i = 0; i < zeile.length; i++) {
			for (int j = 0; j < zeile.length; j++) {
				out+=bewertung[i][j]+" ";
			}
			out+="\n";
		}
		System.out.println(out);
	}
	
	public static void main(String[] args) {
		new KI2(null);
	}
}
