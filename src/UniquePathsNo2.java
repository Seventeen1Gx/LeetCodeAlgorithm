//63. 不同路径Ⅱ
//
//一个机器人位于一个 m x n 网格的左上角。
//
//机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角。
//
//现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
//
//网格中的障碍物和空位置分别用 1 和 0 来表示


package src;

public class UniquePathsNo2 {
    //动态规划
    //dp[i,j]表示到达i、j处的路径数
    public int solution(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        if (m <= 0)
            return 0;
        int n = obstacleGrid[0].length;

        int[][] dp = new int[m][n];
        //标识是否遇到障碍物
        boolean flag = false;
        for (int i = 0; i < m; i++) {
            if (!flag && obstacleGrid[i][0] == 1)
                flag = true;
            if (flag)
                dp[i][0] = 0;
            else
                dp[i][0] = 1;
        }
        flag = false;
        for (int i = 0; i < n; i++) {
            if (!flag && obstacleGrid[0][i] == 1)
                flag = true;
            if (flag)
                dp[0][i] = 0;
            else
                dp[0][i] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1)
                    dp[i][j] = 0;
                else
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
    }
}
