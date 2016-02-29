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
		Move bestMove = new Move();
		boolean thisWin = Brain.isWin(board, color);
		boolean otherWin = Brain.isWin(board, -color);
		if (otherWin && !thisWin)
		{
			bestMove.setGrade(Integer.MIN_VALUE);
		}
		else if (!otherWin && thisWin)
		{
			bestMove.setGrade(Integer.MAX_VALUE);
		}
		else if (depth == 0 || (otherWin && thisWin))
		{
			bestMove.setGrade(evaluation(board));
		}
		else
		{
			bestMove = null;
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
		int result = 0;
		for (int i = 0; i < board.length; i++)
		{
			int[] stoneCounters = {0, 0, 0, 0, 0, 0};
			for (int j = 0; j < board[i].length; j++)
			{
				stoneCounters[0] += Math.max(0, color*board[i][j].getColor());
				stoneCounters[1] += Math.max(0, color*board[j][i].getColor());
				stoneCounters[3] += Math.max(0, -color*board[i][j].getColor());
				stoneCounters[4] += Math.max(0, -color*board[j][i].getColor());
			}
			result += Math.pow(10, stoneCounters[0]-1) + Math.pow(10, stoneCounters[1]-1);
			result -= Math.pow(10, stoneCounters[2]-1) + Math.pow(10, stoneCounters[3]-1);
		}
		for (int i = 0; i < 4; i++)
		{
			int x = i < 2 ? 1 : 4;
			int y = i % 2 == 0 ? 1 : 4;
			result += 5*board[x][y].getColor();
		}
		return result;
	}

	@Override
	public int getColor() 
	{
		return color;
	}

}
