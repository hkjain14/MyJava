package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

// https://leetcode.com/problems/text-justification/

/*
Time complexity: O(n*k)
Space complexity: O(m)
n = words.length, k = average length of a word, and m = maxWidth.
 */

public class JustifyText {
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<>();
        int i = 0;

        while (i < words.length) {
            List<String> currentLineWords = getWordsInCurrentLine(i, words, maxWidth);
            i += currentLineWords.size();
            ans.add(createLine(currentLineWords, i, words, maxWidth));
        }

        return ans;
    }

    private static List<String> getWordsInCurrentLine(int i, String[] words, int maxWidth) {
        List<String> currentLine = new ArrayList<>();
        int currLength = 0;

        while (i < words.length && currLength + words[i].length() <= maxWidth) {
            currentLine.add(words[i]);
            currLength += words[i].length() + 1;
            i++;
        }

        return currentLine;
    }

    private static String createLine(
            List<String> line,
            int i,
            String[] words,
            int maxWidth
    ) {
        int baseLength = -1; // To account for last word, which doesn't have space after it.
        for (String word : line) {
            baseLength += word.length() + 1; // +1 because of space after word.
        }

        int extraSpaces = maxWidth - baseLength;

        if (line.size() == 1 || i == words.length) {
            return String.join(" ", line) + " ".repeat(extraSpaces);
        }

        int wordCount = line.size() - 1;
        int extraSpacesPerWord = extraSpaces / wordCount;
        int needsExtraSpace = extraSpaces % wordCount;

        for (int j = 0; j < needsExtraSpace; j++) {
            line.set(j, line.get(j) + " ");
        }

        for (int j = 0; j < wordCount; j++) {
            line.set(j, line.get(j) + " ".repeat(extraSpacesPerWord));
        }

        return String.join(" ", line);
    }

    private static void print(List<String> ans) {
        System.out.println();
        for(String line: ans)
            System.out.println("\"" + line + "\"");
    }

    public static void main(String[] args) {
       String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
       int maxWidth = 16;
       print(fullJustify(words, maxWidth));

        String[] words2 = {"What","must","be","acknowledgment","shall","be"};
        int maxWidth2 = 16;
        print(fullJustify(words2, maxWidth2));

        String[] words3 = {"Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"};
        int maxWidth3 = 20;
        print(fullJustify(words3, maxWidth3));
    }
}
