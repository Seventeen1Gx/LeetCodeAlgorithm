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


// 二分法精髓：K 从小到大的过程中，有一个临界点，临界点左边不满足要求，临界点右边满足要求


package src.binarySearch;

public class KokoEatingBananas {
    // 假如我们的 K 取很大，每个小时珂珂肯定能吃掉一堆的香蕉
    // 既然每个小时最多吃一堆香蕉，则 K 最大只要取到最大堆的那个香蕉树即可，最少花的时间是 piles.length 个小时
    // 我们再减小这个 K，就会出现香蕉数大于 K 的那堆香蕉要花 2 个小时以上吃完
    // 随着 K 的减小，花费时间上升
    // 现在要求花费时间 <= H，故存在一个最小的 K 达到这个临界点

    // 暴力法（一根一根地加）
    public int solution1(int[] piles, int H) {
        int K  = 0;

        // 随着 K 的增加，time 在减小
        // 找到第一次出现的 time<=H
        int time;
        do {
            K++;
            time = 0;
            for (int i = 0; i < piles.length; i++)
                time += (int) Math.ceil(1.0 * piles[i] / K);
        } while (time > H);

        return K;
    }

    // 显然，一下一下地加，太慢了
    // 于是我们想到二分法
    // 在 K 的取值范围 [1, max] 中 (max 是香蕉数最多的那堆的香蕉树)
    // K 单调增长时，time 单调减小，一对一映射
    // 左边 K 偏小的范围内，所花时间大于 H，右边 K 偏大的范围内，所花时间小于 H
    // 于是就存在一个临界点
    // 故我们求使得 time<=H 的最小 K
    public int solution2(int[] piles, int H) {
        if (piles == null || piles.length <= 0)
            throw new IllegalArgumentException();

        int max = piles[0];
        for (int pile : piles)
            max = Math.max(max, pile);

        int low = 1, high = max;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int time = 0;
            for (int i = 0; i < piles.length; i++)
                time += (int) Math.ceil(1.0 * piles[i] / mid);
            if (time > H)
                // 说明 K 太小了，左边舍弃
                low = mid + 1;
            else
                // 说明 K 太大了，右边舍弃
                high = mid;
        }
        return low;
    }


    public static void main(String[] args) {
        KokoEatingBananas k = new KokoEatingBananas();
        int[] pile1 = {3, 6, 7, 11};
        int[] pile2 = {30, 11, 23, 4, 20};
        System.out.println(k.solution1(pile1, 8));
        System.out.println(k.solution2(pile2, 5));
        System.out.println(k.solution1(pile2, 6));
    }
}
