//19. 删除链表的倒数第n个结点
//
//给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
//
//示例：
//
//给定一个链表: 1->2->3->4->5, 和 n = 2.
//
//当删除了倒数第二个节点后，链表变为 1->2->3->5.
//说明：
//
//给定的 n 保证是有效的。
//
//可以添加哑结点，使头结点的处理不用单独考虑(即头结点前再加一个结点)
//


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

public class RemoveNthNodeFromEndOfList {
    //两次遍历
    public ListNode solution1(ListNode head, int n) {
        //无效的情况
        if (n <= 0 || head == null)
            return head;

        //求链表长度
        ListNode p = head, pre;
        int len = 0;
        while (p != null) {
            p = p.next;
            len++;
        }

        if (n > len) {
            return head;
        } else {
            //倒数第n个，就是正数len-n+1个
            n = len - n + 1;
            if (n == 1) {
                //要删除头结点单独考虑
                head = head.next;
            } else {
                //数到第n个，且保存p所指结点的前一结点pre
                pre = null;
                p = head;
                while (n > 1) {
                    pre = p;
                    p = p.next;
                    n--;
                }
                //删除p所指结点
                pre.next = p.next;
            }
        }
        return head;
    }

    //双指针法，一个指针先走n步
    public ListNode solution2(ListNode head, int n) {
        ListNode p = head, q = head, pre = null;

        int cnt = 0;
        while (q != null) {
            if (cnt >= n) {
                pre = p;
                p = p.next;
            }
            q = q.next;
            //q走的步数
            cnt++;
        }
        //注意删除头节点的情况
        if (p == head)
            head = head.next;
        else
            pre.next = p.next;

        return head;
    }
}
