import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sugan on 13/05/16.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        s = (Item[]) new Object[1];
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return N;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null)
            throw new NullPointerException();
        if (N == s.length)
            resize(2 * N);
        s[N++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < s.length; ++i) {
            copy[i] = s[i];
        }
        s = copy;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (N == 0)
            throw new NoSuchElementException();
        int rand = StdRandom.uniform(N);
        Item item = s[rand];
        if (rand != N - 1) {
            s[rand] = s[N - 1];
        }
        s[--N] = null;
        if (N > 0 && N == s.length / 4) {
            resize(s.length / 2);
        }
        return item;
    }

    public Item sample()                     // return (but do not remove) a random item
    {
        if (N == 0)
            throw new NoSuchElementException();
        return s[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RandomizedQueueIterator<Item>();
    }

    private class RandomizedQueueIterator<Item> implements Iterator<Item> {
        private int iterN = N;
        private int[] randomIndex = new int[iterN];

        @Override
        public boolean hasNext() {
            return iterN != 0;
        }

        @Override
        public Item next() {
            if (N == 0 || iterN == 0) {
                throw new NoSuchElementException();
            }
            if (iterN == N) {
                for (int i = 0; i < iterN; ++i) {
                    randomIndex[i] = i;
                }
                StdRandom.shuffle(randomIndex);
            }
//            Item[] temp = (Item[]) new Object[iterN];
//            for (int i = 0; i < iterN; ++i) {
//                temp[i] = (Item) s[i];
//            }
            return (Item) s[randomIndex[--iterN]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void print() {
        for (int i = 0; i < N; ++i) {
            System.out.print(s[i] + "-");
        }
        System.out.println();
    }

    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        System.out.println(rq.size());
        for (int i = 0; i < 20; ++i) {
            rq.enqueue(i + "");
        }
        Iterator<String> itr = rq.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
    }
}
