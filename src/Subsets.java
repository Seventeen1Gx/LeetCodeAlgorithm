// 78. 子集
//
// 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
//
// 说明：解集不能包含重复的子集。


package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Subsets {
    List<List<Integer>> results = new ArrayList<>();

    public List<List<Integer>> solution(int[] nums) {
        recursion(nums, 0, new LinkedList<>());
        return results;
    }

    private void recursion(int[] nums, int k, LinkedList<Integer> result) {
        //已处理到 k 元素，决定 k 元素添加或不添加
        if (k < nums.length) {
            result.add(nums[k]);
            recursion(nums, k + 1, result);
            result.removeLast();
            recursion(nums, k + 1, result);
        } else if (k == nums.length) {
            results.add(new LinkedList<>(result));
        }
    }
}
