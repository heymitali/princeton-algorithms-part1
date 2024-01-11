package Assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] sites;
    private int openSites = 0;
    private final WeightedQuickUnionUF uf;
    private final int size;
    private final int topPoint;
    private final int bottomPoint;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
        size = n;
        uf = new WeightedQuickUnionUF((size * size) + 2);
        sites = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sites[i][j] = false;
            }
        }
        topPoint = (size * size) + 1;
        bottomPoint = size * size;

        for (int i = 0; i < sites.length; i++) {
            int index1 = coordinatesToIndex(0, i);
            uf.union(topPoint, index1);
            int index2 = coordinatesToIndex(size - 1, i);
            uf.union(bottomPoint, index2);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row--;
        col--;

        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException("Invalid Input");
        }

        if (isOpen(row + 1, col + 1)) {
            return;
        }

        sites[row][col] = true;
        openSites++;
        if ((row + 1) < size && sites[row + 1][col]) {
            uf.union(coordinatesToIndex(row, col), coordinatesToIndex(row + 1, col));
        }
        if ((row - 1) >= 0 && sites[row - 1][col]) {
            uf.union(coordinatesToIndex(row, col), coordinatesToIndex(row - 1, col));
        }
        if ((col + 1) < size && sites[row][col + 1]) {
            uf.union(coordinatesToIndex(row, col), coordinatesToIndex(row, col + 1));
        }
        if ((col - 1) >= 0 && sites[row][col - 1]) {
            uf.union(coordinatesToIndex(row, col), coordinatesToIndex(row, col - 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException("Invalid Input");
        }

        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row--;
        col--;
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException("Invalid Input");
        }

        if (!isOpen(row + 1, col + 1))
            return false;

        int index = coordinatesToIndex(row, col);
        return uf.find(topPoint) == uf.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (openSites < size)
            return false;
        return uf.find(topPoint) == uf.find(bottomPoint);
    }

    // row and col value is between 0 to n - 1
    private int coordinatesToIndex(int row, int col) {
        return ((size * row) + col);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
