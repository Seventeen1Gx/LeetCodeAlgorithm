// 454. 给定四个包含整数的数组列表 A , B , C , D ,计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0。
//
// 为了使问题简单化，所有的 A, B, C, D 具有相同的长度 N，且 0 ≤ N ≤ 500 。所有整数的范围在 -228 到 228 - 1 之间，最终结果不会超过 231 - 1 。


package src;

import java.util.HashMap;
import java.util.Map;

public class FourNumNo2 {

    private static final int REQUIRE_NUM = 4;

    // A与B作笛卡尔积，组成新数组num1[]，C与D作笛卡尔积，组成新数组num2[]
    // 问题转化为两数之和
    public int solution(int[] A, int[] B, int[] C, int[] D) {
        if (A.length + B.length + C.length + D.length < REQUIRE_NUM) {
            return 0;
        }

        int[] num1 = new int[A.length * B.length];
        int[] num2 = new int[C.length * D.length];

        int p1 = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                num1[p1++] = A[i] + B[j];
            }
        }

        // 整数与其出现次数的键值对
        Map<Integer, Integer> map = new HashMap<>(num2.length);

        int p2 = 0;
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                num2[p2] = C[i] + D[j];
                // 将num2放入查找表
                if (map.containsKey(num2[p2])) {
                    map.put(num2[p2], map.get(num2[p2]) + 1);
                } else {
                    map.put(num2[p2], 1);
                }
                p2++;
            }
        }

        int ans = 0;
        // 遍历num1的每个元素，去寻找num2里有无相反数
        for (int i = 0; i < num1.length; i++) {
            if (map.containsKey(-num1[i])) {
                ans += map.get(-num1[i]);
            }
        }
        return ans;
    }
}
