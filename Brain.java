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
	public static final Turn firstTwoTurn(int playerTurn,Square board[][],int maxLevel)
		{
		Turn bestTurn=new Turn();		
		if ((board[1][1].getSide()==0)&&((board[1][4].getSide()*playerTurn>=0)&&((board[4][1].getSide()*playerTurn>=0))))
		{
			bestTurn.setPutH(1);
			bestTurn.setPutW(1);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if ((board[1][4].getSide()==0)&&((board[1][1].getSide()*playerTurn>=0)&&((board[4][4].getSide()*playerTurn>=0))))
		{
			bestTurn.setPutH(1);
			bestTurn.setPutW(4);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if ((board[4][1].getSide()==0)&&((board[1][1].getSide()*playerTurn>=0)&&((board[4][4].getSide()*playerTurn>=0))))
		{
			bestTurn.setPutH(4);
			bestTurn.setPutW(1);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if ((board[4][4].getSide()==0)&&((board[1][4].getSide()*playerTurn>=0)&&((board[4][1].getSide()*playerTurn>=0))))
		{
			bestTurn.setPutH(4);
			bestTurn.setPutW(4);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}		
		
		if (board[1][1].getSide()==0)
		{
			bestTurn.setPutH(1);
			bestTurn.setPutW(1);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if (board[1][4].getSide()==0)
		{
			bestTurn.setPutH(1);
			bestTurn.setPutW(4);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if (board[4][1].getSide()==0)
		{
			bestTurn.setPutH(4);
			bestTurn.setPutW(1);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}
		if (board[4][4].getSide()==0)
		{
			bestTurn.setPutH(4);
			bestTurn.setPutW(4);
			return firstTwoTurnRotation(playerTurn, board, bestTurn, maxLevel);
		}		
		return bestTurn;//����� �� ��� �� ���� ����� ���� ���� ������� �������� �� ���� ������ �������� �� ����� ��� ���� ������� ����� ����� ���� ���� ��� ������ �������� ������ ���� ��� ������ ������� �������
	}
	/**
	* �������� ����� �� ������ ��� ��� ���� ���� �2 ������� ��������
	 * @param playerTurn ����� �������� ����� ���� ���� ���� �� ���� -1 ����� ������ �1 ����� ����
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ���� 
	 * @param bestTurn ���� �������� �� �����  ���� ����� ���� �� �� �����
	* @param maxLevel ���� ��� ����� ������� ����� �� �"� �����
	 * @return �������� ������ �� ����� ���� ����� ����� ����
	 */
	private  static final Turn firstTwoTurnRotation(int playerTurn,Square board[][],Turn bestTurn,int maxLevel)
	{
		//�������� ������ �� ������ ���� ����� ���� ��� ��� ���� ������ ������ ���� ������ �������� ����
		int h,i,grade;
		bestTurn.setGrade(START_GRADE);
		board[bestTurn.getPutH()][bestTurn.getPutW()].setSide(playerTurn);
		for (h=0;h<2;h++)
		{
			for (i=0;i<2;i++)
			{
				clocklWiseRotate(board, h, i);
				grade=heuristit(playerTurn, board, maxLevel);				
				if (grade>bestTurn.getGrade())
				{
					bestTurn.setGrade(grade);
					bestTurn.setSivovH(h);
					bestTurn.setSivovW(i);
				}
				else if ((grade==bestTurn.getGrade())&&(g.nextBoolean()))
				{
					bestTurn.setGrade(grade);
					bestTurn.setSivovH(h);
					bestTurn.setSivovW(i);
				}
				counterClocklWiseRotate(board, h, i);
				counterClocklWiseRotate(board, h, i);			
				grade=heuristit(playerTurn, board, maxLevel);				
				if (grade>bestTurn.getGrade())
				{
					bestTurn.setGrade(grade);
					bestTurn.setSivovH(h);
					bestTurn.setSivovW(i);
				}
				else if ((grade==bestTurn.getGrade())&&(g.nextBoolean()))
				{
					bestTurn.setGrade(grade);
					bestTurn.setSivovH(h);
					bestTurn.setSivovW(i);
				}
				clocklWiseRotate(board, h, i);		
			}
		}
		board[bestTurn.getPutH()][bestTurn.getPutW()].setSide(0);	
		return bestTurn;
	}
	/**
	 * 
	 * @param board ���� �� ����� ����� �� ��� ����� 0 ���� ������� ���� -1 ���� ������� ����� ���� �� ����� ������ �1 ���� ������� ����� ���� �� ���� ����
	 * @param playerTurn ���� �������� ����� ���� ���� ����� ��� ��� ��� ��� �� ����� ����� -1 ����� ������ �1 ����� ����
	 * @return ����� ��� ����� ���������� ����� ������ ��� ��� �� ����� �����   
	 */
	public static final boolean isWin(Square board[][],int playerTurn)
	{
		//�������� ������ ��� ���� ���� ���� ����� ������ ����� ���� ����� ������ ��� ��� �� ����� ������ ���� ���
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
				if (!board[i][h].isBelongToPlayer(playerTurn))
				{
					isW=false;
				}
				if (!board[h][i].isBelongToPlayer(playerTurn))
				{
					isH=false;
				}
				if (!board[i][5-h].isBelongToPlayer(playerTurn))
				{
					isW2=false;
				}
				if (!board[5-h][i].isBelongToPlayer(playerTurn))
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
				if (!board[i][i].isBelongToPlayer(playerTurn))
				{
					d1=false;
				}
				if (!board[5-i][5-i].isBelongToPlayer(playerTurn))
				{
					d2=false;
				}
				if (!board[i][5-i].isBelongToPlayer(playerTurn))
				{
					d3=false;
				}
				if (!board[5-i][i].isBelongToPlayer(playerTurn))
				{
					d4=false;
				}
				if (!board[1+i][i].isBelongToPlayer(playerTurn))
				{
					d5=false;
				}
				if (!board[1+i][5-i].isBelongToPlayer(playerTurn))
				{
					d6=false;
				}
				if (!board[i][4-i].isBelongToPlayer(playerTurn))
				{
					d7=false;
				}
				if (!board[i][1+i].isBelongToPlayer(playerTurn))
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
	private static final boolean isWillWin(int playerTurn,Square board [][])
	{
		//�������� ������ ��� ����� ���� ����� ������ ���� ���� ���� ��� 
		int i;
		for (i=0;i<board.length;i++)
		{
			if ((board[i][0].getSide()+board[i][1].getSide()+board[i][2].getSide()+board[i][3].getSide()+board[i][4].getSide())*playerTurn>=4)
			{
				return true;
			}
			if ((board[i][1].getSide()+board[i][2].getSide()+board[i][3].getSide()+board[i][4].getSide()+board[i][5].getSide())*playerTurn>=4)
			{
				return true;
			}
			if ((board[0][i].getSide()+board[1][i].getSide()+board[2][i].getSide()+board[3][i].getSide()+board[4][i].getSide())*playerTurn>=4)
			{
				return true;
			}	
			if ((board[1][i].getSide()+board[2][i].getSide()+board[3][i].getSide()+board[4][i].getSide()+board[5][i].getSide())*playerTurn>=4)
			{
				return true;
			}	
		}
		if ((board[0][0].getSide()+board[1][1].getSide()+board[2][2].getSide()+board[3][3].getSide()+board[4][4].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][1].getSide()+board[2][2].getSide()+board[3][3].getSide()+board[4][4].getSide()+board[5][5].getSide())*playerTurn>=4)
		{
			return true;
		}		
		if ((board[0][5].getSide()+board[1][4].getSide()+board[2][3].getSide()+board[3][2].getSide()+board[4][1].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][4].getSide()+board[2][3].getSide()+board[3][2].getSide()+board[4][1].getSide()+board[5][0].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][0].getSide()+board[2][1].getSide()+board[3][2].getSide()+board[4][3].getSide()+board[5][4].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[4][0].getSide()+board[3][1].getSide()+board[2][2].getSide()+board[1][3].getSide()+board[0][4].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[1][5].getSide()+board[2][4].getSide()+board[3][3].getSide()+board[4][2].getSide()+board[5][1].getSide())*playerTurn>=4)
		{
			return true;
		}
		if ((board[4][5].getSide()+board[3][4].getSide()+board[2][3].getSide()+board[1][2].getSide()+board[0][1].getSide())*playerTurn>=4)
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
				p2=false;
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
			counterClocklWiseRotate(board, ii, hh);
		}
		else
		{
			clocklWiseRotate(board, ii,hh);
		}
	}	
	/**
	 * �������� ������ �� ���� ���� ��� ������ ��� ����� ������ �� ����� �����
	 * @param board ��� ����� ���������� �����
	 * @param ii ���� ����� �� ���� ��������� �����
	 * @param hh ���� ������ �� ���� ��������� �����
	 */
	public static final void clocklWiseRotate(Square board[][],int ii,int hh)
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
	public static final void counterClocklWiseRotate(Square board[][],int ii,int hh)
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
			if (((board[h][1].getSide()+board[h][2].getSide()+board[h][3].getSide()+board[h][4].getSide())*playerTurn>=2)&&(playerTurn*board[h][1].getSide()>=0)&&(playerTurn*board[h][2].getSide()>=0)&&(playerTurn*board[h][3].getSide()>=0)&&(playerTurn*board[h][4].getSide()>=0))
			{
				return true;
			}
			if (((board[1][h].getSide()+board[2][h].getSide()+board[3][h].getSide()+board[4][h].getSide())*playerTurn>=2)&&(playerTurn*board[1][h].getSide()>=0)&&(playerTurn*board[2][h].getSide()>=0)&&(playerTurn*board[3][h].getSide()>=0)&&(playerTurn*board[4][h].getSide()>=0))
			{
				return true;
			}
		}
		if (((board[1][1].getSide()+board[2][2].getSide()+board[3][3].getSide()+board[4][4].getSide())*playerTurn>=2)&&(playerTurn*board[1][1].getSide()>=0)&&(playerTurn*board[2][2].getSide()>=0)&&(playerTurn*board[3][3].getSide()>=0)&&(playerTurn*board[4][4].getSide()>=0))
		{
			return true;
		}
		if (((board[1][4].getSide()+board[2][3].getSide()+board[3][2].getSide()+board[4][1].getSide())*playerTurn>=2)&&(playerTurn*board[1][4].getSide()>=0)&&(playerTurn*board[2][3].getSide()>=0)&&(playerTurn*board[3][2].getSide()>=0)&&(playerTurn*board[4][1].getSide()>=0))
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
			if ((board[h][0].getSide()!=player*-1)&&(board[h][5].getSide()!=player*-1)&&((board[h][1].getSide()+board[h][2].getSide()+board[h][3].getSide()+board[h][4].getSide())*player>=num))
			{
				return true;
			}
			if ((board[0][h].getSide()!=player*-1)&&(board[5][h].getSide()!=player*-1)&&((board[1][h].getSide()+board[2][h].getSide()+board[3][h].getSide()+board[4][h].getSide())*player>=num))
			{
				return true;
			}
		}
		if ((board[0][0].getSide()!=player*-1)&&(board[5][5].getSide()!=player*-1)&&((board[1][1].getSide()+board[2][2].getSide()+board[3][3].getSide()+board[4][4].getSide())*player>=num))
		{
			return true;
		}
		if ((board[5][0].getSide()!=player*-1)&&(board[0][5].getSide()!=player*-1)&&((board[1][4].getSide()+board[2][3].getSide()+board[3][2].getSide()+board[4][1].getSide())*player>=num))
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
	private static final int heuristit(int playerTurn,Square board[][],int maxLevel)
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
			temp=lineGrade(board[i][0].getSide(), board[i][1].getSide(), board[i][2].getSide(), board[i][3].getSide(), board[i][4].getSide(), playerTurn);
			if (temp!=-1)
			{
				count+=temp;
				if (board[i][5].getSide()*playerTurn>=0)
				{
					count+=lineGrade(board[i][5].getSide(), board[i][1].getSide(), board[i][2].getSide(), board[i][3].getSide(), board[i][4].getSide(), playerTurn);
				}
			}					
			else
			{
				if (!(board[i][0].getSide()*playerTurn>=0))
				{
					temp=lineGrade(board[i][5].getSide(), board[i][1].getSide(), board[i][2].getSide(), board[i][3].getSide(), board[i][4].getSide(), playerTurn);
					if (temp!=-1)
					{
						count+=temp;								
					}
				}
			}
			temp=lineGrade(board[0][i].getSide(), board[1][i].getSide(), board[2][i].getSide(), board[3][i].getSide(), board[4][i].getSide(), playerTurn);
			if (temp!=-1)
			{
				count+=temp;	
				if (board[5][i].getSide()*playerTurn>=0)
				{
					count+=lineGrade(board[5][i].getSide(), board[1][i].getSide(), board[2][i].getSide(), board[3][i].getSide(), board[4][i].getSide(), playerTurn);
				}
			}					
			else
			{
				if (!(board[0][i].getSide()*playerTurn>=0))
				{
					temp=lineGrade(board[5][i].getSide(), board[1][i].getSide(), board[2][i].getSide(), board[3][i].getSide(), board[4][i].getSide(), playerTurn);
					if (temp!=-1)
					{
						count+=temp;								
					}
				}
			}
			
		}
		temp=lineGrade(board[0][0].getSide(), board[1][1].getSide(), board[2][2].getSide(), board[3][3].getSide(), board[4][4].getSide(), playerTurn);
		if (temp!=-1)
		{
			count+=temp;
			if (board[5][5].getSide()*playerTurn>=0)
			{
				count+=lineGrade(board[5][5].getSide(), board[1][1].getSide(), board[2][2].getSide(), board[3][3].getSide(), board[4][4].getSide(), playerTurn);
			}
		}
		else
		{
			if (!(board[0][0].getSide()*playerTurn>=0))
			{
				temp=lineGrade(board[5][5].getSide(), board[1][1].getSide(), board[2][2].getSide(), board[3][3].getSide(), board[4][4].getSide(), playerTurn);
				if (temp!=-1)
				{
					count+=temp;								
				}
			}
		}
		temp=lineGrade(board[0][5].getSide(), board[1][4].getSide(), board[2][3].getSide(), board[3][2].getSide(), board[4][1].getSide(), playerTurn);
		if (temp!=-1)
		{
			count+=temp;
			if (board[5][0].getSide()*playerTurn>=0)
			{
				count+=lineGrade(board[5][0].getSide(), board[1][4].getSide(), board[2][3].getSide(), board[3][2].getSide(), board[4][1].getSide(), playerTurn);
			}
		}
		else
		{
			if (!(board[0][5].getSide()*playerTurn>=0))
			{
				temp=lineGrade(board[5][0].getSide(), board[1][4].getSide(), board[2][3].getSide(), board[3][2].getSide(), board[4][1].getSide(), playerTurn);
				if (temp!=-1)
				{
					count+=temp;								
				}
			}
		}
		temp=lineGrade(board[1][0].getSide(), board[2][1].getSide(), board[3][2].getSide(), board[4][3].getSide(), board[5][4].getSide(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[0][1].getSide(), board[1][2].getSide(), board[2][3].getSide(), board[3][4].getSide(), board[4][5].getSide(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[0][4].getSide(), board[1][3].getSide(), board[2][2].getSide(), board[3][1].getSide(), board[4][0].getSide(), playerTurn);
		if (temp!=-1)	
		{
			count+=temp;
		}		
		temp=lineGrade(board[1][5].getSide(), board[2][4].getSide(), board[3][3].getSide(), board[4][2].getSide(), board[5][1].getSide(), playerTurn);
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
	public static final Turn calcTurns(int playerTurn ,int level,Square board[][],int maxLevel)
	{
		Turn max=new Turn();
		Turn temp2=new Turn();		
		max.setGrade(START_GRADE);
		for (temp2.setPutH(0);temp2.getPutH()<board.length;temp2.setPutH(temp2.getPutH()+1))
		{
			for (temp2.setPutW(0);temp2.getPutW()<board[0].length;temp2.setPutW(temp2.getPutW()+1))			
			{
				if (board[temp2.getPutH()][temp2.getPutW()].getSide()==0)
				{
					for (temp2.setSivovH(0);temp2.getSivovH()<2;temp2.setSivovH(temp2.getSivovH()+1))
					{
						for (temp2.setSivovW(0);temp2.getSivovW()<2;temp2.setSivovW(temp2.getSivovW()+1))
						{
							temp2.setPlusOrMinus(true);		
							doTurn(temp2, board, playerTurn);							
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,max.getGrade()));
							if (temp2.getGrade()>max.getGrade())
							{
								max=new Turn(temp2);
								if (max.getGrade()==100)
								{
									reTurn(temp2,board);
									return max;
								}
							}
							else 
							{
								if (temp2.getGrade()==max.getGrade())
								{
									if (Math.abs(max.getGrade())>=100-2*maxLevel)
									{
										if (max.getHgrade()==Turn.StartHGrade)
										{
											max.setHgrade(heuristit(playerTurn, board, maxLevel));
										}
										temp2.setHgrade(heuristit(playerTurn, board, maxLevel));
										if (temp2.getHgrade()>max.getHgrade())
										{
											max=new Turn(temp2);	
										}
										else
										{
											if ((temp2.getHgrade()==max.getHgrade())&&(g.nextBoolean()))
											{
												max=new Turn(temp2);	
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
							temp2.setPlusOrMinus(false);							
							doTurn(temp2, board, playerTurn);			
							temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,max.getGrade()));
							if (temp2.getGrade()>max.getGrade())
							{
								max=new Turn(temp2);
								if (max.getGrade()==100)
								{
									reTurn(temp2,board);
									return max;
								}
							}
							else 
							{
								if (temp2.getGrade()==max.getGrade())
								{
									if (Math.abs(max.getGrade())>=100-2*maxLevel)
									{
										if (max.getHgrade()==Turn.StartHGrade)
										{
											max.setHgrade(heuristit(playerTurn, board, maxLevel));
										}
										temp2.setHgrade(heuristit(playerTurn, board, maxLevel));
										if (temp2.getHgrade()>max.getHgrade())
										{
											max=new Turn(temp2);	
										}
										else
										{
											if ((temp2.getHgrade()==max.getHgrade())&&(g.nextBoolean()))
											{
												max=new Turn(temp2);	
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
		return max;
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
					int re2=-1*calcTurns2(playerTurn*-1, level-1,board,maxLevel,alfa).getGrade();
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
						re=heuristit(playerTurn,board,maxLevel);	
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
	private static final Turn calcTurns2(int playerTurn ,int level,Square board[][],int maxLevel,int alfa)
	{
		Turn max=new Turn();
		Turn temp2=new Turn();		
		max.setGrade(START_GRADE);
		for (temp2.setPutH(0);temp2.getPutH()<board.length;temp2.setPutH(temp2.getPutH()+1))
		{
			for (temp2.setPutW(0);temp2.getPutW()<board[0].length;temp2.setPutW(temp2.getPutW()+1))
			{
				if (board[temp2.getPutH()][temp2.getPutW()].getSide()==0)
				{
					board[temp2.getPutH()][temp2.getPutW()].setSide(playerTurn);					
					temp2.setGrade(calcBoardGrade(playerTurn, level,board,maxLevel,max.getGrade()));
					if (((alfa>1)&&(-alfa-1<=temp2.getGrade()))||((alfa<-1)&&(-alfa+1<=temp2.getGrade()))||((-alfa<temp2.getGrade())&&(Math.abs(alfa)==1))||((temp2.getGrade()>=0)&&(alfa==0)))
					{
						board[temp2.getPutH()][temp2.getPutW()].setSide(0);		
						return temp2;
					}
					if (temp2.getGrade()>max.getGrade())
					{
						max=new Turn(temp2);
						if (max.getGrade()==100)
						{
							reTurn(temp2,board);
							return max;
						}
					}
					else 
					{
						if (temp2.getGrade()==max.getGrade())
						{
							if (Math.abs(max.getGrade())>=100-2*maxLevel)
							{
								if (max.getHgrade()==Turn.StartHGrade)
								{
									max.setHgrade(heuristit(playerTurn, board, maxLevel));
								}
								temp2.setHgrade(heuristit(playerTurn, board, maxLevel));
								if (temp2.getHgrade()>max.getHgrade())
								{
									max=new Turn(temp2);	
								}
								else
								{
									if ((temp2.getHgrade()==max.getHgrade())&&(g.nextBoolean()))
									{
										max=new Turn(temp2);	
									}
								}											
							}
						}						
					}
//					else if ((temp2.getGrade()==max.getGrade())&&(g.nextBoolean()))
//					{
//						max=new Turn(temp2);	
//					}
					board[temp2.getPutH()][temp2.getPutW()].setSide(0);		
				}						
			}
		}	
		return max;
	}	
	private static final void reTurn(Turn turn,Square board[][])
	{
		rotateArray(board, turn.getSivovH(), turn.getSivovW(),!turn.getPlusOrMinus());
		board[turn.getPutH()][turn.getPutW()].setSide(0);
	}
	private static final void doTurn(Turn turn,Square board[][],int player)
	{
		board[turn.getPutH()][turn.getPutW()].setSide(player);
		rotateArray(board, turn.getSivovH(), turn.getSivovW(),turn.getPlusOrMinus());
	}
}
