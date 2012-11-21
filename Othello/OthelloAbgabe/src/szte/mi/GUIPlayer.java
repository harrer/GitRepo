package szte.mi;

import java.awt.*;
import java.io.PrintStream;
import java.util.Random;
import javax.swing.*;

// Referenced classes of package szte.mi:
// Player, OthelloBoardpp, Move, OthelloGame

public class GUIPlayer implements Player {
	/* member class not found */
	class FieldLabel {
	}

	/* member class not found */
	class OthelloMouseListener {
	}

	public GUIPlayer() {
		monitor = new Object();
		userMove = null;
		initDone = false;
		listener = new OthelloMouseListener(null);
		fieldLabels = new FieldLabel[8][8];
	}

	public void init(int order, long t, Random rnd)
    {
    board = new OthelloBoardpp();
    if(order == 0)
    {
    myColor = 2;
    oppColor = 1;
    } else
    if(order == 1)
    {
    myColor = 1;
    oppColor = 2;
    } else
    {
    throw new illegalargumentException();
    }
    swingutilities.invokeLater(new Object() /* anonymous class not found */
    class _anm1 {}
     
    );
    }

	protected void createAndShowGUI() {
		synchronized (fieldLabels) {
			frame = new JFrame("Othello");
			frame.setDefaultCloseOperation(3);
			gridlayout boardLayout = new gridlayout(8, 8);
			boardLayout.setHgap(2);
			boardLayout.setVgap(2);
			frame.getContentPane().setLayout(boardLayout);
			frame.getContentPane().removeAll();
			for (byte i = 0; i < 8; i++) {
				for (byte j = 0; j < 8; j++) {
					FieldLabel fieldLabel = new FieldLabel(
							board.getState(j, i), j, i);
					fieldLabel.addMouseListener(listener);
					fieldLabels[j][i] = fieldLabel;
					frame.getContentPane().add(fieldLabel);
				}

			}

			frame.pack();
			frame.setSize(500, 500);
			frame.setVisible(true);
			initDone = true;
			fieldLabels.notifyAll();
		}
	}

	private void updateGUI() {
		for (byte i = 0; i < 8; i++) {
			for (byte j = 0; j < 8; j++)
				switch (board.getState(i, j)) {
				case 2: // '\002'
					fieldLabels[i][j].setForeground(BLACK_Color);
					break;

				case 1: // '\001'
					fieldLabels[i][j].setForeground(WHITE_Color);
					break;

				case 0: // '\0'
					fieldLabels[i][j].setForeground(BACKGROUND_Color);
					break;
				}

		}

		frame.repaint();
	}

