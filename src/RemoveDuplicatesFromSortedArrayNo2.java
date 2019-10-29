//80. 删除排序数组中的重复项Ⅱ
//
//给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素最多出现两次，返回移除后数组的新长度。
//
//不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
//
//跟26题的区别是，26题要求每个元素只出现一次，本题是至多可以出现两次

package src;

public class RemoveDuplicatesFromSortedArrayNo2 {
    //排序数组，重复项在数组中相邻
    public int solution(int[] nums) {
        if (nums == null)
            return -1;

        if (nums.length == 0)
            return 0;

        //一个游标i，从第二个元素开始遍历每个元素
        //另一个指针j，指向的位置是上一次保留的元素，初始化指向第一个元素
        //主要判断i所指元素要不要保留，即是否已经保留了两次nums[i]
        int j = 0;
        //标志nums[i]是否已经保留了两次
        boolean flag = false;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[j] && !flag) {
                //与上一次保存的元素相同，但flag为假，说明该元素只出现一次
                //现保存num[i]，且将flag设为true
                flag = true;
                nums[j + 1] = nums[i];
                j++;
            } else if (nums[i] == nums[j]) {
                //但flag为真，说明当前元素已经保存了两次
                //舍弃nums[i]
            } else if (nums[i] != nums[j]) {
                //出现一个新元素
                flag = false;
                nums[j + 1] = nums[i];
                j++;
            }
        }
        return j + 1;
    }
}
