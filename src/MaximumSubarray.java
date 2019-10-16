//53. 最大子序和
//
//给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。


package src;

public class MaximumSubarray {
    //暴力法，尝试所有的子序列

    //一次遍历完成
    public int solution1(int[] nums) {
        if (nums == null)
            return Integer.MIN_VALUE;

        int sum = 0, max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            //当发现sum为负数时，从当前i位置重新统计
            if (sum < 0)
                sum = nums[i];
            else
                sum += nums[i];
            if (sum > max)
                max = sum;
        }
        return max;
    }

    //分治法
    //分成左右两部分，最大子序列和的子序列可能全部在左边，也可能全部右边，也可能跨越中间
    //所以是三选一
    public int solution2(int[] nums) {
        return divideAndConquer(nums, 0, nums.length - 1);
    }

    //求nums[]中[begin, end]范围的最大子序和
    public int divideAndConquer(int[] nums, int begin, int end) {
        if (begin == end)
            return nums[begin];

        //二分
        int mid = (begin + end) / 2;
        //左边的最大子序和
        int leftResult = divideAndConquer(nums, begin, mid);
        //右边的最大子序和
        int rightResult = divideAndConquer(nums, mid + 1, end);
        //跨越中间的最大子序和，从mid开始往左，找最大字序和，同时从mid+1往右，找最大子序和，这两者之和就是跨越中间的最大子序列之和
        int maxLeft = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= begin; i--) {
            sum += nums[i];
            maxLeft = Math.max(sum, maxLeft);
        }
        int maxRight = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= end; i++) {
            sum += nums[i];
            maxRight = Math.max(sum, maxRight);
        }

        //取三种结果最大的
        return leftResult > rightResult ?
                (leftResult > maxLeft + maxRight ? leftResult : maxLeft + maxRight) :
                (rightResult > maxLeft + maxRight ? rightResult : maxLeft + maxRight);

    }
}
