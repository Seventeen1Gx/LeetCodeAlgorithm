// 221. 最大正方形
//
// 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。


package src.dp;

public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        // dp[i][j] 表示以 matrix[i][j] 为右下角的最大正方形的边长
        // dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1，当 matrix[i][j] == '1'

        return 0;
    }
}
