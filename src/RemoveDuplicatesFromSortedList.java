//83. 删除排序链表中的重复元素
//
//给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。


package src;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class RemoveDuplicatesFromSortedList {
    //排序列表，相同元素在相邻位置
    //两个指针，i总是指向每个元素第一次出现的位置，j从i+1开始，寻找i之后的下一个新元素第一次出现的位置
    //然后i.next=j,i=j,j++
    public ListNode solution(ListNode head) {
        ListNode i = head, j = head;
        while (i != null) {
            j = j.next;
            if (j == null || j.val != i.val) {
                i.next = j;
                i = j;
            }
        }
        return head;
    }
}
