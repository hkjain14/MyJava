package DS.Confluent_Practice_R1;
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
                char value = board[i][j];
                if ( value == '.') {
                    continue;
                }
                int pos = value - '1';
                if(rows[i][pos] == 1) {
                    return false;
                }
                rows[i][pos] = 1;

                if(cols[j][pos] == 1) {
                    return false;
                }
                cols[j][pos] = 1;

                int boxIndex = (i/3)*3 + j/3;

                if(boxes[boxIndex][pos] == 1) {
                    return false;
                }
                boxes[boxIndex][pos] = 1;
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

        System.out.println(isValidSudoku(board1));
        System.out.println(isValidSudoku(board2));
    }
}
