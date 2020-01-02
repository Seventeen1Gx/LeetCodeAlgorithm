package src;

import java.util.Arrays;

/**
 *  16. 最接近的三数之和
 *
 *  给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。
 *
 *  返回这三个数的和。假定每组输入只存在唯一答案。
 *
 *  例如，给定数组 nums = [-1，2，1，-4] ，和 target = 1 。
 *  与 target 最接近的三个数的和为 2 。 (-1 + 2 + 1 = 2)
 *
 *  思路：
 *  先考虑最接近的两数之和的问题
 *  两指针法，排序数组， 首尾指针确定 a 与 b
 * 若出现 a+b=target ，则 return a+b
 * 若 a+b>target ，则移动 right ，进入下次循环
 * 若 a+b<target ，则移动 left ，进入下次循环
 * 最终循环结束就得到了最接近的 a+b 的值
 * 循环结束的条件是，两指针相撞。记录该过程中遇到的最接近的三数之和即可。
 * 那三数之和同理，固定一个数，剩下使用两数之和的逻辑
 */
public class ThreeSumClosest {
    private static final int REQUIRE_NUM = 3;

    public int solution(int[] nums, int target) {
        // 距离无穷大的结果
        int ans = Integer.MAX_VALUE - target > target - Integer.MIN_VALUE
                ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        if (nums == null || nums.length < REQUIRE_NUM) {
            return ans;
        }

        Arrays.sort(nums);

        // a+b+c 与 target 的距离
        int minDistant = Integer.MAX_VALUE, distant;

        for (int i = 0; i < nums.length; i++) {
            // 相同的a会产生相同的结果，故跳过
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 从a的后面的数字里找，保证 a<=b<=c
            int low = i + 1;
            int high = nums.length - 1;
            while (low < high) {
                // 刚好等于目标值，那就是最近的三数之和
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

        // 测试用例1: 数组[-1, 2, 1, -4]和目标值1
        // 测试用例2: 数组[-1, 0, 1, 1, 55]和目标值3
    }
}
