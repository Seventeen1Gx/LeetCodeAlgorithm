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
        //事先先'减'一次(不会出现不够减的情况)
        if (dividend == Integer.MIN_VALUE) {
            if (divisor > 0)
                dividend += divisor;
            else if (divisor < 0)
                dividend -= divisor;
            carry++;
        }

        //负数转正数，并记录符号(默认为正数)
        boolean signed1 = true, signed2 = true;
        if (dividend < 0) {
            dividend = -dividend;
            signed1 = false;
        }
        if (divisor < 0) {
            divisor = -divisor;
            signed2 = false;
        }

        //被除数长度
        int x = dividend, len = 0;
        while (x > 0) {
            x /= 10;
            len++;
        }

        //采用竖式除法运算(维护结果，与上次计算残留)
        int ans = 0, bit = 0;
        //遍历被除数的第一位数字到最后一位数字
        for (int i = 1; i <= len; i++) {
            //取被除数的一位数字，然后算上上次计算的残留
            bit = bit * 10 + getKthNum(dividend, len, i);
            //商的第一位
            ans = ans * 10 + bit / divisor;
            //残留
            bit = bit % divisor;
        }
        //符号位异或运算，同假异真
        if (signed1 ^ signed2)
            return -(ans + carry);
        else
            return ans + carry;
    }

    //取长度len的数字n的第k个数字(k从1开始计数)
    public int getKthNum(int n, int len, int k) {
        if (k > len || k < 0)
            return -1;

        //注意这里可能产生溢出，假如n是Integer.MAX_VALUE，取第1位，那么before势必溢出
        //故加个判断
        if (len == 10 && k == 1)
            return n / (int) Math.pow(10, len - 1);

        //用来去除n在k之前的数字
        int before = (int) Math.pow(10, len - k + 1);
        //用来去除n在k之后的数字
        int after = (int) Math.pow(10, len - k);

        return (n % before) / after;
    }

    //还有一种每次倍增除数，即除数+除数，直到结果接近被除数

    public static void main(String[] args) {
        //静态方法不能访问非静态方法和非静态域，所以要用对象来调用非静态方法
        DivideTwoIntegers d = new DivideTwoIntegers();
        d.solution2(2147483647, 1);
    }
}
