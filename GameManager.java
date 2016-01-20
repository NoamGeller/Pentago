import java.util.Stack;
public class GameManager 
{
	Square balls[][],balls2[][];//מטריצה שמייצגת את מצב הגומות	
	int turn,playerTurn,cum[],nextCum[],maxLevel;
	boolean isPlay,isPut,isFinal,isRotate;
	Stack<Turn> ts;
	GraphicsManager gm;
	enum winCheck{none,both,first,second}
	
	public GameManager()
	{
		int ii,hh;
		this.gm=null;
		ts=new Stack<Turn>();
		nextCum=new int[2];	    
		nextCum[0]=0;
		nextCum[1]=0; 
		cum=new int[2];	 
		balls=new Square[6][6];
		balls2=new Square[6][6];	 
		for (hh=0;hh<balls.length;hh++)
		{
			for (ii=0;ii<balls[0].length;ii++)
			{
				balls[hh][ii]=new Square(0,false);
				balls2[hh][ii]=new Square(0,false);	
				
			}
		}
	}
	public void newGame()
	{
		int i,h;		
		turn=1;
		playerTurn=-1;			
		for(i=0;i<balls.length;i++)
		{
			for(h=0;h<balls.length;h++)
			{
				balls[i][h].setSide(0);	
				balls[i][h].setWin(false);	
			}
		}	
		isPut=false;		
		cum[0]=nextCum[0];
		cum[1]=nextCum[1];
		maxLevel=Math.max(cum[0], cum[1]);
		isPlay=true;
		isFinal=false;
		if (cum[0]!=0)
		{
			cumTurn(playerTurn);
		}
		isRotate=false;		
	}
	public void clocklWiseRotateCun()
	{
		Brain.clocklWiseRotate(balls, gm.numBoradH,  gm.numBoradW);		
		endTurn();		
	}
	public void counterClocklWiseRotateCun()
	{
		Brain.counterClocklWiseRotate(balls, gm.numBoradH, gm.numBoradW);		
		endTurn();			
	}
	private void endTurn() 
	{
		playerTurn=-1*playerTurn;
		turn++;
		if (turn>36)
		{
			isFinal=true;
			gm.startWinAnimution(true);	
		}
		gm.w=isWin();
		if (gm.w!=winCheck.none)
		{
			isFinal=true;
			if (gm.w!=winCheck.both)
			{
				gm.startWinAnimution(false);	
			}
			else
			{
				gm.startWinAnimution(true);	
			}
			
			
		}
		isPut=false;
		isRotate=false;	
		gm.repaint();
		if (!isFinal)
		{
			if (checkIfSometingWait())	
			{
				return;
			}	
			if (playerTurn<0)
			{
				if (cum[0]!=0)
				{
					
					cumTurn(playerTurn);
				}
				else
				{
					if (gm.isWaitToChangeP)
					{
						gm.isWaitToChangeP=false;
						gm.changePerspectiv();
					}
					if (gm.isWaitToNewGame)
					{
						gm.isWaitToNewGame=false;
						gm.newGame();
					}
					gm.isInTheard=false;						
				}
			}
			else
			{
				if (cum[1]!=0)
				{
					
					cumTurn(playerTurn);
				}	
				else
				{
					if (gm.isWaitToChangeP)
					{
						gm.isWaitToChangeP=false;
						gm.changePerspectiv();
					}
					if (gm.isWaitToNewGame)
					{
						gm.isWaitToNewGame=false;
						gm.newGame();
					}
					gm.isInTheard=false;
				}
			}		
		}
		else
		{
			if (checkIfSometingWait())	
			{
				return;
			}	
		}
	}	
	public void put()
	{
		put(gm.numSqureH,gm.numSqureW);
	}
	public void put(int ii,int hh)
	{
		balls[ii][hh].setSide(playerTurn);
		isPut=true;	
		gm.repaint();
	}
	public void cumTurn(int playerTurn)
	{
		gm.isInTheard=true;
		int level;			
		if (playerTurn>0)
		{
			level=cum[1];
		}
		else
		{
			level=cum[0];
		}		
		Turn a;	
		ts.clear();		
		copyArray();
		if (turn<5)
		{
			a=Brain.firstTwoTurn(playerTurn,balls2,maxLevel);
		}
		else
		{
			a=Brain.calcTurns(playerTurn, Math.min(level, 37-turn),balls2,maxLevel);	
		}		
		put(a.getPutH(),a.getPutW());
		gm.numBoradH=a.getSivovH();
		gm.numBoradW=a.getSivovW();
		if (!a.getPlusOrMinus())
		{
			clocklWiseRotate();
		}
		else
		{
			counterClocklWiseRotate();
		}			
	}
	
