package src;

/**
 * 110. 平衡二叉树
 *
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 *
 * 本题中，一棵高度平衡二叉树定义为：
 * 一个二叉树 每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BalancedBinaryTree {
    private static final int MAX_Height_Different = 1;

    public boolean solution(TreeNode root) {
        // 当前结点左右子树高度相差不超过 1
        // 而且
        // 当前结点的两个子树也应该是平衡二叉树

        if (root == null) {
            return true;
        }

        MaximumDepthOfBinaryTree m = new MaximumDepthOfBinaryTree();
        int leftDepth = m.solution1(root.left);
        int rightDepth = m.solution1(root.right);

        if (Math.abs(leftDepth - rightDepth) > MAX_Height_Different) {
            return false;
        }

        return solution(root.left) && solution(root.right);
    }
}
