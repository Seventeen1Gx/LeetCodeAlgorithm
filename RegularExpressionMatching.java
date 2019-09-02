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
    public boolean solution(String s, String p) {
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
                return solution(s.substring(i + 1), p.substring(j + 1));
            } else {
                //b为'.'，且b身后有一个'*'，则'.*'可以匹配长度大于等于0的任意子串
                //则每种匹配方式都要试一试
                while (i < m) {
                    //有一种匹配方式成功即可
                    if (solution(s.substring(i), p.substring(j + 2)))
                        return true;
                    i++;
                }
                //这时i==m
                if (solution("", p.substring(j + 2)))
                    return true;
                return false;
            }
        } else if (b >= 'a' && b <= 'z') {
            if (condition) {
                //b为一个字母，且b身后没有字符，或身后字符不为*，则比较
                if (a == b)
                    return solution(s.substring(i + 1), p.substring(j + 1));
                else
                    return false;
            } else {
                //b为一个字母，且身后有一个'*'，则'b*'能匹配大于等于0个b
                while (i < m) {
                    if (solution(s.substring(i), p.substring(j + 2)))
                        return true;
                    if (s.charAt(i) == b)
                        i++;
                    else
                        return false;
                }
                if (i == m && solution("", p.substring(j + 2)))
                    return true;
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "ab";
        String p = ".*c";

        RegularExpressionMatching m = new RegularExpressionMatching();

        m.solution(s, p);
    }
}
