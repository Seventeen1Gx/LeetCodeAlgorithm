// 面试题 17.21. 直方图的水量
//
// 同 42. 接雨水


package src;

import java.util.ArrayDeque;
import java.util.Deque;

public class VolumeOfHistogram {
    // 每列的雨水由该列左边最高的列和右边最高的列来确定
    public int solution(int[] height) {
        if (height == null) {
            return 0;
        }

        int n = height.length;

        if (n == 0) {
            return 0;
        }

        // rightMaxHeight[i] 表示 height[i] 右边最高的高度
        int[] rightMaxHeight = new int[n];
        rightMaxHeight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMaxHeight[i] = Math.max(rightMaxHeight[i + 1], height[i]);
        }

        int ret = 0;

        // 表示当前遍历到的 i 的左边最高的高度
        int leftMaxHeight = height[0];
        for (int i = 1; i < n; i++) {
            leftMaxHeight = Math.max(leftMaxHeight, height[i]);
            ret += Math.min(leftMaxHeight, rightMaxHeight[i]) - height[i];
        }
        return ret;
    }


    // 单调递减栈
    public int solution2(int[] height) {
        Deque<Integer> stack = new ArrayDeque<>();
        int ret = 0;
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 栈顶表示的列，由当前列，和栈顶前一个元素表示的列界定
                // （本质 i 还是栈顶元素右边第一个更大的元素位置）
                int peek = stack.pop();
                if (stack.isEmpty()) {
                    // 无前边界
                    break;
                }
                // 宽 * 水高
                ret += (i - stack.peek() - 1) * (Math.min(height[stack.peek()], height[i]) - height[peek]);
            }
            stack.push(i);
        }
        return ret;
    }


    // 以 [4, 3, 1, 0, 1, 2, 4] 为例
    // 就知道单调栈计算方式是，每个水坑，是一行一行计算积水量的
    // 这里写的两种写法本质上没有关联！
}
