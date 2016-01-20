import java.util.Random;
/**
 * המחלקה מאפשר לעשות כל מיני פעולות של בינה מלאכותית ע"י שימוש בפונקציות סטאטיות 
 * @author Noam Wies
 *
 */
public class Brain 
{
	private static final Random g=new Random();
	private static final int  START_GRADE=-10000;
	/**
	 * הפונקציה מחשבת מה המהלך הכי טוב עבור השחן ב2 תורותיו הראשונים
	 * @param playerTurn מציין לפונקציה לאיזה שחקן צריך לחשב את התור -1 לשחקן הראשון ו1 לשחקן השני
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני 
	 * @return הפונקציה מחזירה את התור שהכי טוב לפי דעתה
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
		return bestTurn;//המחשב אף פעם לא יגיע לשורה הזאת בגלל שקוראים לפונקציה רק בשתי התורות הראשונים מה שאומר שיש בלוח מקסימום שלושה טבעות ולכן בטוח אחד הארבעת האמצעיים פנויים ואחז אחד התנאים הקודמים יתקיימו
	}
	/**
	* הפונקציה מחשבת מה הסיבוב הכי טוב עבור השחן ב2 תורותיו הראשונים
	 * @param playerTurn מציין לפונקציה לאיזה שחקן צריך לחשב את התור -1 לשחקן הראשון ו1 לשחקן השני
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני 
	 * @param bestTurn אומר לפונקציה מה הגומה  שהכי משתלם לשים בה את הכדור
	* @param maxLevel הרמה הכי גבוהה שמשוחקת במשחק זה ע"י המחשב
	 * @return הפונקציה מחזירה את המהלך שהכי משתלם לעשות כרגע
	 */
	private  static final Turn firstTwoTurnRotation(int playerTurn,Square board[][],Turn bestTurn,int maxLevel)
	{
		//הפונקציה מחזירה את הסיבוב שהיא חושבת שהוא הכי טוב והיא מיועדת לשימוש בשתי התורות הראשונים בלבד
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
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני
	 * @param playerTurn יומר לפונקציה לאיזה שחקן צריך לבדוק האם הוא יצר רצף של חמישה טבעות -1 לשחקן הראשון ו1 לשחקן השני
	 * @return מחזיר האם השחקן שהפונקצייה קיבלה כפרמטר יצר רצף של חמישה טבעות   
	 */
	public static final boolean isWin(Square board[][],int playerTurn)
	{
		//הפונקציה מחזירה האם במצב הלוח שהיא מקבלת כפרמטר השחקן שהיא מקבלת כפרמטר יצר רצף של חמישה כדורים בצבע שלו
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
		//הפונקציה מחזירה האם השחקן שהיא מקבלת כפרמטר בטוח ינצח בתור הבא 
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
		//הפונקציה מבצעת את הבדיקה הראשונית של המצב בלוח והבדיקה שהיא מבצעת היא האם יש בלוח ניצחון לאחד הצדדים בתור הנוכחי של השחקן שהיא מקבלת כפרמטר ובתור הבא של היריב שלו
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
	 * פונקציה מסובבת את הלוח 
	 * @param board לוח המשחק שהפונקצייה תסובב
	 * @param ii מספר השורה של הלוח שהפונקציה תסובב
	 * @param hh מספר העמודה של הלוח שהפונקציה תסובב
	 * @param clocklWise   האם הסיבוב הוא עם כיוון השעון או נגד כיוון השעון 
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
	 * הפונקציה מסובבת את הלוח הקטן שאת מיקומו היא מקבלת כפרמטר עם כיוון השעון
	 * @param board לוח המשחק שהפונקצייה תסובב
	 * @param ii מספר השורה של הלוח שהפונקציה תסובב
	 * @param hh מספר העמודה של הלוח שהפונקציה תסובב
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
	  * הפונקציה מסובבת את הלוח הקטן שאת מיקומו היא מקבלת כפרמטר נגד כיוון השעון 
	 * @param board לוח המשחק שהפונקצייה תסובב
	 * @param ii מספר השורה של הלוח הקטן שהפונקציה תסובב
	 * @param hh מספר העמודה של הלוח הקטן שהפונקציה תסובב
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
		//אני מניח שהמקסימום התורות קטן מחמישים בגלל שמספר התורות האפשריים בלוח הוא שלושים ושש
		//הפונקציה מבצעת את הבדיקה השניה של המצב בלוח והבדיקה שהיא מבצעת היא האם יש בלוח מלכודת שתבטיח ניצחון לאחד הצדדים בתור הבא של השחקן שהיא מקבלת כפרמטר ובעוד שתי תורות של היריב שלו ואם אין מלכודת אז בדרך אגב הפונקציה מעריכה האם יש בלוח מצב שעוד תור תוכל להיווצר מלכודת כזאת 
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
//		הפונקציה מבצעת בדיקת עזר עבור הפונקציה שמבצעת את הבדיקה השניה של המצב בלוח והבדיקה שהיא מבצעת היא האם יש בלוח אפשרות ליצרת מלכודת עוד תור שתבטיח ניצחון לאחד הצדדים בתור הבא של השחקן שהיא מקבלת כפרמטר ובעוד שתי תורות של היריב שלו ואם אין מלכודת אז בדרך אגב הפונקציה מעריכה האם יש בלוח מצב שעוד תור תוכל להיווצר מלכודת כזאת 
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
		//הפונקציה משמשת כפונקצית עזר לפונקציה הבדיקה השניה כשהיא בודקת האם יש שורה בלוח שהיא מקבלת כפרמטר שבאמצע שלה (לא כולל הקצוות) יש לפחות מספר מסוים של כדורים שהיא מקבלת כפרמטר בצבע של השחקן שהיא מקבלת ושאין בשורה אפילו כדור אחד בצבע של השחקן היריב 
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
	 * פונקציה היוריסטית שמשמשת להערכת מצב הלוח כרגע(פונקציה חמדנית
	 * @param playerTurn מציין לפונקציה לאיזה שחקן צריך לחשב את התור -1 לשחקן הראשון ו1 לשחקן השני
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני 
	 * @param maxLevel הרמה הכי גבוהה שמשוחקת במשחק זה ע"י המחשב
	 * @return הפונקצייה מחזירה את הציון של התור שמסמל את רמת  הכדאיות שלך לעשות תור זה זהפונקצייה היא פונקצייה חמדנית
	 */
	private static final int heuristit(int playerTurn,Square board[][],int maxLevel)
	{
		//פונקציה היוריסטית שמשמשת להערכת מצב הלוח 
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
		//הפונקציה מעריכה את השורה שהיא מקבלת  כפרמטר (צריך שחמשת הגומות יהיו סמוכות אחת לשניה אחרת אין שום הגיון בבדיקה זו) עבור השחקן שהיא מקבלת כפרמטר
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
		//הפונקציה  מעריכה  כמה המצב של השחקן שהיא מקבלת טוב מבחינת כמה חמישיות עתידיות הוא יכול ליצור כשהיא מעריכה את האפשרות ליצירת חמישייה יותר ככל שיש בה יותר כדורים של השחקן כרגע
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
	 * הפונקציה מחשבת את המהלך הכי טוב עבור השחקן 
	 * @param playerTurn יומר לפונקציה לאיזה שחקן צריך לחשב את התור -1 לשחקן הראשון ו1 לשחקן השני
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני 
	 * @param level גובה העץ המקסימלי שהמחשב יבנה במטרה למצוא את המהלך הטוב ביותר
	 * @param maxLevel הרמה הכי גבוהה שמשוחקת במשחק זה ע"י המחשב
	 * @return הפונקציה מחזירה את התור שהכי טוב לפי דעתה
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
	 * הפונקציה מחשבת את המהלך הכי טוב עבור השחקן כשבניגוד לפונקציה האחרת שעושה זו הפונקציה הזאת לא בודקת את כל האפשרויות של הסיבוב 
	 * @param playerTurn מציין לפונקציה לאיזה שחקן צריך לחשב את התור -1 לשחקן הראשון ו1 לשחקן השני
	 * @param level מציין מה הרמה שלפיה ערך כל תור יחושב כאשר זה משפיע כמה עמוק עץ האפשרויות יפרש
	 * @param board מערך דו מימדי שמתאר את מצב המשחק 0 אומר שהמשבצת ריקה -1 אומר שהמשבצת מכילה טבעת של השחקן הראשון ו1 אומר שהמשבצת מכילה טבעת של השחן השני 
	 * @param maxLevel שומר מה הרמה הממוחשבת הגבוהה ביותר שמשוחקת במשחק הנוכחי
	 * @param alfa הערך הכי גבוהה של הרמה הקודמת בעץ
	 * @return התור המומלץ ביותר לפי המחשב
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
