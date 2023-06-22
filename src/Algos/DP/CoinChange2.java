package Algos.DP;

public class CoinChange2 {
    //https://www.geeksforgeeks.org/coin-change-dp-7/
    static long coinChange(int[]coins, int amount) {
         int n = coins.length;
        long dp[] = new long[amount+1];
        dp[0] = 1;

        for(int i=0;i<n;i++) {
            for(int j=coins[i];j<=amount;j++) {
                dp[j]+=dp[j-coins[i]];
            }
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {2,3,5,6};
        int amount = 10;
        System.out.println(coinChange(coins, amount));
    }
}
