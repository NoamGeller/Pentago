/**
 * Implementation of the data structure for nodes.
 * @author Noam Wies
 *
 * @param <T>
 */
public class Node<T>
{
	private T info;
	private Node<T> next;
	public Node(T x)
	{
		this.info=x;
		this.next=null;
	}
	public Node(T x,Node<T> next)
	{
		this.info=x;
		this.next=next;
	}
	public Node<T> getNext()
	{
		return this.next;
	}
	public void setNext(Node<T> next)
	{
		this.next=next;
	}
	public T getInfo()
	{
		return this.info;
	}
	public void setInfo(T x)
	{
		this.info=x;
	}
	public String toString()
	{
		return this.info.toString();
	}
}
