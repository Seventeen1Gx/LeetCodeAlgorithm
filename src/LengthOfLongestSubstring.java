//3. 无重复字符的最长子串
//
//给定一个字符串，请你找出其中不含有重复字符的最长子串的长度。
//
//思路
//第一个想法是遍历所有子串--O(n^2)，看是不是无重复--O(n)，但可以从大到小查询每个字串，找到就返回，但这个最差时间复杂度为O(n^3)
//后来上网查询发现另一个叫滑动窗口的方法


package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {
    public int solution1(String s) {
        //窗口左右的下标
        int i = 0, j = 1;
        //最长无重复子串的长度至少为1
        int max = 1;
        int len = s.length();

        //空串要特别处理
        if (len == 0) {
            return 0;
        }

        while (i < len && j <= len) {
            String substr = s.substring(i, j);
            //该子串下一个字符是否已经出现
            if (j < len && substr.indexOf(s.charAt(j)) == -1) {
                //没出现时
                j++;
                max = max > j - i ? max : j - i;
            } else {
                i++;
                j = i + 1;
            }
        }
        return max;
    }

    //滑动窗口的官方答案，用了set
    public int solution2_official(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    //滑动窗口优化法
    //想想acbab，普通滑动窗口法若统计到第二个b的时候发现重复，则令i从第二个元素开始重新算，但又会发现b重复
    //在i从第三个元素开始，也一样，所以不如直接让i变为原来b位置的下一个元素
    public int solution3_official(String s) {
        int n = s.length(), ans = 0;
        //Map记录了每个不重复字符在原字符串中的位置
        Map<Character, Integer> map = new HashMap<>();
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            //j表示当前不重复子串的下一个元素位置
            //若下一个位置的元素已经重复，则i设置成已统计中被重复元素的下一个位置
            //也有一种情况，重复的其实已经排除在子串之前了，所以i保持不表
            if (map.containsKey(s.charAt(j))) {
                //当重复了，前面的都舍弃，令i从这个重复的元素的位置开始
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            //可能会覆盖已有，即重复元素记录后出现的下标
            //另一点，这里存j+1，是存的重复元素的下一个位置，便于赋值给i
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
