// 207. 课程表
//
// 你这个学期必须选修 numCourse 门课程，记为 0 到 numCourse-1 。
//
// 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们：[0,1]
//
// 给定课程总量以及它们的先决条件，请你判断是否可能完成所有课程的学习？


package src;

import java.util.*;

public class CourseSchedule {
    // prerequisites 能构成一个有向图，每门课是图中的结点，如果完成 A 需要先完成 B，则存在 A 到 B 的有向边
    // 分辨图中是否有环即可，无环则可以完成学习
    // 采用拓扑排序
    // 分为深度优先和广度优先
    private static class DFS {
        // 0 表示未遍历过，1 表示正在遍历，2 表示该点遍历完成，即该点相邻结点也被遍历
        int[] visited;
        // 统计各个点的邻居
        Map<Integer, List<Integer>> neighbours;
        // 提前结束遍历的指示变量
        boolean valid = true;

        public boolean solution(int numCourses, int[][] prerequisites) {
            // 初始化全局变量
            visited = new int[numCourses];
            neighbours = new HashMap<>(numCourses);
            // 统计
            for (int[] p : prerequisites) {
                List<Integer> list = neighbours.getOrDefault(p[0], new ArrayList<>());
                list.add(p[1]);
                neighbours.put(p[0], list);
            }
            // 每次从一个未遍历的结点开始遍历
            for (int i = 0; i < numCourses && valid; i++) {
                if (visited[i] == 0) {
                    dfs(i);
                }
            }
            return valid;
        }

        // 从 n 开始深度优先遍历
        private void dfs(int n) {
            // 先遍历 n
            visited[n] = 1;
            // 再遍历 n 相邻未遍历结点
            for (int neighbour : neighbours.getOrDefault(n, new ArrayList<>())) {
                // 剪枝
                if (!valid) {
                    return;
                }
                if (visited[neighbour] == 1) {
                    // 深度遍历的过程中，发现又遇到正在进行遍历的结点
                    // 说明有环
                    valid = false;
                    return;
                }
                if (visited[neighbour] == 0) {
                    dfs(neighbour);
                }
            }
            // 相邻结点都被遍历过
            visited[n] = 2;
        }
    }

    private static class BFS {
        // 统计每个结点的入度
        int[] inDegree;
        // 统计各个点的邻居
        Map<Integer, List<Integer>> neighbours;

        public boolean solution(int numCourses, int[][] prerequisites) {
            inDegree = new int[numCourses];
            neighbours = new HashMap<>(numCourses);
            for (int[] p : prerequisites) {
                List<Integer> list = neighbours.getOrDefault(p[0], new ArrayList<>());
                list.add(p[1]);
                neighbours.put(p[0], list);

                inDegree[p[1]]++;
            }
            Queue<Integer> queue = new ArrayDeque<>();

            // 统计删除了多少个结点
            int cnt = 0;

            // 先将入度为 0 的结点入队
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
            while (!queue.isEmpty()) {
                // 出队，将该结点删除，跟它相连的边也删除
                int n = queue.poll();
                cnt++;
                for (int neighbor : neighbours.getOrDefault(n, new ArrayList<>())) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
            return cnt == numCourses;
        }
    }
}
