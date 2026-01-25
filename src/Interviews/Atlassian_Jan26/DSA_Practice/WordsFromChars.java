package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

//https://leetcode.com/problems/find-words-that-can-be-formed-by-characters/description/

/*
Time complexity: O(n + m*k)
 n = length of chars,
 m = length of words array
 k = avg length of each word in words

Space complexity: O(1)
 */

public class WordsFromChars {
    public static int countCharacters(String[] words, String chars) {
        int ans = 0;
        Map<Character,Integer> wordMap;
        Map<Character,Integer> counts = new HashMap<>();
        for (Character c : chars.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        for(String word: words) {
            wordMap = new HashMap<>();
            for (Character c : word.toCharArray()) {
                wordMap.put(c, wordMap.getOrDefault(c, 0) + 1);
            }
            boolean isValid = true;
            for(char c: wordMap.keySet()) {
                if(wordMap.get(c) > counts.getOrDefault(c,0)) {
                    isValid = false;
                    break;
                }
            }
            if(isValid)
                ans+= word.length();
        }

        return ans;
    }

    public static void main(String[] args) {
       String[] words1 = {"cat","bt","hat","tree"};
       String chars1 = "atach";
       System.out.println(countCharacters(words1, chars1));

       String[] words2 = {"hello","world","leetcode"};
       String chars2 = "welldonehoneyr";
        System.out.println(countCharacters(words2, chars2));
    }
}
