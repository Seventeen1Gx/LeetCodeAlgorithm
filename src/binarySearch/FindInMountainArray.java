// 1095. 山脉数组中查找目标值
//
// 这是一个 交互式问题
//
// 给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小的下标 index 值。
//
// 如果不存在这样的下标 index，就请返回 -1。
//
// 何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：
// 首先，A.length >= 3
// 其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
// A[0] < A[1] < ... A[i-1] < A[i]
// A[i] > A[i+1] > ... > A[A.length - 1]
// 
// 你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
// MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
// MountainArray.length() - 会返回该数组的长度


package src.binarySearch;

public class FindInMountainArray {
    // 先找到山顶元素的下标
    // 然后分成两个数组寻找目标值

    public int solution(int target, MountainArray mountainArr) {
        int low = 0, high = mountainArr.length() - 1, mountainTop;
        while (low < high) {
            // 首先进入循环时，[low,high] 一定有 2 个以上的元素
            // 而下面的 midIndex 是向下取整的中位数
            // 所以 midIndex+1 肯定不越界
            int midIndex = low + (high - low) / 2;
            int mid = mountainArr.get(midIndex);
            int nex = mountainArr.get(midIndex + 1);

            // 由题意，相邻元素一定不等
            // 那么有下面两种情况
            // mid < nex，说明 mid 在左边，山顶在 [midIndex+1,high] 中
            // mid > nex，说明 mid 在右边，山顶在 [low,midIndex] 中
            if (mid < nex)
                low = midIndex + 1;
            else
                high = midIndex;
        }
        // 山顶一定存在
        mountainTop = low;
        // 先在左边找，找不到再去右边

        low = 0;
        high = mountainTop;
        while (low < high) {
            int midIndex = low + (high - low) / 2;
            int mid = mountainArr.get(midIndex);
            // [low,midIndex] [midIndex+1,high]

            if (mid >= target)
                // [midIndex+1,high] 肯定不是
                high = midIndex;
            else
                // mid < target，[low,midIndex] 肯定不是
                low = midIndex + 1;
        }

        if (mountainArr.get(low) == target) {
            return low;
        } else {
            low = mountainTop + 1;
            high = mountainArr.length() - 1;
            while (low < high) {
                int midIndex = low + (high - low) / 2;
                int mid = mountainArr.get(midIndex);
                if (mid <= target)
                    high = midIndex;
                else
                    low = midIndex + 1;
            }
            return mountainArr.get(low) == target ? low : -1;
        }
    }

    public static void main(String[] args) {
        new FindInMountainArray().solution(3, new MountainArrayImp(new int[]{1, 2, 3, 4, 5, 3, 1}));
    }
}

interface MountainArray {
    public int get(int index);
    public int length();
}

class MountainArrayImp implements MountainArray {
    int[] nums;

    MountainArrayImp(int[] nums) {
        this.nums = nums;
    }

    public int get(int index) {
        return nums[index];
    }

    public int length() {
        return nums.length;
    }
}
