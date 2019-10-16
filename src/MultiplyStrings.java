//43. 字符串相乘
//
//给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
//
//说明：
//num1 和 num2 的长度小于110。
//num1 和 num2 只包含数字 0-9。
//num1 和 num2 均不以零开头，除非是数字 0 本身。
//不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
//
//
//根据之前做过的字符串间的数学运算，这里想到平时笔算时多位数相乘的方式来编写我们的代码


package src;

public class MultiplyStrings {
    public String solution(String num1, String num2) {
        if (num1 == null && num2 == null)
            return "";
        if (num1 == null)
            return num2;
        if (num2 == null)
            return num1;

        int n1 = num1.length();
        int n2 = num2.length();

        if (n1 == 0 && n2 == 0)
            return "";
        if (n1 == 0)
            return num2;
        if (n2 == 0)
            return num1;

        if (num1.equals("0") || num2.equals("0"))
            return "0";

        //获取num1的倍数，时间复杂度O(n1)
        String[] multiplyStrs = multiplyStrings(num1);

        String multiplyStr;
        String result = "0";
        StringBuilder zero = new StringBuilder();
        //从后往前遍历num2的每位，累加每位相乘结果
        for (int i = n2 - 1; i >= 0; i--) {
            multiplyStr = multiplyStrs[num2.charAt(i) - '0'];
            result = addStrings(result, multiplyStr + zero.toString());
            zero.append('0');
        }
        return result;
    }

    //返回一个数组，数组中含num*0、num*1、num*2、num*3、...、num*9
    public String[] multiplyStrings(String num) {
        String[] strs = new String[10];
        strs[0] = "0";
        for (int i = 1; i < 10; i++) {
            strs[i] = addStrings(strs[i - 1], num);
        }
        return strs;
    }

    //字符串相加，时间复杂度O(max(n1, n2))
    public String addStrings(String num1, String num2) {
        //令num1长度大于num2长度
        if (num1.length() < num2.length())
            return addStrings(num2, num1);

        int n1 = num1.length();
        int n2 = num2.length();

        int carry = 0, byteResult;

        StringBuffer strResult = new StringBuffer();
        //从字符串后往前处理
        int i, j;
        for (i = n1 - 1, j = n2 - 1; i >= 0; i--, j--) {
            if (j >= 0) {
                byteResult = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + carry;
            } else {
                byteResult = num1.charAt(i) - '0' + carry;
            }
            if (byteResult >= 10) {
                carry = 1;
                byteResult %= 10;
            } else {
                carry = 0;
            }
            //结果反序存在StringBuffer中
            strResult.append(byteResult);
        }

        //注意最后可能还剩下1
        if (carry == 1)
            strResult.append(1);

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