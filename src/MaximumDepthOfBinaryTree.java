package src;

/**
 * 104. 二叉树的最大深度
 *
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class MaximumDepthOfBinaryTree {
    public int solution1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = solution1(root.left);
        int rightDepth = solution1(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 使用层次遍历
     */
    public int solution2(TreeNode root) {

    }
}
