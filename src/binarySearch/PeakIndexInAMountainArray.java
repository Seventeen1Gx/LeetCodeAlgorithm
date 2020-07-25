// 852. 山脉数组的顶峰索引
//
// 我们把符合下列属性的数组 A 称作山脉：
// A.length >= 3
// 存在 0 < i < A.length - 1 使得A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
//
// 给定一个确定为山脉的数组，返回任何满足 A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1] 的 i 的值。


package src.binarySearch;

public class PeakIndexInAMountainArray {
    public int solution(int[] mountain) {
        // 由题意，山顶一定存在
        // 且不会是首尾
        int l = 1, h = mountain.length - 2;
        while (l < h) {
            int mid = l + (h - l) / 2;
            if (mountain[mid - 1] < mountain[mid] && mountain[mid] < mountain[mid + 1]) {
                // mid 在左边上升范围，[l,mid] 可排除
                l = mid + 1;
            } else {
                // mid 不在左边上升范围，[mid+1,h] 可排除
                h = mid;
            }
        }
        return l;
    }
}
