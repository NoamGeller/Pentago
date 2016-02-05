import java.util.Stack;
public class GameManager 
{
	Square board[][];//מטריצה שמייצגת את מצב הגומות	
	int turnNum, playerTurn, playerType[], nextType[], maxLevel;
	boolean isPlaying, isPlacing, isFinal, isRotating;
	Stack<Move> ts; // It doesn't seem to be used...
	GraphicsManager grm;
	enum Winner{NONE, BOTH, FIRST, SECOND}
	
	public GameManager()
	{
		this.grm = null;
		ts = new Stack<Move>();
		nextType = new int[2];	    
		nextType[0] = 0;
		nextType[1] = 0;
		playerType = new int[2];	 
		board = new Square[6][6];
		for (int i = 0; i < board.length; i++)
		{
			for (int j=0; j<board[0].length; j++)
			{
				board[i][j] = new Square(0, false);
			}
		}
	}
	
	public void setGrm(GraphicsManager grm) 
	{
		this.grm = grm;
	}
	
	public void newGame()
	{
		int i,h;		
		turnNum=1;
		playerTurn=-1;			
		for(i=0;i<board.length;i++)
		{
			for(h=0;h<board.length;h++)
			{
				board[i][h].setColor(0);	
				board[i][h].setWin(false);
			}
		}	
		isPlacing=false;		
		playerType[0]=nextType[0];
		playerType[1]=nextType[1];
		maxLevel=Math.max(playerType[0], playerType[1]);
		isPlaying=true;
		isFinal=false;
		if (playerType[0]!=0)
		{
			computerTurn(playerTurn);
		}
		isRotating=false;		
	}
	
	public void place()
	{
		place(grm.numSquareH,grm.numSquareW);
	}
	
	public void place(int ii,int hh)
	{
		board[ii][hh].setColor(playerTurn);
		isPlacing=true;	
		grm.repaint();
	}
	
	public void rotate(boolean isClockwise)
	{	
		isRotating=true;
		grm.rotateBoard(isClockwise);		
	}
	
	public void rotateCun(boolean isClockwise)
	{
		if (isClockwise)
		{
			Brain.clockWiseRotate(board, grm.numBoardH,  grm.numBoardW);
		}
		else
		{
			Brain.counterClockWiseRotate(board, grm.numBoardH, grm.numBoardW);
		}
		endTurn();
	}
	
	private void endTurn() 
	{
		playerTurn *= -1;
		grm.w = checkWinning();
		turnNum++;
		if (grm.w != Winner.NONE || turnNum > 36)
		{
			isFinal = true;
			grm.startWinAnimation(grm.w == Winner.BOTH);	
		}
		isPlacing = false;
		isRotating = false;	
		grm.repaint();
		grm.isInTheard = false;
		if (grm.isWaitToChangeP)
		{
			grm.isWaitToChangeP = false;
			grm.changePerspective();
		}
		int playerIndex = playerTurn < 0 ? 0 : 1;
		if (grm.isWaitToNewGame)
		{
			grm.isWaitToNewGame = false;
			grm.newGame();
		}
		else if (!isFinal && playerType[playerIndex] != 0)
		{
			computerTurn(playerTurn);
		}
	}
	
	public Square[][] copyBoard()
	{
		Square[][] boardCopy = new Square[6][6];
		for (int i = 0; i < boardCopy.length; i++)
		{
			for(int h = 0; h < boardCopy[0].length; h++)
			{
				boardCopy[i][h]= new Square(board[i][h].getColor(), board[i][h].isWin());
			}
		}
		return boardCopy;
	}
	
	public void computerTurn(int playerTurn)
	{
		grm.isInTheard=true;
		int level = playerTurn > 0 ? playerType[1] : playerType[0];			
		Move move;
		ts.clear();		
		Square[][] boardCopy = copyBoard();
		if (turnNum<5)
		{
			move=Brain.firstTwoMoves(playerTurn,boardCopy,maxLevel);
		}
		else
		{
			move=Brain.calcMove(playerTurn, Math.min(level, 37-turnNum),boardCopy,maxLevel);	
		}
		place(move.getStoneH(),move.getStoneW());
		grm.numBoardH=move.getRotationH();
		grm.numBoardW=move.getRotationW();
		rotate(!move.isClockwise());
	}
	
