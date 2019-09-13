//36. 有效的数独
//
//判断一个 9x9 的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
//数字 1-9 在每一行只能出现一次。
//数字 1-9 在每一列只能出现一次。
//数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
//
//说明
//一个有效的数独（部分已被填充）不一定是可解的。
//只需要根据以上规则，验证已经填入的数字是否有效即可。
//给定数独序列只包含数字 1-9 和字符 '.' 。
//给定数独永远是 9x9 形式的。


package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ValidSudoku {
    //按给出的条件检查即可，我们利用集合判断
    public boolean solution1(char[][] board) {
        boolean flag = true;

        //行
        for (int i = 0; i < 9 && flag; i++) {
            flag = checkRow(board, i);
        }

        //列
        for (int i = 0; i < 9 && flag; i++) {
            flag = checkColumn(board, i);
        }

        //3*3
        for (int i = 0; i < 3 && flag; i++) {
            for (int j = 0; j < 3 && flag; j++) {
                flag = checkBox(board, i * 3, j *3);
            }
        }

        return flag;
    }

    //检查行
    public boolean checkRow(char[][] board, int row) {
        Set<Character> check = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            if (board[row][i] == '.')
                continue;
            if (check.contains(board[row][i]))
                return false;
            else
                check.add(board[row][i]);
        }
        return true;
    }

    //检查列
    public boolean checkColumn(char[][] board, int col) {
        Set<Character> check = new HashSet<>();

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == '.')
                continue;
            if (check.contains(board[i][col]))
                return false;
            else
                check.add(board[i][col]);
        }
        return true;
    }

    //检查3*3格子，传入的是3*3格子第一个元素的坐标
    public boolean checkBox(char[][] board, int startRow, int startCol) {
        Set<Character> check = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == '.')
                    continue;
                if (check.contains(board[startRow + i][startCol + j]))
                    return false;
                else
                    check.add(board[startRow + i][startCol + j]);
            }
        }
        return true;
    }

    //看了官方题解，一次迭代
    //遍历到每个元素，注意他在其所在的行、列、3*3小宫格中是否已经出现
    public boolean solution2(char[][] board) {
        //设置来统计元素是否出现的Map
        HashSet<Character>[] row = new HashSet[9];
        HashSet<Character>[] col = new HashSet[9];
        HashSet<Character>[] box = new HashSet[9];
        //初始化
        for (int i = 0; i < 9; i++) {
            row[i] = new HashSet<>();
            col[i] = new HashSet<>();
            box[i] = new HashSet<>();
        }
        //一次遍历
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    //判断行
                    if (row[i].contains(num))
                        return false;
                    else
                        row[i].add(num);
                    //判断列
                    if (col[j].contains(num))
                        return false;
                    else
                        col[j].add(num);
                    //判断3*3小宫格
                    int boxIndex = i / 3 * 3 + j / 3;
                    if (box[boxIndex].contains(num))
                        return false;
                    else
                        box[boxIndex].add(num);
                }
            }
        }
        return true;
    }

    //官方代码
    public boolean solution2_official(char[][] board) {
        // init data
        HashMap<Integer, Integer>[] rows = new HashMap[9];
        HashMap<Integer, Integer>[] columns = new HashMap[9];
        HashMap<Integer, Integer>[] boxes = new HashMap[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<Integer, Integer>();
            columns[i] = new HashMap<Integer, Integer>();
            boxes[i] = new HashMap<Integer, Integer>();
        }

        // validate a board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num != '.') {
                    int n = (int) num;
                    int box_index = (i / 3) * 3 + j / 3;

                    // keep the current cell value
                    rows[i].put(n, rows[i].getOrDefault(n, 0) + 1);
                    columns[j].put(n, columns[j].getOrDefault(n, 0) + 1);
                    boxes[box_index].put(n, boxes[box_index].getOrDefault(n, 0) + 1);

                    // check if this value has been already seen before
                    if (rows[i].get(n) > 1 || columns[j].get(n) > 1 || boxes[box_index].get(n) > 1)
                        return false;
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        ValidSudoku v = new ValidSudoku();

        /*char[][] board = new char[][]
                {
                        {'.', '.', '.', '.', '5', '.', '.', '1', '.'},
                        {'.', '4', '.', '3', '.', '.', '.', '.', '.'},
                        {'.', '.', '.', '.', '.', '3', '.', '.', '1'},
                        {'8', '.', '.', '.', '.', '.', '.', '2', '.'},
                        {'.', '.', '2', '.', '7', '.', '.', '.', '.'},
                        {'.', '1', '5', '.', '.', '.', '.', '.', '.'},
                        {'.', '.', '.', '.', '.', '2', '.', '.', '.'},
                        {'.', '2', '.', '9', '.', '.', '.', '.', '.'},
                        {'.', '.', '4', '.', '.', '.', '.', '.', '.'}
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
        v.solution2_official(board);
    }
}
