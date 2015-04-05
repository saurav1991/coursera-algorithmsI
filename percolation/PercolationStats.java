
public class PercolationStats {

	private int gridSize;
	private int rounds;
	private double[] results;
	private Percolation perc;
	
	/**
	 * Constructor which performs monte carlo simulation
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) {
			throw new IllegalArgumentException("N and T should be greater than 0");
		}
		gridSize = N * N;
		rounds = T;
		results = new double[rounds];
		
		for (int i = 0; i < rounds; i++) {
			perc = new Percolation(N);
			int count = 0;
			while (!perc.percolates()) {
				int x =  1 + StdRandom.uniform(N);
				int y = 1 + StdRandom.uniform(N);
				if (!perc.isOpen(x, y)) {
					perc.open(x, y);
					count++;
				}
			}
			results[i] = (double) count / gridSize;
		}
	}
	
	public double mean() {
		return StdStats.mean(results);
	}
	
	public double stddev() {
		return StdStats.stddev(results);
	}
	
	public double confidenceLo() {
		return (mean() - (1.96 * stddev())/Math.sqrt(rounds));
	}
	
	public double confidenceHi() {
		return (mean() + (1.96 * stddev())/Math.sqrt(rounds));
	}
	
	public static void main(String[] args) {
		
		PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		StdOut.println("mean                 = " + percStats.mean());
		StdOut.println("stddev               = " + percStats.stddev());
		StdOut.println("confidenceLo         = " + percStats.confidenceLo());
		StdOut.println("confidenceHi         = " + percStats.confidenceHi());
	}

}
