/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:  java RandomizedQueue < testFiles/randque.txt
 *  Dependencies: StdIn.java StdOut.java StdRandom.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * @program:Queues
 * @description:A generic data type randomized queue is similar to a stack or queue,except that the item removed is chosen uniformly at random from items in the data structure.
 * Iterator.Each iterator must return the items in uniformly random order.The order of two or more iterators to the same randomized queue must be mutually independent;
 * each iterator must maintain its own random order.
 * @author:Xiaolin LU
 * @create:2019-01-18 19:03
 **/


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] ranq;
    private int n;
    private int radIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        n = 0;
        radIndex = 0;
        ranq = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // resizing the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] resizeArr = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            resizeArr[i] = ranq[i];
        }
        ranq = resizeArr;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("can't add a null item to the queue");
        n++;
        if (n == ranq.length) resize(2 * n);
        ranq[n - 1] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        Item item = sample();
        n--;
        if (radIndex != n) ranq[radIndex] = ranq[n];
        ranq[n] = null;
        if (n == ranq.length / 4) resize(ranq.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("queue is empty");
        radIndex = StdRandom.uniform(n);
        return ranq[radIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private int numNonIterable;
        private Item[] temps;

        public QueueIterator() {
            numNonIterable = n;
            temps = (Item[]) new Object[ranq.length];
            for (int j = 0; j < ranq.length; j++)
                temps[j] = ranq[j];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove isn't supported");
        }

        @Override
        public boolean hasNext() {
            return numNonIterable != 0;
        }

        // replace the current item accessed of the order[] to the last item
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
		// return a random integer uniformly in [a, b)
            int randomIndex = StdRandom.uniform(0, numNonIterable);
            Item item = temps[randomIndex];
            temps[randomIndex] = temps[--numNonIterable];
            temps[numNonIterable] = item;
            return item;
        }
    }



    // unit testing (optional)
    public static void main(String[] args) {

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String s = null;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (!s.equals("-")) q.enqueue(s);
            else StdOut.print(q.dequeue() + " ");
        }
        StdOut.println();
        for (String st : q)
            StdOut.print(st + " ");
    }
}
