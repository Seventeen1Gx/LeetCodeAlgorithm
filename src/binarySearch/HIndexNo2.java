// 275. H指数Ⅱ
//
// 给定一位研究者论文被引用次数的数组（被引用次数是非负整数），数组已经按照升序排列。
// 编写一个方法，计算出研究者的 h 指数。
//
// h 指数的定义: h 代表 “高引用次数”（high citations）
// 一名科研人员的 h 指数是指他（她）的 （N 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。
// （其余的 N - h 篇论文每篇被引用次数不多于 h 次。）
//
// 注意：h 可能有多种，取最大的


package src.binarySearch;

public class HIndexNo2 {
    public int solution(int[] citations) {
        // 搜索范围 [0, n]
        // 如果 h 指数是 n，即 n 篇论文的引用都大于等于 n，看 citations[0]>=n 是否满足
        // 如果 h 指数是 n-1，即 n-1 篇论文的引用都大于等于 n-1，看 citations[1]>=n-1 是否满足
        //  ...
        // 如果 h 指数是 x，即 x 篇论文的引用都大于等于 x，看 citations[n-x]>=x 是否满足
        // ...
        // 如果答案是 H，则 [0, H] 都满足，[H+1, N] 都不满足

        int n = citations.length;

        int low = 0, high = n;
        while (low < high) {
            // mid 是我们对 H 的猜测值
            int mid = low + (high - low + 1) / 2;
            if (citations[n - mid] >= mid) {
                // 说明 mid 在 [0,H] 中
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        new HIndexNo2().solution(new int[]{0, 1, 3, 5, 6});
    }
}
