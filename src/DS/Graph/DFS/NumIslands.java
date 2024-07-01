package DS.Graph.DFS;

public class NumIslands {
    static int[][] pos = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

    static boolean isValidMove(int a, int b, int x, int y) {
        return a >= 0 && a < x && b >= 0 && b < y;
    }

    static void DFS(int[][] input, boolean[][] visited, int x, int y) {
        visited[x][y] = true;
        for(int i=0;i<pos.length;i++) {
            int newX = x+pos[i][0];
            int newY = y+pos[i][1];
            if(isValidMove(newX,newY,input.length, input[0].length) && input[newX][newY]==1 && !visited[newX][newY]) {
                DFS(input,visited,newX,newY);
            }
        }
    }

    static int getNumIslands(int[][] input) {
        int ans=0;
        int x = input.length, y=input[0].length;
        boolean[][] visited = new boolean[x][y];
        for(int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                if (!visited[i][j] && input[i][j]==1) {
                    DFS(input,visited, i, j);
                    ans++;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[][] input = {
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {0, 0, 1, 0, 1},
                {0, 1, 0, 0, 0},
                {1, 0, 1, 0, 1}
        };
        System.out.println(getNumIslands(input));
    }
}
