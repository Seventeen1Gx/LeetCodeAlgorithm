// 1482. 制作 m 束花所需的最少天数
//
// 给你一个整数数组 bloomDay，以及两个整数 m 和 k 。
//
// 现需要制作 m 束花。制作花束时，需要使用花园中相邻的 k 朵花 。
//
// 花园中有 n 朵花，第 i 朵花会在 bloomDay[i] 时盛开，恰好可以用于一束花中。
//
// 请你返回从花园中摘 m 束花需要等待的最少的天数。如果不能摘到 m 束花则返回 -1 。
//
// 示例 1：
// 输入：bloomDay = [1,10,3,10,2], m = 3, k = 1
// 输出：3
// 解释：让我们一起观察这三天的花开过程，x 表示花开，而 _ 表示花还未开。
// 现在需要制作 3 束花，每束只需要 1 朵。
// 1 天后：[x, _, _, _, _]   // 只能制作 1 束花
// 2 天后：[x, _, _, _, x]   // 只能制作 2 束花
// 3 天后：[x, _, x, _, x]   // 可以制作 3 束花，答案为 3
//
// 示例 2：
// 输入：bloomDay = [1,10,3,10,2], m = 3, k = 2
// 输出：-1
// 解释：要制作 3 束花，每束需要 2 朵花，也就是一共需要 6 朵花。而花园中只有 5 朵花，无法满足制作要求，返回 -1 。
//
// 示例 3：
// 输入：bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
// 输出：12
// 解释：要制作 2 束花，每束需要 3 朵。
// 花园在 7 天后和 12 天后的情况如下：
// 7 天后：[x, x, x, x, _, x, x]
// 可以用前 3 朵盛开的花制作第一束花。但不能使用后 3 朵盛开的花，因为它们不相邻。
// 12 天后：[x, x, x, x, x, x, x]
// 显然，我们可以用不同的方式制作两束花。



package src.binarySearch;

public class MinimumNumberOfDaysToMakeMBouquets {
    // 由题意，获得以下情报
    // 1. 当 n < m*k 时，制作不出花 → 于是我们只考虑 n >= m*k 的情况
    // 2. 当等待时间够长，所有花都盛开，一定能制作出花束 → 搜索的上界是 bloomDay 的最大值
    //
    // 验证在 x 天时，能不能制作出花束
    // 如果能，说明大于等于 x 天的情况下都能
    // 如果不能，说明小于 x 天的情况下都不能
    // 即 x 递增过程中，有一处临界点，该临界点，就是最小需要等待的天数
    public int minDays(int[] bloomDay, int m, int k) {
        if (bloomDay.length < m * k)
            return -1;

        int low = 1;
        int high = 0;
        for (int b : bloomDay)
            high = Math.max(high, b);
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (valid(bloomDay, m, k, mid))
                // 说明天数偏大
                high = mid;
            else
                low = mid + 1;
        }
        return low;
    }

    // 验证在第 x 天，能不能制作出花束
    // 采用贪心，从前往后遍历，能作出一束花束，我们就制作
    private boolean valid(int[] bloomDay, int m, int k, int x) {
        // 已经制作了 cnt 束花
        int cnt = 0;
        // 已经发现相邻的且开花的花
        int neighborSum = 0;

        int i = 0;
        while (i < bloomDay.length) {
            // 提前退出
            if (cnt >= m)
                return true;

            if (bloomDay[i] <= x) {
                // 说明当前遍历到的这朵花成熟了
                neighborSum++;
                if (neighborSum == k) {
                    cnt++;
                    neighborSum = 0;
                }
            } else {
                // 说明当前遍历到的这朵花没成熟
                neighborSum = 0;
            }
            i++;
        }
        return cnt >= m;
    }

    public static void main(String[] args) {
        new MinimumNumberOfDaysToMakeMBouquets().valid(new int[]{1, 10, 3, 10, 2}, 3, 1, 2);
    }
}
