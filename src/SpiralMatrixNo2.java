//59. 螺旋矩阵Ⅱ
//
//给定一个正整数 n，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的正方形矩阵。
//
//54题为螺旋矩阵1，区别是那题给定矩阵，螺旋输出，这题是螺旋构造矩阵


package src;

public class SpiralMatrixNo2 {
    public int[][] solution(int n) {
        if (n == 0)
            return null;

        int[][] matrix = new int[n][n];

        //记录4个边(上下左右)的界限，碰到边界则转向
        int up = 0, right = n - 1, down = n - 1, left = 0;

        //martrix[i][j]为当前处理的位置
        int i = 0, j = 0;
        //0、1、2、3分别表示当前正向右、下、左、上
        int direction = 0;
        for (int k = 0; k < n * n; k++) {
            matrix[i][j] = k + 1;
            switch (direction) {
                case 0:
                    //向右
                    if (j == right) {
                        //已处理到最右边界，开始向下处理
                        direction = 1;
                        i++;
                        //上边界缩小
                        up++;
                    } else {
                        j++;
                    }
                    break;
                case 1:
                    //向下
                    if (i == down) {
                        //已处理到最下边界，开始向左处理
                        direction = 2;
                        j--;
                        //右边界缩小
                        right--;
                    } else {
                        i++;
                    }
                    break;
                case 2:
                    //向左
                    if (j == left) {
                        //已处理到最左边界，开始向上处理
                        direction = 3;
                        i--;
                        //下边界缩小
                        down--;
                    } else {
                        j--;
                    }
                    break;
                case 3:
                    //向上
                    if (i == up) {
                        //已处理到最上边界，开始向右处理
                        direction = 0;
                        j++;
                        //左边界缩小
                        left++;
                    } else {
                        i--;
                    }
                    break;
            }
        }
        return matrix;
    }
}
