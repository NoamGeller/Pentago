import java.awt.Color;
import java.awt.Graphics;


public class Point3D 
{
	double x,y,z;	
	public Point3D(double x1,double y1,double z1)
	{
		x=x1;
		y=y1;
		z=z1;
	}
	public Point3D(Point3D p)
	{
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	public void setXYZ(double x1,double y1,double z1)
	{
		x=x1;
		y=y1;
		z=z1;
	}
	public void setXYZ(Point3D p1)
	{
		x=p1.x;
		y=p1.y;
		z=p1.z;
	}
	public void move(double x1,double y1,double z1)
	{
		x=x+x1;
		y=y+y1;
		z=z+z1;
	}
	
	public void show(Graphics page)
	{
		page.setColor(Color.black);
		page.fillOval((int)x-5, (int)y-5, 10, 10);
	}
	public void mullMat(Matrix3D mati)
	{
		double xTemp,yTemp,zTemp;
		xTemp=x; yTemp= y; zTemp=z;
		x=xTemp*mati.mat[0][0] + yTemp*mati.mat[1][0] + zTemp*mati.mat[2][0] + 1*mati.mat[3][0]; 
		y=xTemp*mati.mat[0][1] + yTemp*mati.mat[1][1] + zTemp*mati.mat[2][1] + 1*mati.mat[3][1]; 
		z=xTemp*mati.mat[0][2] + yTemp*mati.mat[1][2] + zTemp*mati.mat[2][2] + 1*mati.mat[3][2]; 
	}	
	public void setNormal(Point3D p1,Point3D p2,Point3D p3)
	{
		z = (p1.x*(p2.y-p3.y))+(p2.x*(p3.y-p1.y))+(p3.x*(p1.y-p2.y));
		x = (p1.y*(p2.z-p3.z))+(p2.y*(p3.z-p1.z))+(p3.y*(p1.z-p2.z));
		y = (p1.z*(p2.x-p3.x))+(p2.z*(p3.x-p1.x))+(p3.z*(p1.x-p2.x));
	}
	public void plus(Point3D p1)
	{
		x=p1.x+x;
		y=p1.y+y;
		z=p1.z+z;
	}
	public void minus(Point3D p1)
	{
		x=-1*p1.x+x;
		y=-1*p1.y+y;
		z=-1*p1.z+z;
	}
	public void plus(double dx,double dy,double dz)
	{
		x+=dx;
		y+=dy;
		z+=dz;
	}
	public void minus(double dx,double dy,double dz)
	{
		x-=dx;
		y-=dy;
		z-=dz;
	}
	public void calculateZ(Matrix3D mati,double z)
	{
		this.z=(z-(this.x*mati.mat[0][2])-(this.y*mati.mat[1][2])-(mati.mat[3][2]))/(mati.mat[2][2]);
		
	}
	public void calculateZ(Matrix3D mati,double z,int d,int xp,int yp)
	{
		this.z=(d*(z-mati.mat[0][2]*(this.x-xp)-mati.mat[1][2]*(this.y-yp)-mati.mat[3][2]))/(mati.mat[2][2]*d+mati.mat[0][2]*(this.x-xp)+mati.mat[1][2]*(this.y-yp));
		this.x=((this.x-xp)*(this.z+d))/d;
		this.y=((this.y-yp)*(this.z+d))/d;
	}
}
