public class Chain<T>
{
	private Node<T> first;
	private Node<T> now;	
	public Chain()
	{
		this.now=null;
	}
	public boolean isEmpty()
	{
		boolean a;
		if (this.first==null)
		{
			a=true;
		}
		else
		{
			a=false;
		}
		return a;
	}
	public T getNow()
	{
		return this.now.getInfo();
	}
	public void Next()
	{
		 if (now.getNext()==null)
		 {
			 this.now=this.first;			 
		 }
		 else
		 {
			 this.now=this.now.getNext();
		 }
	}
	public void insert(T x)
	{
		if (this.first==null)
		{
			Node<T> temp = new Node<T>(x);
			temp.setNext(this.first);
			this.first=temp;
			this.now=this.first;
		}
		else
		{
			Node<T> temp = new Node<T>(x);
			temp.setNext(this.first);
			this.first=temp;	
		}
			
	}
	
	
}