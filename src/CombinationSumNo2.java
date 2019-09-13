//40. 组合总和Ⅱ
//
//给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//
//candidates 中的每个数字在每个组合中只能使用一次。
//
//说明：
//
//所有数字（包括目标数）都是正整数。
//解集不能包含重复的组合。 
//
//和39题的区别就是可以有重复元素，但每个元素只能用一次
//其实就是在39题的基础上，本来每个元素可以重复使用，现在有使用次数限制


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

    //k表示上一次选取元素下标
    public void combinationSum2(Stack<Integer> stack, int[] candidates, int n, int k) {
        if (n == 0) {
            List<Integer> newList = new ArrayList<>(stack);
            ansList.add(newList);
        } else if (n > 0) {
            //选择元素--因为不能重复，所以这里用k+1
            for (int i = k + 1; i < candidates.length; i++) {
                //【关键】要避免因为使用不同下标的相同元素而出现重复结果(剪去相同的一个分支)
                if (i > k + 1 && candidates[i] == candidates[i-1])
                    continue;

                //因为数组排序，所以这里选择的必不小于上一次选择元素
                stack.add(candidates[i]);
                combinationSum2(stack, candidates, n - candidates[i], i);
                //回溯
                stack.pop();
            }
        }
    }



    //------------------------------------------------------------------
    //下面的方法将[1,2,5]和[1,2,5]视为两种不同的答案了--因为用了不同的1

    /*
    public List<List<Integer>> solution(int[] candidates, int target) {
        ansList = new ArrayList<>();
        Arrays.sort(candidates);
        combinationSum2(new ArrayList<>(), candidates, target, -1);
        return ansList;
    }

    //k表示上一次选取元素下标
    public void combinationSum2(List<Integer> list, int[] candidates, int n, int k) {
        if (n == 0) {
            List<Integer> newList = new ArrayList<>(list);
            ansList.add(newList);
        } else if (n > 0) {
            //选择元素--要不小于上一次选择，且要不超过该元素使用次数
            for (int i = k + 1; i < candidates.length; i++) {
                //因为数组排序，所以这里选择的必不小于上一次选择元素
                list.add(candidates[i]);
                combinationSum2(list, candidates, n - candidates[i], i);
                //回溯
                list.remove(list.size() - 1);
            }
        }
    }
    */

    /*

    public List<List<Integer>> solution(int[] candidates, int target) {
        ansList = new ArrayList<>();
        //有一个数组标记每个元素是否被使用，局部boolean数组变量，每个值初始化为false
        boolean[] tag = new boolean[candidates.length];
        combinationSum2(new ArrayList<>(), candidates, tag, target);
        return ansList;
    }

    //list表示当前已经选取的数字，tag表示每个元素是否已被使用，n表示当前的目标值
    //函数结束，得到使用candidates数组中元素总和等于n，且每个元素只能用一次，结果存于ansList
    public void combinationSum2(List<Integer> list, int[] candidates, boolean[] tag, int n) {
        if (n == 0) {
            List<Integer> newList = new ArrayList<>(list);
            ansList.add(newList);
        } else if (n > 0) {
            //选择元素--要不小于上一次选择，且该元素没被用过
            for (int i = 0; i < candidates.length; i++) {
                int len = list.size();
                boolean flag = (len == 0 || candidates[i] >= list.get(len - 1));
                if (flag && !tag[i]) {
                    list.add(candidates[i]);
                    tag[i] = true;
                    combinationSum2(list, candidates, tag, n - candidates[i]);
                    //回溯
                    list.remove(list.size() - 1);
                    tag[i] = false;
                }
            }
        }
    }
    */

    public static void main(String[] args) {
        CombinationSumNo2 c = new CombinationSumNo2();
        int[] arr = new int[]{10, 1, 2, 7, 6, 1, 5};
        c.solution(arr, 8);
        //注意上面例子中会出现[1,2,5]和[1,2,5]是两种相同的答案
    }
}
