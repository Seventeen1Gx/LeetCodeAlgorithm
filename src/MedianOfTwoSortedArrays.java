// 4. 寻找两个有序数组的中位数
//
// 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
//
// 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
//
// 你可以假设 nums1 和 nums2 不会同时为空。
//
// 思路（下面两种思路都要确保数组1为小数组）：
// 方法一：借用寻找两排序数组第k个数的思路，中位数就是k =len/2+1(奇数情况)或者k=len/2与k=len/2+1两个数字的平均值(偶数情况)
// 方法二：根据中位数定义，中位数将一组数分成左右两部分
// 这个分割动作显示在两数组上就如下所示，
// 数组1为 a[0] a[1] a[2] ... a[i-1] | a[i] ... a[m-1]
// 数组2为 b[0] b[1] b[2] ... b[j-1] | b[j] ... b[n-1]
// 分割处在a[i]和b[j]之前，i取值范围为[0,m]，j取值范围为[0,n]，为了方便表示，我们令 n1 = a[j-1]，n2 = a[i]，n3 = b[j-1]，n4 = b[j]
//
// - j可有i得出：
// 当m+n为偶数时，中位数在原数组中不存在，则 mid = (max(n1, n3) + min(n2, n4))/2
// 由中位数两侧元素个数相同，即 i+j = m-i + n-j → j = (n+m)/2 - i
// 当m+n为奇数时，中位数在原数组中存在，将其算在分隔符左边，则 mid = max(n1, n3)
// 由中位数两侧元素相同，即 i+j-1 = m-i + n-j → j = (n+m+1)/2 - i
// 由于整除，所以无论总长度奇偶，都有 j = (n+m+1)/2 - i ，即求出i就得到了j
//
//
// - 二分查找满足如下要求的i:
// 由中位数分割的左部分最大值小于等于右部分的最小值，
// 即 当 0<i<m 且 0<j<n 时，应同时满足 a[i-1]<=b[j] 、 b[j-1]<=a[i]
// 当 i=0，不存在 a[i-1]，故只要满足 b[j-1]<=a[i] 即可
// 当 i=m，不存在 a[i]，故只要满足 a[i-1]<=b[j] 即可
// 当 j=0，不存在 b[j-1]，故只要满足 a[i-1]<=b[j] 即可
// 当 j=n，不存在 b[j]，故只要满足 b[j-1]<=a[i] 即可
//
// 小技巧：边界情况导致 n1、n2、n3、n4 不存在时，设置其是一个最值即可，以便统一下面的 max 操作
//
// - 找到i、j后，中位数为：
// 当m+n为偶数时，则 mid = (max(n1, n3) + min(n2, n4))/2
// 当m+n为奇数时，则 mid = max(n1, n3)
//
// - 相似题目：
// L个数的中位数定义为排在L/2位置的数。给定"等长"的两个升序数组，求他们归并后得到的新数组的中位数。
// - 解决方法
// 用的是递归。
// mid=(left+right)/2。a=A[mid]，b=B[mid]。
// a和b分别为A[left:right]、B[left:right]的中位数。
// 若a=b，则返回这个a。
// 若a>b，结果在A的前半部分，在B的后半部分，其余部分舍弃。注意A和B舍弃的长度要相同。
// 若a<b，结果在A的后半部分，在B的前半部分，其余部分舍弃。注意A和B舍弃的长度要相同。
// 中间点是否舍弃？根据舍弃长度相同来确定
// 对于a>b，舍弃A的后半部分，B的前半部分，但如果奇数长度的话，都要保留中间数，如果偶数长度，B连中间数一起舍弃，A中间点保留(保证舍弃长度相同)
// 对于a<b，同理
// 首先偶数不舍弃的话，最后数组只含2元素的数组将永远
// 舍弃操作是将最终A和B合并得到的升序序列，首尾舍弃相同长度的元素，不影响中位数的位置。
// 最终A和B只剩下一个元素，则返回较小者。


package src;

public class MedianOfTwoSortedArrays {
    // 简单方法一：合并，排序
    // 简单方法二：归并排序，只需排前面一半的元素即可


