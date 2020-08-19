//25. K个一组反转链表
//
//给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
//
//k 是一个正整数，它的值小于或等于链表的长度。
//
//如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。


package src;

import src.util.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class ReverseNodesInKGroup {
    //递归处理
    public ListNode solution1(ListNode head, int k) {
        if (k == 1)
            return head;

        if (head == null)
            return head;

        ListNode dummyNode = new ListNode(0), l, r, nextHead = null;
        dummyNode.next = head;
        l = r = dummyNode.next;

        int cnt = 1;
        //每次寻找要反转的head->end，用l，r表示，nextHead用来记住r的下一个结点
        while (cnt < k && r.next != null) {
            r = r.next;
            nextHead = r.next;
            cnt++;
        }

        if (cnt == k) {
            //l->r变为r->l，这个部分要从链表上断开，不断开的话，在reverseList函数中q已经超出末尾本该为null时不为null
            r.next = null;
            reverseList(l, r);
            dummyNode.next = r;
            l.next = solution1(nextHead, k);
            return dummyNode.next;
        } else {
            //不足k个结点，无需反转
            return head;
        }
    }

    //反转head->end->null的单链表，即将每个箭头反向，最终end->head->null
    public void reverseList(ListNode head, ListNode end) {
        //p->q变为p<-q，而防止q的下一个元素找不到，而事先用r保存
        ListNode p = head, q = p.next, r;
        while (q != null) {
            r = q.next;
            q.next = p;
            p = q;
            q = r;
        }
        //处理尾结点
        head.next = null;
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
        p = p.next;
        p.next = new ListNode(5);

        ReverseNodesInKGroup r = new ReverseNodesInKGroup();
        r.solution1(dummyNode.next, 2);
    }
}
