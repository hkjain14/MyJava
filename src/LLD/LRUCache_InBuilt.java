package LLD;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache_InBuilt {
    int capacity;
    LinkedHashMap<Integer, Integer> dic;

    public LRUCache_InBuilt(int capacity) {
        this.capacity = capacity;
        // Next line true is VVImp. Without it, won't work.
            // The entries of a LinkedHashMap can be iterated either in the order the keys were first added to the Map (that's the default behavior) or
            // according to access order (i.e. the most recently accessed entry will be the last entry iterated over). Achieved by passing 3rd param : true
        dic = new LinkedHashMap<>(5, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return dic.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        dic.put(key, value);
    }

    public static void main(String[] args) {
        LRUCache_InBuilt c = new LRUCache_InBuilt(3);
        c.put(1,1);
        c.put(2,2);
        c.put(3,3);

        c.get(1);
        c.put(4,4);
        System.out.println(c.dic);
    }
}
