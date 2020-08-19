// 739. 每日温度
//
// 请根据每日气温列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。
// 如果气温在这之后都不会升高，请在该位置用 0 来代替。
//
// 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
//
// 提示：气温列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。


package src;

import java.util.ArrayDeque;
import java.util.Deque;

public class DailyTemperatures {
    // 每个位置，向右找第一个比它大的元素位置
    public int[] solution1(int[] T) {
        int[] ret = new int[T.length];
        for (int i = 0; i < ret.length; i++) {
            for (int j = i + 1; j < ret.length; j++) {
                if (T[j] > T[i]) {
                    ret[i] = j - i;
                    break;
                }
            }
        }
        return ret;
    }

    // 单调递减栈
    public int[] solution2(int[] T) {
        int[] ret = new int[T.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                // 当前 i 是栈顶元素右边第一个比他大的元素位置
                int peek = stack.pop();
                ret[peek] = i - peek;
            }
            stack.push(i);
        }
        return ret;
    }
}
