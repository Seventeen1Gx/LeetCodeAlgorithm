//2. 两数相加
//
//给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
//如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
//
//您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
//思路
//1. 按照加法运算的思路，结果上的一位，等于数1对应位加数2对应位，加前一位的进位
//2. 注意链表的使用


class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class AddTowNum {
    public ListNode solution(ListNode l1, ListNode l2) {
        //创建结果的头结点--统一操作
        ListNode headNode = new ListNode(-1);
        //前一结点
        ListNode preNode = headNode;
        //当前结点
        ListNode listNode;
        //前一位的进位--开始时为0
        int carry = 0;

        int val;
        while (l1 != null && l2 != null) {
            //创键一个新结点，记录l1加l2的值
            listNode = new ListNode(-1);
            //计算值与进位
            val = l1.val + l2.val + carry;
            if (val >= 10) {
                val -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            listNode.val = val;
            //连上我们已经计算好部分的链表上
            preNode.next = listNode;
            //游标下移
            preNode = preNode.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        //注意两数位数不等的情况
        while (l1 != null) {
            //过程同理上
            listNode = new ListNode(-1);
            //计算值与进位
            val = l1.val + carry;
            if (val >= 10) {
                val -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            listNode.val = val;
            //连上我们已经计算好部分的链表上
            preNode.next = listNode;
            //游标下移
            preNode = preNode.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            //过程同理上
            listNode = new ListNode(-1);
            //计算值与进位
            val = l2.val + carry;
            if (val >= 10) {
                val -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            listNode.val = val;
            //连上我们已经计算好部分的链表上
            preNode.next = listNode;
            //游标下移
            preNode = preNode.next;
            l2 = l2.next;
        }

        //注意最后的进位，若不为0，加一个值为1的结点
        if (carry != 0) {
            listNode = new ListNode(1);
            preNode.next = listNode;
        }

        return headNode.next;
    }

    //官方给的解法，与我思路一致，但代码更简洁
    public ListNode solution_official(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
