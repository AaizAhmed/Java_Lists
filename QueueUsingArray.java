package lists;
//package Java_Lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")

public class QueueUsingArray<T> implements Iterable<T> {

	private T [] theArray;
	private int currentSize, frontIndex, rearIndex;
	private final int DEFAULT_CAPACITY = 10; 

	public QueueUsingArray ()
	{	doClear(DEFAULT_CAPACITY);		}

	public QueueUsingArray (int size)
	{	doClear(size);		}

	public void clear () 
	{	doClear(DEFAULT_CAPACITY);		}	

	private void doClear(int size) {

		currentSize = frontIndex = 0;
		rearIndex = -1;

		if (size > DEFAULT_CAPACITY) 
		{	theArray = (T[]) new Object[size];	} 
		else
		{	theArray = (T[]) new Object[DEFAULT_CAPACITY];	}
	}

	public boolean isEmpty() 
	{ return currentSize == 0;	}

	public int getSize()
	{	return currentSize;	}

	public boolean enqueue (T data) {

		enqueueElement(data);
		return true;
	}

	private void enqueueElement(T data) {

		if (theArray.length == currentSize)
		{	increaseSize (theArray.length*2 + 1);		}		


		rearIndex = incrementIndex(rearIndex);
		theArray[rearIndex] = data;

		currentSize++;
	}

	private void increaseSize(int newSize) {		

		T[] oldArray = (T[]) new Object[newSize];


		System.arraycopy(theArray, frontIndex, oldArray, frontIndex, theArray.length-frontIndex);

		if (frontIndex != 0) {

			System.arraycopy(theArray, 0, oldArray, theArray.length, frontIndex);  

			//i = 0 ; i < frontIndex
			//oldArray[theArray.length+i] = theArray[i];
		}

		theArray = oldArray;
		rearIndex = frontIndex + currentSize-1;
	}

	private int incrementIndex(int index) {

		if (index == theArray.length-1) return 0;
		else return index + 1;
	}

	public T dequeue ()
	{	return dequeueElement();	}	

	private T dequeueElement() {

		if (isEmpty())
		{	throw new NoSuchElementException();   } 

		T removedItem = theArray[frontIndex];
		theArray[frontIndex] = null;

		frontIndex = incrementIndex(frontIndex);
		currentSize--;

		return removedItem;
	}

	public T peek() 
	{	return getTop();	}

	private T getTop() {

		if (isEmpty())
		{	throw new NoSuchElementException();   } 

		return theArray[frontIndex];
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

		int items = 0;		
		if (rearIndex < frontIndex) {

			for (int i = frontIndex; i < theArray.length-1; i++) {

				str += theArray[i];

				if (items < currentSize)
				{	str+= ", ";	}
				items++;
			}

			for (int i = 0; i < rearIndex + 1; i++) {				

				str += theArray[i];

				if (items < currentSize)
				{	str+= ", ";	}
				items++;
			}


		}
		else if (frontIndex < rearIndex) {

			for (int i = 0; i < rearIndex+1; i++) {

				str += theArray[i];

//				if (items < currentSize)
//				{	str+= ", ";	}
				items++;
			}

		}

		if (frontIndex == rearIndex) 
		{	str+= theArray[frontIndex];   }

		str += "]";

		return str;
	}


	public Iterator<T> iterator() 
	{	return new QueueWithArrayIterator();	}

	private class QueueWithArrayIterator implements Iterator<T> {

		private int current = 0;

		public boolean hasNext() 
		{	return current < currentSize;	}


		public T next() {

			if (!hasNext())
			{	throw new NoSuchElementException( );	}

			int index = (current + frontIndex) % theArray.length;			
			T item = theArray[index];
			current++;

			return item;			
		}	

		public void remove() 
		{	QueueUsingArray.this.dequeue();		}
	}


	public static void main (String [] args) {

		QueueUsingArray<Integer> queue = new QueueUsingArray<Integer>(25);

		for (int i = 0; i < 1; i++ ) {
			queue.enqueue(i);
		}	

//		for (int i = 0; i < 7; i++ ) {
//			queue.dequeue();
//		}	
//
//		for (int i = 0; i < 12; i++ ) {
//			queue.enqueue(i);
//		}	

		System.out.println(queue);
	}

}
