
public class Subset {
	
	public static void main(String[] args) {
		
		RandomizedQueue<String> randq = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
		
		while(!StdIn.isEmpty()) {
			randq.enqueue(StdIn.readString());
		}
		for (int i = 0; i < k; i++) {
			StdOut.println(randq.dequeue());
		}
	}

}
