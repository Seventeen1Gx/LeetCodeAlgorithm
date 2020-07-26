// 410. 分割数组的最大值
//
// 给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。
// 设计一个算法使得这 m 个子数组各自和的最大值最小。
//
// 注意:
// 数组长度 n 满足以下条件:
// 1 ≤ n ≤ 1000
// 1 ≤ m ≤ min(50, n)

import java.util.Arrays;

public class SplitArrayLargestSum {
    public int solution(int[] nums, int m) {
        // dp[i][j] 表示数组前 i 个数 nums[0,i) 被分成 j 份的情况
        // dp[i][j] 可以是前 k 个数分成 j-1 份，即 dp[k][j-1]，然后和 [k+1,i) 这个子数组的情况组成
        // 即 dp[i][j] = min[max[dp[k][j-1], sum[k,i)]]，k 可取 1、2、3、... 、i-1
        //
        // i 个数被分成 j 份，由于不能有空的子数组，故只求 i>=j 的 dp[i][j]
        int n = nums.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        // sum[i] 表示 num[0,i) 之和
        // 于是，sum[k,i) = sum[i] - sum[k]
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        // 分成 1 份的基本情况
        for (int i = 1; i <= n; i++) {
            dp[i][1] = sum[i];
        }

        // 求 dp[i][j] 的时候，保证 dp[k][j-1] 已被求出 k < i
        for (int i = 1; i <= n; i++) {
            for (int j = 2; j <= m && j <= i; j++) {
                for (int k = 1; k < i; k++) {
                        dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j - 1], sum[i] - sum[k]));
                }
            }
        }
        return dp[n][m];
    }
}
