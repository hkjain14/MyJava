package Algos.Arrays;

public class SplitIn3Subarrays {
    // Prob : https://leetcode.com/problems/ways-to-split-array-into-three-subarrays
    // Soln explanation : https://leetcode.com/problems/ways-to-split-array-into-three-subarrays/solutions/999257/c-java-python-o-n-with-picture/

    public static int waysToSplit(int[] nums) {
        int ans = 0,mod=1000000007, n = nums.length;
        int[] prefixSum = new int[n];
        prefixSum[0] = nums[0];
        for(int i=1;i<n;i++) {
            prefixSum[i] = prefixSum[i-1]+nums[i];
        }

        for(int i=0,j=0,k=0;i<n-2;i++) {
            while (j<=i || (j<n-1 && prefixSum[j]-prefixSum[i] < prefixSum[i]))
                j++;

            while (k<j || (k<n-1 && prefixSum[k] - prefixSum[i] <= prefixSum[n-1]-prefixSum[k]))
                k++;

            k--;
            ans = (ans+(k-j+1))%mod;
        }

        return ans;
    }
    public static void main(String[] args) {
        int[] nums = {4,2,3,0,3,5,3,12};
        System.out.println(waysToSplit(nums));
    }
}
