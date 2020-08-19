package src;

import src.twoSum.TreeNode;

/**
 * 105. 从前序与中序遍历序列构造二叉树
 *
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public TreeNode solution(int[] preorder, int[] inorder) {
        return recursion(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    /**
     * 使用 preorder[l1:r1] 与 inorder[l2:r2] 构造一棵二叉树
     */
    private TreeNode recursion(int[] preorder, int l1, int r1, int[] inorder, int l2, int r2) {
        if (l1 > r1) {
            return null;
        }

        // 先序序列：根结点 + 左子树先序序列 + 右子树先序序列
        // 中序序列：左子树中序序列 + 根结点 + 右子树中序序列
        // 对应部分长度相等，即始终 r1-l1 = r2-l2
        TreeNode root = new TreeNode(preorder[l1]);
        int index = search(inorder, l2, r2, preorder[l1]);
        root.left = recursion(preorder, l1 + 1, index - l2 + l1, inorder, l2, index - 1);
        root.right = recursion(preorder, index - l2 + l1 + 1, r2 - l2 + l1, inorder, index + 1, r2);
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

    public static void main(String[] args) {
        ConstructBinaryTreeFromPreorderAndInorderTraversal c = new ConstructBinaryTreeFromPreorderAndInorderTraversal();
        c.solution(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
    }
}
