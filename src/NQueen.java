// 51. N皇后
//
// n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
// 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
//
// 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueen {
    public List<List<String>> solution(int n) {
        //典型的回溯法，而回溯法画出dfs的树就一目了然

        List<int[]> result = new ArrayList<>();
        // q[i] = k 表示第 i 行的皇后放在 k 列上
        int[] q = new int[n];

        // 得到结果列表
        solveNQueen(result, q, n, 0);

        // 根据结果转换成答案形式
        List<List<String>> answer = new ArrayList<>();
        for (int[] ans : result) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < n; j++) {
                    if (ans[i] == j) {
                        sb.append('Q');
                    } else {
                        sb.append('.');
                    }
                }
                strings.add(sb.toString());
            }
            answer.add(strings);
        }
        return answer;
    }

    public void solveNQueen(List<int[]> result, int[] q, int n, int k) {
        // 现在在处理第 k 行
        if (n == k) {
            int[] newQ = Arrays.copyOf(q, n);
            result.add(newQ);
        } else {
            // 在第 k 行上放置一个皇后
            // 试验每列的可能性
            for (int i = 0; i < n; i++) {
                if (check(q, k, i)) {
                    q[k] = i;
                    solveNQueen(result, q, n, k + 1);
                    // 回溯
                    // 无需处理，会覆盖
                }
            }
        }
    }

    public boolean check(int[] q, int k, int j) {
        // 在第 k 行第 j 列放置皇后是否和之前行的皇后冲突

        // 因为代码过程中确保了每行只放一个皇后，所以行上不会冲突

        // 而且是一行一行放下来的，所以检查 k 之前的行即可

        // 检查列冲突
        for (int i = 0; i < k; i++) {
            if (q[i] == j) {
                return false;
            }
        }

        // 检查斜向冲突
        for (int i = 0; i < k; i++) {
            if (k - i == Math.abs(q[i] - j)) {
                return false;
            }
        }

        return true;
    }
}
