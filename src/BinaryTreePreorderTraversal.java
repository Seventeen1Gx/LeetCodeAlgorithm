package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 144. 二叉树的前序遍历
 *
 * 给定一个二叉树，返回它的前序遍历。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class BinaryTreePreorderTraversal {
    public List<Integer> solution1(TreeNode root) {
        recursion(root);
        return result;
    }

    private List<Integer> result = new ArrayList<>();

    private void recursion(TreeNode root) {
        if (root != null) {
            result.add(root.val);
            recursion(root.left);
            recursion(root.right);
        }
    }

    public List<Integer> solution2(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode node = stack.pop();
            if (node != null) {
                ans.add(node.val);
                // 注意是先右后左，因为要先处理左边
                stack.push(node.right);
                stack.push(node.left);
            }
        }
        return ans;
    }
}
