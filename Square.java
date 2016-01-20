
public class Square 
{
	int color;
	boolean win;
	public Square(int color,boolean win)
	{
		this.color=color;
		this.win=win;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public boolean belongsToPlayer(int color)
	{
		return this.color==color;
	}
}
