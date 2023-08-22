package Interviews.MS_Aug2023;

import java.util.HashSet;
import java.util.Set;

public class OA_Q1 {
    // https://www.geeksforgeeks.org/find-minimum-number-of-substrings-with-unique-characters/
    static int solution(String s) {
        Set<Character> set = new HashSet<>();
        int ans = 1;
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                ans++;
                set.clear();
            }
            set.add(s.charAt(i));
        }
        return ans;
    }
    public static void main(String[] args) {
        String s = "abacaba";
        System.out.println(solution(s));
    }
}


