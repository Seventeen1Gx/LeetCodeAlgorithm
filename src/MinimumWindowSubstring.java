//76. 最小覆盖子串
//
//给你一个字符串 S、一个字符串 T，请在字符串 S 里面找出：包含 T 所有字母的最小子串。
//
//说明：
//如果 S 中不存这样的子串，则返回空字符串 ""。
//如果 S 中存在这样的子串，我们保证它是唯一的答案。
//
//注意
//如果t中有n个重复的字母，则覆盖子串中也需要有n个重复的字母


package src;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    //暴力法--超时
    public String solution1(String s, String t) {
        //覆盖子串至少要与t串一样长
        if (s.length() < t.length())
            return "";

        //按子串长度从小到大开始遍历
        for (int len = t.length(); len <= s.length(); len++) {
            //遍历子串开头
            for (int i = 0; i < s.length() && i + len <= s.length(); i++) {
                String subStr = s.substring(i, i + len);
                if (isCover(subStr, t)) {
                    return subStr;
                }
            }
        }
        return "";
    }

    //判断s能都覆盖t
    private boolean isCover(String s, String t) {
        for (char c : t.toCharArray()) {
            //c第一次在s中出现的位置
            int index = s.indexOf(c);
            if (index < 0) {
                return false;
            } else {
                //一个字符只能用一次
                s = s.substring(0, index) + s.substring(index + 1);
            }
        }
        return true;
    }

    //滑动窗口，通过计数来判断是否覆盖
    public String solution2(String s, String t) {
        if (s.length() == 0 || t.length() == 0)
            return "";

        //统计t中每个字符出现的次数
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : t.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        //维护一个最小覆盖子串和其长度
        String minWindows = "";
        int minWindowsLen = s.length() + 1;

        int start = 0, end = 0;
        while (end < s.length()) {
            //寻求s[start:end]以覆盖t，现需右移的是end
            char c = s.charAt(end);
            if (map.containsKey(c)) {
                //c可以用来覆盖t中的一个字符
                map.put(c, map.get(c) - 1);
                if (judge(map)) {
                    //现s[start:end]可以覆盖t ①
                    //然后右移start，直到s[start:end]不能覆盖t
                    //即将开头多余的字符去除
                    //要么start所指字符不可以用来覆盖t
                    //要么start所指字符可以用来覆盖t，但有富余
                    char d = s.charAt(start);
                    while (!map.containsKey(d) || map.get(d) < 0) {
                        if (map.containsKey(d))
                            map.put(d, map.get(d) + 1);
                        start++;
                        d = s.charAt(start);
                    }
                    //现s[start:end]可以覆盖t，且长度小于等于①处的长度
                    if (end - start + 1 < minWindowsLen) {
                        minWindowsLen = end - start + 1;
                        minWindows = s.substring(start, end + 1);
                    }
                    //start右移一格，使s[start:end]不再覆盖t，下次开始右移end
                    start++;
                    map.put(d, map.get(d) + 1);
                }
            }
            end++;
        }
        return minWindows;
    }

    //判断map中的每个字符是否都可以被覆盖完，甚至还有富余都没事
    private boolean judge(Map<Character, Integer> map) {
        for (int num : map.values()) {
            if (num > 0)
                return false;
        }
        return true;
    }

    //方法二start右移时还可以优化，可以直接右移到s中包含在t内的字符中的第一个即可，而不用一个一个得右移

    public static void main(String[] args) {
        MinimumWindowSubstring m = new MinimumWindowSubstring();
        m.solution2("ADOBECODEBANC", "ABC");
    }
}
