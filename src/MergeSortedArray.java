// 88. 合并两个有序数组
//
// 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
//
// 说明:
//  - 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
//  - 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。


package src;

public class MergeSortedArray {
    public static void main(String[] args) {
        System.out.println(1 + 2 + "3");
    }

    public void solution(int[] nums1, int m, int[] nums2, int n) {
        // nums1 的剩余空间大于等于 n
        // 所以从后往前放

        int start = m + n - 1;
        int i = m - 1;
        int j = n - 1;

        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[start] = nums1[i];
                i--;
            } else {
                nums1[start] = nums2[j];
                j--;
            }
            start--;
        }

        while (i >= 0) {
            nums1[start] = nums1[i];
            i--;
            start--;
        }

        while (j >= 0) {
            nums1[start] = nums2[j];
            j--;
            start--;
        }
    }
}
