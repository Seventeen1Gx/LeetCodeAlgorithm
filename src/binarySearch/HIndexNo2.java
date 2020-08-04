// 275. H指数Ⅱ
//
// 给定一位研究者论文被引用次数的数组（被引用次数是非负整数），数组已经按照升序排列。
// 编写一个方法，计算出研究者的 h 指数。
//
// h 指数的定义: h 代表 “高引用次数”（high citations）
// 一名科研人员的 h 指数是指他（她）的 （N 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。
// （其余的 N - h 篇论文每篇被引用次数不多于 h 次。）


package src.binarySearch;

public class HIndexNo2 {
    // 因为升序，所以从后往前看
    // citations[i] 是第 n-i 篇论文
    // 当 citations[i] >= n-i 时，n-i 是一个 H 指数
    // 但这里要求最大的
    // 即 citations[i] >= n-i，但 citations[i-1] < n-i+1
    // 二分查找上面这样的 i
    // i 左边的都不满足，i 右边的都满足

    // 注意，上面的 i 不一定存在的
    public int solution(int[] citations) {
        int n = citations.length;

        // 特判
        if (citations[n-1] < 1)
            return 0;

        int low = 0, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (citations[mid] >= n - mid)
                // [mid+1,high] 不存在目标
                high = mid;
            else
                low = mid + 1;
        }
        return n - low;
    }

    public static void main(String[] args) {
        new HIndexNo2().solution(new int[]{0, 1, 3, 5, 6});
    }
}
