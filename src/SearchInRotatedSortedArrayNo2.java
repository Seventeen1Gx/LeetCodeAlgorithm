//81. 搜索旋转排序数组Ⅱ
//
//假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
//( 例如，数组 [0,0,1,2,2,5,6] 可能变为 [2,5,6,0,0,1,2] )。
//
//编写一个函数来判断给定的目标值是否存在于数组中。若存在返回 true，否则返回 false。
//
//本题为33题的延申题，请看33题


package src;

public class SearchInRotatedSortedArrayNo2 {
    public boolean search(int[] nums, int target) {
        //先找到旋转中心j，有nums[j-1]>nums[j]--排除了所有元素相等和j是第一个元素(即正序)的两种情况

        if (nums == null)
            return false;

        int len = nums.length;
        if (len == 0)
            return false;
        if (len == 1)
            return nums[0] == target;

        int rotateIndex = 0;

        //头尾两元素
        int head = nums[0];
        int tail = nums[len - 1];

        //排除正序的情况
        if (tail > head)
            return binarySearch(nums, 0, len - 1, target);

        //二分查找旋转中心
        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            //判断mid是前半部分(在j之前)，还是后半部分(在j之后)--跟首尾元素比较
            //还是mid就是j
            if (mid - 1 >= 0 && nums[mid - 1] > nums[mid]) {
                rotateIndex = mid;
                break;
            }

            if (nums[mid] == tail && tail == head) {
                //首尾元素相等，而nums[mid]也等于该元素，则不好判断mid在前还是在后，使用顺序查找[low:high]中最小元素
                //所有元素相等的情况包括在这里
                rotateIndex = low;
                for (int i = low; i < high; i++) {
                    if (nums[i] > nums[i + 1])
                        rotateIndex = i + 1;
                }
                break;
            }

            if (nums[mid] >= head) {
                //在前部分
                low = mid + 1;
            } else if (nums[mid] <= tail) {
                //在后部分
                high = mid - 1;
            }
        }

        //找到旋转中心，判断target在前还是在后
        if (target == head)
            return true;
        else if (target > head)
            return binarySearch(nums, 0, rotateIndex - 1, target);
        else
            return binarySearch(nums, rotateIndex, len - 1, target);
    }

    //nums数组在[low, high]为正序排列
    private boolean binarySearch(int[] nums, int low, int high, int target) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return false;
    }
}
