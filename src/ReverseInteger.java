//7. 整数反转
//
//给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
//注意：
//假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−2^31,  2^31 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
// → 用long型保存结果，最后强制转化成int型，看是否变化


package src;

public class ReverseInteger {
    //需计算整数长度
    public int solution1(int x) {
        if (x == 0) {
            return x;
        }

        //判断是否有符号
        boolean isSigned = false;
        if (x < 0) {
            x = -x;
            isSigned = true;
        }

        //判断整数位数
        int xTemp = x, len = 0;
        while (xTemp > 0) {
            //截去当前整数的末位
            xTemp = xTemp / 10;
            len++;
        }

        long ans = 0;
        long factor = (long) Math.pow(10, len - 1);
        int n;
        while (x > 0) {
            //取末位数字
            n = x % 10;
            ans += n * factor;
            //下一位
            x = x / 10;
            factor = factor / 10;
        }

        if (isSigned) {
            ans = -ans;
        }

        if ((int) ans != ans) {
            return 0;
        }

        return (int) ans;
    }

    //无需计算整数长度
    public int solution2(int x) {
        if (x == 0) {
            return x;
        }

        //判断是否有符号
        boolean isSigned = false;
        if (x < 0) {
            x = -x;
            isSigned = true;
        }

        long ans = 0;
        int n;
        while (x > 0) {
            //取末位数字
            n = x % 10;
            x = x / 10;
            //之前得到的结果前移一位，然后加上n
            ans = ans * 10 + n;
        }

        if (isSigned) {
            ans = -ans;
        }

        if ((int) ans != ans) {
            return 0;
        }

        return (int) ans;
    }

    //官方解法，在solution2的基础上，加入了更合理的溢出判断
    //注意 -123%10=-3
    public int solution2_official(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            //在进行这步之前，注意可能溢出，所有在这之前有关于溢出的判断
            rev = rev * 10 + pop;
        }
        return rev;
    }

    //还有一种方法，将数字转换成字符串，反转，再转换回来

    public static void main(String[] args) {
        int x = 1534236469;

        ReverseInteger r = new ReverseInteger();

        r.solution1(x);
    }
}
