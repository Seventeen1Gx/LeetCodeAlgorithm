//52. N皇后Ⅱ
//
//跟51题比，该题只需求解的个数

package src;

public class NQueenNo2 {
    int count;

    int solution(int n) {
        count = 0;
        solveNQueen(new int[n], n, 0);
        return count;
    }

    //q[]中表示k行之前皇后的摆放情况，现要处理第k行
    public void solveNQueen(int[] q, int n, int k) {
        if (n == k) {
            count++;
        } else {
            for (int i = 0; i < n; i++) {
                if (check(q, k, i)) {
                    q[k] = i;
                    solveNQueen(q, n, k + 1);
                }
            }
        }
    }

    //在第k行第j列放置皇后是否和之前行的皇后冲突
    public boolean check(int[] q, int k, int j) {
        for (int i = 0; i < k; i++) {
            if (q[i] == j)
                return false;
        }

        for (int i = 0; i < k; i++) {
            if (k - i == Math.abs(q[i] - j))
                return false;
        }

        return true;
    }
}
