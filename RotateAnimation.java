/*
 * Created on Eiar 5767
 * @author levian
 * for Students
 */

public class RotateAnimation extends Thread
{
	GraphicsManager myPanel;
	int numBoardH,numBoardW, rotationCoeff;
	boolean isClockwise;

	public RotateAnimation(GraphicsManager myPanel,int numBoardH,int numBoardW,boolean isClockwise)
	{
		this.myPanel = myPanel;
		this.numBoardH = numBoardH;
		this.numBoardW = numBoardW;
		this.isClockwise = isClockwise;
		this.rotationCoeff = isClockwise ? 1 : -1;
		setDaemon(true);
		start();
	}

	public void run()
	{
		int i;	
		myPanel.isInTheard=true;
		int xAddition = myPanel.depth/40 * (numBoardW == 0 ? -1 : 1);
		int yAddition = myPanel.depth/40 * (numBoardH == 0 ? -1 : 1);
		for (i=0;i<10;i++)
		{
			myPanel.sBoard[numBoardH][numBoardW].move(xAddition, yAddition, 0);		
			myPanel.repaint();
			try
			{
				sleep(50);
			} catch(InterruptedException  e){}
		}
		for (i=0;i<10;i++)
		{
			myPanel.a[numBoardH][numBoardW] += rotationCoeff * Math.PI/20;
			myPanel.repaint();
			try
			{
				sleep(50);
			} catch(InterruptedException  e){}
		}
		for (i=0;i<10;i++)
		{
			myPanel.sBoard[numBoardH][numBoardW].move(-xAddition, -yAddition, 0);	
			myPanel.repaint();
			try
			{
				sleep(50);
			} catch(InterruptedException  e){}
		}
		myPanel.a[0][0]=0.0;
		myPanel.a[1][0]=0.0;
		myPanel.a[0][1]=0.0;
		myPanel.a[1][1]=0.0;
		myPanel.gameManager.rotateCun(isClockwise);
//		myPanel.isInTheard=false;
//		if (myPanel.isWaitToChangeP)
//		{
//			myPanel.isWaitToChangeP=false;
//			myPanel.changePerspectiv();
//		}
//		if (myPanel.isWaitToNewGame)
//		{
//			myPanel.isWaitToNewGame=false;
//			myPanel.newGame();
//		}
	}
	
}

