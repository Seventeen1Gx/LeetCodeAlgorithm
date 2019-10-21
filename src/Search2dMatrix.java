//74. 搜索二维矩阵
//
//编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
//每行中的整数从左到右按升序排列。
//每行的第一个整数大于前一行的最后一个整数。



package src;

public class Search2dMatrix {
    //先找在哪行，然后找在哪列
    public boolean solution1(int[][] matrix, int target) {
        //非法情况
        if (matrix == null)
            return false;

        if (matrix.length == 0)
            return false;

        if (matrix[0].length == 0)
            return false;

        //小于矩阵最小值
        if (matrix[0][0] > target)
            return false;

        //找出target在矩阵哪一行中
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i][0] == target) {
                return true;
            } else if (matrix[i][0] < target && matrix[i + 1][0] > target) {
                return binarySearch(matrix[i], target);
            }
        }

        //在最后一行中寻找
        return binarySearch(matrix[matrix.length - 1], target);
    }

    //二分查找
    private boolean binarySearch(int[] a, int target) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] > target) {
                high = mid - 1;
            } else if (a[mid] < target) {
                low = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    //将二维矩阵视为一维矩阵进行二分查找，关键在于下标转换
    public boolean solution2(int[][] matrix, int target) {
        int m = matrix.length;

        if (m == 0)
            return false;

        int n = matrix[0].length;

        int low = 0;
        int high = m * n - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int element = matrix[mid / n][mid % n];
            if (element > target) {
                high = mid - 1;
            } else if (element < target) {
                low = mid + 1;
            } else {
                return true;
            }
        }

        return false;
    }
}
