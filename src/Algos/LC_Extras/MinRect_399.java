package Algos.LC_Extras;

import java.util.*;

public class MinRect_399 {
    // LC 399
    public static int minAreaRect(int[][] points) {
        int ans = Integer.MAX_VALUE;
        Map<Integer, Set<Integer>> map = new HashMap<>();

        for(int[]p : points) {
            if(!map.containsKey(p[0])) {
                map.put(p[0], new HashSet<Integer>());
            }
            map.get(p[0]).add(p[1]);
        }

        for (int[] p1 : points) {
            for (int[] p2 : points) {
                if(p1[0] == p2[0] || p1[1] == p2[1]) {
                    continue;
                }
                if(map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
                    ans = Math.min(ans, Math.abs(p1[0]-p2[0]) * Math.abs(p1[1]-p2[1]));
                }
            }
        }
        
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    public static void main(String[] args) {
        int[][] points = {{1,1},{1,3},{3,1},{3,3},{4,1},{4,3}};
        System.out.println(minAreaRect(points));
    }
    
}
