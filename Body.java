
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
/**
 * Creates prisms and shows them on the screen in the 3d way.
 * @author Noam Wies
 *
 */
public class Body 
{
	private int numPoints;
	private double xPointsReal[];
	private double yPointsReal[];
	private double zPointsReal[];
	//private double realPoints[][];

	private double xBodyReal[][];
	private double yBodyReal[][];
	private double zBodyReal[][];
	//private double bodyReal[][][];

	private int xInt[];
	private int yInt[];
	/**
	 * Makes a new prism such that the number of nodes in its base is the number given a parameter.
	 * @param numPoints the numer of nodes in its base
	 */
	public Body(int numPoints)
	{
		this.numPoints=numPoints;
		xPointsReal= new double[(numPoints+3)*2];
		yPointsReal= new double[(numPoints+3)*2];
		zPointsReal= new double[(numPoints+3)*2];
		//realPoints = new double[(numPoints+3)*2][3];
	
		xBodyReal= new double[numPoints+5][(numPoints+3)];
		yBodyReal= new double[numPoints+5][(numPoints+3)];
		zBodyReal= new double[numPoints+5][(numPoints+3)];
		//bodyReal = new double[numPoints+5][numPoints+3][3];

		xInt = new int[(numPoints+3)];
		yInt = new int[(numPoints+3)];
		
	}
	/**
	 *   Creates a new prism with the same number of notes in its base as the previous prism (more efficient than a funtion
	 *   that creates a new object because it saves system resources that are required to make a new object).
	 * @param xp the central location of the less deep base on the width axis
	 * @param yp the central location of the less deep base on the height axis
	 * @param zp the central location of the less deep base on the depth axis
	 * @param length
	 * @param rad
	 * @param dz
	 */
	public void createPoints(double xp,double yp,double zp, int length,int rad,int dz)
 	{
 		createPoints( xp, yp, zp,length, rad, dz,0);
 	}

