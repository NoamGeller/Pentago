
public class Square 
{
	int side;
	boolean win;
	public Square(int side,boolean win)
	{
		this.side=side;
		this.win=win;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public boolean isBelongToPlayer(int side)
	{
		return this.side==side;
	}
}
