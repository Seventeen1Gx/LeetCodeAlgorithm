// 287. 寻找重复数
//
// 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
// 假设只有一个重复的整数，找出这个重复的数。
//
// 注意：有个要求是不能改动这个数组


package src.binarySearch;

public class FindTheDuplicateNumber {
    // 移动每个元素 x 到 nums[x] 上，这样一定有个元素的位置被占了
    public int solution1(int[] nums) {
        // 检查每个位置，是不是放了正确的元素
        for (int i = 1; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[nums[i]] == nums[i])
                    return nums[i];
                int t = nums[i];
                nums[i] = nums[nums[i]];
                nums[nums[i]] = t;
            }
        }
        return nums[0];
    }

    // 又规定数组只读
    // 所以上面的方法不行
    // 抽屉原理：
    // n 个苹果放到 n-1 个抽屉里，至少有一个抽屉不少于两个苹果

    // [1,n] 之间搜索一个数字，这个数字是重复的元素
    public int solution2(int[] nums) {
        int low = 1, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int cnt = cnt(nums, mid);

            // n+1 个元素，范围在 [1,n]
            // 且只有一个重复元素
            // 可知，元素组成为，[1,n] 中的元素都有一个，然后再加一个重复元素

            // 小于等于 mid 的数有 cnt 个
            // 将这 cnt 个数放到 [1,mid] 中
            // 当 cnt>mid 时，肯定放不下，会有重复元素
            // 即重复元素在 [1,mid] 范围，则 [mid+1,high] 被排除
            // 当 cnt<=mid 时，重复元素一定不在 [1,mid] 中（利用上面的元素组成进行反证）
            if (cnt > mid)
                high = mid;
            else
                low = mid + 1;
        }
        return low;
    }

    // 统计 nums 中严格小于等于 target 的元素个数
    private int cnt(int[] nums, int target) {
        int count = 0;
        for (int num : nums)
            if (num <= target)
                count++;
        return count;
    }

    // 等价问题
    // n 个元素的数组，其中元素在 [0,n-1] 之间，求重复元素
    // 即将 n 个元素，放到 n-1 个抽屉中，则至少有一个抽屉不少于 2 个元素
    // 重点：元素往范围里放

    public static void main(String[] args) {
        new FindTheDuplicateNumber().solution2(new int[]{1, 3, 4, 2, 2});
    }

}
