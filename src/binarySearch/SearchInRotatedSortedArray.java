//33. 搜索旋转排序数组
//
//假设按照升序排序的数组在预先未知的某个点上进行了旋转。
//
//( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
//
//搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
//
//你可以假设数组中不存在重复的元素。
//
//你的算法时间复杂度必须是 O(log n) 级别。
//
//
//本题不含重复元素，如果含重复元素会怎么样？
//
//
//收获: 复杂的if级联也许可以化简

package src.binarySearch;

public class SearchInRotatedSortedArray {
    //看到O(log n)，使用二分查找法
    //升序数组旋转变为升序+升序前后两部分
    //且后部分所有元素必小于(若存在重复元素是小于或等于)前部分所有元素
    //先找到旋转中心(最小值)，再决定在前半部分还是后半部分找目标值
    //特别的，数组本身也是一个它的旋转
    public int solution(int[] nums, int target) {
        //找旋转中心
        if (nums == null)
            return -1;
        int len = nums.length;
        if (len == 0)
            return -1;
        if (len == 1)
            return nums[0] == target ? 0 : -1;

        int rotationCenterIndex = findRotateCenterIndex(nums);

        //正序的情况
        if (rotationCenterIndex == 0)
            return binarySearch(nums, target, 0, len - 1);

        //判断target在前还是在后半部分
        if (target == nums[0])
            return 0;
        else if (target > nums[0])
            //前半部分
            return binarySearch(nums, target, 1, rotationCenterIndex - 1);
        else
            //后半部分
            return binarySearch(nums, target, rotationCenterIndex, len - 1);

    }

