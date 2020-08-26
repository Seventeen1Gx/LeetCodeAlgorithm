package demo;

public class ShortestPath {

    public int[] dijkstra(int[][] matrix, int source) {
        // 输入为邻接矩阵
        // matrix[i][j] 表示 i 到 j 的直达距离，+∞ 表示不连通，防相加溢出，可以设置成 100000
        // 输出 dist[i] 表示源点到 i 点的最短距离，dist[i] == 100000 表示不可达

        int n = matrix.length;
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        String[] path = new String[n];

        // 初始化
        dist[source] = 0;
        visited[source] = true;
        for (int i = 0; i < n; i++) {
            path[i] = source + "->" + i;
        }


        for (int i = 1; i < n; i++) {
            // 从未被选取的结点中选取离源点结点
            // 贪心思想
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && matrix[source][j] < min) {
                    min = matrix[source][j];
                    index = j;
                }
            }
            // 更新最短路径
            dist[index] = min;
            visited[index] = true;
            // 判断是否通过 index 到达未被访问的目标点的距离更小，是的话就更新 matrix
            for (int j = 0; j < n; j++) {
                if (!visited[j] && matrix[source][index] + matrix[index][j] < matrix[source][j]) {
                    matrix[source][j] = matrix[source][index] + matrix[index][j];
                    path[j] = path[index] + "->" + j;
                }
            }
        }
        return dist;
    }

    public void floyd(int[][] matrix) {
        int n = matrix.length;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j]) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
    }
}
