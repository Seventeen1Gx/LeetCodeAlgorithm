// 371. 两整数之和
//
// 不使用运算符 + 和 - ，计算两整数 a 、b 之和。


package src;

public class SumOfTwoIntegers {
    // Java中Int有32位
    private static final int INT_TOTAL_DIGITS = 32;

    // 转化成二进制数，然后用位运算
    public int solution1(int a, int b) {
        // 给出a、b的补码二进制形式
        String aBinStr = Integer.toBinaryString(a);
        String bBinStr = Integer.toBinaryString(b);

        String result = addBinary(aBinStr, bBinStr);

        // ansStr保存运算结果的补码形式
        if (result.length() > INT_TOTAL_DIGITS) {
            // 截断
            result = result.substring(1);
        }
        // 而parseInt只支持原码，符号要用'+'和'-'表示
        return Integer.parseInt(toUnsigned(result), 2);
    }

    private String addBinary(String a, String b) {
        StringBuilder ansStr = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        boolean carry = false;
        while (i >= 0 || j >= 0) {
            boolean bit1, bit2;
            bit1 = bit2 = false;
            if (i >= 0) {
                bit1 = a.charAt(i) == '1';
                i--;
            }
            if (j >= 0) {
                bit2 = b.charAt(j) == '1';
                j--;
            }

            // 注意加法器的公式
            boolean currentBit = bit1 ^ bit2 ^ carry;
            carry = bit1 & bit2 | carry & (bit1 ^ bit2);

            if (currentBit) {
                ansStr.append('1');
            } else {
                ansStr.append('0');
            }
        }

        if (carry) {
            ansStr.append('1');
        }

        return ansStr.reverse().toString();
    }

    // 字符串s为一个数的补码表示，转换成其原码表示
    private String toUnsigned(String s) {
        if (s.length()  == INT_TOTAL_DIGITS && s.charAt(0) == '1') {
            // s长为32位，且第一个字符为'1'，说明是负数的补码
            // 取反加1，获取对应原码
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '0') {
                    sb.append('1');
                } else {
                    sb.append('0');
                }
            }
            String result = addBinary(sb.toString(), "1");
            if (result.length() > INT_TOTAL_DIGITS) {
                result = result.substring(1);
            }
            return '-' + result;
        } else {
            // 整数的补码与原码形式相同
            return s;
        }
    }

    // 1. 相加各位的值，不算进位，即a^b
    // 2. 计算进位值，即(a&b)<<1
    // 3. 第一步和第二步的值再作加法操作，该加法操作继续用第1步和第2步的思路，直到最终进位为0
    public int solution2(int a, int b) {
        while(b != 0){
            int temp = a ^ b;
            b = (a & b) << 1;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        SumOfTwoIntegers s = new SumOfTwoIntegers();

        // System.out.println(s.solution1(1, 2));
        System.out.println(s.solution1(-14, 16));
        // s.solution1(-1, 1);
        // s.solution1(-12, -8);

        // 请注意为何parseInt("32个1", 2)报错
        // 因为参数中的字符串只能是原码，而不能是补码，32个1的原码值超过了32位补码所表示的最大值，故报错

    }
}
