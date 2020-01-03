package src;

import java.util.ArrayList;
import java.util.List;

/**
 * 91. 解码方法
 *
 * 一条包含字母 A-Z 的消息通过以下方式进行了编码：
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 *
 * 给定一个只包含数字的非空字符串，请计算解码方法的总数。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class DecodeWays {
    /**
     * 回溯 -- 超时
     */
    public int solution1(String s) {
        callback(s, 0, new StringBuilder());
        return results.size();
    }

    private static final int UP = 26;

    private List<String> results = new ArrayList<>();

    /**
     * @param s 要解码的整个数字字符串
     * @param start 当前开始的考虑位置
     * @param result s[0:start) 解码的结果
     */
    private void callback(String s, int start, StringBuilder result) {
        if (start == s.length()) {
            results.add(new String(result));
        } else if (s.charAt(start) == '0') {
            // 这种情况下，两种解码方法都没法转换
            return;
        } else {
            int num;
            // 试验两种情况
            if (start + 1 <= s.length()) {
                num = Integer.parseInt(s.substring(start, start + 1));
                result.append((char) (num - 1 + 'A'));
                callback(s, start + 1, result);
                result.setLength(result.length() - 1);

            }

            if (start + 2 <= s.length()) {
                num = Integer.parseInt(s.substring(start, start + 2));
                if (num <= UP) {
                    result.append((char) (num - 1 + 'A'));
                    callback(s, start + 2, result);
                    result.setLength(result.length() - 1);
                }
            }
        }
    }

    /**
     * 动态规划
     */
    public int solution2(String s) {
        // dp[i] 表示 s[0:i] 的解码方法数
        // 状态转移：
        // dp[i] = dp[i-1] + dp[i-2]，如果 s[i] 代表一个字母，而且 s[i-1:i] 代表一个字母
        int[] dp = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            dp[i] = 0;
            // 看 s[i] 是否能解码成一个字母
            if (s.charAt(i) != '0') {
                if (i - 1 >= 0) {
                    dp[i] += dp[i - 1];
                } else {
                    dp[i] += 1;
                }
            }
            // 看 s[i-1:i] 是否能解码成一个字母
            if (i - 1 >= 0 && s.charAt(i - 1) != '0' && Integer.parseInt(s.substring(i - 1, i + 1)) <= UP) {
                if (i - 2 >= 0) {
                    dp[i] += dp[i - 2];
                } else {
                    dp[i] += 1;
                }
            }
        }
        return dp[s.length() - 1];
    }

    public static void main(String[] args) {
        DecodeWays d = new DecodeWays();
        d.solution2("226");
    }
}
