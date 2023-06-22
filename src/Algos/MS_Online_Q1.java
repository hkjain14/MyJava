package Algos;

import java.util.*;
public class MS_Online_Q1 {
    // https://stackoverflow.com/questions/56000424/algorithm-to-find-shortest-continuous-subarray-that-contains-all-values-from-a-s
    // Instead of values from a set, ints from a range L to R inclusive, are to be found in an array
    static int solution(int[] A, int L, int R) {
        int ans = -1,l=-1,nextInitial=0;
        if(R-L+1 > A.length)
            return ans;
        if(L==R) {
            for(int i=0;i<A.length;i++) {
                if(A[i]==L) {
                    ans=1;
                    break;
                }
            }
            return ans;
        }
        Set<Integer> setOfIntToCheck = new HashSet<>();
        for (int i = L; i <= R; i++) {
            setOfIntToCheck.add(i);
        }
        Set<Integer> setMoving = new HashSet<>();
        ans = Integer.MAX_VALUE;

        for(int i=0;i<A.length;i++) {
            if(setOfIntToCheck.contains(A[i])) {
                setMoving.add(A[i]);
                if(setMoving.size() == 2) {
                    nextInitial=i;
                }
                if(setMoving.size() == 1) {
                    l=i;
                }
                if(setOfIntToCheck.equals(setMoving)) {
                    ans = Math.min(ans,i+1-l);
                    i=nextInitial-1;
                    l=nextInitial;
                    setMoving = new HashSet<>();
                }
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
    public static void main(String[] args) {
        int[] A = {1,4,99,98,97,2,6,3,14};
        int L=2,R=4;
        System.out.println(solution(A,L,R));
    }
}


