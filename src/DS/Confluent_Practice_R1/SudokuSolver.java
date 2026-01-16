package DS.Confluent_Practice_R1;

/*
https://leetcode.com/problems/sudoku-solver

Time complexity: O((N!)^N) = (9!)^9
    10^27 faster than brute force = 9^81
Space complexity: O(N^2) = 81
 */

public class SudokuSolver {
    int n = 3, N = n*n;
    char[][] board;
    int [][] rows = new int[N][N+1];
    int [][] cols = new int[N][N+1];
    int [][] boxes = new int[N][N+1];
    boolean isSolved = false;

    boolean couldPlace(int val, int row, int col) {
        int boxIdx = (row/3)*3 + col/3;
        return rows[row][val] + cols[col][val] + boxes[boxIdx][val] == 0;
    }

    void addNumber(int value, int row, int col) {
        rows[row][value] ++;
        cols[col][value] ++;
        int boxIdx = (row/3)*3 + col/3;
        boxes[boxIdx][value] ++;
        board[row][col] = (char) (value + '0');
    }

    void removeNumber(int value, int row, int col) {
        rows[row][value] --;
        cols[col][value] --;
        int boxIdx = (row/3)*3 + col/3;
        boxes[boxIdx][value] --;
        board[row][col] = '.';
    }

    void placeNextNumbers(int row, int col) {
        if (row == N-1 && col == N-1) {
            isSolved = true;
            return;
        }
        if(col == N-1) {
            backtrack(row+1, 0);
        } else {
            backtrack(row, col + 1);
        }
    }

    void backtrack(int row, int col) {
        if (board[row][col] == '.') {
            for(int value = 1; value<=N;value++) {
                if (couldPlace(value,row,col)) {
                    addNumber(value,row,col);
                    placeNextNumbers(row,col);

                    if(!isSolved) {
                        removeNumber(value,row,col);
                    }
                }
            }
        } else {
            placeNextNumbers(row, col);
        }
    }

    void solveSudoku(char[][] board) {
        this.board = board;
        for(int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                if(board[i][j] != '.') {
                    int value = board[i][j] - '0';
                    addNumber(value,i,j);
                }
            }
        }
        backtrack(0,0);
        printBoard();
    }

    void printBoard() {
        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                System.out.printf((board[i][j] - '0') + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {
        char [][] board1 =
                        {{'5','3','.','.','7','.','.','.','.'}
                        ,{'6','.','.','1','9','5','.','.','.'}
                        ,{'.','9','8','.','.','.','.','6','.'}
                        ,{'8','.','.','.','6','.','.','.','3'}
                        ,{'4','.','.','8','.','3','.','.','1'}
                        ,{'7','.','.','.','2','.','.','.','6'}
                        ,{'.','6','.','.','.','.','2','8','.'}
                        ,{'.','.','.','4','1','9','.','.','5'}
                        ,{'.','.','.','.','8','.','.','7','9'}};
        SudokuSolver sudokuSolver = new SudokuSolver();
        sudokuSolver.solveSudoku(board1);
    }
}
