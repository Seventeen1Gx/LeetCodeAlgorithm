// 33. 搜索旋转排序数组
//
// 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
// ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
//
// 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
//
// 你可以假设数组中不存在重复的元素。
//
// 你的算法时间复杂度必须是 O(log n) 级别。
//
// 本题不含重复元素，如果含重复元素会怎么样？


package src.binarySearch;

public class SearchInRotatedSortedArray {
    // 思路一：
    // 先找到最小值
    // 再分成两个有序数组，分别二分查找

    // 思路二：
    // 减治法，利用单调

    public int solution(int[] nums, int target) {
        // 先排除第一个元素
        if (nums[0] == target) {
            return 0;
        }

        if (nums.length == 1) {
            return -1;
        }

        int lo = 1, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            // 判断 mid 落在左边还是右边
            // 只有 nums[0] > nums[mid] 和 nums[0] < nums[mid] 两种情况，因为元素不相等
            if (nums[0] < nums[mid]) {
                if (nums[lo] <= target && nums[mid] >= target) {
                    // [lo, mid] 是单调的
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[hi]) {
                    // [mid, hi] 是单调的
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
        }
        return nums[lo] == target ? lo : -1;
    }
}
