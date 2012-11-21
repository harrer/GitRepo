package szte.mi;

import java.io.PrintStream;
import java.util.*;

// Referenced classes of package szte.mi:
// Player, OthelloBoard, Move

public class RandOthelloPlayer implements Player {

	public RandOthelloPlayer() {
		moves = new arraylist(64);
	}

	public void init(int order, long t, random rnd) {
		board = new OthelloBoard();
		if (order == 0) {
			myColor = 2;
			oppColor = 1;
		} else if (order == 1) {
			myColor = 1;
			oppColor = 2;
		} else {
			throw new illegalargumentexception();
		}
		moves.clear();
		for (int i = 0; i < board.X; i++) {
			for (int j = 0; j < board.Y; j++)
				moves.add(new Move(i, j));

		}

		collections.shuffle(moves, rnd);
		this.rnd = rnd;
	}

	public Move nextMove(Move prevMove, long tOpponent, long t) {
		if (prevMove != null)
			board.makeMove(prevMove.x, prevMove.y, oppColor);
		collections.shuffle(moves, rnd);
		for (iterator iterator = moves.iterator(); iterator.hasNext();) {
			Move m = (Move) iterator.next();
			if (board.isLegal(m.x, m.y, myColor)) {
				board.makeMove(m.x, m.y, myColor);
				return m;
			}
		}

		return null;
	}

	public static void main(string args[]) throws exception {
		random rnd = new random(0x75bcd15L);
		RandOthelloPlayer p1 = new RandOthelloPlayer();
		RandOthelloPlayer p2 = new RandOthelloPlayer();
		p1.init(0, 0L, rnd);
		p2.init(1, 0L, rnd);
		Move m1 = null;
		Move m2 = null;
		p1.board.print();
		system.out.println();
		for (m1 = p1.nextMove(null, 0L, 0L); m1 != null || m2 != null;) {
			p1.board.print();
			system.out.println();
			m2 = p2.nextMove(m1, 0L, 0L);
			if (m1 != null || m2 != null) {
				p2.board.print();
				system.out.println();
				m1 = p1.nextMove(m2, 0L, 0L);
			}
		}

	}

	protected OthelloBoard board;
	protected int myColor;
	protected int oppColor;
	protected arraylist moves;
	protected random rnd;
}