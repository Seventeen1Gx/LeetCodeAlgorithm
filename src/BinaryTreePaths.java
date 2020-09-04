// 257. 二叉树的所有路径
//
// 给定一个二叉树，返回所有从根节点到叶子节点的路径。
//
// 说明: 叶子节点是指没有子节点的节点。


package src;

import src.util.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BinaryTreePaths {
    public List<String> solution1(TreeNode root) {
        List<String> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        if (root.left == null && root.right == null) {
            ret.add(root.val + "");
            return ret;
        }
        List<String> left = solution1(root.left);
        List<String> right = solution1(root.right);
        for (String s : left) {
            ret.add(root.val + "->" + s);
        }
        for (String s : right) {
            ret.add(root.val + "->" + s);
        }
        return ret;

    }

    public List<String> solution2(TreeNode root) {
        if (root == null) {
            return ret;
        }
        dfs(root, "");
        return ret;
    }

    List<String> ret = new ArrayList<>();

    private void dfs(TreeNode cur, String path) {
        if (path.length() == 0) {
            path += cur.val;
        } else {
            path += "->";
            path += cur.val;
        }

        if (cur.left == null && cur.right == null) {
            ret.add(path);
            return;
        }

        if (cur.left != null) {
            dfs(cur.left, path);
            // 回溯，path 仍是进 dfs 之前的样子
            // 因为 String 是 final 的
        }
        if (cur.right != null) {
            dfs(cur.right, path);
        }
    }

    public List<String> solution3(TreeNode root) {
        // BFS 关键是构造出树结点

        List<String> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }

        class TreeNodeWrapper {
            String path;
            TreeNode treeNode;

            TreeNodeWrapper(String p, TreeNode t) {
                path = p;
                treeNode = t;
            }

        }

        Queue<TreeNodeWrapper> queue = new ArrayDeque<>();
        TreeNodeWrapper rootWrapper = new TreeNodeWrapper(root.val + "", root);
        queue.offer(rootWrapper);
        while (!queue.isEmpty()) {
            TreeNodeWrapper node = queue.poll();
            if (node.treeNode.left == null && node.treeNode.right == null) {
                ret.add(node.path);
            }
            if (node.treeNode.left != null) {
                queue.offer(new TreeNodeWrapper(node.path + "->" + node.treeNode.left.val, node.treeNode.left));
            }
            if (node.treeNode.right != null) {
                queue.offer(new TreeNodeWrapper(node.path + "->" + node.treeNode.right.val, node.treeNode.right));
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;
        TreeNode node = new TreeNode(5);
        left.right = node;
        new BinaryTreePaths().solution2(root);
    }
}