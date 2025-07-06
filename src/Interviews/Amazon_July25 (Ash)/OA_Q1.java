/*
long optimizeCalorieBurn(List<Integer> height) {
}

where the question is:
User is positioned on ground, and there are n blocks, each of height given by height[i]
The goal is to maximize calorie burn during exercise, where
1. the user starts from the ground and intends to jump on each block exactly once. He stays at the last block.
2. he can choose the order of jumping
3. calorie burn while jumping from i block to j block is ( height[i] - height[j] )^2

example:
input: [5,2,5]
output: 43

input: [2,2,4,3]
output: 2

input: [5,2,3,4,1]
output: 55
*/
import java.util.*;

public class Solution {
    public static long optimizeCalorieBurn(List<Integer> height) {
        Collections.sort(height);
        int size = height.size();
        int[] arranged = new int[size];
        
        int left = 0, right = size - 1, i=0;
       
        while (left < right) {
            arranged[i++] = height.get(right);
            arranged[i++] = height.get(left++);
            right--;
        }
        if (left == right) {
            arranged[size-1] = height.get(left);
        }
        

        // Start from ground
        long burn = 0;
        int prev = 0;
        for (int h : arranged) {
            long diff = h - prev;
            burn += diff * diff;
            prev = h;
        }

        return burn;
    }
    
    public static void main(String[] args) {
        List<List<Integer>> testCases = Arrays.asList(
            Arrays.asList(5, 2, 5), //43
            Arrays.asList(2, 2, 4, 3), //22
            Arrays.asList(5, 2, 3, 4, 1), //55
            Arrays.asList(1, 2, 3), //14
            Arrays.asList(10, 1) //181
        );

        for (List<Integer> test : testCases) {
            System.out.println("Input: " + test);
            long result = optimizeCalorieBurn(test);
            System.out.println("Max Calorie Burn: " + result + "\n");
        }
    }
}
