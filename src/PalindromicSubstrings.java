//647. 回文子串
//
//给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
//
//具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。
//
//示例 1:
//输入: "abc"
//输出: 3
//解释: 三个回文子串: "a", "b", "c".
//
//示例 2:
//输入: "aaa"
//输出: 6
//说明: 6个回文子串: "a", "a", "a", "aa", "aa", "aaa".
//
//注意:
//输入的字符串长度不会超过1000。


package src;

public class PalindromicSubstrings {
    //动态规划
    public int solution(String s) {
        int len = s.length();

        int ans = 0;

        //dp[i][j]表示子串s[i,j]是否为回文串，易知，i<=j
        boolean[][] dp = new boolean[len][len];

        //奇数长度情况
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
            ans++;
        }
        for (int i = 0; i < len; i++) {
            //从中间向两边扩散
            int step = 1;
            while (true) {
                int left = i - step, right = i + step;

                if (left < 0 || right >= len) {
                    //超出有效范围，换下一个i
                    break;
                }
                if (s.charAt(left) == s.charAt(right)) {
                    //因为是从中心扩展，一旦遇到非回文串就不再扩展，所以这里，dp[left+1][right-1]肯定是回文串
                    dp[left][right] = true;
                    ans++;
                } else {
                    //往后的串都不是回文串
                    break;
                }
                step++;
            }
        }

        //偶数长度情况
        for (int i = 0; i < len - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = true;
                ans++;
            }
        }
        for (int i = 0; i < len; i++) {
            //从中间向两边扩散
            int step = 1;
            while (true) {
                int left = i - step, right = i + 1 + step;

                if (left < 0 || right >= len) {
                    //超出有效范围，换下一个i
                    break;
                }
                if (s.charAt(left) == s.charAt(right)) {
                    dp[left][right] = true;
                    ans++;
                } else {
                    //往后的串都不是回文串
                    break;
                }
                step++;
            }
        }

        return ans;
    }
}
