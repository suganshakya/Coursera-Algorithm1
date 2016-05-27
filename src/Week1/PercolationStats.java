import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by sugan on 16.29.4.
 */
public class PercolationStats {


    private double[] threshold;
    private int T;

    public PercolationStats(int N, int T) {
        Percolation percolation;
        if (N < 1 || T < 1)
            throw new IllegalArgumentException("N and T should be greater than 0");
        this.T = T;
        threshold = new double[T];
        final int max = N * N;
        int count;
        int x, y;
        for (int i = 0; i < T; ++i) {
            count = 0;
            percolation = new Percolation(N);
            do {
                x = StdRandom.uniform(N) + 1;
                y = StdRandom.uniform(N) + 1;
                if (!percolation.isOpen(x, y)) {
                    percolation.open(x, y);
                    count++;
                }
            } while (!percolation.percolates());
            threshold[i] = (double) count / max;
        }

    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main(String[] args) {

    }
}
