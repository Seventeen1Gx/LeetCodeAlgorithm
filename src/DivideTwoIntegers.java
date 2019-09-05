//29. 两数相除
//
//给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
//
//返回被除数 dividend 除以除数 divisor 得到的商。
//
//说明:
//
//被除数和除数均为 32 位有符号整数。
//除数不为 0。
//假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2^31,  2^31 − 1]。本题中，如果除法结果溢出，则返回 2^31 − 1。


package src;

public class DivideTwoIntegers {
    //利用减法代替除法(速度过慢)
    //因为是整数相除，而返回结果也是整数，所以理解为整除
    int solution1(int dividend, int divisor) {
        int ans;

        //只有int最小值除以-1时才会溢出
        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;

        if (dividend == 0)
            return 0;

        //避免在后面的负数转整数中出现溢出
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE)
            return 1;

        if (divisor == Integer.MIN_VALUE)
            return 0;

        if (divisor == 1)
            return dividend;

        if (divisor == -1)
            return -dividend;

        if (dividend == Integer.MIN_VALUE) {
            if (divisor > 0)
                dividend += divisor;
            else
                dividend -= divisor;
            ans = 1;
        } else {
            ans = 0;
        }

        //结果的符号
        boolean sign = true;
        if (dividend < 0 && divisor > 0)
            sign = !sign;
        else if (dividend > 0 && divisor < 0)
            sign = !sign;


        //转化成正数开始计算(而这里可能会产生溢出比如-Integer.MIN_VALVE，所以在之前进行了排查，这会已经不会溢出)
        if (dividend < 0)
            dividend = -dividend;
        if (divisor < 0)
            divisor = -divisor;


        while (dividend >= divisor) {
            dividend -= divisor;
            ans++;
        }

        if (!sign)
            ans = -ans;

        return ans;
    }


    //利用竖式除法
    int solution2(int dividend, int divisor) {
        //排除特殊情况
        if (dividend == 0)
            return 0;

        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;

        //因为有负数转正数的操作会导致溢出
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE)
            return 1;

        if (divisor == Integer.MIN_VALUE)
            return 0;

        int carry = 0;
        //事先先'减'一次
        if (dividend == Integer.MIN_VALUE)
            if (divisor > 0)
                dividend += divisor;
            else if (divisor < 0)
                dividend -= divisor;
    }

    public static void main(String[] args) {
        DivideTwoIntegers d = new DivideTwoIntegers();
        d.solution1(-2147483648, 1);
    }
}
