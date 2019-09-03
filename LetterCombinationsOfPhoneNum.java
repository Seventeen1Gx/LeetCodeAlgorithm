//16. 电话号码的字母组合
//
//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
//
//给出数字到字母的映射如下（与电话按键相同）
//
//2:abc 3:def 4:ghi 5:jkl 6:mno 7:pqrs 8:tuv 9:wxyz
//
//示例
//输入："23"
//输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"]. (不一定非以字典序输出)


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LetterCombinationsOfPhoneNum {
    private Map<String, String> map = new HashMap<>();

    //初始化(每次实例化当前类都会执行一次，静态子句初始化静态数据成员，且只在加载类时执行一次)
    {
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
    }


    //递归解法
    public List<String> solution1(String digits) {
        return recursive(new ArrayList<String>(), digits);
    }

    //将list中每个元素，添加digits表示的字母组合后形成新的list，并返回
    public List<String> recursive(List<String> list, String digits) {
        if (digits == null || digits.length() == 0)
            return list;

        //首元素
        String c = digits.charAt(0) + "";
        //首元素对应的字母
        String letters = map.get(c);

        if (list.size() == 0) {
            //将首元素对应的字母分别添加到list中
            for (int i = 0; i < letters.length(); i++) {
                list.add(letters.charAt(i) + "");
            }
        } else {
            List<String> newList = new ArrayList<>();
            for (String str : list) {
                for (int i = 0; i < letters.length(); i++) {
                    String newStr = str + letters.charAt(i);
                    newList.add(newStr);
                }
            }
            list = newList;
        }

        return recursive(list, digits.substring(1));
    }

    //发现上面的递归是尾递归，故转为非递归算法
    public List<String> solution2(String digits) {
        List<String> ansList = new ArrayList<>();

        if (digits == null || digits.length() == 0)
            return ansList;

        for (int i = 0; i < digits.length(); i++) {
            //当前处理第i个数字
            String c = digits.charAt(i) + "";
            //对应的字母
            String letters = map.get(c);

            if (ansList.size() == 0) {
                //将数字对应的字母分别添加到list中
                for (int j = 0; j < letters.length(); j++) {
                    ansList.add(letters.charAt(j) + "");
                }
            } else {
                List<String> newList = new ArrayList<>();
                for (String str : ansList) {
                    for (int j = 0; j < letters.length(); j++) {
                        String newStr = str + letters.charAt(j);
                        newList.add(newStr);
                    }
                }
                ansList = newList;
            }
        }
        return ansList;
    }
}
