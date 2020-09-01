// 201. 数字范围按位与
//
// 给定范围 [m, n]，其中 0 <= m <= n <= 2147483647，返回此范围内所有数字的按位与（包含 m, n 两端点）。


package src;

public class BitwiseAndOfNumbersRange {
    public int rangeBitwiseAnd(int m, int n) {
        // 与运算，有 0 得 0，无 0 得 1
        // 观察所有数字二进制相与
        // 结果为所有数字的公共前缀+右边补零
        // 证明：假如这些数字前 i 位相同，从 i+1 位开始不相同
        // 由于 [m,n] 连续，所以第 i+1 位在 [m,n] 的数字范围从小到大列举出来一定是前面全部是 0，后面是 1
        // 即公共前缀的后面的位相与肯定都为 0

        int shift = 0;
        while (m < n) {
            // 右移找到公共前缀
            // 退出循环的条件是 m==n
            n >>= 1;
            m >>= 1;
            shift++;
        }
        // 末尾补 0
        return n << shift;
    }

    public int brianKernighan(int m, int n) {
        // n&(n-1) 清除二进制串中最右边的 1
        // xxx1000 减 1 变为 xxx0111，与原 xxx1000 相与，得到 xxx0000，即最右边的 1 被消除

        // 利用这个方法找公共前缀
        while (m < n) {
            // n >= m 时，n 就是结果
            n &= n - 1;
        }
        return n;
    }
}
