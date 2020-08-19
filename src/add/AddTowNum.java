// 2. 两数相加
//
// 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
//
// 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
//
// 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
//
// 思路：
// 按照加法运算的思路，结果上的一位，等于数1对应位加数2对应位，加前一位的进位


package src.add;

import src.util.ListNode;

import java.util.ArrayList;
import java.util.List;

public class AddTowNum {
    public ListNode solution(ListNode l1, ListNode l2) {
        // 结果链表的头结点
        ListNode headNode = new ListNode(-1);
        // 结果链表的游标，新结点接在 p 所指结点之后
        ListNode p = headNode;


        int num1, num2, carry = 0, result;
        while (l1 != null || l2 != null) {
            num1 = num2 = 0;
            if (l1 != null) {
                num1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                num2 = l2.val;
                l2 = l2.next;
            }

            // 计算当前位的结果值与产生的进位，这里将 result>9 与 result<9 进行了代码统一
            result = num1 + num2 + carry;
            carry = result / 10;
            result = result % 10;
            // 结果值存在新结点中，然后接在结果链表之后
            p.next = new ListNode(result);
            p = p.next;
        }

        // 注意最后的进位，若不为 0，加一个值为 1 的结点
        if (carry != 0) {
            p.next = new ListNode(1);
        }

        return headNode.next;
    }
}