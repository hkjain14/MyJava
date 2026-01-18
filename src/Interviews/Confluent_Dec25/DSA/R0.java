package Interviews.Confluent_Dec25.DSA;

import java.util.*;

/*
Randomized queue implementation : FIFO. Requirements:
random (peeks one element from queue uniformly randomly), enqueue, dequeue: O(1)
*/
public class R0{
    public static void main(String[] args) {
        RandomQueueImpl<Integer> q1 = new RandomQueueImpl<>();
        RandomQueueImpl<Integer> q2 = new RandomQueueImpl<>();

        q1.enqueue(10);
        q1.enqueue(20);

        q2.enqueue(10);
        q2.enqueue(20);
        System.out.println("q1 == q2 : " + RandomQueue.equals(q1, q2) + "\n"); // true

        Integer dequeuedEl= q2.dequeue();
        q2.enqueue(30);
        System.out.println("q1 == q2 : " + RandomQueue.equals(q1, q2) + "\n"); // false

        System.out.println("Random element in Q2: " + q2.random());
    }
}

class RandomQueueImpl<T> implements RandomQueue<T> {
    private List<T> arr;
    private int head;
    private final Random rand;

    public RandomQueueImpl() {
        this.arr = new ArrayList<>();
        this.head = 0;
        this.rand = new Random();
    }

    @Override
    public void enqueue(T x) {
        arr.add(x);
    }

    // O(1) amortized
    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue empty");

        T val = arr.get(head);
        head++;

        // compact when too much unused prefix
        if (head > arr.size() / 2) {
            compact();
        }
        return val;
    }

    // O(1) random element uniformly
    @Override
    public T random() {
        if (isEmpty()) throw new NoSuchElementException("Queue empty");
        int idx = head + rand.nextInt(this.size()); // uniform
        return arr.get(idx);
    }

    @Override
    public boolean isEmpty() {
        return this.size() <= 0;
    }

    @Override
    public int size() {
        return arr.size() - head;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int idx = head;

            @Override
            public boolean hasNext() {
                return idx < arr.size();
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return arr.get(idx++);
            }
        };
    }

    // compact array occasionally to reset head to 0
    private void compact() {
        List<T> newArr = new ArrayList<>(this.size());
        for (int i = head; i < arr.size(); i++) {
            newArr.add(arr.get(i));
        }
        arr = newArr;
        head = 0;
    }
}

interface RandomQueue<T> {
    void enqueue(T x);
    T dequeue();      // FIFO remove
    T random();       // peek uniformly random element
    boolean isEmpty();
    int size();
    Iterator<T> iterator();
    static <T> boolean equals(RandomQueue<T> q1, RandomQueue<T> q2) {
        // Case 1: both null
        if (q1 == null && q2 == null) return true;

        // Case 2: exactly one is null
        if (q1 == null || q2 == null) return false;

        System.out.print("Printing Q1: ");
        printQueue(q1);
        System.out.print("Printing Q2: ");
        printQueue(q2);

        // Case 3: size mismatch
        if (q1.size() != q2.size()) return false;

        // Case 4: compare element-by-element
        Iterator<T> it1 = q1.iterator();
        Iterator<T> it2 = q2.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            T a = it1.next();
            T b = it2.next();

            if (a == null && b == null) continue;
            if (a == null || b == null) return false;
            if (!a.equals(b)) return false;
        }

        return true;
    }

    static <T> void printQueue(RandomQueue<T> q) {
        Iterator<T> it = q.iterator();
        while(it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}

