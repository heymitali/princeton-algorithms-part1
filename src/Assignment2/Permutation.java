package Assignment2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str == null) break;
            queue.enqueue(str);
        }

        for (int i = 0; i < num; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
