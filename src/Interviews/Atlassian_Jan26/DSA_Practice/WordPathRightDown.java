package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

// Find the path of the given word in a n*m grid. Can start from any where and only move right/down

/*
DFS & BACKTRACK

Time: O(N * L) in practice with memo pruning
Worst-case upper bound still large, but memo usually collapses repeated work heavily.

Space: O(N * L)

N = n*m, word length L
 */

public class WordPathRightDown {
    static class Cell {
        int r, c;
        Cell(int r, int c) { this.r = r; this.c = c; }
        @Override public String toString() { return "(" + r + "," + c + ")"; }
    }

    public static List<Cell> findWordPath(char[][] grid, String word) {
        int n = grid.length, m = grid[0].length;
        int L = word.length();

        // failed[r][c][idx] = true means "we already tried and failed from here"
        boolean[][][] failed = new boolean[n][m][L];

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                List<Cell> path = new ArrayList<>();
                if (dfs(grid, word, r, c, 0, path, failed)) {
                    return path;
                }
            }
        }
        return Collections.emptyList();
    }

    private static boolean dfs(char[][] grid, String word,
                               int r, int c, int idx,
                               List<Cell> path,
                               boolean[][][] failed) {
        int n = grid.length, m = grid[0].length;

        if (r < 0 || r >= n || c < 0 || c >= m) return false;
        if (failed[r][c][idx]) return false;

        if (grid[r][c] != word.charAt(idx)) {
            failed[r][c][idx] = true;
            return false;
        }

        path.add(new Cell(r, c));

        // matched last character
        if (idx == word.length() - 1) return true;

        // Try move Right
        if (dfs(grid, word, r, c + 1, idx + 1, path, failed)) return true;

        // Try move Down
        if (dfs(grid, word, r + 1, c, idx + 1, path, failed)) return true;

        // Backtrack
        path.remove(path.size() - 1);
        failed[r][c][idx] = true;
        return false;
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'A','G','K'},
                {'B','X','L'},
                {'C','I','M'}
        };
        System.out.println(findWordPath(grid, "GXLM")); // [(0,1), (1,1), (1,2), (2,2)]
    }
}
