package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
Time: O(N)
Space: O(N)
N: number of input rows
 */

/*
To get final list of actions in:
- Sorted order => Use TreeMap
- Same order as Insertion => Use LinkedHashMap
 */

public class GroupUsersByAction {
    public static Map<String, List<String>> groupByAction(List<List<String>> users) {
        Map<String, List<String>> map = new HashMap<>();
        for (List<String> row : users) {
            String name = row.get(0);
            String action = row.get(1);
            map.computeIfAbsent(action, k -> new ArrayList<>()).add(name);
        }

        return map;
    }

    public static void main(String[] args) {
        List<List<String>> users = Arrays.asList(
                Arrays.asList("Bob", "Disconnect"),
                Arrays.asList("John", "Connect"),
                Arrays.asList("Alice", "Connect")
        );

        Map<String, List<String>> result = groupByAction(users);
        System.out.println(result);
        // {Disconnect=[Bob], Connect=[John, Alice]}
    }
}
