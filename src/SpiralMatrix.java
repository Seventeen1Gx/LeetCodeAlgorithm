// 54. 螺旋矩阵
//
// 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。


package src;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> solution(int[][] matrix) {
        List<Integer> answer = new ArrayList<>();

        if (matrix.length == 0) return answer;

        //记录4个边(上下左右)的界限，碰到边界则转向
        int up = 0, right = matrix[0].length - 1, down = matrix.length - 1, left = 0;

        //martrix[i][j]为当前处理的位置
        int i = 0, j = 0;
        //0、1、2、3分别表示当前正向右、下、左、上
        int direction = 0;
        for (int k = 0; k < matrix.length * matrix[0].length; k++) {
            answer.add(matrix[i][j]);
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
        return answer;
    }

    public static void main(String[] args) {
        //int[][] matrix = new int[][]{
        //        {1, 2, 3},
        //        {4, 5, 6},
        //        {7, 8, 9}
        //};

        int[][] matrix = new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };


        SpiralMatrix s = new SpiralMatrix();
        s.solution(matrix);
    }
}
