package demo;

import java.util.Arrays;

/**
 * 最接近的两数之和
 *
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的两个整数，使得它们的和与 target 最接近。
 *
 * 返回这两个数的和。假定每组输入只存在唯一答案。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class TwoSumClosest {
    private static final int REQUIRE_NUM = 2;

    /**
     * 双指针法，遍历过程中记录离 target 最近的 nums[left]+nums[right]
     */
    public int solution(int[] nums, int target) {
        // 排除异常情况
        if (nums == null || nums.length < REQUIRE_NUM) {
            throw new IllegalArgumentException("No TwoSumClosest Solution");
        }

        // 记录循环过程中遇到的 nums[left]+nums[right] 与 target 的最小距离
        int minDistant = Integer.MAX_VALUE;

        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;
        int ans = nums[left] + nums[right];
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                ans = target;
                break;
            } else if (nums[left] + nums[right] > target) {
                if (nums[left] + nums[right] - target < minDistant) {
                    ans = nums[left] + nums[right];
                    minDistant = nums[left] + nums[right] - target;
                }
                right--;
            } else {
                if (target - nums[left] - nums[right] < minDistant) {
                    ans = nums[left] + nums[right];
                    minDistant = target - nums[left] - nums[right];
                }
                left++;
            }
        }
        return ans;
    }
}
