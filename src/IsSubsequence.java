// 392. 判断子序列
//
// 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
//
// 你可以认为 s 和 t 中仅包含英文小写字母。字符串 t 可能会很长（长度 ~= 500,000），而 s 是个短字符串（长度 <=100）。
//
// 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。
// （例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
//
// 后续挑战 :
// 如果有大量输入的 S，称作S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。
// 在这种情况下，你会怎样改变代码？


package src;

public class IsSubsequence {
    // 假定当前需要匹配字符 c,而字符 c 在 t 中的位置 x1 和 x2 出现 (x1<x2)，那么匹配 x1 更好
    // 因为 x2 之后的字符，x1 也能取到，并且 x1 和 x2 之间的字符也有机会被匹配
    public boolean solution1(String s, String t) {
        int lastIndex = -1;
        for (char c : s.toCharArray()) {
            int index = t.indexOf(c, lastIndex + 1);
            if (index < 0)
                return false;
            lastIndex = index;
        }
        return true;
    }

    // 双指针
    public boolean solution2(String s, String t) {
        int p1 = 0, p2 = 0;
        while (p1 < s.length() && p2 < t.length()) {
            if (s.charAt(p1) == t.charAt(p2)) {
                p1++;
            }
            p2++;
        }
        return p1 == s.length();
    }

    // 动态规划
    // 对于 t 中的每个字符，要或者不要，就可以生成一个子序列
    public boolean solution3(String s, String t) {
        // dp[i][j] 表示 s[0,i) 是否为 t[0,j) 的子序列，且 i<=j
        // 状态转移思路就是 t 中的每个字符，要或不要
        // dp[i][j] = dp[i][j-1] || (dp[i-1][j-1] && s[i-1]==t[j-1])

        int m = s.length();
        int n = t.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        // dp 的第一行，表示 "" 是否为 t[0,j) 的子序列
        for (int i = 0; i < n + 1; i++)
            dp[0][i] = true;
        for (int i = 1; i < m + 1; i++) {
            // j < i 的不用求，默认就是 false
            for (int j = i; j < n + 1; j++) {
                dp[i][j] = dp[i][j - 1] || (dp[i - 1][j - 1] && s.charAt(i - 1) == t.charAt(j - 1));
            }
        }

        return dp[m][n];
    }

    // 根据 t 预处理，这样就无关 s，从而完成后续挑战
    public boolean solution4(String s, String t) {

        if (s.length() == 0)
            return true;
        if (t.length() == 0)
            return false;

        // 先记下来，就便于方法一的寻找，而且题目中说了，t 中都是小写字母
        // f[i][j] 表示字符串 t 从位置 i 开始，第一次出现字符 j 的位置
        // 如果位置 i 就是 j，则 f[i][j] = i
        // 否则 f[i][j]=f[i+1][j]
        // 于是动态规划的顺序是，先求行号大的

        int m = t.length();
        int[][] f = new int[m][26];
        // 先求最后一行
        for (int i = 0; i < 26; i++) {
            if (t.charAt(m - 1) == 'a' + i)
                f[m - 1][i] = m - 1;
            else
                f[m - 1][i] = -1;
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = 0; j < 26; j++) {
                if (t.charAt(i) == 'a' + j)
                    f[i][j] = i;
                else
                    f[i][j] = f[i + 1][j];
            }
        }

        // 得到上面的信息之后，我们再来判断 s 是否为 t 的子序列
        // 就不用每次找了
        int lastIndex = -1;
        for (char c : s.toCharArray()) {
            if (lastIndex == m - 1)
                return false;
            int index = f[lastIndex + 1][c - 'a'];
            if (index < 0)
                return false;
            lastIndex = index;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new IsSubsequence().solution4("aaaaaa", "bbaaaa"));
    }
}
