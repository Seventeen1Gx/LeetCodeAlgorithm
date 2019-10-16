//48. 旋转图像
//
//给定一个 n × n 的二维矩阵表示一个图像。
//
//将图像顺时针旋转 90 度。
//
//说明：
//
//你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。


package src;

public class RotateImage {
    //先转置，然后左右翻转
    public void solution1(int[][] matrix) {
        //判断非法情况
        if (matrix == null)
            return;

        //转置
        int n = matrix.length, temp;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        //左右翻转
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = temp;
            }
        }
    }

    //官方解法二
    //每次旋转4个对应位置的小格子(由外圈到内圈，4是因为4条边)
    public void solution2_official(int[][] matrix) {
        int n = matrix.length;
        //遍历范围为
        //n为奇数时，遍历前一半的行，且包括正中间的行，n为偶数时，遍历前一半的行
        //n为奇数时，遍历前一半的列，不包括正中间的列，n为偶数时，遍历前一半的列
        //直观的看，按行序遍历左上角的小矩形
        for (int i = 0; i < n / 2 + n % 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                int[] tmp = new int[4];
                int row = i;
                int col = j;
                //获取要旋转的对应4个格子中的数
                for (int k = 0; k < 4; k++) {
                    tmp[k] = matrix[row][col];
                    int x = row;
                    row = col;
                    col = n - 1 - x;
                }
                //对获取的4个位置的数旋转，即放到他们旋转后该待在的位置
                //即从第2个数字开始从第一个位置放
                for (int k = 0; k < 4; k++) {
                    matrix[row][col] = tmp[(k + 3) % 4];
                    int x = row;
                    row = col;
                    col = n - 1 - x;
                }
            }
        }
    }

    //官方解法二的进一步精简版
    //关键是要抓住这4个位置的下标间的关系，从而实现知道第一个数的位置，推出剩下3个数的位置
    public void solution3_official(int[][] matrix) {
        int n = matrix.length;
        //同样是按行序遍历左上角的那块小矩形
        for (int i = 0; i < (n + 1) / 2; i ++) {
            for (int j = 0; j < n / 2; j++) {
                //不用tmp[]，直接4个位置进行旋转
                int temp = matrix[n - 1 - j][i];
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - j - 1];
                matrix[n - 1 - i][n - j - 1] = matrix[j][n - 1 -i];
                matrix[j][n - 1 - i] = matrix[i][j];
                matrix[i][j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        RotateImage r = new RotateImage();
        r.solution1(matrix);
    }
}
