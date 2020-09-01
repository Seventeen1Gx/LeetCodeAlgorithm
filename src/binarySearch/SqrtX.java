// 69. x 的平方根
//
// 计算并返回 x 的平方根，其中 x 是非负整数。
//
// 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。


package src.binarySearch;

public class SqrtX {
    public int solution1(int x) {
        // 注意溢出

        if (x == 0) {
            return 0;
        }

        if (x == 1 || x == 2 || x == 3) {
            return 1;
        }

        // 一个数的平方根不会超过其一半
        // 在 [2, x/2] 中二分查找
        int low = 2, high = x / 2;
        while (low <= high) {
            int mid = (low + high) / 2;
            double a = Math.pow(mid, 2);
            double b = Math.pow(mid + 1, 2);
            // 首先有a<b
            if (a <= x && b > x) {
                // a<=x<b
                return mid;
            } else if (b <= x) {
                // a<b<=x
                low = mid + 1;
            } else {
                // x<a<b
                high = mid - 1;
            }
        }
        return 0;
    }

    public int solution2(int a) {
        // 牛顿迭代法
        // x^2 - a = 0 的正实根就是我们要求的结果，a 是被求值
        // 即 y = x^2 - a 与 x 轴在大于 0 方向上的交点
        // 先在该双曲线的 (n, n^2 - a) 点处作切线，保证 n > ans 即可
        // 斜率为 2n，y=2nx+z，代入点，得到 z 为 -n^2-a，代入 y 等于 0，求得，与 x 轴的交点是 (n^2+a)/2n
        // 判断 (n^2+a)/2n 是否满足要求
        // 不满足的话，令 n 为 (n^2+a)/2n，继续作切线
        // ...
        // 直到满足精度要求

        // 从 n=a 那个点开始，不断逼近
        // 遇到得第一个 n*n <= a，就是我们所求的
        long n = a;
        while (n * n > a) {
            n = (n + a / n) / 2;
        }
        return (int) n;
    }

    public int solution3(int x) {
        if (x <= 1) {
            return x;
        }

        // 方法一的改进

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

    public int solution4(int x) {
        // 减治法
        if (x == 0) {
            return 0;
        }

        // [1, x/2] 中二分查找结果值
        // 结果值在递增的过程中，从偏小到偏大，中间有一处分界值
        // 于是我们找到小于等于分界值的最大结果

        int low = 1, high = x / 2;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            // [low,mid-1] [mid,high]

            int sqrt = x / mid;

            // 如果 mid 偏大，sqrt 偏小
            // 正确答案 mid<=sqrt

            if (mid <= sqrt) {
                // mid 偏小，将小的剔除，同时保留 mid，因为正确答案也在这个区间
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static double solution5(int x, int p) {
        // 带精度的减治法
        double low = 0, high = x / 2.0, mid;
        double precision = Math.pow(0.1, p);
        while (true) {
            mid = low + (high - low) / 2.0;
            double sqrt = x / mid;
            if (Math.abs(mid - sqrt) < precision) {
                return mid;
            }
            if (mid < sqrt) {
                // mid 偏小
                low = mid;
            } else {
                high = mid;
            }
        }
    }

    public static double solution6(int x, int p) {
        double n = x;
        double precision = Math.pow(0.1, p);
        while (Math.abs(x / n - x) > precision) {
            n = (n + x / n) / 2;
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println(String.format("%.2f", solution5(5, 2)));
    }
}