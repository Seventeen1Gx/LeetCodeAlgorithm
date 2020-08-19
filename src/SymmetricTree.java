package src;

import src.util.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 101. 对称二叉树
 *
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 * 说明：这里的镜像对称是以过根结点的竖轴来看的
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class SymmetricTree {
    public boolean solution(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric1(root.left, root.right);
    }

    /**
     * 判断两棵树是否是成镜像的
     */
    private boolean isSymmetric1(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && isSymmetric1(p.left, q.right) && isSymmetric1(p.right, q.left);
    }

    /**
     * 层次遍历思想
     */
    private boolean isSymmetric2(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();

        // 根结点入队
        queue1.offer(p);
        queue2.offer(q);
        // 队列不空时循环
        while (!queue1.isEmpty()) {
            // 出队
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();

            if (!check(node1, node2)) {
                return false;
            }

            // 子结点入队，注意这里两棵树的子节点入队顺序相反
            if (node1 != null) {
                queue1.offer(node1.left);
                queue1.offer(node1.right);
                queue2.offer(node2.right);
                queue2.offer(node2.left);
            }
        }
        return true;
    }

    /**
     * 判断两结点是否为相同结点
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
