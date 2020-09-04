// 410. 分割数组的最大值
//
// 给定一个非负整数数组和一个整数 m，你需要将这个数组分成 m 个非空的连续子数组。设计一个算法使得这 m 个子数组各自和的最大值最小。
//
// 注意:
// 数组长度 n 满足以下条件:
// 1 ≤ n ≤ 1000
// 1 ≤ m ≤ min(50, n)


package src.binarySearch;

public class SplitArrayLargestSum {
    @SuppressWarnings("all")
    public int splitArray(int[] nums, int m) {
        // [1, sum(nums)] 中搜索
        // 当 ans 很大时，肯定能分出满足要求的结果
        // 当 ans 很小时，肯定不能分出满足要求的结果
        // 中间存在临界点
        // [1, i] [i+1, sum(nums)]  i+1 是我们的答案
        // 当 mid 落在左区间时，按 mid 去分数组，肯定不能满足要求
        // 当 mid 落在右区间时，按 mid 去分数组，肯定能满足要求

        int lo = -1, hi = 0;
        for (int num : nums) {
            hi += num;
            lo = Math.max(lo, num);
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (valid(nums, m, mid)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }


    private boolean valid(int[] nums, int m, int maxSum) {
        // 贪心，在满足子数组和大于 maxSum 时，尽可能分得大一点

        // 目前在统计第 i 个子数组
        int cnt = 1;
        // 当前这个子数组的累计和
        int sum = 0;
        // 遍历数组
        for (int num : nums) {
            if (cnt > m) {
                return false;
            }

            if (sum + num > maxSum) {
                // 统计下一个子数组
                cnt++;
                sum = num;
            } else {
                sum += num;
            }
        }
        return cnt <= m;
    }

    public static void main(String[] args) {
        new SplitArrayLargestSum().splitArray(new int[]{7, 2, 5, 10, 8}, 2);
    }
}
