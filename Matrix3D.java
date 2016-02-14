/*
 * Created on Eiar 5767
 * @author levian
 * for Student
 */
public class Matrix3D 
{
	final int size=4;
	double mat[][];

	public Matrix3D()
	{
		mat=new double[size][size];
		setIdentity();
	}
	public Matrix3D(Matrix3D aMat)
	{
		mat=new double[size][size];
		int i,h;
		for (i=0;i<aMat.mat.length;i++)
		{
			for (h=0;h<aMat.mat[0].length;h++)
			{
				mat[i][h]=aMat.mat[i][h];
			}
		}
	}
	public void setIdentity()
	{
		mat[0][0]=1.0;  mat[0][1]=0.0;  mat[0][2]=0.0; mat[0][3]=0.0;
		mat[1][0]=0.0;  mat[1][1]=1.0;  mat[1][2]=0.0; mat[1][3]=0.0;
		mat[2][0]=0.0;  mat[2][1]=0.0;  mat[2][2]=1.0; mat[2][3]=0.0;
		mat[3][0]=0.0;  mat[3][1]=0.0;  mat[3][2]=0.0; mat[3][3]=1.0;
	}
	
	public void setMatMove(double dx,double dy,double dz)
	{
		mat[0][0]=1.0;  mat[0][1]=0.0;  mat[0][2]=0.0; mat[0][3]=0.0;
		mat[1][0]=0.0;  mat[1][1]=1.0;  mat[1][2]=0.0; mat[1][3]=0.0;
		mat[2][0]=0.0;  mat[2][1]=0.0;  mat[2][2]=1.0; mat[2][3]=0.0;
		mat[3][0]=dx;   mat[3][1]=dy;   mat[3][2]=dz;  mat[3][3]=1.0;
	}


	public void setMatRotateAxis(double x1, double y1,double z1,
			                     double x2, double y2,double z2,
			                                       double teta)
	{
		Matrix3D m1= new Matrix3D();
		double a,b,c,d,l;
		
	    a = x2-x1; b = y2-y1; c = z2-z1;
	    l = (float) Math.sqrt(a*a+b*b+c*c);
	    a = a/l;   b = b/l;   c = c/l;
	    d = (float) Math.sqrt(b*b+c*c);

	    if (d == 0)
	      
	    this.setMatRotateXFix(teta,x1,y1,z1);
	    else
	    {
	      setMatMove(-x1,-y1,-z1);

          m1.setIdentity();                     // x axis
	      m1.mat[1][1] = c/d;  m1.mat[1][2] = b/d;
	      m1.mat[2][1] = -b/d; m1.mat[2][2] = c/d;
	      mullMatMat(m1);

	      m1.setIdentity();                     // y axis
	      m1.mat[0][0] = d;  m1.mat[0][2] = a;
	      m1.mat[2][0] = -a; m1.mat[2][2] = d;
	      mullMatMat(m1);

	      m1.setMatRotateZ(teta);               // Z axis
	      mullMatMat(m1);

          m1.setIdentity();                     // y axis
	      m1.mat[0][0] = d;  m1.mat[0][2] = -a;
	      m1.mat[2][0] = a; m1.mat[2][2] = d;
	      mullMatMat(m1);


          m1.setIdentity();                     // x axis
	      m1.mat[1][1] = c/d;  m1.mat[1][2] = -b/d;
	      m1.mat[2][1] = b/d;  m1.mat[2][2] = c/d;
	      mullMatMat(m1);

	      m1.setMatMove(x1,y1,z1);
	      mullMatMat(m1);
	    }

		
	}	                    
	public void mullMatMat(Matrix3D aMat)
	{
		Matrix3D temp=new Matrix3D();
		for (int i= 0; i<size;i++)
			for (int j=0; j<size;j++)
			{
				temp.mat[i][j]=0;
				for (int k=0;k<size;k++)
					temp.mat[i][j] += mat[i][k] * aMat.mat[k][j];
			}
		mat=temp.mat;
	}
	
