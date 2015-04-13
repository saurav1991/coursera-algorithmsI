import java.util.Iterator;
import java.util.NoSuchElementException;

/** RandomizedQueue is a generic array-implemented queue that
 * will dequeue items at random. It will also iterate at random. */
public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int first = 0;       // index of first element of queue
    private int last  = 0;       // index of next available slot
	
	/** Constructor to create a new randomised queue. */
	public RandomizedQueue() {
		q = (Item[]) new Object[2];
	}
	
	/** Returns true if the queue is empty. */
	public boolean isEmpty() {
		return N == 0;
	}
	
	/** Returns the size of the queue. */
	public int size() {
		return N;
	}
	
	 // resize the underlying array
    private void resize(int max) {
        assert max >= N;
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last  = N;
    }
	
	/** Adds a new item to the queue. */
	public void enqueue(Item item) {
		if (item == null) {
			throw new NullPointerException("Cannot enqueue null objects.");
		}
		
		if (N == q.length) {
			resize(2 * q.length);
		}
		
		q[last++] = item;
		
		N++;
	}
	
	/** Removes and returns an item at random from the queue. */
	public Item dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is currently empty.");
		}
		
		int rand = StdRandom.uniform(N);
		Item dequeued = q[rand];
		
		q[rand] = q[--last];
		q[last] = null;
		N--;
		
		if (N > 0 && N == q.length/4) resize(q.length/2); 
		
		return dequeued;
	}
	
	/** Returns an item at random without deleting it. */
	public Item sample() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is currently empty.");
		}
		int rand = StdRandom.uniform(N);
		return q[rand];
	}
	
	/** Returns an iterator object to allow random iteration through queue. */
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item> {
		
		private Item[] iteratorQueue;
		private int index = 0;
		
		public ArrayIterator() {
			
			iteratorQueue = (Item[]) new Object[N];
			
			//Copy items into iterator queue
			for(int i = 0; i < iteratorQueue.length; i++) {
				iteratorQueue[i] = q[i];
			}
			StdRandom.shuffle(iteratorQueue);
		}
		
		@Override
		public boolean hasNext() {
			return (index < iteratorQueue.length);
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No more objects to iterate through");
			}
			
			Item item = iteratorQueue[index];
			index++;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove method not supported");
		}
	}
	
	public static void main(String[] args) {
		RandomizedQueue<Integer> nrdq = new RandomizedQueue<Integer>();
		nrdq.enqueue(1);
		nrdq.enqueue(2);
		nrdq.enqueue(3);
		nrdq.enqueue(4);
		
		StdOut.println(nrdq.dequeue());
		StdOut.println(nrdq.dequeue());
		StdOut.println(nrdq.dequeue());
	}
}
