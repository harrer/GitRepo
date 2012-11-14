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
			System.out.println("Bitte die Groesse des Feldes eingeben:");
			String input = konsole.nextLine();
			size = Integer.parseInt(input);
			if(size<1){
				System.out.println("Zahl groesser 0 fuer die Groesse des Feldes eingeben!");
				new Runner();
			}
			}
			catch (NumberFormatException ausnahme) {
			System.out.println("Falsche Eingabe. Boardgroesse wurde auf 3x3 gesetzt");
			}
			catch (IllegalArgumentException e) {
			System.out.print(e.getMessage());
			}
			t = new TicTacToe(size);
		System.out.println(t.toString());
		System.out.println("Zum vorzeitigen Beenden \"end\" +Enter eingeben");
		System.out.println(t.print());
		while(t.fertig()<0){
			try {
				System.out.println("Bitte Spieler [x/o] und Position [1..."+size+"] [1..."+size+"] mit Leerzeichen getrennt eingeben");
				String input = konsole.nextLine();
				if(input.equalsIgnoreCase("end")){System.out.println("Bis zum naechsten Mal!");System.exit(-1);}
				int x,y;
				String[] split = input.split(" ");
				t.setze(input.charAt(0), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
				System.out.println(t.gameStatus());
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Falsche Eingabe: [x/o] [1..."+size+"] [1..."+size+"]");
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
					System.out.println("Bis zum naechsten Mal! \n (c) T.H. 2012");
					System.exit(-1);
				}
				else{
					new Runner();
				}
			}
				catch (NumberFormatException ausnahme) {
				System.out.println("Falsche Eingabe.");
				}
	}	
}
