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
                char charValue = board[i][j];
                if(charValue == '.') {
                    continue;
                }
                int intValue = charValue - '1';

                if(intValue<0 || intValue >8) {
                    System.out.println("Invalid number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }

                // Check if in no x, the intValue'th bit is set
                // x & (1<<intValue)
                if((rows[i] & (1 << intValue)) != 0) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                // Set bit to 1
                // x = x | (1<<intValue)
                rows[i] = rows[i] | (1 << intValue);

                if((cols[j] & (1 << intValue)) != 0) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                cols[j] = cols[j] | (1 << intValue);

                int boxIndex = (i/3)*3 + j/3;
                if((boxes[boxIndex] & (1 << intValue)) != 0) {
                    System.out.println("Duplicate number " + (intValue+1) + " in Sudoku at row: " + (i+1) + " and col: " + (j+1));
                    return false;
                }
                boxes[boxIndex] = boxes[boxIndex] | (1 << intValue);
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
                        {{'7','0','.','.','7','.','.','.','.'}
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

        System.out.println(isValidSudoku(board1) + "\n"); // true
        System.out.println(isValidSudoku(board2) + "\n"); // false (0 present)
        System.out.println(isValidSudoku(board3) + "\n"); // false (7 twice in row 1)
    }
}
