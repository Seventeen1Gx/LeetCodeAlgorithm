// 81. 搜索旋转排序数组Ⅱ
//
// 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
// ( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
//
// 编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。
//
// 本题为 33 题的延申题，请看 33 题


package src.binarySearch;

public class SearchInRotatedSortedArrayNo2 {
    @SuppressWarnings("all")
    public boolean solution(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        // 先排除第一个元素
        if (nums[0] == target) {
            return true;
        }

        if (nums.length == 1) {
            return false;
        }

        int lo = 1, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // 判断 mid 落在左边还是右边
            // 有可能 nums[0] == nums[mid] 就没法判断 mid 落在哪边
            if (nums[0] < nums[mid]) {
                if (nums[lo] <= target && nums[mid] >= target) {
                    // [lo, mid] 是单调的
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            } else if (nums[0] > nums[mid]) {
                if (nums[mid] < target && target <= nums[hi]) {
                    // [mid, hi] 是单调的
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            } else {
                // 排除左边界，排除之前要看左边界是否就是答案
                if (nums[lo] == target) {
                    return true;
                } else {
                    lo = lo + 1;
                }
            }
        }
        return nums[lo] == target;
    }
}
