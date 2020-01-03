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
    private static final int MAX_HEIGHT_DIFFERENT = 1;

    public boolean solution1(TreeNode root) {
        // 当前结点左右子树高度相差不超过 1
        // 而且
        // 当前结点的两个子树也应该是平衡二叉树

        if (root == null) {
            return true;
        }

        MaximumDepthOfBinaryTree m = new MaximumDepthOfBinaryTree();
        int leftDepth = m.solution1(root.left);
        int rightDepth = m.solution1(root.right);

        if (Math.abs(leftDepth - rightDepth) > MAX_HEIGHT_DIFFERENT) {
            return false;
        }

        return solution1(root.left) && solution1(root.right);
    }

    public boolean solution2(TreeNode root) {
        return depth(root) != -1;
    }

    /**
     * 在求最大深度的算法中，得到每个结点的左右子树高度后，判断其高度差是否超过 1，若超过，返回 -1
     */
    private int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = depth(root.left);
        if (leftDepth == -1) {
            // 说明左子树不是平衡树，提前阻断
            return -1;
        }
        int rightDepth = depth(root.right);
        if (rightDepth == -1) {
            return -1;
        }

        return Math.abs(leftDepth - rightDepth) > MAX_HEIGHT_DIFFERENT ?
                -1 : Math.max(leftDepth, rightDepth) + 1;
    }
}
