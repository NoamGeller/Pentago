/*
 * Created on Eiar 5767
 * update Av 5768
 * @author levian
 * for Student
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

public class GraphicsOptions extends JToolBar implements ActionListener ,KeyListener
{
	private static final long serialVersionUID = 3430552137720449288L;

	
	GraphicsManager graphicsManager;

	JButton left,right,rotateXFixUp,rotateXFixDown;

	JButton up,down,rotateYFixLeft,rotateYFixRight;
	
	JButton rotateZFixFor,rotateZFixBack;
	
	JButton rotateAxisLeft,rotateAxisRight;

	JButton bigger,smaller,perspective,light,forward,back,changeColor,reset;	
			
	
    public GraphicsOptions(GraphicsManager m,String name)
	{
		super(name);
		graphicsManager=m;

		left= new JButton(new ImageIcon("Images\\left.GIF"));
		left.setToolTipText("left (X axis) 'a' ");
		left.addActionListener(this);
		left.addKeyListener( this);
		add(left);

		right= new JButton(new ImageIcon("Images\\right.GIF"));
		right.addActionListener(this);
		right.setToolTipText("right (X axis) 's'");
		right.addKeyListener( this);
		add(right);		
   
		up= new JButton(new ImageIcon("Images\\up.GIF"));
		up.addActionListener(this);
		up.setToolTipText("up  (Y axis) 'd'");
		up.addKeyListener( this);
		add(up);

		down= new JButton(new ImageIcon("Images\\down.GIF"));
		down.addActionListener(this);
		down.setToolTipText("right (Y axis) 'f'");
		down.addKeyListener( this);
		add(down);
		
		back= new JButton(new ImageIcon("Images\\back.GIF"));
		back.addActionListener(this);
		back.setToolTipText("back (Z axis) 'f'");
		back.addKeyListener( this);
		add(back);
		
		forward= new JButton(new ImageIcon("Images\\Forword.GIF"));
		forward.addActionListener(this);
		forward.setToolTipText("foward (Z axis) 'f'");
		forward.addKeyListener( this);
		add(forward);	

		rotateYFixLeft= new JButton(new ImageIcon("Images\\rotateFixYLeft.GIF"));
		rotateYFixLeft.addActionListener(this);
		rotateYFixLeft.setToolTipText("Rotate Fix left (Y axis)  'c'");
		rotateYFixLeft.addKeyListener(this);
		add(rotateYFixLeft);

		rotateYFixRight= new JButton(new ImageIcon("Images\\rotateFixYRight.GIF"));
		rotateYFixRight.addActionListener(this);
		rotateYFixRight.setToolTipText("Rotate Fix right ( axis) 'v'");
		rotateYFixRight.addKeyListener( this);
		add(rotateYFixRight);
				
		addSeparator() ;

		
		rotateZFixBack= new JButton(new ImageIcon("Images\\rotateFixLeft.GIF"));
		rotateZFixBack.addActionListener(this);
		rotateZFixBack.setToolTipText("Rotate Fix right (Z axis) 'n'");
		rotateZFixBack.addKeyListener( this);
		add(rotateZFixBack);

		rotateZFixFor= new JButton(new ImageIcon("Images\\rotateFixRight.GIF"));
		rotateZFixFor.addActionListener(this);
		rotateZFixFor.setToolTipText("Rotate Fix left (Z axis)  'b'");
		rotateZFixFor.addKeyListener( this);
		add(rotateZFixFor);

		
		addSeparator() ;

		rotateAxisLeft= new JButton(new ImageIcon("Images\\rotateAxisLeft.GIF"));
		rotateAxisLeft.addActionListener(this);
		rotateAxisLeft.setToolTipText("Rotate axis right ( two points) 'u'");
		rotateAxisLeft.addKeyListener( this);
		add(rotateAxisLeft);
		
		rotateAxisRight= new JButton(new ImageIcon("Images\\rotateAxisRight.GIF"));
		rotateAxisRight.addActionListener(this);
		rotateAxisRight.setToolTipText("Rotate axis left ( two points) 'i'");
		rotateAxisRight.addKeyListener( this);
		add(rotateAxisRight);
				
		addSeparator() ;
		
		bigger= new JButton(new ImageIcon("Images\\bigger.GIF"));;
		bigger.addActionListener(this);
		bigger.setToolTipText(" bigger 'o'");
		bigger.addKeyListener( this);
		add(bigger);
		
		smaller= new JButton(new ImageIcon("Images\\smaller.GIF"));;
		smaller.addActionListener(this);
		smaller.setToolTipText(" smaller 'l'");
		smaller.addKeyListener( this);
		add(smaller);

		addSeparator() ;

		
		
		rotateXFixUp= new JButton(new ImageIcon("Images\\rotateFixXup.GIF"));
		rotateXFixUp.addActionListener(this);
		rotateXFixUp.setToolTipText("Rotate Fix up  (X axis) 'z'");
		rotateXFixUp.addKeyListener( this);
		add(rotateXFixUp);

		rotateXFixDown= new JButton(new ImageIcon("Images\\rotateFixXdoun.GIF"));
		rotateXFixDown.addActionListener(this);
		rotateXFixDown.setToolTipText("Rotate Fix down (X axis) 'x'");
		rotateXFixDown.addKeyListener( this);
		add(rotateXFixDown);

		addSeparator() ;
		
		perspective= new JButton("unperspectiv");
		perspective.addActionListener(this);
		perspective.setToolTipText("is perspectiv");
		perspective.addKeyListener( this);
		add(perspective);
		
		light= new JButton("Board");
		light.addActionListener(this);
		light.setToolTipText("is move board or light point");
		light.addKeyListener( this);
		add(light);
		
		changeColor= new JButton("Change Color");
		changeColor.addActionListener(this);	
		changeColor.addKeyListener( this);
		add(changeColor);
		
		reset= new JButton("Reset");
		reset.addActionListener(this);	
		reset.addKeyListener( this);
		add(reset);

		setEnabled2(false);
		
		graphicsManager.addKeyListener(this);
	}
	
	public void keyTyped(KeyEvent e)
	{
		
			
	}
	
	public void actionPerformed (ActionEvent event)
	{
		JButton now=(JButton)event.getSource();

		if (now==changeColor)
		{
//			JFrame f=new JFrame();		
//			setBackground(JColorChooser.showDialog(f, "board", getBackground()));
//			c1=JColorChooser.showDialog(f," רקע", c1);			
//			c5=JColorChooser.showDialog(f,"לוח גדול", c5);
//			c6=JColorChooser.showDialog(null,"לוחות קטנים", c6);
//			c7=JColorChooser.showDialog(f,"סימון ניצחון", c7);
//			c4=JColorChooser.showDialog(f," סימון המשבצת", c4);
//			c2=JColorChooser.showDialog(f," שחקן 1", c2);
//			c3=JColorChooser.showDialog(f,"שחקן 2", c3);
//			c8=JColorChooser.showDialog(null,"עומק הטבעת", c8);
			String o[]=new String[6];
			o[0]="Background";
			o[1]="Player 1";
			o[2]="Player 2";
			o[3]="Large Board";
			o[4]="Small Board";
			o[5]="Ring";
			int op=JOptionPane.showOptionDialog(null,"בחר את הדבר שתרצה לשנות","Pentago", JOptionPane.DEFAULT_OPTION, JOptionPane.CLOSED_OPTION,new ImageIcon("Images\\board.GIF") , o, o[0]);
			if (op==0)
			{
				graphicsManager.c1=JColorChooser.showDialog(null,o[op], graphicsManager.c1);
				graphicsManager.setBackground(graphicsManager.c1);
			}
			else if (op==1)
			{
				graphicsManager.c2=JColorChooser.showDialog(null,o[op], graphicsManager.c2);		
			}
			else if (op==2)
			{
				graphicsManager.c3=JColorChooser.showDialog(null,o[op], graphicsManager.c3);		
			}
			else if (op==3)
			{
				graphicsManager.c5=JColorChooser.showDialog(null,o[op], graphicsManager.c5);		
			}
			else if (op==4)
			{
				graphicsManager.c6=JColorChooser.showDialog(null,o[op], graphicsManager.c6);		
			}
			else if (op==5)
			{
				graphicsManager.c8=JColorChooser.showDialog(null,o[op], graphicsManager.c8);		
			}
			graphicsManager.repaint();
		}
		else if (now==reset)
		{
			graphicsManager.reset();
		}
		else if (graphicsManager.gameManager.isPlaying) 
		{
			if (now==rotateXFixUp)
			{
				graphicsManager.rotateXFixUp();
			}
			else if (now==rotateXFixDown)
			{
				graphicsManager.rotateXFixDown();
			}
			else if (now==rotateYFixLeft)
			{
				graphicsManager.rotateYFixLeft();
			}
			else if (now==rotateYFixRight)
			{
				graphicsManager.rotateYFixRight();
			}
			else if (now==rotateZFixFor)
			{
				graphicsManager.rotateZFixFor();
			}
			else if (now==rotateZFixBack)
			{
				graphicsManager.rotateZFixBack();
			}
			else if (now==bigger)
			{
				graphicsManager.bigger();
			}
			else if (now==smaller)
			{
				graphicsManager.smaller();
			}
			else if (now==up)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveUpLightPoint();
				}
				else
				{
					graphicsManager.moveUp();
				}
			}
			else if (now==down)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveDownLightPoint();
				}
				else
				{
					graphicsManager.moveDown();
				}
			}
			else if (now==back)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveBackwardLightPoint();
				}
				else
				{
					graphicsManager.moveBackward();
				}
			}
			else if (now==forward)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveForwardLightPoint();
				}
				else
				{
					graphicsManager.moveForward();
				}
			}
			else if (now==left)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveLeftLightPoint();
				}
				else
				{
					graphicsManager.moveLeft();
				}
			}
			else if (now==right)
			{
				if (graphicsManager.showLight)
				{
					graphicsManager.moveRightLightPoint();
				}
				else
				{
					graphicsManager.moveRight();
				}
			}
			
			else if (now==rotateAxisLeft)
			{
				graphicsManager.rotateAxisLeft();
			}
			else if (now==rotateAxisRight)
			{
				graphicsManager.rotateAxisRight();
			}	
			else if (now==perspective)
			{
				if (perspective.getText().equals("unperspectiv"))
				{
					perspective.setText("Perspectiv");
				}
				else
				{
					perspective.setText("unperspectiv");
				}
				if(graphicsManager.isInTheard)
				{
					graphicsManager.isWaitToChangeP=!graphicsManager.isWaitToChangeP;
				}
				else
				{
					graphicsManager.changePerspective();
				}							
			}	
			else if (now==light)
			{
				if (light.getText().equals("Board"))
				{
					light.setText("Light");
					graphicsManager.showLight=true;
				}
				else
				{
					light.setText("Board");
					graphicsManager.showLight=false;
				}
				graphicsManager.repaint();		
			}
		}
	}

	public void keyPressed(KeyEvent e) 
	{
		char ch=e.getKeyChar();
		switch (ch)
		{
		case 'a':
			left.doClick() ;
			break;

		case 's':
			right.doClick() ;
            break;
            
		case 'z':
			rotateXFixUp.doClick() ;
			break;

		case 'x':
			rotateXFixDown.doClick() ;
			break;

		case 'd':
			up.doClick() ;
			break;

		case 'f':
			down.doClick() ;
            break;
            
		case 'c':
			rotateYFixLeft.doClick() ;
			break;

		case 'v':
			rotateYFixRight.doClick() ;
			break;
		            
		case 'b':
			rotateZFixFor.doClick() ;
			break;

		case 'n':
			rotateZFixBack.doClick() ;
			break;

		case 'u':
			rotateAxisLeft.doClick() ;
			break;

		case 'i':
			rotateAxisRight.doClick() ;
			break;

		case 'o':
			bigger.doClick() ;
			break;

		case 'l':
			smaller.doClick() ;
			break;		
		}
		
	}

	public void keyReleased(KeyEvent arg0) 
	{
		// TODO Auto-generated method stub		
	}
	public void setEnabled2(boolean isEnabled)
	{
		left.setEnabled(isEnabled);
		right.setEnabled(isEnabled);
		rotateXFixUp.setEnabled(isEnabled);
		rotateXFixDown.setEnabled(isEnabled);
		up.setEnabled(isEnabled);
		down.setEnabled(isEnabled);
		rotateYFixLeft.setEnabled(isEnabled);
		rotateYFixRight.setEnabled(isEnabled);
		rotateZFixFor.setEnabled(isEnabled);
		rotateZFixBack.setEnabled(isEnabled);
		rotateAxisLeft.setEnabled(isEnabled);
		rotateAxisRight.setEnabled(isEnabled);
		bigger.setEnabled(isEnabled);
		smaller.setEnabled(isEnabled);
		forward.setEnabled(isEnabled);
		back.setEnabled(isEnabled);
		repaint();
	}
	
}
