import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Shape2D 
{
	int numPoints;
	double xPointsReal[];
	double yPointsReal[];
	double zPointsReal[];
	int xInt[];
	int yInt[];	
	public Shape2D(int numPoints)
	{
		this.numPoints=numPoints;
		xPointsReal= new double[numPoints];
		yPointsReal= new double[numPoints];
		zPointsReal= new double[numPoints];
		xInt = new int[numPoints];
		yInt = new int[numPoints];
	}
	public void createPoints(double xp,double yp,double zp, int rad)
 	{
 		createPoints( xp, yp,zp, rad,Math.PI/4); 		
 	}
	public void createPoints(double xp,double yp,double zp, int rad,double teta)
 	{
 		double delta=2*Math.PI/numPoints;
 		double alpha=teta;
 	   	for (int i=0; i<numPoints;i++)
 	   	{
 	   		xPointsReal[i]=xp + (rad*Math.cos(alpha));
	   		yPointsReal[i]=yp + (rad*Math.sin(alpha));	
	   		zPointsReal[i]=zp;
	   		alpha=alpha+delta;
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
		for(int i=0;i<numPoints;i++)
		{
			xTemp=xPointsReal[i]; yTemp= yPointsReal[i]; zTemp=zPointsReal[i];
			xPointsReal[i]=xTemp*mat.mat[0][0] + yTemp*mat.mat[1][0] + zTemp*mat.mat[2][0] + 1*mat.mat[3][0]; 
			yPointsReal[i]=xTemp*mat.mat[0][1] + yTemp*mat.mat[1][1] + zTemp*mat.mat[2][1] + 1*mat.mat[3][1]; 
			zPointsReal[i]=xTemp*mat.mat[0][2] + yTemp*mat.mat[1][2] + zTemp*mat.mat[2][2] + 1*mat.mat[3][2]; 
		}
	}
	public void convertAndShow(Graphics p,Color color, int depth, Point prespctivCenter, boolean perspectiv)
	{
		if (perspectiv)
	          convertTo2DPerspectiv(xPointsReal,yPointsReal,zPointsReal,4,depth,prespctivCenter);
			else
		        convertTo2D(xPointsReal,yPointsReal,4);
		p.setColor(color); 		
        p.fillPolygon(xInt,yInt,xInt.length);  
	}
}
