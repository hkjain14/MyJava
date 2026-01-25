package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
You are given 2 lists - entry: timestamp sorted list of names who badged in, exit: timestamp sorted list of names who badged out.
People either forget to badge in or badge out. Find a list of such people.
 */

/*
Time: O(E + X)
Space: O(U)

E: size of entry array
X: size of exit array
U: Number of unique ppl
 */

public class BadgeCulprits {
    public static class Result {
        public List<String> missingBadgeIn;   // exited without enough entries
        public List<String> missingBadgeOut;  // entered without enough exits

        public Result(List<String> in, List<String> out) {
            this.missingBadgeIn = in;
            this.missingBadgeOut = out;
        }
    }

    public static Result findViolations(List<String> entry, List<String> exit) {
        Map<String, Integer> balance = new HashMap<>();

        // +1 for each entry
        for (String name : entry) {
            balance.put(name, balance.getOrDefault(name, 0) + 1);
        }

        // -1 for each exit
        for (String name : exit) {
            balance.put(name, balance.getOrDefault(name, 0) - 1);
        }

        List<String> missingBadgeIn = new ArrayList<>();
        List<String> missingBadgeOut = new ArrayList<>();

        for (Map.Entry<String, Integer> e : balance.entrySet()) {
            int diff = e.getValue();
            if (diff > 0) {
                // more entries than exits
                missingBadgeOut.add(e.getKey());
            } else if (diff < 0) {
                // more exits than entries
                missingBadgeIn.add(e.getKey());
            }
        }
        return new Result(missingBadgeIn, missingBadgeOut);
    }

    // Demo
    public static void main(String[] args) {
        List<String> entry = Arrays.asList("A", "B", "A", "C");
        List<String> exit = Arrays.asList("A", "B", "B", "D");

        Result res = findViolations(entry, exit);

        System.out.println("Missing badge IN: " + res.missingBadgeIn);   // [B, D]
        System.out.println("Missing badge OUT: " + res.missingBadgeOut); // [A, C]
    }
}
