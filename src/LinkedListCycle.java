package src;

import java.util.HashSet;
import java.util.Set;

/**
 * 141. 环形链表
 *
 * 给定一个链表，判断链表中是否有环。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class LinkedListCycle {
    /**
     * 双指针法，一个指针每次移动两步，一个指针每次移动一步，从而在含有环的链表中，这两个指针总能相遇
     */
    public boolean solution1(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode p = head, q = head.next;
        while (p != null && q != null) {
            if (p == q) {
                return true;
            }
            p = p.next;
            q = q.next;
            if (q != null) {
                q = q.next;
            }
        }
        return false;
    }

    /**
     * 还可以用哈希表记录被访问结点，当遍历到已被访问过的结点，说明链表中存在环
     */
    public boolean solution2(ListNode head) {
        Set<ListNode> hashSet = new HashSet<>();
        while (head != null) {
            if (hashSet.contains(head)) {
                return true;
            } else {
                hashSet.add(head);
                head = head.next;
            }
        }
        return false;
    }
}
