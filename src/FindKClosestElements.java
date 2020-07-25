// 658. 找到 K 个最接近的元素
//
// 给定一个排序好的数组，两个整数 k 和 x，从数组中找到最靠近 x（两数之差最小）的 k 个数。
// 返回的结果必须要是按升序排好的。如果有两个数与 x 的差值一样，优先选择数值较小的那个数。


package src;

import java.util.*;

public class FindKClosestElements {
    public List<Integer> solution(int[] arr, int k, int x) {
        if (arr == null)
            throw  new IllegalArgumentException("Error Input");

        int n = arr.length;
        if (n <= 0 || k <= 0 || n < k)
            throw  new IllegalArgumentException("Error Input");

        // 二分查找到大于等于 x 的数字中，最左边的那个数
        // 然后向两边扩散
        int low = 0, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x)
                high = mid;
            else
                low = mid + 1;
        }

        LinkedList<Integer> ret = new LinkedList<>();
        int p2 = low, p1 = low - 1;
        // 向两边扩散，直到加入 k 个数字到 ret 中
        for (int i = 0; i < k; i++) {
            int beChosen;
            if (p1 < 0)
                beChosen = p2;
            else if (p2 >= n)
                beChosen = p1;
            else if (x - arr[p1] <= arr[p2] - x)
                beChosen = p1;
            else
                beChosen = p2;

            if (beChosen == p1)
                ret.addFirst(arr[p1--]);
            else
                ret.addLast(arr[p2++]);
        }
        return ret;
    }

    public static void main(String[] args) {
        new FindKClosestElements().solution(new int[]{1, 2, 3, 4, 5}, 4, 3);
        new FindKClosestElements().solution(new int[]{0, 0, 1, 2, 3, 3, 4, 7, 7, 8}, 3, 5);

    }
}
