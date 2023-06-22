package DS.Graph.DFS;

import java.util.LinkedList;

public class Graph_DFS {
    int Vertices;
    static LinkedList<Integer>[] adj;

    Graph_DFS(int v) {
        Vertices = v;
        adj=new LinkedList[v];
        for(int i=0;i<v;i++) {
            adj[i] = new LinkedList<>();
        }
    }

    static void addEdge(int start, int end) {
        adj[start].add(end);
    }

    void DFS_Util(int vertex, boolean[] visited) {
        visited[vertex] = true;
        System.out.println(vertex);
        for (int nextVertex : adj[vertex]) {
            if (!visited[nextVertex])
                DFS_Util(nextVertex, visited);
        }
    }

    void DFS(int start) {
        boolean[] visited = new boolean[Vertices];
        DFS_Util(start, visited);
    }


    public static void main(String[] args) {
        Graph_DFS g = new Graph_DFS(4);
        g.addEdge(1,2);
        g.addEdge(2,0);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(2,3);
        g.addEdge(3,3);
        g.DFS(2);
    }
}
