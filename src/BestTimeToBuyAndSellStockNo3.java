// 123. 买卖股票的最佳时机 III
//
// 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
//
// 设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。
//
// 注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。


package src;

public class BestTimeToBuyAndSellStockNo3 {
    public int solution(int[] prices) {
        // 有限状态机
        // 遍历 prices：初始 → 持有股票 1 → 卖出股票 1 → 持有股票 2 → 卖出股票 2
        //
        // 上面五种状态，对应 5 个数组
        // dp[i][0], dp[i][1], dp[i][2], dp[i][3], dp[i][4]
        // dp[i][0] 恒为 0
        // dp[i][1] = max(dp[i-1][1], dp[i-1][0]-prices[i])
        // dp[i][2] = max(dp[i-1][2], dp[i-1][1]+prices[i])
        // dp[i][3] = max(dp[i-1][3], dp[i-1][2]-prices[i])
        // dp[i][4] = max(dp[i-1][4], dp[i-1][3]+prices[i))

        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][5];
        dp[0][1] = dp[0][3] = -prices[0];
        dp[0][2] = dp[0][4] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][1] = Math.max(dp[i - 1][1], prices[i]);
            dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);
            dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);
            dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
        }
        return Math.max(Math.max(Math.max(Math.max(0, dp[n - 1][1]), dp[n - 1][2]), dp[n - 1][3]), dp[n - 1][4]);
    }
}
