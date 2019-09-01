//4. 寻找两个有序数组的中位数
//
//给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
//
//请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
//
//你可以假设 nums1 和 nums2 不会同时为空。
//
//思路
//一种方法是借用寻找两排序数组第k个数的思路
//另一种方法根据中位数定义，中位数将一组数分成左右两部分，且有：
//① len(left)=len(right) ② max(left) <= min(right)
//③ mid = (max(left) + min(right))/2
//用在本题，假设，找到中位数将
//数组1为a[0] a[1] a[2] ... a[i-1] | a[i] ... a[m-1]
//数组2为b[0] b[1] b[2] ... b[j-1] | b[j] ... b[n-1]
//条件①即 i+j = m-1-i+1 + n-1-j+1 → j = (n+m)/2 - i → 确定了i就确定了j → 搜索符合条件的i值
//又 j > 0 那么 (n+m)/2 > i 又i最大取值为m 所以得出约束n>m
//条件②即 a[i-1]<=b[j]  b[j-1]<=a[i]


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
            return min(num1[i], num2[j]);
        }

        //数组1的分界线(当分界线超限时，采用末尾元素来比较)
        int pa = min(i + k / 2, num1.length);
        //数组2的分解线(根据pa来确定)
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

    public int min(int i, int j) {
        return i <= j ? i : j;
    }

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{1, 2};

        MedianOfTwoSortedArrays m = new MedianOfTwoSortedArrays();

        System.out.println(m.solution1(nums1, nums2));
    }
}
