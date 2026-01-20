package Interviews.Confluent_Dec25.DSA_Practice;

import java.util.*;

/**
 * Time complexity:
 * Indexing: O(D*L)
 * D = no of docs
 * L = avg length of a doc
 *
 * Word Querying: O(1)
 *
 *
 * Phrase Querying: Fetching postings + Candidate Doc Intersection cost + y
 * y = For each candidate doc d, we iterate positions of base token P_base(d) and check k-1 contains() operations on sets for subsequent tokens. So per doc cost ~ O(P_base(d) * (k-1)).
 * = O(k + cost_intersection + Σ_d P_base(d) * (k-1))
 *
 * Space complexity: O(sum of all postings)
 * = sum over tokens of number of (docId, positions)
 */

public class SearchPhrasePositionalInvertedIndex {
    public static class Document {
        public final int docId;
        public final String text;
        public Document(int docId, String text) { this.docId = docId; this.text = text; }
    }

    // word -> (docId -> list of positions where this token appears in that doc)
    // Ex:        the -> { [1 -> (0,6)] , [2 -> (0)] , [4 -> (5)]}
    private final Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();

    // Build index from docs. Positions are 0-based token indices within each document.
    public void buildIndex(List<Document> docs) {
        index.clear();
        for (Document d : docs) {
            if (d.text == null)
                continue;

            // For keeping unicode chars (and non-Eng chars): "[^\\p{L}\\p{N}]+"
            String regex = "[^A-Za-z0-9]+";
            String[] tokens = d.text.split(regex);

            int pos = 0;
            for (String t : tokens) {
                if (t.isEmpty())
                    continue;

                Map<Integer, List<Integer>> posting = index.computeIfAbsent(t.toLowerCase(), k -> new HashMap<>());
                /* Above line is same as:
                if (!index.containsKey(t.toLowerCase())) {
                    index.put(t.toLowerCase(), new HashMap<>());
                }
                Map<Integer, List<Integer>> posting = index.get(t.toLowerCase());
                 */
                List<Integer> positions = posting.computeIfAbsent(d.docId, k -> new ArrayList<>());
                positions.add(pos);
                pos++;
            }
        }
    }

    /** Word search: return list of docIds that contain the token (case-insensitive) */
    public List<Integer> queryWord(String word) {
        if (word == null || word.isEmpty())
            return Collections.emptyList();

        Map<Integer, List<Integer>> posting = index.get(word.toLowerCase());

        if (posting == null)
            return Collections.emptyList();
        return new ArrayList<>(posting.keySet()); // VIMP: KeySet of the map returned;
    }

    /**
     * Phrase search: return list of docIds that contain the exact phrase (case-insensitive),
     * tokens must be consecutive and in the same order.
     */
    public List<Integer> queryPhrase(String phrase) {
        if (phrase == null || phrase.isEmpty())
            return Collections.emptyList();

        // Tokenize the phrase using same tokenizer as buildIndex
        String[] phraseTokens = phrase.split("[^A-Za-z0-9]+");

        // Convert to lowercase and check for empty.
        List<String> queryTokens = new ArrayList<>();
        for (String phraseWord : phraseTokens) {
            if (!phraseWord.isEmpty())
                queryTokens.add(phraseWord.toLowerCase());
        }

        if (queryTokens.isEmpty())
            return Collections.emptyList();
        if (queryTokens.size() == 1)
            return queryWord(queryTokens.get(0));

        // Get posting maps for each query token; if any token missing, phrase not present anywhere.
        // postings: List of maps corresponding to each word in query phrase. It is in order of query phrase.
        List<Map<Integer, List<Integer>>> postings = new ArrayList<>();
        for (String tok : queryTokens) {
            Map<Integer, List<Integer>> p = index.get(tok);
            if (p == null)
                return Collections.emptyList(); // missing token => empty result
            postings.add(p);
        }

        // Find candidate docIds, which are the intersection of docId sets across tokens.

        // Skippable optimisation: Start from the smallest posting.
        int bestIdx = 0;
        int bestSize = postings.get(0).size();
        for (int i = 1; i < postings.size(); ++i) {
            int sz = postings.get(i).size();
            if (sz < bestSize) {
                bestSize = sz;
                bestIdx = i;
            }
        }

        Set<Integer> candidateDocs = new HashSet<>(postings.get(bestIdx).keySet());
        // Currently candidateDocs = DocId Keys corresponding to word present in minimum number of docIds
        for (int i = 0; i < postings.size(); ++i) {
            if (i == bestIdx)
                continue;

            candidateDocs.retainAll(postings.get(i).keySet()); // Intersection of current state of set with docId keys of current iteration.

            // Break out if candidateDocs (ie intersection) now becomes empty.
            if (candidateDocs.isEmpty())
                return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        // For each candidate doc, check positional adjacency
        for (int docId : candidateDocs) {
            // For efficient lookup of positions for subsequent tokens, we convert to HashSet.
            // But we iterate over positions of the first token (or best token) to find consecutive runs.
            // To simplify, choose the first token's positions as base (but ideally choose smallest positions list).

            List<Integer> firstPositions = postings.get(0).get(docId);
            if (firstPositions == null)
                continue; // defensive

            // Build sets for tokens 1..k-1 for fast contains checks
            List<Set<Integer>> posSetsList = new ArrayList<>();
            for (int t = 1; t < queryTokens.size(); ++t) {
                List<Integer> ps = postings.get(t).get(docId);
                // defensive check
                if (ps == null) {
                    posSetsList = null;
                    break;
                }
                posSetsList.add(new HashSet<>(ps));
            }

            if (posSetsList == null)
                continue;

            // For each position p in firstPositions, check if p+1 in token1 posSet, p+2 in token2 posSet, ...
            boolean found = false;
            for (int p : firstPositions) {
                boolean ok = true;
                for (int t = 0; t < posSetsList.size(); ++t) {
                    int expected = p + 1 + t; // token 1 should be p+1, token 2 p+2, ...
                    if (!posSetsList.get(t).contains(expected)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    found = true;
                    break;
                }
            }
            if (found)
                result.add(docId);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Document> docs = Arrays.asList(
                new Document(1, "The quick brown fox jumps over the lazy dog."),
                new Document(2, "The quick brown fox."),
                new Document(3, "A quick brown dog outpaces a quick fox."),
                new Document(4, "Quick brown foxes are not the same as quick brown fox.")
        );

        SearchPhrasePositionalInvertedIndex idx = new SearchPhrasePositionalInvertedIndex();
        idx.buildIndex(docs);

        System.out.println("\nWord search 'quick' -> " + idx.queryWord("quick")); // [1, 2, 3, 4]
        System.out.println("Word search 'fox' -> " + idx.queryWord("fox")); // [1, 2, 3, 4]
        System.out.println("Word search 'India' -> " + idx.queryWord("India")); // []

        System.out.println("\nPhrase search 'quick brown' -> " + idx.queryPhrase("quick brown")); // [1, 2, 3, 4]
        System.out.println("Phrase search 'quick brown fox' -> " + idx.queryPhrase("quick brown fox")); // [1, 2, 4]
        System.out.println("Phrase search 'brown fox jumps' -> " + idx.queryPhrase("brown fox jumps")); // [1]
        System.out.println("Phrase search 'quick fox' -> " + idx.queryPhrase("quick fox")); // [3]
        System.out.println("Phrase search 'quick fox jumps' -> " + idx.queryPhrase("quick fox jumps")); // []
    }
}
