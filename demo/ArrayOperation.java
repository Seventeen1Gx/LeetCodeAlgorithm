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
        // 可以返回 -1，也可以返回 target 的插入位置
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
            // 记录 [i:] 中最小元素的下标，初始化为 i
            int min = i;
            // 遍历 [i+1:]，从而得到 min
            for (int j = i + 1; j < nums.length; j++) {
                min = nums[min] < nums[j] ? min : j;
            }
            // 将[i:]中最小元素与num[i]交换
            swap(nums, min, i);
        }
    }

    /**
     * 冒泡排序
     */
    public void bubbleSort(int[] nums) {
        // 考虑第一次遍历
        // num[0] 与 num[1] 比较
        // num[0] > num[1] 则交换，否则不动
        // 接着比较 num[1] 和 num[2]
        for (int i = 0; i < nums.length; i++) {
            // 第一层循环，确定比较范围为 [0:len - i - 1]
            // 第二层循环，从前往后，比较相邻元素
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }
    }

    /**
     * 快速排序
     */
    public void quickSort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
    }

    private void quickSort(int[] nums, int left, int right) {
        int pivotIndex = partition(nums, left, right);
        if (left < pivotIndex - 1) {
            quickSort(nums, left, pivotIndex - 1);
        }
        if(pivotIndex + 1 < right) {
            quickSort(nums, pivotIndex + 1, right);
        }
    }

    /**
     * 将 nums[left:right] 部分选择 nums[left] 作为枢纽，
     * 然后将大于枢纽的元素放在枢纽右侧，小于枢纽的元素放在枢纽左侧
     * 最后枢纽被摆在正确的位置，返回这个正确的位置
     */
    private int partition(int[] nums, int left, int right) {
        // 选择第一个元素作为枢纽
        int pivot = nums[left];
        int i = left, j = right;
        while (i < j) {
            // 因为选择了第一个元素作为枢纽，所以现在 nums[i] 被视为一个空位
            // 先移动 j
            while (i < j && nums[j] >= pivot) {
                j--;
            }
            // 发现不满足要求的 nums[j]，它应该在枢纽之前，将其放在空位上
            nums[i] = nums[j];
            // 此时 nums[j] 为空位
            // 然后移动 i
            while (i < j && nums[i] <= pivot) {
                i++;
            }
            // 发现不满足要求的 nums[i]，它应该在枢纽之后，将其放在空位上
            nums[j] = nums[i];
        }
        // i==j时退出循环，最终，nums[i]为正确的枢纽位置，将枢纽放在正确位置
        nums[i] = pivot;
        return i;
    }

    //模式匹配-基本方法

    //模式匹配-KMP方法

    public static void main(String[] args) {
        ArrayOperation a = new ArrayOperation();
        a.quickSort(new int[]{5, 13, 8, 1, 8, 12, 6});
    }
}
