package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 15. 三数之和
 *
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 * 思路：假设数组中存在三个数之和为 0，我们按从小到大令它们依次为 a、b、c，我们每次固定 a，然后在其之后的元素中按双指针，寻求 b、c ，使得 b + c = -a 。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ThreeSum {
    private static final int REQUIRE_NUM = 3;

    public List<List<Integer>> solution(int[] nums) {
        List<List<Integer>> ansList = new ArrayList<>();

        if (nums == null || nums.length < REQUIRE_NUM) {
            return ansList;
        }

        // 从小到大排序数组，从而按序得到所有满足条件的三元组
        Arrays.sort(nums);

        int a;
        for (int i = 0; i < nums.length; i++) {
            // 选取数组中的一个数作为 a ，然后在其之后找 b+c=-a
            // 始终保证 a<=b<=c
            if (i != 0 && nums[i] == nums[i - 1]) {
                // 相同的 a 会产生重复的结果，所以我们跳过
                continue;
            }
            a = nums[i];

            // 因为我们保证结果中 a<=b<=c ，若 a>0 ，三数之和一定大于 0 了
            if (a > 0) {
                continue;
            }

            // 在 a 之后的元素中使用首尾指针法选择 b+c=-a
            int low = i + 1;
            int high = nums.length - 1;
            int b = Integer.MIN_VALUE, c;
            // 给定 a 情况下，标志第一次找到满足条件的 b，c
            boolean isFirst = true;
            while (low < high) {
                if (nums[low] + nums[high] > -a) {
                    high--;
                } else if (nums[low] + nums[high] < -a) {
                    low++;
                } else {
                    // 找到满足条件的 a、b、c
                    if (isFirst || nums[low] != b) {
                        // 不是重复答案的时候进行存储
                        b = nums[low];
                        c = nums[high];

                        List<Integer> l = new ArrayList<>();
                        l.add(a);
                        l.add(b);
                        l.add(c);
                        ansList.add(l);
                    }
                    // 寻找下一对 b、c
                    low++;
                    high--;
                    // 改变第一次找到的标志
                    isFirst = false;
                }
            }
        }
        return ansList;
    }

    public static void main(String[] args) {
        // 测试数据
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

        ThreeSum t = new ThreeSum();

        t.solution(nums);
    }
}
