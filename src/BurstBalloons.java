// 312. 戳气球
//
// 有 n 个气球，编号为0 到 n-1，每个气球上都标有一个数字，这些数字存在数组 nums 中。
//
// 现在要求你戳破所有的气球。如果你戳破气球 i ，就可以获得 nums[left] * nums[i] * nums[right] 个硬币。 这里的 left 和 right 代表和 i 相邻的两个气球的序号。注意当你戳破了气球 i 后，气球 left 和气球 right 就变成了相邻的气球。
//
// 求所能获得硬币的最大数量。
//
// 说明:
//
// 你可以假设 nums[-1] = nums[n] = 1，但注意它们不是真实存在的所以并不能被戳破。
// 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100


package src;

import java.util.Arrays;

public class BurstBalloons {

    // 每戳破一个气球，原本不相邻的 left 和 right 变得相邻
    // 故很难
    // 正难则反
    // 戳破气球的顺序，我们反过来，看成添加气球

    // solve(i,j) 表示在 (i,j) 内填入 [i+1,j-1] 序号的气球，某种顺序下得到的最大金币
    //
    // 当 i >= j-1 时，开区间內没有气球，则为 0
    // 当 i < j-1 时，
    // 对于 solve(i,j)，我们做的是这样一个操作
    // 被填入数组有 [val[i],val[j]]
    // 我们要从 [i+1,j-1] 序号的气球中选一个气球，假设为 val[mid]，填入 val[i] 和 val[j] 之间，得到金币 val[i]*val[mid]*val[j]
    // 被填入数组有 [val[i],val[mid],val[j]]
    // 接下来就变成，在 val[i] 和 val[mid] 中填入 (i,mid) 中的气球，与，在 val[mid] 和 val[j] 中填入 (mid,j) 中的气球
    // ...
    // 照此下去，被填入数组就变回了 val，顺序也一致
    // 总结，就是
    // solve(i,j) = max[val[i]*val[mid]*val[j] + solve(i,mid) + solve(mid,j)]，i<mid<j


    // 示例:
    //
    // 输入: [3,1,5,8]
    // 输出: 167
    // 解释: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
    //      coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
    //
    // nums 转化为 val，即 [1,3,1,5,8,1]
    // 一开始我们待填入数组中为 [1,1]，下标代表就是 [0,5]
    // solve(0,5)，就是在下标 [1,4] 的元素中，选一个填到 [1,1] 之中，第一次填的是 8，即 [1,8,1]，下标代表是 [0,4,5]
    // 接下来出现的填入就是
    // 1. 在下标 [1,3] 中选一个填到 [1,8] 之中，即 solve(0,4)
    // 2. 在下标 [5,4] 中选一个填到 [8,1] 之中，即 solve(4,5)

    // 记忆数组，防止重复计算
    public int[][] rem;
    // val 是 nums 左右添加元素 1 得到的
    public int[] val;

    public int solution1(int[] nums) {
        int n = nums.length;
        // 先转化数组
        val = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            val[i] = nums[i - 1];
        }
        val[0] = val[n + 1] = 1;

        // rem[i][j] 保存已经求得的的 solve(i,j)
        // solve(i,j) 没求过时，rem[i][j] = -1
        rem = new int[n + 2][n + 2];
        for (int i = 0; i < n + 2; i++) {
            Arrays.fill(rem[i], -1);
        }

        return solve(0, n + 1);
    }

    private int solve(int i, int j) {
        if (j - 1 - i <= 0) {
            // 区间 [i+1, j-1] 之中没有气球可以选
            return 0;
        }

        if (rem[i][j] != -1) {
            // 已经求过了
            return rem[i][j];
        }

        // 在区间 [i+1, j-1] 中选择添加的气球
        for (int mid = i + 1; mid < j; mid++) {
            rem[i][j] = Math.max(rem[i][j], val[i] * val[mid] * val[j] + solve(i, mid) + solve(mid, j));
        }
        return rem[i][j];
    }

    // 上面是自顶向下的，就是一开始就求 solve(0,5)，然后由于分解的子问题没有求出来，又去求子问题
    // 我们可以自底向上，先求子问题，再求大问题
    // 所以要注意求值的顺序
    // 对于 dp[i][j]，
    // 当 j-1-i <= 0 时，其值为 0
    // 当 j-1-i > 0 时，其值为 max[val[i]*val[mid]*val[j]+dp[i][mid]+dp[mid][j]]，i<mid<j
    //
    // 要保证求 dp[i][j] 时，dp[i][mid] 和 dp[mid][j] 已经被求出了
    // 即先求小区间，再求大区间
    //
    // 同样以示例 (0,5)
    // 最基础的是，(0,2) (1,3) (2,4) (3,5)，因为它们区间里只有一个元素
    // 接着是求区间里有两个元素的，(0,3) 选择 1 时，依赖 (0,1) 和 (1,3)，选择 2 时，依赖 (0,2) 和 (2,3) 都在上一次被求出，没问题
    // ...
    public int solution2(int[] nums) {
        int n = nums.length;
        // 先转化数组
        val = new int[n + 2];
        for (int i = 1; i <= n; i++) {
            val[i] = nums[i - 1];
        }
        val[0] = val[n + 1] = 1;

        int[][] dp = new int[n + 2][n + 2];
        // 因为默认值就是 0，所以一些直接求得的基本情况可以不用再操作

        // 第一层循环控制区间大小
        for (int gap = 2; gap <= n + 1; gap++) {
            // 控制左区间
            // j - i - 1 > 0，因为 gap 从 2 开始递增，而 j = i + gap
            for (int i = 0, j; i < n + 2 && (j = i + gap) < n + 2; i++) {
                // mid 在区间 (i,j) 之中
                for (int mid = i + 1; mid < j; mid++) {
                    dp[i][j] = Math.max(dp[i][j], val[i] * val[mid] * val[j] + dp[i][mid] + dp[mid][j]);
                }
            }
        }
        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        new BurstBalloons().solution1(new int[]{3, 1, 5, 8});
    }
}
