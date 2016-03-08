import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * Implements different actions of artificial intelligence with static fuctions.
 * @author Noam Wies
 *
 */
public class Brain 
{
	private static final Random g=new Random();
	private static final int  START_GRADE=-10000;
	/**
	 * Calculates every best move for the player for his first two turns.
	 * @param playerTurn which player the function needs to calculate the turns for (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player 1, 1 means that the spot has a piece of player 2
	 * @return the best move according to its calculations
	 */
	public static final Move firstTwoMoves(int playerTurn,Square board[][],int maxLevel)
		{
		Move bestMove=new Move();		
		if ((board[1][1].getColor()==0)&&((board[1][4].getColor()*playerTurn>=0)&&((board[4][1].getColor()*playerTurn>=0))))
		{
			bestMove.setStoneH(1);
			bestMove.setStoneW(1);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if ((board[1][4].getColor()==0)&&((board[1][1].getColor()*playerTurn>=0)&&((board[4][4].getColor()*playerTurn>=0))))
		{
			bestMove.setStoneH(1);
			bestMove.setStoneW(4);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if ((board[4][1].getColor()==0)&&((board[1][1].getColor()*playerTurn>=0)&&((board[4][4].getColor()*playerTurn>=0))))
		{
			bestMove.setStoneH(4);
			bestMove.setStoneW(1);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if ((board[4][4].getColor()==0)&&((board[1][4].getColor()*playerTurn>=0)&&((board[4][1].getColor()*playerTurn>=0))))
		{
			bestMove.setStoneH(4);
			bestMove.setStoneW(4);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}		
		
		if (board[1][1].getColor()==0)
		{
			bestMove.setStoneH(1);
			bestMove.setStoneW(1);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if (board[1][4].getColor()==0)
		{
			bestMove.setStoneH(1);
			bestMove.setStoneW(4);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if (board[4][1].getColor()==0)
		{
			bestMove.setStoneH(4);
			bestMove.setStoneW(1);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		if (board[4][4].getColor()==0)
		{
			bestMove.setStoneH(4);
			bestMove.setStoneW(4);
			return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
		}
		// the calculation never gets to this line because the function is only called for the first two turns, so there are
		// maximum 3 pieces on the board, and therefore for sure one of the four middles are available and so for sure one
		// one of the previous "if"s will be true
		return bestMove;
	}
	/**
	* Calculates the best rotations for the player for his first two turns
	 * @param playerTurn which player the function needs to calculate the turns for (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player 1, 1 means that the spot has a piece of player 2
	 * @param bestMove which spot is best to put the ball in
	* @param maxLevel the highest level played in the game based on the calculation
	 * @return the best move to do now
	 */
	private  static final Move firstTwoTurnRotation(int playerTurn,Square board[][],Move bestMove,int maxLevel)
	{
		//returns the rotation it thinks is best and it can only uses the first two turns
		int h,i,grade;
		bestMove.setGrade(START_GRADE);
		board[bestMove.getStoneH()][bestMove.getStoneW()].setColor(playerTurn);
		for (h=0;h<2;h++)
		{
			for (i=0;i<2;i++)
			{
				clockWiseRotate(board, h, i);
				grade=basicHeuristic(playerTurn, board, maxLevel);				
				if (grade>bestMove.getGrade())
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(h);
					bestMove.setRotationW(i);
				}
				else if ((grade==bestMove.getGrade())&&(g.nextBoolean()))
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(h);
					bestMove.setRotationW(i);
				}
				counterClockWiseRotate(board, h, i);
				counterClockWiseRotate(board, h, i);			
				grade=basicHeuristic(playerTurn, board, maxLevel);				
				if (grade>bestMove.getGrade())
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(h);
					bestMove.setRotationW(i);
				}
				else if ((grade==bestMove.getGrade())&&(g.nextBoolean()))
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(h);
					bestMove.setRotationW(i);
				}
				clockWiseRotate(board, h, i);		
			}
		}
		board[bestMove.getStoneH()][bestMove.getStoneW()].setColor(0);	
		return bestMove;
	}
	/**
	 * Checks based on the current board, if the player sent to the funtion has 5 in a row on the board of his color.
	 *
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player 1, 1 means that the spot has a piece of player 2
	 * @param playerTurn which player it needs to check if it got 5 in a row (-1 for player1; 1 for player2)
	 * @return if the player that was passed to the funtion has 5 in a row.
	 */
	public static final boolean isWin(Square board[][],int playerTurn)
	{
		int i,h;
		boolean isW,isH,isW2,isH2,d1,d2,d3,d4,d5,d6,d7,d8;
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
				if (!board[i][h].belongsToPlayer(playerTurn))
				{
					isW=false;
				}
				if (!board[h][i].belongsToPlayer(playerTurn))
				{
					isH=false;
				}
				if (!board[i][5-h].belongsToPlayer(playerTurn))
				{
					isW2=false;
				}
				if (!board[5-h][i].belongsToPlayer(playerTurn))
				{
					isH2=false;
				}
				
				
			}			
			if ((isH)||(isH2)||(isW)||(isW2))
			{
				return true;
			}
			
			if (i<5)
			{
				if (!board[i][i].belongsToPlayer(playerTurn))
				{
					d1=false;
				}
				if (!board[5-i][5-i].belongsToPlayer(playerTurn))
				{
					d2=false;
				}
				if (!board[i][5-i].belongsToPlayer(playerTurn))
				{
					d3=false;
				}
				if (!board[5-i][i].belongsToPlayer(playerTurn))
				{
					d4=false;
				}
				if (!board[1+i][i].belongsToPlayer(playerTurn))
				{
					d5=false;
				}
				if (!board[1+i][5-i].belongsToPlayer(playerTurn))
				{
					d6=false;
				}
				if (!board[i][4-i].belongsToPlayer(playerTurn))
				{
					d7=false;
				}
				if (!board[i][1+i].belongsToPlayer(playerTurn))
				{
					d8=false;
				}		
			}				
		}
		if ((d1)||(d2)||(d3)||(d4)||(d5)||(d6)||(d7)||(d8))
		{
			return true;
		}
		return false;
	}

