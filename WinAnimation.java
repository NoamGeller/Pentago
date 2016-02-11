import java.awt.Color;

/**
 * Moves the starting point of the winning animation in an alloted amount of time.
 * @author Noam Wies
 *
 */
public class WinAnimation extends Thread 
{
	GraphicsManager myPanel;
	int ySpeed,xSpeed,count;
	boolean isRun;
	private final Chain<Color> colors=new Chain<Color>();
	private int width=180;
	private static final int HEIGHT=50;
	/**
	 * Builds a new process to move the top corner of the winning animation.
	 * @param gm board of the game that the process will work on
	 */
	public WinAnimation(GraphicsManager gm,boolean isTie)
	{
		myPanel=gm;	
		width = isTie ? 80 : 180;
		colors.insert(Color.CYAN);
		colors.insert(Color.blue);
		colors.insert(Color.blue);
		colors.insert(Color.gray);
		colors.insert(Color.white);	
		colors.insert(Color.yellow);
		colors.insert(Color.orange);
		colors.insert(Color.red);
		colors.insert(Color.pink);
	}
	/**
	 * The commands that will be carried out when the process runs.
	 */
	public void run()
	{
		isRun=true;
		ySpeed=3;
		xSpeed=10;
		count=0;
		myPanel.xtext=myPanel.getX();
		myPanel.ytext=myPanel.getY()+myPanel.getHeight()/3;
		while (isRun)
		{
			count++;
			myPanel.xtext+=xSpeed;
			myPanel.ytext+=ySpeed;
			if (count==12)
			{
				myPanel.c7=colors.getNow();
				colors.Next();
				count=1;
			}			
			if ((myPanel.xtext<0)||(myPanel.xtext+width>myPanel.getX()+myPanel.getWidth()))
			{
				xSpeed*=-1;
				myPanel.xtext+=2*xSpeed;
			}
			if ((myPanel.ytext<40)||(myPanel.ytext+HEIGHT>myPanel.getY()+myPanel.getHeight()))
			{
				ySpeed*=-1;
				myPanel.ytext+=2*ySpeed;
			}
			myPanel.repaint();
			try
			{
				sleep(40);
			} catch(InterruptedException  e){}
		}	
	}
	/**
	 * Stops the process in the recommended way.
	 */
	public void stopMe()
	{
		isRun=false;
	}
}