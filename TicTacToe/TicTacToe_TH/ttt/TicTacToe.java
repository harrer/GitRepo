package ttt;
public class TicTacToe extends basicpackage.BasicBoard implements Game{
	
	
	public TicTacToe(int size){
		super.board = new int[size][size];
	}
	
	public String gameStatus() {
		int i = fertig();
		if(i==-1){
			return "Noch nicht fertig";
		}
		else if(i==0){
			return "Unentschieden";
		}
		else if(i==1){
			return "Spieler Kreuz (x) gewinnt";
		}
		else{
			return "Spieler Kreis (o) gewinnt";
		}
	}
	
	private char letzterSpieler = 'a';
	
	public int setze(char s, int xx, int yy){
		int x=xx-1 , y=yy-1, spieler;
		if(s=='x'){
			spieler = 1;
		}
		else if(s=='o'){
			spieler = 2;
		}
		else{
			System.out.println("Spieler "+s+" nicht vorhanden, entweder 'x' oder 'o'");
			return -1;
		}
		int[][] temp = getBoard();//Durch Seiteneffekte übertragen sich Änderungen in temp auf BasicBoard.Board
		if(x<0||y<0||x>=temp.length||y>=temp.length){//Falls auf eine nicht vorhandene Zelle zugegriffen wird
			System.out.println("Zelle nicht vorhanden");
			return -1;
		}
		else if(s==letzterSpieler){//Falls ein Spieler zweimal hintereinander zu spielen versucht
			if(spieler==1){System.out.println("Spieler Kreis (o) ist an der Reihe");}
			else{System.out.println("Spieler Kreuz (x) ist an der Reihe");}
			return -1;
		}
		else if(temp[x][y]!=0){//Falls die Zelle belegt ist
			System.out.println("Zelle "+xx+","+yy+" ist belegt");
			return temp[x][y];
		}
		else{//Zelle ist frei
			if(spieler==1){
				temp[x][y] = 1;
				letzterSpieler = s;
				System.out.println("Spieler "+s+" hat auf Zelle "+xx+","+yy+" gesetzt");
				System.out.println(print());
				System.out.println("Spieler Kreis (o) ist an der Reihe");
			}
			else{
				temp[x][y] = 2;
				letzterSpieler = s;
				System.out.println("Spieler "+s+" hat auf Zelle "+xx+","+yy+" gesetzt");
				System.out.println(print());
				System.out.println("Spieler Kreuz (x) ist an der Reihe");
			}
			return 0;
		}
	}
	
	public int fertig(){//-1 == noch nicht fertig | 0 == unentschieden | 1/2 Spieler x/o hat gewonnen
		int[][] b = getBoard();
		int Zx=0, Zo=0;//Zählen die Kreuze(x)/Kreise(o) in den Zeilen bzw. Spalten
		for (int i = 0; i < b.length; i++) {//horizontal
			if(Zx==board.length||Zo==board.length){
				break;
			}
			else{
				Zx=0; Zo=0;
			}
			for (int j = 0; j < b.length; j++) {
				if(b[i][j]==0){
					Zx=0; Zo=0;
					break;
				}
				if(j==0){//Falls in einer Zelle der ersten Spalte
					if(b[i][j]==1){
						Zx++;
					}
					else{
						Zo++;
					}
				}
				else{
					if(b[i][j]==1){
						if(b[i][j-1]==1){
							Zx++;
						}
						else{
							Zx=0;
						}
					}
					else if(b[i][j]==2){
						if(b[i][j-1]==2){
							Zo++;
						}
						else{
							Zo=0;
						}
					}
				}
			}
		}
		if(Zx==board.length){
			return 1;
		}
		if(Zo==board.length){
			return 2;
		}
		Zx=0; Zo=0;//setzt die Zähler wieder auf 0
		for (int i = 0; i < b.length; i++) {//vertikal
			if(Zx==board.length||Zo==board.length){
				break;
			}
			else{
				Zx=0; Zo=0;
			}
			for (int j = 0; j < b.length; j++) {
				if(b[j][i]==0){
					Zx=0; Zo=0;
					break;
				}
				if(j==0){//Falls in einer Zelle der ersten Zeile
					if(b[j][i]==1){
						Zx++;
					}
					else{
						Zo++;
					}
				}
				else{
					if(b[j][i]==1){
						if(b[j-1][i]==1){
							Zx++;
						}
						else{
							Zx=0;
						}
					}
					else if(b[j][i]==2){
						if(b[j-1][i]==2){
							Zo++;
						}
						else{
							Zo=0;
						}
					}
				}
			}
		}
		if(Zx==board.length){
			return 1;
		}
		if(Zo==board.length){
			return 2;
		}
		Zx=1; Zo=1;//setzt die Zähler wieder auf zurück, diesmal auf 1
		int diagonal = b[0][0];
		for (int i = 1; i < b.length; i++) {//diagonal l-r
			if(b[i][i]==0||diagonal!=b[i][i]){
				break;
			}
			else{
				diagonal = b[i][i];
				if(b[i][i]==1){
					Zx++;
				}
				else{
					Zo++;
				}
			}
		}
		if(Zx==board.length&&board.length!=1){
			return 1;
		}
		if(Zo==board.length&&board.length!=1){
			return 2;
		}
		Zx=1; Zo=1;//setzt die Zähler wieder auf zurück, diesmal auf 1
		diagonal = b[0][b.length-1];
		for (int i = 1; i < b.length; i++) {//diagonal r-l
			if(b[i][b.length-1-i]==0||diagonal!=b[i][b.length -i -1]){
				break;
			}
			else{
				diagonal = b[i][b.length-i-1];
				if(b[i][b.length-i-1]==1){
					Zx++;
				}
				else{
					Zo++;
				}
			}
		}
		if(Zx==board.length&&board.length!=1){
			return 1;
		}
		if(Zo==board.length&&board.length!=1){
			return 2;
		}
		boolean voll = true;
		for (int i = 0; i < b.length; i++) {
			if(!voll){
				break;
			}
			for (int j = 0; j < b.length; j++) {
				if(b[i][j]==0){//Falls
					voll = false; break;
				}
			}
		}
		if(voll){
			return 0;
		}
		return -1;
	}
	
	public String print(){
		String out = "";
		for (int i = 0; i < getBoard().length; i++) {
			for (int j = 0; j < getBoard().length; j++) {
				if(getBoard()[i][j]==0){
					out += "- ";
				}
				else if(getBoard()[i][j]==1){
					out += "x ";
				}
				else{
					out += "o ";
				}
			}
			out += "\n";
		}
		return out;
	}
}