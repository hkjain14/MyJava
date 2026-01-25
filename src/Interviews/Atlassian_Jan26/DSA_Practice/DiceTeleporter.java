package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
Given a board with numbers from 0 to lastNumber, startPosition, teleporters like. ("3,1", "5,10", "8,2") and maxValue of a die.
If after rollin the die, a number greater than lastNumber is reached then teleport to the lastNumber.

Find all final positions which can be reached by rolling the die ONCE.
 */

/*
Time: O(T + maxValue of dice)
T = number of teleporters

Space: O(T + maxValue)
map + result set
 */

public class DiceTeleporter {
    public static List<Integer> finalPositions(
            int lastNumber,
            int startPosition,
            List<String> teleporters,
            int maxValue
    ) {
        // Build teleporter mapping
        Map<Integer, Integer> teleporterMap = new HashMap<>();
        for (String tp : teleporters) {
            String[] parts = tp.split(",");
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());
            teleporterMap.put(from, to);
        }

        Set<Integer> result = new HashSet<>();

        // Try each possible die roll (1..maxValue)
        for (int roll = 1; roll <= maxValue; roll++) {
            int pos = startPosition + roll;

            if (pos > lastNumber) {
                pos = lastNumber;
            }

            if (teleporterMap.containsKey(pos)) {
                pos = teleporterMap.get(pos);
            }

            result.add(pos);
        }

        return new ArrayList<>(result);
    }

    // Quick demo
    public static void main(String[] args) {
        int lastNumber = 10;
        int startPosition = 4;
        List<String> teleporters = Arrays.asList("3,1", "5,10", "8,2");
        int maxValue = 6;

        System.out.println(finalPositions(lastNumber, startPosition, teleporters, maxValue));
        // Expected: [2, 6, 7, 9, 10]
    }
}