 	public void createPoints(double xp,double yp,double zp,int length, int rad,int dz,double teta)
 	{
 		double delta = (Math.PI/2)/(numPoints-1);
 		double alpha = teta;
 		
 		xPointsReal[2]=xp ;
 		yPointsReal[2]=yp -length;
 		zPointsReal[2]=zp;
 		
 		xPointsReal[2+numPoints+3]=xPointsReal[2];
 		yPointsReal[2+numPoints+3]=yPointsReal[2];
 		zPointsReal[2+numPoints+3]=zPointsReal[2]+dz;
 		
 		xPointsReal[1]=xp -length;
 		yPointsReal[1]=yp -length;
 		zPointsReal[1]=zp;
   		
   		xPointsReal[1+numPoints+3]=xPointsReal[1];
   		yPointsReal[1+numPoints+3]=yPointsReal[1];
   		zPointsReal[1+numPoints+3]=zPointsReal[1]+dz;

   		xPointsReal[0]=xp -length;
   		yPointsReal[0]=yp ;
   	 	zPointsReal[0]=zp;
   	  
   	 	xPointsReal[numPoints+3]=xPointsReal[0];
   	 	yPointsReal[numPoints+3]=yPointsReal[0];
   	 	zPointsReal[numPoints+3]=zPointsReal[0]+dz;


   		for (int i=numPoints+2; i>=3;i--)
 	   	{
 	   		xPointsReal[i]=xp - (rad*Math.cos(alpha));
	   		yPointsReal[i]=yp - (rad*Math.sin(alpha));
	   		zPointsReal[i]=zp;

	   		xPointsReal[i+numPoints+3]=xPointsReal[i];
	   		yPointsReal[i+numPoints+3]=yPointsReal[i];
	   		zPointsReal[i+numPoints+3]=zPointsReal[i]+dz;

	   		alpha += delta;
	   	}
 	}
 	public void buildGalilFromPoints()
 	{
 		int i;
 		for (i=0;i<numPoints+2;i++)
 		{
 			xBodyReal[i][1]=xPointsReal[i];
 			yBodyReal[i][1]=yPointsReal[i];
 			zBodyReal[i][1]=zPointsReal[i];
 			
 			xBodyReal[i][0]=xPointsReal[i+1];
 			yBodyReal[i][0]=yPointsReal[i+1];
 			zBodyReal[i][0]=zPointsReal[i+1];
 			
 			xBodyReal[i][3]=xPointsReal[i+1+numPoints+3];
 			yBodyReal[i][3]=yPointsReal[i+1+numPoints+3];
 			zBodyReal[i][3]=zPointsReal[i+1+numPoints+3];
 			
 			xBodyReal[i][2]=xPointsReal[i+numPoints+3];
 			yBodyReal[i][2]=yPointsReal[i+numPoints+3];
 			zBodyReal[i][2]=zPointsReal[i+numPoints+3];
 		}	
 		i=numPoints+2;
 		xBodyReal[i][0]=xPointsReal[i];
 		yBodyReal[i][0]=yPointsReal[i];
		zBodyReal[i][0]=zPointsReal[i];
		
		xBodyReal[i][1]=xPointsReal[0];
		yBodyReal[i][1]=yPointsReal[0];
		zBodyReal[i][1]=zPointsReal[0];
		
		xBodyReal[i][2]=xPointsReal[numPoints+3];
		yBodyReal[i][2]=yPointsReal[numPoints+3];
		zBodyReal[i][2]=zPointsReal[numPoints+3];
			
		xBodyReal[i][3]=xPointsReal[i+numPoints+3];
		yBodyReal[i][3]=yPointsReal[i+numPoints+3];
		zBodyReal[i][3]=zPointsReal[i+numPoints+3];
 		
// 		for( i=0;i<numPoints+3;i++)
// 		{
// 			xGufReal[numPoints+3][i]=xPointsReal[i];
// 	 		yGufReal[numPoints+3][i]=yPointsReal[i];
// 	 		zGufReal[numPoints+3][i]=zPointsReal[i]; 
// 	 		
// 	 		xGufReal[numPoints+4][i]=xPointsReal[(numPoints+3)*2-1-i];
// 	 		yGufReal[numPoints+4][i]=yPointsReal[(numPoints+3)*2-1-i];
// 	 		zGufReal[numPoints+4][i]=zPointsReal[(numPoints+3)*2-1-i];
// 	
// 		}
 		
 		for( i=0;i<numPoints+3;i++)
 		{
 			xBodyReal[numPoints+3][i]=xPointsReal[i];
 	 		yBodyReal[numPoints+3][i]=yPointsReal[i];
 	 		zBodyReal[numPoints+3][i]=zPointsReal[i];
 	 		
 	 		
 	 		
 	
 		}
 		xBodyReal[numPoints+4][0]=xPointsReal[numPoints+5];
 		yBodyReal[numPoints+4][0]=yPointsReal[numPoints+5];
	 	zBodyReal[numPoints+4][0]=zPointsReal[numPoints+5];
	 	
	 	xBodyReal[numPoints+4][1]=xPointsReal[numPoints+4];
 		yBodyReal[numPoints+4][1]=yPointsReal[numPoints+4];
	 	zBodyReal[numPoints+4][1]=zPointsReal[numPoints+4];
	 	
	 	xBodyReal[numPoints+4][2]=xPointsReal[numPoints+3];
 		yBodyReal[numPoints+4][2]=yPointsReal[numPoints+3];
	 	zBodyReal[numPoints+4][2]=zPointsReal[numPoints+3];
	 	
	 	for( i=0;i<numPoints;i++)
	 	{
 	 		xBodyReal[numPoints+4][i+3]=xPointsReal[(numPoints+3)*2-1-i];
 	 		yBodyReal[numPoints+4][i+3]=yPointsReal[(numPoints+3)*2-1-i];
 	 		zBodyReal[numPoints+4][i+3]=zPointsReal[(numPoints+3)*2-1-i];
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
 	public void convertTo2DPerspective(double xR[],double yR[],double zR[],int aNum, int depth, Point prespctivCenter)
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
		for(int i=0;i<(numPoints+3)*2;i++)
		{
			xTemp=xPointsReal[i]; yTemp= yPointsReal[i]; zTemp=zPointsReal[i];
			xPointsReal[i]=xTemp*mat.mat[0][0] + yTemp*mat.mat[1][0] + zTemp*mat.mat[2][0] + 1*mat.mat[3][0];
			yPointsReal[i]=xTemp*mat.mat[0][1] + yTemp*mat.mat[1][1] + zTemp*mat.mat[2][1] + 1*mat.mat[3][1];
			zPointsReal[i]=xTemp*mat.mat[0][2] + yTemp*mat.mat[1][2] + zTemp*mat.mat[2][2] + 1*mat.mat[3][2];
		}
	}

//	public boolean check(double x1,double y1,
//			             double x2,double y2,
//			             double x3,double y3)
//	{
//		return (int)x1*((int)y2-(int)y3)+(int)x2*((int)y3-(int)y1)+(int)x3*((int)y1-(int)y2)>0.0;
//	}
	public boolean check(double x1,double y1,
            double x2,double y2,
            double x3,double y3)
{
return x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2)>0.0;
}
	public void convertAndShow(Graphics p,Color color1,Color color2,boolean isp,boolean isBrightColor,Point3D lightPoint)
 	{
		int i;		
 	   	for ( i=0;i<numPoints+3;i++)
	   	{
	   	if	(check(xBodyReal[i][0],yBodyReal[i][0], xBodyReal[i][1],yBodyReal[i][1],xBodyReal[i][2],yBodyReal[i][2]))
 			{
				convertTo2D(xBodyReal[i],yBodyReal[i],4);
	   			if ((i>2)&&(i<numPoints+2))
	   			{
	   				if (!isp)
	   				{
	   					if (isBrightColor)
	   					{
	   						p.setColor(Brightness(lightPoint, new Point3D(xBodyReal[i][0],yBodyReal[i][0],zBodyReal[i][0]), new Point3D(xBodyReal[i][1],yBodyReal[i][1],zBodyReal[i][1]), new Point3D(xBodyReal[i][2],yBodyReal[i][2],zBodyReal[i][2]), color2));
	   					}
	   					else
	   					{
	   						p.setColor(color2);
	   					}	   					
		   			    p.fillPolygon(xInt,yInt,4); 
	   				}	   				
	   			}
	   			else
	   			{
	   				if (i<2)
		            {
	   			    	
	   					if (isBrightColor)
	   					{
	   						p.setColor(Brightness(lightPoint, new Point3D(xBodyReal[i][0],yBodyReal[i][0],zBodyReal[i][0]), new Point3D(xBodyReal[i][1],yBodyReal[i][1],zBodyReal[i][1]), new Point3D(xBodyReal[i][2],yBodyReal[i][2],zBodyReal[i][2]), color1));
	   					}
	   					else
	   					{
	   						p.setColor(color1);
	   					}	
		   			    p.fillPolygon(xInt,yInt,4); 
	   			    	p.setColor(Color.black);
			            p.drawPolygon(xInt,yInt,4);
		            }		   			
	   			}      
	                
	   		}
	   	}
	   	for ( i=numPoints+3;i<numPoints+5;i++)
	   	{
	   		if (check(xBodyReal[i][0],yBodyReal[i][0],
	   				  xBodyReal[i][1],yBodyReal[i][1],
	  				  xBodyReal[i][2],yBodyReal[i][2]))
	   		{

				convertTo2D(xBodyReal[i],yBodyReal[i],numPoints+3);
	           p.setColor(color1);
	           p.fillPolygon(xInt,yInt,numPoints+3);
	   		}
	   	}
	}
	private Color Brightness(Point3D lightPoint,Point3D p1,Point3D p2,Point3D p3,Color c)
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
