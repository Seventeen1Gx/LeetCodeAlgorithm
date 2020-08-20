// 70. 爬楼梯
//
// 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
//
// 注意：给定 n 是一个正整数。


package src;

public class ClimbingStairs {
    // 斐波那契数列
    // 爬到第 n 个台阶，先爬到第 n-1 个台阶再爬 1 级或者先爬到第 n-2 个台阶在爬 2 级
    public int solution(int n) {
        if (n == 1){
            return 1;
        } else if (n == 2) {
            return 2;
        } else {
            int a = 1;
            int b = 2;
            int c = 0;
            while (n - 2 > 0) {
                c = a + b;
                a = b;
                b = c;
                n--;
            }
            return c;
        }
    }
}
