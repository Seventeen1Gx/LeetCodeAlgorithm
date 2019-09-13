//39. 组合总数
//
//给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
//candidates 中的数字可以无限制重复被选取。
//
//说明：
//所有数字（包括 target）都是正整数。
//解集不能包含重复的组合。 
//
//思路
//1.可用递归(回溯)--dfs，但要注意剪枝，不然就会重复计算
//2.此外，因为无限制选取，所以目标数的约数(且在数组中存在)都可以形成一个解
//如，6的约数有1、2、3和6，于是可以组成解[1,1,1,1,1,1]、[2、2、2]、[3、3]和[6]


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

    //list含已选择的元素，还需在candidate中选择元素使这些元素之和为n
    public void combinationSum(List<Integer> list, int[] candidates, int n) {
        if (n == 0) {
            //注意使用深拷贝
            List<Integer> newList = new ArrayList<>(list);
            //注意不要重复
            ansList.add(newList);
        } else if (n > 0) {
            //从数组中选择一个元素，每个元素都应该有机会
            //为了避免重复，我们选择的时候，只选不小于当前已选择元素--按一定顺序形成结果，从而不会重复
            for (int i = 0; i < candidates.length; i++) {
                int len = list.size();
                if (len == 0 || list.get(len - 1) <= candidates[i]) {
                    list.add(candidates[i]);
                    combinationSum(list, candidates, n - candidates[i]);
                    //回溯
                    list.remove(list.size() - 1);
                }
            }
        }
        //n<0我们不做任何事
    }

    //同样是递归，这回我们将数组排序
    public List<List<Integer>> solution2(int[] candidates, int target) {
        ansList = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum(new ArrayList<>(), candidates, target, 0);
        return ansList;
    }

    //list含已选择的元素，还需在candidate中选择元素使这些元素之和为n，k表示上一次选取元素的下标
    public void combinationSum(List<Integer> list, int[] candidates, int n, int k) {
        if (n == 0) {
            //注意使用深拷贝
            List<Integer> newList = new ArrayList<>(list);
            //注意不要重复
            ansList.add(newList);
        } else if (n > 0) {
            //从数组中选择一个元素，每个元素都应该有机会
            //为了避免重复，我们选择的时候，只选不小于当前已选择元素--按一定顺序形成结果，从而不会重复
            for (int i = k; i < candidates.length; i++) {
                //因为数组排序，所以这里选择的必不小于上一次选择元素
                list.add(candidates[i]);
                combinationSum(list, candidates, n - candidates[i], i);
                //回溯
                list.remove(list.size() - 1);
            }
        }
        //n<0我们不做任何事
    }
}
