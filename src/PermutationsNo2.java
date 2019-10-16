//47. 全排列Ⅱ
//
//给定一个可包含重复数字的序列，返回所有不重复的全排列。
//
//与46题的区别为，本题的序列有重复元素，而结果不能包含重复序列
//只要加上一个剪枝操作即可--画出按46题方法构造的递归树，就能看出该如何剪枝从而去重


package src;

import java.util.*;

public class PermutationsNo2 {
    public List<List<Integer>> solution(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        recursion(lists, nums, 0);
        return lists;
    }

    //递归求nums[i:]的全排列，i之前的都是摆好的
    public void recursion(List<List<Integer>> lists, int[] nums, int i) {
        if (i == nums.length) {
            //注意要深拷贝
            List<Integer> list = new ArrayList<>();
            for (Integer integer : nums) {
                list.add(integer);
            }
            lists.add(list);
        } else {
            //记录位置i放过的元素(每层分别统计)
            Set<Integer> integerSet = new HashSet<>();

            //[i:]的元素都有机会放在位置i
            for (int j = i; j < nums.length; j++) {
                //仅仅是下面代码这样的剪枝还不行，因为这里是当前要放在i位置的元素和前一次放在i位置元素比
                //相同时剪枝，而在本算法中，不一定相同元素出现在i位置是相邻的两次摆放之间
                //所以我们用了set来记录
                //if (i != j && nums[j] == nums[i])
                //    continue;

                //剪枝(当前要放在i位置的元素已经放过了)
                if (integerSet.contains(nums[j]))
                    continue;

                //换位，即nums[j]放在位置i，同时更新set
                integerSet.add(nums[j]);
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;

                //剩下元素的排列
                recursion(lists, nums, i + 1);

                //回溯
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        PermutationsNo2 p = new PermutationsNo2();
        p.solution(new int[]{1, 1, 2});
        p.solution(new int[]{2, 2, 1, 1});
    }
}
