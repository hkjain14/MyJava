package Algos.DP;

public class LongestPalinSubstr {

    static String LPS(String inp) {
        int n = inp.length();
        String ans = "";
        boolean[][] dp = new boolean[n+1][n+1];

        for(int gap=0;gap<n;gap++) {
            for(int i=0,j=gap;j<n;i++,j++) {
                if(gap==0)
                    dp[i][j] = true;
                else if(gap == 1)
                    dp[i][j] = inp.charAt(i) == inp.charAt(j);
                else
                    dp[i][j] = inp.charAt(i) == inp.charAt(j) && dp[i+1][j-1];
                if(dp[i][j]) {
                    String curr = inp.substring(i,j+1);
                    if(curr.length()>ans.length())
                        ans = curr;
                }
            }
        }

        return ans;
    }
    public static void main(String[] args) {
        String inp = "abccbc";
        System.out.println(LPS(inp));
    }
}
