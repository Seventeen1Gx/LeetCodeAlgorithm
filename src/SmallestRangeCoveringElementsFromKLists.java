// 632. 最小区间
//
// 你有 k 个升序排列的整数数组。找到一个最小区间，使得 k 个列表中的每个列表至少有一个数包含在其中。
//
// 我们定义如果 b-a < d-c 或者在 b-a == d-c 时 a < c，则区间 [a,b] 比 [c,d] 小。
//
// 注意：
// 给定的列表可能包含重复元素，所以在这里升序表示 >= 。
// 1 <= k <= 3500
// -105 <= 元素的值 <= 105


package src;

import java.util.*;

public class SmallestRangeCoveringElementsFromKLists {
    // 题目转化为
    // 从 k 个数组中各取一个数，使得这 k 个数的最大值和最小值之差最小

    // 假设这 k 个数中的最小值是第 i 个列表中的 x
    // 对于任意 j != i，
    // 设第 j 个列表中被选为 k 个数之一的数是 y，则为了找到最小区间，y 应该取第 j 个列表中大于等于 x 的最小的数。
    public int[] solution1(List<List<Integer>> nums) {
        // 维护所选过程中遇到的最小区间的情况
        int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
        int minRange = rangeRight - rangeLeft;
        // 维护堆中目前 k 个指针所指的最大值
        int max = Integer.MIN_VALUE;

        int size = nums.size();

        // 维护 k 个指针，next[i] 表示第 i 个数组的指针的下标
        // 开始时都指向 0
        int[] next = new int[size];

        // 最小堆
        // 存的元素是数组的号码
        // 排序原则是，数组游标所指元素小的在堆前
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(index -> nums.get(index).get(next[index])));
        for (int i = 0; i < size; i++) {
            priorityQueue.offer(i);
            max = Math.max(max, nums.get(i).get(0));
        }

        while (true) {
            // 堆顶出队
            int minIndex = priorityQueue.poll();
            int curRange = max - nums.get(minIndex).get(next[minIndex]);
            if (curRange < minRange) {
                minRange = curRange;
                rangeRight = max;
                rangeLeft = nums.get(minIndex).get(next[minIndex]);
            }
            // 指针后移
            next[minIndex]++;
            if (next[minIndex] == nums.get(minIndex).size()) {
                // 有一个指针走到头，我们就退出
                // 因为我们必须要从从每个列表选一个数
                break;
            }
            // 再把这个数组放回去
            priorityQueue.offer(minIndex);
            max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
        }
        return new int[]{rangeLeft, rangeRight};
    }

    // 哈希表 + 滑动窗口
    public int[] solution2(List<List<Integer>> nums) {
        int size = nums.size();

        // 值表示键这个数字在哪些数组出现过
        Map<Integer, List<Integer>> indices = new HashMap<>();
        // 先遍历所有，做一个统计
        // xMin 是所有出现数字中的最小值
        // xMax 是所有出现数字中的最大值
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            for (int x : nums.get(i)) {
                List<Integer> list = indices.getOrDefault(x, new ArrayList<>());
                list.add(i);
                indices.put(x, list);
                xMin = Math.min(xMin, x);
                xMax = Math.max(xMax, x);
            }
        }

        // 我们在这个 indices 的键上作滑动窗口
        // [left:right]
        // 直到覆盖到所有数组
        // 即每个数组都出一个数字

        // freq[i] 表示第 i 个数组有几个数在 [left:right] 中
        // inside 表示 [left:right] 覆盖到了几个数组
        int[] freq = new int[size];
        int inside = 0;

        int left = xMin, right = xMin - 1;

        // 记录这个过程中的最优解
        int bestLeft = xMin, bestRight = xMax;
        while (right < xMax) {
            // 右边界右移
            // 直到加入一个数字到区间中
            right++;
            if (indices.containsKey(right)) {
                for (int x : indices.get(right)) {
                    freq[x]++;
                    if (freq[x] == 1) {
                        inside++;
                    }
                }
                while (inside == size) {
                    // 现在已覆盖到所有数组
                    // 可以左移左边界缩小区间
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left;
                        bestRight = right;
                    }
                    if (indices.containsKey(left)) {
                        for (int x : indices.get(left)) {
                            freq[x]--;
                            if (freq[x] == 0) {
                                inside--;
                            }
                        }
                    }
                    left++;
                }
            }
        }
        return new int[]{bestLeft, bestRight};
    }
}
