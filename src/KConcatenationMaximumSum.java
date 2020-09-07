// 1191. K 次串联后最大子数组之和
//
// 给你一个整数数组 arr 和一个整数 k。
//
// 首先，我们要对该数组进行修改，即把原数组 arr 重复 k 次。
//
// 举个例子，如果 arr = [1, 2] 且 k = 3，那么修改后的数组就是 [1, 2, 1, 2, 1, 2]。
//
// 然后，请你返回修改后的数组中的最大的子数组之和。
//
// 注意，子数组长度可以是 0，在这种情况下它的总和也是 0。
//
// 由于 结果可能会很大，所以需要 模（mod） 10^9 + 7 后再返回。


package src;

public class KConcatenationMaximumSum {
    public int kConcatenationMaxSum(int[] arr, int k) {
        if (arr.length == 0) {
            return 0;
        }

        // 以下 3 种情况取最大的即可
        // 1. 结果的首尾都在一个子数组中 → 下面的 max
        // 2. 结果的首尾横跨两个数组之间，即 arr 的后缀最大子数组之和 + arr 的前缀最大子数组之和
        // 3. 横跨多个数组，即情况二中，中间加多个 arr，如果 sum(arr) > 0，那么越多越好，最多 k-2 个，如果 < 0，就别加了

        long dp = -1;
        long max = 0;

        long maxPrefix = Long.MIN_VALUE;
        long sum = 0;

        for (int value : arr) {
            if (dp < 0) {
                dp = value;
            } else {
                dp += value;
            }
            max = Math.max(dp, max);
            sum += value;
            maxPrefix = Math.max(maxPrefix, sum);
        }

        if (k == 1) {
            return (int) (max % (1000000000 + 7));
        }

        long maxSuffix = Long.MIN_VALUE;
        sum = 0;
        for (int i = arr.length - 1; i >= 0; i--) {
            sum += arr[i];
            maxSuffix = Math.max(sum, maxSuffix);
        }

        return (int) (Math.max(max, Math.max(sum, 0) * (k - 2) + maxPrefix + maxSuffix) % (1000000000 + 7));
    }
}
