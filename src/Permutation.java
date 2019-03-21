/******************************************************************************
 *  Compilation:  javac -cp ../.lift/algs4.jar;. Permutation.java
 *  Execution:  java -cp ../.lift/algs4.jar;. Permutation 3 < testFiles/distinct.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 ******************************************************************************/


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @program: Queues
 * @description: A client program that takes an integer k as a command-line argument;
 * reads in a sequence of strings from standard input and prints exactly k of them, uniformly at random.
 * Print each item from the sequence at most once.
 * @author: Xiaolin LU
 * @create: 2019-01-21 17:19
 **/

public class Permutation {

    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        if (k == 0) return;

        int n = 0;

        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            n++;
            int indexDequeue = StdRandom.uniform(n);
            String in = StdIn.readString();
            if (n > k) {
                if (indexDequeue >= k)
                    continue;
                q.dequeue();
            }
            q.enqueue(in);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
