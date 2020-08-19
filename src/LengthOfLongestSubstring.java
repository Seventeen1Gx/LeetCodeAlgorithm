// 3. 无重复字符的最长子串
//
// 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
//
// 思路
// 第一个想法是遍历所有子串 -- O(n^2)，看是不是无重复 -- O(n)，但可以从大到小查询每个字串，找到就返回，但这个最差时间复杂度为 O(n^3)
// 后来上网查询发现另一个叫滑动窗口的方法


package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {
    // 其实是暴力法，试验每个子串，只不过做了剪枝
    public int solution1(String s) {
        // i，j 确定子串 s[i:j)
        int i = 0, j = 1;
        // 最长无重复子串的长度至少为1
        int max = 1;
        int len = s.length();

        // 空串要特别处理
        if (len == 0) {
            return 0;
        }

        while (i < len && j <= len) {
            String substr = s.substring(i, j);
            // 该子串下一个字符是否已经出现
            if (j < len && substr.indexOf(s.charAt(j)) == -1) {
                // 没出现时，右边界继续右移
                j++;
                // 处理过程中，记录遇到的最大长度
                max = max > j - i ? max : j - i;
            } else {
                // 下一个字符已出现，切换下一个窗口
                i++;
                j = i + 1;
            }
        }
        return max;
    }

    // 跟方法 1 的切换窗口的思路不同
    public int solution1_official(String s) {
        int n = s.length();
        // set始终保存着s[i,j)中的字符
        Set<Character> set = new HashSet<>();
        // i，j 确定子串 s[i:j)
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
                // 目前已有 j 所指元素时，则移动左边界，直到这个重复元素被排出窗口
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    // 滑动窗口优化法
    // 想想 acbab，普通滑动窗口法若统计到第二个 b 的时候发现重复，则令 i 从第二个元素开始重新算，但又会发现 b 重复
    // 然后令 i 从第三个元素开始，也一样，所以不如直接让 i 变为第一个 b 元素的下一个位置
    public int solution2FromOfficial(String s) {
        int n = s.length(), ans = 0;
        // Map 记录了子串 s[i:j) 中的字符以及这些字符所处位置的下一位置
        Map<Character, Integer> map = new HashMap<>(s.length());
        // 窗口从空串开始
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                // 出现重复，令 i 从这个重复的元素的下一位置开始
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            // 存窗口中的字符以及该字符的下一个位置
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }


    public static void main(String[] args) {
        String s = "abcabcbb";
        LengthOfLongestSubstring l = new LengthOfLongestSubstring();

        int max = l.solution1(s);
    }
}
