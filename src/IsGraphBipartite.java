// 785. 判断二分图
//
// 给定一个无向图 graph，当这个图为二分图时返回 true。
//
// 如果我们能将一个图的节点集合分割成两个独立的子集 A 和 B，并使图中的每一条边的两个节点一个来自 A 集合，一个来自 B 集合，我们就将这个图称为二分图。
//
// graph 将会以邻接表方式给出，graph[i] 表示图中与节点i相连的所有节点。每个节点都是一个在 0 到 graph.length-1 之间的整数。
// 这图中没有自环和平行边： graph[i] 中不存在 i，并且 graph[i] 中没有重复的值。


package src;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class IsGraphBipartite {
    // 思路：如果两个结点有边相连，那它们属于不同的集合

    // 遍历图进行染色，从一个结点开始，先把它染成红色，把它相连的结点染成绿色，与绿色相连的结点染成红色，直到每个结点都被染色。
    // 如果染色过程中，发现某个结点要染的色，和它已经有的颜色冲突，那么就不是二分图。

    // 遍历图，有 DFS 和 BFS 。
    // 一次遍历，只能遍历到一个连通子图。

    private static final int UNCOLORED = 0;
    private static final int RED = 1;
    private static final int GREEN = 2;
    private int[] color;
    private boolean valid;

    public boolean solution1(int[][] graph) {
        // 结点数
        int n = graph.length;
        // 默认认为是二分图
        // 不满足条件时，该值置为 false，不再进行接下来的遍历
        valid = true;
        // color[i] 记录了结点 i 的染色情况
        color = new int[n];
        Arrays.fill(color, UNCOLORED);

        // 因为不是连通图，所以要试验所有结点为遍历的起始节点
        for (int i = 0; i < n && valid; i++) {
            if (color[i] == UNCOLORED)
                // 起始结点没染色，我们染成红色
                dfs(i, RED, graph);
        }

        return valid;
    }

    // 深度是一直往下
    private void dfs(int node, int c, int[][] graph) {
        // 给 node 结点染成颜色 c
        color[node] = c;
        // 与 node 相连的结点，染成 c 的另一种颜色
        int co = c == RED ? GREEN : RED;
        for (int neighbor : graph[node]) {
            if (color[neighbor] == UNCOLORED) {
                dfs(neighbor, co, graph);
                if (!valid)
                    return;
            } else if (color[neighbor] != co) {
                valid = false;
                return;
            }
        }
    }

    // 广度优先搜索
    // 利用队列
    public boolean solution2(int[][] graph) {
        int n = graph.length;
        color = new int[n];
        Arrays.fill(color, UNCOLORED);

        // 遍历所有结点为染色起始结点
        for (int i = 0; i < n; i++) {
            // 该节点没被染色时，开始染色
            if (color[i] == UNCOLORED) {
                Queue<Integer> queue = new LinkedList<>();
                // 根结点入队
                queue.offer(i);
                color[i] = RED;
                while (!queue.isEmpty()) {
                    // 出队
                    int node = queue.poll();
                    // 与之相连的要染的颜色
                    int co = color[node] == RED ? GREEN : RED;
                    for (int neighbor : graph[node]) {
                        if (color[neighbor] == UNCOLORED) {
                            // 染色并入队
                            color[neighbor] = co;
                            queue.offer(neighbor);
                        } else if (color[neighbor] != co) {
                            // 已经染了不同的颜色
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
