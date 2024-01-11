package Assignment2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arrayList;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arrayList = (Item[]) new Object[10];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (size == arrayList.length) {
            arrayList = resize(arrayList, 2);
        }
        arrayList[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item dequedItem = swap(randomIndex);
        size--;
        if (size == arrayList.length / 4.0) {
            arrayList = resize(arrayList, 1.0 / 4.0);
        }
        return dequedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int randomIndex = StdRandom.uniformInt(size);
        return arrayList[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private final Item[] items;
        private int pointer;

        public QueueIterator() {
            items = (Item[]) new Object[size];
            pointer = 0;

            RandomizedQueue<Item> rq = new RandomizedQueue<>();
            for (int i = 0; i < size; i++) {
                rq.enqueue(arrayList[i]);
            }
            for (int i = 0; i < size; i++) {
                items[i] = rq.dequeue();
            }
        }

        public boolean hasNext() {
            return pointer < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The queue is empty");
            }
            return items[pointer++];
        }

        public void remove() {
            throw new UnsupportedOperationException("This action is not permitted");
        }
    }

    private Item[] resize(Item[] arr, double multiple) {
        int newLength = (int) (arr.length * multiple);
        Item[] resizedArr = (Item[]) new Object[newLength];

        int minSize = arr.length > newLength ? newLength : arr.length;
        for (int i = 0; i < minSize; i++) {
            resizedArr[i] = arr[i];
        }
        return resizedArr;
    }

    private Item swap(int index) {
        Item temp = arrayList[index];
        arrayList[index] = arrayList[size - 1];
        arrayList[size - 1] = null;
        return temp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");
        System.out.println(queue.isEmpty());
        System.out.println(queue.sample());
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        System.out.println(queue.size());
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + ", ");
        }
    }
}
