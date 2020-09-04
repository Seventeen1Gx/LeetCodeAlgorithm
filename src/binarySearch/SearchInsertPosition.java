// 35. 搜索插入位置
//
// 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
//
// 你可以假设数组中无重复元素


package src.binarySearch;

public class SearchInsertPosition {
    @SuppressWarnings("all")
    public int solution(int[] nums, int target) {
        int n = nums.length;

        // 因为答案可能是 nums.length，而下面的搜索不包括这个，故特殊判断一下
        if (target > nums[n - 1]) {
            return n;
        }

        // [0, n-1]
        // [0, i] [i+1, n-1] i+1 为答案
        // mid 在第一个区间里，有 nums[mid] < target
        // mid 在第二个区间里，有 nums[mid] >= target

        int l = 0, h = n - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;

            if (nums[mid] >= target) {
                h = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }
}
