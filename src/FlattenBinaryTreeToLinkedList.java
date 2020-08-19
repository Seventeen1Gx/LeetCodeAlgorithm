package src;

import src.util.TreeNode;

/**
 * 114. 二叉树展开为链表
 *
 * 给定一个二叉树，原地将它展开为链表。
 *
 * @author Seventeen1Gx
 * @version
 */
public class FlattenBinaryTreeToLinkedList {
    /**
     * 递归进行，先处理左右子树，然后再结合根结点进行连接
     */
    public void solution(TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            return;
        }

        solution(root.left);
        solution(root.right);

        // 进行连接
        if (root.left != null) {
            // 取处理后的左子树的最后结点
            TreeNode tail = root.left;
            while (tail.right != null) {
                tail = tail.right;
            }
            tail.right = root.right;
            root.right = root.left;
            root.left = null;
        }
    }
}
