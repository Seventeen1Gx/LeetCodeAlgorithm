// 875. 爱吃香蕉的珂珂
//
// 珂珂喜欢吃香蕉。这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
//
// 珂珂可以决定她吃香蕉的速度 K （单位：根/小时）。
// 每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。  
//
// 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
//
// 返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。


package src.binarySearch;

public class KokoEatingBananas {
    public int solution(int[] piles, int H) {
        // 由于一小时最多吃掉一堆香蕉，故前提条件有 H >= piles.length
        // 假如 K 取得很大，肯定能吃完
        // 但每小时最多吃一堆香蕉，故太大也没用，只要能吃掉最大堆的香蕉就行了，得到 K 的上限是 max(piles)
        // 随着 K 的减小，我们吃香蕉的时间肯定增加 → 存在零界点
        //
        // K 在 [1, max] 中取值，左边吃不完，右边一定能吃完
        // [1,i] [i+1,max] i+1 是我们的答案

        int max = piles[0];
        for (int pile : piles) {
            max = Math.max(max, pile);
        }

        int low = 1, high = max;
        while (low < high) {
            // mid 是我们的测试值
            int mid = low + (high - low) / 2;
            // 由 mid 求 time，即吃完香蕉的时间
            int time = 0;
            for (int pile : piles) {
                time += (int) Math.ceil(1.0 * pile / mid);
            }
            if (time > H) {
                // 说明我们猜测的 mid 落在了 [1,i]，答案在它右边
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
