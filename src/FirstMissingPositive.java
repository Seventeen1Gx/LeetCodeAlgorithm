//41. 缺失的第一个正数
//
//给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
//
//示例 1:
//输入: [1,2,0]
//输出: 3
//
//示例 2:
//输入: [3,4,-1,1]
//输出: 2
//
//示例 3:
//输入: [7,8,9,11,12]
//输出: 1
//
//说明:
//你的算法的时间复杂度应为O(n)，并且只能使用常数级别的空间。
//
//思路:
//鸽巢原理：将n+1个元素放入n个位置，则至少有2个元素呆在同一位置。--一个萝卜一个坑
//
//对于本题，数组长度为n，第一个没有出现的正数一定在区间[1:n+1]内
//所以，我们将每个元素放在对应的坑里，使用的方法是原数组内交换
//对于对应坑不在我们所考虑范围内的，我们忽略
//最后得到放好位置的数组，然后找到第一个不满足对应条件的那个位置，则这个位置代表的正数是第一个缺失的正数


package src;

public class FirstMissingPositive {
    public int solution1(int[] nums) {
        if (nums == null)
            return 1;

        //数组长度为n，缺失的第一个正数的值为[1:n+1]
        int n = nums.length;

        if (n == 0)
            return 1;

        //对于长度为n的数组，即有n个坑去放元素，我们期望，第一个位置放1，第二个位置放2，...
        int i = 0;
        while (i < n) {
            //看当前所指位置的元素num[i]
            if (nums[i] == i + 1 || nums[i] <= 0 || nums[i] > n) {
                //该元素已放在其所应该在的坑中
                //或者该元素的坑不在当前数组范围内
                //考虑下一个元素
                i++;
                continue;
            } else {
                //将当前元素换到它应该在的位置上，即下标为num[i]-1处
                int index = nums[i] - 1;
                if (nums[index] == nums[i]) {
                    //要交换的位置上已经放了一个跟自己相等的元素
                    //所以不用交换
                    //这种情况可以看看测试用例[1, 1]
                    i++;
                } else {
                    int t = nums[index];
                    nums[index] = nums[i];
                    nums[i] = t;
                }
            }
        }

        //重新遍历一遍数组，找到第一个不满足对应条件的坑，则第一个缺失的正数是该坑上应该摆放的那个元素
        for (i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                break;
            }
        }

        return i + 1;
    }

    //官方给的方法
    public int solution2(int[] nums) {
        if (nums == null)
            return 1;

        int n = nums.length;

        if (n == 0)
            return 1;

        //遍历一遍，将<=0且大于n的数字转换成1，因为这些数字无需考虑，第一个缺失的正数一定在[1:n+1]内
        //注意转换前，有无1的存在，若无，则1就是缺失的第一个正数
        boolean containOne = false;
        for (int i = 0; i < n; i++) {
            //转换前
            if (nums[i] == 1) {
                containOne = true;
            }
            //转换
            if (nums[i] <= 0 || nums[i] > n) {
                nums[i] = 1;
            }
        }

        if (!containOne)
            return 1;

        //处理数组，使得nums[i]<0表示数i+1存在
        for (int i = 0; i < n; i++) {
            int positiveNum = Math.abs(nums[i]);
            //这个位置加上负号
            nums[positiveNum - 1] = -Math.abs(nums[positiveNum - 1]);
        }

        //找到第一个不存在的正数
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0)
                return i + 1;
        }

        //为[1,2,3,4,5]缺失6的情况
        return n + 1;
    }

    public static void main(String[] args) {
        FirstMissingPositive f = new FirstMissingPositive();
        f.solution1(new int[]{1, 1});
        f.solution2(new int[]{1, 2, 0});
    }
}
