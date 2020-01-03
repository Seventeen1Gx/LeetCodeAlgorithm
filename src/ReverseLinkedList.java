package src;

import javafx.util.Pair;

import java.util.Stack;

/**
 * 206. 反转链表
 *
 * 反转一个单链表。
 *
 * @author Seventeen1Gx
 * @version 1.0
 */
public class ReverseLinkedList {
    public ListNode solution1(ListNode head) {
        Pair<ListNode, ListNode> pair = recursion(head);
        return pair.getKey();
    }

    /**
     * 返回反转链表之后的头尾指针
     */
    private Pair<ListNode, ListNode> recursion(ListNode head) {
        if (head == null) {
            return new Pair<>(null, null);
        }

        if (head.next == null) {
            return new Pair<>(head, head);
        }

        // 摘下头结点
        ListNode p = head, q = head.next;
        p.next = null;
        // 反转头结点之后的链表
        Pair<ListNode, ListNode> pair = recursion(q);
        // 将头结点接在反转链表尾部
        pair.getValue().next = p;
        return new Pair<>(pair.getKey(), p);
    }

    public ListNode solution2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 使用栈
        Stack<ListNode> stack = new Stack<>();
        // 遍历链表
        ListNode p = head;
        while (p != null) {
            stack.push(p);
            p = p.next;
        }

        ListNode dummyNode = new ListNode(0);
        p = dummyNode;
        while (!stack.empty()) {
            p.next = stack.pop();
            p = p.next;
        }
        p.next = null;
        return dummyNode.next;
    }

    /**
     * 假设链表有 m 个结点，且后 k 个结点已被反转，那么如何处理之前的结点，使整个链表被反转
     * 初始时：1 → 2 → 3 → ... → m-k → m+1-k → ... → m
     * 处理中某个时刻：1 → 2 → 3 → ... → m-k → m+1-k ← ... ← m
     * 现要将 m-k → m+1-k 的右箭头变左箭头
     * 即 (m+1-k).next 应指向 (m-k)，注：这里我们用“(结点标号)”表示这个标号指定的结点
     * 使用语句 (m+1-k) = (m-k).next; (m+1-k).next = (m-k);
     * 简化就是 (m-k).next.next = (m-k)
     *
     * 在方法一的递归当中，我们用 Pair 结构记录反转后链表的头尾结点引用，为的就是在尾部接上 head 结点
     * 而其实尾结点可以直接被找到，即 head.next
     */
    public ListNode solution3(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 先反转当前结点之后的结点
        ListNode p = solution3(head.next);
        // 虽然当前结点之后结点被反转了，但此时没有改变 head.next 的指向
        // head.next 指向反转后链表的结尾
        head.next.next = head;
        // 此时head变为新的链尾
        head.next = null;
        // 反转链表的头结点仍未改变
        return p;
    }

    /**
     * 反转链表，就是把每个右箭头变为左箭头
     * 初始时：1 → 2 → 3 → ... → p → q → ... → m
     * 处理中某个时刻：1 ← 2 ← 3 ← ... ← p 断开 q → r → ... → m
     * 可以看到，左边是处理好的，右边是还未处理的
     * 接下来
     * q.next = p
     * 即有：1 ← 2 ← 3 ← ... ← p ← q 断开 r → ... → m
     * 这时 q → r 被断开，所以我们要用第三个指针保存 r 结点位置
     * 之后更新
     * p = q; q = r; r = r.next;
     */
    public ListNode solution4(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode p = null;
        ListNode q = head;
        ListNode r;
        while (q != null) {
            // 记住 q 之后结点，因为 q.next 将会被改变，所有要防止丢失 q.next 这个结点的位置
            r = q.next;
            // 反转箭头
            q.next = p;
            // 更新
            p = q;
            q = r;
        }
        return p;
    }
}
