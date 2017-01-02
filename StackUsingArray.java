package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")

public class StackUsingArray<T> implements Iterable<T> {

	private T [] theArray;
	private int topOfStack, size;
	private final int DEFAULT_CAPACITY = 10; 
	
	public StackUsingArray ()
	{	doClear();		}
	
	public void clear () 
	{	doClear();		}	
	
	private void doClear() {
		
		topOfStack = -1;
		size = 0;
		theArray = (T[]) new Object[DEFAULT_CAPACITY];
	}
	
	public boolean push (T data) {
		
		pushElement(data);
		return true;
	}

	private void pushElement(T data) {
		
		if (theArray.length == topOfStack)
		{	increaseSize(size*2 + 1);		}
		
		topOfStack++;
		theArray[topOfStack] = data;	
		size++;
	}
	
	private void increaseSize(int newSize) {
		
		if (newSize < size)
		{	return;		}
		
		T[] oldArray = theArray;
		theArray = (T[]) new Object[newSize];
		
		for (int i = 0; i < size; i++)
		{
			theArray[i] = oldArray[i];
		}		
	}
	
	public void trimToSize(){
		
		// Can not do: " T[] oldArray = theArray; " because you will create an
		// alias with the current length of theArray instead of a new Array 
		// with the length equal to size.
		
		T[] oldArray = (T[]) new Object[size];
		
		for (int i= 0; i < size; i++)
		{
			theArray[i] = oldArray[i];
		}
	}

	public T pop()
	{	return popElement();	}	

	private T popElement() {
		
		if (isEmpty())
		{	throw new NoSuchElementException();   } 
			
		T removedItem = theArray[topOfStack];
		topOfStack--;
		size--;
		
		return removedItem;
	}
	
	public T peek() 
	{	return getTop();	}
	
	private T getTop() {
		
		if (isEmpty())
		{	throw new NoSuchElementException();   } 
		
		return theArray[topOfStack];
	}

	public boolean isEmpty()
	{	return size == 0;	}
	
	public int getSize()
	{	return size;	}

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
		
		StackWithArrayIterator itr = new StackWithArrayIterator();
		
		while (itr.hasNext()) {
			
			str += itr.next().toString();
			
			if(itr.hasNext())
			{ str+= ", ";	}					
		}
		
		str += "]";
		
		return str;
	}
	
	public Iterator<T> iterator() {
		
		return new StackWithArrayIterator();
	}
	
	private class StackWithArrayIterator implements Iterator<T> {
		
		private int current = topOfStack;
		
		public boolean hasNext() 
		{	return current > -1;	}

		public T next() {

			if (!hasNext())
			{	throw new NoSuchElementException( );	}
			
			return theArray[current--];
		}
		
		public void remove() 
		{	StackUsingArray.this.pop();		}		
	}
	
	public static void main (String [] args) {
		
		StackUsingArray<Integer> stack = new StackUsingArray<Integer>();
		
		stack.push(41);
		stack.push(42);
		stack.push(43);
		stack.push(44);
		stack.push(45);
		
		System.out.println (stack);
		
		Iterator<Integer> itr = stack.iterator();
				
		while(itr.hasNext()) {
			
			System.out.println (itr.next());
			itr.remove();
		}
		
		System.out.println (stack);

	}
}
