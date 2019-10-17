//60. 第k个排列
//
//给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
//
//按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
//
//"123"
//"132"
//"213"
//"231"
//"312"
//"321"
//给定 n 和 k，返回第 k 个排列。
//
//说明：
//给定 n 的范围是 [1, 9]。
//给定 k 的范围是[1,  n!]。



package src;

import java.util.ArrayList;
import java.util.List;

public class PermutationSequence {
    //其实可以回溯方法，直到算到第k个排列
    //或者采用下一个排列的方法，计算k次
    //但其实第k个排列是有规律的
    //比如12345五个数字的排列共5!=120，求第35个排列
    //而开头分别为1、2、3、4、5组成的排列分别由24个，而24<35<48，则第35个排列开头数字为2
    //然后求1345组成的排列中的第35-24=11个，同理以1、3、4、5开头的排列分别有6个
    //而6<11<12，故选择3开头，再求145组成的排列中的第11-6=5个
    //同理以1、4、5开头的排列分别有2个，而4<5<6，故选5开头，再选14组成排列的第5-4=1个，即正序，14
    public String solution(int n, int k) {
        StringBuffer sb = new StringBuffer();
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }
        recursion(sb, nums, k);
        return sb.toString();
    }

    //递归处理--尾递归，可改为非递归
    //将nums中的可用数字组成的排列中的第k个排列，接到sb之后
    private void recursion(StringBuffer sb, List<Integer> nums, int k) {
        //可用数字个数
        int n = nums.size();

        if (n == 1) {
            sb.append(nums.get(0));
        } else {
            //分别取memo中的可用数字作为开头数字，剩下数字可组成的排列数为m=(n-1)!
            int m = 1;
            n -= 1;
            while (n > 0) {
                m *= n;
                n--;
            }

            //寻找(i-1)*m<k<=i*m，则选择可选数字中的第i个为开头
            int i = 1;
            while (i * m < k) i++;
            sb.append(nums.get(i - 1));
            nums.remove(i - 1);
            recursion(sb, nums, k - (i - 1) * m);
        }
    }

    public static void main(String[] args) {
        PermutationSequence p = new PermutationSequence();
        p.solution(5, 35);
    }
}
