// 90. 子集 II
//
// 给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
//
// 说明：解集不能包含重复的子集。
//
// 思路：在 78 题的基础上剪枝


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubSetsNo2 {
    List<List<Integer>> results = new ArrayList<>();

    public List<List<Integer>> solution(int[] nums) {
        process(new ArrayList<>(), nums, 0);
        Arrays.sort(nums);
        return results;
    }

    private void process(List<Integer> result, int[] nums, int start) {
        if (start == nums.length) {
            results.add(new ArrayList<>(result));
            return;
        }
        // 从 [start:) 中挑选一个元素，放到当前 result 的尾部
        // 保证结果递增产生
        for (int i = start; i < nums.length; i++) {
            // 剪枝，前提是数组排序，这样相同的元素才相邻
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            result.add(nums[i]);
            // 挑选下一个元素
            process(result, nums, i + 1);
            result.remove(result.size() - 1);
        }
    }
}
