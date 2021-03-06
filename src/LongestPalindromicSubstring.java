// 5. 最长回文子串
//
// 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
//
// 还有一种 “马拉车” 算法，可以做到 O(n) 时间复杂度，这里不讨论


package src;

public class LongestPalindromicSubstring {
    public String solution1(String s) {
        // 中心探寻法 -- 可以在每个字符两边添加 #，如 aba→#a#b#a#，从而实现奇偶遍历统一，结果再删除 #

        //注意空指针
        if (s == null) {
            return "";
        }

        int len = s.length();

        // 注意空串
        if (len == 0) {
            return "";
        }

        // 结果
        int lenMax = 1, jAns = 0, kAns = 0;

        int j, k, l, lenTemp;

        // 奇数长度
        for (int i = 0; i < len; i++) {
            l = 1;
            lenTemp = 1;
            while (true) {
                // 从中心向两边扩散
                j = i - l;
                k = i + l;

                // 超限退出
                if (j < 0 || k > len - 1) {
                    break;
                }

                if (s.charAt(j) == s.charAt(k)) {
                    lenTemp += 2;
                    if (lenTemp > lenMax) {
                        lenMax = lenTemp;
                        jAns = j;
                        kAns = k;
                    }
                } else {
                    break;
                }
                l++;
            }
        }

        // 偶数长度
        for (int i = 0; i < len - 1; i++) {
            l = 0;
            lenTemp = 0;
            while (true) {
                j = i - l;
                k = i + 1 + l;

                // 超限退出
                if (j < 0 || k > len - 1) {
                    break;
                }

                if (s.charAt(j) == s.charAt(k)) {
                    lenTemp += 2;
                    if (lenTemp > lenMax) {
                        lenMax = lenTemp;
                        jAns = j;
                        kAns = k;
                    }
                } else {
                    break;
                }
                l++;
            }
        }

        return s.substring(jAns, kAns + 1);
    }

    public String solution2(String s) {
        // 动态规划法，子串长度分奇偶，dp[i,j] 表示开头 i 结尾 j 的子串的最长回文子串长度

        // 注意空指针
        if (s == null) {
            return "";
        }

        int len = s.length();

        // 注意空串
        if (len == 0) {
            return "";
        }

        int[][] dp = new int[len][len];

        // 奇数情况初始化
        for (int i = 0; i < len; i++) {
            // 长度为 1 的子串的最大回文子串长度
            dp[i][i] = 1;
        }

        // 结果
        int lenMax = 1, jAns = 0, kAns = 0;

        int j, k, l;

        for (int i = 0; i < len; i++) {
            // 从中心 i 向两边扩散
            l = 1;
            while (true) {
                j = i - l;
                k = i + l;
                if (j < 0 || k > len - 1) {
                    // 讨论范围越界，换下一个 i
                    break;
                }
                if (s.charAt(j) == s.charAt(k)) {
                    dp[j][k] = dp[j + 1][k - 1] + 2;
                    // 记录搜索过程中最长的回文子串
                    if (dp[j][k] > lenMax) {
                        lenMax = dp[j][k];
                        jAns = j;
                        kAns = k;
                    }
                } else {
                    // 此子串和往后的串都不是回文串
                    break;
                }
                l++;
            }
        }

        // 偶数情况初始化
        for (int i = 0; i < len - 1; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                dp[i][i + 1] = 2;
                // 注意这里也要判断
                if (dp[i][i + 1] > lenMax) {
                    lenMax = dp[i][i + 1];
                    jAns = i;
                    kAns = i + 1;
                }
            } else {
                dp[i][i + 1] = 0;
            }
        }

        for (int i = 0; i < len - 1; i++) {
            // 从中心 i 与 i+1 向两边扩散
            if (dp[i][i + 1] == 0) {
                continue;
            }
            l = 1;
            while (true) {
                j = i - l;
                k = i + 1 + l;
                if (j < 0 || k > len - 1) {
                    // 讨论范围越界，换下一个i
                    break;
                }
                if (s.charAt(j) == s.charAt(k)) {
                    dp[j][k] = dp[j + 1][k - 1] + 2;
                    // 记录搜索过程中最长的回文子串
                    if (dp[j][k] > lenMax) {
                        lenMax = dp[j][k];
                        jAns = j;
                        kAns = k;
                    }
                } else {
                    // 此子串和往后的串都不是回文串
                    break;
                }
                l++;
            }
        }

        return s.substring(jAns, kAns + 1);
    }

    public static void main(String[] args) {
        String s = "cbbd";

        LongestPalindromicSubstring l = new LongestPalindromicSubstring();

        System.out.println(l.solution2(s));
    }
}
