// 116. 填充每个节点的下一个右侧节点指针
//
// 给定一个完美二叉树，其所有叶子节点都在同一层，每个父节点都有两个子节点。
//
// 充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
//
// 初始状态下，所有 next 指针都被设置为 NULL。


package src.tree;

import java.util.LinkedList;
import java.util.Queue;

public class PopulatingNextRightPointersInEachNode {
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node pre = null;
            for (int i = 0; i < size; i++) {
                Node cur = queue.poll();
                if (pre != null) {
                    pre.next = cur;
                }
                pre = cur;

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
        }
        return root;
    }

    public void recursion(Node root) {
        // 递归解法
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }
        recursion(root.left);
        recursion(root.right);
        // 左子树的最右结点和右子树的最左结点连接
        Node node_1 = root.left;
        Node node_2 = root.right;
        while (node_1.right != null) {
            node_1.next = node_2;
            node_1 = node_1.right;
            node_2 = node_2.left;
        }
    }
}

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val,Node _left,Node _right,Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}
