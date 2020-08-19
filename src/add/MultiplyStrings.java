// 43. 字符串相乘
//
// 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
//
// 说明：
// - num1 和 num2 的长度小于110。
// - num1 和 num2 只包含数字 0-9。
// - num1 和 num2 均不以零开头，除非是数字 0 本身。
// - 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。


package src.add;

public class MultiplyStrings {
    private static final String ZERO = "0";

    // 模拟两数相乘的竖式笔算过程
    public String solution(String num1, String num2) {
        if (ZERO.equals(num1) || ZERO.equals(num2)) {
            return ZERO;
        }

        String[] multiplyStrs = multiplyStrings(num1);

        String multiplyStr;
        String result = "0";
        StringBuilder zero = new StringBuilder();
        // 从后往前遍历num2的每位，累加每位相乘num1的结果
        for (int i = num2.length() - 1; i >= 0; i--) {
            multiplyStr = multiplyStrs[num2.charAt(i) - '0'];
            result = addStrings(result, multiplyStr + zero.toString());
            zero.append('0');
        }
        return result;
    }

    // 返回一个数组，数组中含num*0、num*1、num*2、num*3、...、num*9
    private String[] multiplyStrings(String num) {
        String[] strs = new String[10];
        strs[0] = "0";
        for (int i = 1; i < strs.length; i++) {
            strs[i] = addStrings(strs[i - 1], num);
        }
        return strs;
    }

    // 字符串相加，时间复杂度O(max(n1, n2))
    private String addStrings(String num1, String num2) {
        int i = num1.length() - 1;
        int j = num2.length() - 1;

        int n1, n2, carry = 0, result;

        StringBuilder strResult = new StringBuilder();
        while (i >= 0 || j >= 0) {
            n1 = n2 = 0;
            if (i >= 0) {
                n1 = num1.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                n2 = num2.charAt(j) - '0';
                j--;
            }
            result = n1 + n2 + carry;
            carry = result / 10;
            result = result % 10;

            strResult.append(result);
        }

        // 注意最后可能还剩下1
        if (carry > 0) {
            strResult.append(1);
        }

        return strResult.reverse().toString();
    }

    public static void main(String[] args) {
        MultiplyStrings m = new MultiplyStrings();
        m.solution("2", "3");
        m.solution("123", "456");
        m.solution("0", "0");
        m.solution("0", "52");
    }
}