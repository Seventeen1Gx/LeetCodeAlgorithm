// LCP 12. 小张刷题计划
//
// 为了提高自己的代码能力，小张制定了 LeetCode 刷题计划，他选中了 LeetCode 题库中的 n 道题，编号从 0 到 n-1，并计划在 m 天内按照题目编号顺序刷完所有的题目（注意，小张不能用多天完成同一题）。
//
// 在小张刷题计划中，小张需要用 time[i] 的时间完成编号 i 的题目。
// 此外，小张还可以使用场外求助功能，通过询问他的好朋友小杨题目的解法，可以省去该题的做题时间。为了防止“小张刷题计划”变成“小杨刷题计划”，小张每天最多使用一次求助。
//
// 我们定义 m 天中做题时间最多的一天耗时为 T（小杨完成的题目不计入做题总时间）。请你帮小张求出最小的 T 是多少。


package src.binarySearch;

public class XiaoZhangShuaTiJiHua {
    public int solution(int[] time, int m) {
        // 想象成将 time 数组分成 m 个子数组，每组扣除最大值，剩下的元素之和就是刷完该组题的要花的时间，则 T 取分组所花时间的最大值即可
        // 如何找到使 T 最小的分组
        // 这样就转变成了题号 410 类似的问题

        if (time == null || time.length == 0) {
            throw new IllegalArgumentException();
        }

        int n = time.length;
        if (m >= n) {
            // 每天让小杨刷一题就行了
            return 0;
        }

        int low = 0, high = 0;
        for (int t : time) {
            high += t;
        }
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (valid(time, m, mid)) {
                // 说明 mid 偏大
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private boolean valid(int[] time, int m, int x) {
        // 贪心的话，让一组中耗时最多的那题给小林做

        // 目前统计的是第 cnt 个子数组
        // 目前统计的子数组小张需要耗时为 sum
        // 目前决定让小杨做的题的耗时是 max
        int cnt = 1;
        int max = 0;
        int sum = 0;

        // 从前往后遍历
        int i = 0;
        while (i < time.length) {
            // 提前退出
            if (cnt > m) {
                return false;
            }

            // 遍历到题目 i，有给小杨做或者不给小杨做两种情况
            // 只有在 time[i] > max 时给小杨做才划算
            if (time[i] > max) {
                // 本组中给小杨做第 i 题，于是小张要做 max 那道题
                if (sum + max <= x) {
                    // 满足要求，于是加入统计
                    sum = sum + max;
                    max = time[i];
                    // 去统计下一题
                    i++;
                } else {
                    // 这时不满足
                    // 统计新一组
                    sum = 0;
                    max = 0;
                    cnt++;
                    // 就算这里不让小杨做 time[i]，仍做 max
                    // sum+max > x，那么 sum+time[i] > x 肯定也成立，故也要统计新一组
                }
            } else {
                // 小张做第 i 题
                if (sum + time[i] <= x) {
                    sum = sum + time[i];
                    i++;
                } else {
                    sum = 0;
                    max = 0;
                    cnt++;
                }
            }
        }
        return cnt <= m;
    }
}
