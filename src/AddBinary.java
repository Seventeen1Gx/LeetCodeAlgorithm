//67. 二进制求和
//
//给定两个二进制字符串，返回他们的和（用二进制表示）。
//
//输入为非空字符串且只包含数字 1 和 0。


package src;

public class AddBinary {
    public String solution(String a, String b) {
        if (a == null || b == null)
            return null;

        int n = a.length();
        int m = b.length();

        StringBuffer sb = new StringBuffer();

        int i = n - 1, j = m - 1;
        int carry = 0;
        while (i >= 0 || j >= 0) {
            int num1 = 0, num2 = 0;
            if (i >= 0) {
                num1 = a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                num2 = b.charAt(j) - '0';
                j--;
            }
            int result = num1 + num2 + carry;
            if (result == 0) {
                sb.append(0);
                carry = 0;
            } else if (result == 1) {
                sb.append(1);
                carry = 0;
            } else if (result == 2) {
                sb.append(0);
                carry = 1;
            } else {
                //result == 3
                sb.append(1);
                carry = 1;
            }
        }
        if (carry == 1)
            sb.append(1);
        return sb.reverse().toString();
    }
}
