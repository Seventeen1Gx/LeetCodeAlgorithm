// 188. 买卖股票的最佳时机 IV
//
// 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
//
// 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。


package src;

public class BestTimeToBuyAndSellStockNo4 {
    public int solution(int[] prices, int k) {
        // 有限状态机
        // 遍历 prices：初始 → 持有股票 1 → 卖出股票 1 → 持有股票 2 → 卖出股票 2 ... → 持有股票 k → 卖出股票 k
        //
        // 上面 2k+1 种状态，对应 2k+1 个数组
        // dp[i][0], dp[i][1], dp[i][2], dp[i][3], dp[i][4], ... , dp[i][2k+1]
        //
        // 利用滚动数组优化空间

        if (prices == null || prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        if (k > n / 2) {
            // 当 k 很大时转换为无限次数交易，不然空间不够
            int[][] dp = new int[n][2];
            dp[0][0] = -prices[0];
            dp[0][1] = 0;
            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][1] - prices[i], dp[i - 1][0]);
                dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
            }
            return dp[n - 1][1];
        }

        int[] dp = new int[2 * k + 2];
        for (int i = 0; i < 2 * k + 2; i += 2) {
            dp[i] = 0;
            dp[i + 1] = -prices[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 2 * k + 2; j++) {
                if (j % 2 == 1) {
                    dp[j] = Math.max(dp[j], dp[j - 1] - prices[i]);
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1] + prices[i]);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < 2 * k + 2; i++) {
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
