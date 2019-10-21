//73. 矩阵置零
//
//给定一个 m x n 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用原地算法。


package src;

import java.util.HashSet;
import java.util.Set;

public class SetMatrixZeroes {
    //O(m+n)额外空间
    public void solution1(int[][] matrix) {
        if (matrix == null)
            return;

        //需要置零的行号
        Set<Integer> row = new HashSet<>();
        //需要置零的列号
        Set<Integer> column = new HashSet<>();

        //遍历一遍，统计出现元素0的位置
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row.add(i);
                    column.add(j);
                }
            }
        }

        //0元素所在行、列需全设为零
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (row.contains(i) || column.contains(j))
                    matrix[i][j] = 0;
            }
        }
    }

    //先设置"临时值"，然后把"临时值"设置成0
    //注意"临时值"不能覆盖原本就是0的元素，否则影响后续判断
    public void solution2(int[][] matrix) {
        if (matrix == null)
            return;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    //将其行列上的元素设置成临时值
                    for (int k = 0; k < matrix.length; k++) {
                        if (matrix[k][j] != 0)
                            matrix[k][j] = Integer.MIN_VALUE;
                    }
                    for (int k = 0; k < matrix[0].length; k++) {
                        if (matrix[i][k] != 0)
                            matrix[i][k] = Integer.MIN_VALUE;
                    }
                }
            }
        }

        //临时值转化为0
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == Integer.MIN_VALUE)
                    matrix[i][j] = 0;
            }
        }
    }

    //每行每列的第一个元素来标记当前行列是否要置零
    //第一行和第一列的第一个元素相同，所以要用一个额外变量来标记
    public void solution3(int[][] matrix) {
        if (matrix == null)
            return;

        boolean isCol = false;

        for (int i = 0; i < matrix.length; i++) {
            //用新的变量标记第一列是否需要置零
            if (matrix[i][0] == 0) {
                isCol = true;
            }
            //排除第一列的情况，从j=1开始遍历
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        //本来是下面这样，但是这样会破坏我们的标记
        //for (int i = 0; i < matrix.length; i++) {
        //    for (int j = 0; j < matrix[0].length; j++) {
        //        if (matrix[i][0] == 0 || matrix[0][j] == 0)
        //            matrix[i][j] = 0;
        //    }
        //}

        //分开讨论
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0)
                    //不会影响到标记
                    matrix[i][j] = 0;
            }
        }

        //行
        if (matrix[0][0] == 0) {
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }

        //列
        if (isCol) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public static void main(String[] args) {
        SetMatrixZeroes s = new SetMatrixZeroes();
        s.solution3(new int[][]{
                {0, 1, 2, 0},
                {3, 4, 5, 2},
                {1, 3, 1, 5}
        });
    }
}
