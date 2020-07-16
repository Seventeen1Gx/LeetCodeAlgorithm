//35. 搜索插入位置
//
//给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
//
//你可以假设数组中无重复元素
//
//在本问题的官网题解中，有人提出了一种"神奇的"二分查找模板，值得学习


package src.binarySearch;

public class SearchInsertPosition {
    //顺序查找
    public int solution1(int[] nums, int target) {
        if (nums == null)
            return -1;

        int len = nums.length;

        //从前往后，发现第一个大于等于target的位置，则插入其之前(占据这个元素的位置)
        for (int i = 0; i < len; i++) {
            if (target <= nums[i])
                //不管是插入还是找到都是返回i
                return i;
        }
        return len;
    }

    //二分查找
    public int solution2(int[] nums, int target) {
        if (nums == null)
            return -1;

        int len = nums.length;

        //二分查找找从前往后第一个大于等于target的位置
        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] >= target)
                //就算找到也要往前缩
                high = mid - 1;
            else
                low = mid + 1;
        }
        //循环退出时，high+1=low

        return low;
    }

    // 减治法
    public int solution3(int[] nums, int target) {
        int n = nums.length;

        // 因为答案可能是 nums.length，而下面的搜索不包括这个，故特殊判断一下
        if (target > nums[n - 1]) {
            return n;
        }

        int l = 0, h = n - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;
            // 分成 [l:mid] 和 [mid+1:h] 两个区间
            if (nums[mid] >= target)
                // 目标有两种情况，target 存在与不存在
                // 这时，目标在左边，不可能在右边
                h = mid;
            else
                // 即 nums[mid] < target
                // 目标肯定在右边
                l = mid + 1;
        }
        // 不管找到没，都是返回 l
        return l;
    }


    public static void main(String[] args) {
        SearchInsertPosition s = new SearchInsertPosition();
        //插入在最前
        s.solution2(new int[]{1}, 0);
        //插入在最后
        s.solution2(new int[]{1}, 2);
        //含该元素
        s.solution2(new int[]{1, 3, 4, 5}, 4);
        //不含该元素
        s.solution2(new int[]{1, 3, 5, 5}, 4);
    }
}
