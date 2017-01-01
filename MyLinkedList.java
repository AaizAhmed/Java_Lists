package lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {

	private int size;
	private int modCount = 0;
	private Node<T> beginMarker;
	private Node<T> endMarker;

	public MyLinkedList() 
	{	clear();	}
	
	/*
	 *
	 * ==> Copy Constructor	 
	 * 
	 */
	
	private class Node<T> {

		private Node<T> previous, next;
		private T data;

		public Node (T data, Node<T> prev, Node<T> next) {

			this.data = data;
			previous = prev;
			this.next = next;			
		}		
	}

	public void clear() {

		beginMarker = new Node<T> (null, null, null);
		endMarker = new Node<T> (null, beginMarker, null);
		beginMarker.next = endMarker;

		size = 0;
		modCount++;
	}	

	public boolean add (T data) {
		
		add(size, data);
		return true;
	}

	public void add (int index, T data) {

		addBefore (getNode(index), data);
	}

	private void addBefore (Node<T> current, T data) {

		Node<T> newNode = new Node<T> (data, current.previous, current);

		newNode.previous.next = newNode;
		current.previous = newNode;	

		size++;
		modCount++;
	}

	public T remove (int index) {

		return remove( getNode(index) );
	}

	private T remove (Node<T> current) {

		current.previous.next = current.next;
		current.next.previous = current.previous;
		size--;
		modCount++;	
		
		return current.data;
	}

	public T getValue (int index) 
	{	return getNode (index).data;	}
	
	private Node<T> getNode (int index) 
	{	return getNode(index, 0, size-1);	}

	private Node<T> getNode (int index, int lower, int upper) {

		inRange(index);

		Node <T> search;

		if (index < size/2) {

			search = beginMarker;
			for (int i = 0; i < index; i++) 
			{	search = search.next;	}
		}
		else {

			search = endMarker;
			for (int i = size; i > index; i--) 
			{	search = search.previous;	}
		}

		return search;	
	}

	public T setNode (int index, T newData) {
		
		inRange(index);
		
		Node<T> change = getNode(index);
		T oldData = change.data;
		change.data = newData;
		
		return oldData;
	}

	public int getSize() 
	{	return size;	}
	
	public boolean isEmpth() 
	{	return size == 0;	}

	
	public void inRange(int index) {
		
		if (index > size || index < 0) 
		{	throw new IndexOutOfBoundsException();	}
	}
	
	/*
	 
	 
	 ==> Add toString() 
	 
	 
	*/	
	
	public Iterator<T> iterator() 
	{	return new LinkedListIterator();	}

	private class LinkedListIterator implements Iterator<T> {

		private Node<T> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext() 
		{	return current != endMarker;	}

		
		public T next() {
			
			if ( expectedModCount != modCount) 
			{	throw new ConcurrentModificationException( );	}
			
			if( !hasNext( ) )
			{	throw new NoSuchElementException( );	}
			
			T nextItem = current.data;
			current = current.next;
			okToRemove = true;
			
			return nextItem;
		}
		
		public void remove () {
			
			if ( expectedModCount != modCount) 
			{	throw new ConcurrentModificationException( );	}
			
			if( !hasNext( ) )
			{	throw new NoSuchElementException( );	}
			
			if (okToRemove)
			{	MyLinkedList.this.remove(current.previous);   }
			
			expectedModCount++;
			okToRemove = false;
		}

	}



}
