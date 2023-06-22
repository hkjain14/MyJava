package DS.Graph.DFS;

public class WordSearch {
    static int[][] pos = {{0,-1},{0,1},{1,0},{-1,0}};
    static boolean ans = false;

    static boolean isValid(int newX,int newY,char[][] board) {
        int n = board.length;
        int m = board[0].length;
        return newX>=0 && newX<n && newY>=0 && newY<m;
    }

    static void DFS(char[][] board, String word,int x,int y,boolean [][]visited, int stringPos) {
        visited[x][y]=true;
        for(int i=0;i<pos.length;i++) {
            if(stringPos==word.length()) {
                ans = true;
                return;
            }
            int newX = x+pos[i][0];
            int newY = y+pos[i][1];
            if(isValid(newX,newY,board) && word.charAt(stringPos)==board[newX][newY] && !visited[newX][newY]) {
                DFS(board,word,newX,newY,visited, stringPos+1);
                visited[newX][newY] = false;
            }
        }
    }

    static boolean checkWordSearch(char[][] board, String word) {
        int x = board.length;
        int y = board[0].length;
        boolean [][]visited = new boolean[x][y];

        for (int i=0;i<x;i++) {
            for(int j=0;j<y;j++) {
                if(word.charAt(0) == board[i][j]) {
                    int stringPos = 1;
                    DFS(board,word,i,j, visited, stringPos);
                    if(ans) {
                        break;
                    }
                    visited = new boolean[x][y];
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'A','B','C','E'},
                {'S','F','E','S'},
                {'A','D','E','E'}
        };
        String word = "ABCESEEEFS";
        System.out.println(checkWordSearch(board,word));
    }
}
