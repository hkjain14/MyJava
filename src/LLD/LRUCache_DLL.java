package LLD;

import java.util.HashMap;
import java.util.Map;

public class LRUCache_DLL {
    int capacity;
    Node dummyHead;
    Node dummyTail;

    Map<Integer, Node> map;

    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key,int val) {
            this.key = key;
            this.val = val;
        }
    }

    LRUCache_DLL(int capacity) {
        map = new HashMap<>();
        this.capacity = capacity;

        this.dummyHead = new Node(-1,-1);
        this.dummyTail = new Node(-1,-1);
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
    }

    void addToEnd(Node node) {
        node.prev = dummyTail.prev;
        node.next = dummyTail;
        dummyTail.prev.next = node;
        dummyTail.prev = node;
    }

    void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    void put(int key, int val) {
        // If already present key
        if(map.containsKey(key)) {
            // Remove from map : automatic at line 57 bcoz HashMap has unique value per key.
            // Remove from LL
            removeNode(map.get(key));
        }

        Node node = new Node(key,val);
        addToEnd(node);
        map.put(key,node);
        if(map.size() > capacity) {
            // Remove it from Map
            map.remove(dummyHead.next.key);

            // Remove actual head from LL
            removeNode(dummyHead.next);
        }
    }

    int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        removeNode(node);
        addToEnd(node);
        return node.val;
    }

    void printCache() {
        Node start = dummyHead.next;
        while(true) {
            if(start == dummyTail)
                break;
            System.out.printf(start.val+ " ");
            start = start.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LRUCache_DLL cache = new LRUCache_DLL(4);
        cache.put(1,1);
        cache.put(2,2);
        cache.put(3,3);
        cache.put(4,4);
        cache.printCache();

        cache.get(3);
        cache.put(5,5);
        cache.printCache();

        cache.get(4);
        cache.put(6,6);
        cache.printCache();
    }
}