	public void mullAllPoints(double xr[], double yr[],double zr[], int aNum)
	{
		double xTemp,yTemp,zTemp;
		for(int i=0;i<aNum;i++)
		{
			xTemp=xr[i]; yTemp= yr[i]; zTemp=zr[i];
			xr[i]=xTemp*mat[0][0] + yTemp*mat[1][0] + zTemp*mat[2][0] + 1*mat[3][0]; 
			yr[i]=xTemp*mat[0][1] + yTemp*mat[1][1] + zTemp*mat[2][1] + 1*mat[3][1]; 
			zr[i]=xTemp*mat[0][2] + yTemp*mat[1][2] + zTemp*mat[2][2] + 1*mat[3][2]; 
		}
		
	}
	public void setMatSilum(double sX,double sY,double sZ)
	{
		mat[0][0]=sX;    mat[0][1]=0.0;   mat[0][2]=0.0;  mat[0][3]=0.0;
		mat[1][0]=0.0;   mat[1][1]=sY;    mat[1][2]=0.0;  mat[1][3]=0.0;
		mat[2][0]=0.0;   mat[2][1]=0.0;   mat[2][2]=sZ  ; mat[2][3]=0.0;
		mat[3][0]=0.0;   mat[3][1]=0.0;   mat[3][2]=0.0;  mat[3][3]=1.0;
	}
	public void setMatRotateX(double a)
	{
		mat[0][0]=1.0;  mat[0][1]=0.0;               mat[0][2]=0.0;         mat[0][3]=0.0;
		mat[1][0]=0.0;  mat[1][1]=Math.cos(a);       mat[1][2]=Math.sin(a); mat[1][3]=0.0;
		mat[2][0]=0.0;  mat[2][1]=(-1)*Math.sin(a);  mat[2][2]=Math.cos(a); mat[2][3]=0.0;
		mat[3][0]=0.0;  mat[3][1]=0.0;               mat[3][2]=0.0;         mat[3][3]=1.0;
	}
	public void setMatRotateY(double a)
	{
		mat[0][0]=Math.cos(a);       mat[0][1]=0.0;   mat[0][2]=Math.sin(a); mat[0][3]=0.0;
		mat[1][0]=0.0;               mat[1][1]=1.0;   mat[1][2]=0.0;         mat[1][3]=0.0;
		mat[2][0]=(-1)*Math.sin(a);  mat[2][1]=0.0;   mat[2][2]=Math.cos(a); mat[2][3]=0.0;
		mat[3][0]=0.0;               mat[3][1]=0.0;   mat[3][2]=0.0;         mat[3][3]=1.0;
	}
	public void setMatRotateZ(double a)
	{
		mat[0][0]=Math.cos(a);       mat[0][1]=Math.sin(a);  mat[0][2]=0.0;  mat[0][3]=0.0;
		mat[1][0]=(-1)*Math.sin(a);  mat[1][1]=Math.cos(a);  mat[1][2]=0.0;  mat[1][3]=0.0;
		mat[2][0]=0.0;               mat[2][1]=0.0;          mat[2][2]=1.0;  mat[2][3]=0.0;
		mat[3][0]=0.0;               mat[3][1]=0.0;          mat[3][2]=0.0;  mat[3][3]=1.0;
	}
	public void setMatSilumFix(double sX,double sY,double sZ,double fx,double fy,double fz)
	{ 
		mat[0][0]=sX;          mat[0][1]=0.0;         mat[0][2]=0.0;        mat[0][3]=0.0;
		mat[1][0]=0.0;         mat[1][1]=sY;          mat[1][2]=0.0;        mat[1][3]=0.0;
		mat[2][0]=0.0;         mat[2][1]=0.0;         mat[2][2]=sZ;         mat[2][3]=0.0;
		mat[3][0]=(1-sX)*fx;   mat[3][1]=(1-sY)*fy;   mat[3][2]=(1-sZ)*fz;  mat[3][3]=1.0;
	}
	public void setMatSilumFix(double sX,double sY,double sZ,Point3D p)
	{
		setMatSilumFix(sX,sY,sZ,p.x,p.y,p.z);
	}
	public void setMatRotateXFix(double a,double fx,double fy,double fz)
	{
		Matrix3D d=new Matrix3D();
		Matrix3D b=new Matrix3D();
		Matrix3D c=new Matrix3D();
		d.setMatMove(-1.0*fx, -1.0*fy,-1.0*fz);
		b.setMatRotateX(a);
		c.setMatMove(fx, fy,fz);
		d.mullMatMat(b);
    	d.mullMatMat(c);
		mat=d.mat;
		
	}
	public void setMatRotateXFix(double a,Point3D p)
	{
		setMatRotateXFix(a,p.x,p.y,p.z);
	}
	public void setMatRotateYFix(double a,double fx,double fy,double fz)
	{
		Matrix3D d=new Matrix3D();
		Matrix3D b=new Matrix3D();
		Matrix3D c=new Matrix3D();
		d.setMatMove(-1.0*fx, -1.0*fy,-1.0*fz);
		b.setMatRotateY(a);
		c.setMatMove(fx, fy,fz);
		d.mullMatMat(b);
		d.mullMatMat(c);
		mat=d.mat;
	}
	public void setMatRotateYFix(double a,Point3D p)
	{
		setMatRotateYFix(a,p.x,p.y,p.z);
	}
	public void setMatRotateZFix(double a,double fx,double fy,double fz)
	{
		Matrix3D d=new Matrix3D();
		Matrix3D b=new Matrix3D();
		Matrix3D c=new Matrix3D();
		d.setMatMove(-1.0*fx, -1.0*fy,-1.0*fz);
		b.setMatRotateZ(a);
		c.setMatMove(fx, fy,fz);
		d.mullMatMat(b);
		d.mullMatMat(c);
		mat=d.mat;
	}
	public void setMatRotateZFix(double a,Point3D p)
	{
		setMatRotateZFix(a,p.x,p.y,p.z);
	}

}