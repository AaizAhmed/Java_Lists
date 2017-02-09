//package lists;
package Java_Lists;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {

	//Declaring variables used internally
	private int size;
	private int modCount = 0;
	private Node<T> beginMarker;
	private Node<T> endMarker;

	/**
	 * Constructor: Instantiates a linked list.
	 */
	public MyLinkedList() 
	{	doClear();	}
	
	/**
	 * Copy Constructor: It creates an identical copy of the 
	 * 					 list passed as argument.
	 * @param otherList: List to be copied.
	 */
	public MyLinkedList(MyLinkedList<T> otherList) {
           	    			
		doClear();
		
		for (T item: otherList)	
		{	add(item);	} 	
    }
	
	/**
	 * Internal Node class. It contains data and links to the next 
	 * and previous nodes.
	 * @author Aaiz N Ahmed
	 */
	private class Node<T> {

		private Node<T> previous, next;
		private T data;

		/**
		 * Constructor: Takes a data type and two node.
		 * @param data
		 * @param prev
		 * @param next
		 */
		public Node (T data, Node<T> prev, Node<T> next) {

			this.data = data;
			previous = prev;
			this.next = next;			
		}		
	}
	
	public void clear()
	{	doClear();	}

	/**
	 * Initiates the linked list by setting up the initial 
	 * references for first and last nodes. 
	 */
	private void doClear() {

		beginMarker = new Node<T> (null, null, null);
		endMarker = new Node<T> (null, beginMarker, null);
		beginMarker.next = endMarker;

		size = 0;
		modCount++;
	}	

	/**
	 * Adds an item to the linked list
	 * @param data
	 * @return
	 */
	public boolean add (T data) {
		
		add(size, data);
		return true;
	}
	
	/**
	 * Add an item at a specified index.
	 * @param index
	 * @param data
	 */
	public void add (int index, T data) {

		addBefore (getNode(index), data);
	}

	/**
	 * Private method to add a node to the list
	 * @param current
	 * @param data
	 */
	private void addBefore (Node<T> current, T data) {

		Node<T> newNode = new Node<T> (data, current.previous, current);

		newNode.previous.next = newNode;
		current.previous = newNode;	

		size++;
		modCount++;
	}
	
	/**
	 * Removes the first node and returns the value.
	 * @return data stored in the node.
	 */
	public T remove () 
	{
		Node<T> current = beginMarker.next;
		
		current.previous.next = current.next;
		current.next.previous = current.previous;
		size--;
		modCount++;	
		
		return current.data;
	}

	/**
	 * Remove an item from a given index
	 * @param index
	 * @return
	 */
	public T remove (int index) {

		return remove( getNode(index) );
	}

	/**
	 * Private method to remove a node and delete it's 
	 * references.
	 * @param current
	 * @return data of the removed node.
	 */
	private T remove (Node<T> current) {

		current.previous.next = current.next;
		current.next.previous = current.previous;
		size--;
		modCount++;	
		
		return current.data;
	}

	/**
	 * Returns the data stored in a node.
	 * @param index
	 * @return
	 */
	public T getValue (int index) 
	{	return getNode (index).data;	}
	
	/**
	 * Private method used to search for a node in the list
	 * @param index
	 * @return node
	 */
	private Node<T> getNode (int index) 
	{	return getNode(index, 0, size-1);	}

	/**
	 * This method performs the search to find the node. The search can 
	 * begin at the start of the list or at the end of the list.
	 * @param index
	 * @param lower 
	 * @param upper
	 * @return node
	 */
	private Node<T> getNode (int index, int lower, int upper) {

		inRange(index);

		Node <T> search;

		// Searching from the beginning of the list.
		if (index < size/2) {

			search = beginMarker.next;
			for (int i = 0; i < index; i++) 
			{	search = search.next;	}
		} 
		else { // Searching from the end of the list.

			search = endMarker;
			for (int i = size; i > index; i--) 
			{	search = search.previous;	}
		}

		return search;	
	}

	/**
	 * Change/Set the value of an existing node.
	 * @param index
	 * @param newData
	 * @return The old data that was removed
	 */
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

	/**
	 * Checks if the index in less then the size of the list
	 * and not less then zero.
	 * @param index
	 */
	public void inRange(int index) {
		
		if (index > size || index < 0) 
		{	throw new IndexOutOfBoundsException();	}
	}
	
	/**
	 * Returns a String representation of the LinkedList.
	 * 
	 * NOTE: If the data stored in the list is a custom Class/Object,
	 * 		 it needs to have it's own toString method. Otherwise the
	 * 		 memory address of those Objects will be printed and not
	 * 		 the actual content. 
	 */	
	public String toString() {
		
		String str = "[";
		
		LinkedListIterator itr = new LinkedListIterator();
						
		while(itr.hasNext()) {	
			
			str += itr.next().toString();
			
			if (itr.hasNext())
			{	str += ", ";	}
		}
		
		str += "]";
		
		return str;
	}
	
	public Iterator<T> iterator() 
	{	return new LinkedListIterator();	}

	/**
	 * A private internal class that implements an iterator to go over the list.
	 * @author Aaiz N Ahmed
	 */
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

	public static void main (String [] args) {
		
		MyLinkedList<Integer> list = new MyLinkedList<Integer>();
		
		list.add(41);
		list.add(42);
		list.add(43);
		list.add(44);
		list.add(45);
		
		MyLinkedList<Integer> list2 = new MyLinkedList<Integer>(list);
		
		System.out.println (list);
		
		list.remove(0);
		list2.add(88);
		
		System.out.println (list);
		System.out.println (list2);	
	}
}
