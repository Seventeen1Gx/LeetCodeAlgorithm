package src.twoSum;

import java.util.HashMap;

/**
 * 1. 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 思路：
 * 第一反应是排序数组，双游标从两边向中间搜索的方法，但是后来发现，本道题返回的是两元素在未排序前数组中的下标，而排序会打乱下标。
 *
 *
 * 注意：
 * - 输入数组为 [3,3]，目标值为 6 的情况，结果为 [0,1]，这不算重复利用数组相同元素
 * - 一次遍历的哈希表法的背后思想，对于　a+b=c，从 a 找 c-b 与从 b 找 c-a 是等价的
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class TwoSum {
    /**
     * 暴力法，试验所有的两两组合
     */
    public int[] solution1(int[] nums, int target) {
        int[] ans = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    ans[0] = i;
                    ans[1] = j;
                }
            }
        }
        return ans;
    }

    /**
     * 哈希表法 -- 两次遍历
     *
     * 注意：
     * 对于输入 [3,3] 与 6，我们可以看到
     * hashMap 中保存的是重复元素的最后一次出现，即 (3 → 1)
     * 而第二次遍历中， num1 先保存的是重复元素的第一次出现，即下标 0 处的 3，再去 hashMap 中寻找 num2
     * 所以在不使用重复元素的情况下，仍可以得到正确结果
     */
    public int[] solution2(int[] nums, int target) {
        int[] ans = new int[]{0, 0};

        // 遍历一遍数组，建立"元素-下标"的哈希表
        // 由于 hashMap 的键的唯一性，对于数组中的重复元素，保存的是其最后一次出现的位置下标
        HashMap<Integer, Integer> hashMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            hashMap.put(nums[i], i);
        }

        int num1;
        int num2;
        for (int i = 0; i < nums.length; i++) {
            num1 = nums[i];
            num2 = target - num1;
            // hashMap.get(num2) != i → 同一个元素不能重复利用
            if (hashMap.containsKey(num2) && hashMap.get(num2) != i) {
                ans[0] = i;
                ans[1] = hashMap.get(num2);
                break;
            }
        }
        return ans;
    }

    /**
     * 哈希表法 -- 一次遍历
     * 对于当前遍历到的 nums[i]，查看 [0,i-1] 中，即 num[i] 之前的元素中是否有我们需要的 target-nums[i]
     * */
    public int[] solution3(int[] nums, int target) {
        int num1;
        int num2;
        HashMap<Integer, Integer> hashMap = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            num1 = nums[i];
            num2 = target - num1;
            // 因为当前元素 num1 还未放进哈希表，所以不会出现重复利用同一元素的情况
            if (hashMap.containsKey(num2)) {
                return new int[]{i, hashMap.get(num2)};
            } else {
                hashMap.put(num1, i);
            }
        }
        throw new IllegalArgumentException("No TwoSum Solution");
    }


    public static void main(String[] args) {
        TwoSum twoNum = new TwoSum();

        // 测试数据1
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;

        twoNum.solution2(nums, target);

        // 测试数据2
        nums = new int[]{3, 3};
        target = 6;

        twoNum.solution2(nums, target);
    }
}
