package Interviews.Confluent_Dec25.DSA_Practice;
// leetcode.com/problems/valid-sudoku
// Time: O(N^2), Space: O(N^2)  where N=number of rows = number of cols

public class SudokuValid_N2_SpaceComplexity {
    public static boolean isValidSudoku(char[][] board) {
        int N = 9;
        int [][] rows = new int[N][N];
        int [][] cols = new int[N][N];
        int [][] boxes = new int[N][N];

        for (int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                char charValue = board[i][j];
                if (charValue == '.')
                    continue;

                int intValue = charValue - '1';

                if(intValue<0 || intValue >8) {
                    System.out.println("Invalid number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }

                if(rows[i][intValue] == 1) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                rows[i][intValue] = 1;

                if(cols[j][intValue] == 1) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                cols[j][intValue] = 1;

                int boxIndex = (i/3)*3 + j/3;
                if(boxes[boxIndex][intValue] == 1) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                boxes[boxIndex][intValue] = 1;
            }
        }
        return true;
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

        char [][] board2 =
                        {{'0','3','.','.','7','.','.','.','.'}
                        ,{'6','.','.','1','9','5','.','.','.'}
                        ,{'.','9','8','.','.','.','.','6','.'}
                        ,{'8','.','.','.','6','.','.','.','3'}
                        ,{'4','.','.','8','.','3','.','.','1'}
                        ,{'7','.','.','.','2','.','.','.','6'}
                        ,{'.','6','.','.','.','.','2','8','.'}
                        ,{'.','.','.','4','1','9','.','.','5'}
                        ,{'.','.','.','.','8','.','.','7','9'}};

        char [][] board3 =
                        {{'7','3','.','.','7','.','.','.','.'}
                        ,{'6','.','.','1','9','5','.','.','.'}
                        ,{'.','9','8','.','.','.','.','6','.'}
                        ,{'8','.','.','.','6','.','.','.','3'}
                        ,{'4','.','.','8','.','3','.','.','1'}
                        ,{'7','.','.','.','2','.','.','.','6'}
                        ,{'.','6','.','.','.','.','2','8','.'}
                        ,{'.','.','.','4','1','9','.','.','5'}
                        ,{'.','.','.','.','8','.','.','7','9'}};

        System.out.println(isValidSudoku(board1) + "\n");
        System.out.println(isValidSudoku(board2) + "\n");
        System.out.println(isValidSudoku(board3) + "\n");
    }
}
