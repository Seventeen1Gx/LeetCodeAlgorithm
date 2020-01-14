package src;

import java.util.HashMap;
import java.util.Map;

/**
 * 169. 多数元素
 *
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊n/2⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class MajorityElement {
    /**
     * 直接计数，计算每个元素出现的次数，最终返回出现次数大于 n/2 的元素
     */
    public int solution1(int[] nums) {
        Map<Integer, Integer> hashMap = new HashMap<>(nums.length);
        for (int num : nums) {
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() > nums.length / 2) {
                return entry.getKey();
            }
        }
        return -1;
    }

    /**
     * 两两消除法，因为多数元素总超过半数，故两两消除后剩下的元素一定是多数元素
     */
    public int solution2(int[] nums) {
        int ans = -1;
        int cnt = 0;
        for (int num : nums) {
            if (cnt == 0) {
                ans = num;
                cnt = 1;
                continue;
            }

            if (num == ans) {
                cnt++;
            } else {
                cnt--;
            }
        }
        return ans;
    }
}
