// Percolation problem with Weighted Quick Union algorithm (Coursera assignment 1)
// Mehdi Ibrahimli

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int number;
    private int noS = 0;
    private int virtop = 0;
    private int virbot;
    private WeightedQuickUnionUF wqu;
    private boolean[][] fullgrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("invalid dimension");
        }
        grid = new boolean[n][n];
        fullgrid = new boolean[n][n];
        number = n;
        virbot = n * n + 1;
        wqu = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number) {
            throw new IllegalArgumentException("site out of bounds");
        }
        if (isOpen(row, col)) {
            return;
        }
        else {
            grid[row - 1][col - 1] = true;
            noS++;
            int p = col + number * (row - 1);
            int q;
            if (row == 1) {
                wqu.union(p, virtop);
                fullgrid[row - 1][col - 1] = true;
            }

            if (row == number) wqu.union(p, virbot);

            if (col != number && isOpen(row, col + 1)) {
                q = (col + 1) + number * (row - 1);
                wqu.union(p, q);
                if (fullgrid[row - 1][col]) fullgrid[row - 1][col - 1] = true;
            }
            if (col != 1 && isOpen(row, col - 1)) {
                q = (col - 1) + number * (row - 1);
                wqu.union(p, q);
                if (fullgrid[row - 1][col - 2]) fullgrid[row - 1][col - 1] = true;
            }
            if (row != 1 && isOpen(row - 1, col)) {
                q = col + number * (row - 2);
                wqu.union(p, q);
                if (fullgrid[row - 2][col - 1]) fullgrid[row - 1][col - 1] = true;
            }
            if (row != number && isOpen(row + 1, col)) {
                q = col + number * (row);
                wqu.union(p, q);
                if (fullgrid[row][col - 1]) fullgrid[row - 1][col - 1] = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number) {
            throw new IllegalArgumentException("site out of bounds");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > number || col < 1 || col > number) {
            throw new IllegalArgumentException("site out of bounds");
        }
        // if (row != number) {
        int q = col + (row - 1) * number;
        return wqu.find(virtop) == wqu.find(q);
        // }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noS;
    }

    // does the system percolate?
    public boolean percolates() {
        return wqu.find(virtop) == wqu.find(virbot);
    }

    // test client (optional)
    public static void main(String[] args) {
        // empty method
    }
}
