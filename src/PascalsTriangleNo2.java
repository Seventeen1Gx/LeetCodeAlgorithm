package src;

import java.util.ArrayList;
import java.util.List;

/**
 * 118. 杨辉三角Ⅱ
 *
 * 给定一个非负索引 k，其中 k ≤ 33，返回杨辉三角的第 k 行。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class PascalsTriangleNo2 {
    /**
     * 利用上一层，求本层
     */
    public List<Integer> solution1(int rowIndex) {
        List<Integer> preList = new ArrayList<>();
        List<Integer> curList = new ArrayList<>();

        // 每次创建第 i 行结果，行号从 0 开始计数
        for (int i = 0; i <= rowIndex; i++) {
            curList = new ArrayList<>();
            // 第 i 行有 i + 1 个元素
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    curList.add(1);
                } else {
                    curList.add(preList.get(j - 1) + preList.get(j));
                }
            }
            preList = curList;
        }
        return curList;
    }

    /**
     * 公式法
     * 第 i 行元素为 C_i^0，C_i^1，... ，C_i^i
     * 组合数计算公式
     * C_n^k = n!/(k!(n-k)!)
     * C_n^k = C_n^{k-1} * (n-k+1) / k
     */
    public List<Integer> solution2(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        list.add(1);

        // 从第2个元素开始添加
        long pre = 1;
        for (int i = 1; i <= rowIndex; i++) {
            long cur = pre * (rowIndex - i + 1) / i;
            list.add((int) cur);
            pre = cur;
        }
        return list;
    }
}
