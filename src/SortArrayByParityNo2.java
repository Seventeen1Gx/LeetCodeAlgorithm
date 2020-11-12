// 922. 按奇偶排序数组 II
//
// 给定一个非负整数数组 A， A 中一半整数是奇数，一半整数是偶数。
//
// 对数组进行排序，以便当 A[i] 为奇数时，i 也是奇数；当 A[i] 为偶数时， i 也是偶数


package src;

public class SortArrayByParityNo2 {
    public int[] solution1(int[] A) {
        // 转移到新数组中

        int n = A.length;

        int[] B = new int[n];

        int i = 0, j = 1;

        for (int x : A) {
            if (x % 2 == 0) {
                B[i] = x;
                i += 2;
            } else {
                B[j] = x;
                j += 2;
            }
        }

        return B;
    }

    public int[] solution2(int[] A) {
        // 原数组中处理

        int n = A.length;

        int i = 0, j = 1, t;
        while (i < n && j < n) {
            while (i < n && A[i] % 2 == 0) {
                i += 2;
            }
            while (j < n && A[j] % 2 == 1) {
                j += 2;
            }
            if (i < n && j < n) {
                t = A[i];
                A[i] = A[j];
                A[j] = t;
            }
        }

        return A;
    }
}
