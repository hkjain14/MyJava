package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
Given a board with numbers from 0 to lastNumber, startPosition, teleporters like. ("3,1", "5,10", "8,2") and maxValue of a die.
If after rollin the die, a number greater than lastNumber is reached then teleport to the lastNumber.

Find if it's possible to reach the lastNumber by rolling the die ANY number of times.
*/

/*
Brute Force: Possibility of infinite loop

BFS or DFS
Try all (and track visited).
Time: O(Last*MaxDiceValue). Space: O(Last)
 */

public class DiceTeleporterLastPos {
    public static boolean canReachLastNumber(
            int lastNumber,
            int startPosition,
            List<String> teleporters,
            int maxValue
    ) {
        Map<Integer, Integer> teleporterMap = new HashMap<>();
        for (String tp : teleporters) {
            String[] parts = tp.split(",");
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());
            if (from == lastNumber)
                return false;
            teleporterMap.put(from, to);
        }

        // BFS
        boolean[] visited = new boolean[lastNumber + 1];
        Queue<Integer> q = new ArrayDeque<>();
        visited[startPosition] = true;
        q.add(startPosition);

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == lastNumber) {
                return true;
            }

            for (int roll = 1; roll <= maxValue; roll++) {
                int next = cur + roll;
                if (next > lastNumber)
                    next = lastNumber;

                if (teleporterMap.containsKey(next))
                    next = teleporterMap.get(next);

                if (next == lastNumber)
                    return true;

                if (!visited[next]) {
                    visited[next] = true;
                    q.add(next);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(canReachLastNumber(
                10, 4, Arrays.asList("3,1", "5,10", "8,2"), 6
        )); // true

        System.out.println(canReachLastNumber(
                10, 7, Arrays.asList("10,1"), 6
        )); // false
    }
}
