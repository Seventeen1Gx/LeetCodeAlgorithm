package src;

import src.util.TreeNode;

import java.util.Stack;

/**
 * 112. 路径总和
 *
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class PathSum {
    public boolean solution1(TreeNode root, int sum) {
        // 走过头的，算错
        if (root == null) {
            return false;
        }

        // 只有到达叶结点时，刚好够，才算正确
        if (root.left == null && root.right == null && root.val == sum) {
            return true;
        }

        return solution1(root.left, sum - root.val) || solution1(root.right, sum - root.val);
    }

    /**
     * 用栈模拟递归过程
     */
    public boolean solution2(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> integerStack = new Stack<>();
        nodeStack.push(root);
        integerStack.push(sum - root.val);
        while (!nodeStack.empty()) {
            TreeNode node = nodeStack.pop();
            int remain = integerStack.pop();

            if (node.left == null && node.right == null && remain == 0) {
                return true;
            }
            // 向左向右没有先后之分
            if (node.left != null) {
                nodeStack.push(node.left);
                integerStack.push(remain - node.left.val);
            }
            if (node.right != null) {
                nodeStack.push(node.right);
                integerStack.push(remain - node.right.val);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        PathSum p = new PathSum();
        p.solution2(root, 2);
    }
}
