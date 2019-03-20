/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:  java Deque < testFiles/deque.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @program: algo
 * @description: A double-ended queue or deque is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 * @author: Xiaolin LU
 * @create: 2019-01-18 14:31
 **/

public class Deque<Item> implements Iterable<Item> {

    //    private static final String fileLocation = "src/testFiles/deque.txt";
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    // helper linked list class
    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node<Item> oldFist = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFist;
        first.prev = null;
        if (oldFist != null) oldFist.prev = first;
        n++;
        if (n == 1) last = first;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (oldLast != null) oldLast.next = last;
        n++;
        if (n == 1) first = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        n--;
        Item item = first.item;
        if (n != 0) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("deque is empty");
        n--;
        Item item = last.item;
        if (n != 0) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private class DequeIterator implements Iterator<Item> {

        Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

        Deque<String> deque = new Deque<String>();
/*        try {
            System.out.println(new File(".").getAbsolutePath());
            FileInputStream input = new FileInputStream(fileLocation);
            System.setIn(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("+")) {
                if (StdIn.hasNextChar()) deque.addLast(StdIn.readString());
            } else if (s.equals("-")) {
                StdOut.print(deque.removeFirst() + " ");
                for (String x : deque)
                    StdOut.print("iterator left " + x);
            } else if (s.equals("--")) {
                StdOut.print(deque.removeLast() + " ");
            } else deque.addFirst(s);
        }
        StdOut.println();
        for (String i : deque) {
            StdOut.print(i);
        }
    }
}
