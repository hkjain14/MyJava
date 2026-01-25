package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
Given input that provides the (directed) steps that each cart takes as pairs, write a function that
identifies all the start locations, and a collection of all of the possible ending locations for each start location.
 */

/*
Time: O(V + E + totalEndingsUnions) ~ near O(V + E) for typical graphs.
Space: O(V + E) for graph + memo sets.
V = number of locations, E = number of edges.

We use DFS + memoization:
    If a node has no outgoing edges → it is an ending itself.
    Otherwise, its endings are the union of endings of its neighbors.
Memoization prevents recomputing the same subtree endings repeatedly.
*/

public class ConveyorRoutes {

    public static Map<String, Set<String>> findStartToEndings(List<List<String>> paths) {
        Map<String, List<String>> graph = new HashMap<>();
        Map<String, Integer> indegree = new HashMap<>();
        Set<String> allNodes = new HashSet<>();

        // Build graph + indegree counts
        for (List<String> edge : paths) {
            String from = edge.get(0);
            String to = edge.get(1);
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
            indegree.put(to, indegree.getOrDefault(to, 0) + 1);

            allNodes.add(from);
            allNodes.add(to);
        }

        // Start nodes = indegree 0
        List<String> starts = new ArrayList<>();
        for (String node : allNodes) {
            if (indegree.getOrDefault(node, 0) == 0) {
                starts.add(node);
            }
        }

        
        // Nodes are added when entering, and removed when leaving. 
        // For cycle protection in CURRENT DFS CHAIN ONLY.
        Set<String> visiting = new HashSet<>();
        Map<String, Set<String>> result = new HashMap<>();
        Map<String, Set<String>> memo = new HashMap<>(); // Memo for endings(node)
        for (String start : starts) {
            result.put(start, dfsEndings(start, graph, memo, visiting));
        }

        return result;
    }

    private static Set<String> dfsEndings(
            String node,
            Map<String, List<String>> graph,
            Map<String, Set<String>> memo,
            Set<String> visiting
    ) {
        if (memo.containsKey(node)) {
            return memo.get(node);
        }

        // Cycle protection
        if (visiting.contains(node)) {
            return new HashSet<>();
        }

        visiting.add(node);
        List<String> neighbors = graph.getOrDefault(node, Collections.emptyList());
        Set<String> endings = new HashSet<>();

        if (neighbors.isEmpty()) {
            // If no outgoing edges => it's an ending location
            endings.add(node);
        } else {
            for (String nei : neighbors) {
                endings.addAll(dfsEndings(nei, graph, memo, visiting));
            }
        }

        visiting.remove(node);
        memo.put(node, endings);
        return endings;
    }

    public static void main(String[] args) {
        List<List<String>> paths = Arrays.asList(
                Arrays.asList("B", "K"),
                Arrays.asList("C", "K"),
                Arrays.asList("E", "L"),
                Arrays.asList("F", "G"),
                Arrays.asList("J", "M"),
                Arrays.asList("E", "F"),
                Arrays.asList("C", "G"),
                Arrays.asList("A", "B"),
                Arrays.asList("A", "C"),
                Arrays.asList("G", "H"),
                Arrays.asList("G", "I")
        );
        System.out.println(findStartToEndings(paths)); // {A=[H, I, K], E=[H, I, L], J=[M]}
    }
}

