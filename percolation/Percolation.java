
/**
 * @author schowdhury
 * Percolation class to check if grid percolates or not
 */
public class Percolation
{	
	private int rowLen;
	private int gridSize;
	private boolean[][] grid;
	private int topIndex;
	private WeightedQuickUnionUF uf;
	
	/**
	 * Constructor fot the class
	 * @param N
	 */
	public Percolation(int N)
	{
		if (N <= 0)
		{
			throw new IllegalArgumentException("Grid size cannot be negative");
		}
		gridSize = N * N;
		rowLen = N;
		grid = new boolean[N][N];
		topIndex = gridSize;
		uf = new WeightedQuickUnionUF(gridSize + 1);
	}
	
	/**
	 * converts 2D grid coordinates to 1D array coordinates
	 * @param i
	 * @param j
	 * @return
	 */
	private int get1DCoordinates(int i, int j)
	{
		return i * rowLen + j;
	}
	
	/**
	 * Checks for valid input coordinates
	 * @param i
	 * @param j
	 */
	private void checkInputs(int i, int j)
	{
		if (i < 1 || i > rowLen) {
			throw new IndexOutOfBoundsException("i must be between 1 and " + rowLen);
		}
		if (j < 1 || j > rowLen) {
			throw new IndexOutOfBoundsException("j must be between 1 and " + rowLen);
		}
	}
	
	/**
	 * Opens a cell at the given coordinates
	 * @param i
	 * @param j
	 */
	public void open(int i, int j)
	{
		checkInputs(i, j);
		
		i = i - 1;
		j = j - 1;
		
		grid[i][j] = true;
		
		int index = get1DCoordinates(i, j);
		
		//If cell in top row connect to topindex;
		
		if (i == 0) {
			uf.union(index, topIndex);
		}
		
		if (isNeighbourOpen(i, j - 1)) {
			uf.union(index, get1DCoordinates(i, j - 1));
		}
		if (isNeighbourOpen(i + 1, j)) {
			uf.union(index, get1DCoordinates(i + 1, j));
		}
		if (isNeighbourOpen(i, j + 1)) {
			uf.union(index, get1DCoordinates(i, j + 1));
		}
		if (isNeighbourOpen(i - 1, j)) {
			uf.union(index, get1DCoordinates(i - 1, j));
		}
	}
	
	/**
	 * Checks if neighbouring cell is open
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean isNeighbourOpen(int i, int j)
	{
		if (i < 0 || i > rowLen - 1) {
			return false;
		}
		if (j < 0 || j > rowLen - 1) {
			return false;
		}
		return grid[i][j];
	}
	
	/**
	 * Checks if cell is open
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j) {
		checkInputs(i, j);
		i = i - 1;
		j = j - 1;
		return grid[i][j];
	}
	
	/**
	 * Checks if cell is full
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j)
	{
		checkInputs(i, j);
		int index = get1DCoordinates(i - 1, j - 1);
		return uf.connected(index, topIndex);
	}
	
	/**
	 * Checks if grid percolates
	 * @return
	 */
	public boolean percolates()
	{
		for (int j = 0; j < rowLen; j++) {
			if (isFull(rowLen, j + 1)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		int N = StdIn.readInt();
		Percolation perc = new Percolation(N);
		perc.open(1, 1);
		perc.open(2, 2);
		StdOut.println("percolates " + perc.percolates());
	}
}