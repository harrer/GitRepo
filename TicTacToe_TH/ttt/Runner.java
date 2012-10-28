package ttt;

import java.util.Scanner;

public class Runner {
	
	public Runner(){
		main(null);
	}

	public static void main(String[] args) {
		TicTacToe t=null;
		int size=3;
		Scanner konsole = new Scanner(System.in);
			try {
				System.out.println("Bitte die Größe des Feldes eingeben:");
				String input = konsole.nextLine();
				size = Integer.parseInt(input);				}
				catch (NumberFormatException ausnahme) {
				System.out.println("Falsche Eingabe.");
				}
				catch (IllegalArgumentException e) {
				System.out.print(e.getMessage());
				}
			if(size>=99){System.out.println("Größe "+size+" des Boards in der Implementierung möglich (siehe TicTacToe.java), aber in der Shell nicht sinnvoll darstellbar"); new Runner();}	
		try {
			t = new TicTacToe(size);
		} catch (IllegalArgumentException e) {
			System.out.println("Enter board size as argument!");
			System.exit(-1);
		}
		System.out.println(t.toString());
		System.out.println(t.print());
		while(t.fertig()<0){
			try {
				System.out.println("Bitte Spieler [x/o] und Position [1..."+size+"] [1..."+size+"] mit Leerzeichen getrennt eingeben");
				String input = konsole.nextLine();
				int l,x,y;//l für die max. Länge der Eingabe, zB "x 13 14"
				if(size<10){
					l = 5; x = Integer.parseInt(input.substring(2,3));
					y = Integer.parseInt(input.substring(4));
				}
				else{
					try {
						x = Integer.parseInt(input.substring(2,4));
						y = Integer.parseInt(input.substring(5));
					} catch (NumberFormatException e) {
						x = Integer.parseInt(input.substring(2,3));
						y = Integer.parseInt(input.substring(4));
					}
				}
				
				t.setze(input.charAt(0), x, y);
				System.out.println(t.gameStatus());
			}
			catch (StringIndexOutOfBoundsException e) {
				System.out.println("Falsche Eingabe: [x/o] [1..."+size+"] [1..."+size+"]");
				}
			catch (NumberFormatException e) {
				System.out.println("Falsche Eingabe: [x/o] [1..."+size+"] [1..."+size+"]");
				}
		}
			try {
				System.out.println("Nochmal spielen? [_/n]");
				String input = konsole.nextLine();
				if(input.equalsIgnoreCase("n")){
					konsole.close();
				}
				else{
					new Runner();
				}
			}
				catch (NumberFormatException ausnahme) {
				System.out.println("Falsche Eingabe.");
				}
			konsole.close();
			System.out.println("(c) T.H. 2012");
	}	
}
