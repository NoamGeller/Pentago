/*
 * Created on Eiar 5767
 * update on Av 5768
 * @author levian
 * for Students
 */

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JToolBar;


public class Main
{
 	public static void main(String[] args) 
	{
		Dimension d = new Dimension(0,0);
		d = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		d.height -= 135;
 		JFrame myFrame = new JFrame("PENTAGO");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameManager gameManager = new GameManager();
		GraphicsManager mainPanel = new GraphicsManager(gameManager);
	    gameManager.setGrm(mainPanel);
		JToolBar jTB = new GameOptions(gameManager,mainPanel,"Game Option");
	   	mainPanel.setOpaque(true);
//	    mainPanel.setPreferredSize(new Dimension(950,650));
	   	mainPanel.setPreferredSize(d);
		myFrame.add(jTB,BorderLayout.PAGE_START);
		myFrame.add(mainPanel,BorderLayout.CENTER);
		myFrame.pack();
		myFrame.setVisible(true);
	}
}
