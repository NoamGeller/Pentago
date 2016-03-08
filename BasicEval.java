
public class BasicEval extends Evaluator {
	
	@Override
	public int evaluation(Square[][] board, int color) {
		int result = 0;
		for (int i = 0; i < board.length; i++)
		{
			int[] stoneCounters = {0, 0, 0};
			for (int j = 0; j < board[i].length; j++)
			{
				int currColor = board[i][j].getColor() * color;
				int prevColor = (int) Math.signum(stoneCounters[0]);
				if (currColor == prevColor)
				{
					stoneCounters[0] += currColor;
				}
				else
				{
					result += prevColor*(int)Math.pow(10, stoneCounters[0]-1);
					stoneCounters[0] = currColor;
				}
				currColor = board[j][i].getColor() * color;
				prevColor = (int) Math.signum(stoneCounters[1]);
				if (currColor == prevColor)
				{
					stoneCounters[1] += currColor;
				}
				else
				{
					result += prevColor*(int)Math.pow(10, stoneCounters[1]-1);
					stoneCounters[1] = currColor;
				}
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
