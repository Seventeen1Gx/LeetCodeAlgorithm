// 445. 两数相加Ⅱ
//
// 给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
//
// 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
//
// 进阶:
// 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
//
// 思路：用栈或者递归反向操作


package src.add;

import src.util.ListNode;

import java.util.Stack;

public class AddTwoNumbersNo2 {
    // 使用栈
    public ListNode solution1(ListNode l1, ListNode l2) {
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        // 将两个链表的值存入栈中
        for (ListNode p = l1; p != null; p = p.next) {
            s1.push(p.val);
        }
        for (ListNode p = l2; p != null; p = p.next) {
            s2.push(p.val);
        }

        ListNode dummuNode = new ListNode(0), p = dummuNode;

        int num1, num2, carry = 0, res;
        Stack<Integer> s3 = new Stack<>();
        while (!s1.empty() || !s2.empty()) {
            num1 = num2 = 0;
            if (!s1.empty()) {
                num1 = s1.pop();
            }
            if (!s2.empty()) {
                num2 = s2.pop();
            }
            res = num1 + num2 + carry;
            carry = res / 10;
            res = res % 10;

            // 每位结果存入第三个栈
            s3.push(res);
        }

        if (carry == 1) {
            s3.push(1);
        }

        // 从第三个栈中取出每位结果
        while (!s3.empty()) {
            ListNode newNode = new ListNode(s3.pop());
            p.next = newNode;
            p = p.next;
        }

        return dummuNode.next;
    }

    // 结果的头结点
    ListNode ans;

    // 使用递归
    public ListNode solution2(ListNode l1, ListNode l2) {
        // 先求两链表的长度
        int len1, len2;
        len1 = len2 = 0;
        for (ListNode p = l1; p != null; p = p.next) {
            len1++;
        }
        for (ListNode p = l2; p != null; p = p.next) {
            len2++;
        }

        int carry = recursion(l1, len1, l2, len2);

        if (carry == 1) {
            ListNode newNode = new ListNode(1);
            newNode.next = ans;
            ans = newNode;
        }

        return ans;
    }

    // recursion 返回值为 l1 和 l2 两链相加结果存在长度为 max(len1,len2) 的结果链上，返回为结果链存不下的最高位进位，这个最高位进位我们作为返回结果返回
    // ans 作为全局变量，得到 ans 是recursion函数的一个附加功能
    //
    // 思路：
    // 计算当前对应结点的结果，先计算其之后尾链得到的结果，然后再接回去，用的链表的头接法
    private int recursion(ListNode l1, int len1, ListNode l2, int len2) {
        int carry, res;
        if (len1 == len2) {
            // 长度对齐时

            if (len1 == 0) {
                // 长度为 0 的两链相加，不对 ans 产生影响，且进位为 0
                return 0;
            }

            // 先去计算尾串相加的结果
            carry = recursion(l1.next, len1 - 1, l2.next, len2 - 1);
            // 计算当前位置的结果，然后接在 ans 之前
            res = l1.val + l2.val + carry;
        } else if (len1 > len2) {
            // 长度不对齐时，将短链前面视为带有值为 0 的结点
            carry = recursion(l1.next, len1 - 1, l2, len2);
            res = l1.val + carry;
        } else {
            // 转到 len1 > len2
            return recursion(l2, len2, l1, len1);
        }

        carry = res / 10;
        res = res % 10;
        ListNode newNode = new ListNode(res);
        newNode.next = ans;
        ans = newNode;

        return carry;
    }
}
