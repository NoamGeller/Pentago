

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
/**
 * המחלקה משמשת ליצירת מנסרות ולהצגתם על המסך בצורה תלת מימדית
 * @author Noam Wies
 *
 */
public class Galil 
{
    private int numPoints;
	private double xPointsReal[];
	private double yPointsReal[];
	private double zPointsReal[];

	private double xGufReal[][];
	private double yGufReal[][];
	private double zGufReal[][];

	private int xInt[];
	private int yInt[];
	/**
	 * הפונקציה יוצרת מנסרה חדשה שמספר הקודקודים בבסיסיה הוא המספר שמתקבל כפרמטר 
	 * @param numPoints
	 */
 	public Galil(int numPoints)
	{
		this.numPoints=numPoints;
		xPointsReal= new double[numPoints*2];
		yPointsReal= new double[numPoints*2];
		zPointsReal= new double[numPoints*2];
	
		xGufReal= new double[numPoints+2][numPoints];
		yGufReal= new double[numPoints+2][numPoints];
		zGufReal= new double[numPoints+2][numPoints];

		xInt = new int[numPoints];
		yInt = new int[numPoints];
		
	}
 	/**
 	 * ת(הפונקציה יותר יעילה מאשר יצירת אובייקט חדש כי היא חוסכת משאביי מערכת שנדרשים על מנת ליצר אובייקט חדש 
	 * @param xp  מיקום מרכז הבסיס הפחות עמוק בציר הרוחב  
	 * @param yp מיקום מרכז הבסיס הפחות עמוק בציר הגובה 
	 * @param zp מיקום מרכז הבסיס הפחות עמוק בציר העומק
 	 * @param rad
 	 * @param dz
 	 */
 	public void createPoints(double xp,double yp,double zp, int rad,int dz)
 	{
 		createPoints( xp, yp, zp, rad, dz,Math.PI/4);
 	}

 	public void createPoints(double xp,double yp,double zp, int rad,int dz,double teta)
 	{
 		double delta=2*Math.PI/numPoints;
 		double alpha=teta;
 	   	for (int i=0; i<numPoints;i++)
 	   	{
 	   		xPointsReal[i]=xp + (rad*Math.cos(alpha));
	   		yPointsReal[i]=yp + (rad*Math.sin(alpha));
	   		zPointsReal[i]=zp;

	   		xPointsReal[i+numPoints]=xPointsReal[i];
	   		yPointsReal[i+numPoints]=yPointsReal[i];
	   		zPointsReal[i+numPoints]=zPointsReal[i]+dz;

	   		alpha=alpha+delta;
	   	}
 	}
 	
 	public void biuldGalilFromPoints()
 	{
 		int i;
 		
 		for(i=0;i<numPoints-1;i++)
 		{
 			xGufReal[i][0]=xPointsReal[i+1];
 			yGufReal[i][0]=yPointsReal[i+1];
 			zGufReal[i][0]=zPointsReal[i+1];

 			xGufReal[i][1]=xPointsReal[i];
 			yGufReal[i][1]=yPointsReal[i];
 			zGufReal[i][1]=zPointsReal[i];
 			
 			xGufReal[i][2]=xPointsReal[i+numPoints];
 			yGufReal[i][2]=yPointsReal[i+numPoints];
 			zGufReal[i][2]=zPointsReal[i+numPoints];

 			xGufReal[i][3]=xPointsReal[i+1+numPoints];
 			yGufReal[i][3]=yPointsReal[i+1+numPoints];
 			zGufReal[i][3]=zPointsReal[i+1+numPoints];
 		}
		xGufReal[numPoints-1][0]=xPointsReal[0];
 		yGufReal[numPoints-1][0]=yPointsReal[0];
 		zGufReal[numPoints-1][0]=zPointsReal[0];

		xGufReal[numPoints-1][1]=xPointsReal[numPoints-1];
 		yGufReal[numPoints-1][1]=yPointsReal[numPoints-1];
 		zGufReal[numPoints-1][1]=zPointsReal[numPoints-1];

		xGufReal[numPoints-1][2]=xPointsReal[numPoints*2-1];
 		yGufReal[numPoints-1][2]=yPointsReal[numPoints*2-1];
 		zGufReal[numPoints-1][2]=zPointsReal[numPoints*2-1];

		xGufReal[numPoints-1][3]=xPointsReal[numPoints];
 		yGufReal[numPoints-1][3]=yPointsReal[numPoints];
 		zGufReal[numPoints-1][3]=zPointsReal[numPoints];
 		
 		for( i=0;i<numPoints;i++)
 		{
 			xGufReal[numPoints][i]=xPointsReal[i];
 	 		yGufReal[numPoints][i]=yPointsReal[i];
 	 		zGufReal[numPoints][i]=zPointsReal[i];
 			
 		}

 		for( i=0;i<numPoints;i++)
 		{
 			xGufReal[numPoints+1][i]=xPointsReal[numPoints*2-1-i];
 	 		yGufReal[numPoints+1][i]=yPointsReal[numPoints*2-1-i];
 	 		zGufReal[numPoints+1][i]=zPointsReal[numPoints*2-1-i];
 	 	}

 	}
 	
