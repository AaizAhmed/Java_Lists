package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T> {	

	private final int DEFAULT_CAPACITY = 10;
	private T [] arrayList;
	private int size;

	//Constructor of the class.
	public MyArrayList() {	doClear(); }
	
//-------------------------------------------------------------------------
//	Next ==> 1 Add a deep copy constructor.
	
//	==> 2 Finish toString
//-------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	private void increaseSize (int newSize) {

		if (newSize < size) { return; }

		T [] oldArray = arrayList;		
		arrayList = (T[]) new Object [newSize];

		for (int i = 0; i < size; i++) {

			arrayList[i] = oldArray[i];
		}
	}

	public int getSize() {  return size;  }
	
	/* Trim to Size from Java Docs using Arrays
	public void trimToSize( )	{ 
		
		int oldSize = arrayList.length;
		
		if (size < oldSize) {
			
			arrayList = Arrays.copyOf(arrayList, size);
		}		
	}
	*/
	
	/*
	 * Trim to size reduces the length of array to the current size.
	 */
	public void trimToSize( )	{ 
				
		T[] oldList = (T[]) new Object[size];
		
		for (int i =0; i < size; i++) 
		{	oldList[i] = arrayList[i];		}
		
		arrayList = oldList;
	}

	public boolean isEmpty() {

		if (size == 0) { return true; }

		return false;
	}

	//Return the item at the specified index if the index is valid.
	public T get(int index) {

		if (index < size) {  return arrayList[index];  }

		else throw new NoSuchElementException();
	}

	/**
	 * Assigns the new value at the specified index.
	 * @param index
	 * @param item
	 * @return old item
	 */
	public T set(int index, T item) {

		if (index < 0 || index > size) {  throw new ArrayIndexOutOfBoundsException();  }

		T oldItem = arrayList[index];
		arrayList[index] = item;

		return oldItem;		
	}

	/**
	 * Calls the private doClear method to clear the contents of the array.
	 */
	public void clear() { doClear(); }

	/**
	 * Clears the contents of the array and changes the size to 0.
	 */
	@SuppressWarnings("unchecked")
	private void doClear() {

		size = 0;
		
		if (arrayList == null || arrayList.length <  DEFAULT_CAPACITY)
			{  arrayList = (T[]) new Object[ DEFAULT_CAPACITY];   }
		
		else {  arrayList = (T[]) new Object[arrayList.length];  }		 
	}

	public void add (T item) {

		add(size, item);
	}

	public void add(int index, T item) {

		if (arrayList.length == size) {  increaseSize(size*2 + 1);  }

		for (int i = size; i > index; i--) 
			{  arrayList[i] = arrayList[i-1];  }
		
		arrayList[index] = item;	
		size++;
	}

	public T remove(int index) {

		if (index < 0 || index > size) {  throw new IndexOutOfBoundsException();  }
		
		T removedItem = arrayList[index];
		
		for (int i = index; i < arrayList.length-1; i++) 
			{  arrayList[i] = arrayList[i+1];  }
		
		size--;
		return removedItem;
	}
	
	public Iterator<T> iterator() {  return new MyArrayListIterator();  }

	/*
	 * Iterator object for the ArrayList class.
	 */
	private class MyArrayListIterator implements Iterator<T> {

		private int current = 0;
		
		//Tells if there is a next element in the list.
		public boolean hasNext() { return current < size;  }		

		//Returns the next element in the list.
		public T next() {
			
			if ( !hasNext() ) {  System.out.println("There is no next element in the list.");  }
			
			return (T) arrayList[current++];
		}
		
		//Removes the current element of the list.
		public void remove () {  MyArrayList.this.remove(--current);  }

	}
	
	public String toString () {
		
		String str = "";
		
		return str;
	}
	
	public static void main (String [] args) {
		
		MyArrayList<String> a = new MyArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			
			a.add("Hello " + i);
		}
		
//		System.out.println(a.getSize());
		
		a.remove(5);
		a.remove(3);
		a.remove(7);
		
		a.trimToSize();
	}


}
