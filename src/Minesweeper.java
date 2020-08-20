// 529. 扫雷游戏
//
// 让我们一起来玩扫雷游戏！
//
// 给定一个代表游戏板的二维字符矩阵。 
// 'M' 代表一个未挖出的地雷，'E' 代表一个未挖出的空方块，'B' 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的已挖出的空白方块，数字（'1' 到 '8'）表示有多少地雷与这块已挖出的方块相邻，'X' 则表示一个已挖出的地雷。
//
// 现在给出在所有未挖出的方块中（'M'或者'E'）的下一个点击位置（行和列索引），根据以下规则，返回相应位置被点击后对应的面板：
// 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X'。
// 如果一个没有相邻地雷的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的未挖出方块都应该被递归地揭露。
// 如果一个至少与一个地雷相邻的空方块（'E'）被挖出，修改它为数字（'1'到'8'），表示相邻地雷的数量。
// 如果在此次点击中，若无更多方块可被揭露，则返回面板。


package src;

import java.util.ArrayDeque;
import java.util.Deque;

public class Minesweeper {
    static int m;
    static int n;
    static int[][] nexts = {
            {1, 1},
            {-1, -1},
            {1, -1},
            {-1, 1},
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };

    public static class DFS {
        public char[][] solution(char[][] board, int[] click) {
            int startRow = click[0];
            int startCol = click[1];
            if (board[startRow][startCol] == 'M') {
                board[startRow][startCol] = 'X';
                return board;
            }

            m = board.length;
            n = board[0].length;

            dfs(startRow, startCol, board);
            return board;
        }

        private void dfs(int curRow, int curCol, char[][] board) {
            if (curRow < 0 || curRow >= m || curCol < 0 || curCol >= n) {
                // 越界
                return;
            }
            if (Character.isDigit(board[curRow][curCol])) {
                // 挖到数字，无需处理
                return;
            }
            if (board[curRow][curCol] == 'B') {
                // 挖到空白，无需处理
                return;
            }

            // 当前位置为 'E'

            // 周围地雷数
            int count = minesCount(curRow, curCol, board);
            if (count == 0) {
                board[curRow][curCol] = 'B';
                for (int[] next : nexts) {
                    int r = curRow + next[0], c = curCol + next[1];
                    dfs(r, c, board);
                }
            } else {
                board[curRow][curCol] = (char) ('0' + count);
            }
        }
    }

    public static class BFS {
        public char[][] solution(char[][] board, int[] click) {
            int startRow = click[0];
            int startCol = click[1];
            if (board[startRow][startCol] == 'M') {
                board[startRow][startCol] = 'X';
                return board;
            }

            m = board.length;
            n = board[0].length;

            Deque<int[]> queue = new ArrayDeque<>();
            queue.offer(new int[]{startRow, startCol});
            while (!queue.isEmpty()) {
                int[] t = queue.poll();
                int r = t[0];
                int c = t[1];

                if (r < 0 || r >= m || c < 0 || c >= n) {
                    continue;
                }
                if (Character.isDigit(board[r][c])) {
                    continue;
                }
                if (board[r][c] == 'B') {
                    continue;
                }

                int count = minesCount(r, c, board);
                if (count == 0) {
                    board[r][c] = 'B';
                    for (int[] next : nexts) {
                        int newR = r + next[0], newC = c + next[1];
                        queue.offer(new int[]{newR, newC});
                    }
                } else {
                    board[r][c] = (char) ('0' + count);
                }
            }
            return board;
        }
    }

    private static int minesCount(int curRow, int curCol, char[][] board) {
        int count = 0;
        for (int[] next : nexts) {
            int r = curRow + next[0], c = curCol + next[1];
            if (r >= 0 && r < m && c >= 0 && c < n && (board[r][c] == 'M' || board[r][c] == 'X')) {
                count++;
            }
        }
        return count;
    }
}
