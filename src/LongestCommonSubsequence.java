// 1143. 最长公共子序列
//
// 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。
//
// 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
// 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。两个字符串的「公共子序列」是这两个字符串所共同拥有的子序列。
//
// 若这两个字符串没有公共子序列，则返回 0。


package src;

public class LongestCommonSubsequence {
    // dp[i][j] 表示 text1 前 i 个字符和 text2 前 j 个字符的最长公共子序列长度
    // dp[i][j] = max(dp[i][j-1], dp[i-1][j], dp[i-1][j-1])
    // if text1[i-1] == text2[j-1]，第三个还要+1
    public int solution(String text1, String text2) {
        if (text1 == null || text1.length() == 0) {
            return 0;
        }
        if (text2 == null || text2.length() == 0) {
            return 0;
        }

        int n = text1.length();
        int m = text2.length();

        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        new LongestCommonSubsequence().solution("abcde", "ace");
    }
}
