//51. N皇后
//
//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
//
//给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
//
//每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。


package src;

import java.util.ArrayList;
import java.util.List;

public class NQueen {
    //典型的回溯法，而回溯法画出dfs的树就一目了然
    public List<List<String>> solution(int n) {
        //初始化
        List<int[]> result = new ArrayList<>();
        int[] q = new int[n];

        //得到结果列表
        solveNQueen(result, q, n, 0);

        //根据结果转换成答案形式
        List<List<String>> answer = new ArrayList<>();
        for (int[] ans : result) {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                StringBuffer sb = new StringBuffer();
                for (int j = 0; j < n; j++) {
                    if (ans[i] == j)
                        sb.append('Q');
                    else
                        sb.append('.');
                }
                strings.add(sb.toString());
            }
            answer.add(strings);
        }
        return answer;
    }

    //返回一个一维数组的列表，每个一维数组代表一种解法，即其第i个元素表示第i行皇后放第i列
    //k表示现在处理第k行
    public void solveNQueen(List<int[]> result, int[] q, int n, int k) {
        if (n == k) {
            int[] newQ = new int[n];
            for (int i = 0; i < n; i++) {
                newQ[i] = q[i];
            }
            //获得一个解，加入到结果列表中
            result.add(newQ);
        } else {
            //在第k行上放置一个皇后
            for (int i = 0; i < n; i++) {
                if (check(q, k, i)) {
                    q[k] = i;
                    solveNQueen(result, q, n, k + 1);
                    //回溯
                    //无需处理，会覆盖
                }
            }
        }
    }

    //在第k行第j列放置皇后是否和之前行的皇后冲突
    public boolean check(int[] q, int k, int j) {
        //因为代码过程中确保了每行只放一个皇后，所以行上不会冲突

        //检查列冲突
        for (int i = 0; i < k; i++) {
            if (q[i] == j)
                return false;
        }

        //检查斜向冲突
        for (int i = 0; i < k; i++) {
            if (k - i == Math.abs(q[i] - j))
                return false;
        }

        return true;
    }
}
