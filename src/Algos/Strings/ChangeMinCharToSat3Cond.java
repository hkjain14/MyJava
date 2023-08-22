package Algos.Strings;

public class ChangeMinCharToSat3Cond {
    // https://leetcode.com/problems/change-minimum-characters-to-satisfy-one-of-three-conditions/solutions/1032042/java-detailed-explanation-find-the-boundary-letter/
    public static int op1(String a, String b) {
        int ans = Integer.MAX_VALUE, count;
        for(int i=1;i<26;i++) {
            count =0;
            for(char c : a.toCharArray()) {
                if(c-'a'>=i)
                    count++;
            }
            for(char c : b.toCharArray()) {
                if(c-'a'<i)
                    count++;
            }
            if(count<ans)
                ans = count;
        }
        return ans;
    }

    public static int op2(String a, String b) {
        int max = 0;
        int[] arr = new int[26];
        for(char c : a.toCharArray()) {
            arr[c-'a']++;
        }
        for(char c : b.toCharArray()) {
            arr[c-'a']++;
        }
        for(int i=0;i<26;i++) {
            if(arr[i]>max)
                max++;
        }
        return a.length()+b.length()-max;
    }

    public static int minCharacters(String a, String b) {
        return(Math.min(Math.min(op1(a,b),op1(b,a)),op2(a,b)));
    }
    public static void main(String[] args) {
        String a = "aba";
        String b = "bca";
        System.out.println(minCharacters(a,b));
    }
}
