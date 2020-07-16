// 704. 二分查找
//
// 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target
// 写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。


package src.binarySearch;

public class BinarySearch {
    // 下面两种方法返回插入位置的话，都是返回 l

    public int solution_1(int[] nums, int target) {
        // [l:h] 范围内查找
        // 所以在 l=h 时仍要进入循环
        // 而且下面更新 h 的语句中也是排除了 mid 成为答案的可能性
        int l = 0, h = nums.length - 1, mid;
        while (l <= h) {
            mid = l + (h - l) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] > target)
                h = mid - 1;
            else if (nums[mid] < target)
                l = mid + 1;
        }
        return -1;
    }

    public int solution_2(int[] nums, int target) {
        // [l:h) 范围内查找
        // 所以在 l=h 时不需要进入循环
        // 而且下面 h 的更新语句中也排除了 mid 成为答案的可能性
        int l = 0, h = nums.length, mid;
        while (l < h) {
            mid = l + (h - l) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] > target)
                h = mid;
            else if (nums[mid] < target)
                l = mid + 1;
        }
        return -1;
    }

    // 减治法
    public int solution_3(int[] nums, int target) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;
            // 将区间分成 [l, mid] 和 [mid+1:h]
            if (nums[mid] >= target)
                // 则目标元素肯定在左边（就算有重复元素，我们也是找他的最左出现）
                h = mid;
            else
                l = mid + 1;
        }
        return nums[l] == target ? l : -1;
    }
}
