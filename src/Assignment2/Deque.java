package Assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private int size;
    private final Node end;
    private final Node front;

    // construct an empty deque
    public Deque() {
        end = new Node();
        front = new Node();
        end.previous = front;
        front.next = end;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Enter valid argument");
        }
        Node oldFirst = front.next;
        Node newNode = new Node();
        newNode.item = item;
        front.next = newNode;
        newNode.previous = front;
        newNode.next = oldFirst;
        oldFirst.previous = newNode;

        // update pointers of newnode - prev,next

        size++;

//        if (last.next == first) {
//            last.next = newNode;
//        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Enter valid argument");
        }
        Node oldLast = end.previous;
        Node newNode = new Node();
        newNode.item = item;
        end.previous = newNode;
        newNode.next = end;
        oldLast.next = newNode;
        newNode.previous = oldLast;

        size++;

//        if (first.previous == last) {
//            first.previous = newNode;
//        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The Queue is empty");
        }
        Node oldFirst = front.next;
        Node newFirst = front.next.next;
        front.next = newFirst;
        newFirst.previous = front;
        size--;
        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("The Queue is empty");
        }
        Node oldLast = end.previous;
        Node newLast = end.previous.previous;
        end.previous = newLast;
        newLast.next = end;
        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node current = front.next;

        public boolean hasNext() {
            return current != end;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The queue is empty");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("This action is not permitted");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addLast("people, ");
        d.addFirst(" hi ");
        d.addLast(" my ");
        d.addLast(" name ");
        d.addLast(" is ");
        d.addLast(" Rachel ");
        d.addLast(" Green ");
        for (String s : d) {
            System.out.print(s);
        }
        d.removeFirst();
        d.removeLast();
        System.out.println(d.size());
    }
}
