package src;

/**
 * 160. 相交链表
 *
 * 编写一个程序，找到两个单链表相交的起始节点。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class IntersectionOfTwoLinkedLists {
    public ListNode solution(ListNode headA, ListNode headB) {
        // 测量两链表的长度
        int len1 = getLength(headA);
        int len2 = getLength(headB);
        if (len1 > len2) {
            return getIntersectionNode(headA, len1, headB, len2);
        } else {
            return getIntersectionNode(headB, len2, headA, len1);
        }
    }

    /**
     * 该方法确保 A 链表长度大于 B 链表
     */
    private ListNode getIntersectionNode(ListNode headA, int len1, ListNode headB, int len2) {
        // 相交链表尾部一段是公共的
        // 所以长链表起始处需要后移 len1-len2 个结点，从而使尾部对齐
        ListNode p = headA, q = headB;

        int cnt = 0;
        while (cnt < len1 - len2) {
            cnt++;
            p = p.next;
        }

        // 现已对齐两链表的尾部
        // 故两指针可以同步移动
        while (p != null) {
            if (p == q) {
                return p;
            }
            p = p.next;
            q = q.next;
        }
        return null;
    }

    private int getLength(ListNode head) {
        ListNode p = head;
        int length = 0;
        while (p != null) {
            length++;
            p = p.next;
        }
        return length;
    }
}
