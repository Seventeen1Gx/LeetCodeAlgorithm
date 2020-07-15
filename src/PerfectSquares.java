// 279. 完全平方数
//
// 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。

package src;

import java.util.ArrayList;
import java.util.List;

public class PerfectSquares {
    public int solution(int n) {
        // dp[i] 表示 n=i 时的解
        // 则其等于 dp[i-a],dp[i-b],dp[i-c],... 中最小值加 1
        // a、b、c 为完全平方数
        int[] dp = new int[n + 1];
        dp[0] = 0;
        List<Integer> list = getSquareList(n);
        for (int i = 1; i < n + 1; i++) {
            int min = Integer.MAX_VALUE;
            for (int square : list) {
                if (i - square < 0)
                    break;
                min = Math.min(min, dp[i - square]);
            }
            dp[i] = min + 1;
        }
        return dp[n];
    }

    // 获得完全平方数的序列
    // 最大完全平方数 <= n
    private List<Integer> getSquareList(int n) {
        List<Integer> list = new ArrayList<>();

        int i = 1;
        while (i * i <= n) {
            list.add(i * i);
            i++;
        }

        return list;
    }
}
