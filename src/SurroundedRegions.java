// 130. 被围绕的区域
//
// 给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
//
// 找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
//
// 解释:
// 被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。
// 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。
// 如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。


package src;

import java.util.ArrayDeque;
import java.util.Queue;

public class SurroundedRegions {
    // 从边界的 'O' 出发，标记所有可到达的 'O'
    // 没有被标记的改为 'X'

    public static class DFS {
        int nr;
        int nc;
        boolean[][] tag;

        public void solution(char[][] board) {
            if (board == null || board.length == 0) {
                return;
            }

            this.nr = board.length;
            this.nc = board[0].length;
            tag = new boolean[nr][nc];

            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    boolean isBoundary = i == 0 || i == nr - 1 || j == 0 || j == nc - 1;
                    if (isBoundary && board[i][j] == 'O') {
                        dfs(i, j, board);
                    }
                }
            }
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    if (board[i][j] == 'O' && !tag[i][j]) {
                        board[i][j] = 'X';
                    }
                }
            }
        }


        private void dfs(int r, int c, char[][] board) {
            if (r < 0 || r >= nr || c < 0 || c >= nc || board[r][c] == 'X' || tag[r][c]) {
                return;
            }

            tag[r][c] = true;
            dfs(r - 1, c, board);
            dfs(r + 1, c, board);
            dfs(r, c - 1, board);
            dfs(r, c + 1, board);
        }

        public static void main(String[] args) {
            /*new DFS().solution(new char[][]{
                    {'X','X','X','X'},
                    {'X','O','O','X'},
                    {'X','X','O','X'},
                    {'X','O','X','X'},
            });*/
            new DFS().solution(new char[][]{
                    {'O','X','O','O', 'O', 'X'},
                    {'O','O','X','X', 'X', 'O'},
                    {'O','O','O','O', 'X', 'X'},
                    {'X','X','O','O', 'X', 'O'},
                    {'O','O','X','X', 'X', 'X'},
            });
        }
    }

    public static class BFS {
        public void solution(char[][] board) {
            if (board == null || board.length == 0) {
                return;
            }

            int nr = board.length;
            int nc = board[0].length;
            boolean[][] tag = new boolean[nr][nc];

            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    boolean isBoundary = i == 0 || i == nr - 1 || j == 0 || j == nc - 1;
                    if (isBoundary && board[i][j] == 'O' && !tag[i][j]) {
                        Queue<int[]> queue = new ArrayDeque<>();
                        queue.offer(new int[]{i, j});
                        tag[i][j] = true;
                        while (!queue.isEmpty()) {
                            int[] temp = queue.poll();
                            int row = temp[0];
                            int col = temp[1];
                            if (row - 1 >= 0 && board[row - 1][col] == 'O' && !tag[row - 1][col]) {
                                tag[row - 1][col] = true;
                                queue.offer(new int[]{row - 1, col});
                            }
                            if (row + 1 < nr && board[row + 1][col] == 'O' && !tag[row + 1][col]) {
                                tag[row + 1][col] = true;
                                queue.offer(new int[]{row + 1, col});
                            }
                            if (col - 1 >= 0 && board[row][col - 1] == 'O' && !tag[row][col - 1]) {
                                tag[row][col - 1] = true;
                                queue.offer(new int[]{row, col - 1});
                            }
                            if (col + 1 < nc && board[row][col + 1] == 'O' && !tag[row][col + 1]) {
                                tag[row][col + 1] = true;
                                queue.offer(new int[]{row, col + 1});
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < nr; i++) {
                for (int j = 0; j < nc; j++) {
                    if (board[i][j] == 'O' && !tag[i][j]) {
                        board[i][j] = 'X';
                    }
                }
            }
        }
    }
}
