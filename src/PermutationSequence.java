// 60. 第 k 个排列
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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PermutationSequence {
    public String solution(int n, int k) {
        // 第 k 个排列是有规律的
        // 比如 12345 五个数字的排列共 5!=120，求第 35 个排列
        // 而开头分别为 1、2、3、4、5 组成的排列分别有 24 个，而 24<35<48，则第 35 个排列开头数字为 2
        // 然后求 1345 组成的排列中的第 35-24=11 个，同理以 1、3、4、5 开头的排列分别有 6 个
        // 而 6<11<12，故选择 3 开头，再求 145 组成的排列中的第 11-6=5 个
        // 同理以 1、4、5 开头的排列分别有 2 个，而 4<5<6，故选 5 开头，再选 14 组成排列的第 5-4=1 个，即正序，14

        // 可用数字
        List<Integer> nums = IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());

        int m = 1;
        while (n > 0) {
            m *= n;
            n--;
        }

        StringBuilder sb = new StringBuilder();
        while ((n = nums.size()) > 1) {
            // m=(n-1)!
            m /= n;

            // 寻找大于 k / m 的最小整数 i
            int i = (int) Math.ceil(1.0 * k / m);

            sb.append(nums.get(i - 1));
            nums.remove(i - 1);

            k -= m * (i - 1);
        }

        sb.append(nums.get(0));
        return sb.toString();
    }
}
