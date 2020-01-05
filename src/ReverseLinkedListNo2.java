package src;

/**
 * 92. 反转链表Ⅱ
 *
 * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
 *
 * 说明:
 * 1 ≤ m ≤ n ≤ 链表长度。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ReverseLinkedListNo2 {
    /**
     * 初始时：1 → 2 → 3 → ... temp1 → m → ... → n → temp2 → ... → L
     * 反转箭头：1 → 2 → 3 → ... temp1 → ← m ← ... ← n 断开 temp2 → ... → L
     * 最终：1 → 2 → 3 → ... temp1 → n → ... → m → temp2 → ... → L
     */
    public ListNode solution1(ListNode head, int m, int n) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;

        // cnt 记录着 p 所指结点的号码
        int cnt = 0;
        ListNode p = dummyNode;
        while (cnt != m - 1) {
            p = p.next;
            cnt++;
        }
        // 出循环后 p 指向 m-1
        ListNode temp1 = p;

        ListNode q = p.next, r;
        // cnt 改为记录 q 所指向结点的号码
        cnt++;
        while (cnt != n + 1) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
            cnt++;
        }
        // 出循环后 q 指向 n+1，p 指向 n

        // 将反转后的链表接回原来的链表上
        temp1.next.next = q;
        temp1.next = p;

        return dummyNode.next;
    }


    /**
     * 保证 solution2() 一定能完成任务
     * 即一定能将 head 开头的链表的 m 到 n 部分正确反转，然后返回结果链表的头结点引用
     */
    public ListNode solution2(ListNode head, int m, int n) {
        if (m == 1 && n == 1) {
            return head;
        }

        if (m == 1) {
            // 反转链表开头 n-m+1 个结点
            ListNode p = solution2(head.next, 1, n - 1);
            ListNode q = head.next.next;
            head.next.next = head;
            head.next = q;
            return p;
        } else {
            head.next = solution2(head.next, m - 1, n - 1);
            return head;
        }
    }
}
