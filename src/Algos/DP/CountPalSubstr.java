package Algos.DP;

public class CountPalSubstr {

    static int CountPS(String inp) {
        int n = inp.length(), ans=0;
        if(n == 0)
            return 0;
        boolean[][] dp=new boolean[n+1][n+1];

        for(int gap=0;gap<n;gap++) {
            for(int i=0, j=gap;j<n;i++,j++) {
                if(gap==0) {
                    dp[i][j] = true;
                } else if(gap == 1) {
                    dp[i][j] = inp.charAt(i) == inp.charAt(j);
                } else {
                    dp[i][j] = inp.charAt(i) == inp.charAt(j) && dp[i+1][j-1];
                }
                if(dp[i][j])
                    ans++;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        String inp = "abccbc";
        System.out.println(CountPS(inp));
    }
}