	/**
	 * Returns if the function recieved as a parameter will for sure win on the next turn.
	 * @param playerTurn the player to check if he will win on the next turn (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player 1, 1 means that the spot has a piece of player 2
	 * @return if the player sent to the function will win on the next turn
     */
	private static final boolean isWillWin(int playerTurn,Square board [][])
	{
		int i;
		for (i=0;i<board.length;i++)
		{
			if ((board[i][0].getColor()+board[i][1].getColor()+board[i][2].getColor()+board[i][3].getColor()+board[i][4].getColor())*playerTurn>=4)
			{
				return true;
			}
			if ((board[i][1].getColor()+board[i][2].getColor()+board[i][3].getColor()+board[i][4].getColor()+board[i][5].getColor())*playerTurn>=4)
			{
				return true;
			}
			if ((board[0][i].getColor()+board[1][i].getColor()+board[2][i].getColor()+board[3][i].getColor()+board[4][i].getColor())*playerTurn>=4)
			{
				return true;
			}	
			if ((board[1][i].getColor()+board[2][i].getColor()+board[3][i].getColor()+board[4][i].getColor()+board[5][i].getColor())*playerTurn>=4)
			{
				return true;
			}	
		}
		if ((board[0][0].getColor()+board[1][1].getColor()+board[2][2].getColor()+board[3][3].getColor()+board[4][4].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][1].getColor()+board[2][2].getColor()+board[3][3].getColor()+board[4][4].getColor()+board[5][5].getColor())*playerTurn>=4)
		{
			return true;
		}		
		if ((board[0][5].getColor()+board[1][4].getColor()+board[2][3].getColor()+board[3][2].getColor()+board[4][1].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][4].getColor()+board[2][3].getColor()+board[3][2].getColor()+board[4][1].getColor()+board[5][0].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][0].getColor()+board[2][1].getColor()+board[3][2].getColor()+board[4][3].getColor()+board[5][4].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[4][0].getColor()+board[3][1].getColor()+board[2][2].getColor()+board[1][3].getColor()+board[0][4].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][5].getColor()+board[2][4].getColor()+board[3][3].getColor()+board[4][2].getColor()+board[5][1].getColor())*playerTurn>=4)
		{
			return true;
		}
		if ((board[4][5].getColor()+board[3][4].getColor()+board[2][3].getColor()+board[1][2].getColor()+board[0][1].getColor())*playerTurn>=4)
		{
			return true;
		}
		return false;
	}

	/**
	 * Does the first check of the state of the board if on the board there is a win for one of the sides in this turn for
	 * the player it receives as a parameter and on the next turn for his opponent.
	 * @param playerTurn the player to check if he has a win on this turn (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player 1, 1 means that the spot has a piece of player 2
	 * @return
     */
	private static final int firstCheck(int playerTurn,Square board [][])
	{
		boolean p1=isWin(board, playerTurn);
		boolean p2=isWin(board, playerTurn*-1);		
		if(p1)
		{
			if(p2)
			{
				return 0;
			}
			else
			{
				return 100;
			}
			
		}
		else
		{
			if (p2)
			{
				return -100;
			}
			else
			{
				boolean k;				
				p2=false; // no effect
				for (int ii=0;(ii<2)&&((!p1)||(!p2));ii++)
				{
					for (int hh=0;(hh<2)&&((!p1)||(!p2));hh++)
					{
						k=true;
						for (int zz=0;(zz<2)&&(!p2);zz++)
						{
							rotateArray(board, ii, hh, k);							
							p2=p2||(isWillWin( playerTurn*-1,board)&&(!isWin(board, playerTurn)));
							rotateArray(board, ii, hh, !k);
							k=!k;
						}
					}
				}
				if (p2)
				{
					return -99;
				}
				else
				{
					return 0;
				}
				
			}
			
		}	
	}
	/**
	 * Rotates the board.
	 *
	 * @param board the game board that the funtion will rotate
	 * @param row the row number that the funtion will rotate
	 * @param col the column number the funtion will rotate
	 * @param clocklWise if the function will rotate clockwise (true) or counterclockwise (false)
	 */
	public static final void rotateArray(Square board [][], int row, int col, boolean clocklWise)
	{
		if (clocklWise)
		{
			counterClockWiseRotate(board, row, col);
		}
		else
		{
			clockWiseRotate(board, row,col);
		}
	}	
	/**
	 * Rotates the small board (quadrant of the board) that is received as a parameter, clockwise.
	 * @param board game board that the function will rotate
	 * @param row row number of the board that will be rotated
	 * @param col column number of the board that will be rotated
	 */
	public static final void clockWiseRotate(Square board[][], int row, int col)
	{
		Square temp1,temp2;		
		temp1=board[row*3][col*3];
		temp2=board[row*3][col*3+1];
		board[row*3][col*3]=board[row*3+2][col*3];
		board[row*3][col*3+1]=board[row*3+1][col*3];
		board[row*3+2][col*3]=board[row*3+2][col*3+2];
		board[row*3+1][col*3]=board[row*3+2][col*3+1];
		board[row*3+2][col*3+2]=board[row*3][col*3+2];
		board[row*3+2][col*3+1]=board[row*3+1][col*3+2];
		board[row*3][col*3+2]=temp1;
		board[row*3+1][col*3+2]=temp2;
	}
	/**
	  * Rotates the small board (quadrant of the board) that is received as a parameter, clockwise.
	 * @param board game board that the function will rotate
	 * @param row row number of the board that will be rotated
	 * @param col column number of the board that will be rotated
	 */
	public static final void counterClockWiseRotate(Square board[][], int row, int col)
	{
		Square temp1,temp2;		
		temp1=board[row*3][col*3];
		temp2=board[row*3][col*3+1];
		board[row*3][col*3]=board[row*3][col*3+2];
		board[row*3][col*3+1]=board[row*3+1][col*3+2];
		board[row*3][col*3+2]=board[row*3+2][col*3+2];
		board[row*3+1][col*3+2]=board[row*3+2][col*3+1];
		board[row*3+2][col*3+2]=board[row*3+2][col*3];
		board[row*3+2][col*3+1]=board[row*3+1][col*3];
		board[row*3+2][col*3]=temp1;
		board[row*3+1][col*3]=temp2;
	}

	/**
	 * Does the second check of the state of the board, checking if the board has a trap that will guarantee a win for one
	 * of the sides on the next turn if the player it gets as a parameter, or if in another two turns of the opponent, and
	 * if there is not a trap then the function caculates if there is a state that another turn could create this trap
     */
	private static final int secondCheck(int playerTurn,Square board [][],int maxLevel)
	{
		//assumes that the maximum amount of turns is less than 50, because the possible amount of turns is 36
		boolean p1=four( playerTurn,4,board);
		boolean p2=four( playerTurn*-1,3,board);
		boolean k;		
		for (int ii=0;(ii<2)&&((!p1)||(!p2));ii++)
		{
			for (int hh=0;(hh<2)&&((!p1)||(!p2));hh++)
			{
				k=true;
				for (int zz=0;(zz<2)&&(!p2);zz++)
				{
					rotateArray(board, ii, hh, k);					
					p2=p2||four( playerTurn*-1,3,board);
					rotateArray(board, ii, hh, !k);
					k=!k;
				}
			}
		}		
		if(p1)
		{
			return 99;
			
		}
		else
		{
			if (p2)
			{
				return -98;
			}
			else
			{
				p1=four( playerTurn,3,board);					
				if(p1)
				{
					return 99-maxLevel;					
				}
				else
				{
					p2=false;
					for (int ii=0;(ii<2)&&((!p1)||(!p2));ii++)
					{
						for (int hh=0;(hh<2)&&((!p1)||(!p2));hh++)
						{
							k=true;
							for (int zz=0;(zz<2)&&(!p2);zz++)
							{
								rotateArray(board, ii, hh, k);							
								p2=p2||(isWillFour(playerTurn*-1,board));
								rotateArray(board, ii, hh, !k);
								k=!k;
							}
						}
					}
					if (p2)
					{
						return -98+maxLevel;
					}
					else
					{
						return 0;
					}					
				}
			}			
		}	
	}

	/**
	 * Helper function for the function that does the second check
     */
	private static final boolean isWillFour(int playerTurn,Square board[][])
	{
		int h;		
		for (h=0;h<board.length;h++)
		{
			if (((board[h][1].getColor()+board[h][2].getColor()+board[h][3].getColor()+board[h][4].getColor())*playerTurn>=2)&&(playerTurn*board[h][1].getColor()>=0)&&(playerTurn*board[h][2].getColor()>=0)&&(playerTurn*board[h][3].getColor()>=0)&&(playerTurn*board[h][4].getColor()>=0))
			{
				return true;
			}
			if (((board[1][h].getColor()+board[2][h].getColor()+board[3][h].getColor()+board[4][h].getColor())*playerTurn>=2)&&(playerTurn*board[1][h].getColor()>=0)&&(playerTurn*board[2][h].getColor()>=0)&&(playerTurn*board[3][h].getColor()>=0)&&(playerTurn*board[4][h].getColor()>=0))
			{
				return true;
			}
		}
		if (((board[1][1].getColor()+board[2][2].getColor()+board[3][3].getColor()+board[4][4].getColor())*playerTurn>=2)&&(playerTurn*board[1][1].getColor()>=0)&&(playerTurn*board[2][2].getColor()>=0)&&(playerTurn*board[3][3].getColor()>=0)&&(playerTurn*board[4][4].getColor()>=0))
		{
			return true;
		}
		if (((board[1][4].getColor()+board[2][3].getColor()+board[3][2].getColor()+board[4][1].getColor())*playerTurn>=2)&&(playerTurn*board[1][4].getColor()>=0)&&(playerTurn*board[2][3].getColor()>=0)&&(playerTurn*board[3][2].getColor()>=0)&&(playerTurn*board[4][1].getColor()>=0))
		{
			return true;
		}
		return false;
	}

	/**
	 * Helper function for the second check funtion, checking if there is a row in the board it gets as a parameter that in
	 * its middle (excludes the edges) there is at least "num" (the paramter) balls of the player it gets as a parameter, and
	 * that there isnt in the row even one ball of the opponent's balls
     */
	private static final boolean four(int player,int num,Square board[][])
	{
		int h;
		for (h=0;h<board.length;h++)
		{
			if ((board[h][0].getColor()!=player*-1)&&(board[h][5].getColor()!=player*-1)&&((board[h][1].getColor()+board[h][2].getColor()+board[h][3].getColor()+board[h][4].getColor())*player>=num))
			{
				return true;
			}
			if ((board[0][h].getColor()!=player*-1)&&(board[5][h].getColor()!=player*-1)&&((board[1][h].getColor()+board[2][h].getColor()+board[3][h].getColor()+board[4][h].getColor())*player>=num))
			{
				return true;
			}
		}
		if ((board[0][0].getColor()!=player*-1)&&(board[5][5].getColor()!=player*-1)&&((board[1][1].getColor()+board[2][2].getColor()+board[3][3].getColor()+board[4][4].getColor())*player>=num))
		{
			return true;
		}
		if ((board[5][0].getColor()!=player*-1)&&(board[0][5].getColor()!=player*-1)&&((board[1][4].getColor()+board[2][3].getColor()+board[3][2].getColor()+board[4][1].getColor())*player>=num))
		{
			return true;
		}
		return false;
	}
	/**
	 * Huristic funtion that is used to value the current state of the board (greedy function)
	 * @param playerTurn which player's turn should be calculated (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 * of player 1, 1 means that the spot has a piece of player 2
	* @param maxLevel the highest level played in the game based on the calculation
	 * @return the grade of the turn that represents the profitability for you to do this turn
	 */
	private static final int basicHeuristic(int playerTurn,Square board[][],int maxLevel)
	{
		int ii,hh,z;
		boolean clockWise=true;
		int p1=0;
		int p2=0;
		for (ii=0;ii<2;ii++)
		{
			for (hh=0;hh<2;hh++)
			{
				for (z=0;z<2;z++)
				{
					rotateArray(board, ii, hh,clockWise);
					p1+=numOfOptionsToWin(playerTurn,board);
					p2+=numOfOptionsToWin(playerTurn*-1,board);
					rotateArray(board, ii, hh,!clockWise);
					clockWise=!clockWise;
				}
			}
		}
		
		
		if (Math.abs((p1-p2))<100-2*maxLevel)
		{
			return (p1-p2);
		}
		else if (Math.abs((p1-p2)/2)<100-2*maxLevel)
		{
			return (p1-p2)/2;
		}
		return 100-2*maxLevel-10;
	}

	/**
	 * Gives a value to the row it gets as a parameter (the five spots have to be adjacent to one another, otherwise the
	 * check doesnt make sense) for the player it gets as a parameter (-1 for player1; 1 for player2)
     */
	private static final int lineGrade(int a,int b,int c,int d,int e,int playerTurn)
	{
		if ((a*playerTurn>=0)&&(b*playerTurn>=0)&&(c*playerTurn>=0)&&(d*playerTurn>=0)&&(e*playerTurn>=0))
		{
			return -1;
		}
		else
		{
			return (int)(Math.pow((double)(a+b+c+d+e), 3.0));
		}
	}

	/**
	 * Determines how good the the state of the player it gets as a parameter is based on the future 5-in-a-row's that it can
	 * create when it determines its ability to create 5 in a row, more when there are more of the player's balls on the board now
     */
	private static final int numOfOptionsToWin(int playerTurn ,Square board[][])
	{
		int i,count=0,temp;		
		for (i=0;i<board.length;i++)
		{
			temp=lineGrade(board[i][0].getColor(), board[i][1].getColor(), board[i][2].getColor(), board[i][3].getColor(), board[i][4].getColor(), playerTurn);
			if (temp!=-1)
			{
				count+=temp;
				if (board[i][5].getColor()*playerTurn>=0)
				{
					count+=lineGrade(board[i][5].getColor(), board[i][1].getColor(), board[i][2].getColor(), board[i][3].getColor(), board[i][4].getColor(), playerTurn);
				}
			}					
			else
			{
				if (!(board[i][0].getColor()*playerTurn>=0))
				{
					temp=lineGrade(board[i][5].getColor(), board[i][1].getColor(), board[i][2].getColor(), board[i][3].getColor(), board[i][4].getColor(), playerTurn);
					if (temp!=-1)
					{
						count+=temp;								
					}
				}
			}
			temp=lineGrade(board[0][i].getColor(), board[1][i].getColor(), board[2][i].getColor(), board[3][i].getColor(), board[4][i].getColor(), playerTurn);
			if (temp!=-1)
			{
				count+=temp;	
				if (board[5][i].getColor()*playerTurn>=0)
				{
					count+=lineGrade(board[5][i].getColor(), board[1][i].getColor(), board[2][i].getColor(), board[3][i].getColor(), board[4][i].getColor(), playerTurn);
				}
			}					
			else
			{
				if (!(board[0][i].getColor()*playerTurn>=0))
				{
					temp=lineGrade(board[5][i].getColor(), board[1][i].getColor(), board[2][i].getColor(), board[3][i].getColor(), board[4][i].getColor(), playerTurn);
					if (temp!=-1)
					{
						count+=temp;								
					}
				}
			}
			
		}
		temp=lineGrade(board[0][0].getColor(), board[1][1].getColor(), board[2][2].getColor(), board[3][3].getColor(), board[4][4].getColor(), playerTurn);
		if (temp!=-1)
		{
			count+=temp;
			if (board[5][5].getColor()*playerTurn>=0)
			{
				count+=lineGrade(board[5][5].getColor(), board[1][1].getColor(), board[2][2].getColor(), board[3][3].getColor(), board[4][4].getColor(), playerTurn);
			}
		}
		else
		{
			if (!(board[0][0].getColor()*playerTurn>=0))
			{
				temp=lineGrade(board[5][5].getColor(), board[1][1].getColor(), board[2][2].getColor(), board[3][3].getColor(), board[4][4].getColor(), playerTurn);
				if (temp!=-1)
				{
					count+=temp;								
				}
			}
		}
		temp=lineGrade(board[0][5].getColor(), board[1][4].getColor(), board[2][3].getColor(), board[3][2].getColor(), board[4][1].getColor(), playerTurn);
		if (temp!=-1)
		{
			count+=temp;
			if (board[5][0].getColor()*playerTurn>=0)
			{
				count+=lineGrade(board[5][0].getColor(), board[1][4].getColor(), board[2][3].getColor(), board[3][2].getColor(), board[4][1].getColor(), playerTurn);
			}
		}
		else
		{
			if (!(board[0][5].getColor()*playerTurn>=0))
			{
				temp=lineGrade(board[5][0].getColor(), board[1][4].getColor(), board[2][3].getColor(), board[3][2].getColor(), board[4][1].getColor(), playerTurn);
				if (temp!=-1)
				{
					count+=temp;								
				}
			}
		}
		temp=lineGrade(board[1][0].getColor(), board[2][1].getColor(), board[3][2].getColor(), board[4][3].getColor(), board[5][4].getColor(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[0][1].getColor(), board[1][2].getColor(), board[2][3].getColor(), board[3][4].getColor(), board[4][5].getColor(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[0][4].getColor(), board[1][3].getColor(), board[2][2].getColor(), board[3][1].getColor(), board[4][0].getColor(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[1][5].getColor(), board[2][4].getColor(), board[3][3].getColor(), board[4][2].getColor(), board[5][1].getColor(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		return count;
	}
	/**
	 * Caclulates the best move for the player.
	 * @param playerTurn which player the function needs to calculate the turns for (-1 for player1; 1 for player2)
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player1, 1 means it has a piece of player2
	 * @param level the maximum level of the of the tree that is built in order to find the best move
	 * @param maxLevel the highest level played in the game based on the calculation
	 * @return the best move according to its calculation
	 */	
	public static final Move calcMove(int playerTurn ,int level,Square board[][],int maxLevel)
	{
		Move bestMove=new Move();
		Move temp2=new Move();
		bestMove.setGrade(START_GRADE);
		for (temp2.setStoneH(0);temp2.getStoneH()<board.length;temp2.setStoneH(temp2.getStoneH()+1))
		{
			for (temp2.setStoneW(0);temp2.getStoneW()<board[0].length;temp2.setStoneW(temp2.getStoneW()+1))			
			{
				if (board[temp2.getStoneH()][temp2.getStoneW()].getColor()==0)
				{
					for (temp2.setRotationH(0);temp2.getRotationH()<2;temp2.setRotationH(temp2.getRotationH()+1))
					{
						for (temp2.setRotationW(0);temp2.getRotationW()<2;temp2.setRotationW(temp2.getRotationW()+1))
						{
							temp2.setIsClockwise(true);		
							applyMove(temp2, board, playerTurn);							
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,bestMove.getGrade()));
							if (temp2.getGrade()>bestMove.getGrade())
							{
								bestMove=new Move(temp2);
								if (bestMove.getGrade()==100)
								{
									unapplyMove(temp2,board);
									return bestMove;
								}
							}
							else 
							{
								if (temp2.getGrade()==bestMove.getGrade())
								{
									if (Math.abs(bestMove.getGrade())>=100-2*maxLevel)
									{
										if (bestMove.getHgrade()==Move.StartHGrade)
										{
											bestMove.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
										}
										temp2.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
										if (temp2.getHgrade()>bestMove.getHgrade())
										{
											bestMove=new Move(temp2);	
										}
										else
										{
											if ((temp2.getHgrade()==bestMove.getHgrade())&&(g.nextBoolean()))
											{
												bestMove=new Move(temp2);	
											}
										}											
									}
								}						
							}
//							else if ((temp2.getGrade()==max.getGrade())&&(g.nextBoolean()))
//							{
//								max=new Turn(temp2);	
//							}
							unapplyMove(temp2,board);
							temp2.setIsClockwise(false);							
							applyMove(temp2, board, playerTurn);			
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,bestMove.getGrade()));
							if (temp2.getGrade()>bestMove.getGrade())
							{
								bestMove=new Move(temp2);
								if (bestMove.getGrade()==100)
								{
									unapplyMove(temp2,board);
									return bestMove;
								}
							}
							else 
							{
								if (temp2.getGrade()==bestMove.getGrade())
								{
									if (Math.abs(bestMove.getGrade())>=100-2*maxLevel)
									{
										if (bestMove.getHgrade()==Move.StartHGrade)
										{
											bestMove.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
										}
										temp2.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
										if (temp2.getHgrade()>bestMove.getHgrade())
										{
											bestMove=new Move(temp2);	
										}
										else
										{
											if ((temp2.getHgrade()==bestMove.getHgrade())&&(g.nextBoolean()))
											{
												bestMove=new Move(temp2);	
											}
										}											
									}
								}						
							}
//							else if ((temp2.getGrade()==max.getGrade())&&(g.nextBoolean()))
//							{
//								max=new Turn(temp2);	
//							}
							unapplyMove(temp2,board);					
						}
					}
				}
			}
		}		
		return bestMove;
	}

	private static final int calcBoardGrade(int playerTurn,int level,Square board[][],int maxLevel,int alfa)
	{
		int re=firstCheck(playerTurn,board);
		if (re==0)
		{
			re=secondCheck(playerTurn,board,maxLevel);			
			if ((re==0)||(re==99-maxLevel)||(re==-98+maxLevel))
			{
				if (level>1)
				{
					int re2=-1*calcMove2(playerTurn*-1, level-1,board,maxLevel,alfa).getGrade();
					if((re2>98-maxLevel)||(re2<maxLevel-97)||(re==0))
					{
						re=re2;
						if (re>1)
						{
							re--;
						}
						else if (re<-1)
						{
							re++;
						}
					}			
				}
				else
				{
					if (re==0)
					{
						re=basicHeuristic(playerTurn,board,maxLevel);	
					}
				}
			}
		}
		return re;	
	}
	/**
	 * Calculates the best move for the player, as opposed to the other funtion with does this, this funtion doesnt check
	 * all the possibilities of the rotation
	 * @param playerTurn which player the function needs to calculate the turns for (-1 for player1; 1 for player2)
	 * @param level the level that according to it, every turn will be calculated, which affects how deep the possibility
	 *              tree will cover/spread
	 * @param board 2d array that represents the state of the game, 0 means the spot is empty, -1 means the spot has a piece
	 *              of player1, 1 means it has a piece of player2
	 * @param maxLevel saves the highest calculated level that is played in the current game
	 * @param alfa the highest value of the previous level in the tree
	 * @return the best move according to its calculations
	 */
	private static final Move calcMove2(int playerTurn ,int level,Square board[][],int maxLevel,int alfa)
	{
		Move bestMove=new Move();
		Move temp2=new Move();		
		bestMove.setGrade(START_GRADE);
		for (temp2.setStoneH(0);temp2.getStoneH()<board.length;temp2.setStoneH(temp2.getStoneH()+1))
		{
			for (temp2.setStoneW(0);temp2.getStoneW()<board[0].length;temp2.setStoneW(temp2.getStoneW()+1))
			{
				if (board[temp2.getStoneH()][temp2.getStoneW()].getColor()==0)
				{
					board[temp2.getStoneH()][temp2.getStoneW()].setColor(playerTurn);					
					temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,bestMove.getGrade()));
					if (((alfa>1)&&(-alfa-1<=temp2.getGrade()))||((alfa<-1)&&(-alfa+1<=temp2.getGrade()))||((-alfa<temp2.getGrade())&&(Math.abs(alfa)==1))||((temp2.getGrade()>=0)&&(alfa==0)))
					{
						board[temp2.getStoneH()][temp2.getStoneW()].setColor(0);		
						return temp2;
					}
					if (temp2.getGrade()>bestMove.getGrade())
					{
						bestMove=new Move(temp2);
						if (bestMove.getGrade()==100)
						{
							unapplyMove(temp2,board);
							return bestMove;
						}
					}
					else 
					{
						if (temp2.getGrade()==bestMove.getGrade())
						{
							if (Math.abs(bestMove.getGrade())>=100-2*maxLevel)
							{
								if (bestMove.getHgrade()==Move.StartHGrade)
								{
									bestMove.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
								}
								temp2.setHgrade(basicHeuristic(playerTurn, board, maxLevel));
								if (temp2.getHgrade()>bestMove.getHgrade())
								{
									bestMove=new Move(temp2);	
								}
								else
								{
									if ((temp2.getHgrade()==bestMove.getHgrade())&&(g.nextBoolean()))
									{
										bestMove=new Move(temp2);	
									}
								}											
							}
						}						
					}
//					else if ((temp2.getGrade()==max.getGrade())&&(g.nextBoolean()))
//					{
//						max=new Turn(temp2);	
//					}
					board[temp2.getStoneH()][temp2.getStoneW()].setColor(0);		
				}						
			}
		}	
		return bestMove;
	}

	public static final void unapplyMove(Move turn,Square board[][])
	{
		rotateArray(board, turn.getRotationH(), turn.getRotationW(),!turn.isClockwise());
		board[turn.getStoneH()][turn.getStoneW()].setColor(0);
	}

	public static final void applyMove(Move turn,Square board[][],int player)
	{
		board[turn.getStoneH()][turn.getStoneW()].setColor(player);
		rotateArray(board, turn.getRotationH(), turn.getRotationW(),turn.isClockwise());
	}
	
	
	public static List<Move> getMoves(Square[][] board)
	{
		LinkedList<Move> moves = new LinkedList<>();
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j].getColor() == 0)
				{
					for (int k = 0; k < 4; k++)
					{
						int subBoardH = k % 2;
						int subBoardW = k / 2;
						moves.add(new Move(subBoardH, subBoardW, true, i, j, 0));
						moves.add(new Move(subBoardH, subBoardW, false, i, j, 0));
					}
				}
			}
		}
		return moves;
	}
}