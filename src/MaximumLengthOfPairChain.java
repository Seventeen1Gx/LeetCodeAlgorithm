// 646. 最长数对链
//
// 给出 n 个数对。 在每一个数对中，第一个数字总是比第二个数字小。
//
// 现在，我们定义一种跟随关系，当且仅当 b < c 时，数对(c, d) 才可以跟在 (a, b) 后面。
// 我们用这种形式来构造一个数对链。
//
// 给定一个对数集合，找出能够形成的最长数对链的长度。
// 你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。


package src;

import java.util.Arrays;
import java.util.Comparator;

public class MaximumLengthOfPairChain {
    // dp[i] 表示以 pairs[i] 为结尾得到最长数链长度
    // dp[i] = max(dp[j]+1)，且要 j 的尾小于 i 的头， j 在 [0,i-1] 取值
    public int solution(int[][] pairs) {
        if (pairs == null || pairs.length == 0) {
            return 0;
        }

        // 先要按左边界排序
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[0]));

        int[] dp = new int[pairs.length];
        // dp[i] 至少是 1
        Arrays.fill(dp, 1);

        // 记录最大的长度
        int maxLen = 1;
        for (int i = 1; i < pairs.length; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[j][1] < pairs[i][0]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (maxLen < dp[i]) {
                maxLen = dp[i];
            }
        }
        return maxLen;
    }
}
