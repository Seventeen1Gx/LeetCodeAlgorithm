package src;

import src.util.TreeNode;

/**
 * 106. 从中序与后序遍历序列构造二叉树
 *
 * 根据一棵树的中序遍历与后序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public TreeNode solution(int[] inorder, int[] postorder) {
        return recursion(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
    }

    /**
     * 使用 inorder[l1:r1] 与 postorder[l2:r2] 构造一棵二叉树
     */
    private TreeNode recursion(int[] inorder, int l1, int r1, int[] postorder, int l2, int r2) {
        if (l1 > r1) {
            return null;
        }

        // 后序序列：左子树后序序列 + 右子树后序序列 + 根结点
        // 中序序列：左子树中序序列 + 根结点 + 右子树中序序列
        // 对应部分长度相等，即始终 r1-l1 = r2-l2
        TreeNode root = new TreeNode(postorder[r2]);
        int index = search(inorder, l1, r1, postorder[r2]);
        root.left = recursion(inorder, l1, index - 1, postorder, l2, index - 1 - l1 + l2);
        root.right = recursion(inorder, index + 1, r1, postorder, index - l1 + l2, r2 - 1);
        return root;
    }

    private int search(int[] nums, int left, int right, int target) {
        int ans = -1;
        for (int i = left; i <= right; i++) {
            if (nums[i] == target) {
                ans = i;
                break;
            }
        }
        return ans;
    }
}
