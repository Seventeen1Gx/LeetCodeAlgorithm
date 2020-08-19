//61. 旋转链表
//
//给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

package src;

import src.util.ListNode;

public class RotateList {
    //向右移动k个位置，就是将结尾k个结点放到开头
    //先首尾相连，组成一个环，然后找到切割点
    public ListNode solution(ListNode head, int k) {
        //非法情况
        if (head == null)
            return null;

        //游标p指向第pi个结点
        ListNode p = head;
        int pi = 1;
        //循环结束p指向尾结点，pi表示有多少个结点
        while (p.next != null) {
            p = p.next;
            pi++;
        }

        //首尾相连
        p.next = head;

        //k超过结点数时，等价于k%pi
        k = k % pi;

        //将结尾k个结点放到开头，也就是将开头pi-k个结点放到结尾
        k = pi - k;
        //从头开始，向后数第k个结点
        //开始时，p指向头节点的前一个结点--原尾结点
        while (k > 0) {
            p = p.next;
            k--;
        }
        //将p指向的结点之后的边断开即可
        ListNode newHead = p.next;
        p.next = null;
        return newHead;
    }
}
