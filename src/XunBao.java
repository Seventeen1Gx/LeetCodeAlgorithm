// LCP. 13 寻宝
//
// 我们得到了一副藏宝图，藏宝图显示，在一个迷宫中存在着未被世人发现的宝藏。
//
// 迷宫是一个二维矩阵，用一个字符串数组表示。它标识了唯一的入口（用 'S' 表示），和唯一的宝藏地点（用 'T' 表示）。但是，宝藏被一些隐蔽的机关保护了起来。在地图上有若干个机关点（用 'M' 表示），只有所有机关均被触发，才可以拿到宝藏。
//
// 要保持机关的触发，需要把一个重石放在上面。迷宫中有若干个石堆（用 'O' 表示），每个石堆都有无限个足够触发机关的重石。但是由于石头太重，我们一次只能搬一个石头到指定地点。
//
// 迷宫中同样有一些墙壁（用 '#' 表示），我们不能走入墙壁。剩余的都是可随意通行的点（用 '.' 表示）。石堆、机关、起点和终点（无论是否能拿到宝藏）也是可以通行的。
//
// 我们每步可以选择向上/向下/向左/向右移动一格，并且不能移出迷宫。搬起石头和放下石头不算步数。那么，从起点开始，我们最少需要多少步才能最后拿到宝藏呢？如果无法拿到宝藏，返回 -1 。
//
// 限制：
// 1 <= maze.length <= 100
// 1 <= maze[i].length <= 100
// maze[i].length == maze[j].length
// S 和 T 有且只有一个
// 0 <= M的数量 <= 16
// 0 <= O的数量 <= 40，题目保证当迷宫中存在 M 时，一定存在至少一个 O 。


package src;

import java.util.*;

public class XunBao {
    // 我们将过程分为 开始→[石堆→触发机关→...→石堆→触发机关]→终点
    // 即我们只有 4 种走法：
    // 从 S 走到 O
    // 从 O 走到 M
    // 从 M 走到 O
    // 从 M 走到 T
    //
    // 关键点：机关的触发顺序是什么样的？
    // 1. 最开始我们 S→O→M，对于特定的 M，我们枚举所有的 O，就能得到 S→O→M 的最短距离
    //    于是我们可以求得对于每一个 M， S→O→M 的最短距离
    // 2. 然后我们有 M→O'→M'，同样对于特定的 M'，枚举所有的 O'，就能得到 M→O'→M' 的最短距离
    //    于是我们可以求得所有 M 和 M' 的 M→O→M' 的最短距离
    // 3. 最后是 M→T，每个 M 到 T 的最短距离很好求
    //
    // 为了让上面好求，我们先预处理，通过 BFS，获得 S O M T 两两之间的最短路径
    //
    // 接下来，就由上面的信息，我们需要确立机关的触发顺序。
    // 这里用的动态规划。
    //
    // 看官方题解好好理解，尤其是最后的小结。

    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    int n, m;

