package Interviews.MS_Aug2023;

import java.util.ArrayList;
import java.util.List;

public class OA_Q2 {
    // https://leetcode.com/problems/adding-two-negabinary-numbers/description/
    // Just in reverse order
    static int[] solution(int[] A, int[] B) {

        int l1 = A.length - 1;
        int l2 = B.length - 1, carry = 0;
        List<Integer> resultArr = new ArrayList<Integer>();

        int [] reverseA = new int[l1+1];
        int [] reverseB = new int[l2+1];

        for(int i=A.length-1;i>=0;i--) {
            reverseA[l1-i] = A[i];
        }
        for(int i=B.length-1;i>=0;i--) {
            reverseB[l2-i] = B[i];
        }

        while(l1>=0 || l2>=0 || carry != 0) {
            int n1 = l1>=0 ? reverseA[l1--]:0;
            int n2 = l2>=0 ? reverseB[l2--]:0;
            int sum = n1+n2+carry;
            int result = sum & 1;
            carry = -1 * (sum >> 1);
            resultArr.add(0, result);
        }

        int start = 0;
        while(start<resultArr.size()&& resultArr.get(start) == 0)
            start++;

        int[] ans = new int[resultArr.size() - start];
        for(int i=0;i<ans.length;i++) {
            ans[ans.length - 1 - i] = resultArr.get(i+start);
        }

        if(ans.length == 0) {
            ans = new int[1];
            ans[0] = 0;
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] A = {1};
        int[] B = {1};
        int[] solutionArr = solution(A,B);
        for(int i=0;i<solutionArr.length;i++)
            System.out.printf(solutionArr[i]+" ");
    }
}


