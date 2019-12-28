// 15. 三数之和
//
// 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
//
// 注意：答案中不可以包含重复的三元组。
//
// 思路：假设数组中存在三个数之和为 0，我们按从小到大令它们依次为 a、b、c，代码中我们每次固定 a，然后在其之后按双指针，寻求 b、c


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    private static final int REQUIRE_NUM = 3;

    public List<List<Integer>> solution(int[] nums) {
        List<List<Integer>> ansList = new ArrayList<>();

        if (nums == null || nums.length < REQUIRE_NUM) {
            return ansList;
        }

        // 从小到大排序，确保结果按序搜寻
        Arrays.sort(nums);
        int a;
        for (int i = 0; i < nums.length; i++) {

            // 选取数组中的一个数作为a，然后在其之后找b+c=-a
            // 始终保证a<=b<=c
            if (i != 0 && nums[i] == nums[i - 1]) {
                // 相同的a会产生重复的结果，所以我们跳过
                continue;
            }
            a = nums[i];

            // 因为我们保证结果中a<=b<=c，若a>0，三数之和一定大于0了
            if (a > 0) {
                continue;
            }

            // 在a之后使用首尾指针法选择b+c=-a
            int low = i + 1;
            int high = nums.length - 1;
            int b = Integer.MIN_VALUE, c;
            // 标志第一次找到b，c结果
            boolean isFirst = true;
            while (low < high) {

                if (nums[low] + nums[high] > -a) {
                    high--;
                } else if (nums[low] + nums[high] < -a) {
                    low++;
                } else {
                    // 找到满足条件的a、b、c
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
                    // 寻找下一个答案
                    low++;
                    high--;
                    // 往后不再是第一次
                    isFirst = false;
                }
            }
        }
        return ansList;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

        ThreeSum t = new ThreeSum();

        t.solution(nums);
    }
}
