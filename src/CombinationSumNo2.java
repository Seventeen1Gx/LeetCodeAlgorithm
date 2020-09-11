// 40. 组合总和Ⅱ
//
// 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
// candidates 中的每个数字在每个组合中只能使用一次。
//
// 说明：
//
// 所有数字（包括目标数）都是正整数。
// 解集不能包含重复的组合。 


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CombinationSumNo2 {

    List<List<Integer>> ansList;

    public List<List<Integer>> solution(int[] candidates, int target) {
        ansList = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2(new Stack<>(), candidates, target, -1);
        return ansList;
    }

    public void combinationSum2(Stack<Integer> stack, int[] candidates, int n, int k) {
        if (n == 0) {
            List<Integer> newList = new ArrayList<>(stack);
            ansList.add(newList);
        } else if (n > 0) {
            for (int i = k + 1; i < candidates.length; i++) {
                // 要避免因为使用不同下标的相同元素而出现重复结果(剪去相同的一个分支)
                if (i > k + 1 && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                stack.add(candidates[i]);
                combinationSum2(stack, candidates, n - candidates[i], i);
                stack.pop();
            }
        }
    }

    public static void main(String[] args) {
        CombinationSumNo2 c = new CombinationSumNo2();
        int[] arr = new int[]{10, 1, 2, 7, 6, 1, 5};
        c.solution(arr, 8);
        // 注意上面例子中会出现 [1,2,5] 和 [1,2,5] 是两种相同的答案
    }
}
