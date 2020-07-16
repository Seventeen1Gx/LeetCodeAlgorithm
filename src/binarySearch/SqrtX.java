//69. x的平方根
//
//计算并返回 x 的平方根，其中 x 是非负整数。
//
//由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。


package src.binarySearch;

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

    // 方法一的改进
    public int solution3(int x) {
        if (x <= 1)
            return x;

        // [l:h] 中寻找目标
        int l = 1, h = x;
        while (l <= h) {
            int mid = l + (h - l) / 2;
            // 用除法而不用乘法，防止溢出
            int sqrt = x / mid;
            if (sqrt == mid) {
                return mid;
            } else if (mid > sqrt) {
                // 需要缩小
                h = mid - 1;
            } else {
                // 需要放大
                l = mid + 1;
            }
        }
        // 返回 l 和 h 的较小值
        return h;
    }

    // 减治法
    public int solution4(int x) {
        if (x == 0)
            return 0;

        // [1, x/2] 中二分查找
        int low = 1, high = x / 2;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            // [low,mid-1] [mid,high]

            // mid*mid==x，则[low,mid-1] 被排除
            // mid*mid>x，则 mid 太大，[mid,high] 被排除
            // mid*mid<x，则 mid 太小，[low,mid-1] 被排除
            // 又由于乘法容易溢出，转成除法
            int sqrt = x / mid;
            if (mid <= sqrt)
                low = mid;
            else
                high = mid - 1;
        }
        return low;
    }
}