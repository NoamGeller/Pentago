import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class GraphicsManager extends JPanel
{
	private static final long serialVersionUID = 291981520296507497L;
	GameManager gameManager;
	WinAnimation winTheard;
	Thread rotateThread; //rotating process for board animation
	Matrix3D look=new Matrix3D(); //perspective matrix
	Matrix3D change=new Matrix3D();	//matrix for current change
	Matrix3D inverseLook=new Matrix3D(); //inverse matrix of the perspective matrix
	Matrix3D inverseChange=new Matrix3D();	//inverse matrix of the current change matrix
	Matrix3D mat1=new Matrix3D(); //helper matrix for the graphic representation
	Matrix3D mat2=new Matrix3D(); //helper matrix for the graphic representation
	Galil body; //body of the base
	Body body1; //the basis part from which the small boards are made up, each board being made up of 36 of these parts
	Shape2D shape; //used to make the frames around the small spaces/boards, each frame is made up of 4 of these
	Point3D sBoard[][],board; //the central points of the big board and the small boards
	int xtext,ytext; //used to determine the top corner of the end message of the game
	int numBoardH,numBoardW,numSquareH,numSquareW,depth,zlength;
	Color c1,c2,c3,c4,c5,c6,c7,c8;
	double a[][];	
	boolean isWaitToNewGame,isInTheard,isBright,showLight;

	Point3D p00=new Point3D(0,0,0);
	Point3D lightPoint=new Point3D(0,0,0);
	Point3D p0=new Point3D(0,0,0);
	Point3D p1=new Point3D(0,0,0);
	Point3D p2=new Point3D(0,0,0);
	Point3D p3=new Point3D(0,0,0);
	GameManager.Winner w;
	public GraphicsManager(GameManager gm)
	{
		super();
		this.gameManager=gm;
		winTheard=new WinAnimation(this,false);
		body=new Galil(4);
	   	body1=new Body(6);	   
	   	shape=new Shape2D(4);
	   	showLight=false;
	   	depth=400;
	   	zlength=5;
		sBoard=new Point3D[2][2];
	   	board=new Point3D(0,0,0);
	   	p00=new Point3D(600,300,1000);
	   	sBoard[0][0]=new Point3D(0,0,0);
		sBoard[1][0]=new Point3D(0,0,0);
		sBoard[0][1]=new Point3D(0,0,0);
		sBoard[1][1]=new Point3D(0,0,0);
	   	a=new double[sBoard.length][sBoard[0].length];
	   	c1=Color.green;
		c2=Color.white;
		c3=Color.black;
		c4=new Color(118,213,223); //frames around moves
	//	c4 = new Color(175,175,175);
		c5=new Color(115,115,115);	//circle spots on board
		c6=new Color(170,4,4);   //board color
		c7=Color.RED;	 
		c8=new Color(45,56,148);
		isBright=true;
		setBackground(Color.DARK_GRAY); //background
		repaint();
	}
	public void newGame()
	{
		int i,h;
		winTheard.stopMe();
		board.setXYZ(p00);
		lightPoint.setXYZ(600,250,1000);
		sBoard[1][1].setXYZ(board.x+depth/4,board.y+depth/4,board.z-zlength);
		sBoard[0][1].setXYZ(board.x+depth/4,board.y-depth/4,board.z-zlength);
		sBoard[1][0].setXYZ(board.x-depth/4,board.y+depth/4,board.z-zlength);
		sBoard[0][0].setXYZ(board.x-depth/4,board.y-depth/4,board.z-zlength);
		numBoardH=0;
		numBoardW=0;
		numSquareH=0;
		numSquareW=0;
		look.setIdentity();
		inverseLook.setIdentity();
		for(i=0;i<sBoard.length;i++)
		{
			for(h=0;h<sBoard[0].length;h++)
			{
				a[i][h]=0;
			}
		}
//		JFrame f=new JFrame();		
//		setBackground(JColorChooser.showDialog(f, "board", getBackground()));
//		c1=JColorChooser.showDialog(f,"background", c1);
//		c5=JColorChooser.showDialog(f,"big board", c5);
//		c6=JColorChooser.showDialog(null,"small boards", c6);
//		c7=JColorChooser.showDialog(f,"win sign", c7);
//		c4=JColorChooser.showDialog(f,"sign of the board", c4);
//		c2=JColorChooser.showDialog(f," player1", c2);
//		c3=JColorChooser.showDialog(f,"player2", c3);
//		c8=JColorChooser.showDialog(null,"depth of the ball", c8);
//		f.hide();		
//		p1.setXYZ(100,1100,100);
//		p2.setXYZ(1100,1100,1100);
//	    p0.setXYZ(100,1100,1100);
//	    p3.setXYZ(100,100,1100);
	    
	    p1.setXYZ(p00.x-100,p00.y+100,p00.z-100);
	    p2.setXYZ(p00.x+100,p00.y+100,p00.z+100);
	    p0.setXYZ(p00.x-100,p00.y+100,p00.z+100);
	    p3.setXYZ(p00.x-100,p00.y-100,p00.z+100);
	    
	    isInTheard=false;
	    gameManager.newGame();
		repaint();
		
	}
	private void rotateAxis( double x1, double y1,double z1,double x2, double y2,double z2, double teta)
    {
		change.setMatRotateAxis(x1, y1, z1, x2, y2, z2, teta);
		inverseChange.setMatRotateAxis(x1, y1, z1, x2, y2, z2, -teta);
		prepareToShowAndRepaint();
	}	
	private void move(int dx, int dy, int dz)
	{
		change.setMatMove(dx,dy,dz);
		inverseChange.setMatMove(-dx,-dy,-dz);
		prepareToShowAndRepaint();
	}
	private void moveLightPoint(int dx, int dy, int dz)
	{
		lightPoint.plus(dx, dy, dz);
		repaint();
	}
	private void silum(double sX, double sY, double sZ,double fx,double fy,double fz)
	{
		change.setMatSilumFix(sX, sY, sZ, fx, fy, fz);
		inverseChange.setMatSilumFix(1/sX, 1/sY, 1/sZ, fx, fy, fz);
		prepareToShowAndRepaint();
	}
	public void smaller()
	{
		silum(0.9, 0.9, 0.9,board.x,board.y,board.z);
	}
	public void bigger()
	{
		silum(1.1, 1.1, 1.1,board.x,board.y,board.z);
	}
	private void rotateFixZ(double a,double fx,double fy,double fz)
	{
		change.setMatRotateZFix(a, fx, fy, fz);
		inverseChange.setMatRotateZFix(-a, fx, fy, fz);
		prepareToShowAndRepaint();
	}
	private void rotateZ(double a)
	{
		change.setMatRotateZ(a);
		inverseChange.setMatRotateZ(-a);
		prepareToShowAndRepaint();
	}
	private void rotateFixX(double a,double fx,double fy,double fz)
	{
		change.setMatRotateXFix(a, fx, fy, fz);			
		inverseChange.setMatRotateXFix(-a, fx, fy, fz);			
		prepareToShowAndRepaint();
	}
	private void rotateFixY(double a,double fx,double fy,double fz)
	{
		change.setMatRotateYFix(a, fx, fy, fz);
		inverseChange.setMatRotateYFix(-a, fx, fy, fz);
		prepareToShowAndRepaint();
	}
	public void rotateXFixDown()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixX(Math.PI/20,temp.x,temp.y,temp.z);
	}
	public void rotateXFixUp()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixX(-1*Math.PI/20,temp.x,temp.y,temp.z);
	}
	public void rotateYFixRight()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixY(-1*Math.PI/20,temp.x,temp.y,temp.z);
	}		
	public void rotateYFixLeft()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixY(Math.PI/20,temp.x,temp.y,temp.z);
	}
	public void rotateZFixFor()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixZ(Math.PI/20,temp.x,temp.y,temp.z);
	}
	public void rotateZFixBack()
	{
		Point3D temp=new Point3D(board);
		temp.mullMat(look);
		rotateFixZ(-1*Math.PI/20,temp.x,temp.y,temp.z);
		
	}
	public void rotateZFor()
	{
		rotateZ(Math.PI/20);
	}
	public void rotateZBack()
	{
		rotateZ(-1*Math.PI/20);
	}
	public void moveLeft() 
	{
		move(-50,0,0);
	}

	public void moveRight() 
	{
		move(50,0,0);
	}
	public void moveUp() 
	{
		move(0,-50,0);
	}

	public void moveDown() 
	{
		move(0,50,0);
	} 
    
	public void moveForward() 
	{
		move(0,0,-50);			
	}

	
	public void moveBackward() 
	{
		move(0,0,50);		
		
	}
	public void moveLeftLightPoint() 
	{
		moveLightPoint(-50,0,0);
	}

	public void moveRightLightPoint() 
	{
		moveLightPoint(50,0,0);
	}
	public void moveUpLightPoint() 
	{
		moveLightPoint(0,-50,0);
	}

	public void moveDownLightPoint() 
	{
		moveLightPoint(0,50,0);
	} 
    
	public void moveForwardLightPoint() 
	{
		moveLightPoint(0,0,-50);			
	}

	
	public void moveBackwardLightPoint() 
	{
		moveLightPoint(0,0,50);		
		
	}
	public void rotateAxisLeft()
	{
		Point3D temp=new Point3D(board.x-depth/2,board.y-depth/2,board.z+zlength*2);
		temp.mullMat(look);
		Point3D temp2=new Point3D(board.x+depth/2,board.y+depth/2,board.z);
		temp2.mullMat(look);
		rotateAxis(temp.x, temp.y, temp.z, temp2.x, temp2.y, temp2.z,-1*Math.PI/20);
	}
	public void rotateAxisRight()
	{
		Point3D temp=new Point3D(board.x-depth/2,board.y-depth/2,board.z+zlength*2);
		temp.mullMat(look);
		Point3D temp2=new Point3D(board.x+depth/2,board.y+depth/2,board.z);
		temp2.mullMat(look);
		rotateAxis(temp.x, temp.y, temp.z, temp2.x, temp2.y, temp2.z,Math.PI/20);
	}
	private void prepareToShowAndRepaint()
	{
		look.mullMatMat(change);	
		inverseChange.mullMatMat(inverseLook);	
		inverseLook=inverseChange;
		inverseChange=new Matrix3D();
		p0.mullMat(change);
		p1.mullMat(change);
		p2.mullMat(change);
		p3.mullMat(change);			
		repaint();
	}	
	private  double getZ(double x1,double y1,
            double x2,double y2,
            double x3,double y3)
{
//		if(perspective)
//		{
//			return (int)x1*( (int)y2- (int)y3)+ (int)x2*( (int)y3- (int)y1)+ (int)x3*( (int)y1- (int)y2);
//		}
//		else
//		{
			return x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2);
