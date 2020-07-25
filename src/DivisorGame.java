// 1025. 除数博弈
//
// 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
//
// 最初，黑板上有一个数字 N 。在每个玩家的回合，玩家需要执行以下操作：
//
// 选出任一 x，满足 0 < x < N 且 N % x == 0 。
// 用 N - x 替换黑板上的数字 N 。
// 如果玩家无法执行这些操作，就会输掉游戏。
//
// 只有在爱丽丝在游戏中取得胜利时才返回 true，否则返回 false。假设两个玩家都以最佳状态参与游戏。


package src;

public class DivisorGame {
    // 表示数字为 N 时，先手是否获胜
    public boolean solution1(int N) {
        if (N == 1)
            return false;
        boolean ret = false;
        // 只要存在一个 x，使得 N%x==0 成立，且 N-x 时先手必败，那么当前数字 N 时，先手必胜
        for (int i = 1; i < N; i++) {
            if (N % i == 0)
                ret |= !solution1(N - i);
        }
        return ret;
    }

    // 借助递归思路，可用动态规划
    public boolean solution2(int N) {
        // dp[i] 表示数字为 i 时，先手是否获胜
        boolean[] dp = new boolean[N + 1];
        dp[1] = false;

        for (int n = 2; n <= N; n++) {
            for (int x = 1; x < n; x++) {
                if (n % x == 0) {
                    // 只要有一种 n-x 时先手必败的情况
                    // 那么数字为 n 时，先手就必胜
                    dp[n] |= !dp[n - x];
                }
            }
        }

        return dp[N];
    }
}
