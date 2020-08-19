// 49. 字母异位词分组
//
// 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
//
// 说明：
// 所有输入均为小写字母。
// 不考虑答案输出的顺序。
//
// 注意：字母异位词必须长度相等，且包含的字母相同，每个字母出现的次数也相同，boo 和 bob 不是字母异位词


package src;

import java.util.*;

public class GroupAnagrams {
    public List<List<String>> solution1(String[] strs) {
        if (strs == null)
            return null;

        List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < strs.length; i++) {
            if (result.size() == 0) {
                List<String> list = new ArrayList<>();
                list.add(strs[i]);
                result.add(list);
            } else {
                //看strs[i]属于哪个列表
                boolean flag = false;
                for (int j = 0; j < result.size(); j++) {
                    String tmp = result.get(j).get(0);
                    if (judge(strs[i], tmp)) {
                        result.get(j).add(strs[i]);
                        flag = true;
                        break;
                    }
                }
                //strs[i]不属于任何一个列表
                if (!flag) {
                    List<String> list = new ArrayList<>();
                    list.add(strs[i]);
                    result.add(list);
                }
            }

        }
        return result;
    }

    // 判断两字符串是否为字母异位词(包含相同的字母，且每个字母出现的次数相同)
    // 这样判断有点慢
    public boolean judge(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        for (int i = 0; i < s2.length(); i++) {
            char c = s2.charAt(i);
            // 字母 c 在 s1 中第一次出现的位置
            int index = s1.indexOf(c);
            if (index < 0)
                return false;
            // 消去 s1 中第一次出现的 c
            s1 = s1.substring(0, index) + s1.substring(index + 1);
        }
        // 到这里说明上面稳定每次消去一个 s1 中存在的字母
        return true;
    }

    // 下面的方法都不是像上面那样两两判断，而是每个词和一个标准判断
    // HashMap 键的设计

    // 官方解法
    // 将每次字符串按字母序进行排列，异位词的排序结果相同
    public List<List<String>> solution2_official(String[] strs) {
        if (strs.length == 0) return new ArrayList();

        // 键为按字母序排好的串，值为与键为异位词的字符串的列表
        Map<String, List> ans = new HashMap<String, List>();
        for (String s : strs) {
            // 将 s 按字母排序
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            // 判断 s 应该属于哪一个列表
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }

    // 官方解法
    // 计数解法，对于异位词，它当中的每次字母的出现次数都相同
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList();

        // 这里的String记录了26个字母每个字母出现的次数，互为异位词的该键值是相同的
        Map<String, List> ans = new HashMap<String, List>();

        int[] count = new int[26];
        for (String s : strs) {
            // 记录每次字母出现的次数
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) count[c - 'a']++;

            // 转化为字符串(因为数组不能当成键)
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#');
                sb.append(count[i]);
            }
            // 判断每次字符串属于哪个列表
            String key = sb.toString();
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }

    public static void main(String[] args) {
        String[] strings = new String[]{"tea", "", "eat", "", "tea", ""};
        GroupAnagrams g = new GroupAnagrams();
        g.solution1(strings);
    }
}
