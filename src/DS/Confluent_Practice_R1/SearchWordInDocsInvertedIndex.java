package DS.Confluent_Practice_R1;

import java.util.*;

/**
 * Time complexity:
 * Indexing: O(D*L)
 * D = no of docs
 * L = avg length of a doc
 *
 * Querying: O(1)
 *
 * Space complexity: O(V + postings)
 * V = no of unique tokens
 * postings = no of (token,docid) pairs stored
 */

public class SearchWordInDocsInvertedIndex {
    public static class Document {
        public final int docId;
        public final String text;
        public Document(int docId, String text) { this.docId = docId; this.text = text; }
    }

    // token -> {d1, d3}
    private final Map<String, Set<Integer>> index = new HashMap<>();

    /**
     * Build index from list of documents.
     * Tokenization: split on non-letter/digit characters, normalize to lower-case.
     */
    public void buildIndex(List<Document> docs) {
        index.clear();
        for (Document d : docs) {
            if (d.text == null) continue;
            // For keeping unicode chars (and non-Eng chars): "[^\\p{L}\\p{N}]+"
            String regex = "[^A-Za-z0-9]+";
            String[] tokens = d.text.split(regex);
            // Use a set to avoid adding same doc id for repeated tokens in same doc
            Set<String> seen = new HashSet<>();
            for (String t : tokens) {
                if (t.isEmpty()) continue;
                String token = t.toLowerCase(Locale.ROOT);
                if (!seen.add(token)) continue;

                // Following lines can be replaced by
                // index.computeIfAbsent(token, k -> new HashSet<>()).add(d.docId);
                if (!index.containsKey(token)) {
                    index.put(token, new HashSet<>());
                }
                index.get(token).add(d.docId);
            }
        }
    }

    /**
     * Query for a single word (case-insensitive).
     */
    public List<Integer> query(String word) {
        if (word == null || word.isEmpty()) return Collections.emptyList();

        Set<Integer> docSet = index.get(word.toLowerCase());
        if (docSet == null) return Collections.emptyList();
        return new ArrayList<>(docSet);
    }

    // Demo
    public static void main(String[] args) {
        List<Document> docs = Arrays.asList(
                new Document(1, "The quick brown fox jumps over the lazy dog."),
                new Document(2, "Earth is the third planet from the Sun."),
                new Document(3, "The artisan crafts beautiful art."),
                new Document(4, "R&D and versioning: art-based solutions.")
        );

        SearchWordInDocsInvertedIndex idx = new SearchWordInDocsInvertedIndex();
        idx.buildIndex(docs);

        System.out.println(idx.query("art"));   // [3,4]
        System.out.println(idx.query("earth")); // [2]
        System.out.println(idx.query("the"));   // [1,2,3]
    }
}

