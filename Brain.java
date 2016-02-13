import java.util.Random;
/**
 * ������ ����� ����� �� ���� ������ �� ���� �������� �"� ����� ��������� ������� 
 * @author Noam Wies
 *
 */
public class Brain 
{
	private static final Random g=new Random();
	private static final int  START_GRADE=-10000;
	/**
	 * �������� ����� �� ����� ��� ��� ���� ���� �2 ������� ��������
	 * @param playerTurn ����� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @return �������� ������ �� ���� ���� ��� ��� ����
	 */
	public static final Move firstTwoMoves(int playerTurn,Square board[][],int maxLevel)
		{
		Move bestMove=new Move();
		int x, y;
		/* Places at a sub-board's center (x,y) if the adjacent centers are 
		 * empty or owned by the current player.
		 */
		for (int i = 0; i < 4; i++)
		{
			x = i < 2 ? 1 : 4;
			y = i % 2 == 0 ? 1 : 4;
			if ((board[x][y].getColor()==0)&&((board[(x+3)%6][y].getColor()*playerTurn>=0)&&((board[x][(y+3)%6].getColor()*playerTurn>=0))))
			{
				bestMove.setStoneH(x);
				bestMove.setStoneW(y);
				return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
			}
		}
		for (int i = 0; i < 4; i++)
		{
			x = i < 2 ? 1 : 4;
			y = i % 2 == 0 ? 1 : 4;
			if (board[x][y].getColor()==0)
			{
				bestMove.setStoneH(x);
				bestMove.setStoneW(y);
				return firstTwoTurnRotation(playerTurn, board, bestMove, maxLevel);
			}
		}		
		return null;//����� �� ��� �� ���� ����� ���� ���� ������� �������� �� ���� ������ �������� �� ����� ��� ���� ������� ����� ����� ���� ���� ��� ������ �������� ������ ���� ��� ������ ������� �������
	}
	/**
	* �������� ����� �� ������ ��� ��� ���� ���� �2 ������� ��������
	 * @param playerTurn ����� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @param bestMove ���� �������� �� �����  ���� ����� ���� �� �� �����
	* @param maxLevel ���� ��� ����� ������� ����� �� �"� �����
	 * @return �������� ������ �� ����� ���� ����� ����� ����
	 */
	private  static final Move firstTwoTurnRotation(int playerTurn,Square board[][],Move bestMove,int maxLevel)
	{
		//�������� ������ �� ������ ���� ����� ���� ��� ��� ���� ������ ������ ���� ������ �������� ����
		int grade;
		bestMove.setGrade(START_GRADE);
		board[bestMove.getStoneH()][bestMove.getStoneW()].setColor(playerTurn);
		for (int i = 0; i < 2; i++)
		{
			for (int j = 0; j < 2; j++)
			{
				clockWiseRotate(board, i, j);
				grade=basicHeuristic(playerTurn, board, maxLevel);				
				if (grade>bestMove.getGrade() || ((grade==bestMove.getGrade())&&(g.nextBoolean())))
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(i);
					bestMove.setRotationW(j);
				}
				counterClockWiseRotate(board, i, j);
				counterClockWiseRotate(board, i, j);			
				grade=basicHeuristic(playerTurn, board, maxLevel);				
				if (grade>bestMove.getGrade() || ((grade==bestMove.getGrade())&&(g.nextBoolean())))
				{
					bestMove.setGrade(grade);
					bestMove.setRotationH(i);
					bestMove.setRotationW(j);
				}
				clockWiseRotate(board, i, j);		
			}
		}
		board[bestMove.getStoneH()][bestMove.getStoneW()].setColor(0);	
		return bestMove;
	}
	/**
	 * 
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ����
	 * @param playerTurn ���� �������� ����� ���� ���� ����� ��� ��� ��� ��� �� ����� ����� -1 ����� ������ �1 ����� ����
	 * @return ����� ��� ����� ���������� ����� ������ ��� ��� �� ����� �����   
	 */
	// TODO delete either this method or GameManager.checkWinning
	public static final boolean isWin(Square board[][],int playerTurn)
	{
		//�������� ������ ��� ���� ���� ���� ����� ������ ����� ���� ����� ������ ��� ��� �� ����� ������ ���� ���
		boolean wFlag,hFlag,wFlag2,hFlag2;
		boolean[] diagonalFlags = {true, true, true, true, true, true, true, true};
		for (int i = 0; i < 6; i++)
		{
			wFlag = true;
			hFlag = true;
			wFlag2 = true;
			hFlag2 = true;
			for (int j = 0; j < 5; j++)
			{
				{
					if (!board[i][j].belongsToPlayer(playerTurn))
					{
						wFlag=false;
					}
					if (!board[j][i].belongsToPlayer(playerTurn))
					{
						hFlag=false;
					}
					if (!board[i][5-j].belongsToPlayer(playerTurn))
					{
						wFlag2=false;
					}
					if (!board[5-j][i].belongsToPlayer(playerTurn))
					{
						hFlag2=false;
					}
				}	
			}			
			if (wFlag || hFlag || wFlag2 || hFlag2)
			{
				return true;
			}
			if (i<5)
			{
				if (!board[i][i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[0]=false;
				}
				if (!board[5-i][5-i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[1]=false;
				}
				if (!board[i][5-i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[2]=false;
				}
				if (!board[5-i][i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[3]=false;
				}
				if (!board[1+i][i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[4]=false;
				}
				if (!board[1+i][5-i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[5]=false;
				}
				if (!board[i][4-i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[6]=false;
				}
				if (!board[i][1+i].belongsToPlayer(playerTurn))
				{
					diagonalFlags[7]=false;
				}
			}				
		}
		for (boolean dFlag : diagonalFlags)
		{
			if (dFlag)
			{
				return true;
			}
		}
		return false;
	}	
	private static final boolean isWillWin(int playerTurn,Square board [][])
	{
		//�������� ������ ��� ����� ���� ����� ������ ���� ���� ���� ��� 
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
	private static final int firstCheck(int playerTurn,Square board [][])
	{
		//�������� ����� �� ������ �������� �� ���� ���� ������� ���� ����� ��� ��� �� ���� ������ ���� ������ ���� ������ �� ����� ���� ����� ������ ����� ��� �� ����� ���
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
	 * ������� ������ �� ���� 
	 * @param board ��� ����� ���������� �����
	 * @param ii ���� ����� �� ���� ��������� �����
	 * @param hh ���� ������ �� ���� ��������� �����
	 * @param clocklWise   ��� ������ ��� �� ����� ����� �� ��� ����� ����� 
	 */
	public static final void rotateArray(Square board [][],int ii,int hh,boolean clocklWise)	
	{
		if (clocklWise)
		{
			counterClockWiseRotate(board, ii, hh);
		}
		else
		{
			clockWiseRotate(board, ii,hh);
		}
	}	
	/**
	 * �������� ������ �� ���� ���� ��� ������ ��� ����� ������ �� ����� �����
	 * @param board ��� ����� ���������� �����
	 * @param ii ���� ����� �� ���� ��������� �����
	 * @param hh ���� ������ �� ���� ��������� �����
	 */
	public static final void clockWiseRotate(Square board[][],int ii,int hh)
	{
		Square temp1,temp2;		
		temp1=board[ii*3][hh*3];
		temp2=board[ii*3][hh*3+1];
		board[ii*3][hh*3]=board[ii*3+2][hh*3];
		board[ii*3][hh*3+1]=board[ii*3+1][hh*3];
		board[ii*3+2][hh*3]=board[ii*3+2][hh*3+2];
		board[ii*3+1][hh*3]=board[ii*3+2][hh*3+1];
		board[ii*3+2][hh*3+2]=board[ii*3][hh*3+2];
		board[ii*3+2][hh*3+1]=board[ii*3+1][hh*3+2];
		board[ii*3][hh*3+2]=temp1;
		board[ii*3+1][hh*3+2]=temp2;	
	}
	/**
	  * �������� ������ �� ���� ���� ��� ������ ��� ����� ������ ��� ����� ����� 
	 * @param board ��� ����� ���������� �����
	 * @param ii ���� ����� �� ���� ���� ��������� �����
	 * @param hh ���� ������ �� ���� ���� ��������� �����
	 */
	public static final void counterClockWiseRotate(Square board[][],int ii,int hh)
	{
		Square temp1,temp2;		
		temp1=board[ii*3][hh*3];
		temp2=board[ii*3][hh*3+1];
		board[ii*3][hh*3]=board[ii*3][hh*3+2];
		board[ii*3][hh*3+1]=board[ii*3+1][hh*3+2];
		board[ii*3][hh*3+2]=board[ii*3+2][hh*3+2];
		board[ii*3+1][hh*3+2]=board[ii*3+2][hh*3+1];
		board[ii*3+2][hh*3+2]=board[ii*3+2][hh*3];
		board[ii*3+2][hh*3+1]=board[ii*3+1][hh*3];
		board[ii*3+2][hh*3]=temp1;
		board[ii*3+1][hh*3]=temp2;	
	}
	private static final int secondCheck(int playerTurn,Square board [][],int maxLevel)
	{
		//��� ���� ��������� ������ ��� ������� ���� ����� ������ �������� ���� ��� ������ ���
		//�������� ����� �� ������ ����� �� ���� ���� ������� ���� ����� ��� ��� �� ���� ������ ������ ������ ���� ������ ���� ��� �� ����� ���� ����� ������ ����� ��� ����� �� ����� ��� ��� ��� ������ �� ���� ��� �������� ������ ��� �� ���� ��� ���� ��� ���� ������� ������ ���� 
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
	private static final boolean isWillFour(int playerTurn,Square board[][])
	{
//		�������� ����� ����� ��� ���� �������� ������ �� ������ ����� �� ���� ���� ������� ���� ����� ��� ��� �� ���� ������ ����� ������ ��� ��� ������ ������ ���� ������ ���� ��� �� ����� ���� ����� ������ ����� ��� ����� �� ����� ��� ��� ��� ������ �� ���� ��� �������� ������ ��� �� ���� ��� ���� ��� ���� ������� ������ ���� 
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
	private static final boolean four(int player,int num,Square board[][])
	{
		//�������� ����� �������� ��� �������� ������ ����� ����� ����� ��� �� ���� ���� ���� ����� ������ ������ ��� (�� ���� ������) �� ����� ���� ����� �� ������ ���� ����� ������ ���� �� ����� ���� ����� ����� ����� ����� ���� ��� ���� �� ����� ����� 
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
	 * ������� ��������� ������ ������ ��� ���� ����(������� ������
	 * @param playerTurn ����� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @param maxLevel ���� ��� ����� ������� ����� �� �"� �����
	 * @return ��������� ������ �� ����� �� ���� ����� �� ���  ������� ��� ����� ��� �� ���������� ��� �������� ������
	 */
	private static final int basicHeuristic(int playerTurn,Square board[][],int maxLevel)
	{
		//������� ��������� ������ ������ ��� ���� 
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
	private static final int lineGrade(int a,int b,int c,int d,int e,int playerTurn)
	{
		//�������� ������ �� ����� ���� �����  ������ (���� ����� ������ ���� ������ ��� ����� ���� ��� ��� ����� ������ ��) ���� ����� ���� ����� ������
		if ((a*playerTurn>=0)&&(b*playerTurn>=0)&&(c*playerTurn>=0)&&(d*playerTurn>=0)&&(e*playerTurn>=0))
		{
			return -1;
		}
		else
		{
			return (int)(Math.pow((double)(a+b+c+d+e), 3.0));
		}
	}
	private static final int numOfOptionsToWin(int playerTurn ,Square board[][])
	{
		//��������  ������  ��� ���� �� ����� ���� ����� ��� ������ ��� ������� ������� ��� ���� ����� ����� ������ �� ������� ������ ������� ���� ��� ��� �� ���� ������ �� ����� ����
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
	 * �������� ����� �� ����� ��� ��� ���� ����� 
	 * @param playerTurn ���� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @param level ���� ��� �������� ������ ���� ����� ����� �� ����� ���� �����
	 * @param maxLevel ���� ��� ����� ������� ����� �� �"� �����
	 * @return �������� ������ �� ���� ���� ��� ��� ����
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
							doTurn(temp2, board, playerTurn);							
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,bestMove.getGrade()));
							if (temp2.getGrade()>bestMove.getGrade())
							{
								bestMove=new Move(temp2);
								if (bestMove.getGrade()==100)
								{
									reTurn(temp2,board);
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
							reTurn(temp2,board);
							temp2.setIsClockwise(false);							
							doTurn(temp2, board, playerTurn);			
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,bestMove.getGrade()));
							if (temp2.getGrade()>bestMove.getGrade())
							{
								bestMove=new Move(temp2);
								if (bestMove.getGrade()==100)
								{
									reTurn(temp2,board);
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
							reTurn(temp2,board);					
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
	 * �������� ����� �� ����� ��� ��� ���� ����� �������� �������� ����� ����� �� �������� ���� �� ����� �� �� ��������� �� ������ 
	 * @param playerTurn ����� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param level ����� �� ���� ����� ��� �� ��� ����� ���� �� ����� ��� ���� �� ��������� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @param maxLevel ���� �� ���� �������� ������ ����� ������� ����� ������
	 * @param alfa ���� ��� ����� �� ���� ������ ���
	 * @return ���� ������ ����� ��� �����
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
							reTurn(temp2,board);
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
	private static final void reTurn(Move turn,Square board[][])
	{
		rotateArray(board, turn.getRotationH(), turn.getRotationW(),!turn.isClockwise());
		board[turn.getStoneH()][turn.getStoneW()].setColor(0);
	}
	private static final void doTurn(Move turn,Square board[][],int player)
	{
		board[turn.getStoneH()][turn.getStoneW()].setColor(player);
		rotateArray(board, turn.getRotationH(), turn.getRotationW(),turn.isClockwise());
	}
}
