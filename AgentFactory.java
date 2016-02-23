
public class AgentFactory {
	/** 
	 * @param name AlphaBeta or Brain, for now
	 * @param details depth (0 for human), color (-1 or 1), etc.
	 * @return The requested Agent, or null for human.
	 */
	public static Agent getAgent(String name, int[] details)
	{
		if (details[0] == 0)
		{
			return null;
		}
		if (name.equals("AlphaBeta"))
		{
			return new AlphaBetaAgent(details[0], details[1]);
		}
		if (name.equals("Brain"))
		{
			return new BrainAgent(details[1], details[0], details[2]);
		}
		return null;
	}
}