//		}

}
	public void rotateBoard(boolean isClockwise)
	{
		rotateThread = new RotateAnimation(this,numBoardH,numBoardW,isClockwise);
	}	
	public void paintComponent (Graphics page)
	{
		super.paintComponent(page);			
		if (gameManager.isPlaying)
		{
			int i,h;			
			int stat=8;
			double normal1=getZ(p3.x, p3.y, p0.x, p0.y, p2.x, p2.y);
			double normal2=getZ(p3.x, p3.y, p0.x, p0.y, p1.x, p1.y);
			double normal3=getZ(p2.x, p2.y, p1.x, p1.y, p0.x, p0.y);
			if (normal1<0)
			{
				if (normal2<0)
				{
					if (normal3<0)
					{
						stat=7;
					}
					else
					{
						stat=3;
					}
				}
				else
				{
					if (normal3<0)
					{
						stat=4;
					}
					else
					{
						stat=0;
					}
				}
			}
			else if (normal1>0)
			{
				if (normal2<0)
				{
					if (normal3<0)
					{
						stat=6;
					}
					else
					{
						stat=2;
					}
				}
				else
				{
					if (normal3<0)
					{
						stat=5;
					}
					else
					{
						stat=1;
					}
				}
			}
			else if (normal1==0)
			{
				if (normal2<0)
				{
					if (normal3<0)
					{
						stat=7;
					}
					else
					{
						stat=3;
					}
				}
				else
				{
					if (normal2==0)
					{
						if (normal3<0)
						{
							stat=4;
						}
						else
						{
							stat=3;
						}
					}
					else
					{
						if (normal3<0)
						{
							stat=4;
						}
						else
						{
							stat=0;
						}
					}
				}
			}
			if (stat==4)
			{
				paintBasis(page);
				for (i=0;i<2;i++)
				{
					for (h=0;h<2;h++)
					{
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);							
						}
					}
				}
			}
			else if (stat==0)
			{
				paintBasis(page);
				for (i=0;i<2;i++)
				{
					for (h=1;h>=0;h--)
					{
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
					}
				}
			}
			else if (stat==3)
			{
				paintBasis(page);
				for (i=1;i>=0;i--)
				{
					for (h=1;h>=0;h--)
					{
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
					}
				}
			}
			else if (stat==7)
			{
				paintBasis(page);
				for (i=1;i>=0;i--)
				{
					for (h=0;h<2;h++)
					{
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
					}
				}
			}
			else if (stat==5)
			{
				for (i=0;i<2;i++)
				{
					for (h=0;h<2;h++)
					{
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
						
					}
				}
				paintBasis(page);
			}
			else if (stat==1)
			{
				for (i=0;i<2;i++)
				{
					for (h=1;h>=0;h--)
					{
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
										
					}
				}
				paintBasis(page);
			}
			else if (stat==2)
			{
				for (i=1;i>=0;i--)
				{
					for (h=1;h>=0;h--)
					{
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
					}
				}
				paintBasis(page);
			}
			else if (stat==6)
			{
				for (i=1;i>=0;i--)
				{
					for (h=0;h<2;h++)
					{
						if ((gameManager.isPlacing)&&(numBoardH==h)&&(numBoardW==i))
						{
							paintFrame(page, (int)((depth/4)*Math.sqrt(2)), sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z,a[h][i],c4);	
							
						}
						paintBoardA(page, stat, depth/2, zlength, sBoard[h][i].x, sBoard[h][i].y, sBoard[h][i].z, smallArray(h, i), a[h][i],(!gameManager.isPlacing)&&(numSquareH/3==h)&&(numSquareW/3==i));						
					}
				}
				paintBasis(page);
			}
			if (gameManager.isFinal)
			{
				page.setColor(c7);
				page.setFont(new Font("Arial Bold",Font.BOLD,40));
				if ((w==GameManager.Winner.BOTH)||(w==GameManager.Winner.NONE))
				{
					page.drawString("DRAW", xtext, ytext);
				}
				else
				{
					if (w==GameManager.Winner.FIRST)
					{
						page.drawString("Player 1 wins!",  xtext, ytext);
					}
					else
					{
						page.drawString("Player 2 wins!",  xtext, ytext);
					}
				}
//				if (Brain.isWin(gm.balls,-1))
//				{
//					if (Brain.isWin(gm.balls,1))
//					{
//						page.drawString("draw", xtext, ytext);
//					}
//					else
//					{
//						page.drawString("Player 1 win",  xtext, ytext);
//					}
//				}
//				else
//				{
//					if (Brain.isWin(gm.balls,1))
//					{
//						page.drawString("Player 2 win",  xtext, ytext);
//					}
//					else
//					{
//						page.drawString("draw", xtext, ytext);
//					}
//				}
			}
			
		}
		else
		{
			page.drawImage(new ImageIcon("Images\\board3.GIF").getImage(), getWidth()/4, getHeight()/8,this);
		}
		if (showLight)
		{
			lightPoint.show(page);
		}
	}
	public void paintBasis (Graphics page)
	{
		body.createPoints(board.x, board.y, board.z,(int)(depth/2/Math.sin(Math.PI/4)),zlength,-1*Math.PI/4);
		body.mullMat(look);
		body.biuldGalilFromPoints();
		body.convertAndShow(page,c5);
	}
	public void paintFrame (Graphics page,int length,double mx,double my,double mz,double rotate,Color c)
	{
		final double width=0.05;
		shape.createPoints(mx, my, mz, length);
		mat1.setMatSilumFix(1.0, width, 1.0,mx,my, mz);
		mat2.setMatMove(0,((1-width)*length)/Math.sqrt(2), 0);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(rotate, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat1.mullMatMat(look);
		shape.mullMat(mat1);
		shape.convertAndShow(page, c);
		
		shape.createPoints(mx, my, mz, length);
		mat1.setMatSilumFix(1.0, width, 1.0,mx,my, mz);
		mat2.setMatMove(0,((1-width)*length)/Math.sqrt(2), 0);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(Math.PI/2, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(rotate, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat1.mullMatMat(look);
		shape.mullMat(mat1);
		shape.convertAndShow(page, c);
		
		shape.createPoints(mx, my, mz, length);
		mat1.setMatSilumFix(1.0, width, 1.0,mx,my, mz);
		mat2.setMatMove(0,((1-width)*length)/Math.sqrt(2), 0);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(Math.PI, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(rotate, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat1.mullMatMat(look);
		shape.mullMat(mat1);
		shape.convertAndShow(page, c);
		
		shape.createPoints(mx, my, mz, length);
		mat1.setMatSilumFix(1.0, width, 1.0,mx,my, mz);
		mat2.setMatMove(0,((1-width)*length)/Math.sqrt(2), 0);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(3*Math.PI/2, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat2.setMatRotateZFix(rotate, mx, my, mz);
		mat1.mullMatMat(mat2);
		mat1.mullMatMat(look);
		shape.mullMat(mat1);
		shape.convertAndShow(page, c);
	}
	public void paintBoardA (Graphics page,int stat,int length,int zlength,double mx,double my,double mz,Square players[][],double rotate,boolean isNeddFrame)
	{
		int i,h;
		if ((stat==5)||(stat==4))
		{
			for (i=0;i<3;i++)
			{
				for (h=0;h<3;h++)
				{
					paintBoardB(page, stat, length/3, zlength, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,players[h][i],rotate,mx,my,mz);
					if ((isNeddFrame)&&(numSquareH%3==h)&&(numSquareW%3==i))
					{
						paintFrame(page, length/6, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,rotate,c4);
					}
				}
				
			}
		}
		else if ((stat==0)||(stat==1))
		{
			for (i=0;i<3;i++)
			{
				for (h=2;h>=0;h--)
				{
					paintBoardB(page, stat, length/3, zlength, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,players[h][i],rotate,mx,my,mz);
					if ((isNeddFrame)&&(numSquareH%3==h)&&(numSquareW%3==i))
					{
						paintFrame(page, length/6, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,rotate,c4);
					}
				}
			}
		}
		else if ((stat==6)||(stat==7))
		{
			for (i=2;i>=0;i--)
			{
				for (h=0;h<3;h++)
				{
					paintBoardB(page, stat, length/3, zlength, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,players[h][i],rotate,mx,my,mz);
					if ((isNeddFrame)&&(numSquareH%3==h)&&(numSquareW%3==i))
					{
						paintFrame(page, length/6, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,rotate,c4);
					}
				}
			}
		}
		else if ((stat==2)||(stat==3))
		{
			for (i=2;i>=0;i--)
			{
				for (h=2;h>=0;h--)
				{
					paintBoardB(page, stat, length/3, zlength, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,players[h][i],rotate,mx,my,mz);
					if ((isNeddFrame)&&(numSquareH%3==h)&&(numSquareW%3==i))
					{
						paintFrame(page, length/6, mx+(i-1)*((length/2)-(length/6)), my+(h-1)*((length/2)-(length/6)), mz,rotate,c4);
					}
					
				}
			}
		}
		
	}
	public void paintBoardB (Graphics page,int stat,int length,int zlength,double mx,double my,double mz,Square player,double rotate,double mxr,double myr,double mzr)
	{
		int bodyRad=length/3;
		if ((stat==5)||(stat==4))
		{
			if (player.getColor()==-1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c2);
			}
			if (player.getColor()==1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c3);
			}			
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(3*Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			if (stat==4)
			{
				if (player.isWin())
				{
					paintFrame(page, length/2+10, mx, my, mz, rotate,c7);	
				}
			}
			
		}
		else if ((stat==0)||(stat==1))
		{
			if (player.getColor()==-1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c2);
			}
			if (player.getColor()==1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c3);
			}			
					
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(3*Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
									
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			
			if (stat==0)
			{
				if (player.isWin())
				{
					paintFrame(page, length/2+10, mx, my, mz, rotate,c7);	
				}
				
			}			
		}
		else if ((stat==6)||(stat==7))
		{
			
			if (player.getColor()==-1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c2);
			}
			if (player.getColor()==1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c3);
			}			
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(3*Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			if (stat==7)
			{
				if (player.isWin())
				{
					paintFrame(page, length/2+10, mx, my, mz, rotate,c7);	
				}
				
			}			
		}
		else if ((stat==2)||(stat==3))
		{
			
			if (player.getColor()==-1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c2);
			}
			if (player.getColor()==1)
			{
				shape.createPoints(mx, my, mz, length/2);
				mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
				mat1.mullMatMat(look);
				shape.mullMat(mat1);
				shape.convertAndShow(page, c3);
			}			
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
						
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(3*Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(Math.PI/2, mx,my,mz);
			mat2.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(mat2);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0,isBright, lightPoint);
			
			body1.createPoints(mx, my, mz,length/2, bodyRad, zlength);
			mat1.setMatRotateZFix(rotate, mxr, myr, mzr);
			mat1.mullMatMat(look);
			body1.mullMat(mat1);
			body1.buildGalilFromPoints();
			body1.convertAndShow(page,c6,c8,player.getColor()!=0, isBright, lightPoint);
			if (stat==3)
			{
				if (player.isWin())
				{
					paintFrame(page, length/2+10, mx, my, mz, rotate,c7);	
				}
				
			}			
			
		}		
	}
	public void help() 
	{
		JOptionPane.showMessageDialog(null,"Press the left and right arrow buttons  \n" +
				"in order to choose the board that you want to put your ball in or rotate \n" +
				"or use the mouse to press the spot you want to put your ball in\n" +
				"or to rotate it, right-click the board to turn it clockwise \n" +
				"and left-click the board to turn it counterclockwise \n", "Help", JOptionPane.INFORMATION_MESSAGE);
 		
	}
	public void rules() 
	{
		JOptionPane.showMessageDialog(null,"The object of the game is to create a sequence of 5 of your balls in a row  \n" +
				"The sequence can be horizontal, vertical, or diagonal and the winner is the first one to create such a sequence.   \n" +
				"In the case of both players getting the sequence at the same time, or the board filling up without \n" +
				"any sequences, there is a tie game. In each turn, the player puts one of his balls in an empty space \n" +
				"on the board and rotates one of the four boards 90 degrees (clockwise or counterclockwise) \n", "Rules", JOptionPane.INFORMATION_MESSAGE);
 		
	}
	public void changePerspective() 
	{
//		perspective =!perspective;
//		if (perspective)
//		{
//			p00.setXYZ(0,0,5000);
//			depth=1000;
//		}
//		else
//		{
//			p00.setXYZ(600,300,5000);
//			depth=400;
//		}
//		p1.setXYZ(p00.x-100,p00.y+100,p00.z-100);
//		p2.setXYZ(p00.x+100,p00.y+100,p00.z+100);
//		p0.setXYZ(p00.x-100,p00.y+100,p00.z+100);
//		p3.setXYZ(p00.x-100,p00.y-100,p00.z+100);
//		board.setXYZ(p00);
//		sBoard[1][1].setXYZ(board.x+depth/4,board.y+depth/4,board.z-zlength);
//		sBoard[0][1].setXYZ(board.x+depth/4,board.y-depth/4,board.z-zlength);
//		sBoard[1][0].setXYZ(board.x-depth/4,board.y+depth/4,board.z-zlength);
//		sBoard[0][0].setXYZ(board.x-depth/4,board.y-depth/4,board.z-zlength);
//		look.setIdentity();
//		inverseLook.setIdentity();
//		repaint();
	}

//	public boolean isPerspective()
//	{
//		return perspective;
//	}
	public Square[][] smallArray(int h,int i)
	{
		Square re[][] = new Square[3][3];
		int ii,hh;
		for (ii=0;ii<3;ii++)
		{
			for (hh=0;hh<3;hh++)
			{
				re[hh][ii]=new Square(gameManager.board[hh+3*h][ii+3*i].getColor(),gameManager.board[hh+3*h][ii+3*i].isWin());
			}
		}
		return re;
	}
	public void startWinAnimation(boolean isTie)
	{
		winTheard=new WinAnimation(this,isTie);
		winTheard.start();
	}
}
