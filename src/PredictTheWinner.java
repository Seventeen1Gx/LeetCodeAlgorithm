// 486. 预测赢家
//
// 给定一个表示分数的非负整数数组。
// 玩家 1 从数组任意一端拿取一个分数，随后玩家 2 继续从剩余数组任意一端拿取分数，然后玩家 1 拿，…… 。
// 每次一个玩家只能拿取一个分数，分数被拿取之后不再可取。直到没有剩余分数可取时游戏结束。最终获得分数总和最多的玩家获胜。
//
// 给定一个表示分数的数组，预测玩家1是否会成为赢家。你可以假设每个玩家的玩法都会使他的分数最大化。


package src;

public class PredictTheWinner {
    public boolean predictTheWinner(int[] nums) {
        return firstWin(nums, 0, nums.length - 1, 0, 0, true);
    }

    private boolean firstWin(int[] nums, int head, int tail, int scoreForFirst, int scoreForSecond, boolean isPlayOne) {
        // 在 (nums, head, tail, scoreForFirst, scoreForSecond) 状态下当前操作者是否获胜
        // scoreForFirst 是当前操作玩家已获得的分数
        // scoreForSecond 是另一位玩家已获得的分数
        // isPlayOne 用在判断当前操作者是否为玩家一，主要是玩家一在分数相等时算赢
        if (head == tail) {
            if ((scoreForFirst + nums[head]) == scoreForSecond) {
                return isPlayOne;
            }
            return (scoreForFirst + nums[head]) > scoreForSecond;
        } else {
            return !firstWin(nums, head + 1, tail, scoreForSecond, scoreForFirst + nums[head], !isPlayOne)
                    || !firstWin(nums, head, tail - 1, scoreForSecond, scoreForFirst + nums[tail], !isPlayOne);
        }
    }

    public boolean solution2(int[] nums) {
        // 动态规划
        // d[i][j] 表示 nums[i:j] 问题下先手玩家和后手玩家能获得的分数的最大差值
        // d[i][j] = max(num[i] - dp[i+1][j], num[j] - dp[i][j-1])
        // 可以看到 dp[i][j] 只与它下一个格子和前一个格子的值有关 → 转变为滚动数组节省空间
        // 只有 i<=j 时有意义
        // dp[i][i] 是基础情况

        // 两个人的分树大小判断，转变为两个人的分数差值判断

        int n = nums.length;
        int[][] dp = new int[n][n];
        // 初始化
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }

    public static void main(String[] args) {
        new PredictTheWinner().predictTheWinner(new int[]{1, 1});
    }
}
