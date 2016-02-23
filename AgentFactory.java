
public class AgentFactory {
	public static Agent getAgent(String name, int color, int[] details)
	{
		if (name.equals("AlphaBeta"))
		{
			return new AlphaBetaAgent(details[0], color);
		}
		return new BrainAgent(color, details[0], details[1]);
	}
}