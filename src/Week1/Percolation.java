import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by sugan on 16.29.4.
 */

public class Percolation {
    private WeightedQuickUnionUF uf, uf_only_to_top;
    private boolean[][] state;
    private int N;
    private int top;
    private int bottom;
    private int currentSite;

    public Percolation(int N) {
        if (N < 1)
            throw new IllegalArgumentException("Size N should be greater than 0");
        this.N = N;
        top = N * N;
        bottom = top + 1;
        uf = new WeightedQuickUnionUF(top + 2);       // Two extre sites for top and bottom (virtual point)
        uf_only_to_top = new WeightedQuickUnionUF(top + 1);
        state = new boolean[N][N];                  // all are false by default with mean closed
    }

    private void checkBoundary(int i, int j) {
        if (i < 1 || i > N)
            throw new IndexOutOfBoundsException("Row Index should be between 1 and " + N);
        if (j < 1 || j > N)
            throw new IndexOutOfBoundsException("Column Index should be between 1 and " + N);
        return;
    }

    public void open(int i, int j) {
        checkBoundary(i, j);

        if (state[i - 1][j - 1])   // already opened
            return;
        i -= 1;     // Convert Human friendly index ot computer index
        j -= 1;
        state[i][j] = true;
        currentSite = i * N + j;

        // CHECK for upper Site
        // Join to top if it is in top ROw
        if (i == 0) {   // top row
            uf.union(top, currentSite);
            uf_only_to_top.union(top, currentSite);
        } else if (state[i - 1][j]) {
            uf.union(currentSite, currentSite - N);
            uf_only_to_top.union(currentSite, currentSite - N);
        }

        // Check for Lower Site
        if (i == N - 1) { // Already at bottom ROW
            uf.union(bottom, currentSite);
            // uf_only_to_top.union(bottom, currentSite);
        } else if (state[i + 1][j]) {
            uf.union(currentSite, currentSite + N);
            uf_only_to_top.union(currentSite, currentSite + N);
        }
        // Check for site on left
        if (j != 0) {   // No need to do for the leftmost column
            if (state[i][j - 1]) {
                uf.union(currentSite, currentSite - 1);
                uf_only_to_top.union(currentSite, currentSite - 1);
            }
        }
        if (j != N - 1) {
            if (state[i][j + 1]) {
                uf.union(currentSite, currentSite + 1);
                uf_only_to_top.union(currentSite, currentSite + 1);

            }
        }

    }

    public boolean isOpen(int i, int j) {
        checkBoundary(i, j);
        return state[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        checkBoundary(i, j);
        if (!state[i - 1][j - 1])    // Closed site is never full
            return false;
        return uf.connected(top, (i - 1) * N + j - 1) && uf_only_to_top.connected(top, (i - 1) * N + j - 1);
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    public static void main(String[] args) {
        final int N = 5;
        int x, y;
        int count = 0;
        Percolation percolation = new Percolation(N);
        System.out.println("Begin");
        do {
            x = StdRandom.uniform(N) + 1;
            y = StdRandom.uniform(N) + 1;
            System.out.println("Opened site: (" + x + "," + y + ")");
            if (!percolation.isOpen(x, y)) {
                percolation.open(x, y);
                count++;
            }
        } while (!percolation.percolates());

        System.out.println("Percolation  ends in " + count + " steps.");
    }
}
