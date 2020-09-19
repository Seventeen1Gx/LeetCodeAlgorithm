// 124. 二叉树中的最大路径和
//
// 给定一个非空二叉树，返回其最大路径和。
//
// 本题中，路径被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。该路径至少包含一个节点，且不一定经过根节点
//
// 注意：存在值为负的树结点


package src.tree;

import src.util.TreeNode;

public class BinaryTreeMaximumPathSum {
    public int maxPathSum(TreeNode root) {
        solution(root);
        return maxAns;
    }

    int maxAns = Integer.MIN_VALUE;

    private int solution(TreeNode root) {
        // 返回以 root 为起点往下走的最大路径和
        // 递归过程中，对于每个结点 node
        // 顺便计算：如果最大路径经过它，则是 solution(node.left) + solution(node.right) + node.val

        if (root == null) {
            return 0;
        }
        int left = Math.max(solution(root.left), 0);
        int right = Math.max(solution(root.right), 0);

        // 下面两句属于顺便计算
        int cur = root.val + left + right;
        maxAns = Math.max(maxAns, cur);

        return root.val + Math.max(left, right);
    }
}
