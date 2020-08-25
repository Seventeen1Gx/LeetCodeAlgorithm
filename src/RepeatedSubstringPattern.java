// 459. 重复的子字符串
//
// 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。
// 给定的字符串只含有小写英文字母，并且长度不超过 10000。

package src;

public class RepeatedSubstringPattern {
    public boolean solution1(String s) {
        // 枚举
        // s' 重复多次构成 s
        // 则：
        // - s 的长度是 s' 的长度的整数倍
        // - s' 是 s 的前缀
        // - 对任意的 i in [n', n)，有 s[i] = s[i-n']
        int n = s.length();

        for (int i = 1; i <= n / 2; i++) {
            if (n % i != 0) {
                continue;
            }
            int j;
            for (j = i; j < n; j++) {
                if (s.charAt(j) != s.charAt(j - i)) {
                    break;
                }
            }
            if (j == n) {
                return true;
            }
        }
        return false;
    }

    // ss = s + s
    // 去除首尾各一个字符得到 ss'
    // 如果 s 是 ss' 的子串，则返回 true
    // 这样就转变成字符串匹配问题
}
