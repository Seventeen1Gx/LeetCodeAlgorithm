//34. 在排序数组中查找元素的第一个和最后一个位置
//
//给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
//
//你的算法时间复杂度必须是 O(log n) 级别。
//
//如果数组中不存在目标值，返回 [-1, -1]。


package src.binarySearch;

public class FindFirstAndLastPositionOfElementInSortedArray {
    //含重复元素的二分查找法
    public int[] solution1(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};

        if (nums == null)
            return ans;

        int len = nums.length;
        if (len == 0)
            return ans;

        int low = 0;
        int high = len - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] > target)
                high = mid - 1;
            else if (nums[mid] < target)
                low = mid + 1;
            else {
                //往前、往后找到起始
                int start = mid;
                int end = mid;
                while (start > 0 && nums[start - 1] == nums[start]) start--;
                while (end < len - 1 && nums[end+1] == nums[end]) end++;
                ans = new int[]{start, end};
                //跳出循环
                break;
            }
        }
        return ans;
    }

    //二分查找，寻找满足a[start-1]<a[start](a[start]=target)和a[end]<a[end+1](a[end]=target)的位置
    public int[] solution2(int[] nums, int target) {
        int[] ans = new int[]{-1, -1};

        if (nums == null)
            return ans;

        int len = nums.length;
        if (len == 0)
            return ans;

        int start = -1, end;

        int low = 0;
        int high = len - 1;
        while (low <= high) {
            start = (low + high) / 2;
            if (nums[start] > target)
                high = start - 1;
            else if (nums[start] < target)
                low = start + 1;
            else {
                if (start == 0 || (start > 0 && nums[start - 1] < nums[start])) {
                    ans[0] = start;
                    break;
                }
                //真正的目标在当前位置之前
                high = start - 1;
            }
        }

        //找不到start也就找不到end
        if (ans[0] == -1)
            return ans;

        low = start;
        high = len - 1;
        while (low <= high) {
            end = (low + high) / 2;
            if (nums[end] > target)
                high = end - 1;
            else if (nums[end] < target)
                low = end + 1;
            else {
                if (end == len - 1 || (end < len - 1 && nums[end + 1] > nums[end])) {
                    ans[1] = end;
                    break;
                }
                low = end + 1;
            }
        }
        return ans;
    }

    public int[] solution_official(int[] nums, int target) {
        int[] targetRange = {-1, -1};

        int leftIdx = extremeInsertionIndex(nums, target, true);

        // assert that `leftIdx` is within the array bounds and that `target`
        // is actually in `nums`.
        if (leftIdx == nums.length || nums[leftIdx] != target) {
            return targetRange;
        }

        targetRange[0] = leftIdx;
        targetRange[1] = extremeInsertionIndex(nums, target, false)-1;

        return targetRange;
    }

    // returns leftmost (or rightmost) index at which `target` should be
    // inserted in sorted array `nums` via binary search.
    public int extremeInsertionIndex(int[] nums, int target, boolean left) {
        int lo = 0;
        int hi = nums.length;

        //搜索范围为[lo,hi)
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            //后面那个条件表示，二分查找中，就算找到了target，还是要往前缩小范围
            //最终结束循环时，lo=hi，且target==num[mid]
            //测试123456和4
            //测试135556和6
            //测试1和1
            //测试12345和8
            //对于寻找右结点left=false，后面那个条件始终为假
            //就算找到target之后，也是一直往后缩小
            if (nums[mid] > target || (left && target == nums[mid])) {
                hi = mid;
            }
            else {
                lo = mid+1;
            }
        }

        return lo;
    }

    // 寻找最左出现
    private int findFirstAppear(int[] nums, int target) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;

            // 区间分成 [l:mid] 和 [mid+1:h] 两个区间

            if (nums[mid] >= target)
                // target 的最左出现不可能在右区间
                h = mid;
            else
                l = mid + 1;
        }
        return nums[l] == target ? l : -1;
    }

    // 寻找最右出现
    private int findLastAppear(int[] nums, int target) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int mid = l + (h - l + 1) / 2;

            // nums[mid]==target，最右出现在 mid 和 mid 之右，[l:mid-1] 肯定不是
            // nums[mid]>target，最右出现在 mid 之前，[mid:h] 肯定不是
            // nums[mid]<target，最右出现在 mid 之后，[l:mid-1] 肯定不是
            if (nums[mid] > target)
                h = mid - 1;
            else
                l = mid;
        }
        return nums[l] == target ? l : -1;
    }

    public static void main(String[] args) {
        FindFirstAndLastPositionOfElementInSortedArray f = new FindFirstAndLastPositionOfElementInSortedArray();
        f.solution2(new int[]{5, 7, 7, 8, 8, 10}, 8);
        f.solution2(new int[]{1}, 1);
    }
}
