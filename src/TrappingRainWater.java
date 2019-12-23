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

    //使用单调栈(每个元素都要入栈出栈各一次，所以O(n)复杂度)
    //
    //官方题解思路：
    //如果当前的条形块小于或等于栈顶的条形块，我们将条形块的索引入栈
    //意思是当前的条形块被栈中的前一个条形块界定
    //如果我们发现一个条形块长于栈顶
    //我们可以确定栈顶的条形块被当前条形块和栈的前一个条形块界定
    //因此我们可以弹出栈顶元素并且累加答案到ans
    //
    //建议使用例子[0,1,0,2,1,0,1,3,2,1,2,1]走一遍
    public int solution3_official(int[] height) {
        int sum = 0;
        //栈内存储柱子的下标
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        //current遍历每个柱子，每个柱子要进栈之后，出栈时才有作用
        while (current < height.length) {
            //如果栈不空并且当前指向的高度大于栈顶高度就一直循环
            //(说明此时current是积水右边的那堵墙，然后往左遍历，一直到左墙壁)
            while (!stack.empty() && height[current] > height[stack.peek()]) {
                int h = height[stack.peek()]; //取出要出栈的元素
                stack.pop(); //出栈
                if (stack.empty()) {
                    //无前一个条形快
                    break;
                }
                //两堵墙(当前柱子和新栈顶表示的柱子)之前的距离。
                //首先直观想，贴着的两根柱子不会是积水坑的两壁
                int distance = current - stack.peek() - 1;
                int min = Math.min(height[stack.peek()], height[current]);
                //注意这里的(min-h)，意味着每个积水坑的水量不是一次性计算得到的--而是按行得到总和
                sum = sum + distance * (min - h);
            }
            stack.push(current); //当前指向的墙入栈
            current++; //指针后移
        }
        return sum;
    }

    //启发双指针法，根据上面第二种方法改进
    //首先方法二中求得maxLeft数组和maxRight数组后
    //在计算积水时，每次循环其实只用对应位置的元素，而之后不再使用，所以我们可以节约数组的空间
    //但下面只省了maxLeft数组，是因为我们的循环是从左到右的，可以用maxLeft变量保存当前遇到的最高柱子高度
    //这个高度就是当前位置左边柱子中最高柱子的高度
    public int solution4_heuristic(int[] height) {
        if (height == null)
            return 0;

        int n = height.length;

        if (n == 0)
            return 0;

        //每个位置右边柱子中(包括自己)最高的柱子
        int[] maxRight = new int[n];
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        }

        int sum = 0;

        //跟踪每个位置左边柱子中(包括自己)最高的柱子
        int maxLeft = height[0];
        for (int i = 0; i < n; i++) {
            if (height[i] > maxLeft)
                maxLeft = height[i];
            sum += Math.min(maxLeft, maxRight[i]) - height[i];
        }

        return sum;
    }


    //双指针法，有了上面这个方法的启发，之所以不能省略maxRight数组的空间，是因为我们只有一个指针i，其遍历为从左往右
    //故我们需要两个指针，left从左向右遍历，right从右往左遍历，直到两针相遇，也就遍历了所有列(还是按列计算)
    //我们用maxLeft来表示left左侧(不包括left)最大高度，maxRight表示right右侧(不包括right)最大高度
    //每次循环动用哪个指针？即计算哪个列上的积水
    //计算每列上的积水，是看其左右两边最大高度中，最小高度确定水平面
    public int solution4_official(int[] height) {
        int sum = 0;
        int maxLeft = 0;
        int maxRight = 0;
        int left = 0;
        int right = height.length - 1; // 加右指针进去

        //每次循环计算left所指列或right所指列上的积水
        while (left < right) {
            if (height[left] < height[right]) {
                //计算left列的积水，因为maxleft可以确定水平面。为什么呢？
                if (height[left] >= maxLeft)
                    //当前列比当前确立的左壁更高，则此列在水平面之上，无积水
                    //更新新的左壁高度
                    maxLeft = height[left];
                else
                    //当前列在水平面下，计算当前列的积水
                    sum += maxLeft - height[left];
                //计算下一列的积水
                left++;
            } else {
                //同理
                if (height[right] >= maxRight)
                    maxRight = height[right];
                else
                    sum += maxRight - height[right];
                right--;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        TrappingRainWater t = new TrappingRainWater();
        t.solution3_official(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
    }
}