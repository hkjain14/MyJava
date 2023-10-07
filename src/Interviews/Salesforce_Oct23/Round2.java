package Interviews.Salesforce_Oct23;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Round2 {
    static PriorityQueue<Integer> minHeap;
    static PriorityQueue<Integer> maxHeap;


    static void addToHeap(int x) {
        if(maxHeap.size() == minHeap.size()) {
            maxHeap.offer(x);
            minHeap.offer(maxHeap.poll());
        } else {
            minHeap.offer(x);
            maxHeap.offer(minHeap.poll());
        }

    }

    static double getMedian() {
        if(minHeap.size()>maxHeap.size()) {
            return minHeap.peek();
        } else {
            return (minHeap.peek()+maxHeap.peek())/2;
        }
    }


    public static void main(String[] args) {
        minHeap= new PriorityQueue<>();
        maxHeap= new PriorityQueue<>(Comparator.reverseOrder());
        addToHeap(5);
        System.out.println(getMedian());
        addToHeap(15);
        System.out.println(getMedian());
        addToHeap(1);
        System.out.println(getMedian());
        addToHeap(3);
        System.out.println(getMedian());
        addToHeap(2);
        System.out.println(getMedian());

    }
}
