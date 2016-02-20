/**
 * An abstract class for pentago AI agents
 */
public abstract class Agent {
	public abstract Move getMove(Square[][] board, int turnNum);
	abstract int evaluation(Square[][] board);
}
