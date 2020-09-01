// 461. 汉明距离
//
// 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
//
// 给出两个整数 x 和 y，计算它们之间的汉明距离。


package src;

public class HammingDistance {
    public int hammingDistance(int x, int y) {
        // 先利用异或操作：同 0 异 1，得到哪些位数不同
        // 然后统计结果中的 1 的个数
        int result = x ^ y;

        // 直接 Integer.bitCount(result) 即可

        int cnt = 0;
        while (result != 0) {
            result &= result - 1;
            cnt++;
        }
        return cnt;
    }
}
