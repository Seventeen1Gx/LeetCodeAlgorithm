// 34. 在排序数组中查找元素的第一个和最后一个位置
//
// 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
//
// 你的算法时间复杂度必须是 O(log n) 级别。
//
// 如果数组中不存在目标值，返回 [-1, -1]


package src.binarySearch;

public class FindFirstAndLastPositionOfElementInSortedArray {
    public int[] solution(int[] nums, int target) {
        return new int[]{findFirstPosition(nums, target), findFirstPosition(nums, target)};
    }

    private int findFirstPosition(int[] nums, int target) {
        // [0, n-1] 分成两部分，分界点是我们求的答案
        // [0, i] [i+1, n-1]
        // 第一个区间的元素都 < target
        // 第二个区间的元素都 >= target → 需要保留 mid

        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return nums[lo] == target ? lo : -1;
    }

    private int findLastPosition(int[] nums, int target) {
        // [0, n-1] 分成两部分，分界点是我们求的答案
        // [0, i] [i+1, n-1]
        // 第一个区间的元素都 <= target → 需要保留 mid
        // 第二个区间的元素都 > target

        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (nums[mid] <= target) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        return nums[lo] == target ? lo : -1;
    }
}
