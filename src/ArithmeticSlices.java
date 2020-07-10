// 413. 等差数列划分
//
// 数组 A 包含 N 个数，且索引从0开始。数组 A 的一个子数组划分为数组 (P, Q)，P 与 Q 是整数且满足 0<=P<Q<N 。
//
// 如果满足以下条件，则称子数组(P, Q)为等差数组：
//
// 元素 A[P], A[p + 1], ..., A[Q - 1], A[Q] 是等差的。并且 P + 1 < Q 。
//
// 函数要返回数组 A 中所有为等差数组的子数组个数。

// 因为 P+1<Q，则 Q-P+1>2，即 (P,Q) 子数组至少有三个元素


package src;

public class ArithmeticSlices {
    public int solution(int[] A) {
        // dp[i] 表示以 A[i] 为结尾的等差子数组的个数
        // 现已知 dp[i-1]，则如果 A[i]-A[i-1]=A[i-1]-A[i-2]，则有 dp[i]=dp[i-1]+1
        // 解释：dp[i-1] 表示的等差子数组，后面加一个 A[i]，仍是等差子数组
        // 同时 A[i-2]、A[i-1]、A[i] 是新增的一个等差子数组

        // 最终要求所有的等差子数组数，则是 dp[0:n-1] 的累加

        if (A == null || A.length < 3)
            return 0;

        int n = A.length;
        int[] dp = new int[n];
        for (int i = 2; i < n; i++) {
            if (A[i - 2] - A[i - 1] == A[i - 1] - A[i])
                dp[i] = dp[i - 1] + 1;
        }

        int total = 0;
        for (int cnt : dp)
            total += cnt;
        return total;
    }
}
