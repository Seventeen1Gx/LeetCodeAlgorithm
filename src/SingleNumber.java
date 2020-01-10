package src;

import java.util.HashSet;
import java.util.Set;

/**
 * 136. 只出现一次的数字
 *
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class SingleNumber {
    /**
     * 暴力法
     */
    public int solution1(int[] nums) {
        int ans;
        boolean isUnique;
        for (int i = 0; i < nums.length; i++) {
            // 确定一个数
            ans = nums[i];
            isUnique = true;
            // 去看看它是否重复出现
            for (int j = 0; j < nums.length; j++) {
                if (j == i) {
                    continue;
                }
                if (ans == nums[j]) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                return ans;
            }
        }
        throw new IllegalArgumentException("No SingleNumber Solution");
    }

    /**
     * 使用额外空间
     */
    public int solution2(int[] nums) {
        Set<Integer> hashSet = new HashSet<>();
        // 遍历一遍
        for (int i = 0; i < nums.length; i++) {
            if (hashSet.contains(nums[i])) {
                hashSet.remove(nums[i]);
            } else {
                hashSet.add(nums[i]);
            }
        }
        // 最终集合中就一个数字，就是那个非重复数字
        return hashSet.iterator().next();
    }

    /**
     * 不使用额外空间
     * 使用异或，相同的数异或为0，0与其他数异或保持不变，1与其他数异或取反
     */
    public int solution3(int[] nums) {
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ans ^= nums[i];
        }
        return ans;
    }
}
