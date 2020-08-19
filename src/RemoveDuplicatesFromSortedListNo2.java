//82. 删除排序列表的重复元素Ⅱ
//
//给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现的数字。
//
//跟83题比，83题是删除元素使得每个元素只出现一次，而本题要将出现重复的那个元素全部移除


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

public class RemoveDuplicatesFromSortedListNo2 {
    //借用83题思路
    public ListNode solution(ListNode head) {
        //假设找到，[i,j)为重复出现的元素
        //则有prei.next = j，删除重复元素
        //i = j，重新开始
        //注意只出现一次的元素，不能删

        //设置哑结点
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode prei = dummyNode, i = prei.next, j = i;
        //保持每次循环结束，prei指向i的前一个结点，i，j指向同一结点
        while (i != null) {
            j = j.next;
            if (j == null || j.val != i.val) {
                //找到[i,j)，现要判断是不是只出现一次
                if (i.next == j) {
                    prei = prei.next;
                    i = i.next;
                } else {
                    prei.next = j;
                    i = j;
                }
            }
        }
        return dummyNode.next;
    }
}
