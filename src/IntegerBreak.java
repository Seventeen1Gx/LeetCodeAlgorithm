// 343. 整数拆分
//
// 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积。

package src;

public class IntegerBreak {
    public int solution(int n) {
        // dp[i] 表示对整数 i 分割的结果
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            // 分成 j 段和 i-j 段
            // 其中 i-j 段可以继续分割，也可以不继续分割
            // j 遍历所有可能的情况
            for (int j = 1; j <= i - 1; j++) {
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
            }
        }
        return dp[n];
    }


    // 其实只要尽量多的 3 即可
    // 如果多余 1，则和一个 3 组成 4，再分成两个 2
    // 如果多余 2，则单独作为一个 2
}
