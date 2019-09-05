//18. 四数之和
//
//给定一个包含 n 个整数的数组 nums 和一个目标值 target，判断 nums 中是否存在四个元素 a，b，c 和 d ，使得 a + b + c + d 的值与 target 相等？找出所有满足条件且不重复的四元组。
//
//注意：
//
//答案中不可以包含重复的四元组。
//
//示例：
//
//给定数组 nums = [1, 0, -1, 0, -2, 2]，和 target = 0。
//
//满足要求的四元组集合为：
//[
//  [-1,  0, 0, 1],
//  [-2, -1, 1, 2],
//  [-2,  0, 0, 2]
//]


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
    //采用三数之和的思路
    public List<List<Integer>> solution(int[] nums, int target) {
        List<List<Integer>> ansList = new ArrayList<>();

        if (nums == null || nums.length < 4)
            return ansList;

        //排序
        Arrays.sort(nums);

        //保证a<=b<=c<=d
        int a = 0, b = 0, c, d;
        for (int i = 0; i < nums.length; i++) {
            //开头为a=num[i]的结果已经考虑过了
            if (i != 0 && a == nums[i])
                continue;
            for (int j = i + 1; j < nums.length; j++) {
                //与上一对a、b比较，相同的组合已经考虑过，则跳过，第一次得到a、b的组合时(此时j=i+1)，不用跳过
                if (j != i + 1 && a == nums[i] && b == nums[j])
                    continue;

                a = nums[i];
                b = nums[j];

                //首尾指针
                int low = j + 1;
                int high = nums.length - 1;
                while (low < high) {
                    c = nums[low];
                    d = nums[high];

                    if (a + b + c + d > target) {
                        high--;
                        //跳过重复
                        while (low < high && nums[high] == nums[high + 1])
                            high--;
                    } else if (a + b + c + d < target) {
                        low++;
                        //跳过重复
                        while (low < high && nums[low] == nums[low - 1])
                            low++;
                    } else {
                        List<Integer> l = new ArrayList<>();
                        l.add(a);
                        l.add(b);
                        l.add(c);
                        l.add(d);
                        ansList.add(l);
                        low++;
                        high--;
                        //跳过重复
                        while (low < high && nums[low] == nums[low - 1] && nums[high] == nums[high + 1]) {
                            low++;
                            high--;
                        }
                    }
                }
            }
        }
        return ansList;
    }

    public static void main(String[] args) {
        FourSum f = new FourSum();
        f.solution(new int[]{-1, -5, -5, -3, 2, 5, 0, 4}, 0);

        //测试用例: [-1, -5, -5, -3, 2, 5, 0, 4] 与 7
        //测试用例: [1, 0, -1, 0, -2, 2] 与 0
    }
}