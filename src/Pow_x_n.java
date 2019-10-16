//50. Pow(x, n)
//
//实现 pow(x, n) ，即计算 x 的 n 次幂函数。
//
//说明:
//
//-100.0 < x < 100.0
//n 是 32 位有符号整数，其数值范围是 [−2^31, 2^31 − 1] 。
//
//注意溢出的问题


package src;

public class Pow_x_n {
    //首先一个一个地乘，会超时
    //我们采用“二分法”
    //pow(x,n) = pow(x, n/2) * pow(x, n/2)
    public double solution(double x, int n) {
        if (Math.abs(x) < 1e-6)
            return 0.0;

        if (n == 0)
            return 1.0;

        double result;
        //当n==Integer.MIN_VALUE时，n=-n会溢出
        if (n == Integer.MIN_VALUE)
            result = 1.0 / (myPow(x, Integer.MAX_VALUE) * x);
        else if (n > 0)
            result = myPow(x, n);
        else
            result = 1.0 / myPow(x, -n);

        return result;
    }

    //对于溢出和负指数有更好的处理方式
    //用更大范围的类型存储和使x变为1/
    //    long N = n;
    //    if (n < 0) {
    //    x = 1.0 / x;
    //    N = -n;
    //    }

    //这里确保x!=0，n>0
    public double myPow(double x, int n) {
        if (n == 1)
            return x;

        double t = myPow(x, n / 2);

        if (n % 2 == 0)
            return t * t;
        return t * t * x;
    }

    //还有一种思路是x^(a+b) = x^a * x^b，将n当成多个数之和，分别计算以提高速度--快速幂
}