	public Move nextMove(Move prevMove, long tOpponent, long t) {
		Move result = null;
		if (prevMove != null)
			board.makeMove((byte) prevMove.x, (byte) prevMove.y,
					(byte) oppColor);
		boolean hasLegalMoves = false;
		synchronized (fieldLabels) {
			while (!initDone)
				fieldLabels.wait();
			for (byte i = 0; i < 8; i++) {
				for (byte j = 0; j < 8; j++)
					if (board.isLegal(i, j, (byte) myColor)) {
						fieldLabels[i][j].setLegal(true);
						hasLegalMoves = true;
					} else {
						fieldLabels[i][j].setLegal(false);
					}

			}

		}
		if (prevMove != null)
			fieldLabels[prevMove.x][prevMove.y].isLastMove = true;
		updateGUI();
		if (hasLegalMoves)
			break MISSING_BLOCK_LABEL_209;
		try {
			thread.sleep(3000L);
		} catch (interruptedException e) {
			e.printStackTrace();
		}
		return null;
		try {
			synchronized (monitor) {
				while (userMove == null
						|| !board.isLegal((byte) userMove.x, (byte) userMove.y,
								(byte) myColor))
					try {
						monitor.wait();
					} catch (interruptedException e) {
						e.printStackTrace();
					}
				board.makeMove((byte) userMove.x, (byte) userMove.y,
						(byte) myColor);
				result = userMove;
				userMove = null;
			}
			for (byte i = 0; i < 8; i++) {
				for (byte j = 0; j < 8; j++)
					fieldLabels[i][j].setLegal(false);

			}

			if (prevMove != null)
				fieldLabels[prevMove.x][prevMove.y].isLastMove = false;
			updateGUI();
		} catch (throwable e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String args[])
    throws Exception
    {
    int NO_SELECTION = -1;
    Random rnd = new Random();
    String name = null;
    int selection = -1;
    for(int i = 0; i < args.length; i++)
    if(args[i].equals("-r"))
    {
    rnd.setSeed(long.parseLong(args[i + 1]));
    system.out.println((new StringBuilder("Seed wurde gesetzt auf: ")).append(args[i + 1]).toString());
    i++;
    } else
    if(args[i].equals("-o"))
    {
    name = args[i + 1];
    system.out.println((new StringBuilder("Lade: ")).append(name).toString());
    i++;
    } else
    if(args[i].equals("-s"))
    {
    selection = integer.parseInt(args[i + 1]);
    if((selection > 1) | (selection < 0))
    {
    system.out.println((new StringBuilder("Schwarzer Spieler ")).append(selection).append(" ung\374ltig. -s 0 oder -s 1 sind m\366glich.").toString());
    selection = -1;
    } else
    {
    system.out.println((new StringBuilder("Schwarzer Spieler: ")).append(selection != 0 ? "Mensch." : "Computer.").toString());
    }
    i++;
    } else
    {
    system.out.println("Aufruf ohne Parameter (interaktiv) oder folgende Optionen:");
    system.out.println("-r <long>\tSetze seed f\374r den Zufallsgenerator");
    system.out.println("-o <classname>\tKlassenname des Computergegners");
    system.out.println("-s <0|1>\t0: Computer f\344ngt an, 1: Mensch f\344ngt an");
    system.exit(0);
    }
     
    if(name == null)
    name = joptionpane.showInputDialog("Bitte den vollen Klassennamen der Player-Klasse eingeben:");
    if(name == null)
    system.exit(0);
    try
    {
    class.forName(name);
    }
    catch(classnotfoundException e)
    {
    joptionpane.showMessageDialog(null, (new StringBuilder("Konnte klasse ")).append(name).append(" nicht finden.").toString(), "Problem", 0);
    system.exit(0);
    }
    if(selection == -1)
    selection = joptionpane.showOptionDialog(null, "Wer soll anfangen? (Schwarzer Spieler)", "Schwarz w\344hlen", -1, 3, null, new String[] {
    name, "Mensch"
    }, null);
    Player player_black = null;
    String name_black = "";
    Player player_white = null;
    String name_white = "";
    switch(selection)
    {
    case 0: // '\0'
    player_black = (Player)class.forName(name).newInstance();
    name_black = (new StringBuilder("Computer (")).append(name).append(")").toString();
    player_white = new GUIPlayer();
    name_white = "Mensch";
    break;
     
    case 1: // '\001'
    player_white = (Player)class.forName(name).newInstance();
    name_white = (new StringBuilder("Computer (")).append(name).append(")").toString();
    player_black = new GUIPlayer();
    name_black = "Mensch";
    break;
     
    case -1:
    system.exit(0);
    break;
     
    default:
    system.err.println("invalid selection");
    system.exit(0);
    break;
    }
    OthelloGame og = new OthelloGame(0x7fffffffffffffffL, player_black, player_white, rnd);
    int res = og.playMatch();
    String winner = "undefined";
    switch(res)
    {
    case 0: // '\0'
    winner = name_black;
    break;
     
    case 2: // '\002'
    winner = name_white;
    break;
     
    case 1: // '\001'
    winner = "unentschieden";
    break;
    }
    system.out.println((new StringBuilder("Sieger: ")).append(winner).toString());
    joptionpane.showMessageDialog(null, (new StringBuilder("Sieger: ")).append(winner).toString());
    }

	protected OthelloBoardpp board;
	protected int myColor;
	protected int oppColor;
	private Object monitor;
	private Move userMove;
	boolean initDone;
	OthelloMouseListener listener;
	JFrame frame;
	private static Color BACKGROUND_Color;
	private static Color LEGAL_BACKGROUND_Color;
	private static Color BLACK_Color;
	private static Color WHITE_Color;
	private FieldLabel fieldLabels[][];

	static {
		BACKGROUND_Color = Color.GREEN;
		LEGAL_BACKGROUND_Color = Color.GRAY;
		BLACK_Color = Color.BLACK;
		WHITE_Color = Color.WHITE;
	}

}