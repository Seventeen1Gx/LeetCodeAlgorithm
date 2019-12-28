// 560. 和为K的子数组
//
// 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
//
// 示例 1 :
// 输入:nums = [1,1,1], k = 2
// 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
//
// 说明 :
// 数组的长度为 [1, 20,000]。
// 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。

package src;

import java.util.HashMap;

public class SubarraySumEqualsK {
    // 暴力法，但用了累加和保存上次结果
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

    // 哈希表
    // sum[i] = nums[0,i)的和，sum[0] = 0
    // sum[i] - sum[j] = nums[j, i)的和，i>j>=0
    // 寻找和为k的nums[i,j)，则求两数之和问题a+b=k，其中a=sum[i]，b=-sum[j]
    public int solution2(int[] nums, int k) {
        int ans = 0;
        int sum = 0;
        // Map存储num[0,i]的值以及该值出现次数
        HashMap<Integer, Integer> hashMap = new HashMap<>(nums.length);
        // ***先放入一个0，即sum[0]=0，预防sum和k相等的情况***
        hashMap.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            // 得到sum[i+1]，即num[0,i+1)，从sum[1]开始
            sum += nums[i];
            // 找到sum[i+1]，去i+1之前找sum[j]，使得sum[i+1]-sum[j]=k
            // 而i+1之前的sum都被存在了哈希表里
            if (hashMap.containsKey(sum - k)) {
                ans += hashMap.get(sum - k);
            }
            hashMap.put(sum, hashMap.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }

}
