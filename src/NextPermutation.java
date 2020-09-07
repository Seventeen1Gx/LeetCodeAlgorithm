// 31. 下一个排列
//
// 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
//
// 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
//
// 必须原地修改，只允许使用额外常数空间。
//
// 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
// 1,2,3 → 1,3,2
// 3,2,1 → 1,2,3
// 1,1,5 → 1,5,1
//
//
// 启发
// 求 n 个数字 a1,a2,...,an 的全排列
// 若 n=1，则其全排列就是 a1
// 若 n>1，则重复 n 个过程
// 第 i 个过程取 ai (1<=i<=n) 放在第一个位置，再递归(DFS)求剩下元素的全排列，放在剩下 n-1 个位置上
// 每次操作前发现放满了，则保存这个排列
// (详情见 46.全排列)
// 就上面的过程来说，若每次都按元素大小从小到大选择，选来做头节点，则最后得到的全排列是有序的
//
// 思路
// 如果我们将一组数字的全排列按照大小顺序进行排序
// 假设我们知道了第 n 个排列是 [A0, A1, A2, A3, ..]，那么第 n+1 个排列就是比 [A0, A1, A2, A3, ...] 大，且最小的那个。
// 找到 n+1 个排列的步骤如下
// 1）从后往前两两比较，找到第一个满足 a[i]<a[i+1] 的两个元素(因为在这之前的元素一定是从小到大排好了)
// 2）从 a[i+1] 开始往后找，找到一个大于 a[i] 中最小的一个元素，这个元素的下标记为 j，交换 a[i] 和 a[j] (对于下一个排列这里放置的头节点要变大一点)
// 3）将 [i+1, a.length-1] 的元素全部逆序(放置好头节点后，接上剩下元素的最小排列，也就是正序)


package src;

public class NextPermutation {
    public void solution(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return;
        }

        // 从后往前，找到第一个 nums[i-1]<nums[i]
        int i = nums.length - 1;
        while (i - 1 >= 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        // i=0 是逆序的情况
        if (i > 0) {
            // nums[i-1]之后的元素(逆序的)中找大于它且是大于它的元素中最小的和 num[i-1] 互换
            // 因为是逆序的，所以从后往前找到第一个大于 num[i-1] 的元素即可
            int j = nums.length - 1;
            while (j >= i && nums[j] <= nums[i - 1]) {
                j--;
            }

            swap(nums, i-1, j);
        }
        // 逆置 num[i-1] 之后的元素
        reverse(nums, i, nums.length - 1);
    }

    public void reverse(int[] nums, int left, int right) {
        for (int i = left, j = right; i < j; i++, j--) {
            swap(nums, i, j);
        }
    }

    private void swap(int[] nums, int i, int j) {
        nums[i] = nums[i] + nums[j];
        nums[j] = nums[i] - nums[j];
        nums[i] = nums[i] - nums[j];
    }

    public static void main(String[] args) {
        NextPermutation n = new NextPermutation();

        n.solution(new int[]{2});
        n.solution(new int[]{1, 2});
        n.solution(new int[]{1, 1});
        n.solution(new int[]{1, 2, 3});
        n.solution(new int[]{3, 2, 1});
        n.solution(new int[]{1, 1, 5});
    }
}
