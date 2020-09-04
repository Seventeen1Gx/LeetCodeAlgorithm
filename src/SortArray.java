// 912. 排序数组
//
// 给你一个整数数组 nums，请你将该数组升序排列。


package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortArray {
    public int[] sortArray(int[] nums) {
        sortArray(nums, "select");
        return nums;
    }

    private void sortArray(int[] nums, String sortType) {
        switch (sortType) {
            case "select":
                selectSort(nums, nums.length);
                break;
            case "bubble":
                bubbleSort(nums, nums.length);
                break;
            case "insert":
                insertSort(nums, nums.length);
                break;
            case "shell":
                shellSort(nums, nums.length);
                break;
            case "heap":
                heapSort(nums, nums.length);
                break;
            case "merge":
                mergeSort(nums, nums.length);
                break;
            case "quick":
                quickSort(nums, nums.length);
                break;
            case "radix":
                radixSort(nums, nums.length);
                break;
            default:
                break;
        }
    }

    private void selectSort(int[] nums, int n) {
        for (int i = 0; i < n; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < n; j++) {
                minElementIndex = nums[j] < nums[minElementIndex] ? j : minElementIndex;
            }
            swap(nums, i, minElementIndex);
        }
    }

    private void bubbleSort(int[] nums, int n) {
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                    flag = false;
                }
            }
            if (flag) {
                return;
            }
        }
    }

    private void insertSort(int[] nums, int n) {
        for (int i = 1; i < n; i++) {
            int temp = nums[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (nums[j] > temp) {
                    nums[j + 1] = nums[j];
                } else {
                    break;
                }
            }
            nums[j + 1] = temp;
        }
    }

    private void shellSort(int[] nums, int n) {
        for (int gap = n / 2; gap >= 1; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = nums[i];
                int j;
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

    private void mergeSort(int[] nums, int n) {
        mergeSort(nums, 0, n - 1);
    }

    private void mergeSort(int[] nums, int lo, int hi) {
        if (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            mergeSort(nums, lo, mid);
            mergeSort(nums, mid + 1, hi);
            merge(nums, lo, mid, hi);
        }
    }

    private void merge(int[] nums, int lo, int mid, int hi) {
        int n = hi - lo + 1;
        int[] temp = new int[n];
        int i = lo;
        int j = mid + 1;
        int k = 0;
        while (i <= mid || j <= hi) {
            int num1 = i <= mid ? nums[i] : Integer.MAX_VALUE;
            int num2 = j <= hi ? nums[j] : Integer.MAX_VALUE;
            if (num1 <= num2) {
                temp[k++] = num1;
                i++;
            } else {
                temp[k++] = num2;
                j++;
            }
        }
        System.arraycopy(temp, 0, nums, lo, n);
    }

    private void heapSort(int[] nums, int n) {
        buildHeap(nums, n);
        for (int i = 0; i < n; i++) {
            swap(nums, 0, n - 1 - i);
            adjust(nums, n - i - 1, 0);
        }
    }

    private void buildHeap(int[] nums, int n) {
        for (int i = n / 2 - 1; i >= 0; i--) {
            adjust(nums, n, i);
        }
    }

    private void adjust(int[] nums, int n, int adjustIndex) {
        int temp = nums[adjustIndex];
        int i;
        for (i = adjustIndex * 2 + 1; i < n; i = i * 2 + 1) {
            if (i + 1 < n && nums[i + 1] > nums[i]) {
                i++;
            }
            if (nums[i] > temp) {
                nums[(i - 1) / 2] = nums[i];
            } else {
                break;
            }
        }
        nums[(i - 1) / 2] = temp;
    }

    private void quickSort(int[] nums, int n) {
        quickSort(nums, 0, n - 1);
    }

    private void quickSort(int[] nums, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = partition(nums, lo, hi);
        quickSort(nums, lo, mid - 1);
        quickSort(nums, mid + 1, hi);
    }

    private int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];
        int i = lo;
        int j = hi;
        while (i < j) {
            while (i < j && nums[j] >= pivot) {
                j--;
            }
            nums[i] = nums[j];
            while (i < j && nums[i] <= pivot) {
                i++;
            }
            nums[j] = nums[i];
        }
        nums[i] = pivot;
        return i;
    }

    private void radixSort(int[] nums, int n) {
        // 不支持负数

        List<List<Integer>> bucks = new ArrayList<>();

        int max = Arrays.stream(nums).max().orElse(-1);

        int radix = 1;
        while (max / radix > 0) {
            for (int i = 0; i < 10; i++) {
                bucks.add(new ArrayList<>());
            }

            for (int i = 0; i < n; i++) {
                int num = nums[i];
                bucks.get((num / radix) % 10).add(num);
            }

            int j = 0;
            for (int i = 0; i < 10; i++) {
                for (Integer integer : bucks.get(i)) {
                    nums[j++] = integer;
                }
            }

            radix *= 10;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 计数排序
    // 创建新数组 a，容量为待排序数组最大元素 +1
    // 遍历待排序数组，a[nums[i]]++
    // 遍历 a 数组，a[i] 是几就输出几个 i

    // 桶排序
    // 创建桶，区间跨度=(最大值-最小值)/(桶的数量-1)
    // 遍历数组，对号入座
    // 每个桶进行排序
    // 遍历桶，输出结果
}
