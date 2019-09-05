//19. 删除链表的倒数第n个结点
//
//给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
//
//你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。


package src;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class SwapNodesInPairs {
    //遍历然后交换
    public ListNode solution1(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;


        ListNode p = dummyNode, q = dummyNode.next, t, u;
        //开始时，p->q->t->u，交换q、t，得到p->t->q->u
        while (q != null && q.next != null) {
            t = q.next;
            u = t.next;
            //交换
            p.next = t;
            t.next = q;
            q.next = u;
            //p和q移到下一个要处理的地方
            //下面这种移动方法是错的，因为t、u已经不是这个顺序了：p->q->t->u，而是：p->t->q->u
            //p = t;
            //q = u;
            p = q;
            q = u;
        }

        return dummyNode.next;
    }


    //也可以写成递归，每次交换head和head.next，然后处理head.next.next开头的链
    public ListNode solution2(ListNode head) {
        if (head == null || head.next == null)
            return head;

        //交换head与head.next
        //开始时p->q->r
        //结束时q->p->r
        ListNode p = head, q = p.next, r = q.next;
        q.next = p;
        p.next = solution2(r);
        return q;
    }



    public static void main(String[] args) {
        ListNode dummyNode = new ListNode(0);

        ListNode p = dummyNode;

        p.next = new ListNode(1);
        p = p.next;
        p.next = new ListNode(2);
        p = p.next;
        p.next = new ListNode(3);
        p = p.next;
        p.next = new ListNode(4);

        SwapNodesInPairs s = new SwapNodesInPairs();
        s.solution1(dummyNode.next);
    }
}
