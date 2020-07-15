// 300. 最长上升子序列
//
// 给定一个无序的整数数组，找到其中最长上升子序列的长度。

package src;

import java.util.Arrays;

public class LongestIncreasingSubsequence {
    public int solution(int[] nums) {
        // dp[i] 表示以 num[i] 为结尾的最长上升子序列的长度

        if (nums == null || nums.length == 0)
            return 0;

        int len = nums.length;
        int[] dp = new int[len];
        // 每个 dp[i] 至少是 1
        Arrays.fill(dp, 1);

        // 求每个 dp[i]
        for (int i = 0; i < len; i++) {
            // 在 dp[j] 的基础上，添加 num[i] 能否构成最长子序列
            // 如果能，长度加一
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        int ret = 0;
        for (int i = 0; i < len; i++) {
            ret = Math.max(ret, dp[i]);
        }
        return ret;
    }

    public int solution2(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int n = nums.length;
        // tails[i] 表示长度为 i+1 的最长递增子序列的最后一个元素
        int[] tails = new int[n];
        // 现在跟踪的最长子序列的长度
        int len = 0;
        for (int num : nums) {
            int index = binarySearch(tails, len, num);
            tails[index] = num;
            if (index == len) {
                // 即 num 大于 tail[0:len) 中所有值
                // 它可以作为最后一个元素，使这些序列的长度都加一
                len++;
            }
        }
        return len;
    }

    // tails[0:len) 中二分寻找 key
    // 找到，则返回下标
    // 找不到则返回插入位置
    private int binarySearch(int[] tails, int len, int key) {
        int l = 0, h = len;
        while (l < h) {
            // l == h 时，不会进入循环
            // l == h+1 时，mid = l
            // 其它情况，l < mid < h
            // 所以，下面 h = mid，因为考虑范围是 [l,h)
            int mid = l + (h - l) / 2;
            if (tails[mid] == key)
                return mid;
            else if (tails[mid] > key)
                h = mid;
            else
                l = mid + 1;
        }
        return l;
    }
}
