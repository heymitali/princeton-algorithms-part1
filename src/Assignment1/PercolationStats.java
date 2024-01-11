package Assignment1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholdStats;
    private final int numOfTrials;
    private double calculatedMean;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("The value of n must me greater than zero");
        }

        numOfTrials = trials;
        thresholdStats = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int randomSite = StdRandom.uniformInt((n * n));
                int siteRow = randomSite / n;
                int siteCol = randomSite % n;
                percolation.open(siteRow + 1, siteCol + 1);
            }
            thresholdStats[i] = 1.0 * percolation.numberOfOpenSites() / (n * n);
//            System.out.println("finished trial " + i);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        calculatedMean = StdStats.mean(thresholdStats);
//        double sum = 0;
//        for (int i = 0; i < thresholdStats.length; i++) {
//            sum += thresholdStats[i];
//        }
        return calculatedMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdStats);
//        double sum = 0;
//        for (double thresholdStat : thresholdStats) {
//            sum += (thresholdStat - calculatedMean) * (thresholdStat - calculatedMean);
//        }
//        return sum / (numOfTrials - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double sqRoot = Math.sqrt(numOfTrials);
        return calculatedMean - CONFIDENCE_95 / sqRoot;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double sqRoot = Math.sqrt(numOfTrials);
        return calculatedMean + CONFIDENCE_95 / sqRoot;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }

}