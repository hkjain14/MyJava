package Algos.Strings;

public class ReverseVowInString {

    static boolean isConsonant(String c) {
        String vowels = "aeiouAEIOU";
        return !vowels.contains(c);
    }

    static String reverseVow(String inp) {
       StringBuilder ans = new StringBuilder(inp);
       int start = 0, end = inp.length()-1;
       while(start<end) {
           while(start<end && isConsonant(String.valueOf(inp.charAt(start)))) {
               start++;
           }
           while(start<end && isConsonant(String.valueOf(inp.charAt(end)))) {
               end--;
           }
           if(start<end) {
               char temp = ans.charAt(start);
               ans.setCharAt(start, ans.charAt(end));
               ans.setCharAt(end, temp);
               start++;
               end--;
           }
       }

       return ans.toString();
    }
    public static void main(String[] args) {
        String s = "hUrdOsbsIsnEdbbdA";
        System.out.println(reverseVow(s));
    }
}
