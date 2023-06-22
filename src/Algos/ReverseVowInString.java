package Algos;

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
               String startString = String.valueOf(ans.charAt(start));
               String endString = String.valueOf(ans.charAt(end));
               ans.replace(start,start+1,endString);
               ans.replace(end,end+1,startString);
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
