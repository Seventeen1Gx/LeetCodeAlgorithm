package src.twoSum;

import java.util.HashMap;

/**
 * 560. 和为K的子数组
 *
 * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
 *
 * 示例:
 * 输入: nums = [1,1,1]， k = 2
 * 输出: 2，[1,1] 与 [1,1] 为两种不同的情况。
 *
 * 说明:
 * 数组的长度为 [1,20000]。
 * 数组中元素的范围是 [-1000,1000]，且整数 k 的范围是 [-1e7,1e7]。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class SubarraySumEqualsK {
    /**
     * 试验所有子数组，使用累加和保存上次结果
     */
    public int solution1(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    ans++;
                }
            }
        }
        return ans;
    }
    
    /**
     * sum[i] 表示为子数组 nums[0,i) 的元素之和，令 sum[0]=0
     * 于是 sum[i]-sum[j] 为子数组 nums[j, i) 的元素之和，且 i>j>=0
     * 寻找和为 k 的子数组 nums[i,j) 的问题，可以转化为两数之和问题，a+b=k，其中 a=sum[i]，b=-sum[j]
     */
    public int solution2(int[] nums, int k) {
        int ans = 0;
        int sum = 0;
        // Map 存储 sum[i] 的值以及该值出现次数
        HashMap<Integer, Integer> hashMap = new HashMap<>(nums.length);
        // ***先放入一个 0，即 sum[0]=0，从而考虑 b=-sum[j] 取 0 的情况***
        hashMap.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            // 随着循环，依次得到 sum[1]，sum[2]，sum[3]，...
            sum += nums[i];
            // 确定数 a，去其之前被存入哈希表中的数中，寻找数 b
            if (hashMap.containsKey(sum - k)) {
                ans += hashMap.get(sum - k);
            }
            // 存入当前的 sum
            hashMap.put(sum, hashMap.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

}
