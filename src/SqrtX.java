//69. x的平方根
//
//计算并返回 x 的平方根，其中 x 是非负整数。
//
//由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。


package src;

public class SqrtX {
    //注意溢出
    public int solution1(int x) {
        if (x == 0)
            return 0;

        if (x == 1 || x == 2 || x == 3)
            return 1;

        //一个数的平方根不会超过其一半
        //在[2, x/2]中二分查找
        int low = 2, high = x / 2;
        while (low <= high) {
            int mid = (low + high) / 2;
            double a = Math.pow(mid, 2);
            double b = Math.pow(mid + 1, 2);
            //首先有a<b
            if (a <= x && b > x) {
                //a<=x<b
                return mid;
            } else if (b <= x) {
                //a<b<=x
                low = mid + 1;
            } else {
                //x<a<b
                high = mid - 1;
            }
        }
        return 0;
    }

    //牛顿迭代法(找到精确的平方根)
    //求根号a的近似值：首先随便猜一个近似值x，然后不断令 x 等于 x 和 a/x 的平均数
    //不断用(x, f(x))处的切线逼近曲线x^2-a与数轴的交点(根)
    //思路为f(x)≈f(x0)+f'(x0)(x-x0)，用一阶泰勒公式逼近函数
    public int solution2(int x) {
        //求得n^n = x
        long n = x;
        while (n * n > x) {
            n = (n + x / n) / 2;
        }
        return (int) n;
    }
}