package src;

import src.twoSum.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 111. 二叉树的最小深度
 *
 * 给定一个二叉树，找出其最小深度。
 *
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class MinimumDepthOfBinaryTree {
    /**
     * 返回以 root 为根结点的树的最小深度
     */
    public int solution1(TreeNode root) {
        // 先排除几种基准情况

        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        if (root.left == null) {
            return solution1(root.right) + 1;
        }

        if (root.right == null) {
            return solution1(root.left) + 1;
        }

        return Math.min(solution1(root.left), solution1(root.right)) + 1;
    }

    /**
     * 深度优先搜索，每遍历到一个叶结点，判断到该结点的路径是不是比当前最短路径还要小，若是，则更新
     */
    public int solution2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        dfs(root, 1);
        return minDepth;
    }

    int minDepth = Integer.MAX_VALUE;

    /**
     * @param root 一棵树的根结点
     * @param level 该根结点所属的层级
     */
    private void dfs(TreeNode root, int level) {
        if (root.left == null && root.right == null && level < minDepth) {
            minDepth = level;
        }
        // 只遍历非空结点
        if (root.left != null) {
            dfs(root.left, level + 1);
        }
        if (root.right != null) {
            dfs(root.right, level + 1);
        }
    }

    public int solution3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();

        // 层级从 1 开始计数
        int level = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelLength = queue.size();
            for (int i = 0; i < levelLength; i++) {
                TreeNode treeNode = queue.poll();
                // 遍历到一个叶结点
                if (treeNode.left == null && treeNode.right == null) {
                    return level;
                }
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            level++;
        }
        return level;
    }
}
