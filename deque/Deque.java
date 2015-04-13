import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author schowdhury
 * Deque is a generalisation of stack and queue that supports
 * insertion and deletion at both ends
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item>{
	
	private int N;				//size of queue
	private Node first;			//Reference to first node - beginning of deque
	private Node last;			//Reference to last node - end of queue
	
	private class Node {		
		private Item item;
		private Node next;
		private Node prev;
		
		public Node(Item item) {
			this.item = item;
			this.next = null;
			this.prev = null;
		}
	}
	
	public Deque() {
		first = null;
		last = null;
		N = 0;
	}
	
	//Check if queue is empty
	public boolean isEmpty() {
		return size() == 0;
	}
	
	//Return size of queue
	public int size() {
		return N;
	}
	
	//Add Item to beginning of queue
	public void addFirst(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (isEmpty()) {
			first = new Node(item);
			last = first;
		} else {
			Node oldFirst = first;
			first = new Node(item);
			first.next = oldFirst;
			oldFirst.prev = first;
		}
		N++;
	}
	
	//Add item to end of queue
	public void addLast(Item item) {
		if (item == null) {
			throw new NullPointerException();
		}
		if (isEmpty()) {
			last = new Node(item);
			first = last;
		} else {
			Node oldLast = last;
			last = new Node(item);
			last.item = item;
			last.prev = oldLast;
			last.next = null;
			oldLast.next = last;
		}
		N++;
	}
	
	//Remove from beginning of queue
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque underflow");
		}
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) {
			first = null;
			last = null;
		} else {
			first.prev = null;
		}
		return item;
	}
	
	//Remove from end of queue
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("Deque underflow");
		}
		Item item = last.item;
		last = last.prev;
		N--;
		if (isEmpty()) {
			first = null;
			last = null;
		} else {
			last.next = null;
		}
		return item;
		
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

	public static void main(String[] args) {
		
		Deque<Integer> ndq = new Deque<Integer>();
		for (int i = 0; i < 10; i++) {
			ndq.addLast(i);
		}
		for (int n : ndq) {
			StdOut.println(n);
		}
	}
}
