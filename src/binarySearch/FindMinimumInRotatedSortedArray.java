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
    public int solution(int[] nums) {
        int n = nums.length;

        // 先排除不旋转的情况
        if (n == 1 || nums[0] < nums[n - 1]) {
            return nums[0];
        }

        // [1, n-1] 分成左右两个区间，分界点是我们的答案
        // [1, i] [i+1, n-1]
        // 第一个区间的元素都大于 nums[0]
        // 第二个区间的元素都小于 nums[0] → mid 需要保留

        int low = 1, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;


            if (nums[mid] < nums[0]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return nums[low];
    }

    public static void main(String[] args) {
        new FindMinimumInRotatedSortedArray().solution(new int[]{3, 4, 5, 1, 2});
    }
}
