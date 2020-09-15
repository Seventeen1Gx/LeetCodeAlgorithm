// 115. 不同的子序列
//
// 给定一个字符串 S 和一个字符串 T，计算在 S 的子序列中 T 出现的个数。
//
// 一个字符串的一个子序列是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。
// （例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
//
// 题目数据保证答案符合 32 位带符号整数范围。


package src.dp;

public class DistinctSubsequences {
    public int numDistinct(String s, String t) {
        // dp[i][j] 表示 s[0:i) 的子序列中 t[0:j) 的出现次数
        // 当 t 是空串的时候，对于 s，去除所有字符即可，所以 dp[i][0] = 1
        // 当 s 是空串，t 不是空串的时候，dp[0][j] = 0，j > 0
        // s[i-1] == t[j-1] 时，dp[i][j] = dp[i-1][j-1] + dp[i-1][j]
        // 前者表示对于 dp[i-1][j-1] 的答案都可以再后面添加 s[i-1] 和 t[j-1] 形成正确的答案
        // 后者表示只添加 s 字符，不改动 t，对结果无影响
        // s[i-1] != t[j-1] 时，dp[i][j] = dp[i-1][j]

        int m = s.length();
        int n = t.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i < m + 1; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; j < n + 1; j++) {
            dp[0][j] = 0;
        }
        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}
