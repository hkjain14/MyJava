package DS.Confluent_Practice_R1;
import java.util.*;

/**
 * Phase 1: exact matching only (no variadic support).
 */
public class FuncTypeSoln {
    static class Func {
        final String name;
        final List<String> params; // exact ordered parameter types

        Func(String name, List<String> params) {
            this.name = name;
            this.params = List.copyOf(params);
        }

        boolean matches(List<String> query) {
            if (query == null) query = Collections.emptyList();
            if (query.size() != params.size()) return false;
            for (int i = 0; i < params.size(); i++) {
                if (!params.get(i).equals(query.get(i))) return false;
            }
            return true;
        }
    }

    public static List<String> findMatches(List<Func> funcs, List<String> query) {
        List<String> res = new ArrayList<>();
        for (Func f : funcs) {
            if (f.matches(query)) res.add(f.name);
        }
        return res;
    }

    public static void main(String[] args) {
        List<Func> funcs = List.of(
                new Func("funA", List.of("int","bool")),
                new Func("funB", List.of("int","int")),
                new Func("funC", List.of("int","int"))
        );

        List<String> q1 = List.of("int","int");
        List<String> q2 = List.of("int","bool");
        List<String> q3 = List.of("int");

        System.out.println("Query " + q1 + " => " + findMatches(funcs, q1)); // funB, funC
        System.out.println("Query " + q2 + " => " + findMatches(funcs, q2)); // funA
        System.out.println("Query " + q3 + " => " + findMatches(funcs, q3)); // []
    }
}
