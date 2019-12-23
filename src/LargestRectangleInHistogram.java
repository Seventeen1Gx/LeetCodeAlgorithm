//84. 柱状图中的最大矩形
//
//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
//
//求在该柱状图中，能够勾勒出来的矩形的最大面积。


package src;


public class LargestRectangleInHistogram {
    //中心扩散法
    public int solution1(int[] heights) {
        int maxArea = 0, area;

        for (int i = 0; i < heights.length; i++) {
            int j = i - 1, k = i + 1;

            area = 0;
            //尽可能往左边扩充
            while (j >= 0 && heights[j] >= heights[i]) {
                j--;
            }
            area += (i - j) * heights[i];
            //尽可能往右边扩充
            while (k < heights.length && heights[k] >= heights[i]) {
                k++;
            }
            area += (k - i - 1) * heights[i];

            if (area > maxArea)
                maxArea = area;
        }
        return maxArea;
    }

    //分治(左边、右边、穿过中间)
    public int solution2(int[] heights) {
        if (heights == null || heights.length == 0)
            return 0;
        return recursion(heights, 0, heights.length - 1);
    }

    //求[left, right]之间柱状图的最大矩形
    public int recursion(int[] heights, int left, int right) {
        if (left == right) {
            return heights[left];
        } else {
            int mid = (left + right) / 2;
            //左边
            int leftMaxArea = recursion(heights, left, mid);
            //右边
            int rightMaxArea = recursion(heights, mid + 1, right);
            //穿过中间
            int minHeight = Math.min(heights[mid], heights[mid + 1]);
            int i = mid - 1, j = mid + 2;
            //尽量向左边扩充
            while (i >= left && heights[i] >= minHeight) {
                i--;
            }
            //尽量向右边扩充
            while (j <= right && heights[j] >= minHeight) {
                j++;
            }
            return Math.max(Math.max(leftMaxArea, rightMaxArea), (j - i - 1) * minHeight);
        }
    }

    public static void main(String[] args) {
        int[] heights = new int[]{
            2, 1, 5, 6, 2, 3
        };
        LargestRectangleInHistogram l = new LargestRectangleInHistogram();
        l.solution2(heights);
    }
}
