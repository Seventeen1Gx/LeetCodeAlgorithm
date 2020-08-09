package src;

import java.util.ArrayList;
import java.util.List;

/**
 * 93. 复原ip地址
 *
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 *
 * 注：ip 地址分为 4 个部分，每个部分范围为 0~255
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class RestoreIpAddresses {
    /**
     * 回溯法
     */
    public List<String> solution(String s) {
        if (s.length() > UP * REQUIRE_NUM || s.length() < LOW * REQUIRE_NUM) {
            return results;
        }
        callback(s, 0, 0, "");
        return results;
    }

    // ip 地址每个部分的字符串长度的上下界
    private static final int UP = 3;
    private static final int LOW = 1;
    // ip 地址共 4 个部分
    private static final int REQUIRE_NUM = 4;
    // ip地址每个部分数值范围的上下界
    private static final int UP_VALUE = 255;
    private static final int LOW_VALUE = 0;


    private List<String> results = new ArrayList<>();

    /**
     * @param s 整个数字字符串
     * @param start 当前开始考虑的位置
     * @param cnt 已经获取的 ip 地址的部分数
     * @param result 已经获取的 ip 地址的部分数的字符串表示
     */
    private void callback(String s, int start, int cnt, String result) {
        // s 串还剩未考虑的字符数
        int remainChars = s.length() - start;

        if (remainChars == 0 && cnt == REQUIRE_NUM) {
            // s 串已处理完，且已获得 ip 地址的 4个部分
            // 说明 sb 是一个满足要求的 ip 地址的字符串表示
            results.add(result);
            return;
        }

        // s 串未处理的字符数已经不能满足 ip 地址构造的需求
        if (remainChars > (REQUIRE_NUM - cnt) * UP || remainChars < (REQUIRE_NUM - cnt) * LOW) {
            return;
        }

        int num;
        String subString;
        // 试验 3、2、1 位字符转成 ip 地址的一部分
        for (int charNum = LOW; charNum <= UP; charNum++) {
            if (remainChars >= charNum) {
                subString = s.substring(start, start + charNum);
                if (charNum != LOW && subString.charAt(0) == '0') {
                    // "010" 不能作为 ip 地址的一部分
                    // 即不允许前导 0
                    break;
                }

                num = Integer.parseInt(subString);
                if (num >= LOW_VALUE && num <= UP_VALUE) {
                    String newString = result + subString;
                    if (cnt < 3) {
                        newString = newString + '.';
                    }
                    callback(s, start + charNum, cnt + 1, newString);
                }
            }
        }
    }
}
