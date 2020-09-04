// 52. N皇后Ⅱ
//
// 跟 51 题比，该题只需求解的个数

package src;

public class NQueenNo2 {
    int count;

    int solution(int n) {
        count = 0;
        solveNQueen(new int[n], n, 0);
        return count;
    }

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

    public boolean check(int[] q, int k, int j) {
        for (int i = 0; i < k; i++) {
            if (q[i] == j) {
                return false;
            }
        }

        for (int i = 0; i < k; i++) {
            if (k - i == Math.abs(q[i] - j)) {
                return false;
            }
        }

        return true;
    }
}
