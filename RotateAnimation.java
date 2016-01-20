/*
 * Created on Eiar 5767
 * @author levian
 * for Student
 */

public class RotateAnimation extends Thread
{
	GraphicsManager myPanel;
	int numBoradH,numBoradW;
	boolean leftOrRight;

	public RotateAnimation(GraphicsManager myPanel,int numBoradH,int numBoradW,boolean leftOrRight)
	{
		this.myPanel=myPanel;
		this.numBoradH=numBoradH;
		this.numBoradW=numBoradW;
		this.leftOrRight=leftOrRight;
		setDaemon(true);
		start();
	}
	

	public void run()
	{
		int i;	
		myPanel.isInTheard=true;
		for (i=0;i<10;i++)
		{
			if ((numBoradH==0)&&(numBoradW==0))
			{
				myPanel.sBorad[0][0].move(-myPanel.depth/40,-myPanel.depth/40,0);	
			}
			else if ((numBoradH==0)&&(numBoradW==1))
			{
				myPanel.sBorad[0][1].move(myPanel.depth/40,-myPanel.depth/40,0);	
			}
			else if ((numBoradH==1)&&(numBoradW==1))
			{
				myPanel.sBorad[1][1].move(myPanel.depth/40,myPanel.depth/40,0);	
			}
			else if ((numBoradH==1)&&(numBoradW==0))
			{
				myPanel.sBorad[1][0].move(-myPanel.depth/40,myPanel.depth/40,0);	
			}		
			myPanel.repaint();
			try
			{
				sleep(50);
			} catch(InterruptedException  e){}
		}
		for (i=0;i<10;i++)
		{
			if ((numBoradH==0)&&(numBoradW==0))
			{
				if (leftOrRight)
				{
					myPanel.a[0][0]=myPanel.a[0][0]-Math.PI/20;
				}
				else
				{
					myPanel.a[0][0]=myPanel.a[0][0]+Math.PI/20;
				}
			}
			else if ((numBoradH==0)&&(numBoradW==1))
			{
				if (leftOrRight)
				{
					myPanel.a[0][1]=myPanel.a[0][1]-Math.PI/20;
				}
				else
				{
					myPanel.a[0][1]=myPanel.a[0][1]+Math.PI/20;
				}
			}
			else if ((numBoradH==1)&&(numBoradW==1))
			{
				if (leftOrRight)
				{
					myPanel.a[1][1]=myPanel.a[1][1]-Math.PI/20;
				}
				else
				{
					myPanel.a[1][1]=myPanel.a[1][1]+Math.PI/20;
				}
			}
			else if ((numBoradH==1)&&(numBoradW==0))
			{
				if (myPanel.leftOrRight)
				{
					myPanel.a[1][0]=myPanel.a[1][0]-Math.PI/20;
				}
				else
				{
					myPanel.a[1][0]=myPanel.a[1][0]+Math.PI/20;
				}
			}		
			myPanel.repaint();
			try
			{
				sleep(50);
			} catch(InterruptedException  e){}
		}
		for (i=0;i<10;i++)
		{
			if ((numBoradH==0)&&(numBoradW==0))
			{
				myPanel.sBorad[0][0].move(myPanel.depth/40,myPanel.depth/40,0);	
			}
			else if ((numBoradH==0)&&(numBoradW==1))
			{
				myPanel.sBorad[0][1].move(-myPanel.depth/40,myPanel.depth/40,0);	
			}
			else if ((numBoradH==1)&&(numBoradW==1))
			{
				myPanel.sBorad[1][1].move(-myPanel.depth/40,-myPanel.depth/40,0);	
			}
			else if ((numBoradH==1)&&(numBoradW==0))
			{
				myPanel.sBorad[1][0].move(myPanel.depth/40,-myPanel.depth/40,0);	
			}		
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
		if (leftOrRight)
		{
			myPanel.gameManager.counterClocklWiseRotateCun();
		}
		else
		{
			myPanel.gameManager.clocklWiseRotateCun();
		}
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

