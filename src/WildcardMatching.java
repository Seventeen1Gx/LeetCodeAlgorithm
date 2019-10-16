//44. 通配符匹配
//
//给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
//
//'?' 可以匹配任何单个字符。
//'*' 可以匹配任意字符串（包括空字符串）。
//
//两个字符串完全匹配才算匹配成功。
//
//说明:
//s 可能为空，且只包含从 a-z 的小写字母。
//p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。


package src;

public class WildcardMatching {
    //但这种方法超时
    public boolean solution1(String s, String p) {
        if (s == null || p == null)
            return false;

        //递归出口
        if (p.isEmpty()) return s.isEmpty();

        //两个字符串的第一个字符是否匹配
        boolean firstMatch = !s.isEmpty() && ( s.charAt(0) == p.charAt(0) || p.charAt(0) == '?');

        if (p.charAt(0) == '*') {
            //注意多个连续的*跟一个*的效果相同
            int i = 0;
            while (i < p.length() && p.charAt(i) == '*') i++;
            //匹配空串或者任意字符串
            return solution1(s, p.substring(i)) || (!s.isEmpty() && solution1(s.substring(1), p.substring(i - 1)));
        } else {
            return firstMatch && solution1(s.substring(1), p.substring(1));
        }
    }

    //动态规划，dp[i][j]表示s[i:]和p[j:]的匹配结果
    public boolean solution2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];

        //空串与空串相匹配
        dp[s.length()][p.length()] = true;

        //s从空串开始一点点加字符，p从末尾一个字符开始一点点加字符
        //每次求dp[i][j]，且其之后的dp都在之前求出(循环方向所致)
        for (int i = s.length(); i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                //s[i:]为空则不匹配，不为空则看对应字符是否匹配
                boolean firstMatch = i < s.length() &&
                        (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?');
                if (p.charAt(j) == '*') {
                    dp[i][j] = dp[i][j + 1] || (i < s.length() && dp[i + 1][j]);
                } else {
                    dp[i][j] = firstMatch && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }

}
