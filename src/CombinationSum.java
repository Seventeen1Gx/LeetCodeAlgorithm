// 39. 组合总数
//
// 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的数字可以无限制重复被选取。
//
// 说明：
// 所有数字（包括 target）都是正整数。
// 解集不能包含重复的组合。 


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    List<List<Integer>> ansList;

    public List<List<Integer>> solution1(int[] candidates, int target) {
        ansList = new ArrayList<>();
        combinationSum(new ArrayList<>(), candidates, target);
        return ansList;
    }

    public void combinationSum(List<Integer> list, int[] candidates, int n) {
        if (n == 0) {
            // 注意使用深拷贝
            List<Integer> newList = new ArrayList<>(list);
            // 注意不要重复
            ansList.add(newList);
        } else if (n > 0) {
            // 从数组中选择一个元素，每个元素都应该有机会
            // 为了避免重复，我们选择的时候，只选不小于当前已选择元素 -- 按一定顺序形成结果，从而不会重复
            for (int candidate : candidates) {
                int len = list.size();
                if (len == 0 || list.get(len - 1) <= candidate) {
                    list.add(candidate);
                    combinationSum(list, candidates, n - candidate);
                    //回溯
                    list.remove(list.size() - 1);
                }
            }
        }
    }

    public List<List<Integer>> solution2(int[] candidates, int target) {
        ansList = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum(new ArrayList<>(), candidates, target, 0);
        return ansList;
    }

    public void combinationSum(List<Integer> list, int[] candidates, int n, int k) {
        // list 含已选择的元素，还需在 candidate 中选择元素使这些元素之和为 n，k 表示上一次选取元素的下标
        if (n == 0) {
            List<Integer> newList = new ArrayList<>(list);
            ansList.add(newList);
        } else if (n > 0) {
            for (int i = k; i < candidates.length; i++) {
                // 因为数组排序，所以这里选择的必不小于上一次选择元素
                list.add(candidates[i]);
                combinationSum(list, candidates, n - candidates[i], i);
                // 回溯
                list.remove(list.size() - 1);
            }
        }
    }
}
