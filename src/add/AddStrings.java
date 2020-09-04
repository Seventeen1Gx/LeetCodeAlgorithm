// 415. 字符串相加
//
// 给定两个字符串形式的非负整数 num1 和 num2 ，计算它们的和。
//
// 注意：
// num1 和 num2 的长度都小于 5100.
// num1 和 num2 都只包含数字 0-9.
// num1 和 num2 都不包含任何前导零。
// 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。


package src.add;

public class AddStrings {
    public String solution(String num1, String num2) {
        int i = num1.length() - 1, j = num2.length() - 1;

        StringBuilder sb = new StringBuilder();

        int bit1, bit2, carry = 0, bitResult;
        while (i >= 0 || j >= 0) {
            bit1 = bit2 = 0;
            if (i >= 0) {
                bit1 = num1.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                bit2 = num2.charAt(j) - '0';
                j--;
            }
            bitResult = bit1 + bit2 + carry;
            carry = bitResult / 10;
            bitResult %= 10;

            sb.append(bitResult);
        }

        if (carry == 1) {
            sb.append(1);
        }

        return sb.reverse().toString();
    }
}