    public double solution1(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 0) {
            // 总长度为偶数
            return (findKthVal(nums1, 0, nums2, 0, len / 2)
                    + findKthVal(nums1, 0, nums2, 0, len / 2 + 1)) / 2.0;
        } else {
            return findKthVal(nums1, 0, nums2, 0, len / 2 + 1);
        }
    }

    // 寻找两排序数组的第k个值，数组范围为从i到结尾与从j到结尾
    public int findKthVal(int[] num1, int i, int[] num2, int j, int k) {
        // 保证数组1长度小于或等于数组2长度
        if (num1.length - i > num2.length - j) {
            return findKthVal(num2, j, num1, i, k);
        }

        // 判断小数组是否为空，若是，则返回大数组的第k个数
        if (num1.length - i == 0) {
            return num2[j + k - 1];
        }

        // 当k等于1时，表示寻找第一个元素，比较两数组的第一个元素即可
        if (k == 1) {
            return Math.min(num1[i], num2[j]);
        }

        // 数组1的分界线(当分界线超限时，采用末尾元素来比较)
        // [i,pa) 一般来说有 k/2 个元素
        int pa = Math.min(i + k / 2, num1.length);
        // 数组2的分解线(根据pa来确定，保证[i,pa)与[j,pb)的元素总数为k)
        int pb = j + k - (pa - i);

        if (num1[pa - 1] < num2[pb - 1]) {
            // num1[pa-1]小于合并之后的第k小值（反证法，令该数为第k+1个数，则num2[pb-1]为第k+2个数，但他们之前没这么多元素）
            // 故排除数组1分界线之前的部分
            return findKthVal(num1, pa, num2, j, k - pa + i);
        } else if (num1[pa - 1] > num2[pb - 1]) {
            // 同理，排除数组2分界线之前的部分
            return findKthVal(num1, i, num2, pb, k - pb + j);
        } else {
            // 相等则满足条件
            return num1[pa - 1];
        }
    }


    // 关键是寻找两数组中的分界线i、j，而j可由i得到，故只要寻找分界线i
    public double solution2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        // 保证m小于或等于n，即数组1为小数组
        // 为了搜索范围更小
        if (m > n) {
            return solution2(nums2, nums1);
        }

        // 初始时i的搜索范围
        int iMin = 0, iMax = m, i, j;
        while (iMin <= iMax) {
            // 二分查找
            i = (iMin + iMax) / 2;
            // 根据左右部分长度相等得出(且可以推导出下面式子得出的j有j>0与j<n始终成立，故不考虑j的边界情况)
            j = (m + n + 1) / 2 - i;

            // 前面的条件是边界时，保证后面的条件不越界
            if (i < m && nums2[j - 1] > nums1[i]) {
                // 说明i太小了
                iMin = i + 1;
            } else if (i > 0 && nums1[i - 1] > nums2[j]) {
                // 说明i太大了
                iMax = i - 1;
            } else {
                // 要么是边界情况，要么满足不等式条件

                int maxLeft;
                if (i == 0) {
                    // 数组1全部在右边
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    // 数组2全部在右边
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }

                // 总长度为奇数
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                // 总长度为偶数
                int minRight = 0;
                if (i == m) {
                    // 数组1全在左边
                    minRight = nums2[j];
                } else if (j == n) {
                    // 数组2全在左边
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    // 减治法
    public double solution3(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        // 保证 m<=n
        if (m > n)
            return solution3(nums2, nums1);

        // 合并后的数组左边应该有的总元素数
        // m+n 偶数时，取一半
        // m+n 奇数时，我们这里认为中位数在左边，所以是上取整
        // 上面两种情况，统一为 totalLeft = (m + n + 1) / 2;
        // nums1 中属于左边元素的有 [0,i)，即 i 个
        // 则易得 j = totalLeft - i
        int totalLeft = (m + n + 1) / 2;

        // i、j 两个分解线此消彼长

        // 正常情况下，分解线处应该有
        // nums1[i-1]<=nums2[j] && nums2[j-1]<=nums1[i]

        // 为了避免边界值的处理
        // nums1[-1] = nums2[-1] = -∞
        // nums1[n] = nums2[m] = +∞


        // 搜寻范围 [0, m]
        int iMin = 0, iMax = m;
        int leftMax_1, leftMax_2, rightMin_1, rightMin_2;
        while (iMin < iMax) {
            int i = iMin + (iMax - iMin + 1) / 2;
            int j = totalLeft - i;

            // 寻找满足 nums1[i-1] <= nums2[j] 且 nums2[j-1] <= nums1[i]
            // 等价于寻找满足 nums1[i-1] <= nums2[j] 的最大 i
            // 证明：
            // 1. i 在 [0,m] 递增时，nums1[i-1] 增大，nums2[j] 减小，此消彼长，故存在这样的最大 i
            // 2. 因为最大 i，所以 i+1 时不满足，可以推出 nums1[i] > nums2[j-1]


            // 解决边界情况
            leftMax_1 = i > 0 ? nums1[i - 1] : Integer.MIN_VALUE;
            rightMin_2 = j < n ? nums2[j] : Integer.MAX_VALUE;

            // leftMax_1>rightMin_2，即 leftMax1 应该减小 [i,iMax] 丢弃
            // leftMax_1<=rightMin_2，即 [iMin, i-1] 应该丢弃
            if (leftMax_1 > rightMin_2)
                iMax = i - 1;
            else
                iMin = i;
        }

        leftMax_1 = iMin > 0 ? nums1[iMin - 1] : Integer.MIN_VALUE;
        leftMax_2 = totalLeft - iMin > 0 ? nums2[totalLeft - iMin - 1] : Integer.MIN_VALUE;

        int leftMax = Math.max(leftMax_1, leftMax_2);

        if ((n + m) % 2 == 1) {
            return leftMax;
        } else {
            rightMin_1 = iMin < m ? nums1[iMin] : Integer.MAX_VALUE;
            rightMin_2 = totalLeft - iMin < n ? nums2[totalLeft - iMin] : Integer.MAX_VALUE;
            int rightMin = Math.min(rightMin_1, rightMin_2);
            return (leftMax + rightMin) / 2.0;
        }
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{3};
        int[] nums2 = new int[]{-2, -1};

        MedianOfTwoSortedArrays m = new MedianOfTwoSortedArrays();

        System.out.println(m.solution3(nums1, nums2));
    }
}
