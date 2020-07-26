// 329. 矩阵中的最长递增路径
//
// 给定一个整数矩阵，找出最长递增路径的长度。
//
// 对于每个单元格，你可以往上，下，左，右四个方向移动。 你不能在对角线方向上移动或移动到边界外（即不允许环绕）。


public class LongestIncreasingPathInAMatrix {

    private int[][] matrix;
    private int m;
    private int n;
    private boolean[][] visited;
    private int maxPathLength = 0;
    // 控制上下左右
    private int[][] nexts = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };


    public int solution1(int[][] matrix) {
        if (matrix == null)
            return 0;

        this.matrix = matrix;
        this.m = matrix.length;

        if (m <= 0)
            return 0;

        this.n = matrix[0].length;

        if (n <= 0)
            return 0;

        // 可以从任一位置上开始
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                visited = new boolean[m][n];
                backtracking(i, j, Integer.MIN_VALUE, 0);
            }
        }

        return maxPathLength;
    }

    // 现在走到位置 i、j
    // 上一个位置的数字是 lastNum
    // 不算当前位置，已经走过的路径长度
    private void backtracking(int i, int j, int lastNum, int pathLength) {
        // 先检验位置的合法性
        if (i < 0 || i >= m || j < 0 || j >= n || matrix[i][j] <= lastNum || visited[i][j])
            return;

        if (pathLength + 1 > maxPathLength)
            maxPathLength = pathLength + 1;

        visited[i][j] = true;
        // 试验上下左右
        for (int[] next : nexts)
            backtracking(i + next[0], j + next[1], matrix[i][j], pathLength + 1);

        // 回溯
        visited[i][j] = false;

        // 保证当前方法前后外部状态保持一致
    }

    // -----------------------------------------------------------------------------
    // 上面的方法试验了每种可能性，但超时了
    // 这时由于我们会重复到达一个位置，而其实第一次到达时，就能算出从它开始的最长递增路径
    // 所以我们需要一个 memo 数组去保存这个结果，下一次到达这个位置的时候，就不用往后走了，直接取计算过的结果
    // memo[i][j] 表示从 i，j 位置开始的最长递增路径的长度
    int[][] memo;

    public int solution2(int[][] matrix) {
        if (matrix == null)
            return 0;

        this.matrix = matrix;
        this.m = matrix.length;

        if (m <= 0)
            return 0;

        this.n = matrix[0].length;

        if (n <= 0)
            return 0;

        memo = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dfs(i, j));
            }
        }
        return ans;
    }

    // 返回从 i，j 位置开始的最长递增路径的长度
    private int dfs(int i, int j) {
        // 已经算过了，就直接返回
        // 没有算过，就算好了再返回
        if (memo[i][j] != 0)
            return memo[i][j];

        // 看上下左右开始的最长递增路径长度
        // 然后 memo[i][j] = memo[x][y] + 1，x、y 为上下左右满足要求的格子
        for (int[] next : nexts) {
            int newRow = i + next[0], newCol = j + next[1];
            if (newRow >=0 && newRow < m && newCol >=0 && newCol < n && matrix[newRow][newCol] > matrix[i][j])
                memo[i][j] = Math.max(memo[i][j], dfs(newRow, newCol));
        }
        memo[i][j]++;

        return memo[i][j];
    }
}
