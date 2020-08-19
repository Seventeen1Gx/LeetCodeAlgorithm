// 496. 下一个更大的元素Ⅰ
//
// 给定两个 没有重复元素 的数组 nums1 和 nums2 ，其中 nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
//
// nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。


package src;

import java.util.*;

public class NextGreaterElement {
    public int[] solution(int[] nums1, int[] nums2) {
        // 对 num1 中的元素能快速找到 num2 中对应的位置
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }

        int[] ret = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            // 对于 num1 中的每个元素，先去 num2 中找到对应位置
            int index = map.get(nums1[i]);
            // 从 index 往后寻找第一个更大的元素
            ret[i] = -1;
            for (int j = index + 1; j < nums2.length; j++) {
                if (nums2[j] > nums1[i]) {
                    ret[i] = nums2[j];
                    break;
                }
            }
        }
        return ret;
    }

    // 单调递减栈
    public int[] solution1(int[] nums1, int[] nums2) {
        // temp[i] 表示 num2[i] 右边第一个比它大的元素
        int[] temp = new int[nums2.length];
        Arrays.fill(temp, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[i] > nums2[stack.peek()]) {
                // num2[i] 是 num2[stack.peek()] 右边第一个比它大的元素
                int peek = stack.pop();
                temp[peek] = nums2[i];
            }
            stack.push(i);
        }

        // 对 num1 中的元素能快速找到 num2 中对应的位置
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            map.put(nums2[i], i);
        }

        int[] ret = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            ret[i] = temp[map.get(nums1[i])];
        }
        return ret;
    }
}
