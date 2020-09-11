// 216. 组合总数Ⅲ
//
// 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
//
// 说明：
//
// 所有数字都是正整数。
// 解集不能包含重复的组合。 


package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CombinationSumNo3 {
    public List<List<Integer>> combinationSum3(int k, int n) {
        dfs(new LinkedList<>(), n, k, 1);
        return ans;
    }

    List<List<Integer>> ans = new ArrayList<>();

    private void dfs(LinkedList<Integer> stack, int n, int k, int index) {
        // 当前已经选择的数在 stack 中
        // 还要在 index ~ 9 中选择 k - stack.size() 个数使和为 n
        if (n == 0 && k == stack.size()) {
            ans.add(new ArrayList<>(stack));
        } else if (n > 0 && k > stack.size()) {
            for (int i = index; i <= 9; i++) {
                stack.push(i);
                dfs(stack, n - i, k, i + 1);
                stack.pop();
            }
        }
    }
}
