// 303. 区域和检索 - 数组不可变
//
// 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。


package src;

public class RangeSumQueryImmutable {
    // sum[i] 表示 nums[0,i) 的连续和
    int[] sum;

    public RangeSumQueryImmutable(int[] nums) {
        int len = nums.length;
        sum = new int[len + 1];
        for (int i = 1; i <= len; i++)
            sum[i] = sum[i - 1] + nums[i - 1];
    }

    //  转换成 sum[j+1] - sum[i]
    public int sumRange(int i, int j) {
        return sum[j + 1] - sum[i];
    }

}
