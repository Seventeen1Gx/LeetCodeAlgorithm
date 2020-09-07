// 347. 前 K 个高频元素
//
// 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。


package src;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequentElements {
    public int[] topKFrequent(int[] nums, int k) {
        // 出现频率前 k 高
        // 即按出现次数从大到小排序，前 k 个元素
        // 使用最小堆维护 k 个元素，当超出 k 个元素，就将最小的元素出队
        // 这样一次遍历下来，堆中的 k 个元素就是最大的 k 个

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(map::get));
        for (int num : map.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        int[] ret = new int[k];
        for (int i = 0; i < k; i++) {
            ret[k - i - 1] = minHeap.poll();
        }
        return ret;
    }
}
