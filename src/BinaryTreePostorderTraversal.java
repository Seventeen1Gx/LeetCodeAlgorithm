// 145. 二叉树后序遍历
//
// 给定一个二叉树，返回它的 后序 遍历。


package src;

import src.twoSum.TreeNode;

import java.util.*;

public class BinaryTreePostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        postorder(root);
        return list;
    }

    List<Integer> list = new ArrayList<>();

    private void postorder(TreeNode root) {
        if (root == null) {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        list.add(root.val);
    }

    private List<Integer> solution2(TreeNode root) {
        LinkedList<Integer> ret = new LinkedList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();

        if (root == null) {
            return ret;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 头插！
            ret.addFirst(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return ret;
    }
}
