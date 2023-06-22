package Algos.DP;

public class LCS {
    //https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
    static int findLCS(String inp1, String inp2) {
        int n1 = inp1.length(),n2 = inp2.length();
        int [][] dp = new int[n1+1][n2+1];

        for(int i=n1;i>=0;i--) {
            for(int j=n2;j>=0;j--) {
                if(i==n1 || j==n2)
                    dp[i][j] = 0;
                else {
                    if(inp1.charAt(i) == inp2.charAt(j))
                        dp[i][j] = 1 + dp[i+1][j+1];
                    else
                        dp[i][j] = Math.max(dp[i+1][j], dp[i][j+1]);
                }
            }
        }

        return dp[0][0];
    }

    public static void main(String[] args) {
        String inp1 = "AGGTAB";
        String inp2 = "GXTXAYB";
        System.out.println(findLCS(inp1,inp2));

    }
}
