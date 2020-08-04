// 210. 课程表Ⅱ
//
// 现在你总共有 n 门课需要选，记为 0 到 n-1。
//
// 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
//
// 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
//
// 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。


package src;

import java.util.*;

public class CourseScheduleNo2 {
    private static class  DFS {
        int[] visited;
        Map<Integer, List<Integer>> neighbors;
        Deque<Integer> stack;
        // 剪枝
        boolean valid = true;

        public int[] solution(int numCourses, int[][] prerequisites) {
            visited = new int[numCourses];
            neighbors = new HashMap<>(numCourses);
            stack = new ArrayDeque<>(numCourses);

            for (int[] info : prerequisites) {
                List<Integer> list = neighbors.getOrDefault(info[0], new ArrayList<>());
                list.add(info[1]);
                neighbors.put(info[0], list);
            }

            for (int i = 0; i < numCourses && valid; i++) {
                if (visited[i] == 0) {
                    dfs(i);
                }
            }
            if (!valid) {
                return new int[]{};
            }
            int[] ret = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                ret[i] = stack.pollFirst();
            }
            return ret;
        }

        private void dfs(int n) {
            // 表示正在进行遍历
            visited[n] = 1;
            // 遍历其相邻未遍历结点
            for (int neighbor : neighbors.getOrDefault(n, new ArrayList<>())) {
                // 剪枝处理
                if (!valid) {
                    return;
                }
                if (visited[neighbor] == 1) {
                    // 说明遇到了回环
                    valid = false;
                    return;
                }
                if (visited[neighbor] == 0) {
                    dfs(neighbor);

                }
            }
            // 到这里，说明当前结点的相邻结点都被遍历完了
            visited[n] = 2;
            stack.offer(n);
        }
    }

    private static class BFS {
        int[] inDegree;
        Map<Integer, List<Integer>> neighbors;

        public int[] solution(int numCourses, int[][] prerequisites) {
            inDegree = new int[numCourses];
            neighbors = new HashMap<>();
            for (int[] info : prerequisites) {
                List<Integer> list = neighbors.getOrDefault(info[0], new ArrayList<>());
                list.add(info[1]);
                neighbors.put(info[0], list);

                inDegree[info[1]]++;
            }

            int[] ret = new int[numCourses];
            int cursor = numCourses - 1;
            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 0; i < numCourses; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }

            while (!queue.isEmpty()) {
                int n = queue.poll();
                ret[cursor--] = n;
                for (int neighbor : neighbors.getOrDefault(n, new ArrayList<>())) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            return cursor == -1 ? ret : new int[]{};
        }
    }
}
