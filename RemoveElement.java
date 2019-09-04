//27. 移除元素
//
//给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
//
//不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
//
//元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。


public class RemoveElement {
    //双指针法
    public int solution1(int[] nums, int val) {
        //i指向待放置新元素的位置，j指向当前想处理的元素
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] == val) {
                continue;
            } else {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }

    //另一种方法，将不符合要求的元素跟数组末位的元素交换，然后就被排除在数组之外
    public int solution2(int[] nums, int val) {
        int i = 0;
        //>=j的就是数组的外面了，即被舍弃的部分
        int j = nums.length;

        while (i < j) {
            if (nums[i] == val) {
                nums[i] = nums[j - 1];
                j--;
            } else {
                i++;
            }
        }
        return j;
    }
}
