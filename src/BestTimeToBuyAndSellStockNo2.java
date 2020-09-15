package src;

/**
 * 122. 买卖股票的最佳时机Ⅱ
 *
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BestTimeToBuyAndSellStockNo2 {
    public int solution(int[] prices) {
        // 有限状态机
        // 遍历 prices：初始 → 持有股票 → 卖出股票 → 持有股票 → 卖出股票 → ...
        //
        // dp[i][0] 表示第 i 天时处于持有股票状态的最大收益，dp[i][1] 表示第 i 天时处于卖出股票的最大收益
        //
        // dp[i][0] = max(dp[i-1][1]-prices[i], dp[i-1][0])
        // dp[i][1] = max(dp[i-1][0]+prices[i], dp[i-1][1])

        if (prices.length == 0) {
            return 0;
        }

        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][1] - prices[i], dp[i - 1][0]);
            dp[i][1] = Math.max(dp[i - 1][0] + prices[i], dp[i - 1][1]);
        }
        return dp[n - 1][1];
    }
}
