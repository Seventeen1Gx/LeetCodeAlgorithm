// 287. 寻找重复数
//
// 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
// 假设只有一个重复的整数，找出这个重复的数。
//
// 注意：有个要求是不能改动这个数组


package src.binarySearch;

public class FindTheDuplicateNumber {
    // 移动每个元素 x 到 nums[x] 上，这样一定有个元素的位置被占了
    public int solution(int[] nums) {
        // 检查每个位置，是不是放了正确的元素
        for (int i = 1; i < nums.length; i++) {
            while (nums[i] != i) {
                if (nums[nums[i]] == nums[i])
                    return nums[i];
                int t=nums[i];
                nums[i] = nums[nums[i]];
                nums[nums[i]]=t;
            }
        }
        return nums[0];
    }

}
