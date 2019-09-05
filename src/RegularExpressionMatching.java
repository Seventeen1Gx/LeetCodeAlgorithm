//10. 正则表达式的匹配
//
//给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
//
//'.' 匹配任意单个字符
//'*' 匹配零个或多个前面的那一个元素
//
//所谓匹配，是要涵盖 整个 字符串s，而不是部分字符串。
//
//s 可能为空，且只包含从 a-z 的小写字母。
//p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
//
//注意不是贪婪匹配


public class RegularExpressionMatching {
    public boolean solution1(String s, String p) {
        //先考虑空的情况
        if (s == null || p == null)
            return false;

        if (s.length() == 0 && p.length() == 0)
            return true;

        if (s.length() != 0 && p.length() == 0)
            return false;

        //注意s=""，p="b*"的情况
        if (s.length() == 0 && p.length() != 0) {
            int i = 0;
            while (i < p.length()) {
                //时刻注意，数组不能越界
                if (i + 1 < p.length() && p.charAt(i) != '*' && p.charAt(i + 1) == '*')
                    //因为s为空，p就得是a*a*a*的形式
                    i = i + 2;
                else
                    return false;
            }
            return true;
        }

        //到这里，s与p都不为空
        int m = s.length();
        int n = p.length();
        int i = 0;
        int j = 0;
        char a = s.charAt(i);
        char b = p.charAt(j);

        boolean condition = j + 1 >= n || p.charAt(j + 1) != '*';

        if (b == '.') {
            if (condition) {
                //b为'.'，且b身后没有字符，或身后字符不为*，则直接匹配任意一个字符
                return solution1(s.substring(i + 1), p.substring(j + 1));
            } else {
                //b为'.'，且b身后有一个'*'，则'.*'可以匹配长度大于等于0的任意子串
                //则每种匹配方式都要试一试
                while (i < m) {
                    //有一种匹配方式成功即可
                    if (solution1(s.substring(i), p.substring(j + 2)))
                        return true;
                    i++;
                }
                //这时i==m
                if (solution1("", p.substring(j + 2)))
                    return true;
                return false;
            }
        } else if (b >= 'a' && b <= 'z') {
            if (condition) {
                //b为一个字母，且b身后没有字符，或身后字符不为*，则比较
                if (a == b)
                    return solution1(s.substring(i + 1), p.substring(j + 1));
                else
                    return false;
            } else {
                //b为一个字母，且身后有一个'*'，则'b*'能匹配大于等于0个b
                while (i < m) {
                    if (solution1(s.substring(i), p.substring(j + 2)))
                        return true;
                    if (s.charAt(i) == b)
                        i++;
                    else
                        return false;
                }
                if (i == m && solution1("", p.substring(j + 2)))
                    return true;
                return false;
            }
        }
        return false;
    }

    //官方回溯算法(我的方法是每种匹配都试一次，其实只要试匹配0个或1个即可，后面的多个会在递归中体现)
    public boolean solution1_official(String text, String pattern) {
        //若是p串为空，则s串为空则匹配，不为空则不匹配
        if (pattern.isEmpty()) return text.isEmpty();

        //first_match为s串与p串第一个字符的匹配结果(在&&之后，s与p都不为空)
        //这里将s串为空，p串不为空视为第一次不匹配，但对于""与"b*"仍然视为匹配
        //因为b*匹配空串的情况是不看first_match的结果的
        boolean first_match = (!text.isEmpty() &&
                (pattern.charAt(0) == text.charAt(0) || pattern.charAt(0) == '.'));

        if (pattern.length() >= 2 && pattern.charAt(1) == '*'){
            //如果有第二个字符且第二个字符为*，则是两种情况的任意一种
            //情况1，a*匹配空串，另一种情况，a*消去一个a，继续用来匹配剩下的s串(匹配多个a在递归中体现)
            return (solution1_official(text, pattern.substring(2)) ||
                    (first_match && solution1_official(text.substring(1), pattern)));
        } else {
            //如果没有第二个字符或者第二个字符不为*
            //在第一个字符匹配的情况下，看剩下的s与p串是否匹配
            return first_match && solution1_official(text.substring(1), pattern.substring(1));
        }
    }

    Boolean[][] memo;

    //动态规划法，dp[i,j]表示s[i:]与p[j:]的匹配结果
    public boolean solution2(String s, String p) {
        memo = new Boolean[s.length() + 1][p.length() + 1];
        return dp_TtoB(0, 0, s, p);
    }

    //自顶向下动态规划
    public boolean dp_TtoB(int i, int j, String s, String p) {
        //若当前答案已存在
        if (memo[i][j] != null) {
            return memo[i][j];
        }

        boolean ans;

        //p串为空的情况
        if (j == p.length()) {
            ans = i == s.length();
        } else {
            //p串不为空，若s串为空，则视为第一个字符不匹配
            //若s串也不为空，则看第一个字符是否匹配
            boolean first_match = (i < s.length() &&
                    (p.charAt(j) == s.charAt(i) ||
                            p.charAt(j) == '.'));

            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                //p串第二个字符存在且为*，试验两种情况，这里串的长度在缩小
                ans = (dp_TtoB(i, j + 2, s, p) ||
                        first_match && dp_TtoB(i + 1, j, s, p));
            } else {
                ans = first_match && dp_TtoB(i + 1, j + 1, s, p);
            }
        }
        //备忘录记住结果
        memo[i][j] = ans;
        return ans;
    }

    //自底向上动态规划
    public boolean dp_BtoT(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];

        //空串与空串匹配
        dp[s.length()][p.length()] = true;

        //s从空串开始一点点加字符，p从一个字符开始一点点加字符
        for (int i = s.length(); i >= 0; i--) {
            for (int j = p.length() - 1; j >= 0; j--) {
                boolean first_match = (i < s.length() &&
                        (p.charAt(j) == s.charAt(i) ||
                                p.charAt(j) == '.'));
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    dp[i][j] = dp[i][j + 2] || first_match && dp[i + 1][j];
                } else {
                    dp[i][j] = first_match && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        String s = "ab";
        String p = ".*c";

        RegularExpressionMatching m = new RegularExpressionMatching();

        m.solution1(s, p);
    }
}
