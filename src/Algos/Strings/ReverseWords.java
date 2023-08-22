package Algos.Strings;

public class ReverseWords {
    /*
    static StringBuilder trimSpaces(String s) {
        int left = 0, right = s.length() - 1;
        // remove leading spaces
        while (left <= right && s.charAt(left) == ' ') ++left;

        // remove trailing spaces
        while (left <= right && s.charAt(right) == ' ') --right;

        // reduce multiple spaces to single one
        StringBuilder sb = new StringBuilder();
        while (left <= right) {
            char c = s.charAt(left);

            if (c != ' ') sb.append(c);
            else if (sb.charAt(sb.length() - 1) != ' ') sb.append(c);

            ++left;
        }
        return sb;
    }
    */

    static void reverse(StringBuilder sb, int l, int r) {
        while(l<r) {
            char temp = sb.charAt(l);
            sb.setCharAt(l,sb.charAt(r));
            sb.setCharAt(r,temp);
            l++;
            r--;
        }
    }

    static void reverseWords(StringBuilder sb) {
        int l=0,r=1;
        while(l<sb.length()) {
            while(r<sb.length() && sb.charAt(r)!=' ')r++;
            reverse(sb,l,r-1);
            l=r+1;r=l+1;
        }
    }

    static String reverseWords(String inp) {
        StringBuilder sb = new StringBuilder(inp);
        reverse(sb, 0, sb.length()-1);
        reverseWords(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String inp = "i love programming very much";
        System.out.println(reverseWords(inp));
    }
}
