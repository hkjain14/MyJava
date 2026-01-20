package Interviews.Confluent_Dec25.DSA_Practice;
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
 * Space complexity: O(n)
 * n: number of active entries (same in DLL & Map)
 *
 * Note: LinkedHashMap solves this already in Java
 */

/**
 * For concurrent requests, requirement is for a strongly (or eventually) consistent system,
 *
 * Soln:
 * Use ConcurrentHashMap. But need thread-safe DLL updates & runningSum/count updates.
 * Use AtomicLong/AtomicInteger for runningSum/count for lock-free reads of aggregated statistics
 *
 *
 *          Read heavy (Many get/getAverage calls)
 *              Use ReentrantReadWriteLock: readers take shared lock; writers (put or eviction) take write lock.
 *              But since we must evict on read (which mutates structure), reads are not purely read-only — they may cause eviction (writes).
 *              To avoid this, separate the eviction process:
 *                  Option A:
 *                      do lazy eviction asynchronously by a background thread
 *                      Slightly stale results
 *                  Option B:
 *                      maintain per-time buckets and atomic counters so getAverage can be lock-free (use atomic sum/count).
 *
 *         Write heavy (Many put calls)
 *              ensure append/remove operations are cheap.
 *              If lock contention is an issue, use sharded map and per-shard DLLs (consistent hashing on key).
 *
 * To balance concurrency and correctness: Use lock striping (shards)
 *
 *          Strongly consistent version
 *              Single leader handles all writes, replicate sync to followers
 *              Evictions happen deterministically by leader
 *              All reads go through leader
 *              getAverage() always accurate
 *              For scale,
 *                  partition keys across shards; use leader per shard
 *                  Global getAverage would need aggregation across shards
 *
 *          Eventually consistent version
 *              Each shard evicts locally, and replicates async
 *              Aggregates getAverate() async
 *              getAverage() may be slightly stale
 *              Acceptable for monitoring / analytics
 *
 *
 *          Practical large scale soln
 *              Partition keys across shards
 *              Maintain per-shard aggregates (sum/count)
 *              For getAverage():
 *                  If slightly stale is allowed,
 *                      query each shard's local aggregate and compute average (fast).
 *                      Optionally, maintain a streaming aggregator (e.g., Kafka Streams / Flink) that keeps a global aggregate updated.
 *
 *                  If strong consistency required,
 *                      coordinate with leaders or read from a single leader that maintains authoritative state.
 */

public class CacheTimeEviction {
    private final Map<String, Node> map;
    private Node head, tail; // oldest at head, latest at tail
    private int runningSum; // sum of active values
    private int runningCount; // count of active entries
    private final int windowSec; // window length in seconds
    /*
    nodeInsertionTimestamp = 3
    windowSec = 5

    queryTime = 7 : exists
    queryTime = 8 : exists
    queryTime = 9 : evicted
     */

    private static class Node {
        final String key;
        int value;
        int timestamp; // insertion time
        Node prev, next;

        Node(String key, int value, int timestamp) {
            this.key = key;
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    public CacheTimeEviction(int windowSec) {
        this.windowSec = windowSec;
        this.map = new HashMap<>();
        this.runningSum = 0;
        this.runningCount = 0;
    }

    /** Evict expired entries using currentTimestamp. Amortized O(1) per removed entry. */
    private void evictExpired(int currentTimestamp) {
        int expiryCutoff = currentTimestamp - windowSec;
        // strict: timestamps < expiryCutoff are evicted, timestamps >= expiryCutoff are allowed.
        while (head != null && head.timestamp < expiryCutoff) {
            map.remove(head.key);
            runningSum -= head.value;
            runningCount--;
            removeNode(head);
        }
    }

    /** Remove node from DLL (assumes node is present). O(1). */
    private void removeNode(Node node) {
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;

        if (node.next != null)
            node.next.prev = node.prev;
        else
            tail = node.prev;

        node.prev = node.next = null;
    }

    /** Append node to tail (as newest). O(1). */
    private void appendToTail(Node node) {
        if (tail != null)
            tail.next = node;
        node.prev = tail;
        tail = node;

        if (head == null)
            head = node;
    }

    /** Put (insert or update) key with value at given timestamp. Amortized O(1). */
    public void put(String key, int value, int timestamp) {
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
    public long get(String key, int timestamp) {
        evictExpired(timestamp);
        if (!map.containsKey(key))
            return -1;

        return map.get(key).value;
    }

    /** Return average of currently active values (as double). Returns 0.0 if none. */
    public double getAverage(int timestamp) {
        evictExpired(timestamp);
        if (runningCount == 0)
            return 0.0;

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
