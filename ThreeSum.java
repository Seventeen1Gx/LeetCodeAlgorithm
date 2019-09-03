//15. 三数之和
//
//给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
//
//注意：答案中不可以包含重复的三元组。


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> solution(int[] nums) {
        List<List<Integer>> ansList = new ArrayList<>();

        if (nums == null || nums.length < 3)
            return ansList;

        //从小到大排序
        Arrays.sort(nums);
        int a;
        for (int i = 0; i < nums.length; i++) {
            //选取数组中的一个数作为a，然后在剩下的数中去找b+c=-a
            //相同的a会产生重复的结果，所以我们跳过
            if (i != 0 && nums[i] == nums[i - 1])
                continue;
            a = nums[i];

            //因为我们保证结果中a<=b<=c，若a>0，三数之和一定大于0了
            if (a > 0)
                continue;

            //首尾指针(注意这里令i<low<high，原来的做法low=0，high=len-1仍会出现重复)
            //这样结果三元组中nums[i]<nums[low]<nums[high]始终成立！！！
            //即我们是将结果三元组排序，然后每个三元组根据首元素再排序，我们的算法按顺序找出！！！
            int low = i + 1;
            int high = nums.length - 1;
            int b = 0, c = 0;
            //标志第一次找到b，c结果
            boolean isFirst = true;
            while (low < high) {
                //跳过已使用的nums[i]
                if (low == i)
                    low++;
                if (high == i)
                    high--;

                if (nums[low] + nums[high] > -a)
                    high--;
                else if (nums[low] + nums[high] < -a)
                    low++;
                else {
                    //找到满足条件的a、b、c
                    if (isFirst || nums[low] != b) {
                        //不是重复答案的时候进行存储
                        b = nums[low];
                        c = nums[high];

                        List<Integer> l = new ArrayList<>();
                        l.add(a);
                        l.add(b);
                        l.add(c);
                        ansList.add(l);
                    }
                    //寻找下一个答案
                    low++;
                    high--;
                    //往后不再是第一次
                    isFirst = false;
                }
            }
        }
        return ansList;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};

        ThreeSum t = new ThreeSum();

        t.solution(nums);
    }
}
