// 733. 图像渲染
//
// 有一幅以二维整数数组表示的图画，每一个整数表示该图画的像素值大小，数值在 0 到 65535 之间。
//
// 给你一个坐标 (sr, sc) 表示图像渲染开始的像素值（行 ，列）和一个新的颜色值 newColor，让你重新上色这幅图像。
//
// 为了完成上色工作，从初始坐标开始，记录初始坐标的上下左右四个方向上/像素值与初始坐标相同的/相连像素点
// 接着再记录这四个方向上符合条件的像素点与他们对应四个方向上像素值与初始坐标相同的相连像素点，……，重复该过程。将所有有记录的像素点的颜色值改为新的颜色值。
//
// 最后返回经过上色渲染后的图像。


package src;

import java.util.*;

public class FloodFill {
    // 单源出发，BFS 和 DFS 都可以

    int oldColor, newColor;
    int nr, nc;
    int[][] direction = {
            {0, 1},
            {0, -1},
            {1, 0},
            {-1, 0}
    };

    public int[][] solution(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) {
            return image;
        }

        this.oldColor = image[sr][sc];
        this.newColor = newColor;
        this.nr = image.length;
        this.nc = image[0].length;

        dfs(sr, sc, image);
        return image;
    }

    private void dfs(int r, int c, int[][] image) {
        if (r < 0 || r >= nr || c < 0 || c >= nc || image[r][c] != oldColor) {
            return;
        }
        image[r][c] = newColor;
        for (int[] next : direction) {
            dfs(r + next[0], c + next[1], image);
        }
    }


    public int[][] solution2(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor) {
            return image;
        }

        int oldColor = image[sr][sc];
        int nr = image.length;
        int nc = image[0].length;

        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{sr, sc});
        while (!queue.isEmpty()) {
            int[] n = queue.poll();
            image[n[0]][n[1]] = newColor;
            for (int[] next : direction) {
                int newRow = n[0] + next[0];
                int newCol = n[1] + next[1];
                if (newRow >= 0 && newRow < nr
                        && newCol >= 0 && newCol < nc
                        && image[newRow][newCol] == oldColor) {
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        return image;
    }
}