	private winCheck isWin() 
	{
		
		//פונקציה שבודקת אם מישהו ניצח
		int i,h,p;
		boolean isW,isH,isW2,isH2,d1,d2,d3,d4,d5,d6,d7,d8,p1,p2;
		winCheck w;
		p1 = false;
		p2=false;
		for (p=-1;p<2;p=p+2)
		{
			d1=true;
			d2=true;
			d3=true;
			d4=true;
			d5=true;
			d6=true;
			d7=true;
			d8=true;
			for (i=0;i<6;i++)
			{
				isW=true;
				isH=true;
				isW2=true;
				isH2=true;
				for (h=0;h<5;h++)
				{
					if (!balls[i][h].isBelongToPlayer(p))
					{
						isW=false;
					}
					if (!balls[h][i].isBelongToPlayer(p))
					{
						isH=false;
					}
					if (!balls[i][5-h].isBelongToPlayer(p))
					{
						isW2=false;
					}
					if (!balls[5-h][i].isBelongToPlayer(p))
					{
						isH2=false;
					}
					
					
				}
				if (isH)
				{
					balls[0][i].setWin(true);
					balls[1][i].setWin(true);
					balls[2][i].setWin(true);
					balls[3][i].setWin(true);
					balls[4][i].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}					
				}
				if (isW)
				{
					balls[i][0].setWin(true);
					balls[i][1].setWin(true);
					balls[i][2].setWin(true);
					balls[i][3].setWin(true);
					balls[i][4].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}	
				}
				if (isH2)
				{
					balls[5][i].setWin(true);
					balls[1][i].setWin(true);
					balls[2][i].setWin(true);
					balls[3][i].setWin(true);
					balls[4][i].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}	
				}
				if (isW2)
				{
					balls[i][5].setWin(true);
					balls[i][1].setWin(true);
					balls[i][2].setWin(true);
					balls[i][3].setWin(true);
					balls[i][4].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}	
				}
				
				if (i<5)
				{
					if (!balls[i][i].isBelongToPlayer(p))
					{
						d1=false;
					}
					if (!balls[5-i][5-i].isBelongToPlayer(p))
					{
						d2=false;
					}
					if (!balls[i][5-i].isBelongToPlayer(p))
					{
						d3=false;
					}
					if (!balls[5-i][i].isBelongToPlayer(p))
					{
						d4=false;
					}
					if (!balls[1+i][i].isBelongToPlayer(p))
					{
						d5=false;
					}
					if (!balls[1+i][5-i].isBelongToPlayer(p))
					{
						d6=false;
					}
					if (!balls[i][4-i].isBelongToPlayer(p))
					{
						d7=false;
					}
					if (!balls[i][1+i].isBelongToPlayer(p))
					{
						d8=false;
					}
				}
				
				
			}
			if (d1)
			{
				balls[0][0].setWin(true);
				balls[1][1].setWin(true);
				balls[2][2].setWin(true);
				balls[3][3].setWin(true);
				balls[4][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
			}
			if (d2)
			{
				balls[5][5].setWin(true);
				balls[1][1].setWin(true);
				balls[2][2].setWin(true);
				balls[3][3].setWin(true);
				balls[4][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
			}
			if (d3)
			{
				balls[0][5].setWin(true);
				balls[1][4].setWin(true);
				balls[2][3].setWin(true);
				balls[3][2].setWin(true);
				balls[4][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}			
			}
			if (d4)
			{
				balls[5][0].setWin(true);
				balls[1][4].setWin(true);
				balls[2][3].setWin(true);
				balls[3][2].setWin(true);
				balls[4][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
				
			}
			if (d5)
			{
				balls[1][0].setWin(true);
				balls[2][1].setWin(true);
				balls[3][2].setWin(true);
				balls[4][3].setWin(true);
				balls[5][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}				
			}
			if (d6)
			{
				balls[1][5].setWin(true);
				balls[2][4].setWin(true);
				balls[3][3].setWin(true);
				balls[4][2].setWin(true);
				balls[5][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}		
			}
			if (d7)
			{
				balls[0][4].setWin(true);
				balls[1][3].setWin(true);
				balls[2][2].setWin(true);
				balls[3][1].setWin(true);
				balls[4][0].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}				
			}
			if (d8)
			{
				balls[0][1].setWin(true);
				balls[1][2].setWin(true);
				balls[2][3].setWin(true);
				balls[3][4].setWin(true);
				balls[4][5].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}		
			}
		}
		if (p1)
		{
			if (p2)
			{
				w=winCheck.both;
			}
			else
			{
				w=winCheck.first;
			}
		}
		else
		{
			if (p2)
			{
				w=winCheck.second;
			}
			else
			{
				w=winCheck.none;
			}
		}
		return w;
	}
	
	
	public void copyArray()
	{
		int ii,hh;
		for (ii=0;ii<balls2.length;ii++)
		{
			for(hh=0;hh<balls2[0].length;hh++)
			{
				balls2[ii][hh].setSide(balls[ii][hh].getSide());
				balls2[ii][hh].setWin(balls[ii][hh].isWin());
			}
		}		
	}
	public void clocklWiseRotate()
	{
		gm.leftOrRight=false;	
		isRotate=true;
		gm.rotateBoard();		
	}
	public void counterClocklWiseRotate()
	{
		
		gm.leftOrRight=true;
		isRotate=true;		
		gm.rotateBoard();		
	}
	public void setGm(GraphicsManager gm) 
	{
		this.gm = gm;
	}
	public boolean checkIfSometingWait()
	{
		gm.isInTheard=false;
		if (gm.isWaitToChangeP)
		{
			gm.isWaitToChangeP=false;
			gm.changePerspectiv();
		}
		if (gm.isWaitToNewGame)
		{
			gm.isWaitToNewGame=false;			
			gm.newGame();
			return true;
		}
		else
		{
			return false;
		}
	}
}
