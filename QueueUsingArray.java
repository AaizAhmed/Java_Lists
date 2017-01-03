//package lists;
package Java_Lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")

public class QueueUsingArray<T> implements Iterable<T> {

	private T [] theArray;
	private int currentSize, frontIndex, rearIndex;
	private final int DEFAULT_CAPACITY = 10; 
	
	public QueueUsingArray ()
	{	doClear();		}
	
	public void clear () 
	{	doClear();		}	
	
	private void doClear() {
		
		currentSize = frontIndex = 0;
		rearIndex = -1;
		
		theArray = (T[]) new Object[DEFAULT_CAPACITY];
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
				
		//System.out.println("Front: " + frontIndex + " Rear: " + rearIndex);
			
		currentSize++;
	}
	
	private void increaseSize(int newSize) {		
		
		T[] oldArray = (T[]) new Object[newSize]; 
		
		System.arraycopy(theArray, frontIndex, oldArray, frontIndex, theArray.length-frontIndex);
		
		if (frontIndex != 0) {
			
		    System.arraycopy(theArray, 0, oldArray, theArray.length, frontIndex);  
		    
		    //i = 0 ; i < frontIndex
		    // oldArray[theArray.length+i] = theArray[i];
		}
		
	    theArray = oldArray;
		rearIndex = frontIndex + currentSize-1;
	}
	
	private void expandCapacity (int newSize) {
		
		T[] largerArray = (T[]) new Object[newSize];
		
		int size = theArray.length;
		
		for (int i = 0; i < currentSize; i++) {
			
			largerArray[i] = theArray[frontIndex];
			frontIndex = (frontIndex + 1) % size;
		}
		
		frontIndex = 0;
		rearIndex = currentSize-1;
		theArray = largerArray;		
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
		
		QueueWithArrayIterator itr = new QueueWithArrayIterator();
		
		while (itr.hasNext()) {
			
			str += itr.next().toString();
			
			if(itr.hasNext())
			{ str+= ", ";	}					
		}
		
//		int items = 0;			
//		
//		if (frontIndex < rearIndex) {
//		
//		for (int i = frontIndex; i < rearIndex + 1; i++) {				
//			
//			str += theArray[i];
//			
//			if (items < currentSize-1)
//			{	str+= ", ";	}
//			items++;
//		}
//		}
//		
//		if (rearIndex < frontIndex) {
//		
//		for (int i = rearIndex; i < theArray.length; i++) {
//			
//			str += theArray[i];
//			
//			if (items < currentSize-1)
//			{	str+= ", ";	}
//			items++;
//		}
//		
//		 for (int i = 0; i < rearIndex+1; i++) {
//			 
//			 str += theArray[i];
//				
//				if (items < currentSize-1)
//				{	str+= ", ";	}
//				items++;
//		 }
//		}
//		
//		if (frontIndex == rearIndex) 
//		{	str+= theArray[frontIndex];   }
//
//				
		str += "]";
		
		return str;
	}

	
	public Iterator<T> iterator() {
		
		return new QueueWithArrayIterator();
	}
	
	private class QueueWithArrayIterator implements Iterator<T> {

		private int current = 0;
		private int size = theArray.length;
				
//		public QueueWithArrayIterator() 
//		{	mapQueue();		}
//		
//		int items = 0;
//		public void mapQueue () {
//			
//			if(!isEmpty()) {
//			if (frontIndex < rearIndex) {
//				
//				for (int i = frontIndex; i < rearIndex + 1; i++) 
//				{	indexes[items++] = i;	}
//			 }
//			
//			if (rearIndex < frontIndex) {
//				
//				for (int i = frontIndex; i < theArray.length; i++) 
//				{	indexes[items++] = i;	}
//					
//				 for (int i = 0; i < rearIndex+1; i++) 
//				 {	indexes[items++] = i;	}
//			}
//			
//			if (frontIndex == rearIndex) 
//			{	indexes[items++] = frontIndex;   }
//		 }
//			
//		}		
//		
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
		
		QueueUsingArray<Integer> queue = new QueueUsingArray<Integer>();
		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);
		queue.enqueue(6);
		queue.enqueue(7);
		
		queue.dequeue();
		queue.dequeue();
		queue.dequeue();
		
		queue.enqueue(8);
		queue.enqueue(9);
		queue.enqueue(10);
		queue.enqueue(11);
		queue.enqueue(12);
		queue.enqueue(13);
		queue.enqueue(14);
		queue.enqueue(15);
		
		System.out.println (queue.getSize());
		
		Iterator<Integer> itr = queue.iterator();
		
//		while(itr.hasNext()) {
//			
//			System.out.println (queue.dequeue());
//			
//		}
		
//		int  h = queue.getSize();
//				
//		for (int i = 0; i  < h; i++) {
//			
//			queue.dequeue();
//		}
//		
//		queue.dequeue();
//		queue.dequeue();
//		queue.dequeue();
//		queue.dequeue();
		
		System.out.println (queue);

	}

}
