/**
 * An abstract class for pentago AI agents
 */
public abstract class Agent {
	public abstract Move getMove(Move[] moves, Square[][] board);
	abstract int evaluation(Square[][] board);
}
