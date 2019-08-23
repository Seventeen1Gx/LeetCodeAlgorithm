// 1. 两数之和
//
//给定一个整数数组nums和一个目标值target，请你在该数组中找出和为目标值的那两个整数，并返回他们的数组下标。
//
//你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
//
//第一反应是排序数组，双游标从两边向中间搜索的方法，但是后来发现，本道题返回的是两元素在未排序前数组中的下标
//
//感悟
//1. 多时不用Java，数组的构造有些遗忘
//2. 注意Java中的哈希表--无须键值对，键不可重复，提取某键值时，先看该键是否存在
//3. 注意[3, 3]和6的情况要返回[0, 1]，是同一个元素不能重复利用
//4. 一次遍历的哈希表法主要是因为，a+b=c，从a找c-b与从b找c-a是等价的


import java.util.HashMap;
import java.util.HashSet;

public class TwoNum {
    //暴力解法，注意两两组合的遍历方法
    public int[] solution1(int[] nums, int target) {
        //如下语句创建的整数数组，默认元素值为2
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

    //哈希表法--两次遍历
    public int[] solution2(int[] nums, int target) {
        //另一种创建数组的方式
        int[] ans = new int[]{0, 0};

        //遍历一遍数组，建立值-下标的哈希表
        //注意题目中的条件，同一个元素不能重复利用，故这里一个值记录一个下标即可
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            hashMap.put(nums[i], i);
        }

        int num_1;
        int num_2;
        for (int i = 0; i < nums.length; i++) {
            num_1 = nums[i];
            num_2 = target - num_1;
            //同一个元素不能重复利用
            if (hashMap.containsKey(num_2) && hashMap.get(num_2) != i) {
                ans[0] = i;
                ans[1] = hashMap.get(num_2);
            }
        }
        return ans;
    }

    //哈希表法--一次遍历
    public int[] solution3(int nums[], int target) {
        //这里不创建答案数组，采用标准答案中的写法

        //遍历的同时，看已经存在的键是否有合适的
        int num_1;
        int num_2;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            num_1 = nums[i];
            num_2 = target - num_1;
            //因为当前元素num_1还未放进哈希表，所以这不会出现重复利用同一元素的可能
            if (hashMap.containsKey(num_2)) {
                return new int[]{i, hashMap.get(num_2)};
            } else {
                hashMap.put(num_1, i);
            }
        }
        throw new IllegalArgumentException("No tow num solution");
    }

    public static void main(String[] args) {
        TwoNum twoNum = new TwoNum();

        //测试数据1
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;

        //测试数据2
        nums = new int[]{3, 3};
        target = 6;

        twoNum.solution2(nums, target);
    }
}
