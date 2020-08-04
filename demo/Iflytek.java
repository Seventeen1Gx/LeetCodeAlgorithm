// 科大讯飞笔试题

package demo;

public class Iflytek {
    private static int[] money = {1, 5, 10, 50, 100};

    // nums[i] 表示 money[i] 的可用个数
    // 选取最少的数字使之和为 target
    // 无解返回 -1
    public int solution1(int[] nums, int target) {
        // dp[i] 表示 target=i 时最少需要的纸币数（但其实它对应不同的 nums[i] 状态）
        // dp[i] = min(dp[i-1],dp[i-5],dp[i-10],dp[i-50],dp[i-100])+1
        // 且对应项必须有纸币可用
        if (target == 0) {
            return 0;
        }
        if (target < 0) {
            return -1;
        }
        int ret = -1;
        for (int i = 0; i < 5; i++) {
            if (nums[i] > 0) {
                nums[i]--;
                int temp = solution1(nums, target - money[i]);
                if (temp != -1 && (ret == -1 || ret > temp)) {
                    ret = temp;
                }
                // 回溯
                nums[i]++;
            }
        }

        return ret == -1 ? -1 : ret + 1;
    }

    // ------------------------------------------------
    // 给你数组排序过程的采样，让你实现这个排序算法得到一样的输出
    // 其实就是快排

    // ------------------------------------------------
    // 给定两个矩阵，判断它们有没有相交
    // 参考一维相交，只要两个维度上都相交，就说明矩阵相交

    // ------------------------------------------------
    // 输入一个非空字符串，去除非数字字符之后，代表的数是什么
    // 按序遍历，注意开头的正负号即可
}
