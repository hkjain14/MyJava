/*
DP + Segment tree question:

given an input string containing 0s and 1s signifying bits, 
A bit in the string can be flipped with a cost of 1.
find the minimum number of even-length subsegments that can be created by flipping the minimum bits in the string
a subsegment is a segment derived from another, by deleting some elements without changing the order of the remaining elements.

Note: priority is to minimize flips first, and then get the min number of even length subsegments

ex: 
input: 1110011000
Output: 2
because by flipping the first three 0 to 1, we get 11111111 and 00

ex: 110011
output: 3
by flipping 0 bits

ex: 100110
output: 1
by flipping 3 bits

ex: 101011
by flipping 2 bits : we either get 001111 or 111111
hence they are of lengths 1 and 2
so ans = 1
*/

import java.util.*;
public class OA_Q2 {


  // Approach 1: DP only(no segment trees): O(n^2) time
  static class DP {
    static class State implements Comparable<State> {
        int flips;
        int segments;

        State(int flips, int segments) {
            this.flips = flips;
            this.segments = segments;
        }

        @Override
        public int compareTo(State o) {
            if (this.flips != o.flips) return Integer.compare(this.flips, o.flips);
            return Integer.compare(this.segments, o.segments);
        }
    }

    public static int getMinSubsegments(String s) {
        int n = s.length();
        State[] dp = new State[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        dp[0] = new State(0, 0); // base case: 0 length has 0 flips and 0 segments

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= i - 2; j++) {
                if ((i - j) % 2 != 0) continue; // only consider even-length segments

                // Count 0s and 1s in s[j..i-1]
                int zeroCount = 0;
                int oneCount = 0;
                for (int k = j; k < i; k++) {
                    if (s.charAt(k) == '0') zeroCount++;
                    else oneCount++;
                }

                // We can flip either all 0s to 1s or all 1s to 0s
                int flipsNeeded = Math.min(zeroCount, oneCount);
                State candidate = new State(dp[j].flips + flipsNeeded, dp[j].segments + 1);
                if (candidate.compareTo(dp[i]) < 0) {
                    dp[i] = candidate;
                }
            }
        }

        return dp[n].segments;
    }  
  }


  // Approach 2: DP + Segment Tree: O(n log n) time
  static class DPAndSegmentTree {
    static class State implements Comparable<State> {
        int flips;
        int segments;

        State(int f, int s) {
            this.flips = f;
            this.segments = s;
        }

        @Override
        public int compareTo(State o) {
            if (this.flips != o.flips) return Integer.compare(this.flips, o.flips);
            return Integer.compare(this.segments, o.segments);
        }
    }

    static class Node {
        State c1 = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
        State c2 = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    static Node[] tree;
    static int N;

    static void build(int size) {
        N = 1;
        while (N < size) N <<= 1;
        tree = new Node[2 * N];
        for (int i = 0; i < 2 * N; i++) tree[i] = new Node();
    }

    static Node merge(Node a, Node b) {
        Node res = new Node();
        res.c1 = a.c1.compareTo(b.c1) < 0 ? a.c1 : b.c1;
        res.c2 = a.c2.compareTo(b.c2) < 0 ? a.c2 : b.c2;
        return res;
    }

    static void update(int idx, State c1, State c2, int x, int lx, int rx) {
        if (rx - lx == 1) {
            if (c1.compareTo(tree[x].c1) < 0) tree[x].c1 = c1;
            if (c2.compareTo(tree[x].c2) < 0) tree[x].c2 = c2;
            return;
        }
        int mid = (lx + rx) / 2;
        if (idx < mid) update(idx, c1, c2, 2 * x + 1, lx, mid);
        else update(idx, c1, c2, 2 * x + 2, mid, rx);
        tree[x] = merge(tree[2 * x + 1], tree[2 * x + 2]);
    }

    static Node query(int l, int r, int x, int lx, int rx) {
        if (lx >= r || rx <= l) return new Node();
        if (lx >= l && rx <= r) return tree[x];
        int mid = (lx + rx) / 2;
        Node s1 = query(l, r, 2 * x + 1, lx, mid);
        Node s2 = query(l, r, 2 * x + 2, mid, rx);
        return merge(s1, s2);
    }

    public static int getMinSubsegments(String s) {
        int n = s.length();
        if (n == 0) return 0;

        int[] p0 = new int[n + 1];
        for (int i = 0; i < n; i++) {
            p0[i + 1] = p0[i] + (s.charAt(i) == '0' ? 1 : 0);
        }

        int offset = n;
        int size = 2 * n + 1;
        build(size);

        State[] dp = new State[n + 1];
        for (int i = 0; i <= n; i++) {
            dp[i] = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        dp[0] = new State(0, 0);

        int f0 = 2 * p0[0] - 0;
        int idx0 = f0 + offset;
        update(idx0,
                new State(dp[0].flips - p0[0], dp[0].segments),
                new State(dp[0].flips + p0[0] - 0, dp[0].segments),
                0, 0, N);

        for (int i = 2; i <= n; i += 2) {
            int fi = 2 * p0[i] - i;
            int idxi = fi + offset;
            int p1 = i - p0[i];

            State cand1 = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Node q1 = query(idxi, size, 0, 0, N);
            if (q1.c1.flips != Integer.MAX_VALUE) {
                cand1 = new State(q1.c1.flips + p0[i], q1.c1.segments + 1);
            }

            State cand2 = new State(Integer.MAX_VALUE, Integer.MAX_VALUE);
            Node q2 = query(0, idxi, 0, 0, N);
            if (q2.c2.flips != Integer.MAX_VALUE) {
                cand2 = new State(q2.c2.flips + p1, q2.c2.segments + 1);
            }

            dp[i] = cand1.compareTo(cand2) < 0 ? cand1 : cand2;

            if (dp[i].flips != Integer.MAX_VALUE) {
                int fval = 2 * p0[i] - i;
                int idx = fval + offset;
                update(idx,
                        new State(dp[i].flips - p0[i], dp[i].segments),
                        new State(dp[i].flips + p0[i] - i, dp[i].segments),
                        0, 0, N);
            }
        }

        return dp[n].segments;
    }
  }

  public static void main(String[] args) {
        List<String> testCases = Arrays.asList(
            "1110011000",  // Expected: 2
            "110011",      // Expected: 3
            "100110",      // Expected: 1
            "101011",      // Expected: 1
            "11"           // Expected: 1
        );

        for (String s : testCases) {
            System.out.println("Input: " + s);
            int result = DP.getMinSubsegments(s);
            // int result = DPAndSegmentTree.getMinSubsegments(s);
            System.out.println("Minimum even-length subsegments: " + result + "\n");
        }
    }
}





