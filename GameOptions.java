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

	private static final long serialVersionUID = 3430552137720449288L;

	GameManager gameManager;
	GraphicsManager graphicsManager;
	JButton newGame, put,help,rules;
	JButton counterClockWiseRotate,clockWiseRotate;
	JButton up,down,left,right;
	JButton player1,player2;	
	final int maxLevel=8;	
	Point3D clickedPoint;
	public GameOptions(GameManager gam,GraphicsManager grm, String name)
	{
		super(name);
		this.gameManager=gam;	
		this.graphicsManager=grm;
		
		help= new JButton("Help");
		help.addActionListener(this);
		add(help); 
		
		rules= new JButton("Rules");
		rules.addActionListener(this);
		add(rules); 	
		
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
		
		put = new JButton("Put");
		put.addActionListener(this);
		put.setEnabled(false);
		add(put);

		String rotateLeftPath = "/Images/rotateFixLeft.GIF";
		counterClockWiseRotate= new JButton(new ImageIcon(getClass().getResource(rotateLeftPath)));
		counterClockWiseRotate.addActionListener(this);
		counterClockWiseRotate.setEnabled(false);
		add(counterClockWiseRotate);

		String rotateRightPath = "/Images/rotateFixRight.GIF";
		clockWiseRotate= new JButton(new ImageIcon(getClass().getResource(rotateRightPath)));
		clockWiseRotate.addActionListener(this);
		clockWiseRotate.setEnabled(false);
		add(clockWiseRotate);
		
		addSeparator() ;

		String leftPath = "/Images/left.GIF";
		left= new JButton(new ImageIcon(getClass().getResource(leftPath)));
		left.addActionListener(this);	
		left.setEnabled(false);
		add(left);

		String rightPath = "/Images/right.GIF";
		right= new JButton(new ImageIcon(getClass().getResource(rightPath)));
		right.addActionListener(this);	
		right.setEnabled(false);
		add(right);

		String upPath = "/Images/up.GIF";
		up= new JButton(new ImageIcon(getClass().getResource(upPath)));
		up.addActionListener(this);	
		up.setEnabled(false);
		add(up);

		String downPath = "/Images/down.GIF";
		down= new JButton(new ImageIcon(getClass().getResource(downPath)));
		down.addActionListener(this);	
		down.setEnabled(false);
		add(down);	
		
		addSeparator() ;	
		
		
		grm.addMouseListener(this);
		
		clickedPoint=new Point3D(0,0,0);
	}
	/*
	 * TODO Display the GUI's menus with heuristics names instead of numbers
	 */
	public void actionPerformed (ActionEvent event)
	{
		JButton now=(JButton)event.getSource();		
		if (now==player1)
		{
			String agentTypeOptions[]=new String[maxLevel+2];
			agentTypeOptions[0]=" Human";
			agentTypeOptions[1] = "Alpha Beta";
			for (int i=2;i<agentTypeOptions.length;i++)
			{
				agentTypeOptions[i]=" "+i;
			}		
			gameManager.nextType[0]=(JOptionPane.showOptionDialog(null,"Choose Player 1 Agent","Player 1", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION,new ImageIcon("Images\\board.GIF") , agentTypeOptions, agentTypeOptions[0]));
		}
		else if (now==player2)
		{
			String agentTypeOptions[]=new String[maxLevel+2];
			agentTypeOptions[0]=" Human";
			agentTypeOptions[1] = " Alpha Beta";
			for (int i=2;i<agentTypeOptions.length;i++)
			{
				agentTypeOptions[i]=" "+i;
			}		
		    gameManager.nextType[1]=(JOptionPane.showOptionDialog(null,"Choose Player 2 Agent","Player 2", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION,new ImageIcon("Images\\board.GIF") , agentTypeOptions, agentTypeOptions[0]));
		}
		else if (now==newGame)
		{
			if (graphicsManager.isInTheard)
			{
//				graphicsOptions.setEnabled2(true);
				graphicsManager.isWaitToNewGame=true;				
			}
			else
			{
				graphicsManager.newGame();
				put.setEnabled(true);
				clockWiseRotate.setEnabled(false);
				counterClockWiseRotate.setEnabled(false);
				right.setEnabled(true);
				left.setEnabled(true);
				up.setEnabled(true);
				down.setEnabled(true);
//				graphicsOptions.setEnabled2(true);
			}			
		}
		else if (now==help)
		{
			graphicsManager.help();
		}
		else if (now==rules)
		{
			graphicsManager.rules();
		}
		else if (now==clockWiseRotate || now==counterClockWiseRotate)
		{
			boolean isClockwise = now==clockWiseRotate;
			if ((gameManager.isPlaying)&&(!gameManager.isFinal))
			{
				if (gameManager.isPlacing && !gameManager.isRotating && gameManager.currPlayers[gameManager.playerIndex]==null )
				{
					put.setEnabled(true);
					clockWiseRotate.setEnabled(false);
					counterClockWiseRotate.setEnabled(false);
					gameManager.rotate(isClockwise);
				}
			}	
		}
		else if (now== put)
		{
			
			if ((gameManager.isPlaying)&&(!gameManager.isFinal))
			{
				if ((!gameManager.isPlacing)&&(((gameManager.currPlayers[0]==null)&&(gameManager.playerIndex<0))||((gameManager.currPlayers[1]==null)&&(gameManager.playerIndex>0))))					
				{
					if (gameManager.board[graphicsManager.numSquareH][graphicsManager.numSquareW].getColor()==0)
					{
						put.setEnabled(false);
						clockWiseRotate.setEnabled(true);
						counterClockWiseRotate.setEnabled(true);
						gameManager.place();
					}
				}
			}				
		}
		else if (now==up)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPlacing)
				{
					if (graphicsManager.numBoardH>0)
					{
						graphicsManager.numBoardH--;
					}
				}
				else
				{
					if (graphicsManager.numSquareH>0)
					{
						graphicsManager.numSquareH--;
					}
				}
				graphicsManager.repaint();
			}
			
		}
		else if (now==down)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPlacing)
				{
					if (graphicsManager.numBoardH<graphicsManager.sBoard.length-1)
					{
						graphicsManager.numBoardH++;
					}
				}
				else
				{
					if (graphicsManager.numSquareH<gameManager.board.length-1)
					{
						graphicsManager.numSquareH++;
					}
				}
				graphicsManager.repaint();
			}
			
			
		}
		else if (now==left)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPlacing)
				{
					if (graphicsManager.numBoardW>0)
					{
						graphicsManager.numBoardW--;
					}
				}
				else
				{
					if (graphicsManager.numSquareW>0)
					{
						graphicsManager.numSquareW--;
					}
				}
				graphicsManager.repaint();
			}
			
		}		
		else if (now==right)
		{
			if (!gameManager.isFinal)
			{
				if(gameManager.isPlacing)
				{
					if (graphicsManager.numBoardW<graphicsManager.sBoard[graphicsManager.numBoardH].length-1)
					{
						graphicsManager.numBoardW++;					
					}
				}
				else
				{
					if (graphicsManager.numSquareW<gameManager.board[graphicsManager.numSquareH].length-1)
					{
						graphicsManager.numSquareW++;					
					}
				}
				graphicsManager.repaint();
			}
			
		}
		
	}
	public void mouseClicked(MouseEvent e) 
	{
		if ((gameManager.isPlaying)&&(!gameManager.isFinal))
		{
			if (gameManager.currPlayers[gameManager.playerIndex]==null)
			{
				clickedPoint.setXYZ(e.getX(),e.getY(),0);
				clickedPoint.calculateZ(graphicsManager.inverseLook, graphicsManager.board.z);

				clickedPoint.mullMat(graphicsManager.inverseLook);
				int i,h;
				if (!gameManager.isPlacing)
				{
					for (h=0;(h<gameManager.board.length+1)&&(clickedPoint.y>graphicsManager.board.y+(h-3)*(graphicsManager.depth/gameManager.board.length));h++)
					{
						
					}
					for (i=0;(i<gameManager.board[0].length+1)&&(clickedPoint.x>graphicsManager.board.x+(i-3)*graphicsManager.depth/gameManager.board[0].length);i++)
					{
						
					}
					i--;
					h--;
					if ((i<6)&&(h<6)&&(i>=0)&&(h>=0))
					{
						graphicsManager.numSquareH=h;
						graphicsManager.numSquareW=i;
						if (gameManager.board[graphicsManager.numSquareH][graphicsManager.numSquareW].getColor()==0)
						{
							put.setEnabled(false);
							clockWiseRotate.setEnabled(true);
							counterClockWiseRotate.setEnabled(true);
							gameManager.place();
						}
					}
				}
				else
				{
					if (!gameManager.isRotating)
					{
						for (h=0;(h<3)&&(clickedPoint.y>graphicsManager.board.y+(h-1)*(graphicsManager.depth/2));h++)
						{
							
						}
						for (i=0;(i<3)&&(clickedPoint.x>graphicsManager.board.x+(i-1)*graphicsManager.depth/2);i++)
						{
							
						}
						i--;
						h--;
						if ((i<2)&&(h<2)&&(i>=0)&&(h>=0))
						{
							graphicsManager.numBoardH=h;
							graphicsManager.numBoardW=i;
							put.setEnabled(true);
							clockWiseRotate.setEnabled(false);
							counterClockWiseRotate.setEnabled(false);
							gameManager.rotate(e.getButton()!=MouseEvent.BUTTON1);						
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
