// 69. x 的平方根
//
// 计算并返回 x 的平方根，其中 x 是非负整数。
//
// 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。


package src.binarySearch;

public class SqrtX {
    public int solution(int x) {
        // 注意溢出
        if (x == 0) {
            return 0;
        }

        if (x == 1 || x == 2 || x == 3) {
            return 1;
        }

        // 一个数的平方根不会超过其一半
        // 在 [2, x/2] 中二分查找
        // [2, i] [i+1, x/2]   i 是答案
        // mid 在第一个区间时，mid * mid <= x
        // mid 在第二个区间时，mid * mid > x
        long low = 2, high = x / 2;
        while (low < high) {
            long mid = (low + high + 1) / 2;
            if (mid * mid <= x) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return (int)low;
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
        if (x == 0) {
            return 0;
        }

        int low = 1, high = x / 2;
        while (low < high) {
            int mid = low + (high - low + 1) / 2;
            // 防溢出
            int sqrt = x / mid;

            if (mid <= sqrt) {
                // mid 偏小，将小的剔除，同时保留 mid
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static double solution4(int x, int p) {
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

    public static double solution5(int x, int p) {
        double n = x;
        double precision = Math.pow(0.1, p);
        while (Math.abs(x / n - x) > precision) {
            n = (n + x / n) / 2;
        }
        return n;
    }
}