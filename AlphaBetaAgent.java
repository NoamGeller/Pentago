
public class AlphaBetaAgent extends Agent {
	
	private int maxDepth;

	public AlphaBetaAgent(int maxDepth) {
		this.maxDepth = maxDepth;
	}
	
	private Move alphaBetaPruning(Square[][] board, int depth, double alpha, double beta)
	{
		return null;
	}

	@Override
	public Move getMove(Square[][] board) {
		return alphaBetaPruning(board, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	@Override
	int evaluation(Square[][] board) {
		// TODO Auto-generated method stub
		return 0;
	}

}
