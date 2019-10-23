//77. 组合
//
//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。


package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Combinations {
    //每个元素取或不取
    //这里的角度是按顺序考虑1~n的每个元素
    public List<List<Integer>> solution1(int n, int k) {
        if (k > n)
            return null;

        List<List<Integer>> ans = new ArrayList<>();
        recursion(ans, new ArrayList<>(), n, k);
        return ans;
    }

    //在1,2,3,...,n中选k个数
    private void recursion(List<List<Integer>> results, List<Integer> result, int n, int k) {
        if (n >= k && k != 0) {
            result.add(n);
            recursion(results, result, n - 1, k - 1);
            result.remove(result.size() - 1);
            recursion(results, result, n - 1, k);
        } else if (k == 0) {
            List<Integer> temp = new ArrayList<>();
            for (Integer i : result) {
                temp.add(i);
            }
            results.add(temp);
        }
    }


    //官方回溯代码
    //这里的角度是考虑每个list添加第i个元素，可选项中选一个
    List<List<Integer>> output = new LinkedList();
    int n;
    int k;

    public List<List<Integer>> solution2(int n, int k) {
        this.n = n;
        this.k = k;
        backtrack(1, new LinkedList<Integer>());
        return output;
    }

    public void backtrack(int first, LinkedList<Integer> curr) {
        // if the combination is done
        if (curr.size() == k)
            output.add(new LinkedList(curr));

        for (int i = first; i < n + 1; ++i) {
            // add i into the current combination
            curr.add(i);
            // use next integers to complete the combination
            backtrack(i + 1, curr);
            // backtrack
            curr.removeLast();
        }
    }

    //官方字典序（二进制排序）方法
    //走一遍例子即可，发现是按字典序添加的结果
    public List<List<Integer>> solution3(int n, int k) {
        LinkedList<Integer> nums = new LinkedList<Integer>();
        //nums初始化为1~k,n+1，最后一个元素为哨兵
        for(int i = 1; i < k + 1; ++i)
            nums.add(i);
        nums.add(n + 1);

        List<List<Integer>> output = new ArrayList<List<Integer>>();
        int j = 0;
        while (j < k) {
            //nums前k个元素，为一个组合
            output.add(new LinkedList(nums.subList(0, k)));
            //从头开始，找到nums中的第一个满足 nums[j] + 1 != nums[j + 1]的元素
            //将其加1，变为下一组合
            j = 0;
            while ((j < k) && (nums.get(j + 1) == nums.get(j) + 1))
                //j元素设置成j+1，然后j后移一位
                nums.set(j, j++ + 1);
            //j元素的值加1
            nums.set(j, nums.get(j) + 1);
        }
        return output;
    }

    public static void main(String[] args) {
        Combinations c = new Combinations();
        c.solution3(4, 2);
    }
}
