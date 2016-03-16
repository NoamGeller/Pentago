
public class BasicEval extends Evaluator {
	
	int evaluateSeq(Square square, int color, int[] counters, int index)
	{
		int currColor = square.getColor() * color;
		int prevColor = (int) Math.signum(counters[index]);
		if (currColor == prevColor)
		{
			counters[index] += currColor;
			return 0;
		}
		int prevSeq = Math.abs(counters[index]);
		counters[index] = currColor;
		//if (counters[index] > 2)
		{
			return prevColor*(int)Math.pow(10, prevSeq-1);
		}
	}
	
	@Override
	public int evaluation(Square[][] board, int color) {
		int result = 0;
		int[] diagonalCounters = {0, 0, 0, 0, 0, 0};
		for (int i = 0; i < board.length; i++)
		{
			
			if (i < 6)
			{
				result += evaluateSeq(board[i][i], color, diagonalCounters, 0);
				result += evaluateSeq(board[5-i][i], color, diagonalCounters, 1);
			}
			if (i < 5)
			{
				result += evaluateSeq(board[1+i][i], color, diagonalCounters, 2);
				result += evaluateSeq(board[1+i][5-i], color, diagonalCounters, 3);
				result += evaluateSeq(board[i][4-i], color, diagonalCounters, 4);
				result += evaluateSeq(board[i][1+i], color, diagonalCounters, 5);
			}
			
			int[] straightCounters = {0, 0};
			for (int j = 0; j < board[i].length; j++)
			{
				result += evaluateSeq(board[i][j], color, straightCounters, 0);
				result += evaluateSeq(board[j][i], color, straightCounters, 1);
			}
		}
		for (int i = 0; i < 4; i++)
		{
			int x = i < 2 ? 1 : 4;
			int y = i % 2 == 0 ? 1 : 4;
			result += 5*board[x][y].getColor();
		}
		return result;
	}

}
