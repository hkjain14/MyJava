package Interviews.Confluent_Dec25.DSA_Practice;
import java.util.*;

/**
 Time complexity (of 1 query): O(k*m)
 k = no of funcs
 m = no of parameters in a query

 Space complexity: O(x)
 x = totoal number of params in all funcs
 */

public class FuncTypeVariadicSoln {

    static class Func {
        final String name;
        final List<String> fixedParams; // parameters before varargs
        final String varArgType;        // null if not variadic

        /**
         * Constructor for both variadic & non-variadic functions.
         *
         * @param name        function name
         * @param params      list of parameter types (final element = vararg type if variadic)
         * @param isVariadic  whether this is a variadic function
         */
        Func(String name, List<String> params, boolean isVariadic) {
            this.name = name;

            if (!isVariadic) {
                // Store all params as fixed
                this.fixedParams = List.copyOf(params);
                this.varArgType = null;
            } else {
                if (params.isEmpty()) {
                    // Edge-case: variadic but no params given
                    this.fixedParams = List.of();
                    this.varArgType = null;
                } else if (params.size() == 1) {
                    // Only vararg type
                    this.fixedParams = List.of();
                    this.varArgType = params.get(0);
                } else {
                    // Split last element as vararg type
                    this.fixedParams = List.copyOf(params.subList(0, params.size() - 1));
                    this.varArgType = params.get(params.size() - 1);
                }
            }
        }

        boolean matches(List<String> query) {
            if (query == null) query = Collections.emptyList();
            int qLen = query.size();
            int fixedCount = fixedParams.size();

            if (varArgType == null) {
                // Non-variadic: exact match required
                if (qLen != fixedCount) return false;
                for (int i = 0; i < fixedCount; i++) {
                    if (!fixedParams.get(i).equals(query.get(i))) return false;
                }
                return true;
            }

            // Variadic case:
            if (qLen < fixedCount) return false;

            // Match fixed params
            for (int i = 0; i < fixedCount; i++) {
                if (!fixedParams.get(i).equals(query.get(i))) return false;
            }

            // Match varargs (zero or more allowed)
            for (int i = fixedCount; i < qLen; i++) {
                if (!varArgType.equals(query.get(i))) return false;
            }

            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(name).append(": (");
            for (int i = 0; i < fixedParams.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(fixedParams.get(i));
            }
            if (varArgType != null) {
                if (!fixedParams.isEmpty()) sb.append(", ");
                sb.append(varArgType).append("...");
            }
            sb.append(")");
            return sb.toString();
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
        // Your example functions:
        Func funA = new Func("funA", List.of("int", "bool"), true);
        Func funB = new Func("funB", List.of("int", "int", "int"), true);
        Func funC = new Func("funC", List.of("int", "int"), false);

        List<Func> funcs = List.of(funA, funB, funC);

        List<String> q1 = List.of("int", "int");
        List<String> q2 = List.of("int", "int", "int", "int");
        List<String> q3 = List.of("int");
        List<String> q4 = List.of("int", "bool", "bool");

        System.out.println("Stored functions:");
        funcs.forEach(f -> System.out.println("  " + f));

        System.out.println("\nQuery " + q1 + " => " + findMatches(funcs, q1)); // funB, funC
        System.out.println("Query " + q2 + " => " + findMatches(funcs, q2)); // funB
        System.out.println("Query " + q3 + " => " + findMatches(funcs, q3)); // funA
        System.out.println("Query " + q4 + " => " + findMatches(funcs, q4)); // funA
    }
}
