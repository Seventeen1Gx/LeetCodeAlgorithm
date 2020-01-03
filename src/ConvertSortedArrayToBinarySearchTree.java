package src;

/**
 * 108. 将有序数组转化为二叉搜索树
 *
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ConvertSortedArrayToBinarySearchTree {
    public TreeNode solution(int[] nums) {
        return convert(nums, 0, nums.length - 1);
    }

    /**
     * @param nums 有序数组
     * @param left 左边界
     * @param right 右边界
     * @return 将 nums[left:right] 转化成平衡二叉树，返回转化后平衡二叉树的根结点引用
     */
    private TreeNode convert(int[] nums, int left, int right) {
        // 因为要平衡，故选定中间的元素作为根结点，然后将该元素左右两侧的元素分别做为该根结点的左子树与右子树
        if (left > right) {
            return null;
        }

        if (left == right) {
            return new TreeNode(nums[left]);
        }

        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = convert(nums, left, mid - 1);
        root.right = convert(nums, mid + 1, right);
        return root;
    }
}
