package src.tree;

import src.util.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 94. 二叉树的中序遍历
 *
 * 给定一个二叉树，返回它的中序遍历。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BinaryTreeInorderTraversal {
    private List<Integer> result = new ArrayList<>();

    public List<Integer> solution1(TreeNode root) {
        inorder(root);
        return result;
    }

    private void inorder(TreeNode root) {
        if (root == null) {
            return;
        }

        // 遍历当前根结点的左子树
        inorder(root.left);

        // 访问当前根结点
        result.add(root.val);

        // 遍历当前根结点的右子树
        inorder(root.right);
    }

    public List<Integer> solution2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        // curr 是在树上进行遍历的指针
        TreeNode curr = root;
        while (curr != null || !stack.empty()) {
            while (curr != null) {
                // 因为要先处理当前结点的左子树，故先把当前结点入栈，即先记住它，而不处理它
                stack.push(curr);
                curr = curr.left;
            }

            // 当前结点为空，退回上一个结点
            curr = stack.pop();
            // 访问
            result.add(curr.val);
            // 去处理当前结点的右结点
            curr = curr.right;
        }
        return result;
    }
}
