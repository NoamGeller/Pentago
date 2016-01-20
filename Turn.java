
public class Turn 
{
	private int sivovH,sivovW,putH,putW,grade,hgrade;	
	private boolean clocklWise;
	public static final int StartHGrade=-100000;
	public Turn(int sivovH,int sivovW,boolean plusOrMinus,int putH,int putW,int grade)
	{
		this(sivovH, sivovW, plusOrMinus, putH, putW, grade, 0);
	}
	public Turn(int sivovH,int sivovW,boolean plusOrMinus,int putH,int putW,int grade,int hgrade)
	{
		this.sivovH=sivovH;
		this.sivovW=sivovW;
		this.putH=putH;
		this.putW=putW;
		this.clocklWise=plusOrMinus;
		this.grade=grade;
		this.hgrade=hgrade;
		
	}
	public Turn(Turn a)
	{
		this.sivovH=a.sivovH;
		this.sivovW=a.sivovW;
		this.putH=a.putH;
		this.putW=a.putW;
		this.clocklWise=a.clocklWise;
		this.grade=a.grade;
		this.hgrade=a.hgrade;
	}
	public Turn()
	{
		this.sivovH=0;
		this.sivovW=0;
		this.putH=-1;
		this.putW=0;
		this.clocklWise=true;
		this.grade=0;
		this.hgrade=StartHGrade;
		
	}	
	public boolean getPlusOrMinus() 
	{
		return clocklWise;
	}
	public void setPlusOrMinus(boolean plusOrMinus) 
	{
		this.clocklWise = plusOrMinus;
	}
	public int getPutH() 
	{
		return putH;
	}
	public void setPutH(int putH) 
	{
		this.putH = putH;
	}
	public int getPutW() 
	{
		return putW;
	}
	public void setPutW(int putW) 
	{
		this.putW = putW;
	}
	public int getSivovH() 
	{
		return sivovH;
	}
	public void setSivovH(int sivovH) 
	{
		this.sivovH = sivovH;
	}
	public int getSivovW() 
	{
		return sivovW;
	}
	public void setSivovW(int sivovW)
	{
		this.sivovW = sivovW;
	}
	public int getGrade() 
	{
		return grade;
	}
	public void setGrade(int grade) 
	{
		this.grade = grade;
	}
	public String toString()
	{
		return "Grade "+this.grade+" Heuristic grade "+this.hgrade+" PUT high "+this.putH+" width "+this.putW+" ROTETE high "+this.sivovH+" width "+this.sivovW+" "+ this.clocklWise;
	}
	public int getHgrade() {
		return hgrade;
	}
	public void setHgrade(int hgrade) {
		this.hgrade = hgrade;
	}
}
