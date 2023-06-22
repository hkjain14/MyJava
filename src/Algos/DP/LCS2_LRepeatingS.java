package Algos.DP;

public class LCS2_LRepeatingS {
    //https://www.geeksforgeeks.org/longest-repeating-subsequence/

    static int findLRS(String inp) {
        int n = inp.length();
        int[][] dp = new int[n+1][n+1];
        for(int i=n;i>=0;i--) {
            for(int j=n;j>=0;j--) {
                if(i==n || j==n)
                    dp[i][j] = 0;
                else {
                    if(inp.charAt(i) == inp.charAt(j) && i!=j)
                        dp[i][j] = 1 + dp[i+1][j+1];
                    else
                        dp[i][j] = Math.max(dp[i+1][j],dp[i][j+1]);
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        String inp = "abacbc";
        System.out.println(findLRS(inp));
    }
}
