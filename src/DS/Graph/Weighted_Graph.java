package DS.Graph;

import java.util.*;

public class Weighted_Graph {
    static int totalVertices = 100;

    static class Edge {
        String source;
        String destination;
        int weight;
        Edge(String source, String destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }


    static class Graph {
        LinkedList<Edge>[] adj;
        Map<String,Integer> stringMap;

        Graph() {
            stringMap = new HashMap<>();
            adj = new LinkedList[totalVertices];
            for(int i=0;i<totalVertices;i++) {
                adj[i] = new LinkedList<>();
            }
        }

        void addEdge(String source, String destination, int weight) {
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

        void printGraph() {
            for(LinkedList<Edge> el: adj) {
                for(int i=0;i<el.size();i++) {
                    System.out.println(el.get(i).source + "----" + el.get(i).weight + "----" + el.get(i).destination);
                }
            }
        }
    }



    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.addEdge("Delhi","Mumbai",1500);
        graph.addEdge("Delhi","Lucknow",500);
        graph.addEdge("Chandigarh","Dharamsala",200);
        graph.addEdge("Mumbai","Chennai",2000);
        graph.addEdge("Delhi","Chennai",3000);

        graph.printGraph();
    }
}
