// 404. 左叶子之和
//
// 计算给定二叉树的所有左叶子之和。


package src.tree;

import src.util.TreeNode;

public class SumOfLeftLeaves {
    public int sumOfLeftLeaves(TreeNode root) {
        // 根节点默认是右节点
        preOrder(root, false);
        return sum;
    }

    private int sum = 0;

    private void preOrder(TreeNode root, boolean isLeft) {
        // isLeft 表示是从父结点往左来到当前节点还是往右

        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null && isLeft) {
            sum += root.val;
            return;
        }
        preOrder(root.left, true);
        preOrder(root.right, false);

    }
}