    public int minimalSteps(String[] maze) {
        n = maze.length;
        m = maze[0].length();

        // 记录 S O M T 这四类特殊点
        List<int[]> buttons = new ArrayList<>();
        List<int[]> stones = new ArrayList<>();
        int sx = -1, sy = -1, tx = -1, ty = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char c = maze[i].charAt(j);
                if (c == 'S') {
                    sx = i;
                    sy = j;
                } else if (c == 'O') {
                    stones.add(new int[]{i, j});
                } else if (c == 'M') {
                    buttons.add(new int[]{i, j});
                } else if (c == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }

        // 机关个数，石堆个数
        int nb = buttons.size();
        int ns = stones.size();

        // 起始点到地图各点的最短路径长度
        int[][] startDist = bfs(sx, sy, maze);

        // 地图中没有机关
        // 直接起点到终点
        if (nb == 0)
            return startDist[tx][ty];


        // dist[i][j] 表示 buttons 中第 i 个开关到第 j 个开关的最短距离     M→O→M'
        // dist[i][nb] 表示 buttons 中第 i 个开关到起点的最短距离          S→O→M
        // dist[i][nb+1] 表示 buttons 中第 i 个开关到终点的最短距离        M→T
        int[][] dist = new int[nb][nb + 2];
        for (int i = 0; i < nb; i++) {
            Arrays.fill(dist[i], -1);
        }

        // dd[i] 表示 buttons 中第 i 个按钮离其余各点的最短距离
        int[][][] dd = new int[nb][][];
        for (int i = 0; i < nb; i++) {
            int[][] d = bfs(buttons.get(i)[0], buttons.get(i)[1], maze);
            dd[i] = d;
            // 第 i 个开关到终点的最短距离
            dist[i][nb + 1] = d[tx][ty];
        }


        for (int i = 0; i < nb; i++) {
            // 对每个 M，求最小的 S→O→M
            int tmp = -1;
            // 枚举所有的 O
            for (int j = 0; j < ns; j++) {
                int midX = stones.get(j)[0], midY = stones.get(j)[1];
                // S 先到 O，再 O 到 M，保证路径可达
                if (dd[i][midX][midY] != -1 && startDist[midX][midY] != -1) {
                    // 取最短的情况
                    if (tmp == -1 || tmp > dd[i][midX][midY] + startDist[midX][midY]) {
                        tmp = dd[i][midX][midY] + startDist[midX][midY];
                    }
                }
            }
            dist[i][nb] = tmp;

            // 对 M 两两选择，求 M→O→M'
            for (int j = i + 1; j < nb; j++) {
                // 选好了 M 和 M'
                int mn = -1;
                // 枚举所有的 O
                for (int k = 0; k < ns; k++) {
                    int midX = stones.get(k)[0], midY = stones.get(k)[1];
                    if (dd[i][midX][midY] != -1 && dd[j][midX][midY] != -1) {
                        if (mn == -1 || mn > dd[i][midX][midY] + dd[j][midX][midY]) {
                            mn = dd[i][midX][midY] + dd[j][midX][midY];
                        }
                    }
                }
                dist[i][j] = mn;
                dist[j][i] = mn;
            }
        }

        // 至此，所有预处理信息已准备好

        // 不可达的情况
        for (int i = 0; i < nb; i++) {
            // 存在一个开关
            // 使得 S→O→M 或者 M→T 不成立
            // 说明就无法拿到宝藏
            if (dist[i][nb] == -1 || dist[i][nb + 1] == -1)
                return -1;
        }

        // 下面开始动态规划
        // dp[mask][i] 表示目前任务的完成状态为 mask，且刚完成了任务 i
        // 行数 1<<nb，则行号的选择范围就是 nb 位的二进制数
        // 比如 nb=4，则 1<<nb 为 2^4 即下标范围为 0000~1111

        // 状态转移 dp[mask | 2^j][j] = min( dp[mask][i]+dist[i][j] )
        // 即完成 i 之后，去完成 j

        int[][] dp = new int[1 << nb][nb];
        // -1 表示这种状态不可达
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        // 初始状态，即 S→O→M，完成第一个任务的花费（按下第一个按钮）
        // mask 上只有一位是 1
        for (int i = 0; i < nb; i++) {
            dp[1 << i][i] = dist[i][nb];
        }
        for (int mask = 1; mask < dp.length; mask++) {
            for (int i = 0; i < nb; i++) {
                // 由 dp[mask][i] 的状态，再去完成下一个任务
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < nb; j++) {
                        // j 必须在 mask 表示中没有完成的那些任务
                        if ((mask & (1 << j)) == 0) {
                            int next = mask | (1 << j);
                            if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j])
                                dp[next][j] = dp[mask][i] + dist[i][j];
                        }
                    }
                }
            }
        }

        // 经过上面，我们就知道了所有任务完成顺序下，最小的花费
        // 即 dp[111..111][i]，表示第 i 个任务最后完成时的最小花费

        // 最后统计 M→T
        // 完成所有任务，有 nb 种可能，再去终点
        // 这些可能中，找最小的
        int ret = Integer.MAX_VALUE;
        int finalMask = (1 << nb) - 1;
        for (int i = 0; i < nb; i++) {
            ret = Math.min(ret, dp[finalMask][i] + dist[i][nb + 1]);
        }
        return ret;
    }

    private int[][] bfs(int x, int y, String[] maze) {
        // ret[i][j] 表示 (i,j) 到 (x,y) 的最短路径长度
        // -1 表示不可达
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(ret[i], -1);
        }

        // 从 (x,y) 点向四周扩散
        // 入队时计算 ret
        ret[x][y] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int curx = p[0], cury = p[1];
            // 周围 4 个方向入队
            for (int i = 0; i < 4; i++) {
                int nx = curx + dx[i], ny = cury + dy[i];
                // 下一个访问位置必须可达，且之前没访问过
                if (inBound(nx, ny) && maze[nx].charAt(ny) != '#' && ret[nx][ny] == -1) {
                    ret[nx][ny] = ret[curx][cury] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return ret;
    }

    // 下标是否在矩阵有效范围内
    private boolean inBound(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }
}
