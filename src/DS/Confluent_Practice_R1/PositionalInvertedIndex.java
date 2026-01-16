package DS.Confluent_Practice_R1;

import java.util.*;
import java.util.stream.Collectors;

public class PositionalInvertedIndex {
    public static class Document {
        public final int docId;
        public final String text;
        public Document(int docId, String text) { this.docId = docId; this.text = text; }
    }

    // token -> (docId -> list of positions)
    private final Map<String, Map<Integer, List<Integer>>> index = new HashMap<>();

    // Build index from docs. Positions are 0-based token indices within each document.
    public void buildIndex(List<Document> docs) {
        index.clear();
        for (Document d : docs) {
            if (d.text == null) continue;
            String[] tokens = d.text.split("[^\\p{L}\\p{N}]+");
            int pos = 0;
            for (String t : tokens) {
                if (t.isEmpty()) continue;
                String token = t.toLowerCase(Locale.ROOT);
                Map<Integer, List<Integer>> posting = index.computeIfAbsent(token, k -> new HashMap<>());
                List<Integer> positions = posting.computeIfAbsent(d.docId, k -> new ArrayList<>());
                positions.add(pos);
                pos++;
            }
        }
    }

    /** Word search: return list of docIds that contain the token (case-insensitive) */
    public List<Integer> queryWord(String word) {
        if (word == null || word.isEmpty()) return Collections.emptyList();
        String token = word.toLowerCase(Locale.ROOT);
        Map<Integer, List<Integer>> posting = index.get(token);
        if (posting == null) return Collections.emptyList();
        return new ArrayList<>(posting.keySet()); // VIMP: KeySet of the map returned;
    }

    /**
     * Phrase search: return list of docIds that contain the exact phrase (case-insensitive),
     * tokens must be consecutive and in the same order.
     */
    public List<Integer> queryPhrase(String phrase) {
        if (phrase == null || phrase.isEmpty()) return Collections.emptyList();
        // Tokenize the phrase using same tokenizer as buildIndex
        String[] raw = phrase.split("[^\\p{L}\\p{N}]+");
        List<String> tokens = new ArrayList<>();
        for (String t : raw) {
            if (t.isEmpty()) continue;
            tokens.add(t.toLowerCase(Locale.ROOT));
        }
        if (tokens.isEmpty()) return Collections.emptyList();
        if (tokens.size() == 1) {
            return queryWord(tokens.get(0));
        }

        // Get posting maps for each token; if any token missing, phrase not present anywhere.
        List<Map<Integer, List<Integer>>> postings = new ArrayList<>();
        for (String tok : tokens) {
            Map<Integer, List<Integer>> p = index.get(tok);
            if (p == null) return Collections.emptyList(); // missing token => empty result
            postings.add(p);
        }

        // Find candidate docIds = intersection of docId sets across tokens.
        // Start from the smallest posting (optimization).
        int bestIdx = 0;
        int bestSize = postings.get(0).size();
        for (int i = 1; i < postings.size(); ++i) {
            int sz = postings.get(i).size();
            if (sz < bestSize) { bestSize = sz; bestIdx = i; }
        }

        Set<Integer> candidateDocs = new HashSet<>(postings.get(bestIdx).keySet());
        for (int i = 0; i < postings.size(); ++i) {
            if (i == bestIdx) continue;
            candidateDocs.retainAll(postings.get(i).keySet());
            if (candidateDocs.isEmpty()) return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        // For each candidate doc, check positional adjacency
        for (int docId : candidateDocs) {
            // For efficient lookup of positions for subsequent tokens, we convert to HashSet.
            // But we iterate over positions of the first token (or best token) to find consecutive runs.
            // To simplify, choose the first token's positions as base (but ideally choose smallest positions list).
            List<Integer> firstPositions = postings.get(0).get(docId);
            if (firstPositions == null) continue; // defensive
            // Build sets for tokens 1..k-1 for fast contains checks
            List<Set<Integer>> posSets = new ArrayList<>();
            for (int t = 1; t < tokens.size(); ++t) {
                List<Integer> ps = postings.get(t).get(docId);
                // defensive check
                if (ps == null) { posSets = null; break; }
                posSets.add(new HashSet<>(ps));
            }
            if (posSets == null) continue;
            // For each position p in firstPositions, check if p+1 in token1 posSet, p+2 in token2 posSet, ...
            boolean found = false;
            for (int p : firstPositions) {
                boolean ok = true;
                for (int t = 0; t < posSets.size(); ++t) {
                    int expected = p + 1 + t; // token 1 should be p+1, token 2 p+2, ...
                    if (!posSets.get(t).contains(expected)) {
                        ok = false;
                        break;
                    }
                }
                if (ok) { found = true; break; }
            }
            if (found) result.add(docId);
        }

        return result;
    }

    // --- simple demo
    public static void main(String[] args) {
        List<Document> docs = Arrays.asList(
                new Document(1, "The quick brown fox jumps over the lazy dog."),
                new Document(2, "The quick brown fox."),
                new Document(3, "A quick brown dog outpaces a quick fox."),
                new Document(4, "Quick brown foxes are not the same as quick brown fox.")
        );

        PositionalInvertedIndex idx = new PositionalInvertedIndex();
        idx.buildIndex(docs);

        System.out.println("Word search 'quick' -> " + idx.queryWord("quick"));
        System.out.println("Word search 'fox' -> " + idx.queryWord("fox"));
        System.out.println("Phrase search 'quick brown' -> " + idx.queryPhrase("quick brown"));
        System.out.println("Phrase search 'quick brown fox' -> " + idx.queryPhrase("quick brown fox"));
        System.out.println("Phrase search 'brown fox jumps' -> " + idx.queryPhrase("brown fox jumps"));
        System.out.println("Phrase search 'quick fox' -> " + idx.queryPhrase("quick fox")); // not consecutive
    }
}
