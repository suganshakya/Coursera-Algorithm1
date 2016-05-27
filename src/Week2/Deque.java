import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by sugan on 13/05/16.
 */
public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first, last;

    public Deque() // construct an empty deque
    {
        N = 0;
    }

    private class Node {
        private Item item;
        private Node previous;
        private Node next;
    }

    public boolean isEmpty() {               // is the deque empty?
        return N == 0;
    }

    public int size() {                       // return the number of items on the deque
        return N;
    }

    public void addFirst(Item item) {          // add the item to the front
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        N++;
    }

    public void addLast(Item item)        // add the item to the end
    {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        if (N == 0) {
            first = last;
        } else {
            last.previous = oldlast;
            oldlast.next = last;
        }
        N++;
    }

    public Item removeFirst()      // remove and return the item from the front
    {
        if (N == 0) {
            throw new NoSuchElementException("Can not remove from empty deque");
        }
        Item item = first.item;
        first = first.next;
        N--;
        if (N == 0) {
            last = null;
        } else {
            first.previous = null;
        }
        return item;
    }

    public Item removeLast()        // remove and return the item from the end
    {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        N--;
        if (N == 0) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator()   // return an iterator over items in order from front to end
    {
        return new Iterator<Item>() {
            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null)
                    throw new NoSuchElementException();
                Item item = (Item) current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 1000; i++) {
            deque.addLast(i);
        }
        for (Integer integer : deque) {
            System.out.println(integer);
        }
    }  // unit testing
}
