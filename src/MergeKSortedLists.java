//23. 合并K个排序列表
//
//合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。


package src;

import src.util.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

public class MergeKSortedLists {
    //多指针跟踪法
    public ListNode solution1(ListNode[] lists) {
        if (isEmpty(lists))
            return null;

        //哑节点(结果头节点的前一个节点)
        ListNode dummyNode = new ListNode(0), p = dummyNode, q, singleL;

        //至少还有两个链没处理完时，进入循环
        while ((singleL = isSingle(lists)) == null) {
            //获取值最小的那个结点
            q = min(lists);
            //接在结果链的链位
            p.next = q;
            p = p.next;
        }

        //出循环时，lists中还剩一个未处理好的串
        p.next = singleL;

        return dummyNode.next;
    }

    public boolean isEmpty(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return true;

        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null)
                //存在一个不为空
                return false;
        }
        return true;
    }

    //判断数组中是否只剩一个链没有处理完，若是，则返回这个链的头节点，若不是返回空
    public ListNode isSingle(ListNode[] lists) {
        ListNode l = lists[0];
        for (int i = 1; i < lists.length; i++) {
            if (lists[i] == null)
                continue;
            if (l != null && lists[i] != null)
                return null;
            if (l == null && lists[i] != null)
                l = lists[i];
        }
        return l;
    }

    //获取当前各个所指结点中最小的那个结点，且对应链的指针后移一位
    public ListNode min(ListNode[] lists) {
        int min = Integer.MAX_VALUE, index = 0;
        ListNode l , t;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] == null)
                continue;
            if (lists[i].val < min) {
                min = lists[i].val;
                index = i;
            }
        }
        //不用t的话，结果会一起后移一格(跟引用添加的顺序有关)
        t = lists[index];
        l = t;
        lists[index] = lists[index].next;
        return l;
    }

    //与方法1相同，使用优先队列优化上面的min过程
    public ListNode solution2(ListNode[] lists) {
        if (isEmpty(lists))
            return null;

        //创建优先队列，传入参数是长度，以及一个比较器
        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1.val < o2.val) return -1;     //o1<o2
                else if (o1.val == o2.val) return 0;    //o1=o2
                else return 1;  //o1>o2
            }
        });

        ListNode dummyNode = new ListNode(0), p = dummyNode;

        //每个链表的头指针插入优先级队列，他们在最终队列的位置是排好序的
        for (ListNode node : lists) {
            if (node != null) queue.add(node);
        }

        while (!queue.isEmpty()) {
            p.next = queue.poll();
            p = p.next;
            //p.next是刚才添加到结果链结点在原链的下一个结点
            if (p.next != null) queue.add(p.next);
        }

        return dummyNode.next;
    }

    //递归法
    public ListNode solution3(ListNode[] lists) {
        if (isEmpty(lists))
            return null;

        ListNode l, m;
        if ((l = isSingle(lists)) != null) {
            return l;
        } else {
            m = min(lists);
            m.next = solution3(lists);
        }
        return m;
    }

    //还有就是分治，最后合并的链表=合并(数组左半边链合并结果+数组有版本链合并结果)
    public ListNode solution4(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;
        return divideConquer(lists, 0, lists.length - 1);
    }

    //合并[l,r]包含的链表
    public ListNode divideConquer(ListNode[] listNodes, int l, int r) {
        if (l == r)
            //只有一条链
            return listNodes[l];
        else {
            //两链合并
            int mid = (l + r) / 2;
            ListNode l1 = divideConquer(listNodes, l, mid);
            ListNode l2 = divideConquer(listNodes, mid + 1, r);
            return mergeTwoList(l1, l2);
        }
    }

    //两两合并
    public ListNode solution5(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;

        ListNode ans = null;
        for (int i = 0; i < lists.length; i++) {
            ans = mergeTwoList(ans, lists[i]);
        }
        return ans;
    }


    //合并两个链表的官方代码，简洁！
    public ListNode mergeTwoList(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = mergeTwoList(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoList(l1, l2.next);
            return l2;
        }
    }

    public static void main(String[] args) {
        ListNode dummyNode1 = new ListNode(0);
        ListNode dummyNode2 = new ListNode(0);
        ListNode dummyNode3 = new ListNode(0);

        ListNode p = dummyNode1, q = dummyNode2, r = dummyNode3;

        p.next = new ListNode(1);
        p = p.next;
        p.next = new ListNode(4);
        p = p.next;
        p.next = new ListNode(5);

        q.next = new ListNode(1);
        q = q.next;
        q.next = new ListNode(3);
        q = q.next;
        q.next = new ListNode(4);

        r.next = new ListNode(2);
        r = r.next;
        r = new ListNode(6);


        MergeKSortedLists m = new MergeKSortedLists();

        m.solution1(new ListNode[]{dummyNode1.next, dummyNode2.next, dummyNode3.next});
    }
}
