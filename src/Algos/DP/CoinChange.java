package Algos.DP;

import java.util.Arrays;

public class CoinChange {
    //https://leetcode.com/problems/coin-change/
    static int coinChange(int[]coins, int amount) {
        if(amount==0)
            return 0;
        int n = coins.length;
        int dp[] = new int[amount+1];
        dp[0] = 0;

        for(int i=0;i<n;i++) {
            if(amount>=coins[i]) {
                dp[coins[i]]=1;
                for(int j=coins[i]+1;j<=amount;j++) {
                    if(dp[j-coins[i]]>0) {
                        if(dp[j] > 0)
                            dp[j] = Math.min(dp[j-coins[i]]+1,dp[j]);
                        else
                            dp[j] = dp[j-coins[i]]+1;
                    }

                }
            }
        }
        return dp[amount] >0 ? dp[amount] : -1;
    }

    public static void main(String[] args) {
        int[] coins = {2};
        int amount = 1;
        System.out.println(coinChange(coins, amount));
    }
}
