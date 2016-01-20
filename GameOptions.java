import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
public class GameOptions extends JToolBar implements ActionListener ,MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3430552137720449288L;

	GameManager gameManager;
	GraphicsManager graphicsManager;
	GraphicsOptions graphicsOptions;
	JButton newGame,put,help,rule;
	JButton counterClocklWiseRotate,clocklWiseRotate;
	JButton up,down,left,right;
	JButton player1,player2;	
	final int maxLevel=8;	
	Point3D clickedPoint;
	public GameOptions(GameManager gam,GraphicsManager grm,GraphicsOptions gro,String name)	
	{
		super(name);
		this.gameManager=gam;	
		this.graphicsManager=grm;
		this.graphicsOptions=gro;
		
		help= new JButton("Help");
		help.addActionListener(this);
		add(help); 
		
		rule= new JButton("Rule");
		rule.addActionListener(this);
		add(rule); 	
		
		newGame= new JButton("New Game");
		newGame.addActionListener(this);
		add(newGame); 	
		
		player1= new JButton("Player 1");
		player1.addActionListener(this);
		add(player1); 	
		
		player2= new JButton("Player 2");
		player2.addActionListener(this);
		add(player2); 	
		
				
		addSeparator() ;
		
		put= new JButton("put");
		put.addActionListener(this);	
		put.setEnabled(false);
		add(put);
		
		counterClocklWiseRotate= new JButton(new ImageIcon("Images\\rotateFixLeft.GIF"));
		counterClocklWiseRotate.addActionListener(this);
		counterClocklWiseRotate.setEnabled(false);
		add(counterClocklWiseRotate); 
		
		clocklWiseRotate= new JButton(new ImageIcon("Images\\rotateFixRight.GIF"));
		clocklWiseRotate.addActionListener(this);
		clocklWiseRotate.setEnabled(false);
		add(clocklWiseRotate);
		
		addSeparator() ;
		
		left= new JButton(new ImageIcon("Images\\left.GIF"));		
		left.addActionListener(this);	
		left.setEnabled(false);
		add(left);

		right= new JButton(new ImageIcon("Images\\right.GIF"));
		right.addActionListener(this);	
		right.setEnabled(false);
		add(right);
		
		up= new JButton(new ImageIcon("images\\up.GIF"));
		up.addActionListener(this);	
		up.setEnabled(false);
		add(up);

		down= new JButton(new ImageIcon("Images\\down.GIF"));
		down.addActionListener(this);	
		down.setEnabled(false);
		add(down);	
		
		addSeparator() ;	
		
		
		grm.addMouseListener(this);
		
		clickedPoint=new Point3D(0,0,0);
	}	
	public void actionPerformed (ActionEvent event)
	{
		JButton now=(JButton)event.getSource();		
		if (now==player1)
		{
			String o[]=new String[maxLevel+1];
			o[0]=" human";
			for (int i=1;i<o.length;i++)
			{
				o[i]=" "+i;
			}		
			gameManager.nextCum[0]=(JOptionPane.showOptionDialog(null,"בחר את סוג השחקן הראשון","Pentago", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION,new ImageIcon("Images\\borad.GIF") , o, o[0]));
		}		
		else if (now==player2)
		{
			String o[]=new String[maxLevel+1];
			o[0]=" human";
			for (int i=1;i<o.length;i++)
			{
				o[i]=" "+i;
			}		
		    gameManager.nextCum[1]=(JOptionPane.showOptionDialog(null,"בחר את סוג השחקן השני","Pentago", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION,new ImageIcon("Images\\borad.GIF") , o, o[0]));	
		}
		else if (now==newGame)
		{
			if (graphicsManager.isInTheard)
			{
				graphicsOptions.setEnabled2(true);
				graphicsManager.isWaitToNewGame=true;				
			}
			else
			{
				graphicsManager.newGame();
				put.setEnabled(true);
				clocklWiseRotate.setEnabled(false);
				counterClocklWiseRotate.setEnabled(false);
				right.setEnabled(true);
				left.setEnabled(true);
				up.setEnabled(true);
				down.setEnabled(true);
				graphicsOptions.setEnabled2(true);
			}			
		}
		else if (now==help)
		{
			graphicsManager.help();
		}
		else if (now==rule)
		{
			graphicsManager.rule();
		}
		else if (now==counterClocklWiseRotate)
		{
			if ((gameManager.isPlay)&&(!gameManager.isFinal))
			{
				if ((gameManager.isPut)&&(!gameManager.isRotate)&&(((gameManager.cum[0]==0)&&(gameManager.playerTurn<0))||((gameManager.cum[1]==0)&&(gameManager.playerTurn>0))))
				{
					put.setEnabled(true);				
					clocklWiseRotate.setEnabled(false);
					counterClocklWiseRotate.setEnabled(false);
					gameManager.counterClocklWiseRotate();
				}
			}			
		}
		else if (now==clocklWiseRotate)
		{
			if ((gameManager.isPlay)&&(!gameManager.isFinal))
			{
				if ((gameManager.isPut)&&(!gameManager.isRotate)&&(((gameManager.cum[0]==0)&&(gameManager.playerTurn<0))||((gameManager.cum[1]==0)&&(gameManager.playerTurn>0))))
				{
					put.setEnabled(true);
					clocklWiseRotate.setEnabled(false);
					counterClocklWiseRotate.setEnabled(false);
					gameManager.clocklWiseRotate();
				}
			}	
		}
		else if (now==put)
		{
			
			if ((gameManager.isPlay)&&(!gameManager.isFinal))
			{
				if ((!gameManager.isPut)&&(((gameManager.cum[0]==0)&&(gameManager.playerTurn<0))||((gameManager.cum[1]==0)&&(gameManager.playerTurn>0))))					
				{
					if (gameManager.balls[graphicsManager.numSqureH][graphicsManager.numSqureW].getSide()==0)
					{
						put.setEnabled(false);
						clocklWiseRotate.setEnabled(true);
						counterClocklWiseRotate.setEnabled(true);
						gameManager.put();
					}
				}
			}				
		}
		else if (now==up)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPut)
				{
					if (graphicsManager.numBoradH>0)
					{
						graphicsManager.numBoradH--;
					}
				}
				else
				{
					if (graphicsManager.numSqureH>0)
					{
						graphicsManager.numSqureH--;
					}
				}
				graphicsManager.repaint();
			}
			
		}
		else if (now==down)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPut)
				{
					if (graphicsManager.numBoradH<graphicsManager.sBorad.length-1)
					{
						graphicsManager.numBoradH++;
					}
				}
				else
				{
					if (graphicsManager.numSqureH<gameManager.balls.length-1)
					{
						graphicsManager.numSqureH++;
					}
				}
				graphicsManager.repaint();
			}
			
			
		}
		else if (now==left)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPut)
				{
					if (graphicsManager.numBoradW>0)
					{
						graphicsManager.numBoradW--;
					}
				}
				else
				{
					if (graphicsManager.numSqureW>0)
					{
						graphicsManager.numSqureW--;
					}
				}
				graphicsManager.repaint();
			}
			
		}		
		else if (now==right)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPut)
				{
					if (graphicsManager.numBoradW<graphicsManager.sBorad[graphicsManager.numBoradH].length-1)
					{
						graphicsManager.numBoradW++;					
					}
				}
				else
				{
					if (graphicsManager.numSqureW<gameManager.balls[graphicsManager.numSqureH].length-1)
					{
						graphicsManager.numSqureW++;					
					}
				}
				graphicsManager.repaint();
			}
			
		}
		
	}
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		if ((gameManager.isPlay)&&(!gameManager.isFinal))
		{
			if (((gameManager.cum[0]==0)&&(gameManager.playerTurn<0))||((gameManager.cum[1]==0)&&(gameManager.playerTurn>0)))
			{
				clickedPoint.setXYZ(e.getX(),e.getY(),0);
				if(graphicsManager.isPerspectiv())
				{
					clickedPoint.calculateZ(graphicsManager.inverseLook, graphicsManager.borad.z,graphicsManager.depthPersective,graphicsManager.prespctivCenter.x,graphicsManager.prespctivCenter.y);
				}
				else
				{
					clickedPoint.calculateZ(graphicsManager.inverseLook, graphicsManager.borad.z);
				}		
				clickedPoint.mullMat(graphicsManager.inverseLook);
				int i,h;
				if (!gameManager.isPut)
				{
					for (h=0;(h<gameManager.balls.length+1)&&(clickedPoint.y>graphicsManager.borad.y+(h-3)*(graphicsManager.depth/gameManager.balls.length));h++)
					{
						
					}
					for (i=0;(i<gameManager.balls[0].length+1)&&(clickedPoint.x>graphicsManager.borad.x+(i-3)*graphicsManager.depth/gameManager.balls[0].length);i++)
					{
						
					}
					i--;
					h--;
					if ((i<6)&&(h<6)&&(i>=0)&&(h>=0))
					{
						graphicsManager.numSqureH=h;
						graphicsManager.numSqureW=i;
						if (gameManager.balls[graphicsManager.numSqureH][graphicsManager.numSqureW].getSide()==0)
						{
							put.setEnabled(false);
							clocklWiseRotate.setEnabled(true);
							counterClocklWiseRotate.setEnabled(true);
							gameManager.put();
						}
					}
				}
				else
				{
					if (!gameManager.isRotate)
					{
						for (h=0;(h<3)&&(clickedPoint.y>graphicsManager.borad.y+(h-1)*(graphicsManager.depth/2));h++)
						{
							
						}
						for (i=0;(i<3)&&(clickedPoint.x>graphicsManager.borad.x+(i-1)*graphicsManager.depth/2);i++)
						{
							
						}
						i--;
						h--;
						if ((i<2)&&(h<2)&&(i>=0)&&(h>=0))
						{
							graphicsManager.numBoradH=h;
							graphicsManager.numBoradW=i;
							put.setEnabled(true);
							clocklWiseRotate.setEnabled(false);
							counterClocklWiseRotate.setEnabled(false);
							if (e.getButton()==MouseEvent.BUTTON1)
							{
								gameManager.counterClocklWiseRotate();
							}
							else
							{
								gameManager.clocklWiseRotate();
							}						
						}
					}					
				}
			}
		}		
	}
	public void mouseEntered(MouseEvent e) 
	{
				
	}
	public void mouseExited(MouseEvent e) 
	{
			
	}
	public void mousePressed(MouseEvent e) 
	{
		
	}
	public void mouseReleased(MouseEvent e) 
	{
		
	}
}
