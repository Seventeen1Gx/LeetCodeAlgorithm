// 67. 二进制求和
//
// 给定两个二进制字符串，返回他们的和（用二进制表示）。
//
// 输入为非空字符串且只包含数字 1 和 0。


package src;

public class AddBinary {
    public String solution(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int num1, num2, carry = 0, result;
        StringBuilder sb = new StringBuilder();
        while (i >= 0 || j >= 0) {
            num1 = num2 = 0;
            if (i >= 0) {
                num1 = a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                num2 = b.charAt(j) - '0';
                j--;
            }
            result = num1 + num2 + carry;
            carry = result / 2;
            result = result % 2;
            sb.append(result);
        }
        if (carry == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }
}
