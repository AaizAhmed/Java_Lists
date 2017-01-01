package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StackUsingLinkedList<T> implements Iterable<T> {
	
	private Node<T> front;
	private int size, modCount;
	
	/**
	 * Constructor:
	 */
	public StackUsingLinkedList () 
	{	doClear();	}
	
	/**
	 * Node class
	 */
	private class Node<T> {
		
		private Node<T> next;
		private T data;
		
		public Node (T data, Node<T> next) {
			
			this.data = data;
			this.next = next;
		}	
	}
	
	public int getSize() 
	{	return size;	}
	
	public boolean isEmpty()
	{	return size == 0;	}
	
	public void clear()
	{	doClear();	}
	
	private void doClear () {
		
		front = new Node<T> (null, null);		
		size = 0;
		modCount++;
	}
	
	public boolean push (T data) {
		
		pushElement(data);
		return true;
	}
	
	private void pushElement (T data) {
		
		Node<T> newNode = new Node<T> (data, front.next);
		front.next = newNode;
		
		size++;
		modCount++;
	}
	
	public T pop(){
		
		return popElement();
	}
	
	private T popElement() {
		
		if (isEmpty())
		{	throw new NoSuchElementException( );	}
		
		Node<T> removedNode = front.next;		
		front.next = removedNode.next;		
		size--;
		modCount++;
		
		return removedNode.data;
	}
	
	public T peek() 
	{	return getTop().data;	}
	
	private Node<T> getTop() {
		
		if (isEmpty())
		{	throw new NoSuchElementException( );	}
		
		Node<T> topNode = front.next;
		
		return topNode;
	}
	
	/**
	 * Returns a String representation of the Stack.
	 * 
	 * NOTE: If the data stored in the list is a custom Class/Object,
	 * 		 it needs to have it's own toString method. Otherwise the
	 * 		 memory address of those Objects will be printed and not
	 * 		 the actual content. 
	 */
	public String toString() {
		
		String str = "[";
		
		StackIterator itr = new StackIterator();
						
		while(itr.hasNext()) {	
			
			str += itr.next().toString();
			
			if (itr.hasNext())
			{	str += ", ";	}
		}
		
		str += "]";
		
		return str;
	}
	
	public Iterator<T> iterator() 
	{	return new StackIterator();   }
	
	private class StackIterator implements Iterator<T> {

		private Node<T> current = front.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;
	
		public boolean hasNext() 
		{	return current != null;	}

		public T next() {
	
			if (expectedModCount != modCount)
			{	throw new java.util.ConcurrentModificationException( );	}
			
			if (!hasNext())
			{	throw new NoSuchElementException();	}
			
			T nextItem = current.data;
			current = current.next;
			okToRemove = true;
			
			return nextItem;
		}
		
		public void remove() {
			
			if (expectedModCount != modCount) 
			{	throw new java.util.ConcurrentModificationException( );	}
				
			if( !hasNext( ) )
			{	throw new java.util.NoSuchElementException( );	}
				
			if (okToRemove)
			{	StackUsingLinkedList.this.popElement();   }
			
			expectedModCount++;
			okToRemove = false;			
		}		
	}

	public static void main (String [] args) {
		
		StackUsingLinkedList<Integer> stack = new StackUsingLinkedList<Integer>();
		
		stack.push(41);
		stack.push(42);
		stack.push(43);
		stack.push(44);
		stack.push(45);
		
		System.out.println (stack);
	}
	
}
