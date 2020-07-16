// 153. 寻找旋转数组的最小值
//
// 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
// 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2]。
//
// 请找出其中最小的元素。
//
// 你可以假设数组中不存在重复元素。


package src.binarySearch;

public class FindMinimumInRotatedSortedArray {
    // 不是针对目标，而是针对排除
    public int solution(int[] nums) {
        int n = nums.length;

        // 先排除不旋转的情况
        if (nums[0] < nums[n - 1])
            return nums[0];

        int low = 0, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;

            // [low,mid]  [mid+1,high]
            if (nums[0] <= nums[mid])
                // [low:mid] 是递增数组，目标值一定不在这个里面
                low = mid + 1;
            else
                // [mid:high] 是递增数组，目标值一定不在 [mid+1,high]
                high = mid;
        }
        return nums[low];
    }

    public static void main(String[] args) {
        new FindMinimumInRotatedSortedArray().solution(new int[]{3, 4, 5, 1, 2});
    }
}
