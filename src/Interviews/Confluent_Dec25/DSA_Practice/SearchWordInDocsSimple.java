package Interviews.Confluent_Dec25.DSA_Practice;
import java.util.*;
import java.util.regex.*;

/**
 * Complexity (1 query):
 * Time: O(D*L)
 * D = no of docs
 * L = avg length of a doc
 *
 * Space: O(1) + O(x)
 * x: Ans list size
 */

public class SearchWordInDocsSimple {
    public static class Document {
        public final int docId;
        public final String text;
        public Document(int docId, String text) { this.docId = docId; this.text = text; }
    }

    private static boolean patternSolver(String word, String text) {
        // \bWORD\b with Pattern.quote(), so that special chars in word (like C++) don't break the regex
        Pattern p = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        return m.find();
    }

    private static boolean bruteSolver(String word, String text) {
        if (word == null || text == null) return false;

        String target = word.toLowerCase();
        String searchSpace = text.toLowerCase();
        int n = searchSpace.length();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char c = searchSpace.charAt(i);

            // word characters: letter or digit
            if (Character.isLetterOrDigit(c)) {
                current.append(Character.toLowerCase(c));
            } else {
                // non-word boundary reached → check accumulated word
                if (!current.isEmpty()) {
                    if (current.toString().equals(target)) {
                        return true;
                    }
                    current.setLength(0);
                }
            }
        }

        // check last word (if text didn't end with punctuation/space)
        if (!current.isEmpty()) {
            return current.toString().equals(target);
        }

        return false;
    }


    /** Find all document ids that contain the given word (case-insensitive, whole word match). */
    public static List<Integer> findDocumentsContainingWord(String word, List<Document> docs) {
        if (word == null || word.isEmpty()) return Collections.emptyList();
        List<Integer> result = new ArrayList<>();
        for (Document d : docs) {
            if (d.text == null) continue;

            if (bruteSolver(word, d.text)) {
                result.add(d.docId);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Document> docs = Arrays.asList(
                new Document(1, "The quick brown fox jumps over the lazy dog."),
                new Document(2, "Earth is the third planet from the Sun."),
                new Document(3, "The artisan crafts beautiful art."),
                new Document(4, "R&D and versioning: art-based solutions.")
        );

        System.out.println(findDocumentsContainingWord("art", docs)); // prints [3, 4]
        System.out.println(findDocumentsContainingWord("earth", docs)); // prints [2]
        System.out.println(findDocumentsContainingWord("the", docs)); // prints [1,2,3]
    }
}
