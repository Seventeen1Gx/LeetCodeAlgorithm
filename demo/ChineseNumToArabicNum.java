// 字节跳动笔试题
// 中文数字转阿拉伯数字
//
// 例：三千亿零八十
//
// 有用的结论：
// 1. “零”不影响，可以删除
// 2. “十”到底是数字还是单位
// 3. “万”、“亿”是特殊节点

package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChineseNumToArabicNum {
    static Map<String, Integer> numMap = new HashMap<>();

    static {
        numMap.put("零", 0);
        numMap.put("一", 1);
        numMap.put("二", 2);
        numMap.put("三", 3);
        numMap.put("四", 4);
        numMap.put("五", 5);
        numMap.put("六", 6);
        numMap.put("七", 7);
        numMap.put("八", 8);
        numMap.put("九", 9);
    }

    static Map<String, Long> unitMap = new HashMap<>();

    static {
        unitMap.put("十", 10L);
        unitMap.put("百", 100L);
        unitMap.put("千", 1000L);
        unitMap.put("万", 10000L);
        unitMap.put("亿", 100000000L);
        unitMap.put("万亿", 1000000000000L);
    }

    static List<String> test = new ArrayList<>();

    static {
        test.add("一");
        test.add("十");
        test.add("一十五");
        test.add("十五");
        test.add("二十");
        test.add("二十三");
        test.add("一百");
        test.add("一百零一");
        test.add("一百一十");
        test.add("一百一十一");
        test.add("一千");
        test.add("三千零十五");
        test.add("一千零三十一");
        test.add("一万零一");
        test.add("一万零二十一");
        test.add("一万零三百二十一");
        test.add("一万一千三百二十一");
        test.add("三千零十五万");
        test.add("三千零一十五万");
        test.add("三千五百六十八万零一百零一");
        test.add("五十亿三千零七十五万零六百二十二");
        test.add("十三亿三千零十五万零三百一十二");
        test.add("三千零七十八亿三千零十五万零三百一十二");
        test.add("一千二百五十八亿");
        test.add("一千二百五十八万亿零三千三百二十一");
    }

    public static void main(String[] args) {
        for (int i = 0; i < test.size(); i++) {
            String toTest = test.get(i);
            System.out.println(toTest + "-------------" + convert(toTest));
        }
    }

    // 先考虑一种简单情况
    // 最高涉及单位为千，且每个单位都带一个数字
    // 注意，“十”前面的一省略的情况
    // 1234 → 一 千 二 百 三 十 四
    // 1 → 一
    // 10 → 一十
    // 17 → 一十七
    // 1001 → 一 千 零 一 → 一千一
    public static long baseConvert(String str) {
        long ret = 0;
        int i = 0;
        int num = 0;
        while (i < str.length()) {
            String temp = str.charAt(i++) + "";

            // 忽略字符串中的“零”
            if ("零".equals(temp)) {
                continue;
            }

            if (numMap.containsKey(temp)) {
                num = numMap.get(temp);
            } else if (unitMap.containsKey(temp)) {
                // 对于单位“十”特判一下
                // 它前面的“一”可能被省略了
                if ("十".equals(temp) && num == 0) {
                    num = 1;
                }
                ret += num * unitMap.get(temp);
                // 置零，表示消耗了，别被后面利用了
                num = 0;
            }
        }
        // 末尾没单位的数字
        ret += num;
        return ret;
    }

    public static long convert(String str) {
        // 按万亿、亿、万分割
        long ret = 0;
        String temp;

        int index = str.indexOf("万亿");
        if (index != -1) {
            temp = str.substring(0, index);
            ret += baseConvert(temp) * unitMap.get("万亿");
            str = str.substring(index + 2);
        }

        index = str.indexOf("亿");
        if (index != -1) {
            temp = str.substring(0, index);
            ret += baseConvert(temp) * unitMap.get("亿");
            str = str.substring(index + 1);
        }

        index = str.indexOf("万");
        if (index != -1) {
            temp = str.substring(0, index);
            ret += baseConvert(temp) * unitMap.get("万");
            str = str.substring(index + 1);
        }

        ret += baseConvert(str);
        return ret;
    }
}
