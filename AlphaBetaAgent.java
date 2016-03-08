import java.util.List;


public class AlphaBetaAgent extends Agent {
	
	private int initialDepth;
	private int color;
	private Evaluator evaluator;

	public AlphaBetaAgent(int initialDepth, int color, Evaluator evaluator) {
		this.initialDepth = initialDepth;
		this.color = color;
		this.evaluator = evaluator;
	}
	
	private Move alphaBetaPruning(Square[][] board, int depth, int alpha, int beta)
	{
		Move bestMove = new Move();
		int currentColor = this.color * (depth % 2 == 0 ? 1 : -1);
		boolean thisWin = Brain.isWin(board, currentColor);
		boolean otherWin = Brain.isWin(board, -currentColor);
		if (otherWin && !thisWin)
		{
			bestMove.setGrade(Integer.MIN_VALUE+1);
		}
		else if (!otherWin && thisWin)
		{
			bestMove.setGrade(Integer.MAX_VALUE);
		}
		else if (depth == 0 || (otherWin && thisWin))
		{
			bestMove.setGrade(evaluator.evaluation(board, currentColor));
		}
		else
		{
			bestMove = null;
			List<Move> moves = Brain.getMoves(board);
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
		return alphaBetaPruning(board, Math.min(initialDepth, 36 - turnNum), Integer.MIN_VALUE+1, Integer.MAX_VALUE);
	}

	@Override
	public int getColor() 
	{
		return color;
	}

}
