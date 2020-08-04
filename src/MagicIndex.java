// 面试题 08.03. 魔术索引
//
// 在数组 A[0...n-1] 中，有所谓的魔术索引，满足条件 A[i] = i。
// 给定一个有序整数数组，编写一种方法找出魔术索引。
// 若有的话，在数组 A 中找出一个魔术索引，如果没有，则返回 -1。
// 若有多个魔术索引，返回索引值最小的一个。


package src;

public class MagicIndex {
    // 顺序遍历
    public int solution1(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == i) {
                return i;
            }
        }
        return -1;
    }

    // 但顺序遍历没有用到数组有序这个信息
    // 想想能不能用二分法
    // 而二分法的关键是，每次划分左右后，能舍弃一半元素
    //
    // 二分之后有下面三种情形
    // nums[mid] == mid
    //     → 可能多个魔术索引，我们要最小的，排除 [mid+1,high]
    //     → 因为 [mid+1,high] 就算存在魔术索引，也是 mid 比他们小
    // nums[mid] > mid
    //     举个例子：
    //     index 0 1 2 3 4
    //     nums  1 1 3 3 4
    //     直观上看，左右两边都有可能出现魔术索引
    //     → 分而治之，先找左边，左边找不到才找右边
    // nums[mid] < mid
    //     index 0 1 2 3 4
    //     nums  0 0 1 3 4
    //     → 同理
    public int solution2(int[] nums) {
        return getIndex(nums, 0, nums.length - 1);
    }

    // 分而治之
    private int getIndex(int[] nums, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left) / 2;
        if (nums[mid] == mid) {
            int index = getIndex(nums, left, mid - 1);
            return index == -1 ? mid : index;
        }
        // 先找左边再找右边
        int index = getIndex(nums, left, mid - 1);
        if (index != -1) {
            return index;
        }
        return getIndex(nums, mid + 1, right);
    }

    public static void main(String[] args) {
        new MagicIndex().solution2(new int[]{0, 2, 3, 4, 5});
    }
}
