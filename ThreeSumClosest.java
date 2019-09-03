//16. 最接近的三数之和
//
// 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。
// 返回这三个数的和。假定每组输入只存在唯一答案。
//
//例如，给定数组 nums = [-1，2，1，-4], 和 target = 1.
//
//与 target 最接近的三个数的和为 2. (-1 + 2 + 1 = 2).
//
//思路：
//先考虑最接近的两数之和
//两指针法，排序后的数组，left++使两数之和增大，right--使两数之和减小
//进入循环，首先看看当前两数之和是否更接近目标值
//若出现a+b=target return a+b
//若a+b>target，则移动right，进入下次循环
//若a+b<target，则移动left，进入下次循环
//最终循环结束就得到了最接近的a+b的值
//
//那三数之和同理，固定一个数，剩下使用两数之和的逻辑


import java.util.Arrays;

public class ThreeSumClosest {
    public int solution(int[] nums, int target) {
        //距离无穷大的结果
        int ans = Integer.MAX_VALUE - target > target - Integer.MIN_VALUE
                ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        if (nums == null || nums.length < 3)
            return ans;

        Arrays.sort(nums);

        int minDistant = Integer.MAX_VALUE, distant;
        for (int i = 0; i < nums.length; i++) {
            //相同的a会产生相同的结果，故跳过
            if (i != 0 && nums[i] == nums[i - 1])
                continue;

            //从a的后面的数字里找
            int low = i + 1;
            int high = nums.length - 1;
            while (low < high) {
                //刚好等于目标值，那就是最近的三数之和
                if (nums[i] + nums[low] + nums[high] == target)
                    return target;

                //计算当前三数之和有没有更接近目标值
                distant = Math.abs(target - nums[low] - nums[high] - nums[i]);
                if (distant < minDistant) {
                    minDistant = distant;
                    ans = nums[i] + nums[low] + nums[high];
                }

                if (nums[i] + nums[low] + nums[high] > target) {
                    high--;
                    //跳过重复数字
                    while (low < high && nums[high] == nums[high + 1])
                        high--;
                } else {
                    //nums[i] + nums[low] + nums[high] < target
                    low++;
                    //跳过重复数字
                    while (low < high && nums[low] == nums[low - 1])
                        low++;
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        ThreeSumClosest t = new ThreeSumClosest();
        t.solution(new int[]{-1, 0, 1, 1, 55}, 3);

        //测试用例1: 数组[-1, 2, 1, -4]和目标值1
        //测试用例2: 数组[-1, 0, 1, 1, 55]和目标值3
    }
}
