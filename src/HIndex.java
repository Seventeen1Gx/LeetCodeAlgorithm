// 274. H 指数
//
// 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h 指数。
//
// h 指数的定义：
// h 代表 “高引用次数”（high citations）
// 一名科研人员的 h 指数是指他（她）的 （N 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。
// （其余的 N - h 篇论文每篇被引用次数 不超过 h 次。）
//
// 例如：某人的 h 指数是 20，这表示他已发表的论文中，每篇被引用了至少 20 次的论文总共有 20 篇。
//
// 和 275 的差别就是这里数组无序


package src;

public class HIndex {
    public int solution(int[] citations) {
        // 倒序排序，这里使用冒泡
        for (int i = 0; i < citations.length; i++) {
            boolean flag = true;
            for (int j = 0; j < citations.length - i - 1; j++) {
                if (citations[j] < citations[j + 1]) {
                    int t = citations[j];
                    citations[j] = citations[j + 1];
                    citations[j + 1] = t;
                    flag = false;
                }
            }
            if (flag)
                break;
        }

        for (int i = 0; i < citations.length; i++) {
            // 第 i+1 篇论文的引用大于 i+1
            // 是的话，继续往下找
            // 不是的话，指数为 i
            if (citations[i] <= i)
                return i;
        }
        return citations.length;
    }
}
