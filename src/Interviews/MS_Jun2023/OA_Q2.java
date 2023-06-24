package Interviews.MS_Jun2023;

import java.util.*;
public class OA_Q2 {

    //https://www.geeksforgeeks.org/count-of-pairs-a-b-in-range-1-to-n-such-that-last-digit-of-a-is-equal-to-the-first-digit-of-b/
    // Instead of in a range from 1 to n, we want to find the ans from a given arr

    // INCOMPLETE ANS Coz hacky soln for {12,21}
    public static int solution(int[] numbers) {
        int ans = 0;
        Map<Integer, Integer> fdigitc = new HashMap<>();
        Map<Integer, Integer> ldigitc = new HashMap<>();
        for (int n : numbers) {
            int firstDigit;
            int x = n;
            while(true) {
                x/=10;
                if(x/10 == 0) {
                    firstDigit=x;
                    break;
                }
            }
            int lastDigit = n % 10;

            fdigitc.put(firstDigit, fdigitc.getOrDefault(firstDigit, 0) + 1);
            ldigitc.put(lastDigit, ldigitc.getOrDefault(lastDigit, 0) + 1);
        }

        for (int n : numbers) {
            int lastDigit = n % 10;
            ans += fdigitc.getOrDefault(lastDigit, 0);;
        }
        int len = numbers.length;
        return Math.min(ans, len*(len-1)/2);
    }
    public static void main(String[] args) {
        int[] numbers = {122,21,21,23};
        System.out.println(solution(numbers));
    }
}
