//26. 删除排序数组中的重复项
//
//给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
//
//不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成
//
//你不需要考虑数组中超出新长度后面的元素。
//
//数组是引用传递，函数中改变数组，原传入函数的数组也会改变，所以本题其实两个返回值，一个是新数组，一个是新数组长度


public class RemoveDuplicatesFromSortedArray {
    //因为数组排好序了，所以重复元素一般在相邻位置
    public int solution(int[] nums) {
        //一个指针j指向上一次保留的元素，一个指针i指向我们待处理的元素(移动速度i快于j)
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == nums[j]) {
                continue;
            } else {
                nums[j + 1] = nums[i];
                j++;
            }
        }
        return j + 1;
    }
}
