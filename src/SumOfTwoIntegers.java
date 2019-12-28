//371. 两整数之和
//
//不使用运算符 + 和 - ，计算两整数 a 、b 之和。


package src;

public class SumOfTwoIntegers {
    //Java中Int有32位
    private static final int INT_TOTAL_DIGITS = 32;

    //第一个想法
    //转化成二进制数，然后用位运算
    public int solution(int a, int b) {
        //给出a、b的补码二进制形式
        String aBinStr = Integer.toBinaryString(a);
        String bBinStr = Integer.toBinaryString(b);

        StringBuilder ansStr = new StringBuilder();

        int i = aBinStr.length() - 1;
        int j = bBinStr.length() - 1;
        boolean carry = false;
        while (i >= 0 || j >= 0) {
            boolean bit1, bit2;
            bit1 = bit2 = false;
            if (i >= 0 && aBinStr.charAt(i) == '1') {
                bit1 = true;
            }
            if (j >= 0 && bBinStr.charAt(j) == '1') {
                bit2 = true;
            }

            //注意加法器的公式
            boolean currentBit = bit1 ^ bit2 ^ carry;
            carry = bit1 & bit2 | carry & (bit1 ^ bit2);

            if (currentBit) {
                ansStr.append('1');
            } else {
                ansStr.append('0');
            }

            i--;
            j--;
        }

        if (carry) {
            ansStr.append('1');
        }

        ansStr.reverse();

        //ansStr保存运算结果的补码形式
        //而parseInt只支持源码

        if (ansStr.length() > INT_TOTAL_DIGITS) {
            //截断，且还可能需要转换成带符号的原码
            String newStr = ansStr.substring(1);
            if (newStr.charAt(0) == '1') {
                newStr = '-' + newStr.substring(1);
            }
            return Integer.parseInt(newStr, 2);
        } else {
            return Integer.parseInt(ansStr.toString(), 2);
        }
    }

    //1. 相加各位的值，不算进位，即a^b
    //2. 计算进位值，即(a&b)<<1
    //3. 第一步和第二步的值再作加法操作，该加法操作继续用第1步和第2步的思路，直到最终进位为0
    public int solution_official(int a, int b) {
        while(b != 0){
            int temp = a ^ b;
            b = (a & b) << 1;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        SumOfTwoIntegers s = new SumOfTwoIntegers();

        //s.solution(-1, 1);

        //请注意为何parseInt("32个1", 2)报错
        //因为参数中的字符串只能是原码，而不能是补码
        //负数应该表示成带负号的原码形式，比如"-11"解析成-3
        s.solution(-12, -8);
    }
}
