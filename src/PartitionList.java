//86. 分隔列表
//
//给定一个链表和一个特定值 x，对链表进行分隔，使得所有小于 x 的节点都在大于或等于 x 的节点之前。
//
//你应当保留两个分区中每个节点的初始相对位置。
//
//思路：
//x 将链表分成两部分，然后需要一部分在前，一部分在后


package src;

public class PartitionList {
    //先拆后接
    public ListNode solution(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }

        //把每个结点断开，再根据值大小接到head1或head2上
        //一定要断开，拿下来，不然会造成死循环

        //连接小于x的
        ListNode head1 = new ListNode(0), p1 = head1;
        //连接大于等于x的
        ListNode head2 = new ListNode(0), p2 = head2;

        ListNode p = head;

        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }
            p = p.next;

            //断开
            p1.next = null;
            p2.next = null;
        }

        //连接
        p1.next = head2.next;
        return head1.next;
    }
}
