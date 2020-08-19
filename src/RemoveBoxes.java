// 546. 移除盒子
//
//
// 给出一些不同颜色的盒子，盒子的颜色由数字表示，即不同的数字表示不同的颜色。
// 你将经过若干轮操作去去掉盒子，直到所有的盒子都去掉为止。
// 每一轮你可以移除具有相同颜色的连续 k 个盒子（k >= 1），这样一轮之后你将得到 k*k 个积分。
// 当你将所有盒子都去掉之后，求你能获得的最大积分和。


package src;

public class RemoveBoxes {
    // f(l, r, k) 表示 [l, r] + k 个 ar 这个区间能获得的最大积分
    // 比如 [6,3,6,5,6],6,6,6 → f(1, 5, 3)
    // 可以做的：
    // 右边 4 个 6 移除 → f(1, 4, 0) + f(5, 5, 4)
    // 先把 5 移除 → f(1, 3, 4) + f(4, 4, 0)
    // 先把 [3,6,5] 移除 f(1, 1, 4) + f(2, 4, 0)
    //
    // 于是 f(l, r, k) = max(
    //     f(l, r-1, 0) + f(r, r, k)，
    //     f(l, x1, k+1) + f(x1+1, r-1, 0)，
    //     f(l, x2, k+1) + f(x2+1, r-1, 0)，
    //     ......
    // )
    // 注：1. x1, x2, ... 是 boxes[r] 往前出现的位置
    //    2. f(r, r, k) = (k+1)^2

    public int solution(int[] boxes) {
        // 题目限制 boxes 最长为 100
        int[][][] dp = new int[100][100][100];
        return calculatePoints(boxes, dp, 0, boxes.length - 1, 0);
    }

    private int calculatePoints(int[] boxes, int[][][] dp, int l, int r, int k) {
        if (l > r) {
            return 0;
        }
        // 记忆化搜索（好处是，不用确立动规的顺序）
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        // dp[l][r][k] 等同于 dp[l][r+i][k+i]，如果末尾有相同元素的情况
        // 例：[6,3,6,5,6,6,6,6] → dp[1, 8, 0]
        // 等价于 [6,3,6,5,6],6,6,6 → dp[1, 5, 3]
        while (r > l && boxes[r] == boxes[r - 1]) {
            r--;
            k++;
        }
        dp[l][r][k] = calculatePoints(boxes, dp, l, r - 1, 0) + (k + 1) * (k + 1);
        for (int i = l; i < r; i++) {
            if (boxes[i] == boxes[r]) {
                dp[l][r][k] = Math.max(dp[l][r][k],
                        calculatePoints(boxes, dp, l, i, k + 1) +
                                calculatePoints(boxes, dp, i + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }

}
