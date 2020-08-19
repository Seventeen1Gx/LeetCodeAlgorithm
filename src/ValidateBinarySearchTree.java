package src;

import src.twoSum.TreeNode;

import java.util.Stack;

/**
 * 98. 验证二叉搜索树
 *
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 * - 节点的左子树只包含小于当前节点的数。
 * - 节点的右子树只包含大于当前节点的数。
 * - 所有左子树和右子树自身必须也是二叉搜索树
 *
 * 注意：只检查每个结点以及其左右两个子节点的值是否满足要求是不行的
 * 比如 [5,1,4,null,null,3,6]
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ValidateBinarySearchTree {
    /**
     * 先序遍历每个结点，检查每个结点是否满足二叉搜索树的定义
     */
    public boolean solution1(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return true;
        }

        if (root.left != null && root.val <= getMax(root.left)) {
            return false;
        }

        if (root.right != null && root.val >= getMin(root.right)) {
            return false;
        }

        return solution1(root.left) && solution1(root.right);
    }

    private int getMax(TreeNode root) {
        TreeNode cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.val;
    }

    private int getMin(TreeNode root) {
        TreeNode cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.val;
    }

    /**
     * 中序遍历二叉搜索树，得到的是一个有序列表
     */
    public boolean solution2(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();

        boolean isFirst = true;
        int lastNum = Integer.MIN_VALUE;
        TreeNode cur = root;
        while (cur != null || !stack.empty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            if (isFirst) {
                // 访问的第一个数
                lastNum = cur.val;
                isFirst = false;
            } else {
                if (lastNum >= cur.val) {
                    return false;
                } else {
                    lastNum = cur.val;
                }
            }
            cur = cur.right;
        }
        return true;
    }

    public boolean solution3(TreeNode root) {
        return recursion(root, null, null);
    }

    /**
     * 上面的思路是父结点和子结点比，而本题是子结点和父结点比
     */
    private boolean recursion(TreeNode root, Integer up, Integer low) {
        if (root == null) {
            return true;
        }

        // 判断当前根结点是否满足要求
        if (up != null && root.val >= up) {
            return false;
        }
        if (low != null && root.val <= low) {
            return false;
        }

        // 判断当前结点的左右子树是否满足要求
        return recursion(root.left, root.val, low) && recursion(root.right, up, root.val);
    }
}