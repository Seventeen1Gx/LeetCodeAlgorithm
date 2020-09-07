// 60. 第k个排列
//
// 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
//
// 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
//
// "123"
// "132"
// "213"
// "231"
// "312"
// "321"
// 给定 n 和 k，返回第 k 个排列。
//
// 说明：
// 给定 n 的范围是 [1, 9]。
// 给定 k 的范围是 [1, n!]。



package src;

import java.util.ArrayList;
import java.util.List;

public class PermutationSequence {
    public String solution(int n, int k) {
        // 其实可以回溯方法，直到算到第 k 个排列
        // 或者采用下一个排列的方法，计算 k 次
        // 但其实第 k 个排列是有规律的
        // 比如 12345 五个数字的排列共 5!=120，求第 35 个排列
        // 而开头分别为 1、2、3、4、5 组成的排列分别有 24 个，而 24<35<48，则第 35 个排列开头数字为 2
        // 然后求 1345 组成的排列中的第 35-24=11 个，同理以 1、3、4、5 开头的排列分别有 6 个
        // 而 6<11<12，故选择 3 开头，再求 145 组成的排列中的第 11-6=5 个
        // 同理以 1、4、5 开头的排列分别有 2 个，而 4<5<6，故选 5 开头，再选 14 组成排列的第 5-4=1 个，即正序，14

        StringBuffer sb = new StringBuffer();
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
        recursion(sb, nums, k);
        return sb.toString();
    }

    private void recursion(StringBuffer sb, List<Integer> nums, int k) {
        // 将 nums 中的可用数字组成的排列中的第 k 个排列，接到 sb 之后

        // 可用数字个数
        int n = nums.size();

        if (n == 1) {
            sb.append(nums.get(0));
        } else {
            // 分别取 nums 中的可用数字作为开头数字，剩下数字可组成的排列数为 m=(n-1)!
            int m = 1;
            n -= 1;
            while (n > 0) {
                m *= n;
                n--;
            }

            // 寻找 (i-1)*m<k<=i*m，则选择可选数字中的第 i 个为开头
            int i = 1;
            while (i * m < k) {
                i++;
            }

            sb.append(nums.get(i - 1));
            nums.remove(i - 1);
            // 尾递归，可改成非递归
            recursion(sb, nums, k - (i - 1) * m);
        }
    }

    public static void main(String[] args) {
        PermutationSequence p = new PermutationSequence();
        p.solution(5, 35);
    }
}
