package DS.Confluent_Practice_R1;
import java.util.*;
import java.util.regex.*;

public class SearchWordInDocsSimple {
    public static class Document {
        public final int docId;
        public final String text;
        public Document(int docId, String text) { this.docId = docId; this.text = text; }
    }

    /** Find all document ids that contain the given word (case-insensitive, whole word match). */
    public static List<Integer> findDocumentsContainingWord(String word, List<Document> docs) {
        if (word == null || word.isEmpty()) return Collections.emptyList();
        // \bWORD\b with quoting so special chars in word don't break the regex
        Pattern p = Pattern.compile("\\b" + Pattern.quote(word) + "\\b", Pattern.CASE_INSENSITIVE);
        List<Integer> result = new ArrayList<>();
        for (Document d : docs) {
            if (d.text == null) continue;
            Matcher m = p.matcher(d.text);
            if (m.find()) result.add(d.docId);
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
