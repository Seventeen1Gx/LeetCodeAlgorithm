// 46. 全排列
//
// 给定一个没有重复数字的序列，返回其所有可能的全排列。
//
// 思路
// 先放第一个位置，然后递归处理剩下元素，而每个元素都有机会放在对应的位置上


package src;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
    public List<List<Integer>> solution(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        recursion(lists, nums, 0);
        return lists;
    }

    public void recursion(List<List<Integer>> lists, int[] nums, int i) {
        // 递归求 nums[i:] 的全排列，i 之前的都是摆好的
        if (i == nums.length) {
            // 注意要深拷贝
            List<Integer> list = new ArrayList<>();
            for (Integer integer : nums) {
                list.add(integer);
            }
            lists.add(list);
        } else {
            // [i:] 的元素都有机会放在位置 i
            for (int j = i; j < nums.length; j++) {
                // 换位
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
                // 剩下元素的排列
                recursion(lists, nums, i + 1);
                // 回溯
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
    }
}
