//84. 柱状图中的最大矩形
//
//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
//
//求在该柱状图中，能够勾勒出来的矩形的最大面积。


package src;


import java.util.Stack;

public class LargestRectangleInHistogram {
    //中心扩散法：每次假定中心是最终矩形的高
    public int solution1(int[] heights) {
        int maxArea = 0, area;

//        int ansLeft = -1, ansRight = -1;

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

            if (area > maxArea) {
                maxArea = area;
//                ansLeft = j + 1;
//                ansRight = k - 1;
            }
        }
        return maxArea;
    }

    //分治(左边、右边、穿过中间)
    //而这里的中间并不是mid，而是minHeight那个柱子
    public int solution2(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        return recursion(heights, 0, heights.length - 1);
    }

    //求[left, right]之间柱状图的最大矩形
    private int recursion(int[] heights, int left, int right) {
        if (left == right) {
            return heights[left];
        } else if (left < right) {
            //寻求[left, right]范围内最小柱子高度
            int mid = getIndexOfMinHeight(heights, left, right);
            //左边
            int leftMaxArea = recursion(heights, left, mid - 1);
            //右边
            int rightMaxArea = recursion(heights, mid + 1, right);
            //穿过中间
            int minHeight = heights[mid];
            int i = mid - 1, j = mid + 1;
            //尽量向左边扩充
            while (i >= left && heights[i] >= minHeight) {
                i--;
            }
            //尽量向右边扩充
            while (j <= right && heights[j] >= minHeight) {
                j++;
            }
            return Math.max(Math.max(leftMaxArea, rightMaxArea), (j - i - 1) * minHeight);
        } else {
            return Integer.MIN_VALUE;
        }
    }

    //返回[left, right]范围内最小柱子高度所在的列
    private int getIndexOfMinHeight(int[] heights, int left, int right) {
        int minHeight = Integer.MAX_VALUE;
        int ans = -1;

        for (int i = left; i <= right; i++) {
            if (heights[i] < minHeight) {
                minHeight = heights[i];
                ans = i;
            }
        }
        return ans;
    }

    //单调栈方法
    //每个元素进栈、出栈一次
    //栈内呈单调递增
    //即遇到比栈顶大的，则入栈
    //遇到比栈顶小的，出栈，直到栈顶变成大于当前元素
    //出栈时，计算矩形大小（不涉及当前元素）
    //当前元素入栈
    public int solution3_official(int[] heights) {
        Stack <Integer> stack = new Stack< >();
        stack.push(-1);
        int maxarea = 0;
        for (int i = 0; i < heights.length; ++i) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                //因为栈内是递增的，所以(stack.peek(),i)一定以heights[stack.pop()]为高组成矩形
                //多看官方题解的图
                maxarea = Math.max(maxarea, heights[stack.pop()] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            maxarea = Math.max(maxarea, heights[stack.pop()] * (heights.length - stack.peek() - 1));
        }
        return maxarea;
    }

    public static void main(String[] args) {
        int[] heights = new int[]{
                3, 5, 5, 2, 5, 5, 6, 6, 4, 4, 1, 1, 2, 5, 5, 6, 6, 4, 1, 3
        };
        LargestRectangleInHistogram l = new LargestRectangleInHistogram();
        l.solution3_official(heights);
    }
}
