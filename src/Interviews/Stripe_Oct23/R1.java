package Interviews.Stripe_Oct23;
import java.util.*;
public class R1 {
    static int totalVertices = 100;
    static Map<String,Integer> stringMap;
    static LinkedList<Edge>[] adj;

    static class Edge {
        String source;
        String destination;
        double weight;
        Edge(String source, String destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {

        Graph() {
            stringMap = new HashMap<>();
            adj = new LinkedList[totalVertices];
            for(int i=0;i<totalVertices;i++) {
                adj[i] = new LinkedList<>();
            }
        }

        void addEdge(String source, String destination, double weight) {
            Edge e = new Edge(source, destination, weight);
            int listIndex;
            if(stringMap.containsKey(source)) {
                listIndex = stringMap.get(source);
            } else {
                listIndex = stringMap.size();
                stringMap.put(source,listIndex);
            }
            adj[listIndex].add(e);
        }
        /*
        void printGraph() {
            for(LinkedList<Edge> el: adj) {
                for(int i=0;i<el.size();i++) {
                    System.out.println(el.get(i).source + "----" + el.get(i).weight + "----" + el.get(i).destination);
                }
            }
        }
        */
    }

    static void fill(String input, Graph g) {
        String[] split = input.split(",");
        for(String el:split) {
            String[] innerSplit = el.split(":");
            String c1 = innerSplit[0];
            String c2 = innerSplit[1];
            Double val = Double.parseDouble(innerSplit[2]);
            g.addEdge(c1,c2,val);
            g.addEdge(c2,c1,1.0/val);
        }
    }

    static double dfsUtil(String source, String destination, boolean[] visited) {
        int index = stringMap.get(source);
        visited[index] = true;

        for(Edge e : adj[index]) {
            if(e.destination.equals(destination))
                return e.weight;
        }
        for(Edge e : adj[index]) {
            int destIndex = stringMap.get(e.destination);
            if(!visited[destIndex])
                return e.weight * dfsUtil(e.destination, destination, visited);
        }
        return -1;
    }

    static double findExchange(String c1, String c2) {
        boolean[] visited = new boolean[totalVertices];
        return dfsUtil(c1,c2,visited);
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        String input = "AUD:USD:0.75,USD:CAD:1.26,USD:INR:82.56,GBP:USD:1.21,CAD:CNY:5.3,CAD:JPY:108.69";
        fill(input, g);
//        g.printGraph();
        System.out.println(findExchange("CAD","AUD"));
    }
}
