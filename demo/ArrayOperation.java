package demo;

/**
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ArrayOperation {
    /**
     * 折半查找
     * @param nums 有序数组 -- 从小到大
     * @param target 目标值
     * @return 返回 target 在数组 nums 中的位置
     */
    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        // nums 中不存在 target
        // 可以返回 -1 ，也可以返回 target 的插入位置
        return left;
    }

    /**
     * 交换数组中两元素的位置
     */
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 选择排序
     */
    public void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // 记录[i:]中最小元素的下标
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                min = nums[min] < nums[j] ? min : j;
            }
            // 将[i:]中最小元素与num[i]交换
            swap(nums, min, i);
        }
    }

    //冒泡排序

    //模式匹配-基本方法

    //模式匹配-KMP方法

    public static void main(String[] args) {
        ArrayOperation a = new ArrayOperation();
        a.binarySearch(new int[]{0, 1, 2, 3, 4, 8}, 9);
    }
}