	public void convertTo2D(double xr[],double yr[],int aNum)
	{
		for(int i=0;i<aNum;i++)
		{
			xInt[i]=(int)xr[i];
			yInt[i]=(int)yr[i];
		}
	}
	
	public void convertTo2DPerspectiv(double xR[],double yR[],double zR[],int aNum, int depth, Point prespctivCenter)
	{
		for(int i=0;i<aNum;i++)
		{
			xInt[i]=0;
			this.xInt[i]=(int)(xR[i]*(depth/(zR[i]+depth)))+prespctivCenter.x;
			yInt[i]=0;
			this.yInt[i]=(int)(yR[i]*(depth/(zR[i]+depth)))+prespctivCenter.y;
		}
	}
	
	
	public void mullMat(Matrix3D  mat)
	{
		double xTemp,yTemp,zTemp;
		for(int i=0;i<numPoints*2;i++)
		{
			xTemp=xPointsReal[i]; yTemp= yPointsReal[i]; zTemp=zPointsReal[i];
			xPointsReal[i]=xTemp*mat.mat[0][0] + yTemp*mat.mat[1][0] + zTemp*mat.mat[2][0] + 1*mat.mat[3][0]; 
			yPointsReal[i]=xTemp*mat.mat[0][1] + yTemp*mat.mat[1][1] + zTemp*mat.mat[2][1] + 1*mat.mat[3][1]; 
			zPointsReal[i]=xTemp*mat.mat[0][2] + yTemp*mat.mat[1][2] + zTemp*mat.mat[2][2] + 1*mat.mat[3][2]; 
		}
	}

	public boolean check(double x1,double y1,
			             double x2,double y2,
			             double x3,double y3)
	{
		return x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2)>0.0;
	}
		
	public void convertAndShow(Graphics p,Color color, int depth, Point prespctivCenter, boolean perspectiv)
 	{
		int i;
 	   	for ( i=0;i<numPoints;i++)
	   	{
	   		if (check(xGufReal[i][0],yGufReal[i][0],
	   				  xGufReal[i][1],yGufReal[i][1],
	   				  xGufReal[i][2],yGufReal[i][2]))
	   		{
	   			if (perspectiv)
		          convertTo2DPerspectiv(xGufReal[i],yGufReal[i],zGufReal[i],4,depth,prespctivCenter);
	   			else
			        convertTo2D(xGufReal[i],yGufReal[i],4);
	   			p.setColor(color);
	            p.fillPolygon(xInt,yInt,4);   			
		        p.setColor(Color.black);
	            p.drawPolygon(xInt,yInt,4);
	   		}
	   	}
	   	for ( i=numPoints;i<numPoints+2;i++)
	   	{
	   		if (check(xGufReal[i][0],yGufReal[i][0],
	   				  xGufReal[i][1],yGufReal[i][1],
	  				  xGufReal[i][2],yGufReal[i][2]))
	   		{
	   			if (perspectiv)
			          convertTo2DPerspectiv(xGufReal[i],yGufReal[i],zGufReal[i],numPoints,depth,prespctivCenter);
		   		else
				        convertTo2D(xGufReal[i],yGufReal[i],numPoints);
	           p.setColor(color);
	           p.fillPolygon(xInt,yInt,numPoints);
	           p.setColor(Color.black);
		       p.drawPolygon(xInt,yInt,numPoints);
	   		}
	   	}
	}
	public Color Brightness(Point3D lightPoint,Point3D p1,Point3D p2,Point3D p3,Color c)
	{

		Point3D normal,vec;
		normal=new Point3D(0,0,0);
		vec=new Point3D(0,0,0);
		normal.setNormal(p1, p2, p3);
		vec.x = p1.x - lightPoint.x;
		vec.y = p1.y - lightPoint.y;
		vec.z = p1.z - lightPoint.z;
		double cosa = ((normal.x*vec.x) + (normal.y*vec.y) + (normal.z*vec.z));
		cosa /= (Math.sqrt(normal.x*normal.x + normal.y*normal.y + normal.z*normal.z) * Math.sqrt(vec.x*vec.x + vec.y*vec.y + vec.z*vec.z));
		
		if(cosa < 0 )
			cosa = 0;
		if (cosa >1)
			cosa=1;
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		return Color.getHSBColor(hsb[0], hsb[1], (float) (hsb[2]+0.3*cosa));
	}	

}
