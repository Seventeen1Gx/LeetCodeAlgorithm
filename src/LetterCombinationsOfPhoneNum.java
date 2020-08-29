// 16. 电话号码的字母组合
//
// 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
//
// 给出数字到字母的映射如下（与电话按键相同）
//
// 2:abc 3:def 4:ghi 5:jkl 6:mno 7:pqrs 8:tuv 9:wxyz
//
// 示例
// 输入："23"
// 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]. (不一定非以字典序输出)


package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfPhoneNum {
    private Map<Character, String> map = new HashMap<>();

    {
        map.put('2', "abc");
        map.put('3', "def");
        map.put('4', "ghi");
        map.put('5', "jkl");
        map.put('6', "mno");
        map.put('7', "pqrs");
        map.put('8', "tuv");
        map.put('9', "wxyz");
    }


    List<String> ret = new ArrayList<>();
    StringBuilder sb = new StringBuilder();

    public List<String> solution(String digits) {
        if (digits != null && digits.length() > 0) {
            dfs(digits, 0);
        }
        return ret;
    }

    private void dfs(String digits, int i) {
        if (i == digits.length()) {
            ret.add(sb.toString());
            return;
        }
        char c = digits.charAt(i);
        String sub = map.get(c);
        for (int j = 0; j < sub.length(); j++) {
            sb.append(sub.charAt(j));
            dfs(digits, i + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

}
