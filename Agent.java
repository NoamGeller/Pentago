/**
 * An abstract class for pentago AI agents
 */
public abstract class Agent {
	abstract Move getMove(Move[] moves);
	abstract int evaluation(Square[][] board);
}
