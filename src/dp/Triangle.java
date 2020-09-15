// 120. 三角形最小路径和
//
// 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
//
// 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
//
// 例如，给定三角形：
//
// [
//      [2],
//     [3,4],
//    [6,5,7],
//   [4,1,8,3]
// ]
//
// 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。


package src.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Triangle {
    public int minimumTotal(List<List<Integer>> triangle) {
        // dp[i][j] 表示到达第 i 行第 j 个元素的最小路径和
        // dp[i][j] = Min(dp[i-1][j-1],dp[i-1][j])+cur[i][j]
        // 每行只取决于前一行，故收缩 dp 到一维

        int n = triangle.size();
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; i++) {
            // 由于对于 dp[j]，它依赖于 dp[j] 和 dp[j-1]
            // 故我们先求 dp[j]，再求 dp[j-1]
            // 如果反过来，求 dp[j] 的时候，dp[j-1] 已经不是上一行的结果了
            for (int j = i; j >= 0; j--) {
                if (j != 0) {
                    dp[j] = Math.min(dp[j - 1], dp[j]);
                }
                dp[j] += triangle.get(i).get(j);
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            min = Math.min(min, dp[i]);
        }
        return min;
    }

    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));
        new Triangle().minimumTotal(triangle);
    }
}