    //在含重复元素的旋转数组中找到最小元素且其第一次出现的位置(旋转中心，用j表示)
    //旋转前nums[0:len-1]:nums[0] <= nums[1] <= nums[2] <= ... <= nums[len-1]
    //旋转后nums[0:len-1]:nums[0] <= nums[1] <= ... <= nums[i] > num[j] <= ... <= nums[len-1]
    //旋转中心为j，当j=0时，不存在nums[i]>nums[j]，是一个正序排列:nums[0] <= nums[1] <= nums[2] <= ... <= nums[len-1]
    //且nums[len-1] >= nums[0]
    //当0<j<len时，存在nums[i]>nums[j]，旋转后的数组由两部分组成，两部分各为正序排列：nums[0:i]和[j:len-1](注意因为是第一次出现，故这里一定是大于，不是大于等于)
    //且num[len-1] <= num[0](头大于等于尾)
    public int findRotateCenterIndex(int[] nums) {
        //函数传入旋转后的数组

        //首尾元素
        int head = nums[0];
        int tail = nums[nums.length - 1];

        //排除j=0时的情况(但不包括所有元素相同的情况)
        if (head < tail)
            return 0;

        //采用二分查找，我们要找的最小元素第一次出现的位置rotateCenterIndex始终在[low,high]区间内
        //从下面的过程得知，所有元素相等，陷入顺序查找，也能得到正确结果
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            //假定nums[mid]为旋转点
            int mid = (low + high) / 2;
            if (mid - 1 >= 0 && nums[mid - 1] > nums[mid]) {
                //mid的确是旋转点的下标
                return mid;
            } else {
//                --------------------------------------------------
//                //当前mid不是旋转点，判断真正的旋转点在mid前还是mid后
//                if (nums[mid] > head)
//                    //nums[mid] > head >= tail
//                    //一定在mid之后
//                    low = mid + 1;
//                else if (nums[mid] < tail)
//                    //nums[mid] < tail <= head
//                    //一定在mid之前
//                    high = mid - 1;
//                else {
//                    //进入这里，根据if条件排除，有
//                    //nums[mid] <= head 且 nums[mid] >= tail
//                    //而旋转数组的性质: tail <= head
//                    //故tail <= nums[mid] <= head
//                    if (tail == head) {
//                        //tail = nums[mid] = head
//                        //没法判断真正的旋转点在前还是在后
//                        //比如"10111"和"11101"
//                        //采用顺序查找，找[low:high]中的最小值
//                        return minInOrder(nums, low, high);
//                    } else {
//                        //nums[mid]=tail或nums[mid]=head
//                        //因为tail和head在旋转前的数组中是相邻的两个值，他们中间不会有值
//                        if (tail == nums[mid])
//                            //真正的旋转点在mid之前
//                            high = mid - 1;
//                        else
//                            //head == nums[mid]
//                            //真正的旋转点在mid之后
//                            low = mid + 1;
//                    }
//                }
//              --------------------------------------------------
                //上面说的，我们换种写法
                //由于我们这里每次比较nums[mid-1]>nums[mid](官方题解是nums[mid]>nums[mid+1])
                //所有倒数第二步[low，high]都在后一部分，不能nums[mid]和nums[low]比较判断下一步指针的移动
                if (head == tail && head == nums[mid])
                    //三数相等时，顺序查找
                    return minInOrder(nums, low, high);
                if (nums[mid] >= head)
                    low = mid + 1;
                else if (nums[mid] <= tail)
                    high = mid - 1;
            }
        }
        return 0;
    }

    //剑指Offer上的方法，他和上面方法的区别就是他始终将左指针放在前一部分，右指针放在后一部分
    //而上面的方法[low,high]在倒数第二步时，low为旋转中心，right = low或者right = low + 1，这时两指针都在后一部分
    //搜索的目的是，low往右，high往左，从而找到nums[low] >= nums[high]条件下(保证两针在左右不同部分)，low与high相邻
    public int findRotateCenterIndex_swordToOffer(int[] nums) {
        if (nums[0] < nums[nums.length - 1])
            return 0;

        int low = 0;
        int high = nums.length - 1;
        int mid = low;
        while (nums[low] >= nums[high]) {
            if (low + 1 == high) {
                mid = high;
                break;
            }
            mid = (low + high) / 2;
            if (nums[mid] == nums[low] && nums[low] == nums[high])
                return minInOrder(nums, low, high);
            //注意下面的赋值也不一样
            //(因为是从两端向中间靠拢，直到两针贴近相邻一格结束循环，当mid=low或者mid=high，循环已经在上面的语句结束)
            //nums[mid]仍可能成为我们要找的目标如45123，所以这里的赋值方式是保留
            if (nums[mid] >= nums[low])
                low = mid;
            else if (nums[mid] <= nums[high])
                high = mid;
        }
        return mid;
    }

    //官方题解方法，假定nums中无重复元素
    public int findRotateCenterIndex_official(int[] nums, int left, int right) {
        if (nums[left] < nums[right])
            return 0;

        while (left <= right) {
            int pivot = (left + right) / 2;
            if (nums[pivot] > nums[pivot + 1])
                return pivot + 1;
            else {
                //pivot始终在前面那部分
                if (nums[pivot] < nums[left])
                    right = pivot - 1;
                else
                    //大于或等于，相等是因为pivot=left → left + 1 = right 或 left = right → 已经是倒数第二步的事
                    left = pivot + 1;
            }
        }
        return 0;
    }


    //[low:high]中的最小值(第一次出现的)的下标
    public int minInOrder(int[] nums, int low, int high) {
        if (low > high)
            return -1;

        int minIndex = low;

//        --------------------------------------------------
//        //方法一(遍历找第一个最小元素)
//        int min = nums[low];
//        for (int i = low + 1; i <= high; i++) {
//            if (min > nums[i]) {
//                min = nums[i];
//                minIndex = low;
//            }
//        }
//        --------------------------------------------------

        //方法二(遍历找第一个下降点的下标，没有返回首元素下标)
        for (int i = low + 1; i <= high; i++) {
            if (nums[i-1] > nums[i])
                return i;
        }

        return minIndex;
    }

    //假定target在nums[start:end]中最多出现一次，nums为正序
    public int binarySearch(int[] nums, int target, int start, int end) {
        int low = start, high = end;
        while (low <= high) {
            //搜索范围为[low:high]
            int mid = (low + high) / 2;
            if (nums[mid] > target)
                //排除mid(包括mid)之后的元素--我们要找的元素在mid之前
                high = mid - 1;
            else if (nums[mid] < target)
                //排除mid(包括mid)之前的元素
                low = mid + 1;
            else
                return mid;
        }
        return -1;
    }

    // 减治法，边界不断缩小，直到得到最后一个元素，然后再看是不是我们要的
    public int solution2(int[] nums, int target) {
        int l = 0, h = nums.length - 1;
        while (l < h) {
            int mid = l + (h - l) / 2;


            // nums[0]==nums[mid] 只能是 mid==0，[l,mid] 递增
            // nums[0]<nums[mid]，[l,mid] 递增
            // nums[0]>nums[mid],[mid+1,h] 递增

            if (nums[0] <= nums[mid]) {
                if (nums[l] <= target && nums[mid] >= target)
                    h = mid;
                else
                    l = mid + 1;
            } else if (nums[0] > nums[mid]) {
                if (nums[mid+1] <= target && target <= nums[h])
                    l = mid + 1;
                else
                    h = mid;
            }
        }
        return nums[l] == target ? l : -1;
    }

    public static void main(String[] args) {
        SearchInRotatedSortedArray s = new SearchInRotatedSortedArray();
        //s.solution(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
        //int a = s.findRotateCenterIndex(new int[]{2, 3, 4, 1});
        //a = s.findRotateCenterIndex(new int[]{2, 3, 4, 1, 1});
        //a = s.findRotateCenterIndex(new int[]{2, 3, 4, 1, 1, 1});
        //a = s.findRotateCenterIndex(new int[]{2, 3, 4, 1, 1, 1, 1});
        //a = s.findRotateCenterIndex(new int[]{1, 0, 1, 1, 1});
        //a = s.findRotateCenterIndex(new int[]{1, 1, 1, 0, 1});
        s.solution2(new int[]{4, 5, 6, 7, 0, 1, 2}, 0);
    }
}
