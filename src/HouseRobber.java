// 198. 打家劫舍
//
// 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。


package src;

public class HouseRobber {
    // 从前往后，路过每家，要么是偷，要么是不偷
    //
    // dp[i] 表示偷到第 i 家时能偷到的最高金额
    // 状态转移：dp[i] = max(dp[i-2]+nums[i], dp[i-1])
    //
    // 我们易知，dp[i] 只取决于前两项 dp[i-2]、dp[i-1]
    // 则为了节省空间，我们只用三个变量

    public int solution(int[] nums) {
        if (nums == null || nums.length <= 0)
            return 0;

        if (nums.length == 1)
            return nums[0];
        else if (nums.length == 2)
            return Math.max(nums[0], nums[1]);

        int pre2 = nums[0], pre1 = Math.max(nums[0], nums[1]);
        int cur = 0;
        int i = 2;
        while (i < nums.length) {
            cur = Math.max(pre2 + nums[i], pre1);
            pre2 = pre1;
            pre1 = cur;
            i++;
        }
        return cur;
    }
}
