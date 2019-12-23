//90. 子集Ⅱ
//
//给定一个可能包含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
//
//说明：解集不能包含重复的子集。
//
//思路：在78题的基础上剪枝


package src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubSetsNo2 {
    List<List<Integer>> results = new ArrayList<>();

    //经典回溯
    public List<List<Integer>> solution(int[] nums) {
        process(new ArrayList<>(), nums, 0);
        Arrays.sort(nums);
        return results;
    }

    //首先，数组得是排序的
    //其次，树的每个结点都要被存储
    //
    //结果树来看，每层确定子集的宽度，每下一层宽度就加一
    private void process(List<Integer> result, int[] nums, int start) {
        //注意要深拷贝
        results.add(new ArrayList<>(result));
        for (int i = start; i < nums.length; i++) {
            //剪枝
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }

            //取nums[i]
            result.add(nums[i]);
            process(result, nums, i + 1);
            //不去nums[i]
            result.remove(result.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{
                1, 2, 2
        };
        SubSetsNo2 s = new SubSetsNo2();
        s.solution(nums);
    }
}
