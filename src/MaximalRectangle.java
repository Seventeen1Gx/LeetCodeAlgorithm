//85. 最大矩形
//
//给定一个仅包含 0 和 1 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。


package src;

import java.util.Arrays;

public class MaximalRectangle {
    //暴力法
    public int solution1(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }

        //width[i][j]表示matrix[i][j]开始，往前有多少个连续的'1'
        int[][] width = new int[matrix.length][matrix[0].length];

        int maxArea = 0;

        //遍历matrix，每次求以matrix[i][j]为右下角的矩形面积
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                //计算width[i][j]
                if (matrix[i][j] == '1') {
                    if (j == 0) {
                        width[i][j] = 1;
                    } else {
                        width[i][j] = width[i][j - 1] + 1;
                    }
                } else {
                    width[i][j] = 0;
                }


                //当前元素为'0'，以该元素为右下角无法形成矩形
                if (width[i][j] == 0) {
                    continue;
                }


                //midWith记录向上扩展过程中的最小矩形宽度，即遍历到的width[i][j]、width[i-1][j]、...，中的最小值
                int minWidth = width[i][j];

                //向上扩展，依次寻求高度为1，高度为2，高度为3，...，的最大矩形面积
                for (int k = i; k >= 0; k--) {
                    minWidth = Math.min(minWidth, width[k][j]);
                    maxArea = Math.max(maxArea, minWidth * (i - k + 1));
                }

            }
        }
        return maxArea;
    }


    //按行遍历，可知当前行，每个元素竖向最大高度
    //考虑当前行及当前行之前的部分中的最大矩形
    //问题就变成了跟84题相同
    public int solution2(char[][] matrix) {
        int maxArea = 0;

        if (matrix.length == 0) {
            return maxArea;
        }

        //height[col]为matrix[row][col]竖向连续'1'的个数
        int[] height = new int[matrix[0].length];

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                height[col] = matrix[row][col] == '1' ? height[col] + 1 : 0;
            }

            //调用84题的方法
            LargestRectangleInHistogram l = new LargestRectangleInHistogram();
            l.solution3_official(height);
        }

        return 0;
    }


    //当row=0时，我们能获取leftLessMin和rightLessMin计算柱形图中最大矩形
    //当row增加一行时，如果全部每列都加了一个1，则leftLessMin和rightLessMin不用改变
    //可惜，有时候新增的一行中含有0，从而将那一列的柱高变成0
    //所以我们在增加一行时，对leftLessMin和rightLessMin进行更新
    public int solution3(char[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }

        int maxArea = 0;

        int cols = matrix[0].length;

        //leftLessMin[i]记录i列左边第一次出现的更小柱子标号
        int[] leftLessMin = new int[cols];
        //rightLessMin[i]记录i列右边第一次出现的更小柱子标号
        int[] rightLessMin = new int[cols];
        //初始化
        Arrays.fill(leftLessMin, -1);
        Arrays.fill(rightLessMin, cols);

        int[] heights = new int[cols];
        for (int row = 0; row < matrix.length; row++) {
            //更新当前行的每列高度
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    heights[col] += 1;
                } else {
                    heights[col] = 0;
                }
            }

            //从左往右更新leftLessMin的每个值
            //boundary记录从左往右遍历途中，上次出现0的位置
            int boundary = -1;
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    //和上次出现 0 的位置比较，保存较大结果
                    //因为leftLessMin[col]是看col列往左数，第一次出现的更小柱高
                    leftLessMin[col] = Math.max(leftLessMin[col], boundary);
                } else {
                    //当前是 0 代表当前高度是 0，所以初始化为 -1，防止对下次一行的更新产生影响
                    leftLessMin[col] = -1;
                    //更新 0 的位置
                    boundary = col;
                }
            }
            //右边同理
            boundary = cols;
            for (int col = cols - 1; col >= 0; col--) {
                if (matrix[row][col] == '1') {
                    rightLessMin[col] = Math.min(rightLessMin[col], boundary);
                } else {
                    rightLessMin[col] = cols;
                    boundary = col;
                }
            }

            //更新所有面积
            for (int col = cols - 1; col >= 0; col--) {
                int area = (rightLessMin[col] - leftLessMin[col] - 1) * heights[col];
                maxArea = Math.max(area, maxArea);
            }

        }
        return maxArea;
    }

    public static void main(String[] args) {
        char[][] example = new char[][]{
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'},
        };

        MaximalRectangle m = new MaximalRectangle();
        m.solution1(example);
    }
}
