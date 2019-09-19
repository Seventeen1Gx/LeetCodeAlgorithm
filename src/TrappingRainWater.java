//42. 接雨水
//
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水


package src;

import java.util.Stack;

public class TrappingRainWater {
    //暴力法
    //按列寻找，看每列能接多少水
    public int solution1(int[] height) {
        if (height == null)
            return 0;

        int n = height.length;

        if (n == 0)
            return 0;

        //每列能接多少水，取决于它所在哪个水槽中
        //则要看其左右两边有没有比它高的

        int ans = 0;

        //遍历列
        for (int i = 0; i < n; i++) {
            int maxLeft, maxRight;
            maxLeft = maxRight = 0;

            //看左边有无更高的列
            for (int j = i; j >= 0; j--) {
                maxLeft = Math.max(maxLeft, height[j]);
            }
            //看右边有无更高的列
            for (int j = i; j < n; j++) {
                maxRight = Math.max(maxRight, height[j]);
            }

            //当前列能接水
            ans += Math.min(maxLeft, maxRight) - height[i];
        }

        return ans;
    }

    //在上面的方法中，我们对每列都寻找其左右两边的最高列
    //其实我们无需每次都找，可以先将每列左右两边最高列的高度保存起来
    public int solution2(int[] height) {
        if (height == null)
            return 0;

        int n = height.length;

        if (n == 0)
            return 0;

        int[] maxLeft = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        }

        int[] maxRight = new int[n];
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        int ans = 0;
        //遍历每列，累加每列可接的雨水量
        for (int i = 0; i < n; i++) {
            ans += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }

        return ans;
    }

    //使用栈(每次发现左右两边的墙壁就计算它们中间所接的水)
    public int solution3(int[] height) {
        if (height == null)
            return 0;

        int length = height.length;

        if (length == 0)
            return 0;

        //保存下标--因为要计算距离
        Stack<Integer> s = new Stack<>();

        s.push(height[0]);

        int ans = 0;
        for (int i = 1; i < length; i++) {
            if (height[i] <= s.peek()) {
                //当前列高，小于或等于栈顶元素表示的列高
                //则表示，当前列的左边界为栈顶元素表示的列
                s.push(height[i]);
            } else {
                //栈顶元素的右边界为当前元素
            }
        }

    }
}