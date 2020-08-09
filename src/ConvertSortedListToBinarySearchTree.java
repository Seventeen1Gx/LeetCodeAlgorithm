package src;

/**
 * 109. 有序链表转换二叉搜索树
 *
 * 给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ConvertSortedListToBinarySearchTree {
    // 有序链表相较于有序数组，主要是其不能任意访问某个位置的结点
    // 获取单链表中点的方法 → 快慢指针

    public TreeNode solution(ListNode head) {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return new TreeNode(head.val);
        }
        // 到这里，说明链表至少二个结点

        // pre 始终记录慢指针的前一个结点
        ListNode pre = null, slowPtr = head, fastPtr = head;
        do {
            fastPtr = fastPtr.next.next;
            pre = slowPtr;
            slowPtr = slowPtr.next;
        } while (fastPtr != null && fastPtr.next != null);

        TreeNode root = new TreeNode(slowPtr.val);
        // 断开，形成两个链表
        pre.next = null;

        root.left = solution(head);
        root.right = solution(slowPtr.next);

        return root;
    }


}
