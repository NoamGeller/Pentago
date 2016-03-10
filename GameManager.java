
public class GameManager 
{
	Square board[][];//matrix that represents the state of the board
	int turnNum, playerIndex, nextType[];
	int[] currPlayers;
	Agent[] currPlayerAgents;
	boolean isPlaying, isPlacing, isFinal, isRotating;
	GraphicsManager grm;
	enum Winner{FIRST, BOTH, SECOND, NONE}
	
	public GameManager()
	{
		this.grm = null;
		nextType = new int[2];	    
		nextType[0] = 0;
		nextType[1] = 0;
		currPlayers = new int[2];
		currPlayers[0] = 0;
		currPlayers[1] = 0;
		currPlayerAgents = new Agent[2];
		currPlayerAgents[0] = null;
		currPlayerAgents[1] = null;
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
		turnNum = 1;
		playerIndex = 0;			
		for(int i = 0 ; i < board.length; i++)
		{
			for(int j = 0; j < board.length; j++)
			{
				board[i][j].setColor(0);
				board[i][j].setWin(false);
			}
		}
		isPlacing = false;
		int maxLevel = Math.max(nextType[0], nextType[1]);
		for (int i=0; i<2; i++)
		{
			System.out.print("Player " + (i+1) + " is ");
			if (currPlayers[i]==0) //human
			{
				System.out.println("Human");
				currPlayerAgents[i] = AgentFactory.getAgent("Human", new int[] {});
			}
			else if (currPlayers[i]==1) //alphaBeta
			{
				System.out.println("AlphaBeta (BasicEval) with depth " + nextType[i]);
				int playerNum = (i == 0) ? 1 : -1;
				currPlayerAgents[i] = AgentFactory.getAgent("AlphaBeta (BasicEval)", new int[] {nextType[i], playerNum, maxLevel});
			}
			else if (currPlayers[i]==2) //brain
			{
				System.out.println("Brain with depth " + nextType[i]);
				int player = (i == 0) ? 1 : -1;
				currPlayerAgents[i] = AgentFactory.getAgent("Brain", new int[] {nextType[i], player, maxLevel});
			}
		}

//		currPlayers[0] = AgentFactory.getAgent(currPlayers[0], new int[] {nextType[0], -1, maxLevel});
//		currPlayers[1] = AgentFactory.getAgent("Brain", new int[] {nextType[1], 1, maxLevel});
//		System.out.println(currPlayers[0]);
		isPlaying = true;
		isFinal = false;
		if (currPlayerAgents[0] != null)
		{
			System.out.print("turn " + turnNum + " ");
			computerTurn();
		}
		isRotating=false;	
	}
	
	public void place()
	{
		place(grm.numSquareH, grm.numSquareW);
	}
	
	public void place(int i ,int j)
	{
		board[i][j].setColor(playerIndex == 0 ? -1 : 1);
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
		playerIndex = turnNum % 2;
		grm.w = checkWinning();
		turnNum++;
		if (grm.w != Winner.NONE || turnNum > 36)
		{
			isFinal = true;
			System.out.println("winning color is " + (grm.w.ordinal()-1));
			grm.startWinAnimation(grm.w == Winner.BOTH);
		}
		isPlacing = false;
		isRotating = false;	
		grm.repaint();
		grm.isInTheard = false;
		if (grm.isWaitToNewGame)
		{
			grm.isWaitToNewGame = false;
			grm.newGame();
		}
		else if (!isFinal && currPlayerAgents[playerIndex] != null)
		{
			System.out.print("turn " + turnNum + " ");
			computerTurn();
		}
	}
	
	public Square[][] copyBoard()
	{
		Square[][] boardCopy = new Square[6][6];
		for (int i = 0; i < boardCopy.length; i++)
		{
			for(int j = 0; j < boardCopy[0].length; j++)
			{
				boardCopy[i][j]= new Square(board[i][j].getColor(), board[i][j].isWin());
			}
		}
		return boardCopy;
	}
	
	public void computerTurn()
	{
		grm.isInTheard = true;			
		Square[][] boardCopy = copyBoard();
		long startTime = System.nanoTime();
		Move move = currPlayerAgents[playerIndex].getMove(boardCopy, turnNum);
		long finishTime = System.nanoTime();
		System.out.println("took " + (finishTime - startTime) + " ns");
		place(move.getStoneH(),move.getStoneW());
		grm.numBoardH = move.getRotationH();
		grm.numBoardW = move.getRotationW();
		rotate(!move.isClockwise()); // TODO make the move.isClockwise to have the right direction
	}
	
	private Winner checkWinning()
	{
		//checks if someone won the game
		boolean wFlag,hFlag,wFlag2,hFlag2,p1,p2;
		Winner w;
		p1 = false;
		p2 = false;
		for (int p=-1;p<2;p=p+2)
		{
			boolean[] diagonalFlags = {true, true, true, true, true, true, true, true};
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
				if (hFlag || hFlag2)
				{
					for (int k = hFlag ? 0 : 1; k < 5 + (hFlag2 ? 1 : 0); board[k][i].setWin(true), k++);
					if (p>0)
					{
						p2=true;
					}
					else
					{
						p1=true;
					}					
				}
				if (wFlag || wFlag2)
				{
					for (int k = wFlag ? 0 : 1; k < 5 + (wFlag2 ? 1 : 0); board[i][k].setWin(true), k++);
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
						diagonalFlags[0]=false;
					}
					if (!board[5-i][5-i].belongsToPlayer(p))
					{
						diagonalFlags[1]=false;
					}
					if (!board[i][5-i].belongsToPlayer(p))
					{
						diagonalFlags[2]=false;
					}
					if (!board[5-i][i].belongsToPlayer(p))
					{
						diagonalFlags[3]=false;
					}
					if (!board[1+i][i].belongsToPlayer(p))
					{
						diagonalFlags[4]=false;
					}
					if (!board[1+i][5-i].belongsToPlayer(p))
					{
						diagonalFlags[5]=false;
					}
					if (!board[i][4-i].belongsToPlayer(p))
					{
						diagonalFlags[6]=false;
					}
					if (!board[i][1+i].belongsToPlayer(p))
					{
						diagonalFlags[7]=false;
					}
				}
			}
			if (diagonalFlags[0])
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
			if (diagonalFlags[1])
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
			if (diagonalFlags[2])
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
			if (diagonalFlags[3])
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
			if (diagonalFlags[4])
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
			if (diagonalFlags[5])
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
			if (diagonalFlags[6])
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
			if (diagonalFlags[7])
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
