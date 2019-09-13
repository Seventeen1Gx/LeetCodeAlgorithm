//37. 解数独
//
//编写一个程序，通过已填充的空格来解决数独问题。
//
//一个数独的解法需遵循如下规则：
//数字 1-9 在每一行只能出现一次。
//数字 1-9 在每一列只能出现一次。
//数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
//空白格用 '.' 表示。
//
//说明
//给定的数独序列只包含数字 1-9 和字符 '.' 。
//你可以假设给定的数独只有唯一解。
//给定数独永远是 9x9 形式的。


package src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SudokuSolver {
    List<int[]> nQueenAnswer = new ArrayList<>();

    //启发--n皇后(回溯算法，多种摆法)
    //n*n的棋盘，每行放置一个皇后，如何放，让n位皇后，不同行，不同列，且不同斜线
    //q[i]表示第i行皇后摆在q[i]列上
    //k表示已经放好第k行，开始时k=0
    //对于nQueen表示：解决已放好k行的n皇后问题(去放剩下n-k行的皇后而不是理解为去放下一行的皇后)
    public void nQueen(int[] q, int n, int k) {
        if (k == n) {
            //全部放好，又是老生常谈
            //列表中添加引用时，注意这个引用是否在后续有变化，有则列表中该引用也一同改变了，所以要加上下面这一行(qq是新的不变引用)
            int[] qq = new int[n];
            for (int i = 0; i < n; i++) {
                qq[i] = q[i];
            }
            nQueenAnswer.add(qq);
            return;
        }

        //已放好k行，现处理k+1行
        //遍历该行，每个位置都可以放皇后，放了检查有效性，有效则去放下一行，无效则不放
        for (int i = 0; i < n; i++) {
            if (isValid(q, k, i)) {
                //该位置能放，则放一个皇后
                q[k] = i;
                //去放剩下行的皇后
                nQueen(q, n, k + 1);
                //当上面这句退出，就说明[k,i]位置放皇后之后的可能已经全被考虑
                //之后考虑[k,i+1]放皇后(回溯到现在考虑的k行)
                //接下来，将皇后放到k+1行的下一列
                //之前q中的数据会被覆盖(但有效的方法已经被保存了，所以不用担心)
            }
        }
    }

    //判断棋盘第row行第col列是否可以摆放皇后
    public boolean isValid(int[] q, int row, int col) {
        //每次一行摆放一个，行的冲突不用检查

        //检查列
        for (int i = 0; i < row; i++) {
            if (q[i] == col)
                //当前位置之前行的该列已经有皇后
                return false;
        }

        //检查对角线
        for (int i = 0; i < row; i++) {
            if (Math.abs(q[i] - col) == row - i)
                //当前位置之前行的每个皇后位置跟现在要放皇后的位置组成一个等腰直角三角形
                return false;
        }

        return true;
    }

    //---------------------------------------------------------------------

    //记录当前棋盘摆放情况
    HashSet<Integer>[] rowTag;
    HashSet<Integer>[] colTag;
    HashSet<Integer>[] boxTag;
    //标记是否找到了解
    boolean flag = false;

    //采用深度搜索，一个个放，
    public void solution(char[][] board) {
        //初始化
        rowTag = new HashSet[9];
        colTag = new HashSet[9];
        boxTag = new HashSet[9];
        for (int i = 0; i < 9; i++) {
            rowTag[i] = new HashSet();
            colTag[i] = new HashSet();
            boxTag[i] = new HashSet();
        }
        //棋盘上已填写的情况
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int n = board[i][j] - '0';
                    rowTag[i].add(n);
                    colTag[j].add(n);
                    boxTag[i / 3 * 3 + j / 3].add(n);
                }
            }
        }
        //深度优先搜索
        dfs(board, 0, 0);
    }

    //处理棋盘上第row行第col列位置
    public void dfs(char[][] board, int row, int col) {
        if (flag)
            //找到了就不用做之后的事的
            return;

        if (row == 6)
            System.out.println();

        if (row == 9) {
            //全部处理好，打印棋盘
            print(board);
            flag = true;
        } else if (board[row][col] != '.') {
                //已经放了，放下一个位置
                if (col == 8) dfs(board, row + 1, 0);
                else dfs(board, row, col + 1);
        } else {
            //1-9依次尝试放在位置row，col
            for (int i = 1; i <= 9 && !flag; i++) {
                if (check(row, col, i)) {
                    //在这个位置放上数字
                    placeNum(board, row, col, i);
                    //再解决之后的事
                    if (col == 8) dfs(board, row + 1, 0);
                    else dfs(board, row, col + 1);
                    //回溯到这里，需要做点工作，更新Tag
                    rowTag[row].remove(i);
                    colTag[col].remove(i);
                    boxTag[row / 3 * 3 + col / 3].remove(i);
                    //要改成'.'，不然回溯后，再看这个位置发现已经放好东西了
                    //还要注意，当我们的解已经得到，board就不能变了
                    if (!flag) board[row][col] = '.';
                }
            }
        }
    }

    //检查row,col位置放置数字n的有效性
    public boolean check(int row, int col, int n) {
        if (rowTag[row].contains(n) || colTag[col].contains(n) || boxTag[row / 3 * 3 + col / 3].contains(n))
            return false;
        return true;
    }

    //在row,col位置放置数字n
    public void placeNum(char[][] board, int row, int col, int n) {
        //放数字
        char c = (char) ('0' + n);
        board[row][col] = c;
        //更新约束条件
        rowTag[row].add(n);
        colTag[col].add(n);
        boxTag[row / 3 * 3 + col / 3].add(n);
    }

    public void print(char[][] board) {
        System.out.print("------------------\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    //---------------------------------------------------------------------

    //官方代码
    // box size
    int n = 3;
    // row size
    int N = n * n;

    int [][] rows = new int[N][N + 1];
    int [][] columns = new int[N][N + 1];
    int [][] boxes = new int[N][N + 1];

    char[][] board;

    boolean sudokuSolved = false;

    public boolean couldPlace(int d, int row, int col) {
    /*
    Check if one could place a number d in (row, col) cell
    */
        int idx = (row / n ) * n + col / n;
        return rows[row][d] + columns[col][d] + boxes[idx][d] == 0;
    }

    public void placeNumber(int d, int row, int col) {
    /*
    Place a number d in (row, col) cell
    */
        int idx = (row / n ) * n + col / n;

        rows[row][d]++;
        columns[col][d]++;
        boxes[idx][d]++;
        board[row][col] = (char)(d + '0');
    }

    public void removeNumber(int d, int row, int col) {
    /*
    Remove a number which didn't lead to a solution
    */
        int idx = (row / n ) * n + col / n;
        rows[row][d]--;
        columns[col][d]--;
        boxes[idx][d]--;
        board[row][col] = '.';
    }

    public void placeNextNumbers(int row, int col) {
    /*
    Call backtrack function in recursion
    to continue to place numbers
    till the moment we have a solution
    */
        // if we're in the last cell
        // that means we have the solution
        if ((col == N - 1) && (row == N - 1)) {
            sudokuSolved = true;
        }
        // if not yet
        else {
            // if we're in the end of the row
            // go to the next row
            if (col == N - 1) backtrack(row + 1, 0);
                // go to the next column
            else backtrack(row, col + 1);
        }
    }

    public void backtrack(int row, int col) {
    /*
    Backtracking
    */
        // if the cell is empty
        if (board[row][col] == '.') {
            // iterate over all numbers from 1 to 9
            for (int d = 1; d < 10; d++) {
                if (couldPlace(d, row, col)) {
                    placeNumber(d, row, col);
                    placeNextNumbers(row, col);
                    // if sudoku is solved, there is no need to backtrack
                    // since the single unique solution is promised
                    if (!sudokuSolved) removeNumber(d, row, col);
                }
            }
        }
        else placeNextNumbers(row, col);
    }

    public void solveSudoku(char[][] board) {
        this.board = board;

        // init rows, columns and boxes
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int d = Character.getNumericValue(num);
                    placeNumber(d, i, j);
                }
            }
        }
        backtrack(0, 0);
    }

    public static void main(String[] args) {
        SudokuSolver s = new SudokuSolver();

        //测试n皇后
        //s.nQueen(new int[4], 4, 0);
        //for (int[] a : s.nQueenAnswer) {
        //    for (int i = 0; i < a.length; i++) {
        //        System.out.print(a[i]);
        //    }
        //    System.out.println();
        //}

        /*char[][] board = new char[][]
                {
                        {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                        {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                        {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                        {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                        {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                        {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                        {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                        {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                };*/
        /*char[][] board = new char[][]
                {
                        {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                        {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                        {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                        {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                        {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                        {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                        {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                };*/
        /*char[][] board = new char[][]
                {
                        {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                        {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                        {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                        {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                        {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                        {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                };*/
        char[][] board = new char[][]
                {
                        {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                        {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                        {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                        {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                        {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                        {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                        {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                        {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                        {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
                };
        s.solution(board);
    }
}
