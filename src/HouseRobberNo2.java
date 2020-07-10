// 213. 打家劫舍Ⅱ
//
// 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都围成一圈，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你在不触动警报装置的情况下，能够偷窃到的最高金额。
//
// 注意：本题与 198 的区别是，nums[0] 和 nums[nums.length()-1] 被视为相邻


package src;

public class HouseRobberNo2 {
    public int solution(int[] nums) {
        if (nums == null || nums.length <= 0)
            return 0;

        int n = nums.length;
        if (n == 1)
            return nums[0];

        // 将问题拆分成两个子问题
        return Math.max(rob(nums, 0, n - 2), rob(nums, 1, n - 1));
    }


    // 在 [start, last] 中被视为 198 题这样的问题
    private int rob(int[] nums, int start, int last) {
        int len = last - start + 1;

        if (len <= 0)
            return 0;

        if (len == 1)
            return nums[start];

        if (len == 2)
            return Math.max(nums[start], nums[last]);

        int pre2 = nums[start], pre1 = Math.max(nums[start], nums[start + 1]);
        int cur = 0;
        int i = start + 2;
        while (i <= last) {
            cur = Math.max(pre2 + nums[i], pre1);
            pre2 = pre1;
            pre1 = cur;
            i++;
        }
        return cur;
    }

    public static void main(String[] args) {
        new HouseRobberNo2().solution(new int[]{1, 3, 1, 3, 100});

    }
}
