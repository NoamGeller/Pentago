import java.util.List;


public class AlphaBetaAgent extends Agent {
	
	private int initialDepth;
	private int color;

	public AlphaBetaAgent(int initialDepth, int color) {
		this.initialDepth = initialDepth;
		this.color = color;
	}
	
	private Move alphaBetaPruning(Square[][] board, int depth, int alpha, int beta)
	{
		Move bestMove = null;
		if (depth == 0 || Brain.isWin(board, 1) || Brain.isWin(board, -1))
		{
			bestMove = new Move();
			bestMove.setGrade(evaluation(board));
		}
		else
		{
			List<Move> moves = Brain.getMoves(board);
			int currentColor = this.color * (depth % 2 == 0 ? 1 : -1);
			for (Move move : moves)
			{
				Brain.applyMove(move, board, currentColor); // TODO reconsider checking moves not in-place
				int value = -alphaBetaPruning(board, depth - 1, -beta, -alpha).getGrade();
				Brain.unapplyMove(move, board);
				if (bestMove == null || value > bestMove.getGrade())
				{
					bestMove = move;
					bestMove.setGrade(value);
				}
				if (bestMove.getGrade() >= beta)
				{
					// TODO test whether this prunes right
					bestMove.setGrade(beta);
					break;
				}
				if (value > alpha)
				{
					alpha = bestMove.getGrade();
				}
			}
		}
		return bestMove;
	}

	@Override
	public Move getMove(Square[][] board, int turnNum) 
	{
		return alphaBetaPruning(board, Math.min(initialDepth, 36 - turnNum), Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	@Override
	int evaluation(Square[][] board) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
