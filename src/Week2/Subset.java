import edu.princeton.cs.algs4.StdIn;

/**
 * Created by sugan on 13/05/16.
 */
public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }
        if (k < randomizedQueue.size()) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < k; ++i) {
            System.out.println(randomizedQueue.dequeue());
        }
    }
}
