package Interviews.Confluent_Dec25.DSA_Practice;
// leetcode.com/problems/valid-sudoku
// Time: O(N^2), Space: O(N)  where N=number of rows = number of cols

public class SudokuValid_N_SpaceComplexity {
    public static boolean isValidSudoku(char[][] board) {
        int N = 9;
        // Store 9 bit integer at each position of the array ->
        // the bit's x position's value corresponds to whether x value was set as 1 already for that index.
        int[] rows = new int[N];
        int[] cols = new int[N];
        int[] boxes = new int[N];

        for(int i=0;i<N;i++) {
            for (int j=0;j<N;j++) {
                char value = board[i][j];
                if(value == '.') {
                    continue;
                }
                int pos = value - '1';

                // Check if in no x, the pos'th bit is set
                // x & (1<<pos)
                if((rows[i] & (1 << pos)) != 0) {
                    return false;
                }
                // Set bit to 1
                // x = x | (1<<pos)
                rows[i] = rows[i] | (1 << pos);

                if((cols[j] & (1 << pos)) != 0) {
                    return false;
                }
                cols[j] = cols[j] | (1 << pos);

                int boxIndex = (i/3)*3 + j/3;
                if((boxes[boxIndex] & (1 << pos)) != 0) {
                    return false;
                }
                boxes[boxIndex] = boxes[boxIndex] | (1 << pos);
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
                        {{'8','3','.','.','7','.','.','.','.'}
                        ,{'6','.','.','1','9','5','.','.','.'}
                        ,{'.','9','8','.','.','.','.','6','.'}
                        ,{'8','.','.','.','6','.','.','.','3'}
                        ,{'4','.','.','8','.','3','.','.','1'}
                        ,{'7','.','.','.','2','.','.','.','6'}
                        ,{'.','6','.','.','.','.','2','8','.'}
                        ,{'.','.','.','4','1','9','.','.','5'}
                        ,{'.','.','.','.','8','.','.','7','9'}};

        System.out.println(isValidSudoku(board1)); // true
        System.out.println(isValidSudoku(board2)); // false (8 twice in col 1)
    }
}
