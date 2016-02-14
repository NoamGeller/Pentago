/**
 * Encapsulates a computer's move. "Turn" might refer to it as well.
 */
public class Move 
{
	private int rotationH,rotationW,stoneH,stoneW,grade,hgrade;	
	private boolean isClockwise;
	public static final int StartHGrade=-100000;
	public Move(int rotationH,int rotationW,boolean isClockwise,int stoneH,int stoneW,int grade)
	{
		this(rotationH, rotationW, isClockwise, stoneH, stoneW, grade, 0);
	}
	public Move(int rotationH,int rotationW,boolean isClockwise,int stoneH,int stoneW,int grade,int hgrade)
	{
		this.rotationH=rotationH;
		this.rotationW=rotationW;
		this.stoneH=stoneH;
		this.stoneW=stoneW;
		this.isClockwise=isClockwise;
		this.grade=grade;
		this.hgrade=hgrade;
		
	}
	public Move(Move a)
	{
		this.rotationH=a.rotationH;
		this.rotationW=a.rotationW;
		this.stoneH=a.stoneH;
		this.stoneW=a.stoneW;
		this.isClockwise=a.isClockwise;
		this.grade=a.grade;
		this.hgrade=a.hgrade;
	}
	public Move()
	{
		this.rotationH=0;
		this.rotationW=0;
		this.stoneH=-1;
		this.stoneW=0;
		this.isClockwise=true;
		this.grade=0;
		this.hgrade=StartHGrade;
		
	}	
	public boolean isClockwise() 
	{
		return isClockwise;
	}
	public void setIsClockwise(boolean isClockwise) 
	{
		this.isClockwise = isClockwise;
	}
	public int getStoneH() 
	{
		return stoneH;
	}
	public void setStoneH(int stoneH) 
	{
		this.stoneH = stoneH;
	}
	public int getStoneW() 
	{
		return stoneW;
	}
	public void setStoneW(int stoneW) 
	{
		this.stoneW = stoneW;
	}
	public int getRotationH() 
	{
		return rotationH;
	}
	public void setRotationH(int rotationH) 
	{
		this.rotationH = rotationH;
	}
	public int getRotationW() 
	{
		return rotationW;
	}
	public void setRotationW(int rotationW)
	{
		this.rotationW = rotationW;
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
		return "Grade "+this.grade+" Heuristic grade "+this.hgrade+" PUT height "+this.stoneH+" width "+this.stoneW+" ROTATE height "+this.rotationH+" width "+this.rotationW+" "+ this.isClockwise;
	}
	public int getHgrade() {
		return hgrade;
	}
	public void setHgrade(int hgrade) {
		this.hgrade = hgrade;
	}
}