//4. 寻找两个有序数组的中位数
//
//给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
//
//请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
//
//你可以假设 nums1 和 nums2 不会同时为空。
//
//思路（下面两种思路都要确保数组1为小数组）
//一种方法是借用寻找两排序数组第k个数的思路
//另一种方法根据中位数定义，中位数将一组数分成左右两部分，且有：
//① len(left)=len(right) ② max(left) <= min(right)
//③ mid = (max(left) + min(right))/2
//用在本题，假设，找到中位数将
//数组1为a[0] a[1] a[2] ... a[i-1] | a[i] ... a[m-1]
//数组2为b[0] b[1] b[2] ... b[j-1] | b[j] ... b[n-1]
//i的范围为[0,m]，分隔符在i位置之前，同理j
//当m+n为偶数时，中位数在原数组中不存在，条件①即 i+j = m-i + n-j → j = (n+m)/2 - i
//当m+n为奇数时，中位数在原数组中存在，将其算在左边部分，条件①即 i+j = m-i + n-j + 1 → j = (n+m+1)/2 - i
//对于整除和n+m为偶数的情况，(n+m)/2与(n+m+1)/2在本题中是一样的，故无论奇偶都用后者式子求j值
//又该式得出的j应该有 j>=0 那么 (n+m)/2 >= i 或 (n+m+1)/2 >= i 又i最大取值为m 所以得出约束n>=m
//条件②即 a[i-1]<=b[j]  b[j-1]<=a[i]
//最后若m+n为偶数时，mid = (max(a[i-1],b[j-1]) + min(a[i],b[j]))/2
//若m+n为奇数时，mid=max(a[i-1],b[j-1])
//但要注意边界条件，即i=0，i=m，j=0，j=n的情况，i=0时j不会同时为0，i=m时j不会同时为n，即条件②至少要有一项成立
//又m<=n且i<m时，可推出j=(n+m+1)/2-i>(n+m+1)/2-m>=(2m+1)/2-m>0，即i<m时，b[j-1]<=a[i]不会出现越界
//又m<=n且i>0时，可推出j=(n+m+1)/2-i<(n+m+1)/2<=(2n+1)/2<n，即i>0时，a[i-1]<=b[j]不会出现越界
//
//
//
//我以前有做到也是求两升序数组中位数的题，但那题把中位数定义为数组中第L/2个元素。且A和B等长。
//用的是递归。中间点=(l+r)/2。a=A[中间点]，b=B[中间点]。
//若a=b，则返回这个a。
//若a>b，结果在A的前半部分，在B的后半部分，其余部分舍弃。注意A和B舍弃的长度要相同。
//若a<b，结果在A的后半部分，在B的前半部分，其余部分舍弃。注意A和B舍弃的长度要相同。
//中间点是否舍弃？根据舍弃长度相同来确定
//对于a>b，舍弃A的后半部分，B的前半部分，但如果奇数长度的话，都要保留中间数，如果偶数长度，B连中间数一起舍弃，A中间点保留(保证舍弃长度相同)
//对于a<b，同理
//首先偶数不舍弃的话，最后数组只含2元素的数组将永远
//舍弃操作是将最终A和B合并得到的升序序列，首尾舍弃相同长度的元素，不影响中位数的位置。
//最终A和B只剩下一个元素，则返回较小者。


package src;

public class MedianOfTwoSortedArrays {
    public double solution1(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        if (len % 2 == 0) {
            //总长度为偶数
            return (findKthVal(nums1, 0, nums2, 0, len / 2)
                    + findKthVal(nums1, 0, nums2, 0, len / 2 + 1)) / 2.0;
        } else {
            return findKthVal(nums1, 0, nums2, 0, len / 2 + 1);
        }
    }

    //寻找两排序数组的第k个值，数组范围为从i到结尾与从j到结尾
    public int findKthVal(int[] num1, int i, int[] num2, int j, int k) {
        //保证数组1长度小于或等于数组2长度
        if (num1.length - i > num2.length - j) {
            return findKthVal(num2, j, num1, i, k);
        }

        //判断小数组是否为空，若是，则返回大数组的第k个数
        if (num1.length - i == 0) {
            return num2[j + k - 1];
        }

        //当k等于1时，表示寻找第一个元素，比较两数组的第一个元素即可
        if (k == 1) {
            return Math.min(num1[i], num2[j]);
        }

        //数组1的分界线(当分界线超限时，采用末尾元素来比较)
        int pa = Math.min(i + k / 2, num1.length);
        //数组2的分解线(根据pa来确定，保证[i,pa]与[j,pb]的元素总数为k)
        int pb = j + k - (pa - i);

        if (num1[pa - 1] < num2[pb - 1]) {
            //num1[pa-1]小于合并之后的第k小值（反证法，令该数为第k+1个数，则num2[pb-1]为第k+2个数，但他们之前没这么多元素）
            //故排除数组1分界线之前的部分
            return findKthVal(num1, pa, num2, j, k - pa + i);
        } else if (num1[pa - 1] > num2[pb - 1]) {
            //同理，排除数组2分界线之前的部分
            return findKthVal(num1, i, num2, pb, k - pb + j);
        } else {
            //相等则满足条件
            return num1[pa - 1];
        }
    }


    //关键是寻找两数组中的分界线i、j，而j可由i得到，故是去寻找i
    public double solution2(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        //保证m小于或等于n，即数组1为小数组
        if (m > n) {
            return solution2(nums2, nums1);
        }

        //初始时i的搜索范围
        int iMin = 0, iMax = m, i, j;
        while (iMin <= iMax) {
            //二分查找
            i = (iMin + iMax) / 2;
            //根据左右部分长度相等得出(且可以推导出下面式子得出的j有j>0与j<n始终成立，故不考虑j的边界情况)
            j = (m + n + 1) / 2 - i;

            //前面的条件是边界时，保证后面的条件不越界
            if (i < m && nums2[j - 1] > nums1[i]) {
                //说明i太小了
                iMin = i + 1;
            } else if (i > 0 && nums1[i - 1] > nums2[j]) {
                //说明i太大了
                iMax = i - 1;
            } else {
                //要么数组越界，要么满足不等式条件

                int maxLeft = 0;
                if (i == 0) {
                    //数组1全部在右边
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    //数组2全部在右边
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }

                //总长度为奇数
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }

                //总长度为偶数
                int minRight = 0;
                if (i == m) {
                    //数组1全在左边
                    minRight = nums2[j];
                } else if (j == n) {
                    //数组2全在左边
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3};
        int[] nums2 = new int[]{2};

        MedianOfTwoSortedArrays m = new MedianOfTwoSortedArrays();

        System.out.println(m.solution2(nums1, nums2));
    }
}