	private Winner checkWinning()
	{
		//פונקציה שבודקת אם מישהו ניצח
		boolean wFlag,hFlag,wFlag2,hFlag2,p1,p2;
		Winner w;
		p1 = false;
		p2 = false;
		for (int p=-1;p<2;p=p+2)
		{
			boolean[] diagonalFlag = {true, true, true, true, true, true, true, true};
			for (int i=0;i<6;i++)
			{
				wFlag = true;
				hFlag = true;
				wFlag2 = true;
				hFlag2 = true;
				for (int j = 0; j < 5; j++)
				{
					if (!board[i][j].belongsToPlayer(p))
					{
						wFlag=false;
					}
					if (!board[j][i].belongsToPlayer(p))
					{
						hFlag=false;
					}
					if (!board[i][5-j].belongsToPlayer(p))
					{
						wFlag2=false;
					}
					if (!board[5-j][i].belongsToPlayer(p))
					{
						hFlag2=false;
					}
				}
				if (hFlag)
				{
					board[0][i].setWin(true);
					board[1][i].setWin(true);
					board[2][i].setWin(true);
					board[3][i].setWin(true);
					board[4][i].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}					
				}
				if (wFlag)
				{
					board[i][0].setWin(true);
					board[i][1].setWin(true);
					board[i][2].setWin(true);
					board[i][3].setWin(true);
					board[i][4].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}	
				}
				if (hFlag2)
				{
					board[5][i].setWin(true);
					board[1][i].setWin(true);
					board[2][i].setWin(true);
					board[3][i].setWin(true);
					board[4][i].setWin(true);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}	
				}
				if (wFlag2)
				{
					board[i][5].setWin(true);
					board[i][1].setWin(true);
					board[i][2].setWin(true);
					board[i][3].setWin(true);
					board[i][4].setWin(true);
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
					if (!board[i][i].belongsToPlayer(p))
					{
						diagonalFlag[0]=false;
					}
					if (!board[5-i][5-i].belongsToPlayer(p))
					{
						diagonalFlag[1]=false;
					}
					if (!board[i][5-i].belongsToPlayer(p))
					{
						diagonalFlag[2]=false;
					}
					if (!board[5-i][i].belongsToPlayer(p))
					{
						diagonalFlag[3]=false;
					}
					if (!board[1+i][i].belongsToPlayer(p))
					{
						diagonalFlag[4]=false;
					}
					if (!board[1+i][5-i].belongsToPlayer(p))
					{
						diagonalFlag[5]=false;
					}
					if (!board[i][4-i].belongsToPlayer(p))
					{
						diagonalFlag[6]=false;
					}
					if (!board[i][1+i].belongsToPlayer(p))
					{
						diagonalFlag[7]=false;
					}
				}
			}
			if (diagonalFlag[0])
			{
				board[0][0].setWin(true);
				board[1][1].setWin(true);
				board[2][2].setWin(true);
				board[3][3].setWin(true);
				board[4][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
			}
			if (diagonalFlag[1])
			{
				board[5][5].setWin(true);
				board[1][1].setWin(true);
				board[2][2].setWin(true);
				board[3][3].setWin(true);
				board[4][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
			}
			if (diagonalFlag[2])
			{
				board[0][5].setWin(true);
				board[1][4].setWin(true);
				board[2][3].setWin(true);
				board[3][2].setWin(true);
				board[4][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}			
			}
			if (diagonalFlag[3])
			{
				board[5][0].setWin(true);
				board[1][4].setWin(true);
				board[2][3].setWin(true);
				board[3][2].setWin(true);
				board[4][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}	
				
			}
			if (diagonalFlag[4])
			{
				board[1][0].setWin(true);
				board[2][1].setWin(true);
				board[3][2].setWin(true);
				board[4][3].setWin(true);
				board[5][4].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}				
			}
			if (diagonalFlag[5])
			{
				board[1][5].setWin(true);
				board[2][4].setWin(true);
				board[3][3].setWin(true);
				board[4][2].setWin(true);
				board[5][1].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}		
			}
			if (diagonalFlag[6])
			{
				board[0][4].setWin(true);
				board[1][3].setWin(true);
				board[2][2].setWin(true);
				board[3][1].setWin(true);
				board[4][0].setWin(true);				
				if (p>0)
				{
					p2=true;
				}
				else
				{
					p1=true;
				}				
			}
			if (diagonalFlag[7])
			{
				board[0][1].setWin(true);
				board[1][2].setWin(true);
				board[2][3].setWin(true);
				board[3][4].setWin(true);
				board[4][5].setWin(true);				
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
				w=Winner.BOTH;
			}
			else
			{
				w=Winner.FIRST;
			}
		}
		else
		{
			if (p2)
			{
				w=Winner.SECOND;
			}
			else
			{
				w=Winner.NONE;
			}
		}
		return w;
	}
}
