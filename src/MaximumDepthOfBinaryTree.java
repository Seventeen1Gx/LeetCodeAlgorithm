package src;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 104. 二叉树的最大深度
 *
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class MaximumDepthOfBinaryTree {
    public int solution1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = solution1(root.left);
        int rightDepth = solution1(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 使用层次遍历
     */
    public int solution2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();

        queue.offer(root);

        int level = 0;
        while (!queue.isEmpty()) {
            //子循环保证每次直接处理一整层的元素
            int levelLength = queue.size();
            for (int i = 0; i < levelLength; i++) {
                TreeNode treeNode = queue.poll();
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
