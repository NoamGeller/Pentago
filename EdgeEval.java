
public class EdgeEval extends Evaluator {
	
	private static final int TIE = 4;

	@Override
	public int evaluation(Square[][] board, int color) {
		int result = 0;
		int[] counters = {0, 0, 0, 0, 0, 0, 0, 0};
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				// first outer columns, then outer rows
				Square currSquare = (i < 4) ? board[(i%2)*5][(j + 3*((i/2)%2))] : board[(j + 3*((i/2)%2))][(i%2)*5];
				int currColor = currSquare.getColor()*color;
				if (currColor*counters[i] >= 0)
				{
					counters[i] += currColor;
				}
				else
				{
					counters[i] = TIE;
					break;
				}
			}	
		}
		// iterate over possibly-adjacent sequences
		for (int i : new int[]{0,3,4,7})
		{
			for (int j : new int[]{1,2,5,6})
			{
				// points are given only for a single-colored sequence
				if (counters[i] != TIE && counters[j] != TIE && counters[i]*counters[j] >= 0)
				{
					int seqSize = counters[i] + counters[j];
					result += (int) Math.signum(seqSize)*Math.pow(10, Math.abs(seqSize-1));
				}
			}
		}
		return result;
	}

}
