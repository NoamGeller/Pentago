
public class BrainAgent extends Agent {

	int color, depth, maxDepth;
	
	public BrainAgent(int color, int depth, int maxDepth)
	{
		this.color = color;
		this.depth = depth;
		this.maxDepth = maxDepth;
	}
	
	public int getColor()
	{
		return color;
	}
	
	public Move getMove(Square[][] board, int turnNum)
	{
		if (turnNum < 5)
		{
			return Brain.firstTwoMoves(color, board, maxDepth);
		}
		return Brain.calcMove(color, Math.min(depth, 37-turnNum), board, maxDepth);
	}

	@Override
	int evaluation(Square[][] board) {
		return 0;
	}

}
