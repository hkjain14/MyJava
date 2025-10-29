package Interviews.Uber_Oct25_L5A_SSE;

import java.util.*;

// Part1: https://leetcode.com/problems/water-and-jug-problem/ => DFS soln below, there is GCD soln too
// Part2: Print path
public class R1 {

    private static int[] getNewState(int caseId, int maxX, int maxY, int currX, int currY) {
        int[][] list = new int[6][2];
        // Fill x
        list[0] = new int[]{maxX, currY};
        // Fill y
        list[1] = new int[]{currX, maxY};
        // Empty x
        list[2] = new int[]{0, currY};
        // Empty y
        list[3] = new int[]{currX, 0};
        // Pour x to y
        list[4] = new int[]{Math.max(currX - (maxY - currY),0), Math.min(maxY, currY + currX)};
        // Pour y to x
        list[5] = new int[]{Math.min(maxX, currX + currY), Math.max(currY - (maxX - currX),0)};

        return list[caseId];
    }

    public static boolean canMeasureWater(int x, int y, int target) {
        Set<String> visited = new HashSet<>();
        int [] state = new int[]{0,0};
        Queue<int[]> queue = new LinkedList<>();
        Map<String, String> pathMap = new HashMap<>();
        queue.offer(state);
        visited.add("0_0");

        while(!queue.isEmpty()) {
            int [] currState = queue.poll();
            int currX = currState[0], currY = currState[1];
            String currStateString = currX + "_" + currY;
            if(target == currX || target == currY || target == currX+currY) {
                printPath(pathMap, currStateString);
                return true;
            }
            for(int i=0;i<6;i++) {
                int[] newState = getNewState(i, x, y, currX, currY);
                String newStateString = newState[0] + "_" + newState[1];
                if(!visited.contains(newStateString)) {
                    queue.offer(newState);
                    visited.add(newStateString);
                    pathMap.put(newStateString, currStateString);
                }
            }
        }
        return false;
    }
    private static void printPath(Map<String, String> pathMap, String endStateString) {
        List<String> pathsList = new ArrayList<>();
        pathsList.add(endStateString);
        String curr = endStateString;

        while(pathMap.containsKey(curr)) {
            String parent = pathMap.get(curr);
            pathsList.add(parent);
            curr = parent;
        }
        Collections.reverse(pathsList);

        for(String s: pathsList) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        System.out.println(canMeasureWater(3,5,4));
    }
}
