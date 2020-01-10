package src;

import java.util.ArrayList;
import java.util.List;

/**
 * 118. 杨辉三角
 *
 * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class PascalsTriangle {
    public List<List<Integer>> solution(int numRows) {
        List<List<Integer>> results = new ArrayList<>();
        // 从上到下遍历行
        for (int i = 0; i < numRows; i++) {
            results.add(new ArrayList<>());
            // 从左到右遍历每行的元素
            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) {
                    results.get(i).add(1);
                } else {
                    results.get(i).add(results.get(i - 1).get(j - 1) + results.get(i - 1).get(j));
                }
            }
        }
        return results;
    }
}
