// 287. 寻找重复数
//
// 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
// 假设只有一个重复的整数，找出这个重复的数。
//
// 注意：有个要求是不能改动这个数组


package src.binarySearch;

public class FindTheDuplicateNumber {
    public int solution1(int[] nums) {
        // 移动每个元素 x 到 nums[x] 上，这样一定有个元素的位置被占了
        for (int i = 1; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[nums[i]] == nums[i]) {
                    return nums[i];
                }
                int t = nums[i];
                nums[i] = nums[nums[i]];
                nums[nums[i]] = t;
            }
        }
        return nums[0];
    }

    // 抽屉原理：
    // n 个苹果放到 n-1 个抽屉里，至少有一个抽屉不少于 2 个苹果

    public int solution2(int[] nums) {
        // [1, n-1] 中减治
        // [1, i] [i+1, n-1]，假设 i+1 是重复整数中最小的
        // 选定 mid → 求得 cnt
        // 对于第一个区间中 mid，有 cnt <= mid
        // 对于第二个区间中 mid，有 cnt > mid + 1

        int low = 1, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int cnt = cnt(nums, mid);
            if (cnt <= mid) {
                // 说明 mid 在第一个区间，答案在它右边
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int cnt(int[] nums, int target) {
        // 统计 nums 中小于等于 target 的元素个数
        int count = 0;
        for (int num : nums) {
            if (num <= target) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        new FindTheDuplicateNumber().solution2(new int[]{1, 3, 4, 2, 2});
    }

}
