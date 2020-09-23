// 214. 最短回文串
//
// 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。


package src;

public class ShortestPalindrome {
    public String solution1(String s) {
        // 前面添加字符形成回文串，可以转变成，删去结尾串形成回文串

        int cnt = 0;
        // 依次删除结尾，判断是否为回文串
        for (int i = s.length(); i >= 0; i--) {
            if (isPalindrome(s.substring(0, i))) {
                break;
            }
            cnt++;
        }

        // cnt 记录了使 s 成为回文串而删去的结尾字符数
        // 下面将 s 尾部 cnt 个字符串反向记录在 sb 中
        int i = s.length() - 1;
        StringBuffer sb = new StringBuffer();
        while (cnt > 0) {
            sb.append(s.charAt(i));
            i--;
            cnt--;
        }

        return sb.toString() + s;
    }

    private boolean isPalindrome(String s) {
        // 判断一个字符串是否为回文串

        for (int i = 0, j = s.length() - 1; i < j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;

        // 另一种方法
        // StringBuffer sb = new StringBuffer(s);
        // sb.reverse();
        // if (sb.toString().equals(s)) {
        //     return true;
        // }
        // return false;
    }

    public String solution1_official(String s) {
        // 删除尾字符串，判断是否回文（跟上面同）

        StringBuffer sb = new StringBuffer(s);
        String rev = sb.reverse().toString();

        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.substring(0, len - i).equals(rev.substring(i))) {
                return rev.substring(0, i) + s;
            }
        }
        return "";
    }

    public String solution2_official(String s) {
        // 找到 s.substring(0,i) 为最长回文串
        // s.substring(i).rev() + s 就是我们的答案

        int len = s.length();
        int i = 0;
        // j 从后往前遍历
        for (int j = len - 1; j >= 0; j--) {
            if (s.charAt(i) == s.charAt(j)) {
                // 发现相等则 i 后移
                i++;
            }
        }

        // 到 s[0,i) 上从开头寻找最长回文子串，即考虑范围缩小
        if (i == len) {
            //本身就是回文串
            return s;
        }

        String remainString = s.substring(i);
        StringBuffer sb = new StringBuffer(remainString);
        String remainStrRev = sb.reverse().toString();
        // resultStr = 串1+串2+串3
        // 其中串2一定是回文串
        // 串1和串2镜像对称
        // 所以 resultStr 也是回文串
        return remainStrRev + solution2_official(s.substring(0, i)) + remainString;
    }

    // KMP 算法思想
    // 寻找 s 开头起的最长回文串，转变为寻找 s+s.rev 的最长前后缀匹配长度
    // 如：
    // s:         a a c e c a a | a
    // s.rev: a | a a c e c a a
    // 那就是 s + s.rev: a a c e c a a | a # a | a a c e c a a → 可以看到最大的前缀后缀匹配
    //
    // 假设字符串 b 的 next 数组用 f 表示，这里 f(i) 表示 b[0:i] 的最大前后缀匹配长度
    //   f(0) = 0
    //   for(i = 1; i < n; i++)
    //   {
    //  	 t = f(i-1)
    //  	 while(t > 0 && b[i] != b[t])
    //  		 t = f(t-1)
    //  	 if(b[i] == b[t]){
    //  		 ++t
    //  	 f(i) = t
    //  }
    public String solution3_official(String s) {
        StringBuffer sb = new StringBuffer(s);
        String sRev = sb.reverse().toString();

        int len = s.length();

        // '#'是为了区分连接处
        // 否则，对于s='aaaa'，newS='aaaaaaaa'，最长前后缀匹配长度就变成了 7
        // 应该 newS='aaaa#aaaa'，从而，最长前后缀的匹配长度为 4
        String newStr = s + "#" + sRev;
        int newLen = newStr.length();
        int[] f = new int[newLen];
        for (int i = 1; i < newLen; i++) {
            int t = f[i - 1];
            while (t > 0 && newStr.charAt(i) != newStr.charAt(t)) {
                t = f[t - 1];
            }
            if (newStr.charAt(i) == newStr.charAt(t)) {
                ++t;
            }
            f[i] = t;
        }
        return sRev.substring(0, len - f[newLen - 1]) + s;
    }
}
