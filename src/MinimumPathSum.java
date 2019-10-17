//64. 最小路径和
//
//给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
//
//说明：每次只能向下或者向右移动一步。


package src;

public class MinimumPathSum {
    //动态规划
    //dp[i,j]表示到达i、j处的最小路径和
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        if (m <= 0)
            return 0;
        int n = grid[0].length;

        int[][] dp = new int[m][n];
        //初始化
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }
}
