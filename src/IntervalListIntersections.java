// 986. 区间列表的交集
//
// 给定两个由一些 闭区间 组成的列表，每个区间列表都是成对不相交的，并且已经排序。
//
// 返回这两个区间列表的交集。
//
//（形式上，闭区间 [a, b]（其中 a <= b）表示实数 x 的集合，而 a <= x <= b。
// 两个闭区间的交集是一组实数，要么为空集，要么为闭区间。例如，[1, 3] 和 [2, 4] 的交集为 [2, 3]。）


package src;

import java.util.ArrayList;
import java.util.List;

public class IntervalListIntersections {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> ans = new ArrayList<>();

        // 双指针
        // A[i] 表示 A 中的第 i 个区间
        // B[j] 表示 B 中的第 j 个区间
        int i = 0, j = 0;

        while (i < A.length && j < B.length) {
            // 左端点选大的
            int lo = Math.max(A[i][0], B[j][0]);
            // 右端点选小的
            int hi = Math.min(A[i][1], B[j][1]);
            if (lo <= hi) {
                // 说明相交
                ans.add(new int[]{lo, hi});
            }

            // 右端点小的区间被淘汰
            if (A[i][1] < B[j][1]) {
                i++;
            } else {
                j++;
            }
        }

        return ans.toArray(new int[ans.size()][]);
    }


}
