package src;

/**
 * 121. 买卖股票的最佳时机
 *
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意你不能在买入股票前卖出股票。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BestTimeToBuyAndSellStock {
    /**
     * 暴力法
     */
    public int solution1(int[] prices) {
        int maxProfit = 0;
        // 考虑每天的股票价格和该天之后股票价格的差
        // 低买高抛
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[j] - prices[i] > maxProfit) {
                    maxProfit = prices[j] - prices[i];
                }
            }
        }
        return maxProfit;
    }

    /**
     * 动态规划
     */
    public int solution2(int[] prices) {
        // 有限状态机
        // 遍历 prices：初始 → 持有股票 → 卖出股票
        //
        // dp[i][0] 表示第 i 天时处于持有股票状态的最大收益，dp[i][1] 表示第 i 天时处于卖出股票的最大收益
        //
        // 因为只能买一次，第 i 天处于持有股票状态的最大收益就是这 i 天最小的股价的负数，于是
        // dp[i][0] = max(dp[i-1][0], -prices[i])
        //
        // 要么前一天的时候卖出了，要么今天再卖出
        // dp[i][1] = max(dp[i-1][1], dp[i-1][0] + prices[i])

        if (prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = -prices[0];
        dp[0][1] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], -prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], prices[i] + dp[i - 1][0]);
        }
        return dp[n - 1][1];
    }

    /**
     * 一次遍历，遍历到的每个元素都跟其之前的最低元素来计算收益
     *
     * 反向思考，暴力法是确定买入日期，再去试不同的卖出日期
     * 本方法是，根据每个卖出日期，去找之前其遇到的最低的买入价格
     * 因为是从左往右遍历，最低价格可以随之更新
     *
     * 本质是贪心
     */
    public int solution3(int[] prices) {
        // 维护两个变量
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else {
                // 假设在之前最低价买入，这时抛出
                int profit = prices[i] - minPrice;
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }
        return maxProfit;
    }
}
