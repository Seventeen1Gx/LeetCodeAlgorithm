// 154. 寻找旋转数组的最小值Ⅱ
//
// 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
// 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2]。
//
// 请找出其中最小的元素。
//
// 注意，本题存在重复元素


package src.binarySearch;

public class FindMinimumInRotatedSortedArrayNo2 {
    public int solution(int[] nums) {
        int n = nums.length;
        // 排除不存在旋转的情况
        if (n == 1 || nums[0] < nums[n - 1]) {
            return nums[0];
        }

        int low = 1, high = n - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[0] < nums[mid]) {

                low = mid + 1;
            } else if (nums[0] > nums[mid]) {
                high = mid;
            } else {
                // 这时无法判断 num[mid] 在哪一部分
                // 判断区间线性收缩
                // 如果 low 是答案就返回
                // 如果 low 不是答案就排除
                if (low > 0 && nums[low] < nums[low - 1]) {
                    return nums[low];
                } else {
                    low = low + 1;
                }
            }
        }
        return nums[low];
    }
}
