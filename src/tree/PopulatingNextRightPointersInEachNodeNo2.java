// 117. 填充每个节点的下一个右侧节点指针 II
//
// 给定一个二叉树
//
// 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
//
// 初始状态下，所有 next 指针都被设置为 NULL。


package src.tree;

public class PopulatingNextRightPointersInEachNodeNo2 {
    public Node connect(Node root) {
        // 利用这一层的 next 遍历，去处理下一层的 next
        // 每层开头一个虚头结点，便于寻找下一层
        Node cur = root;
        while (cur != null) {
            Node head = new Node();
            Node tail = head;
            // 每次处理左右
            while (cur != null) {
                if (cur.left != null) {
                    tail.next = cur.left;
                    tail = tail.next;
                }
                if (cur.right != null) {
                    tail.next = cur.right;
                    tail = tail.next;
                }
                cur = cur.next;
            }
            cur = head.next;
        }
        return root;

    }
}
