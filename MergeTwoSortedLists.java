//21. 合并两个有序列表
//
//将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class MergeTwoSortedLists {
    public ListNode solution1(ListNode l1, ListNode l2) {
        //哑节点(结果头节点的前一个节点)
        ListNode dummyNode = new ListNode(0);

        //处理空的情况
        if (l1 == null && l2 == null)
            dummyNode.next = null;
        else if (l1 == null)
            dummyNode.next = l2;
        else
            dummyNode.next = l1;


        //p来跟踪合并后链表的尾节点
        ListNode p = dummyNode;
        //到这l1和l2都不为空
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        //接上剩余部分
        if (l1 != null)
            p.next = l1;
        if (l2 != null)
            p.next = l2;

        return dummyNode.next;
    }

    //递归，每次接上一个，然后合并剩下两链表
    public ListNode solution2(ListNode l1, ListNode l2) {
        //哑节点(结果头节点的前一个节点)
        ListNode dummyNode = new ListNode(0);
        recursive(dummyNode, l1, l2);
        return dummyNode.next;
    }

    //p是已合并列表的末尾，将l1和l2有序得合并到已合并列表的后面，返回合并后列表的头结点
    public void recursive(ListNode p, ListNode l1, ListNode l2) {
        //处理空的情况
        if (l1 == null && l2 == null)
            p.next = null;
        else if (l1 == null)
            p.next = l2;
        else if (l2 == null)
            p.next = l1;
        else {
            //已合并列表接上两结点中较小的那个
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
            recursive(p, l1, l2);
        }
    }

    //官方代码，简洁！
    public ListNode solution2_official(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = solution2_official(l1.next, l2);
            return l1;
        }
        else {
            l2.next = solution2_official(l1, l2.next);
            return l2;
        }
    }

    //还有一种是将一个列表的元素插入另一个列表的元素中，假设len(l1)<=len(l2)
    public ListNode solution3(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;


        ListNode dummyNode = new ListNode(0);
        dummyNode.next = l1;
        //插入位置在p之后，q之前
        ListNode p = dummyNode, q = dummyNode.next, t;
        while (l2 != null) {
            //找到第一个值大于l2值的结点，然后将l2插入到他之前
            //之后结点的插入位置也总是不小于上一个插入结点
            while (q != null && q.val <= l2.val) {
                p = q;
                q = q.next;
            }

            if (q == null) {
                //当前l2插入到l1的末尾
                p.next = l2;
                break;
            } else {
                //这里注意各个指针最后指向要正确
                //注意多个不同名称的引用，其实指向的是同一个东西，那这个东西改变，所以的引用也改变，所以用到了t保存改变之前的东西
                t = l2.next;
                p.next = l2;
                l2.next = q;
                p = l2;
                l2 = t;
            }
        }

        l1 = dummyNode.next;
        return l1;
    }

    public static void main(String[] args) {
        ListNode dummyNode1 = new ListNode(0);
        ListNode dummyNode2 = new ListNode(0);

        ListNode p = dummyNode1, q = dummyNode2;

        p.next = new ListNode(1);
        p = p.next;
        p.next = new ListNode(2);
        p = p.next;
        p.next = new ListNode(4);

        q.next = new ListNode(1);
        q = q.next;
        q.next = new ListNode(3);
        q = q.next;
        q.next = new ListNode(4);

        MergeTwoSortedLists m = new MergeTwoSortedLists();

        m.solution3(dummyNode1.next, dummyNode2.next);
    }
}
