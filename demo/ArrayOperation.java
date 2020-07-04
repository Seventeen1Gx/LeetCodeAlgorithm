package demo;

import java.util.*;

/**
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ArrayOperation {
    /**
     * 折半查找
     */
    public int binarySearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1, mid;
        // 在 [left, right] 范围内寻找目标数字
        // 每次没找到，就减半搜索范围
        // 注意 left == right 时，仍要进入循环判断
        while (left <= right) {
            mid = (left + right) >> 1;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        // nums 中不存在 target
        // 返回 target 应该插入的位置，即 left
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
        // 每次寻找 [i:] 中最小的元素，然后跟位置 i 的元素交换
        for (int i = 0; i < nums.length; i++) {
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                min = nums[min] < nums[j] ? min : j;
            }
            swap(nums, min, i);
        }
    }

    public void selectSort2(int[] nums) {
        // 一次性确定两个数
        for (int i = 0, j = 0; i < nums.length - j; i++, j++) {
            int min = i;
            int max = nums.length - j - 1;
            for (int k = i + 1; k < nums.length - j - 1; k++) {
                min = nums[min] < nums[k] ? min : k;
                max = nums[max] > nums[k] ? max : k;
            }
            swap(nums, min, i);
            swap(nums, max, nums.length - j - 1);
        }
    }

    /**
     * 冒泡排序
     */
    public void bubbleSort(int[] nums) {
        // 控制冒泡的循环次数，每次确定一个数的位置
        for (int i = 0; i < nums.length; i++) {
            // 用于提前终止，提高效率
            boolean flag = true;
            // 第一层循环，确定比较范围为 [0:len - i - 1]
            // 第二层循环，从前往后依次遍历，比较相邻元素
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * 直接插入排序
     */

    public void insertSort(int[] nums) {
        // 第 i 个元素插入到 0~i-1 号元素之中
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (nums[j] > temp) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            // break 跳出循环时，nums[j] <= temp
            nums[j + 1] = temp;
        }
    }

    public void insertSort2(int[] nums) {
        // 使用二分查找，确定插入位置
        for (int i = 1; i < nums.length; i++) {
            int temp = nums[i];
            int insertIndex = binarySearch(Arrays.copyOf(nums, i), temp);
            for (int j = i; j > insertIndex ; j--) {
                nums[j] = nums[j - 1];
            }
            nums[insertIndex] = temp;
        }
    }

    public  void shellSort(int[] nums) {
        // 设置 gap，每次减半，最后一次 gap=1
        for (int gap = nums.length / 2; gap > 0; gap /= 2) {
            // 遍历每个小组，每次从那个组的第 2 个元素开始
            for (int i = gap; i < nums.length; i++) {
                // 每个小组进行插入排序
                int j;
                int temp = nums[i];
                for (j = i - gap; j >= 0; j -= gap) {
                    if (nums[j] > temp) {
                        nums[j + gap] = nums[j];
                    } else {
                        break;
                    }
                }
                nums[j + gap] = temp;
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

    private void quickSort2(int[] nums, int left, int right) {
        int i = left;
        int j = right;

        int pivot = nums[(left + right) >> 1];
        // 下面的一次循环，保证了枢纽左边的元素较小，右边的元素较大
        while (i <= j) {
            // 从左往右寻找大于枢纽的元素，i 不回退
            while (nums[i] < pivot) {
                i++;
            }

            // 从右往左寻找小于枢纽的元素，j 不回退
            while (nums[j] > pivot) {
                j--;
            }

            if (i <= j) {
                swap(nums, i, j);
                i++;
                j--;
            }
        }

        // 再去分别排序枢纽左右
        if (left < j) {
            quickSort2(nums, left, j);
        }

        if (right > i) {
            quickSort2(nums, i, right);
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


    /**
     * 归并排序
     */
    public void mergeSort(int[] nums) {
        // 先创建临时数组，就能避免在递归过程中建立
        int[] temp = new int[nums.length];
        mergeSort(nums, 0, nums.length - 1, temp);
    }

    /**
     * 对 [left, right] 部分进行归并排序
     */
    private void mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left < right) {
            int mid = (left + right) >> 1;
            mergeSort(nums, left, mid, temp);
            mergeSort(nums, mid + 1, right, temp);
            // 此时左右两部分有序，进行合并
            merge(nums, left, mid, right, temp);
        }
    }

    // 将 [l1,r1] 和 [l2,r2] 两部分进行归并
    private void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int p = left, q = mid + 1;
        int k = 0;

        while (p <= mid || q <= right) {
            int val;
            if (p > mid) {
                val = nums[q++];
            } else if (q > right) {
                val = nums[p++];
            } else {
                val = nums[p] < nums[q] ? nums[p++] : nums[q++];
            }
            temp[k++] = val;
        }

        // temp 数组拷贝回原数组
        k = 0;
        while (left <= right) {
            nums[left++] = temp[k++];
        }

    }

    /**
     * 基数排序，也叫桶排
     * 分配与回收
     * 不适合数组里有负数（因为比较每位）和 0（因为回收判断） 的情况
     */
    public void radixSort(int nums[]) {
        // 从最低位开始，每次比较数字的一位，按该位数值放到不同的桶里

        int max = max(nums);

        // 如果最大的数还有位数没比较循环就继续
        for (int i = 1; (max /= i) > 0; i *= 10) {
            // 初始化一个桶，默认值为 0
            // 也可由单链表结点数组实现
            int[][] bucks = new int[nums.length][10];
            // 遍历 nums，进行分配每个元素到对应的桶里
            for (int j = 0; j < nums.length; j++) {
                int bit = nums[j] / i % 10;
                bucks[j][bit] = nums[j];
            }
            // 回收，按列遍历
            int index = 0;
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < nums.length; k++) {
                    if (bucks[k][j] != 0) {
                        nums[index] = bucks[k][j];
                        index++;
                    }
                }
            }
        }
    }

    /**
     * 堆排序
     * 建堆
     * 跟结点跟堆的最后一个叶子结点交换，然后剩下的部分又调整
     *
     * 数组的下标对应着树从上往下层次遍历的序号
     */
    public void headSort(int nums[]) {
        // 建堆
        buildHeap(nums);
        // 选择排序
        // 每次把根（最大结点）放在指定位置上
        for (int i = nums.length - 1; i > 0; i--) {
            swap(nums, 0, i);
            adjustHeap(nums, 0, i);
        }
    }

    /**
     * 建立最大堆，即每个非叶子结点，都要大于等于它的子节点
     */
    private void buildHeap(int nums[]) {
        int len = nums.length;
        // 从最右下角的非叶子结点开始，往前遍历
        int start = len / 2 - 1;
        // 遍历，找到不满足要求的
        for (int i = start; i >= 0; i--) {
            adjustHeap(nums, i, nums.length);
        }
    }

    private void adjustHeap(int[] nums, int i, int len) {
        // i 始终指向需要调整的结点
        // k 始终指向 i 的儿子结点中最大的那个
        // 每次调整要一直往下直到满足条件或者到达叶子结点
        int value = nums[i];
        for (int k = i * 2 + 1; k < len; k = k * 2 + 1) {
            if (k + 1 < len && nums[k] < nums[k + 1]) {
                k++;
            }
            if (value < nums[k]) {
                nums[i] = nums[k];
                i = k;
            } else {
                break;
            }
        }
        // 放到最终位置
        nums[i] = value;

    }

    /**
     * 返回数组里的最大值
     */
    public int max(int nums[]) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Arguments is false!");
        }

        int max = nums[0];
        for (int i = 2; i < nums.length; i++) {
            if (nums[i] > max)
                max = nums[i];
        }
        return max;
    }

    //模式匹配-基本方法

    //模式匹配-KMP方法

    public static void main(String[] args) {
        ArrayOperation a = new ArrayOperation();
        a.quickSort(new int[]{5,7,2,3,0,4});
    }
}