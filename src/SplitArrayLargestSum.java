// 410. 分割数组的最大值
//
// 给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。
// 设计一个算法使得这 m 个子数组各自和的最大值最小。
//
// 注意:
// 数组长度 n 满足以下条件:
// 1 ≤ n ≤ 1000
// 1 ≤ m ≤ min(50, n)


package src;

import java.util.Arrays;

public class SplitArrayLargestSum {
    public int solution1(int[] nums, int m) {
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

    // 贪心 + 二分查找
    // 我们选定一个 x，然后验证能不能分割数组，使分组和的最大值小于等于这个 x
    // 然后我们就能找到一个临界 x，小于这个 x 就不满足要求，大于这个 x 就满足要求
    //
    // 验证方法我们使用贪心，使一个子数组在满足要求的情况下尽可能多包含元素，如果这种情况都满足，那肯定满足
    // 从前往后遍历，sum 表示当前统计的子数组的和，当 sum+nums[i]<=x，就加入子数组，否则重新开始一个新数组
    // 当遍历完成，子数组数小于等于 m 就说明能分出来
    public int solution2(int[] nums, int m) {
        int low = 0, high = 0;
    // 下界取数组元素的最大值
    // 上界取数组元素总和
        for (int i = 0; i < nums.length; i++) {
        low = nums[i] > low ? nums[i] : low;
        high += nums[i];
    }
        while (low < high) {
        int mid = low + (high - low) / 2;
        if (valid(nums, m, mid))
            // 满足要求，x 偏大
            high = mid;
        else
            low = mid + 1;
    }
        return low;
}

    private boolean valid(int[] nums, int m, int x) {
        int cnt = 1;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 提前退出，没必要算了
            if (cnt > m)
                return false;

            if (sum + nums[i] <= x) {
                sum += nums[i];
            } else {
                cnt++;
                sum = nums[i];
            }
        }
        return cnt <= m;
    }
}
