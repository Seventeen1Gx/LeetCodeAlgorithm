// 538. 把二叉搜索树转换为累加树
//
// 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。


package src.tree;

import src.util.TreeNode;

public class ConvertBstToGreaterTree {
    public TreeNode convertBST(TreeNode root) {
        inOrder(root);
        return root;
    }

    int lastVal = 0;

    public void inOrder(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.right);
        root.val += lastVal;
        lastVal = root.val;
        inOrder(root.left);
    }
}