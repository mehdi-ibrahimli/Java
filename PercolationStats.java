//// Percolation problem with Weighted Quick Union algorithm (Coursera assignment 1)
// // Mehdi Ibrahimli

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] openSites;
    private final int tr;
    private double con = 1.96;

    public PercolationStats(int n, int trials) {
        tr = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid constructor arguments");
        }
        openSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int r = (int) Math.ceil(Math.abs(((StdRandom.uniform()) * n)));
                int c = (int) Math.ceil(Math.abs(((StdRandom.uniform()) * n)));
                perc.open(r, c);
            }
            int counter = 0;
            for (int k = 1; k <= n; k++) {
                for (int j = 1; j <= n; j++) {
                    if (perc.isOpen(k, j)) {
                        counter++;
                    }
                }
            }
            openSites[i] = (double) counter / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - con * stddev() / Math.sqrt(tr);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + con * stddev() / Math.sqrt(tr);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats percSt = new PercolationStats(Integer.parseInt(args[0]),
                                                       Integer.parseInt(args[1]));
        System.out.println("mean = " + percSt.mean());
        System.out.println("stddev = " + percSt.stddev());
        System.out.println(
                "95% confidence interval = [" + percSt.confidenceLo() + ", " + percSt
                        .confidenceHi() + "]");
    }
}
