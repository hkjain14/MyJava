package DS.Confluent_Practice_R1;;
import java.util.*;

/**
 * Time-windowed KV store with TTL (sliding window).
 * Timestamps provided to operations are non-decreasing (incoming stream is ordered by time).
 *
 * Methods:
 * - put(key, value, timestamp) : O(1)
 * - get(key, timestamp) : O(1)
 * - getAverage(timestamp) : O(1)
 *
 * Note: LinkedHashMap solves this already in Java
 */

/**
 * For concurrent requests, requirement is for a strongly (or eventually) consistent system,
 *
 * Soln:
 */

public class CacheTimeEviction {
    private final Map<String, Node> map;
    private Node head, tail; // oldest at head, latest at tail
    private long runningSum; // sum of active values
    private int runningCount; // count of active entries
    private final long windowSec; // window length in seconds

    private static class Node {
        final String key;
        long value;
        long timestamp; // insertion time
        Node prev, next;

        Node(String key, long value, long timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    public CacheTimeEviction(long windowSec) {
        this.windowSec = windowSec;
        this.map = new HashMap<>();
        this.head = this.tail = null;
        this.runningSum = 0L;
        this.runningCount = 0;
    }

    /** Evict expired entries using currentTimestamp. Amortized O(1) per removed entry. */
    private void evictExpired(long currentTimestamp) {
        long expiryCutoff = currentTimestamp - windowSec - 1; // strict: timestamps < expiryCutoff are expired
        while (head != null && head.timestamp <= expiryCutoff) {
            Node node = head;
            removeNode(node);
            map.remove(node.key);
            runningSum -= node.value;
            runningCount--;
        }
    }

    /** Remove node from DLL (assumes node is present). O(1). */
    private void removeNode(Node node) {
        if (node.prev != null) node.prev.next = node.next;
        else head = node.next;

        if (node.next != null) node.next.prev = node.prev;
        else tail = node.prev;

        node.prev = node.next = null;
    }

    /** Append node to tail (as newest). O(1). */
    private void appendToTail(Node node) {
        node.prev = tail;
        if (tail != null) tail.next = node;
        tail = node;
        if (head == null) head = node;
    }

    /** Put (insert or update) key with value at given timestamp. Amortized O(1). */
    public void put(String key, long value, long timestamp) {
        // step 1: evict expired entries using current timestamp
        evictExpired(timestamp);

        Node existing = map.get(key);
        if (existing != null) {
            // existing entry is active (because we evicted expired ones)
            // update running sum, timestamp, value, and move to tail (newest)
            runningSum += value - existing.value;
            existing.value = value;
            existing.timestamp = timestamp;

            // move node to tail (newest)
            removeNode(existing);
            appendToTail(existing);

            // Map insertion not needed, because we already updated the value of the node itself, corr to same key.
            // runningCount updation not needed
        } else {
            // fresh insert
            Node node = new Node(key, value, timestamp);
            map.put(key, node);
            appendToTail(node);
            runningSum += value;
            runningCount++;
        }
    }

    /** Get value if present and not expired; else return -1. */
    public long get(String key, long timestamp) {
        evictExpired(timestamp);
        if (!map.containsKey(key)) {
            return -1;
        }
        // present and active
        return map.get(key).value;
    }

    /** Return average of currently active values (as double). Returns 0.0 if none. */
    public double getAverage(long timestamp) {
        evictExpired(timestamp);
        if (runningCount == 0) return 0.0;
        return (double) runningSum / runningCount;
    }

    public static void main(String[] args) {
        CacheTimeEviction cache = new CacheTimeEviction(5); // 5 sec window

        cache.put("foo", 1, 1);
        cache.put("bar", 2, 3);
        System.out.println(cache.get("foo", 4)); // -> 1
        System.out.println(cache.getAverage(5)); // -> (1+2)/2 = 1.5
        System.out.println(cache.get("foo", 7)); // -> -1 (foo expired)
        System.out.println(cache.getAverage(8)); // -> 2.0 (only bar)
    }
}
