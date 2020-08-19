package src.twoSum;

import java.util.Arrays;

/**
 *  16. 最接近的三数之和
 *
 *  给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。
 *
 *  返回这三个数的和。假定每组输入只存在唯一答案。
 *
 *  例如，给定数组 nums = [-1，2，1，-4]，和 target = 1。
 *  与 target 最接近的三个数的和为 2。 (-1 + 2 + 1 = 2)
 *
 *  思路：
 *  每次固定一个数，令其为 a，然后在其之后的元素中采用双指针法遍历
 *  记录遍历过程中遇到的最接近 target 的三数之和
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ThreeSumClosest {
    private static final int REQUIRE_NUM = 3;

    public int solution(int[] nums, int target) {
        if (nums == null || nums.length < REQUIRE_NUM) {
            throw new IllegalArgumentException("No ThreeSumClosest Solution");
        }

        Arrays.sort(nums);

        //初始化ans
        int ans = nums[0] + nums[1] + nums[2];
        // a+b+c 与 target 的距离
        int minDistant = Integer.MAX_VALUE, distant;

        for (int i = 0; i < nums.length; i++) {
            // 相同的 a 会产生相同的结果，故跳过
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 从 a 的后面的数字里找，从而保证了 a<=b<=c
            int low = i + 1;
            int high = nums.length - 1;
            while (low < high) {
                // 刚好等于目标值，那目标值就是最近的三数之和
                if (nums[i] + nums[low] + nums[high] == target) {
                    return target;
                }

                // 计算当前三数之和有没有更接近目标值
                distant = Math.abs(target - nums[low] - nums[high] - nums[i]);
                if (distant < minDistant) {
                    minDistant = distant;
                    ans = nums[i] + nums[low] + nums[high];
                }

                if (nums[i] + nums[low] + nums[high] > target) {
                    high--;
                    // 跳过重复数字
                    while (low < high && nums[high] == nums[high + 1]) {
                        high--;
                    }
                } else {
                    //nums[i] + nums[low] + nums[high] < target
                    low++;
                    // 跳过重复数字
                    while (low < high && nums[low] == nums[low - 1]) {
                        low++;
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        ThreeSumClosest t = new ThreeSumClosest();
        t.solution(new int[]{-1, 0, 1, 1, 55}, 3);

        // 测试用例1: 数组 [-1, 2, 1, -4] 和目标值 1
        // 测试用例2: 数组 [-1, 0, 1, 1, 55] 和目标值 3
    }
}
