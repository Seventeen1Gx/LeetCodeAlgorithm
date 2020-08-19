package src;

import src.twoSum.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 100. 相同的树
 *
 * 给定两个二叉树，编写一个函数来检验它们是否相同。
 *
 * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class SameTree {
    /**
     * 返回根结点为 p，q 的两树是否为相同树
     */
    public boolean solution1(TreeNode p, TreeNode q) {
        // 两节点都为空
        // 或者
        // 两节点不为空，结点值相同，且具有相同的子树
        return p == null && q == null ||
                p != null && q != null && p.val == q.val &&
                        solution1(p.left, q.left) && solution1(p.right, q.right);

    }

    /**
     * 分别层次遍历两棵树，判断每次遍历到的结点是否相同
     */
    public boolean solution2(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();

        // 根结点入队
        // 注意：空结点也能入队
        queue1.offer(p);
        queue2.offer(q);
        // 当队列不为空时进行循环
        while (!queue1.isEmpty()) {
            // 出队
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();

            // 层次遍历到的结点是否相同
            if (!check(node1, node2)) {
                return false;
            }

            // 当前遍历到的两个结点相同
            // 将它们的子结点分别入栈
            if (node1 != null) {
                queue1.offer(node1.left);
                queue1.offer(node1.right);
                queue2.offer(node2.left);
                queue2.offer(node2.right);
            }

        }
        return true;
    }


    /**
     * 判断结点 p 和 q 是否相同
     */
    private boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val;
    }

}