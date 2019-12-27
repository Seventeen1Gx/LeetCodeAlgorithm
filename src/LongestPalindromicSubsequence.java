//516. 最长回文子序列
//
//给定一个字符串s，找到其中最长的回文子序列。可以假设s的最大长度为1000。
//
//注意：子序列和子串的不同就是，子序列可以不连续


package src;

public class LongestPalindromicSubsequence {
    //动态规划
    //dp[i][j]表示子串s[i][j]的最长回文子序列长度
    //状态转移
    //dp[i][j] = dp[i+1][j-1] + 2 ，当s[i]==s[j]
    //dp[i][j] = max(dp[i+1][j], dp[i][j-1]) ，当s[i]!=s[j]
    public int solution(String s) {
        //注意以下求值顺序--从矩阵的右下角开始，然后每行是从左往右求
        int n = s.length();
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            f[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    f[i][j] = f[i + 1][j - 1] + 2;
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]);
                }
            }
        }
        return f[0][n - 1];
    }
}
