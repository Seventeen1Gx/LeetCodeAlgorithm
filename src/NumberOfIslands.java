// 200. 岛屿数量
//
// 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
//
// 岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
//
// 此外，你可以假设该网格的四条边均被水包围。


package src;

import java.util.ArrayDeque;
import java.util.Queue;

public class NumberOfIslands {
    public static class DFS {
        void dfs(char[][] grid, int r, int c) {
            int nr = grid.length;
            int nc = grid[0].length;

            if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
                return;
            }

            // 遍历过的点置为零
            grid[r][c] = '0';

            // 往 4 个方向走
            dfs(grid, r - 1, c);
            dfs(grid, r, c - 1);
            dfs(grid, r + 1, c);
            dfs(grid, r, c + 1);
        }

        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }

            int nr = grid.length;
            int nc = grid[0].length;
            int num_islands = 0;
            for (int r = 0; r < nr; r++) {
                for (int c = 0; c < nc; c++) {
                    if (grid[r][c] == '1') {
                        ++num_islands;
                        dfs(grid, r, c);
                    }
                }
            }
            return num_islands;
        }
    }

    public static class BFS {
        public int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }

            int nr = grid.length;
            int nc = grid[0].length;
            int num_islands = 0;

            for (int r = 0; r < nr; r++) {
                for (int c = 0; c < nc; c++) {
                    if (grid[r][c] == '1') {
                        ++num_islands;
                        grid[r][c] = '0';
                        Queue<Integer> queue = new ArrayDeque<>();
                        queue.offer(r * nc + c);
                        while (!queue.isEmpty()) {
                            int id = queue.poll();
                            int row = id / nc;
                            int col = id % nc;
                            if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                                grid[row - 1][col] = '0';
                                queue.offer((row - 1) * nc + col);
                            }
                            if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                                grid[row][col - 1] = '0';
                                queue.offer(row * nc + col - 1);
                            }
                            if (row + 1 < nr && grid[row + 1][col] == '1') {
                                grid[row + 1][col] = '0';
                                queue.offer((row + 1) * nc + col);
                            }
                            if (col + 1 < nc && grid[row][col + 1] == '1') {
                                grid[row][col + 1] = '0';
                                queue.offer(row * nc + col + 1);
                            }
                        }
                    }
                }
            }
            return num_islands;
        }
    }

    public static class UnionFind {
        // 独立集合个数
        int count;
        // parent[i] 是结点 i 的父结点
        int[] parent;
        // 控制合并时，谁向谁合并
        int[] rank;

        public UnionFind(char[][] grid) {
            count = 0;
            int m = grid.length;
            int n = grid[0].length;
            parent = new int[m * n];
            rank = new int[m * n];
            // 初始化时，每个 '1' 结点各成一个独立集合
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        parent[i * n + j] = i * n + j;
                        ++count;
                    }
                    rank[i * n + j] = 0;
                }
            }
        }

        // 求结点 i 所属集合的根结点
        // parent[i] == i 时，说明 i 就是根结点
        // 否则找它父结点所属集合的根结点
        public int find(int i) {
            if (parent[i] != i) {
                find(parent[i]);
            }
            return parent[i];
        }

        // 将 x 和 y 所属的集合合并
        public void union(int x, int y) {
            int rootx = find(x);
            int rooty = find(y);
            // 所属不同集合才合并
            if (rootx != rooty) {
                // 控制小集合往大集合上合并
                if (rank[rootx] > rank[rooty]) {
                    parent[rooty] = rootx;
                } else if (rank[rootx] < rank[rooty]) {
                    parent[rootx] = rooty;
                } else {
                    // 相等时，后者往前者上合并
                    parent[rooty] = rootx;
                    rank[rootx] += 1;
                }
                // 独立集合数减一
                --count;
            }

        }

        public int getCount() {
            return count;
        }

        public static int numIslands(char[][] grid) {
            if (grid == null || grid.length == 0) {
                return 0;
            }
            int nr = grid.length;
            int nc = grid[0].length;
            int num_islands = 0;
            UnionFind uf = new UnionFind(grid);
            for (int r = 0; r < nr; r++) {
                for (int c = 0; c < nc; c++) {
                    if (grid[r][c] == '1') {
                        grid[r][c] = '0';
                        if (r - 1 >= 0 && grid[r - 1][c] == '1') {
                            uf.union(r * nc + c, (r - 1) * nc + c);
                        }
                        if (r + 1 < nr && grid[r + 1][c] == '1') {
                            uf.union(r * nc + c, (r + 1) * nc + c);
                        }
                        if (c - 1 >= 0 && grid[r][c - 1] == '1') {
                            uf.union(r * nc + c, r * nc + c - 1);
                        }
                        if (c + 1 < nc && grid[r][c + 1] == '1') {
                            uf.union(r * nc + c, r * nc + c + 1);
                        }
                    }
                }
            }
            return uf.getCount();
        }
    }
}
